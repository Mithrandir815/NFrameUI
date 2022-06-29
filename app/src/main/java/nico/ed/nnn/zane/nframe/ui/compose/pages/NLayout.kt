package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NLayoutDirection
import nico.ed.nnn.zane.nframe.data.NLayoutItemAlign
import nico.ed.nnn.zane.nframe.data.NLayoutType
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SliderMenu

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
            /**
             * Type の設定
             */
            var typeSelected by remember { mutableStateOf(NLayoutType.FLOW) }
            Text(text = "設定項目", fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
            Text(text = "Type", modifier = Modifier.padding(bottom = 12.dp))
            SelectionMenu(
                options = NLayoutType.values().toList(),
                selectedValue = typeSelected.toString(),
                selectOption = { typeSelected = it }
            )

            /**
             * Direction の設定
             */
            var directionSelected by remember { mutableStateOf(NLayoutDirection.ROW) }
            Text(text = "Direction", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                options = NLayoutDirection.values().toList(),
                selectedValue = directionSelected.toString(),
                selectOption = { directionSelected = it }
            )

            /**
             * Line の設定
             */
            var line by remember { mutableStateOf(0f) }
            Text(
                text = "Line（Item Wrapが0であれば無効）",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = line.toInt().toString(), Modifier.width(20.dp))
                SliderMenu(
                    value = line,
                    onValueChange = { line = it }
                )
            }

            /**
             * Item Wrap の設定
             */
            var itemWrap by remember { mutableStateOf(0f) }
            Text(
                text = "Item Wrap",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = itemWrap.toInt().toString(), Modifier.width(20.dp))
                SliderMenu(
                    value = itemWrap,
                    onValueChange = { itemWrap = it }
                )
            }

            /**
             * Item Align の設定
             */
            var itemAlign by remember { mutableStateOf(NLayoutItemAlign.START) }
            Text(text = "Item Align", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                options = NLayoutItemAlign.values().toList(),
                selectedValue = itemAlign.toString(),
                selectOption = { itemAlign = it }
            )

            /**
             * Edge Spacing の設定
             */
            var topEdgeSpacing by remember { mutableStateOf(0) }
            var leftEdgeSpacing by remember { mutableStateOf(0) }
            var rightEdgeSpacing by remember { mutableStateOf(0) }
            var bottomEdgeSpacing by remember { mutableStateOf(0) }
            Text(text = "Edge Spacing", modifier = Modifier.padding(vertical = 12.dp))
            TextField(
                value = topEdgeSpacing.toString(),
                onValueChange = { topEdgeSpacing = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "top") },
                modifier = Modifier.width(100.dp)
            )
            Row(modifier = Modifier.padding(vertical = 12.dp)) {
                TextField(
                    value = leftEdgeSpacing.toString(),
                    onValueChange = { leftEdgeSpacing = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "left") },
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(100.dp)
                )
                TextField(
                    value = rightEdgeSpacing.toString(),
                    onValueChange = { rightEdgeSpacing = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "right") },
                    modifier = Modifier.width(100.dp)
                )
            }
            TextField(
                value = bottomEdgeSpacing.toString(),
                onValueChange = { bottomEdgeSpacing = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "bottom") },
                modifier = Modifier.width(100.dp)
            )

            /**
             * Line Spacing の設定
             */
            var lineSpacing by remember { mutableStateOf(0f) }
            Text(
                text = "Line Spacing",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = lineSpacing.toInt().toString(), Modifier.width(20.dp))
                Slider(
                    value = lineSpacing,
                    onValueChange = { lineSpacing = it },
                    valueRange = 0f..32f,
                    steps = 7
                )
            }

            /**
             * Item Spacing の設定
             */
            var itemSpacing by remember { mutableStateOf(0f) }
            Text(
                text = "Item Spacing",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = itemSpacing.toInt().toString(), Modifier.width(20.dp))
                Slider(
                    value = itemSpacing,
                    onValueChange = { itemSpacing = it },
                    valueRange = 0f..32f,
                    steps = 7
                )
            }
        }
    }
}
