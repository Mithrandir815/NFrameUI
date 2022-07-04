package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nico.ed.nnn.zane.nframe.ui.theme.Blue500

@Composable
private fun NCardFootItem(text: String) {
    Box(
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp, end = 12.dp)
            .background(Color(0xffdcdcdc))
            .fillMaxWidth()
            .height(96.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = W500
        )
    }
}

@Composable
private fun NCardFootAction(
    modifier: Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color
) {
    TextButton(
        onClick = {},
        modifier = modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = W700
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NCardFoot() {
    Column(Modifier.fillMaxWidth()) {
        NCardFootItem("Item 1")
        NCardFootItem("Item 2")
        NCardFootAction(
            modifier = Modifier.padding(top = 24.dp),
            text = "Action 1",
            backgroundColor = Blue500,
            contentColor = Color.White
        )
        NCardFootAction(
            modifier = Modifier.padding(top = 8.dp),
            text = "Action 2",
            backgroundColor = Color.White,
            contentColor = Blue500
        )
    }
}
