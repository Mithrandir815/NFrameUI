package nico.ed.nnn.zane.nframe.data

enum class NLayoutType(private val value: String) {
    FLOW("Flow"),
    SLIDER("Slider");

    override fun toString(): String {
        return value
    }
}

enum class NLayoutDirection(private val value: String) {
    ROW("Row（→）"),
    COLUMN("Column（↓）");

    override fun toString(): String {
        return value
    }
}

enum class NLayoutItemAlign(private val value: String) {
    START("Start"),
    CENTER("Center"),
    END("End"),
    STRETCH("Stretch");

    override fun toString(): String {
        return value
    }
}
