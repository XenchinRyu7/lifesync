package com.saefulrdevs.lifesync.utils

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

object CodeInputUtils {

    fun setupPinInputListeners(
        pinFields: List<TextInputEditText>
    ) {
        for (i in pinFields.indices) {
            val currentField = pinFields[i]
            val nextField = pinFields.getOrNull(i + 1)
            val previousField = pinFields.getOrNull(i - 1)

            currentField.addTextChangedListener {
                val text = currentField.text.toString()
                if (text.length == 1) {
                    nextField?.requestFocus()
                } else if (text.isEmpty()) {
                    previousField?.requestFocus()
                }
            }
        }

        for (field in pinFields) {
            setupPasteListener(field, pinFields)
        }
    }

    private fun setupPasteListener(
        pinField: TextInputEditText,
        pinFields: List<TextInputEditText>
    ) {
        pinField.setOnPasteListener { pastedText ->
            handlePaste(pastedText, pinFields)
        }
    }

    private fun handlePaste(pastedText: String, pinFields: List<TextInputEditText>) {
        val pinArray = pastedText.take(pinFields.size).toCharArray()
        for (i in pinArray.indices) {
            pinFields.getOrNull(i)?.setText(pinArray[i].toString())
        }
    }

    private fun TextInputEditText.setOnPasteListener(onPaste: (String) -> Unit) {
        this.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription?.hasMimeType(
                        ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
                    clipboard.addPrimaryClipChangedListener {
                        val pastedData = clipboard.primaryClip?.getItemAt(0)?.text.toString()
                        onPaste(pastedData)
                    }
                }
            }
        }
    }
}
