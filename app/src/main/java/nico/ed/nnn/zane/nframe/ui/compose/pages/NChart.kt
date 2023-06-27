package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.organisms.VerticalBarGraphScreen

@Preview
@Composable
fun NChart() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.charts)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 270.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nbg_media),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(140.dp)
                    .fillMaxWidth(),
                contentDescription = null,
            )
            Row(
                modifier = Modifier.padding(top = 28.dp, bottom = 24.dp, start = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(40.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "山田　太郎",
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
            VerticalBarGraphScreen(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Preview
@Composable
private fun NChartSample(modifier: Modifier = Modifier) {
    NChart()
}
