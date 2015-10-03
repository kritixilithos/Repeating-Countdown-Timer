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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timeLeft;
    TextView tvv;
    Uri notification;
    Ringtone r;
    boolean clicked = false;
    EditText minutes;
    EditText seconds;

    public void repeatTimer(int numMins, int numSecs) {
        final int milliseconds = numMins * 60000 + numSecs*1000;
        final int mins = numMins;
        final int secs = numSecs;

        timeLeft = (TextView) findViewById(R.id.timeLeft);
        new CountDownTimer(milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {

                double minutesLeft = Math.floor(millisUntilFinished / 60000);
                double secondsLeft = (millisUntilFinished / 1000)-minutesLeft*60;

                timeLeft.setText("Time remaining: " + minutesLeft + "min " + secondsLeft +"sec");
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
                repeatTimer(mins, secs);
            }
        }.start();
    }


    public void startRepeater(View view) {
        if(!clicked) {
            minutes = (EditText) findViewById(R.id.minutes);
            seconds = (EditText) findViewById(R.id.seconds);
            tvv = (TextView) findViewById(R.id.tvv);

            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            int minValue = Integer.parseInt(minutes.getText().toString());
            int secValue = Integer.parseInt(seconds.getText().toString());

            tvv.setText(minValue + "min " + secValue + "sec");
            repeatTimer(minValue, secValue);
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        minutes = (EditText) findViewById(R.id.minutes);
        seconds = (EditText) findViewById(R.id.seconds);
        minutes.setText("0");
        seconds.setText("0");
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
