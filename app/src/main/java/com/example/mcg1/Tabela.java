package com.example.mcg1;

import androidx.appcompat.app.ActionBar;
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

public class Tabela extends AppCompatActivity {

    TextView tv_raf;
    String raf_str, user;
    LinearLayout mainLayout;
    ActionBar actionBar;

    Button nazadbtn;
    EditText sifra_artikla;
    Connection con;
    String sifra;
    String kodovi[];
    String naziv;
    String becovi[];
    String kolicine_old[];
    String kolicine_new[];
    int i = 0;
    int brojac = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela);
        tv_raf = findViewById(R.id.tv04);
        Intent intent = getIntent();
        raf_str = intent.getStringExtra("raf");
        Log.d("rafff", "rafff " + raf_str);
        user = intent.getStringExtra("user");
        tv_raf.setText("RAF: " + raf_str);
        nazadbtn = (Button)findViewById(R.id.nazad1);
        nazadbtn.setBackgroundColor(Color.rgb(0, 54, 125));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        TableLayout tl = (TableLayout)findViewById(R.id.table_main);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.artikal);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        kodovi = new String[5000];
        becovi = new String[5000];
        kolicine_old = new String[5000];
        kolicine_new = new String[5000];

        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("getPopisZaRaf '" + raf_str + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Raf: " + raf_str + " nije pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Raf: " + raf_str + " pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("getPopisZaRaf '" + raf_str + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        kodovi[i] = rs.getString("MaterialCOde");
                        becovi[i] = rs.getString("Batch");
                        kolicine_old[i] = rs.getString("QuantityBase");
                        kolicine_new[i] = rs.getString("Quantity");

                        i++;
                        brojac++;
                    }
                    String[][] stringovi = new String[brojac][4];
                    for (int j = 0; j < 4; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kodovi[k];

                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = becovi[k];

                                }
                                break;
                            case 2:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine_old[k];

                                }
                                break;
                            case 3:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine_new[k];
                                }
                                break;
                        }
                    }


                    TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.rgb(0, 54, 125));
                    tbrow0.setPadding(2, 5, 2, 2);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(12);
                    tv0.setText("OZNAKA");
                    tv0.setTextColor(Color.WHITE);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setPadding(3, 1, 3, 1);
                    tv0.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv0);
                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(12);
                    tv1.setText("SIFRA");
                    tv1.setTextColor(Color.WHITE);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(3, 1, 3, 1);
                    tv1.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv1);
                    TextView tv2 = new TextView(this);
                    tv2.setTextSize(12);
                    tv2.setText("KOLICINA");
                    tv2.setTextColor(Color.WHITE);
                    tv2.setGravity(Gravity.LEFT);
                    tv2.setPadding(3, 1, 3, 1);
                    tv2.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv2);
                    TextView tv3 = new TextView(this);
                    tv3.setTextSize(12);
                    tv3.setText("KOLICINA");
                    tv3.setTextColor(Color.WHITE);
                    tv3.setGravity(Gravity.CENTER);
                    tv3.setPadding(3, 1, 3, 1);
                    tv3.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv3);

                    tbrow0.setGravity(Gravity.CENTER);
                    tbrow0.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    stk.addView(tbrow0);

                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setBackgroundColor(Color.parseColor("#E5E4E2"));
                        String art_id = stringovi[j][0];
                        TextView pom = new TextView(this);
                        pom.setText("pot");
                        pom.setClickable(true);
                         pom.setTag(art_id);


                        for (int k = 0; k < 4; k++) {
                            /*EditText et0 = new EditText(this);
                            et0.setPadding(3, 1, 3, 1);
                            et0.setText(stringovi[j][k]);
                            et0.setTextSize(15);*/
                            TextView t1v = new TextView(this);
                            t1v.setPadding(3, 1, 3, 1);
                            t1v.setText(stringovi[j][k]);
                            t1v.setHeight(60);
                            t1v.setTextSize(12);
                            t1v.setVerticalFadingEdgeEnabled(true);


                            t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));
                            //et0.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));

                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            //et0.setTextColor(Color.BLACK);
                            //et0.setGravity(Gravity.CENTER);

                            //tbrow.addView(et0);
                            tbrow.addView(t1v);
                            //tbrow.setPadding(2, 5, 2, 2);

                            //tbrow.setMinimumWidth(650);

                        }
                        //tbrow.addView(pom);
                        tbrow.setGravity(Gravity.CENTER);
                        tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border3));
                        stk.addView(tbrow);
                        pom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id_artikla = (String) v.getTag();
                                Log.d("arttt", "artttt" + id_artikla);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(Tabela.this, "ART ID JE" + id_artikla, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

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