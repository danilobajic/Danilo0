package com.example.mcg1;

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
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Premestanje extends AppCompatActivity {

    public static TextView tv_pom, tv_naziv;
    Button scan_btn,  rucno_btn, nazad_btn;
    Connection con;
    String raf_str, user, code, sifra;
    Context context;
    LinearLayout mainLayout;
    int code2;

    String kodovi[];
    String rafovi[];
    String kolicine[];

    int i = 0;
    int brojac = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premestanje);

        // tv_batchid = findViewById(R.id.tv_batchid);
        tv_naziv = findViewById(R.id.tv_naziv);
        tv_pom = findViewById(R.id.tv_pom);
        tv_pom.setVisibility(View.INVISIBLE);
        context = this;


        Intent intent = getIntent();
        //uzimam raf i usera od prethodnog activity-a

        user = intent.getStringExtra("user");
        code = intent.getStringExtra("code");
        code2 = Integer.parseInt(code);
        if(code2 != -1){
            tv_pom.setText(code);
        }
        scan_btn = findViewById(R.id.skeniraj_btn);
        scan_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        rucno_btn = findViewById(R.id.rucno_btn);
        rucno_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));



        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        tv_pom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TableLayout tl = (TableLayout)findViewById(R.id.table_main);
                tl.removeAllViews();
                mainLayout = (LinearLayout)findViewById(R.id.premestanje);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

                sifra = "" + tv_pom.getText();
                kodovi = new String[5000];
                rafovi = new String[5000];
                kolicine = new String[5000];



                try{
                    con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
                    if(con != null) {
                        PreparedStatement statement0 = con.prepareStatement("MCGgetMaterialsStorageBin N'" + sifra + "'");
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
                            PreparedStatement statement = con.prepareStatement("MCGgetMaterialsStorageBin N'" + sifra + "'");
                            ResultSet rs = statement.executeQuery();
                            while (rs.next()) {
                                Log.d("uupadavela", "uopadavaelaels");
                                kodovi[i] = rs.getString("MaterialCode");
                                rafovi[i] = rs.getString("StorageBin");
                                kolicine[i] = rs.getString("AvailableStock");
                                Log.d("kod", "kod je " + kodovi[i] );
                                Log.d("naziv", "kod je " + rafovi[i] );
                                Log.d("bar", "kod je " + kolicine[i] );
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
                                            stringovi[k][j] = rafovi[k];

                                        }
                                        break;
                                    case 2:
                                        for (int k = 0; k < brojac; k++) {
                                            stringovi[k][j] = kolicine[k];

                                        }
                                        break;
                                }
                            }

                            for (int j = 0; j < 3; j++) {
                                for (int k = 0; k < brojac; k++) {
                                    Log.d("POLJA", "polje je " + stringovi[k][j]);
                                }
                            }
                            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                            TableRow tbrow0 = new TableRow(context);
                            tbrow0.setBackgroundColor(Color.rgb(0, 54, 125));
                            tbrow0.setPadding(2, 5, 2, 2);
                            TextView tv0 = new TextView(context);
                            tv0.setTextSize(15);
                            tv0.setText("KOD ARTIKLA");
                            tv0.setTextColor(Color.WHITE);
                            tv0.setGravity(Gravity.CENTER);
                            tv0.setPadding(3, 1, 3, 1);
                            tv0.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                            tbrow0.addView(tv0);
                            TextView tv1 = new TextView(context);
                            tv1.setTextSize(15);
                            tv1.setText("RAF");
                            tv1.setTextColor(Color.WHITE);
                            tv1.setGravity(Gravity.CENTER);
                            tv1.setPadding(3, 1, 3, 1);
                            tv1.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                            tbrow0.addView(tv1);
                            TextView tv2 = new TextView(context);
                            tv2.setTextSize(15);
                            tv2.setText("KOLIČINA");
                            tv2.setTextColor(Color.WHITE);
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setPadding(3, 1, 3, 1);
                            tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                            tbrow0.addView(tv2);


                            tbrow0.setGravity(Gravity.CENTER);
                            tbrow0.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
                            stk.addView(tbrow0);

                            for (int j = 0; j < brojac; j++) {
                                int pom = j;
                                TableRow tbrow = new TableRow(context);
                                tbrow.setBackgroundColor(Color.parseColor("#E5E4E2"));

                                for (int k = 0; k < 3; k++) {


                                    TextView t1v = new TextView(context);
                                    t1v.setPadding(2, 1, 2, 1);
                                    t1v.setHeight(90);
                                    t1v.setText(stringovi[j][k]);
                                    t1v.setTextSize(15);
                                    t1v.setVerticalFadingEdgeEnabled(true);


                                    t1v.setBackground(ContextCompat.getDrawable(context, R.drawable.border4));

                                    t1v.setTextColor(Color.BLACK);
                                    t1v.setGravity(Gravity.CENTER);
                                    tbrow.addView(t1v);

                                }
                                tbrow.setGravity(Gravity.CENTER);
                                tbrow.setBackground(ContextCompat.getDrawable(context, R.drawable.border3));
                                stk.addView(tbrow);
                                tbrow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        String naziv = "" + tv_naziv.getText();

                                        Intent intent = new Intent(context, PremestanjePotvrda.class);
                                        intent.putExtra("user", user);
                                        intent.putExtra("naziv", naziv);
                                        intent.putExtra("kod", stringovi[pom][0]);
                                        intent.putExtra("raf", stringovi[pom][1]);
                                        intent.putExtra("kol", stringovi[pom][2]);
                                        intent.putExtra("sifra", sifra);
                                        startActivity(intent);
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
        });




        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCode4.class));

            }
        });





    }







    public void Rucno(View v){
        //tv_pom.setText("");

        Intent intent = new Intent(this, PremestanjeRucno.class);
        intent.putExtra("user", user);
        startActivity(intent);
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
                        a.putExtra("user", user);
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
                        a.putExtra("user", user);
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