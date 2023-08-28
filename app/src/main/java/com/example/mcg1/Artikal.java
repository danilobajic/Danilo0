package com.example.mcg1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Artikal extends AppCompatActivity {

    EditText sifra_artikla;
    Connection con;
    Button nadji_btn, nazad_btn;
    TextView tv_raf;
    String raf_str, user;
    LinearLayout mainLayout;

    Context context;

    String kodovi[];
    String nazivi[];
    String barovi[];

    int i = 0;
    int brojac = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikal);
        tv_raf = findViewById(R.id.tv_raf);
        Intent intent = getIntent();
        raf_str = intent.getStringExtra("raf");
        Log.d("rafff", "rafff " + raf_str);
        user = intent.getStringExtra("user");
        tv_raf.setText("Raf: " + raf_str);
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        nadji_btn = (Button)findViewById(R.id.nadji_btn);
        nadji_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        context = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }


    public void Nadji(View v){
        TableLayout tl = (TableLayout)findViewById(R.id.table_main);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.artikal);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        sifra_artikla = (EditText)findViewById(R.id.unosartikla);
        String sifra = sifra_artikla.getText().toString();
        kodovi = new String[5000];
        nazivi = new String[5000];
        barovi = new String[5000];



        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("getMaterialInfo N'" + sifra + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Artikal nije pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getBaseContext(), "artikal " + sifra + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("getMaterialInfo N'" + sifra + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        kodovi[i] = rs.getString("OznakaArtikla");
                        nazivi[i] = rs.getString("Naziv");
                        barovi[i] = rs.getString("BarKod");
                        Log.d("kod", "kod je " + kodovi[i] );
                        Log.d("naziv", "kod je " + nazivi[i] );
                        Log.d("bar", "kod je " + barovi[i] );
                        i++;
                        brojac++;
                    }
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


                    TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.rgb(0, 54, 125));
                    tbrow0.setPadding(2, 5, 2, 2);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(10);
                    tv0.setText("KOD ARTIKLA");
                    tv0.setTextColor(Color.WHITE);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setPadding(3, 1, 3, 1);
                    tv0.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv0);
                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(10);
                    tv1.setText("NAZIV");
                    tv1.setTextColor(Color.WHITE);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(3, 1, 3, 1);
                    tv1.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv1);
                    TextView tv2 = new TextView(this);
                    tv2.setTextSize(10);
                    tv2.setText("BARKOD");
                    tv2.setTextColor(Color.WHITE);
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setPadding(3, 1, 3, 1);
                    tv2.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv2);


                    tbrow0.setGravity(Gravity.CENTER);
                    tbrow0.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    stk.addView(tbrow0);

                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setBackgroundColor(Color.parseColor("#E5E4E2"));
                        int pom = j;

                        for (int k = 0; k < 3; k++) {

                            /*EditText et0 = new EditText(this);
                            et0.setPadding(3, 1, 3, 1);
                            et0.setText(stringovi[j][k]);
                            et0.setTextSize(15);*/
                            TextView t1v = new TextView(this);
                            t1v.setPadding(2, 1, 2, 1);
                            t1v.setHeight(90);
                            t1v.setTextSize(12);
                            t1v.setVerticalFadingEdgeEnabled(true);


                            t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));

                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            if(k == 1){
                                if(stringovi[j][k].isEmpty()){
                                    t1v.setText("");
                                }else{
                                    int duzina = stringovi[j][k].length();
                                    int pola = duzina / 2;
                                    String pomocni = stringovi[j][k].substring(0, pola) + "\n" + stringovi[j][k].substring(pola);
                                    t1v.setText(pomocni);
                                    //t1v.setText(stringovi[j][k].substring(0,20));
                                }


                            }else{
                                t1v.setText(stringovi[j][k]);
                            }
                            tbrow.addView(t1v);

                            //et0.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));


                            //et0.setTextColor(Color.BLACK);
                            //et0.setGravity(Gravity.CENTER);

                            //tbrow.addView(et0);

                            //tbrow.setPadding(2, 5, 2, 2);

                            //tbrow.setMinimumWidth(650);

                        }
                        tbrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Log.d("arttt", "artttt" + redni_br);
                                String art_id = stringovi[pom][0];
                                String naziv = stringovi[pom][1];

                                Inventar.tv_articleid.setText(art_id);
                                Inventar.tv_naziv.setText(naziv);
                                Inventar.tv_pom.setText("0");
                                Intent intent = new Intent(context, Inventar.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(Artikal.this, "ART ID JE" + id_artikla, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        tbrow.setGravity(Gravity.CENTER);
                        tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border3));
                        stk.addView(tbrow);


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
        Intent intent = new Intent(this, Inventar.class);
        intent.putExtra("user", user);
        intent.putExtra("raf", raf_str);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Inventar.class);
        intent.putExtra("user", user);
        intent.putExtra("raf", raf_str);

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