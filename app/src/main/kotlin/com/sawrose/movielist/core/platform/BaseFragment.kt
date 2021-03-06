package com.sawrose.movielist.core.platform

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sawrose.movielist.MovieApp
import com.sawrose.movielist.R.color
import com.sawrose.movielist.core.di.component.AppComponent
import com.sawrose.movielist.core.extension.appContext
import com.sawrose.movielist.core.extension.viewContainer
import kotlinx.android.synthetic.main.toolbar.progress
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    val appComponent: AppComponent by lazy(mode = NONE) {
        (activity?.application as MovieApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? = inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int,
            action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(appContext,
                color.colorTextPrimary))
        snackBar.show()
    }
}