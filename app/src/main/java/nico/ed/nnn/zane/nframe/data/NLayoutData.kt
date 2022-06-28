package nico.ed.nnn.zane.nframe.data

enum class NLayoutType(private val value: String) {
    FLOW("Flow"),
    SLIDER("Slider");

    override fun toString(): String {
        return value
    }
}
