package glucose.arch.state

import android.os.Bundle
import glucose.arch.PresenterOwner
import glucose.arch.lifecycleError

/**
 * StateOwnerDelegate is a reference implementation of [StateOwner], also serving
 * as a regular [State]. Therefore, using this class, you can create fully persistent
 * hierarchies of states, since objects using StateOwnerDelegate can be used both
 * to store structured data (using StateOwner) and subsequently be saved into a simple
 * [Bundle] using the [State] interface.
 *
 * This class is especially useful when implementing custom [PresenterOwner]s.
 */
class StateOwnerDelegate(private val key: String) : StateOwner, State {

    private val states = HashSet<State>()
    private var isInitialized = false

    /* State */

    override fun readFromBundle(data: Bundle) {
        initFromBundle(data.getBundle(key) ?: Bundle())
    }

    override fun writeToBundle(data: Bundle) {
        data.putBundle(key, saveToBundle())
    }

    /* State Owner */

    override fun registerState(state: State) {
        if (isInitialized) lifecycleError("Cannot register state. Component already initialized")
        states.add(state)
    }

    override fun initFromBundle(data: Bundle) {
        states.forEach { it.readFromBundle(data) }
    }

    override fun saveToBundle(): Bundle {
        val result = Bundle()
        states.forEach { it.writeToBundle(result) }
        return result
    }

}