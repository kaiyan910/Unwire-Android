package hk.olleh.unwire.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected var mBindings: T? = null
    protected var mViewCreated = false

    open val mIsObserveAfterRequest: Boolean = false

    private var mRequested = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBindings = DataBindingUtil.inflate(inflater, layout(), container, false)
        mBindings!!.lifecycleOwner = viewLifecycleOwner
        mViewCreated = true
        return mBindings!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBindings = null
        mViewCreated = false
        mRequested = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        afterViews()
    }

    protected open fun afterViews() {
        // do something after inflate view
        if (!mIsObserveAfterRequest) observe()
        checkIfRequestNeeded()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        super.setUserVisibleHint(isVisibleToUser)
        checkIfRequestNeeded()
    }

    protected open fun observe() {
    }

    protected open fun request() {
    }

    private fun checkIfRequestNeeded() {

        if (userVisibleHint && !mRequested && mViewCreated) {
            mRequested = true
            request()
            if (mIsObserveAfterRequest) observe()
        }
    }

    protected abstract fun layout(): Int
}