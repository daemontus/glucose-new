package com.github.daemontus.glucose

import android.os.Bundle
import android.view.ViewGroup
import glucose.arch.Presenter
import glucose.arch.PresenterActivity
import glucose.arch.inferred

class MainActivity : PresenterActivity<RootPresenter>(inferred(), Bundle.EMPTY)

class RootPresenter(parent: ViewGroup?, activity: PresenterActivity<*>) : Presenter(R.layout.presenter_root, parent, activity)