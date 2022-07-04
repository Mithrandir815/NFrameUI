package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NCardMedia
import nico.ed.nnn.zane.nframe.data.PresentAbsent
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.atoms.SelectionMenu
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NCardBottomSheet
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NCardPreview

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun NCard() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            NCardBottomSheet()
        },
        scaffoldState = bottomSheetScaffoldState,
        topBar = {
            NFrameTopAppBar(R.string.n_card)
        },
        sheetPeekHeight = 0.dp // 閉じたボトムシートは完全に隠す。
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
            var nCardMedia by remember { mutableStateOf(NCardMedia.IMAGE) }
            Text(text = "NCardMedia", modifier = Modifier.padding(vertical = 12.dp))
            SelectionMenu(
                options = NCardMedia.values().toList(),
                selectedValue = nCardMedia.toString(),
                selectOption = {
                    nCardMedia = it
                }
            )

            /**
             * アイコンの設定
             */
            var icon by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - アイコン",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = icon.toString(),
                selectOption = { icon = it }
            )

            /**
             * タイトルの設定
             */
            var title by remember { mutableStateOf(PresentAbsent.PRESENT) }
            var subtitle by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - タイトル",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = title.toString(),
                selectOption = {
                    title = it
                    // タイトルに「なし」を選択した場合は、サブタイトルも「なし」を選択する。
                    if (it == PresentAbsent.ABSENT) subtitle = PresentAbsent.ABSENT
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
                options = if (title == PresentAbsent.PRESENT) {
                    PresentAbsent.values().toList()
                } else {
                    listOf(PresentAbsent.ABSENT)
                },
                selectedValue = subtitle.toString(),
                selectOption = {
                    subtitle = it
                }
            )

            /**
             * Clickable の設定
             */
            var clickable by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - Clickable",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = clickable.toString(),
                selectOption = {
                    clickable = it
                }
            )

            /**
             * メニューの設定
             */
            var menu by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardHead - メニュー",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = menu.toString(),
                selectOption = { menu = it }
            )

            /**
             * NCardFoot の設定
             */
            var nCardFoot by remember { mutableStateOf(PresentAbsent.PRESENT) }
            Text(
                text = "NCardFoot",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            SelectionMenu(
                options = PresentAbsent.values().toList(),
                selectedValue = nCardFoot.toString(),
                selectOption = { nCardFoot = it }
            )

            Text(
                text = "表示確認",
                modifier = Modifier.padding(vertical = 12.dp),
                fontSize = 18.sp
            )

            NCardPreview(
                nCardMedia = nCardMedia,
                hasIcon = icon == PresentAbsent.PRESENT,
                hasTitle = title == PresentAbsent.PRESENT,
                hasSubtitle = subtitle == PresentAbsent.PRESENT,
                isClickable = clickable == PresentAbsent.PRESENT,
                hasMenu = menu == PresentAbsent.PRESENT,
                hasNCardFoot = nCardFoot == PresentAbsent.PRESENT,
                onMoreVertClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            )
        }
    }
}
