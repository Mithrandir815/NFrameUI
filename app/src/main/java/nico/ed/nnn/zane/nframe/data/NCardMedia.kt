package nico.ed.nnn.zane.nframe.data

enum class NCardMedia(private val value: String) {
    IMAGE("画像"),
    SOLID("背景色"),
    MOVIE("動画"),
    NONE("なし");

    override fun toString(): String {
        return value
    }
}
