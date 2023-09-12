package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.shape.cornered.RoundedCornerTreatment
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.marker.Marker
import nico.ed.nnn.zane.nframe.ui.theme.Blue800

@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = Blue800.copy(alpha = 0.6f)
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(
            labelBackgroundShape,
            labelBackgroundColor.toArgb()
        ).setShadow(
            radius = 0f,
            applyElevationOverlay = true,
        )
    }
    val label = textComponent(
        color = Color.White,
        background = labelBackground,
        padding = labelPadding,
    )
    return remember(label, null, null) {
        object : MarkerComponent(label, null, null) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                labelFormatter = DefaultMarkerLabelFormatter
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = with(context) {
                outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                        LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                        LABEL_BACKGROUND_SHADOW_DY.pixels
            }
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
private const val LABEL_BACKGROUND_SHADOW_DY = 2f
private const val INDICATOR_SIZE_DP = 36f
private const val SHADOW_RADIUS_MULTIPLIER = 1.3f

private val labelBackgroundShape = MarkerCorneredShape(
    Corner.Relative(
        20,
        RoundedCornerTreatment
    )
)
private val labelALLPaddingValue = 8.dp
private val labelPadding = dimensionsOf(all = labelALLPaddingValue)
