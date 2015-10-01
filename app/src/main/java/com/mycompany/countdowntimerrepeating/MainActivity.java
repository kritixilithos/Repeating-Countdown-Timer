package com.mycompany.countdowntimerrepeating;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timeLeft;
    TextView tvv;
    Uri notification;
    Ringtone r;
    boolean clicked = false;
    EditText minutes;

    public void repeatTimer(double numMins) {
        final double milliseconds = numMins * 60000;
        final double mins = numMins;

        timeLeft = (TextView) findViewById(R.id.timeLeft);
        new CountDownTimer((int) milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {

                timeLeft.setText("seconds remaining: " + millisUntilFinished / 1000);
                if (millisUntilFinished < milliseconds - 1000) {
                    r.stop();
                }
                if(!clicked) {
                    r.stop();
                    timeLeft.setText(" ");
                    tvv = (TextView) findViewById(R.id.tvv);
                    tvv.setText("");
                    this.cancel();
                }
            }

            public void onFinish() {
                r.play();
                repeatTimer(mins);
            }
        }.start();
    }


    public void startRepeater(View view) {
        if(!clicked) {
            minutes = (EditText) findViewById(R.id.minutes);
            tvv = (TextView) findViewById(R.id.tvv);

            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            double numValue = Double.parseDouble(minutes.getText().toString());

            tvv.setText(numValue + "");
            repeatTimer(numValue);
        }
        clicked = true;
    }

    public void stopRepeater(View view) {
        clicked = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
