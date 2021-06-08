package com.example.one.textcolor;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.TextView;

public class textcolor1 {

    public static void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize()* textView.getText().length(), 0, Color.parseColor("#5c6ddd"), Color.parseColor("#2bb7d2"), Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}
