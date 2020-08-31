package com.example.gorevyoneticisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gorevyoneticisi.adapter.listAdapter;
import com.example.gorevyoneticisi.database.DatabaseConnection;
import com.example.gorevyoneticisi.entity.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static DatabaseConnection con;
    List<tasks> tasksList = new ArrayList<tasks>();
    ListView listView;
    static Button btn_aylikplan;
    static Button btn_haftalikplan;
    static Button btn_gunlukplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Ana Sayfa - Görevlerini Yönet");
        // Tanımlamalar
        con = new DatabaseConnection(getApplicationContext());
        listView = findViewById(R.id.list_view_1);
        btn_aylikplan = findViewById(R.id.btn_aylikplan);
        btn_haftalikplan = findViewById(R.id.btn_haftalikplan);
        btn_gunlukplan = findViewById(R.id.btn_gunlukplan);

        // listview dolduruluyor...
        tasksList = con.plans(0); // listeye verileri aktarıyoruz
        Collections.reverse(tasksList); // listeyi en son eklenenden ilk eklenene doğru sıralatıyoruz.
        listAdapter adapter = new listAdapter(this, tasksList);
        listView.setAdapter(adapter);

        statistics(); // ana sayfada ki istatistikleri günceller
    }

    public static void statistics() {
        btn_gunlukplan.setText("Günlük Plan" + System.getProperty("line.separator") + System.getProperty("line.separator") + con.getRowCount("Günlük", 0));
        btn_haftalikplan.setText("Haftalık Plan" + System.getProperty("line.separator") + System.getProperty("line.separator") + con.getRowCount("Haftalık", 0));
        btn_aylikplan.setText("Aylık Plan" + System.getProperty("line.separator") + System.getProperty("line.separator") + con.getRowCount("Aylık", 0));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_about: {
                Toast.makeText(this, "Bu uygulama Piton Ar-Ge ve Yazılım firmasının isteği üzerine Hakan AKKAYA tarafından tasarlanmıştır.", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.navigation_add: {
                Intent intent = new Intent(MainActivity.this, add.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navgation_menu, menu);
        return true;
    }
}