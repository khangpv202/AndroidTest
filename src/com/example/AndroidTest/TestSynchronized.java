package com.example.AndroidTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestSynchronized extends Activity
{
    /**
     * Called when the activity is first created.
     */
    private final Object lock = new Object();
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_synchronized);
        Button button1 = (Button) findViewById(R.id.show1);
        Button button2 = (Button) findViewById(R.id.show2);

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setMessage("bla bla");
                    }
                }).start();

            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        editMessage("huong huong");
                    }
                }).start();
            }
        });
    }

    private void setMessage(String message)
    {
        synchronized (lock)
        {
            this.message = message;
            Log.e("message in setMessage: ", message);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String editMessage(String newMessage)
    {
        synchronized (lock)
        {
            this.message = newMessage;
            Log.e("message in editMessage: ", message);
            return message;
        }
    }
}
