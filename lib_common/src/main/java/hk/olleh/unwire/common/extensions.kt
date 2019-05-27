package hk.olleh.unwire.common

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun String.toDateTime(pattern: String): DateTime? = try {
    val formatter = DateTimeFormat.forPattern(pattern)
    formatter.parseDateTime(this)
} catch (e: Exception) {
    null
}

fun String.findFirstMatchPattern(pattern: String): String? =
    pattern
        .toRegex()
        .find(this)
        ?.groupValues
        ?.get(1)