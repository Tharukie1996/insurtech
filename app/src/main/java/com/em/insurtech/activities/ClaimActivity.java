package com.em.insurtech.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.em.insurtech.R;
import com.em.insurtech.domain.Claim;
import com.em.insurtech.repository.OfflineSQLiteDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClaimActivity extends AppCompatActivity {

    Claim claim = new Claim();

    private static final int CAMERA_REQUEST = 102;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 101;
    String imageFileName = "test.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText nameEditText = (EditText) findViewById(R.id.editTextTextPersonName);
        claim.setUserName(nameEditText.getText().toString());

        EditText amountTextEdit = (EditText) findViewById(R.id.amount);

        int amt;
        if(amountTextEdit.getText().toString().isEmpty()) {
            amt = 0;
        }
        else {
            amt = Integer.parseInt(amountTextEdit.getText().toString());
        }
        BigDecimal amount = new BigDecimal(amt);
        claim.setAmount(amount);

        claim.setImageUri(imageFileName);

        this.imageView = (ImageView)this.findViewById(R.id.displayImageView);
    }

    String currentPhotoPath;

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        createImageFile();
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPhotoPath = image.getAbsolutePath();
        claim.setImageUri(imageFileName);
        return image;
    }

    private void saveToDB() {
        SQLiteDatabase database = new OfflineSQLiteDBHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_NAME, claim.getUserName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_IMAGE_URI, claim.getUserName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_AMOUNT, claim.getAmount().intValue());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_DEPENDENT, claim.getDependentName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_STATUS, "SUBMITTED");
        long newRowId = database.insert(OfflineSQLiteDBHelper.CLAIM_TABLE_NAME, null, values);

        Toast.makeText(this, "Claim requested", Toast.LENGTH_LONG).show();
    }

    private void saveToDBOffline() {
        SQLiteDatabase database = new OfflineSQLiteDBHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_NAME, claim.getUserName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_IMAGE_URI, claim.getUserName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_AMOUNT, claim.getAmount().intValue());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_DEPENDENT, claim.getDependentName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_STATUS, "RETRY");
        long newRowId = database.insert(OfflineSQLiteDBHelper.CLAIM_TABLE_NAME, null, values);

        Toast.makeText(this, "Network Error. Please try again later", Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public boolean checkActiveInternetConnection() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
        } else {
            System.out.println("No network available");
        }
        return false;
    }

    public void submitbuttonHandler(View view) {
        if (checkActiveInternetConnection()) {
            //Call backend service. if success,
            saveToDB();
        }
        else {
            saveToDBOffline();
        }
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}