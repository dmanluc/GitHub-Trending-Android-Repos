package com.dmanluc.githubrepos.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmanluc.githubrepos.presentation.App
import com.dmanluc.githubrepos.presentation.base.BaseFragment.BaseCallback
import com.dmanluc.githubrepos.presentation.di.component.AppComponent

/**
 * Base Fragment Template for MVP pattern
 *
 * @author Daniel Manrique Lucas
 */
abstract class BaseFragment<in V : BaseView, out P : Presenter<V>, C : BaseCallback> : Fragment() {

    protected var callback: C? = null

    @get:LayoutRes protected abstract val layoutId: Int

    protected abstract var screenTitle: String

    protected abstract val presenter: P

    protected val appComponent: AppComponent
        get() = (activity?.application as App).appComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)

        provideDaggerDependency()
        try {
            callback = context as C
        } catch (e: ClassCastException) {
            throw ClassCastException(
                    context.toString() + " must implement " + (callback as BaseActivity<*, *>).javaClass.simpleName)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.recoverState(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(layoutId, container, false)
        presenter.attachView(this as V)
        (callback as BaseActivity<*, *>).setActionBar(screenTitle, showBackArrow())

        return rootView
    }

    abstract fun showBackArrow(): Boolean

    open protected fun provideDaggerDependency() {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveState(outState)
    }

    override fun onDetach() {
        callback = null
        presenter.detachView()
        super.onDetach()
    }

    interface BaseCallback

}
