package glucose.arch.state

import android.os.Bundle
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A [State] implementation which also serves as [ReadOnlyProperty] and [ReadWriteProperty].
 *
 * This is used to implement actual values in the state hierarchy.
 */
class StateProperty<T : Any>(
        private val key: String,
        private val default: T,
        bundler: Bundler<T>
) : State, Bundler<T> by bundler, ReadOnlyProperty<StateOwner, T>, ReadWriteProperty<StateOwner, T> {

    private var value: T = default

    override fun getValue(thisRef: StateOwner, property: KProperty<*>): T = this.value

    override fun setValue(thisRef: StateOwner, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun readFromBundle(data: Bundle) {
        value = data.getValue(key, default)
    }

    override fun writeToBundle(data: Bundle) {
        data.putValue(key, value)
    }
}