package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.ui.theme.Blue300

@Composable
fun Bar(height: Dp, color: Color) {
    Box(
        Modifier
            .width(10.dp)
            .height(height)
            .background(color, shape = RoundedCornerShape(3.dp))
            .padding(
                bottom = 20.dp, top = 100
                    .dp
            )
    )
}

@Composable
fun VerticalBarGraph(dataList: List<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.width(36.dp))
        val length = dataList.size
        dataList.forEachIndexed { index, data ->
            val barColor =
                if (index == length - 1) Color.Blue.copy(alpha = 0.1f) else Blue300.copy(alpha = 0.1f)
            Column(
                modifier = Modifier
                    .background(color = if (index == length - 1) Color.Blue.copy(alpha = 0.2f) else Color.Transparent)
                    .padding(horizontal = 5.dp),
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

@Composable
fun VerticalBarGraphScreen() {
    val dataList = listOf(50, 20, 20, 10, 50, 0, 20, 10, 30, 20, 5, 9, 4, 40)
    // 点の横幅っぽい
    val onInterval = 1f
    // 点の感覚っぽい
    val offInterval = 5f
    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "学習数 累計", fontSize = 10.sp)
                Text(text = "12,112", fontSize = 24.sp)
            }
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "1日平均", fontSize = 10.sp, color = Color.Blue)
                Text(text = "10.2", fontSize = 24.sp, color = Color.Blue)
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
                Text(text = "12", fontSize = 24.sp, color = Color.Blue)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Row(
                modifier = Modifier.padding(start = 10.dp, end = 40.dp, bottom = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "平均",
                    fontSize = 8.sp,
                    color = Color.Blue
                )
                Canvas(modifier = Modifier.width(300.dp)) {
                    drawRoundRect(
                        color = Color.Blue,
                        cornerRadius = CornerRadius(1f),
                        style = Stroke(
                            width = 1f,
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(onInterval, offInterval),
                                phase = onInterval + offInterval,
                            )
                        )
                    )
                }
            }
            VerticalBarGraph(dataList)
        }
    }
}

@Preview
@Composable
private fun PreviewNFrameGraph() {
    VerticalBarGraphScreen()
}
