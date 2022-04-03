package com.example.newmail.constants;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextInputHelper {
    public TextInputLayout layout;

    public TextInputEditText editText;

    public TextInputHelper(TextInputLayout layout, TextInputEditText editText){
        this.layout = layout;
        this.editText = editText;
    }
}
