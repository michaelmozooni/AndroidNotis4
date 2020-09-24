package com.example.androidnotis3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ScheduledExecutorService scheduler;
    private NotificationHandler nHandler;

    //Defines how many seconds the thread will be delayed before starting.
    private static final int THREAD_DELAY_START = 5;
    //Defines the interval in seconds with which the runnable should be executed.
    private static final int THREAD_DELAY_INTERVAL = 2;
    //Defines how many threads are created in the thread pool
    private static final int THREAD_SIZE = 1;


    /*GUi items for example */

     private Switch flipSwitch;
     private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nHandler = new NotificationHandler(this);

        //Creates a thread with a total of THREAD_SIZE threads
        scheduler = Executors.newScheduledThreadPool(THREAD_SIZE);

        flipSwitch = findViewById(R.id.flipSwitch);
        textView = findViewById(R.id.textView);


        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTask();
            }
        });



    }

    //Starts running background thread
    public void startTask()
    {
        final ScheduledFuture<?> notificationer = scheduler.scheduleAtFixedRate(checkStatusOfButton, THREAD_DELAY_START, THREAD_DELAY_INTERVAL, TimeUnit.SECONDS);
    }


    final Runnable checkStatusOfButton = new Runnable(){
        public void run() {
            //When the switch is flipped to the right
            if(flipSwitch.isChecked())
            {
                System.out.println("Switch is currently checked");
                textView.setText("Checked");
                //Sends a notification via nHandler
                nHandler.sendNotification("Switch status", "Switch is currenly checked");

            //When th switch is flipped to the left
            } else if (!flipSwitch.isChecked())
            {
                System.out.println("Switch is currently NOT checked");
                textView.setText("Not checked");
            }

        }
    };



}