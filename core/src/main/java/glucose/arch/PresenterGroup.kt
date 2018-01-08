package glucose.arch

import android.arch.lifecycle.LifecycleObserver
import glucose.arch.state.State
import glucose.arch.state.StateOwner

/**
 * The PresenterOwner must:
 *
 * - register the lifecycle observer
 * - preserve internal state
 */
interface PresenterGroup : LifecycleObserver, StateOwner, State