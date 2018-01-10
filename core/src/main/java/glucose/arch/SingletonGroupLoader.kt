package glucose.arch

import android.os.Bundle
import android.view.ViewGroup
import glucose.arch.state.StateOwnerDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SingletonGroupLoader<T: Presenter>(
        private val presenterClass: Class<T>,
        private val container: ViewGroup,
        private val initialState: Bundle
) {

    operator fun provideDelegate(thisRef: Presenter, property: KProperty<*>): ReadOnlyProperty<Presenter, SingletonGroup<T>> {
        val group = SingletonGroup(presenterClass, initialState, container, thisRef.activity, StateOwnerDelegate(property.name))
        thisRef.lifecycle.addObserver(group)
        return Property(group)
    }

    private class Property<T: Presenter>(
            private val value: SingletonGroup<T>
    ) : ReadOnlyProperty<Presenter, SingletonGroup<T>> {
        override fun getValue(thisRef: Presenter, property: KProperty<*>): SingletonGroup<T> = value
    }

}