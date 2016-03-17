package com.example.dxuser.passwordstrengthchecker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dxuser.passwordstrengthchecker.R;

public class MainActivity extends AppCompatActivity {
    private EditText mInputPassword;
    private ImageView mMeterExcellent, mMeterGood, mMeterOk, mMeterPoor;
    private Button mSubmitBtn;
    int lowercaseCount = 0;
    int minLengthCount = 0;
    int uppercaseCount = 0;
    int numberCount = 0;
    int symbolCount = 0;
    int combinationCount = 0;
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
        keyChangeListener();
    }

    private void keyChangeListener() {
        mInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString(), count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                int whiteColor = R.color.white;
                if (s.length() < 8) {
                    showMeter(whiteColor, whiteColor, whiteColor, whiteColor);
                }
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mInputPassword.getText().toString();
                int length = password.length();
                if (length < 8) {
                    mInputPassword.setError(getResources().getString(R.string.min_length_error));
                }
                calculatePasswordStrength(password, length);
            }
        });

        mInputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_CLEAR){
                    //this is for backspace
                    lowercaseCount = 0;
                    minLengthCount = 0;
                    uppercaseCount = 0;
                    numberCount = 0;
                    symbolCount = 0;
                    combinationCount = 0;
                }
                return false;
            }
        });
    }

    private void calculatePasswordStrength(String password, int count) {
        if (count > 8) {
            int strength = passwordStrengthCalculator(password);
            int totalScore = ((minLengthCount * 3) + (uppercaseCount * 4)
                    + (numberCount * 5) + (symbolCount * 5) + (combinationCount * 7));
            if (totalScore < 26) {
                setStrengthAmount(R.drawable.poor_circle, strength);
            } else if (totalScore < 51) {
                setStrengthAmount(R.drawable.ok_circle, strength);
            } else if (totalScore < 76) {
                setStrengthAmount(R.drawable.good_circle, strength);
            } if (totalScore > 76) {
                setStrengthAmount(R.drawable.excellent_circle, strength);
            }
        }
    }

    private void setStrengthAmount(int color, int score) {
        int whiteColor = R.color.white;
        switch (score) {
            case 25:
                showMeter(color, whiteColor, whiteColor, whiteColor);
                break;
            case 50:
                showMeter(color, color, whiteColor, whiteColor);
                break;
            case 75:
                showMeter(color, color, color, whiteColor);
                break;
            case 100:
                showMeter(color, color, color, color);
                break;
        }
    }

    private void showMeter(int poorColor, int okColor, int goodColor, int excellentColor) {
        mMeterPoor.setBackgroundResource(poorColor);
        mMeterOk.setBackgroundResource(okColor);
        mMeterGood.setBackgroundResource(goodColor);
        mMeterExcellent.setBackgroundResource(excellentColor);
    }

    private int passwordStrengthCalculator(String password) {
        int strengthPercentage=0;

        minLengthCount = (password.length())/ 8;
        if (password.matches(partialRegexChecks[0])) {
            if (lowercaseCount == 0)    combinationCount ++;
            lowercaseCount ++;
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[1])) {
            if (uppercaseCount == 0)    combinationCount ++;
            uppercaseCount ++;
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[2])) {
            if (numberCount == 0)   combinationCount ++;
            numberCount ++;
            strengthPercentage+=25;
        }
        if (password.matches(partialRegexChecks[3])) {
            if (symbolCount == 0)   combinationCount ++;
            symbolCount ++;
            strengthPercentage+=25;
        }
        return strengthPercentage;
    }
}
