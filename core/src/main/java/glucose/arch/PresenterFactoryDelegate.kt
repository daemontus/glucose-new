package glucose.arch

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

class PresenterFactoryDelegate(
        private val activity: PresenterActivity<*>
) : PresenterFactory {

    private val constructors = HashMap<Class<*>, ((ViewGroup?, PresenterActivity<*>) -> Any)>()

    internal var savedState: SparseArray<Bundle>? = null

    override fun <T : Presenter> makePresenter(presenterClass: Class<T>, initialState: Bundle, parent: ViewGroup?): T {
        synchronized(constructors) {
            val p = presenterClass.cast(constructors[presenterClass]?.invoke(parent, activity) ?: run {
                val constructor = presenterClass.getConstructor(ViewGroup::class.java, PresenterActivity::class.java)
                constructor.newInstance(parent, activity)
            })
            val state = savedState?.get(initialState.getInt(Presenter::id.name, View.NO_ID))?.let { saved ->
                Bundle().apply { putAll(initialState); putAll(saved) }
            } ?: initialState
            p.initFromBundle(state)
            p.create()
            return p
        }
    }

    override fun <T : Presenter> registerConstructor(presenterClass: Class<T>, constructor: (ViewGroup?, PresenterActivity<*>) -> T) {
        synchronized(constructors) {
            constructors[presenterClass] = constructor
        }
    }
}