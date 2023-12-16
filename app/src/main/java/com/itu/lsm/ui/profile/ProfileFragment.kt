package com.itu.lsm.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itu.lsm.databinding.FragmentHomeBinding
import com.itu.lsm.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView1: TextView = binding.nameText
        val textView2: TextView = binding.surnameText

        profileViewModel.name.observe(viewLifecycleOwner) {
            textView1.text = it
        }

        profileViewModel.surname.observe(viewLifecycleOwner) {
            textView2.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

// todo add the logic to the switch button
//ToggleButton switchButton = findViewById(R.id.switchButton);
//
//switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        // Handle the switch state change (isChecked)
//        if (isChecked) {
//            // Switch is ON
//        } else {
//            // Switch is OFF
//        }
//    }
//});
