package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NLayoutDirection
import nico.ed.nnn.zane.nframe.data.NLayoutItemAlign
import nico.ed.nnn.zane.nframe.data.NLayoutType
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NLayoutDisplay

@Preview
@Composable
fun NLayout() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.n_layout)
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            var typeSelected by remember { mutableStateOf(NLayoutType.FLOW) }
            var directionSelected by remember { mutableStateOf(NLayoutDirection.ROW) }

            Text(text = "設定項目", fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                /**
                 * Type の設定
                 */
                SelectionMenu(
                    options = NLayoutType.values().toList(),
                    selectedValue = typeSelected.toString(),
                    selectOption = {
                        typeSelected = it
                        // Slider の場合は必ず横方向になる
                        if (it == NLayoutType.SLIDER) directionSelected = NLayoutDirection.COLUMN
                    },
                    label = "Type",
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 12.dp)
                )

                /**
                 * Direction の設定
                 */
                SelectionMenu(
                    options = NLayoutDirection.values()
                        // Slider の場合は横方向のみが選択できるようにする
                        .filter { typeSelected == NLayoutType.FLOW || it == NLayoutDirection.COLUMN }
                        .toList(),
                    selectedValue = directionSelected.toString(),
                    selectOption = { directionSelected = it },
                    label = "Direction",
                    modifier = Modifier.width(180.dp)
                )
            }

            /**
             * アイテム数の設定
             */
            var itemCount by remember { mutableStateOf(1f) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "アイテム数: ")
                Text(text = itemCount.toInt().toString(), Modifier.width(20.dp))
                Slider(
                    value = itemCount,
                    onValueChange = { itemCount = it },
                    valueRange = 1f..20f
                )
            }

            /**
             * Line の設定
             */
            var line by remember { mutableStateOf(1f) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Line: ")
                Text(text = line.toInt().toString(), Modifier.width(20.dp))
                Slider(
                    value = line,
                    onValueChange = { line = it },
                    valueRange = 0f..5f,
                    steps = 4
                )
            }
            Text(text = "（Item Wrapが0であれば無効）", fontSize = 10.sp)

            /**
             * Item Wrap の設定
             */
            var itemWrap by remember { mutableStateOf(1f) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Item Wrap: ")
                Text(text = itemWrap.toInt().toString(), Modifier.width(20.dp))
                Slider(
                    value = itemWrap,
                    onValueChange = { itemWrap = it },
                    valueRange = 0f..5f,
                    steps = 4
                )
            }

            /**
             * Item Align の設定
             */
            var itemAlign by remember { mutableStateOf(NLayoutItemAlign.START) }
            SelectionMenu(
                options = NLayoutItemAlign.values().toList(),
                selectedValue = itemAlign.toString(),
                selectOption = { itemAlign = it },
                label = "Item Align"
            )

            /**
             * Edge Spacing の設定
             */
            var topEdgeSpacing by remember { mutableStateOf(0) }
            var leftEdgeSpacing by remember { mutableStateOf(0) }
            var rightEdgeSpacing by remember { mutableStateOf(0) }
            var bottomEdgeSpacing by remember { mutableStateOf(0) }
            Text(text = "Edge Spacing", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                options = listOf(0, 1, 2, 3, 4),
                selectedValue = (topEdgeSpacing / 8).toString(),
                selectOption = { topEdgeSpacing = it * 8 },
                modifier = Modifier.width(120.dp),
                label = "top"
            )
            Row(modifier = Modifier.padding(vertical = 12.dp)) {
                SelectionMenu(
                    options = listOf(0, 1, 2, 3, 4),
                    selectedValue = (leftEdgeSpacing / 8).toString(),
                    selectOption = { leftEdgeSpacing = it * 8 },
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(120.dp),
                    label = "left"
                )
                SelectionMenu(
                    options = listOf(0, 1, 2, 3, 4),
                    selectedValue = (rightEdgeSpacing / 8).toString(),
                    selectOption = { rightEdgeSpacing = it * 8 },
                    modifier = Modifier.width(120.dp),
                    label = "right"
                )
            }
            SelectionMenu(
                options = listOf(0, 1, 2, 3, 4),
                selectedValue = (bottomEdgeSpacing / 8).toString(),
                selectOption = { bottomEdgeSpacing = it * 8 },
                modifier = Modifier
                    .width(120.dp)
                    .padding(bottom = 12.dp),
                label = "bottom"
            )

            var lineSpacing by remember { mutableStateOf(0) }
            var itemSpacing by remember { mutableStateOf(0) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                /**
                 * Line Spacing の設定
                 */

                SelectionMenu(
                    options = listOf(0, 1, 2, 3, 4),
                    selectedValue = (lineSpacing / 4).toString(),
                    selectOption = { lineSpacing = it * 4 },
                    label = "Line Spacing",
                    modifier = Modifier
                        .width(180.dp)
                        .padding(end = 12.dp)
                )
                /**
                 * Item Spacing の設定
                 */
                SelectionMenu(
                    options = listOf(0, 1, 2, 3, 4),
                    selectedValue = (itemSpacing / 4).toString(),
                    selectOption = { itemSpacing = it * 4 },
                    label = "Item Spacing",
                    modifier = Modifier.width(180.dp)
                )
            }

            /**
             * 表示の確認
             */
            Text(
                text = "表示確認",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            NLayoutDisplay(
                type = typeSelected,
                direction = directionSelected,
                itemCount = itemCount.toInt(),
                line = line.toInt(),
                itemWrap = itemWrap.toInt(),
                itemAlign = itemAlign,
                topEdgeSpacing = 0,
                leftEdgeSpacing = 0,
                rightEdgeSpacing = 0,
                bottomEdgeSpacing = 0,
                lineSpacing = 0,
                itemSpacing = 0
            )
        }
    }
}
