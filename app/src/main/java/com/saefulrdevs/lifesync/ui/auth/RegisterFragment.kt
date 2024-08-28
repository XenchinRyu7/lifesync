package com.saefulrdevs.lifesync.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.saefulrdevs.lifesync.databinding.FragmentRegisterBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val birthDateEditText: TextInputEditText = binding.birthDateEditText

        birthDateEditText.setOnClickListener {
            showDatePicker(birthDateEditText)
        }

        return binding.root
    }

    private fun showDatePicker(editText: EditText) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val formattedDate = formatDate(selection)
            editText.setText(formattedDate)
        }
    }

    private fun formatDate(milliseconds: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(milliseconds))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
