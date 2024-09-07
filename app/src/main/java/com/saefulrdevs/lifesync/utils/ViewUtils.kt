package com.saefulrdevs.lifesync.utils

import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.saefulrdevs.lifesync.utils.DateUtils.formatDate

object ViewUtils {
    fun showDatePicker(
        fragment: Fragment,
        editText: TextInputEditText? = null,
        cardView: CardView? = null,
        textView: TextView? = null
    ) {
        editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build()

            datePicker.show(fragment.parentFragmentManager, "MATERIAL_DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val formattedDate = formatDate(selection)
                editText.setText(formattedDate)
            }
        }

        cardView?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build()

            datePicker.show(fragment.parentFragmentManager, "MATERIAL_DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val formattedDate = formatDate(selection)

                if (textView != null) {
                    textView.text = formattedDate
                } else {
                    Toast.makeText(
                        fragment.requireContext(),
                        "Selected Date: $formattedDate",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}