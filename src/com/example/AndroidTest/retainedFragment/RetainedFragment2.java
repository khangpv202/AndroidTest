package com.example.AndroidTest.retainedFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.AndroidTest.R;

/**
 * Created by khangpv on 10/23/2014.
 */
public class RetainedFragment2 extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_retained_fragment2, null);
    }
}
