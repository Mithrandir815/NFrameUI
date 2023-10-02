package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import com.patrykandpatrick.vico.core.chart.forEachInRelativelyIndexed
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.extension.half
import kotlin.math.abs

class CustomCoreColumnChart : ColumnChart() {

    override fun ChartDrawContext.drawChartInternal(
        chartValues: ChartValues,
        model: ChartEntryModel,
    ) {
        val yRange = (chartValues.maxY - chartValues.minY).takeIf { it != 0f } ?: return
        val heightMultiplier = bounds.height() / yRange

        var drawingStart: Float
        var height: Float
        var columnCenterX: Float
        var column: LineComponent
        var columnTop: Float
        var columnBottom: Float
        val zeroLinePosition = bounds.bottom + chartValues.minY / yRange * bounds.height()

        model.entries.forEachIndexed { index, entryCollection ->

            column = columns.getRepeating(index)
            drawingStart = getDrawingStart(index, model.entries.size) - horizontalScroll

            entryCollection.forEachInRelativelyIndexed(chartValues.minX..chartValues.maxX) { entryIndex, entry ->
                // 最後の棒の描画に使う LineComponent は恣意的に columns の最後のものに置き換える
                column = if (entryIndex == entryCollection.lastIndex) columns.last() else column
                height = abs(entry.y) * heightMultiplier
                val xSpacingMultiplier = (entry.x - chartValues.minX) / chartValues.xStep
                check(xSpacingMultiplier % 1f == 0f) { "Each entry’s x value must be a multiple of the x step." }
                columnCenterX = drawingStart +
                        (horizontalDimensions.xSpacing * xSpacingMultiplier + column.thicknessDp.half.pixels * chartScale) *
                        layoutDirectionMultiplier

                when (mergeMode) {
                    MergeMode.Stack -> {
                        val (stackedNegY, stackedPosY) = heightMap.getOrElse(entry.x) { 0f to 0f }
                        columnBottom = zeroLinePosition +
                                if (entry.y < 0f) {
                                    height + abs(stackedNegY) * heightMultiplier
                                } else {
                                    -stackedPosY * heightMultiplier
                                }

                        columnTop = (columnBottom - height).coerceAtMost(columnBottom)
                        heightMap[entry.x] =
                            if (entry.y < 0f) {
                                stackedNegY + entry.y to stackedPosY
                            } else {
                                stackedNegY to stackedPosY + entry.y
                            }
                    }

                    MergeMode.Grouped -> {
                        columnBottom = zeroLinePosition + if (entry.y < 0f) height else 0f
                        columnTop = columnBottom - height
                    }
                }

                val columnSignificantY = if (entry.y < 0f) columnBottom else columnTop

                if (column.intersectsVertical(
                        context = this,
                        top = columnTop,
                        bottom = columnBottom,
                        centerX = columnCenterX,
                        boundingBox = bounds,
                        thicknessScale = chartScale,
                    )
                ) {
                    updateMarkerLocationMap(
                        entry,
                        columnSignificantY,
                        columnCenterX,
                        column,
                        entryIndex
                    )
                    column.drawVertical(this, columnTop, columnBottom, columnCenterX, chartScale)
                }

                if (mergeMode == MergeMode.Grouped) {
                    drawDataLabel(
                        modelEntriesSize = model.entries.size,
                        columnThicknessDp = column.thicknessDp,
                        dataLabelValue = entry.y,
                        x = columnCenterX,
                        y = columnSignificantY,
                        isFirst = index == 0 && entry.x == chartValues.minX,
                        isLast = index == model.entries.lastIndex && entry.x == chartValues.maxX,
                    )
                } else if (index == model.entries.lastIndex) {
                    val yValues = heightMap[entry.x]
                    drawStackedDataLabel(
                        modelEntriesSize = model.entries.size,
                        columnThicknessDp = column.thicknessDp,
                        negativeY = yValues?.first,
                        positiveY = yValues?.second,
                        x = columnCenterX,
                        zeroLinePosition = zeroLinePosition,
                        heightMultiplier = heightMultiplier,
                        isFirst = entry.x == chartValues.minX,
                        isLast = entry.x == chartValues.maxX,
                    )
                }
            }
        }
    }
}

internal fun <T> List<T>.getRepeating(index: Int): T {
    if (isEmpty()) throw IllegalStateException("Cannot get repeated item from empty collection.")
    return get(index % size.coerceAtLeast(1))
}
