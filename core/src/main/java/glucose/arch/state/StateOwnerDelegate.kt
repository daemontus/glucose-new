package glucose.arch.state

import android.os.Bundle
import glucose.arch.PresenterGroup
import glucose.arch.lifecycleError

/**
 * StateOwnerDelegate is a reference implementation of [StateOwner], also serving
 * as a regular [State]. Therefore, using this class, you can create fully persistent
 * hierarchies of states, since objects using StateOwnerDelegate can be used both
 * to store structured data (using StateOwner) and subsequently be saved into a simple
 * [Bundle] using the [State] interface.
 *
 * This class is especially useful when implementing custom [PresenterGroup]s.
 *
 * //TODO: Don't allow access to state when it's not loaded yet.
 */
class StateOwnerDelegate(private val key: String) : StateOwner, State {

    private val states = HashSet<State>()

    override var hasState: Boolean = false
        private set

    /* State */

    override fun readFromBundle(data: Bundle) {
        initFromBundle(data.getBundle(key) ?: Bundle())
    }

    override fun writeToBundle(data: Bundle) {
        data.putBundle(key, saveToBundle())
    }

    /* State Owner */

    override fun registerState(state: State) {
        if (hasState) lifecycleError("Cannot register state. Component already initialized.")
        states.add(state)
    }

    override fun initFromBundle(data: Bundle) {
        if (hasState) lifecycleError("Cannot load state. Component already initialized.")
        hasState = true
        states.forEach { it.readFromBundle(data) }
    }

    override fun saveToBundle(): Bundle {
        if (!hasState) lifecycleError("Cannot save state. Component not initialized.")
        val result = Bundle()
        states.forEach { it.writeToBundle(result) }
        return result
    }

}