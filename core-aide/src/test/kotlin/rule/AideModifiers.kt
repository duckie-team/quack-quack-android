package rule

internal val aideModifiers: Map<String, List<String>> = run {
    val aide = mutableMapOf<String, List<String>>()

    aide["text"] = listOf("span", "highlight")
    aide["_span"] = emptyList()
    aide["_highlight"] = emptyList()

    aide
}

