package com.iitr.kaishu.khotspotstarter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;


public class  MainActivity extends AppCompatActivity {
Switch abcd;
    EditText second;
    SharedPreferences.Editor changest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abcd = (Switch) findViewById(R.id.activestate);
        second = (EditText) findViewById(R.id.second);
        init1();
        init2();
        changest = getSharedPreferences("switch", MODE_PRIVATE).edit();
        abcd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(abcd.isChecked()){
                    abcd.setText("Enabled");
                    changest.putBoolean("one",true);
                    changest.commit();
                }
                else{
                    abcd.setText("Disabled");
                    changest.putBoolean("one",false);
                    changest.commit();

                }
            }
        });
        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2==17){
                    changest.putString("mac",charSequence.toString());
                    changest.commit();
                }
                if(i2==0){
                    changest.putString("mac","");
                    changest.commit();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    public void init1(){
        SharedPreferences state = this.getSharedPreferences("switch", MODE_PRIVATE);
        if(state.getBoolean("one" , false)){
            abcd.setText("Enabled");
            abcd.setChecked(true);
        }
        else{abcd.setText("Disabled");
            abcd.setChecked(false);}
    }
    public void init2(){
        SharedPreferences mac = this.getSharedPreferences("switch",MODE_PRIVATE);
        String fill = mac.getString("mac","");
        if(!fill.equals("")){second.setText(fill);}
    }

}
