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

public class Aktivnosti extends AppCompatActivity implements OpisDialog.OpisDialogListener{

    String username;
    Connection con;
    TextView tv_naziv;
    LinearLayout ll;
    Context context;
    Button nazad_btn, obnovi;
    int ii;
    static String naziv, id;


    String naslovi[] = new String[1000];
    String idjevi[] = new String[1000];

    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivnosti);

        username = getIntent().getStringExtra("user");
        //sta = getIntent().getStringExtra("sta");
        //Log.d("Sta", "Sta je " + sta);
        Log.d("User", "User je " + username);
        //sta2 = Integer.parseInt(sta);
        tv_naziv = findViewById(R.id.tv_naziv);

        ll = findViewById(R.id.linearlayout2);
        context = this;
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));


        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetActivity");


                ResultSet rs0 = statement0.executeQuery();

                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Nema aktivnosti.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                    PreparedStatement statement = con.prepareStatement("MCGgetActivity");

                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");

                        naslovi[i] = rs.getString("ActivityName");
                        idjevi[i] = rs.getString("ActivityID");

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
                        String btn_id = idjevi[i];
                        String naslov = naslovi[i];
                        Integer int_id = Integer.parseInt(btn_id);
                        button.setTextColor(Color.parseColor("#ffffff"));
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                if(btn_id.equals("0")){
                                    id = "0";
                                    naziv = "Istovar kamiona, slaganje na paletu - po transp. pakovanju";
                                }else if(btn_id.equals("1")){
                                    id = "1";
                                    naziv = "Istovar paleta sa robom i smeštanje u magacin (po paleti)";
                                }else if(btn_id.equals("5")){
                                    id = "5";
                                    naziv = "Deklarisanje robe (po krupnoj robi)";
                                }else if(btn_id.equals("16")){
                                    id = "16";
                                    naziv = "Čišćenje magacina - u minutama";
                                }else{
                                    id = "17";
                                    naziv = "Inventar - u minutama";
                                }

                                OpisDialog opisDialog = new OpisDialog();
                                opisDialog.show(getSupportFragmentManager(), "opis dialog");

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

    public static String getID(){
        return id;
    }

    public static String getNaziv(){
        return naziv;
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



    @Override
    public void dodajText(String kolicina, String naziv, String id) {
        int kol, ajdi;
        kol = Integer.parseInt(kolicina);
        ajdi = Integer.parseInt(id);
        try {
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

            PreparedStatement statement2 = con.prepareStatement("MCGinsertActivity '" + username + "', " + ajdi + ", " + kol);
            ResultSet rs = statement2.executeQuery();
            if(rs.next()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Aktivnosti.this, "Zapisano!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Intent intent = new Intent(this, Aktivnosti.class);
            intent.putExtra("user", username);
            startActivity(intent);

        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }
    }
}