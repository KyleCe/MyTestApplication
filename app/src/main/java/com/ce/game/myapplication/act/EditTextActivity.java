package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextActivity extends Activity {

    @BindView(R.id.enter_test)
    EditText mEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.bind(this);

        try{
            startActivity(new Intent(this,OneLineActivity.class));
        }catch (Exception e){
            DU.td(this,"exception caught");
        }

        test();
    }

    private void test() {

        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEnter.getWindowToken(), 0);

        mEnter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    DU.t(getApplicationContext(),"enter");
                }
                return true;
            }
        });
//        mEnter.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
//
//        mEnter.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    DU.t(getApplicationContext(),"enter");
//                    return true;
//                }
//                return false;
//            }
//        });
    }
}
