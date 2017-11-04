package glucose.arch.state

import android.os.Bundle

/**
 * A state object represents a single value managed by [StateOwner] which can be
 * saved into a [Bundle].
 */
interface State {

    /**
     * Read the state data from the given [data] bundle, using a sensible default when
     * this data is not available.
     */
    fun readFromBundle(data: Bundle)

    /**
     * Write the state data to the given [data] bundle. If the object holds no data, no
     * write is necessary.
     */
    fun writeToBundle(data: Bundle)

}