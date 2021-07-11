package net.plantix.demo.data.model

sealed class APIresult<out T > {

    data class Success<out T>(val value: T) : APIresult<T>()

    data class Failure<out T>(val message : String) : APIresult<T>()

    data class Loading<out T>(val value: T? = null) : APIresult<T>()

    companion object {

        fun <T> loading( value: T? ): APIresult<T> = Loading(value)

        fun <T> success( value: T ): APIresult<T> = Success(value)

        fun <T> failure( error_msg : String ): APIresult<T> = Failure(error_msg)

    }

}
