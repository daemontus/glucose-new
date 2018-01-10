package glucose.arch.state

import android.os.Bundle

/**
 * A state object represents a single value managed by [StateOwner] which can be
 * saved into a [Bundle].
 *
 * It does not have any lifecycle or specific data access functionality (you can
 * choose to keep the whole bundle and update the data directly, or cache it in a local property
 * and write it into bundle only when requested, etc.). It can be a single get/set property,
 * but also a more complex data structure which simply serializes into a bundle.
 *
 * However, it can refuse to return any data if [readFromBundle] has not been called at
 * least once.
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