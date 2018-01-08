package glucose.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.view.ViewGroup
import glucose.arch.state.Persistent
import glucose.arch.state.State
import glucose.arch.state.StateOwner
import glucose.arch.state.StateOwnerDelegate

/**
 * Always displays a single Presenter which can't change.
 */
class SingletonGroup<T: Presenter> constructor(
        presenterClass: Class<T>, initialState: Bundle,
        private val container: ViewGroup,
        private val activity: PresenterActivity<*>,
        private val delegate: StateOwnerDelegate
): PresenterGroup, StateOwner by delegate, State by delegate {

    private val presenterClass: Class<T> by Persistent.serializable(presenterClass, inferred())
    private var presenterState: Bundle by Persistent.bundle(initialState)

    private var child: T? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        child = activity.factory.makePresenter(presenterClass, presenterState)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        child?.let {
            container.addView(it.view)
            it.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        child?.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        child?.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        child?.let {
            it.stop()
            container.removeView(it.view)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        child?.destroy()
        child = null
    }

    override fun saveToBundle(): Bundle {
        presenterState = child?.saveToBundle() ?: Bundle.EMPTY
        return delegate.saveToBundle()
    }

}

