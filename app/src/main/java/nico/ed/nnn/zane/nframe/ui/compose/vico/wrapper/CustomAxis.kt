package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import android.graphics.RectF
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.AxisRenderer
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DefaultAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.orZero
import com.patrykandpatrick.vico.core.extension.setAll

/**
 * A basic implementation of [AxisRenderer] used throughout the library.
 *
 * @see AxisRenderer
 * @see HorizontalAxis
 * @see VerticalAxis
 */
abstract class CustomAxis<Position : AxisPosition> : AxisRenderer<Position> {

    private val restrictedBounds: MutableList<RectF> = mutableListOf()

    override val bounds: RectF = RectF()

    protected val MeasureContext.axisThickness: Float
        get() = axisLine?.thicknessDp.orZero.pixels

    protected val MeasureContext.tickThickness: Float
        get() = tick?.thicknessDp.orZero.pixels

    protected val MeasureContext.guidelineThickness: Float
        get() = guideline?.thicknessDp.orZero.pixels

    protected val MeasureContext.tickLength: Float
        get() = if (tick != null) tickLengthDp.pixels else 0f

    /**
     * The [TextComponent] to use for labels.
     */
    var labels: List<TextComponent?>? = null

    /**
     * The [LineComponent] to use for the axis line.
     */
    var axisLine: LineComponent? = null

    /**
     * The [LineComponent] to use for ticks.
     */
    var tick: LineComponent? = null

    /**
     * The [LineComponent] to use for guidelines.
     */
    var guideline: LineComponent? = null

    /**
     * The tick length (in dp).
     */
    var tickLengthDp: Float = 0f

    /**
     * Used by [Axis] subclasses for sizing and layout.
     */
    var sizeConstraint: SizeConstraint = SizeConstraint.Auto()

    /**
     * The [AxisValueFormatter] for the axis.
     */
    var valueFormatter: AxisValueFormatter<Position> = DefaultAxisValueFormatter()

    /**
     * The rotation of axis labels (in degrees).
     */
    var labelRotationDegrees: Float = 0f

    /**
     * An optional [TextComponent] to use as the axis title.
     */
    var titleComponent: TextComponent? = null

    /**
     * The axis title.
     */
    var title: CharSequence? = null

    override fun setRestrictedBounds(vararg bounds: RectF?) {
        restrictedBounds.setAll(bounds.filterNotNull())
    }

    protected fun isNotInRestrictedBounds(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
    ): Boolean = restrictedBounds.none {
        it.contains(left, top, right, bottom) || it.intersects(left, top, right, bottom)
    }

    /**
     * Used to construct [Axis] instances.
     */
    open class Builder<Position : AxisPosition>(builder: Builder<Position>? = null) {
        /**
         * The [TextComponent] to use for labels.
         */
        var label: List<TextComponent?>? = builder?.label

        /**
         * The [LineComponent] to use for the axis line.
         */
        var axis: LineComponent? = builder?.axis

        /**
         * The [LineComponent] to use for axis ticks.
         */
        var tick: LineComponent? = builder?.tick

        /**
         * The tick length (in dp).
         */
        var tickLengthDp: Float = builder?.tickLengthDp ?: DefaultDimens.AXIS_TICK_LENGTH

        /**
         * The [LineComponent] to use for guidelines.
         */
        var guideline: LineComponent? = builder?.guideline

        /**
         * The [AxisValueFormatter] for the axis.
         */
        var valueFormatter: AxisValueFormatter<Position> =
            builder?.valueFormatter ?: DecimalFormatAxisValueFormatter()

        /**
         * Used by [Axis] subclasses for sizing and layout.
         */
        var sizeConstraint: SizeConstraint = SizeConstraint.Auto()

        /**
         * An optional [TextComponent] to use as the axis title.
         */
        var titleComponent: TextComponent? = builder?.titleComponent

        /**
         * The axis title.
         */
        var title: CharSequence? = builder?.title

        /**
         * The rotation of axis labels (in degrees).
         */
        var labelRotationDegrees: Float = builder?.labelRotationDegrees ?: 0f
    }

    /**
     * Defines how an [Axis] is to size itself.
     * - For [VerticalAxis], this defines the width.
     * - For [HorizontalAxis], this defines the height.
     *
     * @see [VerticalAxis]
     * @see [HorizontalAxis]
     */
    sealed class SizeConstraint {

        /**
         * The axis will measure itself and use as much space as it needs, but no less than [minSizeDp], and no more
         * than [maxSizeDp].
         */
        class Auto(
            val minSizeDp: Float = 0f,
            val maxSizeDp: Float = Float.MAX_VALUE,
        ) : SizeConstraint()

        /**
         * The axis size will be exactly [sizeDp].
         */
        class Exact(val sizeDp: Float) : SizeConstraint()

        /**
         * The axis will use a fraction of the available space.
         *
         * @property fraction the fraction of the available space that the axis should use.
         */
        class Fraction(val fraction: Float) : SizeConstraint() {
            init {
                require(fraction in MIN..MAX) { "Expected a value in the interval [$MIN, $MAX]. Got $fraction." }
            }

            private companion object {
                const val MIN = 0f
                const val MAX = 0.5f
            }
        }

        /**
         * The axis will measure the width of its label component ([labels]) for the given [String] ([text]), and it will
         * use this width as its size. In the case of [VerticalAxis], the width of the axis line and the tick length
         * will also be considered.
         */
        class TextWidth(val text: String) : SizeConstraint()
    }
}

/**
 * A convenience function that allows for applying the properties from an [Axis.Builder] to an [Axis] subclass.
 *
 * @param axis the [Axis] whose properties will be updated to this [Axis.Builder]’s properties.
 */
fun <Position : AxisPosition, A : CustomAxis<Position>> CustomAxis.Builder<Position>.setTo(axis: A): A {
    axis.axisLine = this.axis
    axis.tick = tick
    axis.guideline = guideline
    axis.labels = label
    axis.tickLengthDp = tickLengthDp
    axis.valueFormatter = valueFormatter
    axis.sizeConstraint = sizeConstraint
    axis.titleComponent = titleComponent
    axis.title = title
    axis.labelRotationDegrees = labelRotationDegrees
    return axis
}
