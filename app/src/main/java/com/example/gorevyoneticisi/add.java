package com.example.gorevyoneticisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gorevyoneticisi.database.DatabaseConnection;
import com.example.gorevyoneticisi.entity.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add extends AppCompatActivity {
    private String[] typeList = {"Günlük", "Haftalık", "Aylık"};
    Boolean duzenlemeMi = false;
    Spinner spin_type;
    Button btn_cancel, btn_add;
    TextView txt_subject, txt_content;
    DatabaseConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Planlarınıza Yenisini Ekleyin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tanımlamalar
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        txt_subject = findViewById(R.id.txt_subject);
        txt_content = findViewById(R.id.txt_content);
        spin_type = findViewById(R.id.spin_type);
        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancel);
        con = new DatabaseConnection(getApplicationContext());
        //
        ArrayAdapter spinAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_type.setAdapter(spinAdapter);

        // Eğer düzenle butonuna basıldıysa verilerimizi çekelim.
        final tasks temp = new tasks();
        if (getIntent().getIntExtra("id", 0) > 0) {
            temp.setTask_id(getIntent().getExtras().getInt("id"));
            temp.setTask_header(getIntent().getStringExtra("header"));
            temp.setTask_content(getIntent().getStringExtra("content"));
            temp.setTask_type(getIntent().getStringExtra("type"));
            temp.setTask_date(getIntent().getStringExtra("date"));
            temp.setTask_status(getIntent().getExtras().getInt("status"));
            duzenlemeMi = true;
            txt_subject.setText(temp.getTask_header() + "");
            txt_content.setText(temp.getTask_content() + "");
            if (temp.getTask_type().equals("Günlük"))
                spin_type.setSelection(0);
            else if (temp.getTask_type().equals("Haftalık"))
                spin_type.setSelection(1);
            else if (temp.getTask_type().equals("Aylık"))
                spin_type.setSelection(2);
            btn_add.setText("Düzenlemeyi Kaydet");

        }



        /*if (temp.getTask_id() > 0) {

        }*/

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (duzenlemeMi == false) {
                    tasks temp = new tasks();
                    temp.setTask_header(txt_subject.getText().toString());
                    temp.setTask_content(txt_content.getText().toString());
                    temp.setTask_type(spin_type.getSelectedItem().toString());
                    temp.setTask_date(formatter.format(date));
                    con.addPlan(temp);
                    Toast.makeText(add.this, "Plan başarıyla EKLENDİ!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(add.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (duzenlemeMi) {
                    temp.setTask_header(txt_subject.getText().toString());
                    temp.setTask_content(txt_content.getText().toString());
                    temp.setTask_type(spin_type.getSelectedItem().toString());
                    con.editPlan(temp);
                    Toast.makeText(add.this, "Plan başarıyla GÜNCELLENDİ!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(add.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(add.this, "Değişiklikler kayıt EDİLMEDİ.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(add.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(add.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}