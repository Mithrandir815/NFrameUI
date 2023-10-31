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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.ui.theme.Gray500

@Composable
fun VerticalBarGraphScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(188.dp)
            .background(color = Color.White, shape = RoundedCornerShape(5))
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                text = "学習数",
                fontSize = 14.sp,
                color = Gray500
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(144.dp)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "学習数 累計", fontSize = 10.sp, color = Gray500)
                    Text(text = "999,999", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(
                    modifier = Modifier
                        .height(52.dp)
                        .width(1.dp)
                        .background(color = Color.Black.copy(alpha = 0.1f))
                )
                Column(
                    modifier = Modifier
                        .width(95.dp)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "今日", fontSize = 10.sp, color = Gray500)
                    Text(text = "99", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(
                    modifier = Modifier
                        .height(52.dp)
                        .width(1.dp)
                        .background(color = Color.Black.copy(alpha = 0.1f))
                )
                Column(
                    modifier = Modifier
                        .width(95.dp)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "平均(2週間)", fontSize = 10.sp, color = Gray500)
                    Text(text = "99", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
        //被せることによってグラフのバルーンが数値の上に来るようにしている
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val sample = listOf(
                //平均の文字をグラフないに収めるために最初のデータを無くす
                Pair("", 0),
                Pair("25", 0),
                Pair("2", 0),
                Pair("3", 0),
                Pair("4", 0),
                Pair("5", 0),
                Pair("6", 0),
                Pair("7", 0),
                Pair("8", 0),
                Pair("9", 1),
                Pair("10", 0),
                Pair("11", 0),
                Pair("12", 0),
                Pair("13", 0),
                Pair("14", 0),
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
