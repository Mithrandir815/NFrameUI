package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.core.chart.Chart
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.extension.rangeWith
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import com.patrykandpatrick.vico.core.scroll.ScrollListener
import com.patrykandpatrick.vico.core.scroll.ScrollListenerHost
import kotlin.math.abs

/**
 * Houses information on a [Chart]’s scroll state. Allows for programmatic scrolling.
 */
class ChartScrollState : ScrollableState, ScrollListenerHost {

    private val _value: MutableState<Float> = mutableStateOf(0f)
    private val _maxValue: MutableState<Float> = mutableStateOf(0f)
    private val scrollListeners: MutableSet<ScrollListener> = mutableSetOf()
    private var initialScrollHandled: Boolean = false

    /**
     * The current scroll amount (in pixels).
     */
    var value: Float
        get() = _value.value
        private set(newValue) {
            val oldValue = value
            _value.value = newValue
            scrollListeners.forEach { scrollListener -> scrollListener.onValueChanged(oldValue, newValue) }
        }

    /**
     * The maximum scroll amount (in pixels).
     */
    var maxValue: Float
        get() = _maxValue.value
        internal set(newMaxValue) {
            val oldMaxValue = maxValue
            _maxValue.value = newMaxValue
            if (abs(value) > abs(newMaxValue)) value = newMaxValue
            scrollListeners.forEach { scrollListener -> scrollListener.onMaxValueChanged(oldMaxValue, newMaxValue) }
        }

    private val scrollableState = ScrollableState { delta ->
        val unlimitedValue = value + delta
        val limitedValue = unlimitedValue.coerceIn(0f.rangeWith(maxValue))
        val consumedValue = limitedValue - value
        value += consumedValue

        val unconsumedScroll = delta - consumedValue
        if (unconsumedScroll != 0f) notifyUnconsumedScroll(unconsumedScroll)

        if (unlimitedValue != limitedValue) consumedValue else delta
    }

    override val isScrollInProgress: Boolean
        get() = scrollableState.isScrollInProgress

    override suspend fun scroll(scrollPriority: MutatePriority, block: suspend ScrollScope.() -> Unit) {
        scrollableState.scroll(scrollPriority, block)
    }

    override fun dispatchRawDelta(delta: Float): Float = scrollableState.dispatchRawDelta(delta)

    override fun registerScrollListener(scrollListener: ScrollListener) {
        with(scrollListener) {
            if (scrollListeners.add(this).not()) return@with
            onValueChanged(oldValue = value, newValue = value)
            onMaxValueChanged(oldMaxValue = maxValue, newMaxValue = maxValue)
        }
    }

    override fun removeScrollListener(scrollListener: ScrollListener) {
        scrollListeners.remove(scrollListener)
    }

    internal fun handleInitialScroll(initialScroll: InitialScroll) {
        if (initialScrollHandled) return
        value = when (initialScroll) {
            InitialScroll.Start -> 0f
            InitialScroll.End -> maxValue
        }
        initialScrollHandled = true
    }

    private fun notifyUnconsumedScroll(delta: Float) {
        scrollListeners.forEach { scrollListener -> scrollListener.onScrollNotConsumed(delta) }
    }
}

/**
 * Houses scrolling-related settings for charts.
 *
 * @property isScrollEnabled whether horizontal scrolling is enabled.
 * @property initialScroll represents the chart’s initial scroll position.
 * @property autoScrollCondition defines when an automatic scroll should be performed.
 * @property autoScrollAnimationSpec the [AnimationSpec] to use for automatic scrolling.
 */
@Stable
class ChartScrollSpec<in Model : ChartEntryModel>(
    val isScrollEnabled: Boolean,
    val initialScroll: InitialScroll,
    val autoScrollCondition: AutoScrollCondition<Model>,
    val autoScrollAnimationSpec: AnimationSpec<Float>,
) {

    /**
     * Performs an automatic scroll.
     */
    suspend fun performAutoScroll(
        model: Model,
        oldModel: Model?,
        chartScrollState: ChartScrollState,
    ) {
        if (autoScrollCondition.shouldPerformAutoScroll(model, oldModel)) {
            if (chartScrollState.isScrollInProgress) {
                chartScrollState.stopScroll(MutatePriority.PreventUserInput)
            }

            chartScrollState.animateScrollBy(
                value = when (initialScroll) {
                    InitialScroll.Start -> -chartScrollState.value
                    InitialScroll.End -> -chartScrollState.value + chartScrollState.maxValue
                },
                animationSpec = autoScrollAnimationSpec,
            )
        }
    }
}
