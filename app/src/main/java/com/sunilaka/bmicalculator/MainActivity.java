package com.sunilaka.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {

    private EditText w, h;
    private RadioGroup heightUnit, weightUnit;
    private  RadioButton radioHeight, radioWeight;
    private Button calBMI;
    private TextView res, comment, knowmore, wforh;
    private Double weight, height, bmi, minWeight, maxWeight;
    private RelativeLayout relativeLayout;
    private ImageView img01, img02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        w = (EditText) findViewById(R.id.weight);
        h = (EditText) findViewById(R.id.height);
        heightUnit = (RadioGroup) findViewById(R.id.heightUnit);
        weightUnit = (RadioGroup) findViewById(R.id.weightUnit);
        calBMI = (Button) findViewById(R.id.calbmi);
        res = (TextView) findViewById(R.id.result);
        comment = (TextView) findViewById(R.id.comment);
        knowmore = (TextView) findViewById(R.id.knowmore);
        wforh = (TextView) findViewById(R.id.wforh);
        img01 = (ImageView) findViewById(R.id.img01);
        img02 = (ImageView) findViewById(R.id.img02);

        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9889082375891050/5866025187");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        calBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (w.getText().toString().equals("") && h.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "You cannot keep empty fields", Toast.LENGTH_SHORT).show();
                }else if (w.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter your weight", Toast.LENGTH_SHORT).show();
                }else if (h.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter your height", Toast.LENGTH_SHORT).show();
                }else if (heightUnit.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Select centimeters or feets", Toast.LENGTH_SHORT).show();
                }else if (weightUnit.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Select kilograms or pounds", Toast.LENGTH_SHORT).show();
                } else{
                    calculateBMI();
                    hideKeyboard();
                    showCharts();

                    // Show Add
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    public void calculateBMI(){
        weight = Double.parseDouble(w.getText().toString());
        height = Double.parseDouble(h.getText().toString());

        int hUnit = heightUnit.getCheckedRadioButtonId();
        radioHeight = (RadioButton) findViewById(hUnit);

        int wUnit = weightUnit.getCheckedRadioButtonId();
        radioWeight = (RadioButton) findViewById(wUnit);

        if (radioWeight.getText().toString().equals("lb")){
            weight /= 2.205;
        }

        if (radioHeight.getText().toString().equals("ft")) {
            height /= 3.281;
        }else{
            height /= 100.0;
        }

        Double temp = weight / (height * height);
        bmi = Math.round(temp * 100.0)/100.0;
        res.setText("Your BMI : " + bmi.toString());

        minWeight = Math.round((18.5 * height * height)*100.0) / 100.0;
        maxWeight = Math.round((24.9 * height * height)*100.0) / 100.0;

        if (bmi < 18.5){
            comment.setText("Underweight");
            comment.setTextColor(Color.parseColor("#0000AA"));
        }else if(bmi >= 18.5 && bmi <= 24.9){
            comment.setText("Healthy");
            comment.setTextColor(Color.parseColor("#00AA00"));
        }else if(bmi >= 25 && bmi <= 29.9){
            comment.setText("Overweight");
            comment.setTextColor(Color.parseColor("#C4C231"));
        }else if(bmi >= 30 && bmi <= 34.9){
            comment.setText("Obese");
            comment.setTextColor(Color.parseColor("#F5923B"));
        }else if (bmi >= 35){
            comment.setText("Extremely Obese");
            comment.setTextColor(Color.parseColor("#FF0F0F"));
        }

        if (radioWeight.getText().toString().equals("kg")){
            wforh.setText("For your height, a normal weight range would be from " + minWeight + " to " + maxWeight + " kilograms. ");
        }else {
            wforh.setText("For your height, a normal weight range would be from " + Math.round(minWeight*2.205*100.0)/100.0 + " to " + Math.round(maxWeight*2.205*100.0)/100.0 + " pounds. ");
        }

        knowmore.setText("Refer Following Charts");
    }

    public void hideKeyboard(){
        // Hide keyboard on button click
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(relativeLayout.getWindowToken(), 0);
    }

    public void showCharts(){
        img01.setImageResource(R.mipmap.img01);
        img02.setImageResource(R.mipmap.img02);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clr:
                w.setText("");
                h.setText("");
                res.setText("");
                comment.setText("");
                wforh.setText("");
                return true;

            case R.id.info:
                startActivity(new Intent("com.sunilaka.bmicalculator.APP_INFO"));
                return true;

            case R.id.help:
                startActivity(new Intent("com.sunilaka.bmicalculator.HELP_MENU"));
                return true;

            case R.id.why:
                startActivity(new Intent("com.sunilaka.bmicalculator.WHY_BMI"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
