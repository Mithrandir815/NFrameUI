package nico.ed.nnn.zane.nframe.data

enum class NFrameHeader(private val value: String) {
    PRESENT("あり"),
    ABSENT("なし");

    override fun toString(): String {
        return value
    }
}

enum class NBGMedia(private val value: String) {
    IMAGE("画像"),
    SOLID("背景色"),
    MOVIE("動画"),
    NONE("なし");

    override fun toString(): String {
        return value
    }
}

enum class NFrameHeaderCard(private val value: String) {
    PRESENT("表示する"),
    ABSENT("表示しない");

    override fun toString(): String {
        return value
    }
}

enum class NFrameHeaderFixed(private val value: String) {
    FIXED("固定する"),
    MOVE("固定しない");

    override fun toString(): String {
        return value
    }
}
