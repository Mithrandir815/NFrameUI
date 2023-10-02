package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.formatter.ValueFormatter
import com.patrykandpatrick.vico.core.marker.Marker

@Composable
fun customColumnChart(
    columns: List<LineComponent> = currentChartStyle.columnChart.columns,
    spacing: Dp = currentChartStyle.columnChart.outsideSpacing,
    innerSpacing: Dp = currentChartStyle.columnChart.innerSpacing,
    mergeMode: ColumnChart.MergeMode = currentChartStyle.columnChart.mergeMode,
    decorations: List<Decoration>? = null,
    persistentMarkers: Map<Float, Marker>? = null,
    targetVerticalAxisPosition: AxisPosition.Vertical? = null,
    dataLabel: TextComponent? = currentChartStyle.columnChart.dataLabel,
    dataLabelVerticalPosition: VerticalPosition = currentChartStyle.columnChart.dataLabelVerticalPosition,
    dataLabelValueFormatter: ValueFormatter = currentChartStyle.columnChart.dataLabelValueFormatter,
    dataLabelRotationDegrees: Float = currentChartStyle.columnChart.dataLabelRotationDegrees,
    axisValuesOverrider: AxisValuesOverrider<ChartEntryModel>? = null,
): CustomCoreColumnChart = remember { CustomCoreColumnChart() }.apply {
    this.columns = columns
    this.spacingDp = spacing.value
    this.innerSpacingDp = innerSpacing.value
    this.mergeMode = mergeMode
    this.dataLabel = dataLabel
    this.dataLabelVerticalPosition = dataLabelVerticalPosition
    this.dataLabelValueFormatter = dataLabelValueFormatter
    this.dataLabelRotationDegrees = dataLabelRotationDegrees
    this.axisValuesOverrider = axisValuesOverrider
    this.targetVerticalAxisPosition = targetVerticalAxisPosition
    decorations?.also(::setDecorations)
    persistentMarkers?.also(::setPersistentMarkers)
}

