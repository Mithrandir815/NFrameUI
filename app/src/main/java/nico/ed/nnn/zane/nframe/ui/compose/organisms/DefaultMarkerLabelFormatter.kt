package nico.ed.nnn.zane.nframe.ui.compose.organisms

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.extension.appendCompat
import com.patrykandpatrick.vico.core.extension.sumOf
import com.patrykandpatrick.vico.core.extension.transformToSpannable
import com.patrykandpatrick.vico.core.marker.DefaultMarkerLabelFormatter
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

/**
 * The default label formatter used for markers.
 *
 * @see MarkerLabelFormatter
 */
object DefaultMarkerLabelFormatter : MarkerLabelFormatter {

    private const val PATTERN = "%d"

    override fun getLabel(
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues,
    ): CharSequence = markedEntries.transformToSpannable(
        prefix = if (markedEntries.size > 1) PATTERN.format(markedEntries.sumOf { it.entry.y }
            .toInt()) else "",
    ) { model ->
        appendCompat(
            PATTERN.format(model.entry.y.toInt()),
            ForegroundColorSpan(Color.White.hashCode()),
            Spannable.SPAN_COMPOSING,
        )
    }
}
