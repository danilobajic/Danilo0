package com.example.mcg1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Transfer2 extends AppCompatActivity {

    String username, id;
    Connection con;
    TextView tv_naziv;
    LinearLayout ll;
    Context context;
    Button nazad_btn, obnovi;

    String grupe[] = new String[1000];
    String idjevi[] = new String[1000];
    String naslovi[] = new String[1000];

    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer2);

        username = getIntent().getStringExtra("user");
        id = getIntent().getStringExtra("id");
        tv_naziv = findViewById(R.id.tv_naziv);
        ll = findViewById(R.id.linearlayout2);
        context = this;
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        obnovi = (Button)findViewById(R.id.obnovi_btn);
        obnovi.setBackgroundColor(Color.rgb(0, 54, 125));

        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetTransportOrderItem " + id);
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Nemate ni jedan zadatak.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getBaseContext(), "artikal " + sifra + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("MCGgetTransportOrderItem " + id);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        grupe[i] = rs.getString("Adresa");
                        naslovi[i] = rs.getString("Naziv");
                        idjevi[i] = rs.getString("OznakaKupca");
                        Log.d("grupa", "grupa je " + grupe[i] );
                        Log.d("naslov", "naslov je " + naslovi[i] );
                        Log.d("progid", "progid je " + idjevi[i] );
                        i++;
                        brojac++;
                    }
                    Log.d("brojac", "brojac je " + brojac );

                    for(int i = 0; i < brojac; i++){
                        Button button = new Button(context);
                        button.setText(naslovi[i]);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                380, 130
                        );
                        params.setMargins(105, 10, 10, 10);
                        button.setLayoutParams(params);
                        button.setBackgroundColor(Color.rgb(0, 54, 125));

                        String naziv = naslovi[i];
                        String oznaka = idjevi[i];
                        button.setTextColor(Color.parseColor("#ffffff"));
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(context, Transfer3.class);
                                intent.putExtra("id", id);
                                //treba mu 17 i oznaka kupca
                                intent.putExtra("oznaka", oznaka);
                                intent.putExtra("naziv", naziv);
                                intent.putExtra("user", username);
                                startActivity(intent);
                            }
                        });
                        ll.addView(button);
                    }

                    i = 0;
                    brojac = 0;
                }
            }
        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }



    }

    public void Nazad(View v){
        Intent intent = new Intent(this, Transfer.class);
        intent.putExtra("user", username);
        startActivity(intent);
        startActivity(intent);
    }

    public void Obnovi(View view){
        Intent intent = new Intent(this, Transfer2.class);
        intent.putExtra("user", username);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Transfer.class);
        intent.putExtra("user", username);
        startActivity(intent);
        startActivity(intent);
    }



    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + ":1433;databaseName=" + database + ";user=" + user + ";password=" + password + ";";

            connection = DriverManager.getConnection(connectionURL);
        }catch(Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }
        return connection;
    }
}