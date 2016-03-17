package com.example.dxuser.passwordstrengthchecker.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.dxuser.passwordstrengthchecker.R;

public class MainActivity extends AppCompatActivity {
    private EditText mInputPassword;
    private ProgressBar mProgressBar;
    String[] partialRegexChecks = { ".*[a-z]+.*", // lower
            ".*[A-Z]+.*", // upper
            ".*[\\d]+.*", // digits
            ".*[@#$%]+.*" // symbols
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputPassword = (EditText) findViewById(R.id.password_field);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        keyChangeListener();
    }

    private void keyChangeListener() {
        mInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int strength = passwordStrengthCalculator(s.toString());
                String color = "#00000000";
                switch (strength) {
                    case 25:
                        color = "#E01414";
                        break;
                    case 50:
                        color = "#E09115";
                        break;
                    case 75:
                        color = "#6EAD09";
                        break;
                    case 100:
                        color = "#008CDD";
                        break;
                }

                if (strength >= 0) {
                    mProgressBar.setProgress(strength);
                    Rect bounds = mProgressBar.getProgressDrawable().getBounds();
                    Drawable progressDrawable = new ColorDrawable(Color.parseColor(color));
                    mProgressBar.setProgressDrawable(progressDrawable);
                    mProgressBar.getProgressDrawable().setBounds(bounds);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int passwordStrengthCalculator(String password) {
        int strengthPercentage=0;

        if (password.matches(partialRegexChecks[0])) {
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[1])) {
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[2])) {
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[3])) {
            strengthPercentage+=25;
        }
        return strengthPercentage;
    }
}
