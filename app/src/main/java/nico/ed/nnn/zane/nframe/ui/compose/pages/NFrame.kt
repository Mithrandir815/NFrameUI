package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NFrameDisplay

@Preview
@Composable
fun NFrame() {
    var isDisplay by remember { mutableStateOf(false) }
    var nFrameHeaderSelected by remember { mutableStateOf(NFrameHeader.PRESENT) }
    var nBGMediaSelected by remember { mutableStateOf(NBGMedia.IMAGE) }
    var nFrameHeaderCardSelected by remember { mutableStateOf(NFrameHeaderCard.PRESENT) }
    var nFrameHeaderFixedSelected by remember { mutableStateOf(NFrameHeaderFixed.FIXED) }
    var positionMs by remember { mutableStateOf(0L) }

    Scaffold(
        topBar = {
            if (isDisplay) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.n_frame_display),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    },
                    backgroundColor = colorResource(R.color.primary_500),
                    navigationIcon = {
                        IconButton(onClick = { isDisplay = false }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            } else {
                NFrameTopAppBar(R.string.n_frame_settings)
            }
        }
    ) { padding ->
        if (isDisplay) {
            NFrameDisplay(
                nFrameHeader = nFrameHeaderSelected,
                nBGMedia = nBGMediaSelected,
                nFrameHeaderCard = nFrameHeaderCardSelected,
                nFrameHeaderFixed = nFrameHeaderFixedSelected,
                positionMs = positionMs,
                onPositionChange = { positionMs = it },
                content = List(10) { "test$it" }
            )
        } else {
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
                        onClick = { isDisplay = true }
                    ) {
                        Text(text = "表示")
                    }
                }
            }
        }
    }
}
