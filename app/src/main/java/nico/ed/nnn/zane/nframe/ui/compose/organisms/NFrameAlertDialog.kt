package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NFrameAlertDialog(
    text: String,
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                isVisible = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        isVisible = false
                    }
                ) {
                    Text(stringResource(android.R.string.ok))
                }
            },
            modifier = Modifier.shadow(24.dp),
            text = {
                Text(text)
            }
        )
    }
}

@Preview
@Composable
private fun PreviewNFrameAlertDialog() {
    NFrameAlertDialog("テキスト") {}
}
