package lab.tm.rogalski.lab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends Activity {
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        retrieveIntentData();
    }

    private void retrieveIntentData() {
        Intent intent = getIntent();
        TextView target = (TextView) findViewById(R.id.TextDestination);
        target.setText(intent.getStringExtra("data"));
    }

    private void showPicture() {
        if (mCurrentPhotoPath == null)
            return;
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back button disabled. Use a button above.", Toast.LENGTH_SHORT).show();
        // super.onBackPressed();
    }

    public void onBtnClick(View v) {
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }

    public void onTakePhotoBtnClick(View v) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Camera not detected.", Toast.LENGTH_SHORT).show();
        }

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.d("TAG", Log.getStackTraceString(ex));
            Toast.makeText(this, "Failed to create image file.", Toast.LENGTH_SHORT).show();
        }

        if (photoFile != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (isExternalStorageWritable() && !storageDir.isDirectory()) {
            storageDir.mkdirs();
        }

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showPicture();
    }
}