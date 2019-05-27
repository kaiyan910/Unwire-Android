package hk.olleh.unwire.common.miscellaneous

sealed class Resource<T> {

    data class success<T>(val data: T): Resource<T>()
    data class error<T>(val error: ErrorState): Resource<T>()
}