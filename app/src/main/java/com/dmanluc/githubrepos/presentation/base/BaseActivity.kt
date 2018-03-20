package com.dmanluc.githubrepos.presentation.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.presentation.App
import com.dmanluc.githubrepos.presentation.di.component.AppComponent
import kotlinx.android.synthetic.main.base_activity_with_fragment.loading_container
import kotlinx.android.synthetic.main.base_activity_with_fragment.loading_indicator

/**
 * Base Activity Template for MVP pattern
 *
 * @author Daniel Manrique Lucas
 */
abstract class BaseActivity<in V : BaseView, out P : Presenter<V>> : AppCompatActivity() {

    @get:LayoutRes protected abstract val layoutId: Int

    protected abstract val screenTitle: String?

    protected abstract val presenter: P

    protected val appComponent: AppComponent
        get() = (application as App).appComponent

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideDaggerDependency()
        setContentView(layoutId)
        setActionBar(screenTitle, showBackArrow())
        disableTouchLoadingView()
        presenter.attachView(this as V)
        presenter.recoverState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this as V)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.attachView(this as V)
    }

    override fun onPause() {
        presenter.detachView()
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    open protected fun provideDaggerDependency() {}

    abstract protected fun showBackArrow(): Boolean

    open fun setActionBar(heading: String?, enableBackArrow: Boolean) {
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_TITLE
            title = heading.orEmpty()
            setDisplayHomeAsUpEnabled(enableBackArrow)
            setDisplayShowHomeEnabled(enableBackArrow)
            show()
        }
    }

    fun switchFragment(@NonNull fragment: Fragment) {
        switchFragment(fragment, true)
    }

    fun switchFragment(
            @NonNull fragment: Fragment,
            addToBackStack: Boolean) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        transaction.replace(R.id.fragment, fragment, fragment.javaClass.simpleName)

        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.commit()

        Log.d(BaseActivity.TAG, "Switch to fragment [$fragment]")
    }

    protected fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    protected fun showLoading() {
        loading_container.visibility = VISIBLE
        loading_indicator.smoothToShow()
    }

    protected fun hideLoading() {
        loading_indicator.smoothToHide()
        loading_container.visibility = GONE
    }

    private fun disableTouchLoadingView() {
        loading_container.setOnTouchListener { _, _ -> true }
    }

    companion object {

        private val TAG = this::class.java.simpleName
    }

}
