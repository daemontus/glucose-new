package com.github.daemontus.glucose

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import glucose.arch.*
import glucose.arch.state.Persistent

class MainActivity : PresenterActivity<RootPresenter>(inferred(), Bundle.EMPTY) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On create $this")
    }
}

class RootPresenter(parent: ViewGroup?, activity: PresenterActivity<*>) : Presenter(R.layout.presenter_root, parent, activity) {

    private val container = view as LinearLayout

    private val text1 by Groups.singleton<TextPresenter>(container)
    private val text2 by Groups.singleton<TextPresenter>(container, initialState = Bundle().apply {
        putString(TextPresenter::text.name, "text2")
    })

    override fun onCreate() {
        super.onCreate()
        println("On create $this")
    }
}

class TextPresenter(parent: ViewGroup?, activity: PresenterActivity<*>) : Presenter(R.layout.presenter_text, parent, activity) {

    val text by Persistent.string("unknown")
    private val label = view.findViewById<TextView>(R.id.label)

    override fun onCreate() {
        super.onCreate()
        println("On create $this")
        label.text = text
    }

}