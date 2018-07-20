package vn.com.example.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import vn.com.example.demoasyntask.R;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new Splash().execute();
    }

    private void initView() {
        mProgessLoading = findViewById(R.id.progress_bar);
    }

    private class Splash extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //todo in background
            long minisPerProges = 25;
            int progess = 0;
            while (progess <= 100) {
                progess++;
                publishProgress(progess); //push progess number to UI
                try {
                    Thread.sleep(minisPerProges);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //todo when asyntask running
            super.onProgressUpdate(values);
            mProgessLoading.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //todo asyntask done
            super.onPostExecute(aVoid);
            Intent intent = new Intent(MainActivity.this, LoadImageActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
