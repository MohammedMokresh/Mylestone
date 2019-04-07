package com.bernovia.mylestone.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class TextWatcherAdapter(private val view: EditText, private val listener: TextWatcherListener) : TextWatcher {

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        listener.onTextChanged(view, s.toString().trim())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // pass
    }

    override fun afterTextChanged(s: Editable) {
        // pass
    }

    interface TextWatcherListener {

        fun onTextChanged(view: EditText, text: String)

    }

}