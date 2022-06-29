package nico.ed.nnn.zane.nframe.ui.compose.atoms

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SelectionMenu(
    options: List<T>,
    selectedValue: String,
    selectOption: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
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
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = modifier,
            label = if (label != null) {
                { Text(text = label) }
            } else null
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectOption(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption.toString())
                }
            }
        }
    }
}
