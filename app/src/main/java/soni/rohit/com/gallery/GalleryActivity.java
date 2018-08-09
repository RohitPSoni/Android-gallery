package soni.rohit.com.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        listView = findViewById(R.id.imageList);
        listView.setLayoutManager(new GridLayoutManager(this, 4));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.READ_EXTERNAL_STORAGE);
        } else {
            loadData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadData();
                } else {
                    finish();
                }
                break;
        }
    }

    private void loadData(){
        new LoadImages().execute();
    }

    class LoadImages extends AsyncTask<Void,Void, ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Uri uri;
            Cursor cursor;
            int indexData;
            ArrayList<String> listOfAllImages = new ArrayList<>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = getContentResolver().query(uri, projection, null,
                    null, null);

            indexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(indexData);

                listOfAllImages.add(absolutePathOfImage);
            }


            return listOfAllImages;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            ImageViewAdapter imageViewAdapter = new ImageViewAdapter(strings, GalleryActivity.this);
            listView.setAdapter(imageViewAdapter);
            listView.addItemDecoration(new DividerItemDecoration(GalleryActivity.this,DividerItemDecoration.HORIZONTAL));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.camera:
//                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
