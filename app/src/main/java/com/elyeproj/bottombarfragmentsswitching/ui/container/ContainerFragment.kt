package com.elyeproj.bottombarfragmentsswitching.ui.container

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elyeproj.bottombarfragmentsswitching.ui.child.ChildFragment
import com.elyeproj.bottombarfragmentsswitching.R
import kotlinx.android.synthetic.main.fragment_container.*

class ContainerFragment : Fragment(),
    ChildFragment.OnSendMessageListener {

    companion object {
        const val KEY = "FragmentKey"
        const val COLOR = "FragmentColor"
        fun newInstance(key: String, color: String): Fragment {
            val fragment =
                ContainerFragment()
            val argument = Bundle()
            argument.putString(KEY, key)
            argument.putString(COLOR, color)
            fragment.arguments = argument
            return fragment
        }
    }

    private var count = 0

    override fun onSendMessage(childName: String?) {
        from_child.text = getString(R.string.child_name, childName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.addOnBackStackChangedListener {
            val message =
                StringBuffer("Total child fragments are = ${childFragmentManager.backStackEntryCount}\n");

            for (index in childFragmentManager.backStackEntryCount - 1 downTo 0) {
                val entry = childFragmentManager.getBackStackEntryAt(index)
                message.append("${entry.name} \n")
            }
            Log.d("ContainerFragment", message.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            count = childFragmentManager.backStackEntryCount
        }
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val key = it.getString(KEY)
            text_title.text = key
            container.setBackgroundColor(Color.parseColor(it.getString(COLOR)))


            button_open_child_fragment.setOnClickListener {
                val childKey = key + (count + 1).toString()
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        ChildFragment.newInstance(childKey),
                        childKey
                    )
                    .addToBackStack(childKey)
                    .commit()
            }
        }

        childFragmentManager.addOnBackStackChangedListener {
            count = childFragmentManager.backStackEntryCount
        }
    }
}
