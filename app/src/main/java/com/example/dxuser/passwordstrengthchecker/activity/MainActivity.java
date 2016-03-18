package com.example.dxuser.passwordstrengthchecker.activity;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxuser.passwordstrengthchecker.R;



public class MainActivity extends AppCompatActivity {
    private EditText mInputPassword;
    private ImageView mMeterExcellent, mMeterGood, mMeterOk, mMeterPoor;
    private TextView strengthRange;
    private Button mSubmitBtn;
    private String[] partialRegexChecks = { ".*[a-z]+.*", // lower
            ".*[A-Z]+.*", // upper
            ".*[\\d]+.*", // digits
            ".*[@#$%]+.*" // symbols
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputPassword = (EditText) findViewById(R.id.password_field);
        mMeterExcellent = (ImageView) findViewById(R.id.excellent);
        mMeterGood = (ImageView) findViewById(R.id.good);
        mMeterOk = (ImageView) findViewById(R.id.ok);
        mMeterPoor = (ImageView) findViewById(R.id.poor);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        strengthRange = (TextView) findViewById(R.id.strength_range);
        keyChangeListener();
    }

    private void keyChangeListener() {
        mInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    showMeter(R.color.white);
                }
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mInputPassword.getText().toString();
                int length = password.length();
                if (length < 8) {
                    mInputPassword.setError(getApplicationContext().getResources().getString(R.string.min_length_error));
                    strengthRange.setVisibility(View.INVISIBLE);
                }
                calculatePasswordStrength(password);
            }
        });
    }

    private void calculatePasswordStrength(String password) {
        if (password.length() > 8) {
            int strength = passwordStrengthCalculator(password);
            strengthRange.setVisibility(View.VISIBLE);
            switch (strength) {
                case 25:
                    showMeter(R.drawable.poor_circle);
                    strengthRange.setText(this.getResources().getString(R.string.weak));
                    strengthRange.setTextColor(ContextCompat.getColor(this, R.color.poor));
                    break;
                case 50:
                    showMeter(R.drawable.ok_circle);
                    strengthRange.setText(this.getResources().getString(R.string.ok));
                    strengthRange.setTextColor(ContextCompat.getColor(this, R.color.ok));
                    break;
                case 75:
                    showMeter(R.drawable.good_circle);
                    strengthRange.setText(this.getResources().getString(R.string.good));
                    strengthRange.setTextColor(ContextCompat.getColor(this, R.color.good));
                    break;
                case 100:
                    showMeter(R.drawable.excellent_circle);
                    strengthRange.setText(this.getResources().getString(R.string.excellent));
                    strengthRange.setTextColor(ContextCompat.getColor(this, R.color.excellent));
                    break;
            }
        } else {
            strengthRange.setVisibility(View.INVISIBLE);
        }
    }

    private void showMeter(int circle) {
        mMeterPoor.setBackground(ContextCompat.getDrawable(this, circle));
        mMeterOk.setBackground(ContextCompat.getDrawable(this, circle));
        mMeterGood.setBackground(ContextCompat.getDrawable(this, circle));
        mMeterExcellent.setBackground(ContextCompat.getDrawable(this, circle));
    }

    private int passwordStrengthCalculator(String password) {
        int strengthPercentage=0;
        String[] patterns = this.getResources().getStringArray(R.array.patterns);

        if (password.matches(patterns[0])) {
            strengthPercentage+=25;
        }
        if (password.matches(patterns[1])) {
            strengthPercentage+=25;
        }
        if (password.matches(patterns[2])) {
            strengthPercentage+=25;
        }
        if (password.matches(patterns[3])) {
            strengthPercentage+=25;
        }
        return strengthPercentage;
    }
}
