package com.example.AndroidTest.retainedFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.AndroidTest.R;

/**
 * Created by khangpv on 10/23/2014.
 */
public class RetainedFragment1 extends Fragment
{
    private ProgressDialog loadingDialog;
    private LoadingAsyncTask loadingAsyncTask;
    private Activity activity;
    private boolean isShowProgress;
    EditText editText;
    EditText editText2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_retained_fragment1, null);
        Button showDialog = (Button) view.findViewById(R.id.showDialog);
        editText = (EditText) view.findViewById(R.id.etKeepData);
//        editText2 = (EditText) view.findViewById(R.id.etKeepData2);
        showDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (loadingAsyncTask == null)
                {
                    loadingAsyncTask = new LoadingAsyncTask();
                    loadingAsyncTask.execute();
                }
                else
                {
                    loadingAsyncTask.execute();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Log.e("Activity: ", activity.toString());
        this.activity = activity;
        if (isShowProgress)
        {
            showProgress(activity);
        }
    }

    @Override
    public void onDetach()
    {
        if (loadingDialog != null && loadingDialog.isShowing())
        {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        super.onDetach();
    }

    private void showProgress(Activity activity)
    {
        if (loadingDialog == null)
        {
            loadingDialog = new ProgressDialog(activity);
        }
        loadingDialog.setMessage("Loading...");
        loadingDialog.setCancelable(true);
        Log.d("ID of Loading", loadingDialog.toString());
        isShowProgress = true;
        loadingDialog.show();
    }

    class LoadingAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showProgress(activity);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            for (int i = 1; i <= 10; i++)
            {
                Log.e("GREC", "AsyncTask is working: " + i);
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            RetainedFragment1.this.loadingAsyncTask = null;
            if (loadingDialog != null)
            {
                Log.d("ID of done", loadingDialog.toString());
                if (loadingDialog.isShowing())
                {
                    isShowProgress = false;
                    loadingDialog.dismiss();
                }
            }
        }

    }
}
