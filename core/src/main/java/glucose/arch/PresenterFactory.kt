package glucose.arch

import android.os.Bundle
import android.view.ViewGroup

/**
 * A PresenterFactory is responsible for creating new presenters.
 *
 * PresenterFactory expects the presenter to have a constructor with a
 * (ViewGroup?, PresenterActivity<*>) signature. If you want to provide a different
 * constructor, you can use the [registerConstructor] method.
 *
 * Note that the factory always matches the class exactly when looking for a constructor.
 */
interface PresenterFactory {

    /**
     * Create a fresh instance of [presenterClass] with given [initialState].
     *
     * Note that if the [initialState] has data about the [Presenter.id] property,
     * additional state data can be added from the previously saved state for this id,
     * similar to the way View hierarchy can preserve View state for Views with a valid id.
     *
     * Aside form initializing the presenter with the given state, this method should also
     * call [Presenter.create] on the produced instance.
     */
    fun <T: Presenter> makePresenter(presenterClass: Class<T>, initialState: Bundle = Bundle.EMPTY, parent: ViewGroup? = null): T

    /**
     * Register a custom constructor for the given [presenterClass]. Note that this does not
     * affect any classes inheriting from [T], etc. — just [T] itself.
     *
     * Specifically, be careful if in [constructor], you return some U s.t. U inherits from [T].
     * If the presenter is then recreated from persistent state, the class of U is going to be used,
     * not [T], so make sure you also register the same constructor for U.
     */
    fun <T: Presenter> registerConstructor(presenterClass: Class<T>, constructor: (ViewGroup?, PresenterActivity<*>) -> T)

}