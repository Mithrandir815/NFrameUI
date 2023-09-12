package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.ui.compose.organisms.VerticalBarGraphScreen
import nico.ed.nnn.zane.nframe.ui.theme.Gray200

@Preview(showSystemUi = true)
@Composable
fun NChart() {
    Scaffold(modifier = Modifier) { padding ->
        Column(
            modifier = Modifier
                .background(color = Gray200)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                //ここで大きい画像だと画像の高さになってしまい高さがおかしくなります。
                Image(
                    painter = painterResource(id = R.drawable.nbgmedia),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentDescription = null
                )
                Row(
                    modifier = Modifier
                        .padding(top = 28.dp, bottom = 24.dp, start = 16.dp)
                        .height(IntrinsicSize.Max)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(40.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            modifier = Modifier.padding(end = 16.dp),
                            text = "山田　太郎太郎太郎太郎太郎太郎太郎太郎太郎太郎太郎太郎",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = "N予備校ID 594856",
                            color = Color.White
                        )
                    }
                }
            }
            // TODO - オフセットですとコンテンツ全体が上に上がってしまい。無駄な余白ができてしまう
            VerticalBarGraphScreen(modifier = Modifier.offset(y = (-8).dp))
            Column(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(color = Color.White)
            ) {
                repeat(20) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = Color.Black)
                        )
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "テスト",
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NChartSample(modifier: Modifier = Modifier) {
    NChart()
}
