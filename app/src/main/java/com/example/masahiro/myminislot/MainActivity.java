package com.example.masahiro.myminislot;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //起動時にデータをクリアする
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        editor.clear();
        editor.commit();

        Button betUp = (Button)findViewById(R.id.up_button);
        betUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                editData(1);
            }
        });

        Button betDown = (Button)findViewById(R.id.down_button);
        betDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editData(2);
            }
        });

        Button start = (Button)findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(MainActivity.this,GameActivity.class),REQUEST_CODE);
            }
        });

        Button reset = (Button)findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editor.clear();
                editor.commit();
                TextView hav = (TextView)findViewById(R.id.Coin);
                int j = pref.getInt("HAD_COINS",1000);
                String s = String.valueOf(j);
                hav.setText(s);
            }
        });
    }

    public void editData(int num){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        int defaultHadCoins = pref.getInt("DEFAULT_HAD", 1000);
        int defaultBetCoincs = pref.getInt("DEFAULT_BET", 10);
        int hadCoins = pref.getInt("HAD_COINS", defaultHadCoins);
        int betCoins = pref.getInt("BET_COINS", defaultBetCoincs);

        switch(num){
            case 1:
                TextView betText = (TextView) findViewById(R.id.BetCoin);
                if(betCoins < hadCoins) {
                    betCoins += 10;
                    betText.setText(String.valueOf(betCoins));
                    editor.putInt("BET_COINS", betCoins);
                }
                break;

            case 2:
                TextView betText2 = (TextView) findViewById(R.id.BetCoin);
                if(betCoins > 10) {
                    betCoins -= 10;
                    betText2.setText(String.valueOf(betCoins));
                    editor.putInt("BET_COINS", betCoins);
                }
                break;
        }

        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //SecondActivityから戻ってきた場合
            case (REQUEST_CODE):
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra("COINS");
                    TextView hav = (TextView)findViewById(R.id.Coin);
                    hav.setText(result);
                }
                break;
        }
    }
}
