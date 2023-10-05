package nico.ed.nnn.zane.nframe.ui.compose.vico

import android.text.Layout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.shape.cornered.RoundedCornerTreatment
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.marker.Marker
import nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper.CustomMarkerComponent
import nico.ed.nnn.zane.nframe.ui.theme.Blue800

@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = Blue800.copy(alpha = 0.6f)
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(
            labelBackgroundShape,
            labelBackgroundColor.toArgb()
        )
    }
    val label = textComponent(
        color = Color.White,
        background = labelBackground,
        padding = labelPadding,
        lineCount = 2,
    )
    val guideline = lineComponent(
        Blue800.copy(alpha = 0.6f),
        guidelineThickness,
    )
    return remember(label, null, guideline) {
        object : CustomMarkerComponent(label, null, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                //書式に関して定義しますlabelよりも優先されます
                labelFormatter = DefaultMarkerLabelFormatter
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = with(context) {
                outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels
            }
        }
    }
}

private const val INDICATOR_SIZE_DP = 36f

private val labelBackgroundShape = MarkerCorneredShape(
    Corner.Relative(
        20,
        RoundedCornerTreatment
    )
)
private val guidelineThickness = 1.dp
private val labelALLPaddingValue = 8.dp
private val labelPadding = dimensionsOf(all = labelALLPaddingValue)
