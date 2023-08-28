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

public class WmsTabela2 extends AppCompatActivity {

    String username;
    String id, order;
    EditText sifra_artikla;
    Button zavrsi_btn, skeniraj_btn, nazad_btn;
    Connection con;
    TextView tv1;
    public static String pomocni_kod, pomocni_batch;

    LinearLayout mainLayout;
    Context context;
    String orderi[];
    String taskovi[];
    String rafovi[];
    String kodovi[];
    String nazivi[];
    String kolicine0[];
    String kolicine1[];
    String eani[];
    String becovi[];
    String boxovi[];
    String statusi[];

    String pomocni[];
    String stringovi[][];

    int i = 0;
    int brojac = 0;
    int pom_br = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wms_tabela2);


        tv1 = findViewById(R.id.tv_baner); //naslov


        //pribavljam iz intenta
        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        order = intent.getStringExtra("order");
        id = intent.getStringExtra("id");
        tv1.setText("PRIPREMA " + id);


        //buttoni
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        //nadji_btn = (Button)findViewById(R.id.nadji_btn);
        //nadji_btn.setBackgroundColor(Color.rgb(187, 38, 38));

        zavrsi_btn = (Button)findViewById(R.id.zavrsi);
        zavrsi_btn.setBackgroundColor(Color.rgb(0, 54, 125));


        context = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        TableLayout tl = (TableLayout)findViewById(R.id.table_main);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.artikal);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);



        orderi = new String[5000];
        taskovi = new String[5000];
        kodovi = new String[5000];
        nazivi = new String[5000];
        eani = new String[5000];
        becovi = new String[5000];
        rafovi = new String[5000];
        kolicine0 = new String[5000];
        kolicine1 = new String[5000];
        boxovi = new String[5000];
        statusi = new String[5000];
        pomocni = new String[5000];

        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetOrderItems1 N'" + id + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Ništa nije pronadjeno.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getBaseContext(), "artikal " + sifra + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("MCGgetOrderItems1 N'" + id + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        orderi[i] = rs.getString("OrderNumber");
                        taskovi[i] = rs.getString("TaskID");
                        rafovi[i] = rs.getString("StorageBin1");
                        kodovi[i] = rs.getString("MaterialCode");
                        nazivi[i] = rs.getString("MaterialName");
                        kolicine0[i] = rs.getString("RequestedQuantity");
                        kolicine1[i] = rs.getString("ConfirmedQuantity");
                        eani[i] = rs.getString("EANCoDe");
                        becovi[i] = rs.getString("Batch");
                        boxovi[i] = rs.getString("Box1");
                        statusi[i] = rs.getString("Status");



                        /*
                        Double pomocc = Double.parseDouble(kolicine0[i]);
                        int x = (int)pomocc;
                        String pomoc = pom
                        String pomocc2 = pomocc.toString();
                        Log.d("kolicina", "kolicina je" + pomocc);
                        kolicine[i] = pomocc;*/

                        //ispits po potrebi

                        i++;
                        brojac++;
                    }
                    stringovi = new String[brojac][11];
                    for (int j = 0; j < 11; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = orderi[k];

                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = taskovi[k];

                                }
                                break;
                            case 2:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = rafovi[k];

                                }
                                break;
                            case 3:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kodovi[k];

                                }
                                break;
                            case 4:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = nazivi[k];

                                }
                                break;
                            case 5:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine0[k];

                                }
                                break;
                            case 6:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = boxovi[k];

                                }
                                break;
                            case 7:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = eani[k];

                                }
                                break;
                            case 8:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = becovi[k];

                                }
                                break;
                            case 9:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine1[k];

                                }
                                break;
                            case 10:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = statusi[k];

                                }
                                break;
                        }
                    }


                    TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.rgb(0, 54, 125));
                    tbrow0.setPadding(2, 5, 0, 2);

                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(11);
                    tv0.setText("RAF");
                    tv0.setTextColor(Color.WHITE);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setPadding(3, 1, 3, 1);
                    tv0.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv0);

                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(11);
                    tv1.setText("KOD ARTIKLA");
                    tv1.setTextColor(Color.WHITE);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(3, 1, 3, 1);
                    tv1.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv1);

                    TextView tv2 = new TextView(this);
                    tv2.setTextSize(11);
                    tv2.setText("NAZIV");
                    tv2.setTextColor(Color.WHITE);
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setPadding(3, 1, 3, 1);
                    tv2.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv2);

                    TextView tv3 = new TextView(this);
                    tv3.setTextSize(11);
                    tv3.setText("KOLIČINA");
                    tv3.setTextColor(Color.WHITE);
                    tv3.setGravity(Gravity.CENTER);
                    tv3.setPadding(3, 1, 3, 1);
                    tv3.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv3);

                    TextView tv4 = new TextView(this);
                    tv4.setTextSize(11);
                    tv4.setText("BOX");
                    tv4.setTextColor(Color.WHITE);
                    tv4.setGravity(Gravity.CENTER);
                    tv4.setPadding(3, 1, 3, 1);
                    tv4.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv4);



                    stk.addView(tbrow0);

                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setMinimumHeight(100);
                        int pom = j;
                        for (int k = 2; k < 7; k++) {

                            TextView t1v = new TextView(this);
                            t1v.setPadding(3, 1, 3, 1);
                            t1v.setText(stringovi[j][k]);
                            t1v.setTextSize(12);
                            t1v.setMinHeight(100);
                            //t1v.setVerticalFadingEdgeEnabled(true);
                            if(k == 4){
                                if(stringovi[j][k].isEmpty()){
                                    t1v.setText("");
                                }else{
                                    int duzina = stringovi[j][k].length();
                                    int pola = duzina / 3;
                                    String pomocni = stringovi[j][k].substring(0, pola) + "\n" + stringovi[j][k].substring(pola, pola + pola) + "\n" + stringovi[j][k].substring(pola + pola);
                                    t1v.setText(pomocni);
                                    //t1v.setText(stringovi[j][k].substring(0,20));
                                }
                                t1v.setTextSize(10);
                                t1v.setVerticalFadingEdgeEnabled(true);




                            }
                            t1v.setGravity(Gravity.CENTER);


                            t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));
                            t1v.setTextColor(Color.BLACK);
                            tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border3));
                            if(stringovi[pom][10].contains("1")){
                                pom_br++;
                                t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border_green_tv));
                                t1v.setTextColor(Color.BLACK);
                                tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border_green_tbrow));
                            }else if(stringovi[pom][10].contains("2")){
                                pom_br++;
                                t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border_red_tv));
                                t1v.setTextColor(Color.BLACK);
                                tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border_red_tbrow));
                            }



                            tbrow.addView(t1v);
                            tbrow.setGravity(Gravity.CENTER);
                            //tbrow.setMinimumWidth(650);


                        }
                        tbrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id_artikla = (String) v.getTag();
                                Log.d("arttt", "artttt" + id_artikla);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WmsTabela2.this, "Task je" + stringovi[pom][1], Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, Priprema.class);
                                        intent.putExtra("id", id);
                                        intent.putExtra("user", username);
                                        intent.putExtra("order", stringovi[pom][0]);
                                        intent.putExtra("task", stringovi[pom][1]);
                                        intent.putExtra("raf", stringovi[pom][2]);
                                        intent.putExtra("kod", stringovi[pom][3]);
                                        intent.putExtra("naziv", stringovi[pom][4]);
                                        intent.putExtra("kolicina0", stringovi[pom][5]);
                                        intent.putExtra("kolicina1", stringovi[pom][9]);
                                        intent.putExtra("ean", stringovi[pom][7]);
                                        intent.putExtra("batch", stringovi[pom][8]);
                                        intent.putExtra("box", stringovi[pom][6]);
                                        intent.putExtra("status", stringovi[pom][10]);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                        stk.addView(tbrow);

                    }


                }
            }
        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }


    }

    public void Zavrsi(View view){
        Log.d("BROJAC", "BROJAC JE " + brojac);
        Log.d("POM", "POM JE " + pom_br);

        int broj = 0;
        for(int j = 0; j < brojac; j++){
            if(stringovi[j][10].contains("1") || stringovi[j][10].contains("2")){
                broj = 1;
            }
        }
        Log.d("Pomocni", "Pomocni br je " + pom_br);
        pom_br = pom_br/5;
        Log.d("Pomocni", "Pomocni br je " + pom_br);
        if(pom_br == brojac){
            try {
                con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

                PreparedStatement statement2 = con.prepareStatement("MCGupdateOrderStatus1 '" + id + "'");

                ResultSet rs = statement2.executeQuery();


                if(rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WmsTabela2.this, "ZAVRŠENO!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
                Intent intent = new Intent(this, Wms.class);

                intent.putExtra("user", username);
                startActivity(intent);

            }catch (Exception ex){
                Log.e("error", ex.getMessage());
            }
            Intent intent = new Intent(this, Wms.class);
            intent.putExtra("user", username);
            startActivity(intent);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WmsTabela2.this, "GREŠKA!", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public void Obnovi(View view){
        Intent intent = new Intent(context, WmsTabela2.class);
        intent.putExtra("id", id);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    public void Nazad(View view){
        Intent intent = new Intent(this, Wms.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Wms.class);
        intent.putExtra("user", username);
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