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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //TextView timeLeft;
    TextView tvv;
    Uri notification;
    Ringtone r;
    boolean clicked = false;
    EditText minutes;
    EditText seconds;
    TextView elapsed;
    long elapsedTime = 0;
    long startTime = 0;
    Spinner spinner;
    Object alarmType;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        alarmType = parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void repeatTimer(int numMins, int numSecs) {
        final int milliseconds = numMins * 60000 + numSecs*1000;
        final int mins = numMins;
        final int secs = numSecs;

        new CountDownTimer(milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {

                double minutesLeft = Math.floor(millisUntilFinished / 60000);
                double secondsLeft = (millisUntilFinished / 1000)-minutesLeft*60;

                elapsed = (TextView) findViewById(R.id.elapsed);
                elapsedTime = System.currentTimeMillis() - startTime;
                minutes = (EditText) findViewById(R.id.minutes);
                seconds = (EditText) findViewById(R.id.seconds);

                double minsElapsed = Math.floor(elapsedTime / 60000);
                double secsElapsed = (elapsedTime / 1000)-minsElapsed*60;

                String secsElapsedTxt = (secsElapsed+"").split("\\.")[0];
                if(secsElapsedTxt.length() == 1) {
                    secsElapsedTxt = "0"+secsElapsedTxt;
                }
                elapsed.setText((minsElapsed+"").split("\\.")[0]+":" + secsElapsedTxt);

                //displaying int instead of double
                minutes.setText((minutesLeft+"").split("\\.")[0]);
                seconds.setText((secondsLeft+"").split("\\.")[0]);

                if (millisUntilFinished < milliseconds - 1000) {
                    r.stop();
                }
                if(!clicked) {
                    r.stop();
                    tvv = (TextView) findViewById(R.id.tvv);
                    tvv.setText("Time Chosen: ");
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
            startTime = System.currentTimeMillis();
            minutes = (EditText) findViewById(R.id.minutes);
            seconds = (EditText) findViewById(R.id.seconds);
            tvv = (TextView) findViewById(R.id.tvv);

            System.out.println(alarmType.toString());

            switch(alarmType.toString()) {
                case "ALARM":
                    notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    break;
                case "NOTIFICATION":
                    notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    break;
                case "ALL":
                    notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
                    break;
                case "RINGTONE":
                    notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    break;
            }

            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            int minValue = Integer.parseInt(minutes.getText().toString());
            int secValue = Integer.parseInt(seconds.getText().toString());

            tvv.setText("Time Chosen: "+minValue + "min " + secValue + "sec");
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
        elapsed = (TextView) findViewById(R.id.elapsed);
        spinner = (Spinner)  findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_sounds, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        elapsed.setText(""+elapsedTime);
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
