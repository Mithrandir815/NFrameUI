package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nico.ed.nnn.zane.nframe.data.NLayoutDirection
import nico.ed.nnn.zane.nframe.data.NLayoutItemAlign
import nico.ed.nnn.zane.nframe.data.NLayoutType

@Composable
fun NLayoutDisplay(
    type: NLayoutType,
    direction: NLayoutDirection,
    line: Int,
    itemWrap: Int,
    itemAlign: NLayoutItemAlign,
    topEdgeSpacing: Int,
    leftEdgeSpacing: Int,
    rightEdgeSpacing: Int,
    bottomEdgeSpacing: Int,
    lineSpacing: Int,
    itemSpacing: Int
) {
    when (type) {
        NLayoutType.FLOW -> {
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(20) {
                    DisplayBox(index = it + 1)
                }
            }
        }
        NLayoutType.SLIDER -> {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                repeat(20) {
                    DisplayBox(index = it + 1)
                }
            }
        }
    }
}

@Composable
private fun DisplayBox(index: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(Color.Gray)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = "Item $index",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .width(60.dp)
        )
    }
}
