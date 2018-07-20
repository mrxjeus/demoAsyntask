package vn.com.example.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.com.example.demoasyntask.R;
import vn.com.example.demoasyntask.adapter.ImageAdapter;

public class LoadImageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final int SPAN_COUNT = 2;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final String DOTPNG = ".png";
    private static final String DOTJPG = ".jpg";
    private static final String DOTJPEG = ".jpeg";
    private RecyclerView mRecyclerImage;
    private ImageAdapter mImageAdapter;
    private List<File> mPhotos;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        checkpermission();
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkpermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            new LoadImage().execute();
            return;
        }
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

    }


    private void initView() {
        mRecyclerImage = findViewById(R.id.recycle_image);
        mPhotos = new ArrayList<>();
        mImageAdapter = new ImageAdapter(LoadImageActivity.this, mPhotos);
        GridLayoutManager manager = new GridLayoutManager(LoadImageActivity.this, SPAN_COUNT);
        mRecyclerImage.setLayoutManager(manager);
        mRecyclerImage.setAdapter(mImageAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new LoadImage().execute();
            return;
        }
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    private class LoadImage extends AsyncTask<Void, File, Void> {
        File files = new File(PATH);

        @Override
        protected Void doInBackground(Void... voids) {
            getFile(files);
            return null;
        }

        private void getFile(File dir) {
            File listFile[] = dir.listFiles();
            if (listFile == null || listFile.length <= 0) {
                return;
            }
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else if (listFile[i].getName().endsWith(DOTPNG)
                        || listFile[i].getName().endsWith(DOTJPG)
                        || listFile[i].getName().endsWith(DOTJPEG)) {
                    publishProgress(listFile[i]);
                }
            }
        }

        @Override
        protected void onProgressUpdate(File... values) {
            super.onProgressUpdate(values);
            mPhotos.add(values[0]);
            mImageAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
