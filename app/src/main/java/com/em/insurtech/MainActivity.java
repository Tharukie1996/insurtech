package com.em.insurtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.em.insurtech.activities.ClaimActivity;
import com.em.insurtech.activities.DashboardActivity;
import com.em.insurtech.activities.HistoryActivity;
import com.em.insurtech.activities.ProfileActivity;
import com.em.insurtech.domain.Claim;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.company_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void goToDashboardActivity(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}