package hk.olleh.unwire.common.miscellaneous

object TimeAgo {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(timestamp: Long): String? {

        var time = timestamp

        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = System.currentTimeMillis()

        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time

        return when {
            diff < MINUTE_MILLIS -> "剛剛"
            diff < 2 * MINUTE_MILLIS -> "1 分鐘前"
            diff < 50 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} 分鐘前"
            diff < 90 * MINUTE_MILLIS -> "1 小時前"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} 小時前"
            diff < 48 * HOUR_MILLIS -> "昨天"
            else -> "${diff / DAY_MILLIS} 日前"
        }
    }
}