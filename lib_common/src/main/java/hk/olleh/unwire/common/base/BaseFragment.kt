package hk.olleh.unwire.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected var bindings: T? = null
    protected var viewCreated = false

    open val isObserveAfterRequest: Boolean = false

    private var requested = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindings = DataBindingUtil.inflate(inflater, layout(), container, false)
        bindings!!.lifecycleOwner = viewLifecycleOwner
        viewCreated = true
        return bindings!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindings = null
        viewCreated = false
        requested = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        afterViews()
    }

    protected open fun afterViews() {
        // do something after inflate view
        if (!isObserveAfterRequest) observe()
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

        if (userVisibleHint && !requested && viewCreated) {
            requested = true
            request()
            if (isObserveAfterRequest) observe()
        }
    }

    protected abstract fun layout(): Int
}