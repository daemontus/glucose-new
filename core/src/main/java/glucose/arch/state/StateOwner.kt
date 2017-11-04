package glucose.arch.state

import android.os.Bundle

/**
 * This interface is implemented by all objects which hold a state which must be persisted
 * between application restarts.
 *
 * While the state owner is constructed, [registerState] can be called several times to
 * associate all state objects with their owner. However, once [initFromBundle] is called,
 * no further calls to [registerState] are allowed (to preserve data integrity).
 */
interface StateOwner {

    /**
     * Indicate that given [state] is managed by this StateOwner.
     *
     * Can be called only before [initFromBundle] is called.
     */
    fun registerState(state: State)

    /**
     * Initialize the state of this object from the given bundle.
     *
     * Should be called only once, after all [State] objects have been
     * registered using [registerState].
     */
    fun initFromBundle(data: Bundle)

    /**
     * Save the state of this object into a [Bundle].
     *
     * Unfortunately, due to the internal behaviour of Android, we can't guarantee
     * that this method is called only once. However, a fresh bundle should be produced
     * for each call.
     */
    fun saveToBundle(): Bundle

}