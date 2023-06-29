package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hu.ma.charts.ChartShape
import hu.ma.charts.bars.HorizontalBarsChart
import hu.ma.charts.bars.data.HorizontalBarsData
import hu.ma.charts.bars.data.StackedBarData
import hu.ma.charts.bars.data.StackedBarEntry
import hu.ma.charts.legend.data.LegendEntry

private val SimpleColors = listOf(
    Color.Black,
    Color.Blue,
    Color.Yellow,
    Color.Red,
    Color.LightGray,
    Color.Magenta,
    Color.Cyan,
    Color.Green,
    Color.Gray,
)

private val Categories = listOf(
    "Teams",
    "Locations",
    "Devices",
    "People",
    "Laptops",
    "Titles",
    "Flowers",
    "Bugs",
    "Windows",
    "Screens",
    "Colors",
    "Bottles",
    "Cars",
    "Tricks",
)

private val BarsSampleData = listOf(
    "Bars" to HorizontalBarsData(
        bars = createBars(true),
    ),
    "Bars without colors for entries" to HorizontalBarsData(
        bars = createBars(false),
        colors = SimpleColors.reversed(),
    ),
    "Bars without popup" to HorizontalBarsData(
        bars = createBars(true),
        colors = SimpleColors.reversed(),
        isPopupEnabled = false,
    ),
    "Bars with custom legend" to HorizontalBarsData(
        bars = createBars(true),
        colors = SimpleColors.reversed(),
        customLegendEntries = (0..4).map {
            LegendEntry(
                text = AnnotatedString("Legend entry $it"),
                value = it.toFloat(),
                shape = ChartShape.Default.copy(color = SimpleColors.reversed()[it])
            )
        }
    ),
)

@Preview
@Composable
fun ComposeChartsSample(modifier: Modifier = Modifier) {
    ScreenContainer(modifier = modifier) {
        items(BarsSampleData) { (title, data) ->
            ChartContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .animateContentSize(),
                title = title
            ) {
                HorizontalBarsChart(
                    data = data,
                    legendOffset = 12.dp,
                    divider = {
                        Divider(color = Color.LightGray)
                    },
                    textContent = {
                        Text(it, style = MaterialTheme.typography.subtitle1)
                    },
                    valueContent = {
                        Text(it, style = MaterialTheme.typography.caption)
                    },
                )
            }
        }
    }
}

@Composable
private fun ScreenContainer(
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 24.dp,
        ),
    ) {
        content()
    }
}

@Composable
private fun ChartContainer(
    modifier: Modifier = Modifier,
    title: String,
    chartOffset: Dp = 12.dp,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.requiredSize(chartOffset))
        content()
    }
}

private fun createBars(withColor: Boolean) = listOf(
    listOf(12f, 2f, 3f, 2f),
    listOf(3f, 2f, 4f, 5f),
    listOf(1f, 4f, 12f, 5f),
    listOf(1f, 20f, 2f, 1f),
).mapIndexed { idx, values ->
    StackedBarData(
        title = AnnotatedString("Bars $idx"),
        entries = values.mapIndexed { index, value ->
            StackedBarEntry(
                text = AnnotatedString(Categories[index]),
                value = value,
                color = SimpleColors[index].takeIf { withColor }
            )
        }
    )
}
