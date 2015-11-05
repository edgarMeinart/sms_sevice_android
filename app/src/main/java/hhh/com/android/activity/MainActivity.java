package hhh.com.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hhh.com.android.R;
import hhh.com.android.service.SmsService;

/**
 * Created by konstantin.bogdanov on 05.11.2015.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static Pattern pattern = Pattern.compile("[a-zA-Z]");

    private EditText addressTxt;
    private EditText portTxt;
    private Button startBtn;
    private Button stopBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressTxt = (EditText) findViewById(R.id.addressText);
        portTxt = (EditText) findViewById(R.id.portText);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                onStartClick();
                return;
            case R.id.stopBtn:
                onStopClick();
                return;
        }
    }

    private void onStopClick() {
        Intent intent = new Intent(getApplicationContext(), SmsService.class);
        stopService(intent);
    }

    private void onStartClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String address = addressTxt.getText().toString();
                String ip = null;
                Matcher matcher = pattern.matcher(address);
                if (matcher.find()) {
                    try {
                        ip = InetAddress.getByName(address).getHostAddress();
                    } catch (UnknownHostException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Unknown host: " + address, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    ip = address;
                }
                if (ip != null) {
                    Intent intent = new Intent(getApplicationContext(), SmsService.class);
                    intent.putExtra(SmsService.ADDRESS, address);
                    intent.putExtra(SmsService.PORT, Long.valueOf(portTxt.getText().toString()));
                    startService(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Unknown host: " + address, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
