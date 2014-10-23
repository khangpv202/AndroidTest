package com.example.AndroidTest.retainedFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import com.example.AndroidTest.R;

/**
 * Created by khangpv on 10/23/2014.
 */
public class TestRetainedFragment extends FragmentActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_retained_activity);
        Button button1 = (Button) findViewById(R.id.btOk1);
        Button button2 = (Button) findViewById(R.id.btOk2);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RetainedFragment1 retainedFragment1 = new RetainedFragment1();
                retainedFragment1.setRetainInstance(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,retainedFragment1).commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RetainedFragment2 retainedFragment2 = new RetainedFragment2();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,retainedFragment2).commit();
            }
        });
    }
}
