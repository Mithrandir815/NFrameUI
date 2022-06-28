package nico.ed.nnn.zane.nframe.data

enum class PresentAbsent(private val value: String) {
    PRESENT("あり"),
    ABSENT("なし");

    override fun toString(): String {
        return value
    }
}
