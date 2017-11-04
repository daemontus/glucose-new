package glucose.arch

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import glucose.arch.state.Persistent
import glucose.arch.state.StateOwner
import glucose.arch.state.StateOwnerDelegate

/**
 * Presenter is a simple component responsible for managing its assigned view hierarchy,
 * replacing standard Activities and Fragments.
 *
 * Typically, this includes loading data from an associated [ViewModel] based on a
 * restart-persistent state, displaying animations/styles, reacting to user input, etc.
 *
 * Presenter is a [StateOwner], which means you can use [Persistent] delegates to
 * access the persistent state without saving/loading it from the state bundle directly.
 *
 * Presenters form a tree, similar to the typical fragment structure. However, as opposed
 * to the fragment paradigm, where each component has one general fragment manager,
 * presenters use domain specific [PresenterOwner]s when managing their child presenters.
 *
 */
open class Presenter private constructor(
        stateDelegate: StateOwnerDelegate = StateOwnerDelegate()
) : LifecycleOwner, StateOwner by stateDelegate {

    constructor(): this(StateOwnerDelegate("unused"))

    /* ---------- Basic Presenter lifecycle ---------- */

    @Suppress("LeakingThis")    // leak is ok, because registry won't use it in its constructor
    private val lifecycle = LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle = lifecycle

    @CallSuper
    protected open fun onCreate() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @CallSuper
    protected open fun onStart() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @CallSuper
    protected open fun onResume() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @CallSuper
    protected open fun onStop() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @CallSuper
    protected open fun onDestroy() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    /* ---------- State preservation ---------- */

}

/**
 * The PresenterOwner must:
 *
 * - register the lifecycle observer
 * - preserve internal state
 */
interface PresenterOwner : LifecycleObserver

/**
 * This very simple [PresenterOwner] simply always displays
 */
class SingletonPresenterOwner internal constructor(
        private val presenterClass: Class<in Presenter>,
        private val defaultState: Bundle = Bundle(),
        private val delegate: PersistentValueDelegate = PersistentValueDelegate()
) : PresenterOwner, PersistentStateOwner by delegate, BundleValue by delegate  {

    fun onCreate(owner: LifecycleOwner) {

    }

}

class PresenterActivity : AppCompatActivity()

class LifecycleException(message: String) : RuntimeException(message)

fun lifecycleError(message: String): Nothing = throw LifecycleException(message)