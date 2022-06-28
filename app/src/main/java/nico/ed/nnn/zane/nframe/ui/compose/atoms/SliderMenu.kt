package nico.ed.nnn.zane.nframe.ui.compose.atoms

import androidx.compose.material.Slider
import androidx.compose.runtime.Composable

@Composable
fun SliderMenu(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = 0f..20f
    )
}
