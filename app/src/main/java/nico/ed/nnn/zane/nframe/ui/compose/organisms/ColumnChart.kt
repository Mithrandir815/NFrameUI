package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.marker.Marker
import nico.ed.nnn.zane.nframe.ui.theme.Blue300

@Composable
fun Bar(height: Dp, color: Color) {
    Box(
        Modifier
            .width(10.dp)
            .height(height)
            .background(color, shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
    )
}

@Composable
fun VerticalBarGraph(dataList: List<Int>, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.width(36.dp))
        val length = dataList.size
        dataList.forEachIndexed { index, data ->
            val barColor =
                if (index == length - 1) Color.Blue.copy(alpha = 0.9f) else Blue300.copy(alpha = 0.9f)
            Column(
                modifier = Modifier
                    .background(color = if (index == length - 1) Color.Blue.copy(alpha = 0.2f) else Color.Transparent)
                    .padding(start = 5.dp, end = 5.dp, top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Bar(height = data.dp, color = barColor)
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = (index + 1).toString(),
                    fontSize = 12.sp
                )
                if (index == 1) {
                    Text(
                        modifier = Modifier,
                        text = "6月",
                        fontSize = 10.sp
                    )
                } else {
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

private val model1 = entryModelOf(
    1 to 2,
    2 to 4,
    3 to 1,
    4 to 4,
    5 to 4,
    6 to 4,
    7 to 4,
    8 to 4,
    9 to 4,
    10 to 4,
    11 to 4,
    12 to 4,
    13 to 4,
    14 to 4
)

private val markerMap: Map<Float, Marker>
    @Composable get() = mapOf(4f to rememberMarker())

@Composable
fun VerticalBarGraphScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(172.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "学習数 累計", fontSize = 10.sp)
                Text(text = "999,999", fontSize = 32.sp)
            }
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "1日平均", fontSize = 10.sp, color = Color.Blue)
                Text(text = "999,999", fontSize = 24.sp, color = Color.Blue)
            }
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 16.dp)
                    .background(
                        color = Color.Blue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "今日", fontSize = 10.sp, color = Color.Blue)
                Text(text = "999,999", fontSize = 24.sp, color = Color.Blue)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val sample = listOf(
                Pair("", 0),
                Pair("12/25", 1),
                Pair("2", 1),
                Pair("3", 1),
                Pair("4", 2),
                Pair("5", 3),
                Pair("6", 7),
                Pair("7", 10),
                Pair("8", 7),
                Pair("9", 12),
                Pair("10", 20),
                Pair("11", 2),
                Pair("12", 2),
                Pair("13", 2),
                Pair("14", 2),
            )
            VicoColumnChart(
                chartValues = sample
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNFrameGraph() {
    VerticalBarGraphScreen(

    )
}