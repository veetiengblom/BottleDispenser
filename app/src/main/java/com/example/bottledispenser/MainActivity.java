package com.example.bottledispenser;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView outputText;
    TextView textMoneyAmount;
    int bottles;
    float money;
    ArrayList<Bottle> bottle_array;
    SeekBar seekBar;
    Spinner spinner;
    int bottleChoice;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textMoneyAmount = (TextView) findViewById(R.id.textView2);
        textMoneyAmount.setText("Click 'ADD MONEY' to add: ");
        outputText = findViewById(R.id.outputTextview);
        seekBar = (SeekBar) findViewById(R.id.seekBarID);
        spinner = (Spinner) findViewById(R.id.spinnerID);
        context = MainActivity.this;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Sting.valueof otettu pois i edestä
                textMoneyAmount.setText("Click 'ADD MONEY' to add: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bottles = 5;
        money = 0;
        bottle_array = new ArrayList<Bottle>();
        String n, m;
        double e, s, p, a;

        for (int i = 0; i < bottles; i++) {
            if (i < 2) {
                n = "Pepsi Max";
                m = "Pepsi";
                e = 0.3;
                a = 1;
                if (i < 1) {
                    s = 0.5;
                    p = 1.80;
                } else {
                    s = 1.5;
                    p = 2.20;
                }
            } else if (i < 4) {
                n = "Coca-Cola Zero";
                m = "Coca-Cola";
                e = 0.4;
                a = 1;
                if (i < 3) {
                    s = 0.5;
                    p = 2.00;

                } else {
                    s = 1.5;
                    p = 2.50;
                }
            } else {
                n = "Fanta Zero";
                m = "Coca-Cola";
                e = 0.5;
                s = 0.5;
                p = 1.95;
                a = 1;
            }
            bottle_array.add(i, new Bottle(n, m, e, s, p, a));
        }
        ArrayAdapter<Bottle> adapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, bottle_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bottleChoice = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addMoney(View v) {
        int amountOfMoney;
        amountOfMoney = seekBar.getProgress();
        money += amountOfMoney;
        outputText.setText("Klink! Added more money!");
        seekBar.setProgress(0);
    }

    public void buyBottle(View v) {
        if(bottles == 0) {
            outputText.setText("No more bottles!");
        }
        Bottle b = bottle_array.get(bottleChoice);
        if (bottle_array.get(bottleChoice).getAmount() == 0){
            outputText.setText(("No " + b.getName() + " " + b.getSize() + " availeble!"));
        } else {
            if(money < b.getPrize()) {
                outputText.setText("Add money first!");
            }
            else {
                bottles -= 1;
                money -= b.getPrize();
                outputText.setText("KACHUNK! " + b.getName() + " " + b.getSize() + " came out of the dispenser!");
                bottle_array.get(bottleChoice).setAmount(bottle_array.get(bottleChoice).getAmount()-1);

                try {
                    OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("Receipt.txt", context.MODE_PRIVATE));
                    ows.write("***OUR RECEIPT***\n\n");
                    ows.write("Bottle: " + b.getName() + " " + b.getSize() + ", price: " + b.getPrize() + "€\n\n");
                    ows.write("***THANK YOU FOR DOING BUSINESS WITH US***\n");
                    ows.close();

                } catch (IOException e){
                    Log.e("IOException", "Virhe syötteessä WriteFile");
                }
            }
        }
    }


    public void returnMoney(View v) {
        String moneyText = "Klink klink. Money came out! You got " + money + "€ back";
        outputText.setText(moneyText);
        money = 0;
    }

}

