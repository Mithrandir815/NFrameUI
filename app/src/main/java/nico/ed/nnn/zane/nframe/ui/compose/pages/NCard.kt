package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NCardMedia
import nico.ed.nnn.zane.nframe.data.PresentAbsent
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NFrameExoPlayer

@Preview
@Composable
fun NCard() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.n_card)
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
             * NCardMedia の設定
             */
            var nCardMediaSelected by remember { mutableStateOf(NCardMedia.IMAGE) }
            Text(text = "NCardMedia", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                options = NCardMedia.values().toList(),
                selectedValue = nCardMediaSelected.toString(),
                selectOption = {
                    nCardMediaSelected = it
                }
            )

            /**
             * アイコンの設定
             */
            var iconSelected by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - アイコン",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = iconSelected.toString(),
                selectOption = { iconSelected = it }
            )

            /**
             * タイトルの設定
             */
            var titleSelected by remember { mutableStateOf(PresentAbsent.PRESENT) }
            var subtitleSelected by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - タイトル",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = titleSelected.toString(),
                selectOption = {
                    titleSelected = it
                    // タイトルに「なし」を選択した場合は、サブタイトルも「なし」を選択する。
                    if (it == PresentAbsent.ABSENT) subtitleSelected = PresentAbsent.ABSENT
                }
            )

            /**
             * サブタイトルの設定
             */
            Text(
                text = "NCardHead - サブタイトル（タイトルなしの場合はなし）",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                // タイトルに「なし」を選択した場合は、サブタイトルの選択肢も「なし」のみにする。
                options = if (titleSelected == PresentAbsent.PRESENT) {
                    PresentAbsent.values().toList()
                } else {
                    listOf(PresentAbsent.ABSENT)
                },
                selectedValue = subtitleSelected.toString(),
                selectOption = {
                    subtitleSelected = it
                }
            )

            /**
             * Clickable の設定
             */
            var clickableSelected by remember { mutableStateOf(true) }
            Text(
                text = "NCardHead - Clickable",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = listOf(true, false),
                selectedValue = clickableSelected.toString(),
                selectOption = {
                    clickableSelected = it
                }
            )

            /**
             * メニューの設定
             */
            var menuSelected by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - メニュー",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = menuSelected.toString(),
                selectOption = { menuSelected = it }
            )

            /**
             * NCardFoot の設定
             */
            var nCardFootSelected by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardFoot",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = nCardFootSelected.toString(),
                selectOption = { nCardFootSelected = it }
            )

            Text(
                text = "表示確認",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Box(
                modifier = Modifier
                    .width(316.dp)
                    .height(160.dp)
            ) {
                when (nCardMediaSelected) {
                    NCardMedia.IMAGE -> {
                        Image(
                            painter = painterResource(id = R.drawable.sample),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null
                        )
                    }
                    NCardMedia.SOLID -> {
                        Spacer(
                            modifier = Modifier
                                .background(Color(0xff828282))
                                .fillMaxSize()
                        )
                    }
                    NCardMedia.MOVIE -> NFrameExoPlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                    NCardMedia.NONE -> {}
                }
            }
        }
    }
}
