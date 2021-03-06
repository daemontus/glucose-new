package glucose.arch.state

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A delegate provider wrapper which registers each [StateProperty] with the corresponding
 * [StateOwner] upon creation.
 */
class StatePropertyLoader<T : Any>(
        private val bundler: Bundler<T>,
        private val default: T
) {

    operator fun provideDelegate(
            thisRef: StateOwner, property: KProperty<*>
    ): ReadWriteProperty<StateOwner, T> {
        return StateProperty(property.name, default, bundler).also { thisRef.registerState(it) }
    }

}