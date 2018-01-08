package glucose.arch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import glucose.arch.state.StateOwner
import glucose.arch.state.StateOwnerDelegate

abstract class PresenterActivity<P: Presenter> private constructor(
        private val rootPresenter: Class<P>, private val defaultState: Bundle,
        private val delegate: StateOwnerDelegate
) : AppCompatActivity(), StateOwner by delegate {

    constructor(rootPresenter: Class<P>, defaultState: Bundle) : this(rootPresenter, defaultState, StateOwnerDelegate("glucose"))

    @Suppress("LeakingThis") // We won't be creating anything until onCreate. This is just to register constructors.
    private val _factory = PresenterFactoryDelegate(this)
    val factory: PresenterFactory
        get() = _factory

    private lateinit var root: SingletonGroup<P>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FrameLayout(this)
        setContentView(view)
        root = SingletonGroup(rootPresenter, defaultState, view, this, StateOwnerDelegate("root"))
        lifecycle.addObserver(root)
        registerState(root)
        delegate.readFromBundle(savedInstanceState ?: Bundle.EMPTY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.writeToBundle(outState)
    }

}

