package com.example.mcg1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Wms extends AppCompatActivity {

    String username;
    Connection con;
    TextView tv_naziv;
    LinearLayout ll;
    Context context;
    Button nazad_btn, obnovi;
    String butonnn;

    String grupe[] = new String[1000];
    String naslovi[] = new String[1000];
    String idjevi[] = new String[1000];

    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wms);

        username = getIntent().getStringExtra("user");
        tv_naziv = findViewById(R.id.tv_naziv);
        ll = findViewById(R.id.linearlayout);
        context = this;
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        obnovi = (Button)findViewById(R.id.obnovi_btn);
        obnovi.setBackgroundColor(Color.rgb(0, 54, 125));

        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetOrders N'" + username + "'");
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
                    PreparedStatement statement = con.prepareStatement("MCGgetOrders N'" + username + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        grupe[i] = rs.getString("TOGroup");
                        naslovi[i] = rs.getString("Naslov");
                        idjevi[i] = rs.getString("ProgramID");
                        Log.d("grupa", "grupa je " + grupe[i] );
                        Log.d("naslov", "naslov je " + naslovi[i] );
                        Log.d("progid", "progid je " + idjevi[i] );
                        i++;
                        brojac++;
                    }
                    Log.d("brojac", "brojac je " + brojac );
                    /*
                    String[][] stringovi = new String[brojac][4];
                    for (int j = 0; j < 3; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kodovi[k];

                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = nazivi[k];

                                }
                                break;
                            case 2:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = barovi[k];

                                }
                                break;
                        }
                    }

                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < brojac; k++) {
                            Log.d("POLJA", "polje je " + stringovi[k][j]);
                        }
                    }*/

                    for(int i = 0; i < brojac; i++){
                        Button button = new Button(context);
                        button.setText(naslovi[i]);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                380, 130
                        );
                        params.setMargins(105, 10, 10, 10);
                        button.setLayoutParams(params);
                        button.setBackgroundColor(Color.rgb(0, 54, 125));
                        String btn_id = grupe[i];
                        String naslov = naslovi[i];
                        Integer int_id = Integer.parseInt(btn_id);
                        button.setTextColor(Color.parseColor("#ffffff"));
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {

                                if(naslov.contains("Prijem")){
                                    Intent intent = new Intent(context, WmsTabela1.class);
                                    intent.putExtra("id", btn_id);
                                    intent.putExtra("user", username);
                                    startActivity(intent);
                                }else if(naslov.contains("Priprema")){
                                    Intent intent = new Intent(context, WmsTabela2.class);
                                    intent.putExtra("id", btn_id);
                                    intent.putExtra("user", username);
                                    startActivity(intent);
                                }
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

    public void Obnovi(View view){
        Intent intent = new Intent(this, Wms.class);
        intent.putExtra("user", username);
        startActivity(intent);
        startActivity(intent);
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