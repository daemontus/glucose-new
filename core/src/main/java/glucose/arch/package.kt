package glucose.arch

/**
 * LifecycleException is thrown when the code tries to perform operation
 * not allowed in the current lifecycle state.
 */
class LifecycleException(message: String) : RuntimeException(message)

fun lifecycleError(message: String): Nothing = throw LifecycleException(message)

inline fun <reified T> inferred(): Class<T> = T::class.java