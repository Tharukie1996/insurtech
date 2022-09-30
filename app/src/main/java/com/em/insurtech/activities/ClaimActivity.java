package com.em.insurtech.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClaimActivity extends AppCompatActivity {

    Claim claim = new Claim();

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

//    OfflineSQLiteDBHelper offlineSQLiteDBHelper = new OfflineSQLiteDBHelper(null);

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

        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
//        FloatingActionButton camButton = (FloatingActionButton)this.findViewById(R.id.floatingActionButton);
    }

    String currentPhotoPath;

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photo.reconfigure(70, 120, Bitmap.Config.ARGB_8888);
            imageView.setImageBitmap(photo);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveToDB() {
        SQLiteDatabase database = new OfflineSQLiteDBHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_NAME, claim.getUserName());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_AMOUNT, claim.getAmount().intValue());
        values.put(OfflineSQLiteDBHelper.CLAIM_COLUMN_DEPENDENT, claim.getDependentName());
        long newRowId = database.insert(OfflineSQLiteDBHelper.CLAIM_TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    public void submitbuttonHandler(View view) {
        //Decide what happens when the user clicks the submit button
        System.out.println();
    }



}