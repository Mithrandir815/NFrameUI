package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NLayoutType
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu

@Preview
@Composable
fun NLayout() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.n_layout)
        }
    ) { padding ->
        /**
         * Type の設定
         */
        var typeMenuExpanded by remember { mutableStateOf(false) }
        var typeSelected by remember { mutableStateOf(NLayoutType.FLOW) }
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "設定項目", fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
            Text(text = "Type", modifier = Modifier.padding(bottom = 12.dp))
            SelectionMenu(
                expanded = typeMenuExpanded,
                onExpandedChange = { typeMenuExpanded = it },
                options = NLayoutType.values().toList(),
                selectedValue = typeSelected.toString(),
                selectOption = { typeSelected = it }
            )
        }
    }
}
