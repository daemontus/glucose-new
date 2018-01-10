package glucose.arch.state

import android.os.Bundle
import glucose.arch.LifecycleException

/**
 * This interface is implemented by all objects which hold a state which must be persisted
 * between application restarts.
 *
 * While the state owner is constructed, [registerState] can be called several times to
 * associate all state objects with their owner. However, once [initFromBundle] is called,
 * no further calls to [registerState] are allowed (to preserve data integrity).
 *
 * Overall, each state owner has only two states, indicated by [hasState].
 *
 * If [hasState] is false, [registerState] is available but any requests for value on
 * registered [State] objects, or [saveToBundle] should fail with a [LifecycleException].
 *
 * Then, [initFromBundle] can be called, which sets [hasState] to true. At this point,
 * all associated [State] objects can be accessed and modified and [saveToBundle] is available.
 * however, any subsequent calls to [registerState] or [initFromBundle] will
 * fail with [LifecycleException].
 */
interface StateOwner {

    /**
     * True if [initFromBundle] has been called.
     */
    val hasState: Boolean

    /**
     * Indicate that given [state] is managed by this StateOwner.
     * Can be called only before [initFromBundle] is called.
     *
     * @throws [LifecycleException] when called after [initFromBundle].
     */
    fun registerState(state: State)

    /**
     * Initialize the state of this object from the given bundle.
     *
     * Should be called only once, after all [State] objects have been
     * registered using [registerState].
     *
     * @throws [LifecycleException] when called multiple times.
     */
    fun initFromBundle(data: Bundle)

    /**
     * Save the state of this object into a [Bundle].
     *
     * Unfortunately, due to the internal behaviour of Android, we can't guarantee
     * that this method is called only once. However, a fresh bundle should be produced
     * for each call.
     *
     * @throws [LifecycleException] if called before [initFromBundle].
     */
    fun saveToBundle(): Bundle

}