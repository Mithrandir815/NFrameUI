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
import kotlin.math.ceil
import kotlin.math.min

@Composable
fun NLayoutDisplay(
    type: NLayoutType,
    direction: NLayoutDirection,
    itemCount: Int,
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
    val maxLinesByItem =
        if (itemWrap == 0) 1
        else ceil(itemCount.toDouble() / itemWrap.toDouble()).toInt()
    val horizontalArrangement: Arrangement.Horizontal = when (itemAlign) {
        NLayoutItemAlign.CENTER -> Arrangement.Center
        NLayoutItemAlign.END -> Arrangement.End
        else -> Arrangement.Start
    }
    val verticalArrangement: Arrangement.Vertical = when (itemAlign) {
        NLayoutItemAlign.CENTER -> Arrangement.Center
        NLayoutItemAlign.END -> Arrangement.Bottom
        else -> Arrangement.Top
    }
    val isStretch = itemAlign == NLayoutItemAlign.STRETCH

    when (type) {
        NLayoutType.FLOW -> {
            when (direction) {
                NLayoutDirection.ROW -> {
                    // itemWrap が 0 のときは改行せずそのまま並べる
                    if (itemWrap == 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = topEdgeSpacing.dp,
                                    start = rightEdgeSpacing.dp,
                                    bottom = bottomEdgeSpacing.dp,
                                    end = leftEdgeSpacing.dp
                                ),
                            horizontalArrangement = horizontalArrangement
                        ) {
                            repeat(itemCount) {
                                DisplayBox(
                                    index = it + 1,
                                    modifier = if (isStretch) Modifier.weight(1f) else Modifier
                                )
                            }
                        }
                    }
                    // ItemWrap が 1 以上のときは改行する
                    else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            val lineNum =
                                // Line が 0 のときは行数制限がない
                                if (line == 0) maxLinesByItem
                                else min(maxLinesByItem, line)
                            repeat(lineNum) { lineIndex ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = topEdgeSpacing.dp,
                                            start = rightEdgeSpacing.dp,
                                            bottom = bottomEdgeSpacing.dp,
                                            end = leftEdgeSpacing.dp
                                        ),
                                    horizontalArrangement = horizontalArrangement
                                ) {
                                    repeat(min(itemCount - itemWrap * lineIndex, itemWrap)) {
                                        DisplayBox(
                                            index = itemWrap * lineIndex + it + 1,
                                            modifier = if (isStretch) Modifier.weight(1f) else Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                NLayoutDirection.COLUMN -> {
                    // itemWrap が 0 のときは改行せずそのまま並べる
                    if (itemWrap == 0) {
                        Column(
                            verticalArrangement = verticalArrangement,
                            modifier = Modifier.padding(
                                top = topEdgeSpacing.dp,
                                start = rightEdgeSpacing.dp,
                                bottom = bottomEdgeSpacing.dp,
                                end = leftEdgeSpacing.dp
                            )
                        ) {
                            repeat(itemCount) {
                                DisplayBox(index = it + 1)
                            }
                        }
                    }
                    // ItemWrap が 1 以上のときは改行する
                    else {
                        Row(
                            modifier = Modifier
                                .height(IntrinsicSize.Max)
                                .padding(
                                    top = topEdgeSpacing.dp,
                                    start = rightEdgeSpacing.dp,
                                    bottom = bottomEdgeSpacing.dp,
                                    end = leftEdgeSpacing.dp
                                )
                        ) {
                            val lineNum =
                                // Line が 0 のときは行数制限がない
                                if (line == 0) maxLinesByItem
                                else min(maxLinesByItem, line)
                            repeat(lineNum) { lineIndex ->
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = verticalArrangement
                                ) {
                                    repeat(min(itemCount - itemWrap * lineIndex, itemWrap)) {
                                        DisplayBox(
                                            index = itemWrap * lineIndex + it + 1,
                                            modifier = if (isStretch) Modifier.weight(1f) else Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        NLayoutType.SLIDER -> {
            // itemWrap が 0 のときは改行せずそのまま並べる
            if (itemWrap == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(
                            top = topEdgeSpacing.dp,
                            start = rightEdgeSpacing.dp,
                            bottom = bottomEdgeSpacing.dp,
                            end = leftEdgeSpacing.dp
                        )
                ) {
                    repeat(itemCount) {
                        DisplayBox(index = it + 1)
                    }
                }
            }
            // ItemWrap が 1 以上のときは改行する
            else {
                Column {
                    val lineNum =
                        // Line が 0 のときは行数制限がない
                        if (line == 0) maxLinesByItem
                        else min(maxLinesByItem, line)

                    repeat(lineNum) { lineIndex ->
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(
                                    top = topEdgeSpacing.dp,
                                    start = rightEdgeSpacing.dp,
                                    bottom = bottomEdgeSpacing.dp,
                                    end = leftEdgeSpacing.dp
                                )
                        ) {
                            repeat(min(itemCount - itemWrap * lineIndex, itemWrap)) {
                                DisplayBox(index = itemWrap * lineIndex + it + 1)
                            }
                        }
                    }
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
