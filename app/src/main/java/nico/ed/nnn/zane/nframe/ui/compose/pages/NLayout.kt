package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NLayoutDirection
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
        ) {
            /**
             * Type の設定
             */
            var typeMenuExpanded by remember { mutableStateOf(false) }
            var typeSelected by remember { mutableStateOf(NLayoutType.FLOW) }
            Text(text = "設定項目", fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
            Text(text = "Type", modifier = Modifier.padding(bottom = 12.dp))
            SelectionMenu(
                expanded = typeMenuExpanded,
                onExpandedChange = { typeMenuExpanded = it },
                options = NLayoutType.values().toList(),
                selectedValue = typeSelected.toString(),
                selectOption = { typeSelected = it as NLayoutType }
            )

            /**
             * Direction の設定
             */
            var directionMenuExpanded by remember { mutableStateOf(false) }
            var directionSelected by remember { mutableStateOf(NLayoutDirection.ROW) }
            Text(text = "Direction", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                expanded = directionMenuExpanded,
                onExpandedChange = { directionMenuExpanded = it },
                options = NLayoutDirection.values().toList(),
                selectedValue = directionSelected.toString(),
                selectOption = { directionSelected = it as NLayoutDirection }
            )

            /**
             * Line の設定
             */
            var lineSpacing by remember { mutableStateOf(0f) }
            Text(
                text = "Line（Item Wrapが0であれば無効）",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = lineSpacing.toInt().toString(), Modifier.width(20.dp))
                SliderMenu(
                    value = lineSpacing,
                    onValueChange = { lineSpacing = it }
                )
            }
        }
    }
}
