package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import android.graphics.RectF
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.layout.segmented
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.gesture.OnZoom
import com.patrykandpatrick.vico.compose.layout.getMeasureContext
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DEF_MAX_ZOOM
import com.patrykandpatrick.vico.core.DEF_MIN_ZOOM
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.AxisManager
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.AxisRenderer
import com.patrykandpatrick.vico.core.chart.Chart
import com.patrykandpatrick.vico.core.chart.draw.chartDrawContext
import com.patrykandpatrick.vico.core.chart.draw.drawMarker
import com.patrykandpatrick.vico.core.chart.draw.getMaxScrollDistance
import com.patrykandpatrick.vico.core.chart.edges.FadingEdges
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartModelProducer
import com.patrykandpatrick.vico.core.extension.set
import com.patrykandpatrick.vico.core.layout.VirtualLayout
import com.patrykandpatrick.vico.core.legend.Legend
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerVisibilityChangeListener
import com.patrykandpatrick.vico.core.model.Point
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import com.patrykandpatrick.vico.core.scroll.ScrollListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Displays a chart.
 *
 * This function accepts a [ChartEntryModel]. For dynamic data, use the function overload that accepts a
 * [ChartModelProducer] instance.
 *
 * @param chart the chart itself (excluding axes, markers, etc.). You can use [lineChart] or [columnChart], or provide a
 * custom [Chart] implementation.
 * @param model the [ChartEntryModel] for the chart.
 * @param modifier the modifier to be applied to the chart.
 * @param startAxis the axis displayed at the start of the chart.
 * @param topAxis the axis displayed at the top of the chart.
 * @param endAxis the axis displayed at the end of the chart.
 * @param bottomAxis the axis displayed at the bottom of the chart.
 * @param marker appears when the chart is touched, highlighting the entry or entries nearest to the touch point.
 * @param markerVisibilityChangeListener allows for listening to [marker] visibility changes.
 * @param legend an optional legend for the chart.
 * @param chartScrollSpec houses scrolling-related settings.
 * @param isZoomEnabled whether zooming in and out is enabled.
 * @param oldModel the chart’s previous [ChartEntryModel]. This is used to determine whether to perform an automatic
 * scroll.
 * @param fadingEdges applies a horizontal fade to the edges of the chart area for scrollable charts.
 * @param autoScaleUp defines whether the content of a scrollable chart should be scaled up when the dimensions are such
 * that, at a scale factor of 1, an empty space would be visible near the end edge of the chart.
 * @param chartScrollState houses information on the chart’s scroll state. Allows for programmatic scrolling.
 * @param horizontalLayout defines how the chart’s content is positioned horizontally.
 * @param getXStep overrides the _x_ step (the difference between the _x_ values of neighboring major entries). If this
 * is null, the default _x_ step ([ChartEntryModel.xGcd]) is used.
 */
@Composable
fun <Model : ChartEntryModel> Chart(
    chart: Chart<Model>,
    model: Model,
    modifier: Modifier = Modifier,
    startAxis: AxisRenderer<AxisPosition.Vertical.Start>? = null,
    topAxis: AxisRenderer<AxisPosition.Horizontal.Top>? = null,
    endAxis: AxisRenderer<AxisPosition.Vertical.End>? = null,
    bottomAxis: AxisRenderer<AxisPosition.Horizontal.Bottom>? = null,
    marker: Marker? = null,
    markerVisibilityChangeListener: MarkerVisibilityChangeListener? = null,
    legend: Legend? = null,
    chartScrollSpec: ChartScrollSpec<Model> = rememberChartScrollSpec(),
    isZoomEnabled: Boolean = true,
    oldModel: Model? = null,
    fadingEdges: FadingEdges? = null,
    autoScaleUp: AutoScaleUp = AutoScaleUp.Full,
    chartScrollState: ChartScrollState = rememberChartScrollState(),
    horizontalLayout: HorizontalLayout = HorizontalLayout.segmented(),
    getXStep: ((Model) -> Float)? = null,
) {
    ChartBox(modifier = modifier) {
        ChartImpl(
            chart = chart,
            model = model,
            startAxis = startAxis,
            topAxis = topAxis,
            endAxis = endAxis,
            bottomAxis = bottomAxis,
            marker = marker,
            markerVisibilityChangeListener = markerVisibilityChangeListener,
            legend = legend,
            chartScrollSpec = chartScrollSpec,
            isZoomEnabled = isZoomEnabled,
            oldModel = oldModel,
            fadingEdges = fadingEdges,
            autoScaleUp = autoScaleUp,
            chartScrollState = chartScrollState,
            horizontalLayout = horizontalLayout,
            getXStep = getXStep,
        )
    }
}

/**
 * Creates and remembers an instance of [ChartScrollSpec].
 */
@Composable
fun <Model : ChartEntryModel> rememberChartScrollSpec(
    isScrollEnabled: Boolean = true,
    initialScroll: InitialScroll = InitialScroll.Start,
    autoScrollCondition: AutoScrollCondition<Model> = AutoScrollCondition.Never,
    autoScrollAnimationSpec: AnimationSpec<Float> = spring(),
): ChartScrollSpec<Model> = remember(
    isScrollEnabled,
    initialScroll,
    autoScrollCondition,
    autoScrollAnimationSpec,
) {
    ChartScrollSpec(
        isScrollEnabled = isScrollEnabled,
        initialScroll = initialScroll,
        autoScrollCondition = autoScrollCondition,
        autoScrollAnimationSpec = autoScrollAnimationSpec,
    )
}

@Composable
internal fun ChartBox(
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.height(DefaultDimens.CHART_HEIGHT.dp),
        content = content,
    )
}

@Suppress("LongMethod")
@Composable
internal fun <Model : ChartEntryModel> ChartImpl(
    chart: Chart<Model>,
    model: Model,
    startAxis: AxisRenderer<AxisPosition.Vertical.Start>?,
    topAxis: AxisRenderer<AxisPosition.Horizontal.Top>?,
    endAxis: AxisRenderer<AxisPosition.Vertical.End>?,
    bottomAxis: AxisRenderer<AxisPosition.Horizontal.Bottom>?,
    marker: Marker?,
    markerVisibilityChangeListener: MarkerVisibilityChangeListener?,
    legend: Legend?,
    chartScrollSpec: ChartScrollSpec<Model>,
    isZoomEnabled: Boolean,
    oldModel: Model? = null,
    fadingEdges: FadingEdges?,
    autoScaleUp: AutoScaleUp,
    chartScrollState: ChartScrollState = rememberChartScrollState(),
    horizontalLayout: HorizontalLayout,
    getXStep: ((Model) -> Float)?,
) {
    val axisManager = remember { AxisManager() }
    val bounds = remember { RectF() }
    val markerTouchPoint = remember { mutableStateOf<Point?>(null) }
    val zoom = remember { mutableStateOf(1f) }
    val measureContext = getMeasureContext(chartScrollSpec.isScrollEnabled, zoom.value, bounds, horizontalLayout)
    val scrollListener = rememberScrollListener(markerTouchPoint)
    val lastMarkerEntryModels = remember { mutableStateOf(emptyList<Marker.EntryModel>()) }

    axisManager.setAxes(startAxis, topAxis, endAxis, bottomAxis)
    chartScrollState.registerScrollListener(scrollListener)

    val virtualLayout = remember { VirtualLayout(axisManager) }
    val elevationOverlayColor = currentChartStyle.elevationOverlayColor.toArgb()
    val (wasMarkerVisible, setWasMarkerVisible) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val onZoom = rememberZoomState(
        zoom = zoom,
        getScroll = { chartScrollState.value },
        scrollBy = { value -> coroutineScope.launch { chartScrollState.scrollBy(value) } },
        chartBounds = chart.bounds,
    )

    LaunchedEffect(key1 = model.id) {
        chartScrollSpec.performAutoScroll(
            model = model,
            oldModel = oldModel,
            chartScrollState = chartScrollState,
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .chartTouchEvent(
                setTouchPoint = remember(marker == null) {
                    markerTouchPoint
                        .component2()
                        .takeIf { marker != null }
                },
                isScrollEnabled = chartScrollSpec.isScrollEnabled,
                scrollableState = chartScrollState,
                onZoom = onZoom.takeIf { isZoomEnabled },
            ),
    ) {
        bounds.set(left = 0, top = 0, right = size.width, bottom = size.height)
        chart.updateChartValues(measureContext.chartValuesManager, model, getXStep?.invoke(model))

        val horizontalDimensions = chart.getHorizontalDimensions(measureContext, model)

        val chartBounds = virtualLayout.setBounds(
            context = measureContext,
            contentBounds = bounds,
            chart = chart,
            legend = legend,
            horizontalDimensions = horizontalDimensions,
            marker,
        )

        if (chartBounds.isEmpty) return@Canvas

        chartScrollState.maxValue = measureContext.getMaxScrollDistance(
            chartWidth = chart.bounds.width(),
            horizontalDimensions = horizontalDimensions,
        )

        chartScrollState.handleInitialScroll(initialScroll = chartScrollSpec.initialScroll)

        val chartDrawContext = chartDrawContext(
            canvas = drawContext.canvas.nativeCanvas,
            elevationOverlayColor = elevationOverlayColor,
            measureContext = measureContext,
            markerTouchPoint = markerTouchPoint.value,
            horizontalDimensions = horizontalDimensions,
            chartBounds = chart.bounds,
            horizontalScroll = chartScrollState.value,
            autoScaleUp = autoScaleUp,
        )

        // ガイドラインの下がグラフに隠れるよう、chartDrawContextの定義直後にマーカーの描画して一番後ろに来させる
        if (marker != null) {
            chartDrawContext.drawMarker(
                marker = marker,
                markerTouchPoint = markerTouchPoint.value,
                chart = chart,
                markerVisibilityChangeListener = markerVisibilityChangeListener,
                wasMarkerVisible = wasMarkerVisible,
                setWasMarkerVisible = setWasMarkerVisible,
                lastMarkerEntryModels = lastMarkerEntryModels.value,
                onMarkerEntryModelsChange = lastMarkerEntryModels.component2(),
            )
        }

        val count = if (fadingEdges != null) chartDrawContext.saveLayer() else -1

        axisManager.drawBehindChart(chartDrawContext)
        chart.drawScrollableContent(chartDrawContext, model)

        fadingEdges?.apply {
            applyFadingEdges(chartDrawContext, chart.bounds)
            chartDrawContext.restoreCanvasToCount(count)
        }

        axisManager.drawAboveChart(chartDrawContext)
        chart.drawNonScrollableContent(chartDrawContext, model)
        legend?.draw(chartDrawContext)

        measureContext.reset()
    }
}

/**
 * Creates and remembers a [ChartScrollState] instance.
 */
@Composable
fun rememberChartScrollState(): ChartScrollState = remember { ChartScrollState() }

@Composable
internal fun rememberScrollListener(touchPoint: MutableState<Point?>): ScrollListener = remember {
    object : ScrollListener {
        override fun onValueChanged(oldValue: Float, newValue: Float) {
            touchPoint.value?.let { point ->
                touchPoint.value = point.copy(x = point.x + oldValue - newValue)
            }
        }

        override fun onScrollNotConsumed(delta: Float) {
            touchPoint.value?.let { point ->
                touchPoint.value = point.copy(x = point.x - delta)
            }
        }
    }
}

@Composable
internal fun rememberZoomState(
    zoom: MutableState<Float>,
    getScroll: () -> Float,
    scrollBy: (value: Float) -> Unit,
    chartBounds: RectF,
): OnZoom = remember {
    onZoom@{ centroid, zoomChange ->
        val newZoom = zoom.value * zoomChange
        if (newZoom !in DEF_MIN_ZOOM..DEF_MAX_ZOOM) return@onZoom
        val transformationAxisX = getScroll() + centroid.x - chartBounds.left
        val zoomedTransformationAxisX = transformationAxisX * zoomChange
        zoom.value = newZoom
        scrollBy(zoomedTransformationAxisX - transformationAxisX)
    }
}

internal fun Modifier.chartTouchEvent(
    setTouchPoint: ((Point?) -> Unit)?,
    isScrollEnabled: Boolean,
    scrollableState: ChartScrollState,
    onZoom: OnZoom?,
): Modifier =
    scrollable(
        state = scrollableState,
        orientation = Orientation.Horizontal,
        reverseDirection = true,
        enabled = isScrollEnabled,
    ).pointerInput(scrollableState, setTouchPoint, onZoom) {
        coroutineScope {
            if (setTouchPoint != null) {
                launch {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> setTouchPoint(event.changes.first().position.point)
                                PointerEventType.Release -> setTouchPoint(null)
                            }
                        }
                    }
                }
            }

            if (isScrollEnabled.not() && setTouchPoint != null) {
                launch {
                    detectHorizontalDragGestures(
                        onDragCancel = {
                            setTouchPoint(null)
                        },
                        onDragEnd = {
                            setTouchPoint(null)
                        },
                        onDragStart = { offset ->
                            setTouchPoint(offset.point)
                        },
                    ) { change, _ ->
                        setTouchPoint(change.position.point)
                    }
                }
            }

            if (onZoom != null && isScrollEnabled) {
                launch {
                    detectZoomGestures { centroid, zoom ->
                        setTouchPoint?.invoke(null)
                        onZoom(centroid, zoom)
                    }
                }
            }
        }
    }

private val Offset.point: Point
    get() = Point(x, y)

internal suspend fun PointerInputScope.detectZoomGestures(onGesture: (centroid: Offset, zoom: Float) -> Unit) {
    awaitEachGesture {
        var zoom = 1f
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        awaitFirstDown(requireUnconsumed = false)
        do {
            val event = awaitPointerEvent()
            val canceled = event.changes.any { it.isConsumed }
            if (!canceled) {
                val zoomChange = event.calculateZoom()
                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = abs(1 - zoom) * centroidSize
                    if (zoomMotion > touchSlop) pastTouchSlop = true
                }
                if (pastTouchSlop) {
                    val centroid = event.calculateCentroid(useCurrent = false)
                    if (zoomChange != 1f) onGesture(centroid, zoomChange)
                    event.changes.forEach { if (it.positionChanged()) it.consume() }
                }
            }
        } while (!canceled && event.changes.any { it.pressed })
    }
}
