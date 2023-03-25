@file:Suppress("unused", "PackageDirectoryMismatch")

internal val aideModifiers: Map<String, List<String>> = run {
    val aide = mutableMapOf<String, List<String>>()

    aide["text"] = listOf("span")
    aide["_span"] = emptyList()
    aide["_longParameters"] = emptyList()
    aide["_onClick"] = emptyList()

    aide
}

