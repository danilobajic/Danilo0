package com.example.mcg1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Meni extends AppCompatActivity {

    String username;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meni);
        username = getIntent().getStringExtra("user");
        context = this;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("ODJAVA")
                .setMessage("Zelite da se odjavite?")
                .setNegativeButton("NE", null)
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(context, MainActivity.class);
                        startActivity(a);
                    }
                }).create().show();
    }


    public void Inventar(View view){
        Intent intent = new Intent(this, Raf.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    public void WMS(View view){
        Intent intent = new Intent(this, Wms.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    public void Premestanje(View view){
        Intent intent = new Intent(this, Premestanje.class);
        intent.putExtra("user", username);
        intent.putExtra("code", "-1");
        //intent.putExtra("sta", "1");

        startActivity(intent);
    }

    public void Transfer(View view){
        Intent intent = new Intent(this, Transfer.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    public void Aktivnosti(View view){
        Intent intent = new Intent(this, Aktivnosti.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }
}