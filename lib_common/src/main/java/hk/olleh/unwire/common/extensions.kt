package hk.olleh.unwire.common

import androidx.fragment.app.Fragment
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

fun <T> Fragment.argument(key: String) =
    lazy { arguments?.get(key) as T ?: throw IllegalArgumentException("$key is required.") }

fun <T> Fragment.argument(key: String, defaultValue: T) = lazy {
    arguments?.get(key) as? T ?: defaultValue
}
