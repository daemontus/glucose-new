package glucose.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import glucose.arch.state.Persistent
import glucose.arch.state.State
import glucose.arch.state.StateOwner
import glucose.arch.state.StateOwnerDelegate

/**
 * Presenter is a simple component responsible for managing its assigned view hierarchy.
 *
 * Typically, this includes loading data from an associated [ViewModel] based on a
 * restart-persistent [State], displaying animations/styles, reacting to user input, etc.
 *
 * Presenter is a [StateOwner], which means you can use [Persistent] delegates to
 * access the persistent state without saving/loading it from the state bundle directly.
 *
 * Presenters form a tree, similar to the typical fragment structure. However, as opposed
 * to the fragment paradigm, where each component has one general fragment manager,
 * presenters use domain specific [PresenterGroup]s when managing their child presenters.
 * This ensures more transparency and safer interaction between Presenters.
 *
 * As opposed to Fragments, Presenters have a [View] associated with them for the
 * whole duration of their lifecycle. Therefore, you should avoid keeping presenters
 * alive if not necessary. All your critical data should be saved in the persistent state
 * and all additional data which can be fetched/retrieved asynchronously should be part
 * of the ViewModel.
 */
open class Presenter private constructor(
        val view: View,
        val activity: PresenterActivity<*>,
        stateDelegate: StateOwnerDelegate
) : LifecycleOwner, StateOwner by stateDelegate {

    constructor(view: View, activity: PresenterActivity<*>): this(view, activity, StateOwnerDelegate("unused"))

    constructor(@LayoutRes layout: Int, parentView: ViewGroup?, activity: PresenterActivity<*>)
        : this(LayoutInflater.from(activity.application).inflate(layout, parentView, false), activity)

    val id: Int by Persistent.int(View.NO_ID)

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
    protected open fun onPause() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    @CallSuper
    protected open fun onStop() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @CallSuper
    protected open fun onDestroy() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    fun create() = onCreate()

    fun start() = onStart()

    fun resume() = onResume()

    fun pause() = onPause()

    fun stop() = onStop()

    fun destroy() = onDestroy()

}