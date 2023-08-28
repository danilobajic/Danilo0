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
import android.text.Editable;
import android.text.TextWatcher;
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

public class Transfer3 extends AppCompatActivity {
    public static String pomocni;
    String username, id, naziv, oznaka;
    Connection con;
    public static TextView tv_pom;
    TextView tv_naziv;
    LinearLayout ll;
    Context context;
    Button nazad_btn, skeniraj_btn;

    String boxovi[] = new String[1000];


    int i = 0;
    int brojac = 0;
    int brojac2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer3);

        username = getIntent().getStringExtra("user");
        id = getIntent().getStringExtra("id");
        naziv = getIntent().getStringExtra("naziv");
        oznaka = getIntent().getStringExtra("oznaka");
        tv_naziv = findViewById(R.id.tv_naziv);
        tv_pom = findViewById(R.id.tv_pom);
        tv_pom.setVisibility(View.INVISIBLE);
        tv_naziv.setText(naziv);
        ll = findViewById(R.id.linearlayout2);
        context = this;
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        skeniraj_btn = (Button)findViewById(R.id.skeniraj_btn);
        skeniraj_btn.setBackgroundColor(Color.rgb(0, 54, 125));


        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetTransportOrderBox " + id + ", " + oznaka);
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Nemate ni jedan zadatak.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, Transfer2.class);
                            intent.putExtra("user", username);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            startActivity(intent);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getBaseContext(), "artikal " + sifra + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("MCGgetTransportOrderBox " + id + ", " + oznaka);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        boxovi[i] = rs.getString("Box");
                        Log.d("box", "box je " + boxovi[i] );

                        i++;
                        brojac++;
                    }
                    brojac2 = brojac;
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
                        button.setText(boxovi[i]);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                380, 130
                        );
                        params.setMargins(105, 10, 10, 10);
                        button.setLayoutParams(params);
                        button.setBackgroundColor(Color.rgb(0, 54, 125));
                        String box = boxovi[i];
                        button.setTextColor(Color.parseColor("#ffffff"));
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                new AlertDialog.Builder(context)
                                        .setTitle("ISPORUCI")
                                        .setMessage("Zelite da isporucite paket?")
                                        .setNegativeButton("NE", null)
                                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

                                                    PreparedStatement statement2 = con.prepareStatement("MCGupdateTransportOrderBox '" + box + "'");

                                                    ResultSet rs = statement2.executeQuery();


                                                    if(rs.next()) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Transfer3.this, "Završeno!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }



                                                }catch (Exception ex){
                                                    Log.e("error", ex.getMessage());
                                                }
                                                Intent intent = new Intent(context, Transfer3.class);
                                                intent.putExtra("id", id);
                                                //treba mu 17 i oznaka kupca
                                                intent.putExtra("oznaka", oznaka);
                                                intent.putExtra("naziv", naziv);
                                                intent.putExtra("user", username);
                                                startActivity(intent);


                                            }
                                        }).create().show();

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

        tv_pom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pomocni = "" + tv_pom.getText();
                int tru = 0;
                Log.d("pomocni", "pomocni je " + pomocni);
                Log.d("brojac2", "brojac2 je " + brojac2);
                for(int i = 0; i < brojac2; i++){
                    Log.d("box", "box je " + boxovi[i]);
                    if(pomocni.equals(boxovi[i])){
                        tru = 1;
                    }
                }
                if(tru == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Transfer3.this, "Nista nije pronadjeno!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    try {
                        con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

                        PreparedStatement statement2 = con.prepareStatement("MCGupdateTransportOrderBox '" + pomocni + "'");

                        ResultSet rs = statement2.executeQuery();


                        if(rs.next()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Transfer3.this, "Završeno!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    }catch (Exception ex){
                        Log.e("error", ex.getMessage());
                    }
                    Intent intent = new Intent(context, Transfer3.class);
                    intent.putExtra("id", id);
                    //treba mu 17 i oznaka kupca
                    intent.putExtra("oznaka", oznaka);
                    intent.putExtra("naziv", naziv);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            }
        });



    }

    public void Skeniraj(View v){

        startActivity(new Intent(getApplicationContext(),ScanCode5.class));



    }

    public void Nazad(View v){
        Intent intent = new Intent(this, Transfer2.class);
        intent.putExtra("user", username);
        intent.putExtra("id", id);
        startActivity(intent);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Transfer2.class);
        intent.putExtra("user", username);
        intent.putExtra("id", id);
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