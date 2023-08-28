package com.example.mcg1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Raf extends AppCompatActivity {

    public static TextView tv;
    EditText et1;
    Button scan_btn, potvrdi_btn, potvrdi_btn2;
    String username;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raf);
        username = getIntent().getStringExtra("user");
        tv = findViewById(R.id.tv2);
        et1 = findViewById(R.id.et1);
        scan_btn = findViewById(R.id.skeniraj_btn);
        scan_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        potvrdi_btn = findViewById(R.id.potvrdi1);
        potvrdi_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        potvrdi_btn2 = findViewById(R.id.potvrdi2);
        potvrdi_btn2.setBackgroundColor(Color.rgb(0, 54, 125));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCode2.class));
            }
        });
    }

    public void Potvrdi(View view){
        Intent intent = new Intent(this, Inventar.class);
        intent.putExtra("raf", tv.getText().toString());
        intent.putExtra("user", username);
        startActivity(intent);
    }
    public void Potvrdi2(View view){
        if (TextUtils.isEmpty(et1.getText().toString()))
        {
            Toast.makeText(Raf.this,
                    "Unesite raf!",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, Inventar.class);
            intent.putExtra("raf", et1.getText().toString());
            intent.putExtra("user", username);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("IZLAZ")
                .setMessage("Vratite se na meni?")
                .setNegativeButton("NE", null)
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(context, Meni.class);
                        a.putExtra("user", username);
                        startActivity(a);
                    }
                }).create().show();
    }


}