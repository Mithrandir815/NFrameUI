package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NBGMedia
import nico.ed.nnn.zane.nframe.data.NFrameHeader
import nico.ed.nnn.zane.nframe.data.NFrameHeaderCard
import nico.ed.nnn.zane.nframe.data.NFrameHeaderFixed
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu

@Preview
@Composable
fun NFrame() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.n_frame_settings)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "設定項目", fontSize = 18.sp)

            /**
             * NFrameHeader の設定
             */
            var nFrameHeaderSelected by remember { mutableStateOf(NFrameHeader.PRESENT) }
            Text(
                text = "NFrameHeader",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = NFrameHeader.values().toList(),
                selectedValue = nFrameHeaderSelected.toString(),
                selectOption = {
                    nFrameHeaderSelected = it
                }
            )

            /**
             * NBGMedia の設定
             */
            var nBGMediaSelected by remember { mutableStateOf(NBGMedia.IMAGE) }
            Text(
                text = "NFrameHeader - 背景（NBGMedia）",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = NBGMedia.values().toList(),
                selectedValue = nBGMediaSelected.toString(),
                selectOption = {
                    nBGMediaSelected = it
                }
            )

            /**
             * NFrameHeader - カードの設定
             */
            var nFrameHeaderCardSelected by remember { mutableStateOf(NFrameHeaderCard.PRESENT) }
            Text(
                text = "NFrameHeader - カード",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = NFrameHeaderCard.values().toList(),
                selectedValue = nFrameHeaderCardSelected.toString(),
                selectOption = {
                    nFrameHeaderCardSelected = it
                }
            )

            /**
             * NFrameHeader - 固定の設定
             */
            var nFrameHeaderFixedSelected by remember { mutableStateOf(NFrameHeaderFixed.FIXED) }
            Text(
                text = "NFrameHeader - 固定",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = NFrameHeaderFixed.values().toList(),
                selectedValue = nFrameHeaderFixedSelected.toString(),
                selectOption = {
                    nFrameHeaderFixedSelected = it
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.primary_500),
                            shape = RoundedCornerShape(5)
                        ),
                    onClick = {}
                ) {
                    Text(text = "表示")
                }
            }
        }
    }
}
