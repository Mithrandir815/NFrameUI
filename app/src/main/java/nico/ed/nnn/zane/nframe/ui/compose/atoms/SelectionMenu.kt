package nico.ed.nnn.zane.nframe.ui.compose.atoms

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectionMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<Any>,
    selectedValue: String,
    selectOption: (Any) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        TextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectOption(selectionOption)
                        onExpandedChange(false)
                    }
                ) {
                    Text(text = selectionOption.toString())
                }
            }
        }
    }
}
