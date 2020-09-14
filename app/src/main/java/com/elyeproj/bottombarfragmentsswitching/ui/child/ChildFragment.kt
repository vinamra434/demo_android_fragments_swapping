package com.elyeproj.bottombarfragmentsswitching.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elyeproj.bottombarfragmentsswitching.R
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {

    private var onSendMessageListener: OnSendMessageListener? = null

    companion object {
        const val KEY = "FragmentKey"
        fun newInstance(key: String): Fragment {
            val fragment =
                ChildFragment()
            val argument = Bundle()
            argument.putString(KEY, key)
            fragment.arguments = argument
            return fragment
        }
    }


    private fun onAttachToParentFragment(fragment: Fragment) {
        try {
            onSendMessageListener = fragment as OnSendMessageListener
        } catch (e: java.lang.ClassCastException) {
            throw java.lang.ClassCastException(
                "$fragment must implement OnPlayerSelectionSetListener"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment?.let { onAttachToParentFragment(it) }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var childName:String? = null

        arguments?.let {
            childName = it.getString(KEY)
            child_text_title.text = childName
        }

        child_send_message.setOnClickListener {
            onSendMessageListener?.onSendMessage(childName)
        }
    }

    interface OnSendMessageListener {
        fun onSendMessage(childName: String?)
    }
}
