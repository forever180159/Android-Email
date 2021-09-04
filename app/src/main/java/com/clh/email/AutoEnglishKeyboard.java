package com.clh.email;
import android.content.Context;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;

public class AutoEnglishKeyboard {
    /*例如输入帐号，如果默认用中文输入法，输入中文的"@"符号会导致闪退，这里设置自动转换英文输入法*/
    public void EnglishKeyboard(Context context, EditText editText){
        editText.setKeyListener(new DigitsKeyListener(){
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(context,R.string.english_keyboard).toCharArray();
                return data;
            }
        });
    }
    public String getStringData(Context getContext,int id){
        return getContext.getResources().getString(id);
    }
}
