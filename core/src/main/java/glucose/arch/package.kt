package glucose.arch

import android.os.Bundle
import android.view.ViewGroup

/**
 * LifecycleException is thrown when the code tries to perform operation
 * not allowed in the current lifecycle state.
 */
class LifecycleException(message: String) : RuntimeException(message)

fun lifecycleError(message: String): Nothing = throw LifecycleException(message)

inline fun <reified T> inferred(): Class<T> = T::class.java

inline fun <reified T: Presenter> PresenterFactory.makePresenter(initialState: Bundle = Bundle.EMPTY, parent: ViewGroup? = null)
        = makePresenter(T::class.java, initialState, parent)

object Groups

inline fun <reified T: Presenter> Groups.singleton(container: ViewGroup, initialState: Bundle = Bundle.EMPTY): SingletonGroupLoader<T>
        = SingletonGroupLoader(inferred(), container, initialState)