package com.example.payobejalan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabTambah;
    private RecyclerView rvDestinasi;
    private MyDatabaseHelper myDB;
    private ArrayList<String> arrId, arrNAma, arrAlamat, arrJam;
    private AdapterDestinasi adDestinasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTambah = findViewById(R.id.fab_tambah);
        rvDestinasi = findViewById(R.id.rv_destinasi);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
    }

    private void SQLLiteToArrayList(){
        Cursor varCursor = myDB.bacaDataDestinasi();
        if(varCursor.getCount() == 0){
            Toast.makeText(this, "Data Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }
        else{
            while (varCursor.moveToNext()){
                arrId.add(varCursor.getString(0));
                arrNAma.add(varCursor.getString(1));
                arrAlamat.add(varCursor.getString(2));
                arrJam.add(varCursor.getString(3));
            }
        }
    }

    private void tampilDestinasi(){
        arrId = new ArrayList<>();
        arrNAma = new ArrayList<>();
        arrAlamat = new ArrayList<>();
        arrJam = new ArrayList<>();

        SQLLiteToArrayList();

        adDestinasi = new AdapterDestinasi(MainActivity.this,arrId,arrNAma, arrAlamat, arrJam);
        rvDestinasi.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvDestinasi.setAdapter(adDestinasi);

    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDestinasi();
    }
}