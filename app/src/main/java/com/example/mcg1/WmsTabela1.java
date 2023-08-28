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
import android.text.Editable;
import android.text.TextWatcher;
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

import javax.xml.transform.Result;

public class WmsTabela1 extends AppCompatActivity {
    String username;
    String id, order;
    EditText sifra_artikla;
    Button zavrsi_btn, skeniraj_btn, nazad_btn, obnovi_btn;
    Connection con;
    public static TextView tv1, tv_barcode;
    String pom_kod;
    public static String pomocni_kod, pomocni_batch, barcode;
    boolean prazno = false;

    LinearLayout mainLayout;
    Context context;
    String orderi[];
    String taskovi[];
    String kodovi[];
    String nazivi[];
    String eani[];
    String becovi[];
    String rafovi[];
    String kolicine[];
    String kolicine0[];
    String pomocni[];
    String stringovi[][];
    String stringovinew[][];

    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wms_tabela1);

        tv1 = findViewById(R.id.tv_baner); //naslov
        tv_barcode = findViewById(R.id.tv_barcode);
        tv_barcode.setVisibility(View.INVISIBLE);

        //pribavljam iz intenta
        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        order = intent.getStringExtra("order");
        id = intent.getStringExtra("id");
        tv1.setText("PRIJEM " + id);


        //buttoni
        nazad_btn = (Button)findViewById(R.id.nazad1);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));

        obnovi_btn = (Button)findViewById(R.id.obnovi);
        obnovi_btn.setBackgroundColor(Color.rgb(0, 54, 125));

        skeniraj_btn = (Button)findViewById(R.id.skeniraj_btn);
        skeniraj_btn.setBackgroundColor(Color.rgb(0, 54, 125));
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

        ///sifra_artikla = (EditText)findViewById(R.id.et_sifra_artikla);
        //String sifra = sifra_artikla.getText().toString();
        orderi = new String[5000];
        taskovi = new String[5000];
        kodovi = new String[5000];
        nazivi = new String[5000];
        eani = new String[5000];
        becovi = new String[5000];
        rafovi = new String[5000];
        kolicine0 = new String[5000];
        kolicine = new String[5000];
        pomocni = new String[5000];
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);

        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("MCGgetOrderItems N'" + id + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    prazno = true;
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
                    PreparedStatement statement = con.prepareStatement("MCGgetOrderItems N'" + id + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        Log.d("uupadavela", "uopadavaelaels");
                        orderi[i] = rs.getString("OrderNumber");
                        taskovi[i] = rs.getString("TaskID");
                        kodovi[i] = rs.getString("MaterialCode");
                        nazivi[i] = rs.getString("MaterialName");
                        eani[i] = rs.getString("EANCode");
                        becovi[i] = rs.getString("Batch");
                        rafovi[i] = rs.getString("StorageBin1");
                        kolicine0[i] = rs.getString("RequestedQuantity");
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
                    stringovi = new String[brojac][8];
                    for (int j = 0; j < 8; j++) {
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
                                    stringovi[k][j] = kodovi[k];

                                }
                                break;
                            case 3:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = nazivi[k];

                                }
                                break;
                            case 4:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = eani[k];

                                }
                                break;
                            case 5:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = becovi[k];

                                }
                                break;
                            case 6:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine0[k];

                                }
                                break;
                            case 7:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = rafovi[k];
                                    Log.d("raf", "raf je " + rafovi[k]);

                                }
                                break;
                        }
                    }


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
                    tv2.setText("EAN");
                    tv2.setTextColor(Color.WHITE);
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setPadding(3, 1, 3, 1);
                    tv2.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    tbrow0.addView(tv2);

                    TextView tv3 = new TextView(this);
                    tv3.setTextSize(10);
                    tv3.setText("BATCH");
                    tv3.setTextColor(Color.WHITE);
                    tv3.setGravity(Gravity.CENTER);
                    tv3.setPadding(3, 1, 3, 1);
                    tv3.setBackground(ContextCompat.getDrawable(this, R.drawable.border2));
                    //tbrow0.addView(tv3);

                    TextView tv4 = new TextView(this);
                    tv4.setTextSize(10);
                    tv4.setText("KOLIČINA");
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

                            if(k!=5){
                                TextView t1v = new TextView(this);
                                t1v.setPadding(3, 1, 3, 1);
                                t1v.setText(stringovi[j][k]);
                                t1v.setTextSize(11);
                                t1v.setMinHeight(100);
                                //t1v.setVerticalFadingEdgeEnabled(true);
                                if(k == 3){
                                    if(stringovi[j][k].isEmpty()){
                                        t1v.setText("");
                                    }else{
                                        int duzina = stringovi[j][k].length();
                                        int pola = duzina / 3;
                                        String pomocni = stringovi[j][k].substring(0, pola) + "\n" + stringovi[j][k].substring(pola, pola + pola) + "\n" + stringovi[j][k].substring(pola + pola);
                                        t1v.setText(pomocni);
                                        //t1v.setText(stringovi[j][k].substring(0,20));
                                    }
                                    t1v.setTextSize(9);
                                    t1v.setVerticalFadingEdgeEnabled(true);


                                    t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));

                                    t1v.setTextColor(Color.BLACK);
                                    t1v.setGravity(Gravity.CENTER);

                                }else{
                                    t1v.setBackground(ContextCompat.getDrawable(this, R.drawable.border4));


                                    t1v.setTextColor(Color.BLACK);
                                    t1v.setGravity(Gravity.CENTER);

                                }
                                //t1v.setBackgroundColor(Color.rgb(236, 93, 85));


                                tbrow.addView(t1v);
                                tbrow.setGravity(Gravity.CENTER);
                                //tbrow.setMinimumWidth(650);
                                tbrow.setBackground(ContextCompat.getDrawable(this, R.drawable.border3));
                            }

                        }
                        tbrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id_artikla = (String) v.getTag();
                                Log.d("arttt", "artttt" + id_artikla);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(context, Prijem.class);
                                        intent.putExtra("id", id);
                                        intent.putExtra("user", username);
                                        intent.putExtra("order", stringovi[pom][0]);
                                        Log.d("order", "order je " + stringovi[pom][0]);
                                        intent.putExtra("task", stringovi[pom][1]);
                                        Log.d("task", "task je " + stringovi[pom][1]);
                                        intent.putExtra("kod", stringovi[pom][2]);
                                        intent.putExtra("naziv", stringovi[pom][3]);
                                        Log.d("naziv", "naziv je " + stringovi[pom][3]);
                                        intent.putExtra("kolicina", stringovi[pom][6]);
                                        intent.putExtra("raf", stringovi[pom][7]);
                                        Log.d("raf", "raf je " + stringovi[pom][7]);
                                        intent.putExtra("zadrzi", "0");
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

        tv_barcode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String barkodd = tv_barcode.getText().toString();
                Log.d("UPADDDDD", "UPAOOO i barkod je " + barkodd);
                try{
                    con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
                    if(con != null) {
                        PreparedStatement statement0 = con.prepareStatement("getMaterialInfo '" + barkodd + "'");
                        ResultSet rs0 = statement0.executeQuery();
                        Log.d("konekcija", "uspesna");
                        if (!rs0.next()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Ništa nije pronadjeno.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            PreparedStatement statement = con.prepareStatement("getMaterialInfo N'" + barkodd + "'");
                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            pom_kod = rs.getString("OznakaArtikla");
                        }
                    }
                }catch (Exception ex){
                    Log.e("error", ex.getMessage());
                }
                stringovinew = new String[brojac][8];
                int brojac2 = 0;
                for (int j = 0; j < brojac; j++) {
                    if(pom_kod.equals(stringovi[j][2])){
                        stringovinew[brojac2][0] = stringovi[j][0];
                        stringovinew[brojac2][1] = stringovi[j][1];
                        stringovinew[brojac2][2] = stringovi[j][2];
                        stringovinew[brojac2][3] = stringovi[j][3];
                        stringovinew[brojac2][4] = stringovi[j][4];
                        stringovinew[brojac2][5] = stringovi[j][5];
                        stringovinew[brojac2][6] = stringovi[j][6];
                        stringovinew[brojac2][7] = stringovi[j][7];
                        brojac2++;
                    }
                }
                stk.removeAllViews();
                TableRow tbrow0 = new TableRow(context);
                tbrow0.setBackgroundColor(Color.rgb(0, 54, 125));
                tbrow0.setPadding(2, 5, 2, 2);
                TextView tv0 = new TextView(context);
                tv0.setTextSize(10);
                tv0.setText("KOD ARTIKLA");
                tv0.setTextColor(Color.WHITE);
                tv0.setGravity(Gravity.CENTER);
                tv0.setPadding(3, 1, 3, 1);
                tv0.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                tbrow0.addView(tv0);
                TextView tv1 = new TextView(context);
                tv1.setTextSize(10);
                tv1.setText("NAZIV");
                tv1.setTextColor(Color.WHITE);
                tv1.setGravity(Gravity.CENTER);
                tv1.setPadding(3, 1, 3, 1);
                tv1.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                tbrow0.addView(tv1);
                TextView tv2 = new TextView(context);
                tv2.setTextSize(10);
                tv2.setText("EAN");
                tv2.setTextColor(Color.WHITE);
                tv2.setGravity(Gravity.CENTER);
                tv2.setPadding(3, 1, 3, 1);
                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                tbrow0.addView(tv2);

                TextView tv3 = new TextView(context);
                tv3.setTextSize(10);
                tv3.setText("BATCH");
                tv3.setTextColor(Color.WHITE);
                tv3.setGravity(Gravity.CENTER);
                tv3.setPadding(3, 1, 3, 1);
                tv3.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                //tbrow0.addView(tv3);

                TextView tv4 = new TextView(context);
                tv4.setTextSize(10);
                tv4.setText("KOLIČINA");
                tv4.setTextColor(Color.WHITE);
                tv4.setGravity(Gravity.CENTER);
                tv4.setPadding(3, 1, 3, 1);
                tv4.setBackground(ContextCompat.getDrawable(context, R.drawable.border2));
                tbrow0.addView(tv4);

                stk.addView(tbrow0);

                for (int j = 0; j < brojac2; j++) {
                    TableRow tbrow = new TableRow(context);
                    tbrow.setMinimumHeight(100);
                    int pom = j;
                    for (int k = 2; k < 7; k++) {
                        if(k!=5){
                            TextView t1v = new TextView(context);
                            t1v.setPadding(3, 1, 3, 1);
                            t1v.setText(stringovinew[j][k]);
                            t1v.setTextSize(11);
                            t1v.setMinHeight(100);
                            //t1v.setVerticalFadingEdgeEnabled(true);
                            if(k == 3){
                                if(stringovinew[j][k].isEmpty()){
                                    t1v.setText("");
                                }else{
                                    int duzina = stringovinew[j][k].length();
                                    int pola = duzina / 2;
                                    String pomocni = stringovinew[j][k].substring(0, pola) + "\n" + stringovinew[j][k].substring(pola);
                                    t1v.setText(pomocni);
                                    //t1v.setText(stringovinew[j][k].substring(0,20));
                                }
                                t1v.setTextSize(9);
                                t1v.setVerticalFadingEdgeEnabled(true);


                                t1v.setBackground(ContextCompat.getDrawable(context, R.drawable.border4));

                                t1v.setTextColor(Color.BLACK);
                                t1v.setGravity(Gravity.CENTER);

                            }else{
                                t1v.setBackground(ContextCompat.getDrawable(context, R.drawable.border4));


                                t1v.setTextColor(Color.BLACK);
                                t1v.setGravity(Gravity.CENTER);

                            }
                            //t1v.setBackgroundColor(Color.rgb(236, 93, 85));


                            tbrow.addView(t1v);
                            tbrow.setGravity(Gravity.CENTER);
                            //tbrow.setMinimumWidth(650);
                            tbrow.setBackground(ContextCompat.getDrawable(context, R.drawable.border3));
                        }


                    }
                    tbrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id_artikla = (String) v.getTag();
                            Log.d("arttt", "artttt" + id_artikla);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(context, Prijem.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("user", username);
                                    intent.putExtra("order", stringovi[pom][0]);
                                    Log.d("order", "order je " + stringovi[pom][0]);
                                    intent.putExtra("task", stringovi[pom][1]);
                                    Log.d("task", "task je " + stringovi[pom][1]);
                                    intent.putExtra("kod", stringovi[pom][2]);
                                    intent.putExtra("naziv", stringovi[pom][3]);
                                    Log.d("naziv", "naziv je " + stringovi[pom][3]);
                                    intent.putExtra("kolicina", stringovi[pom][6]);
                                    intent.putExtra("raf", stringovi[pom][7]);
                                    Log.d("raf", "raf je " + stringovi[pom][7]);
                                    intent.putExtra("zadrzi", "1");
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                    stk.addView(tbrow);

                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

       skeniraj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ScanCode3.class));

            }
        });

        obnovi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, WmsTabela1.class);
                intent.putExtra("id", id);
                intent.putExtra("order", order);

                intent.putExtra("user", username);
                startActivity(intent);

            }
        });



    }




    public void Nazad(View view){
        Intent intent = new Intent(this, Wms.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }


    public void Zavrsi(View view){
        if(prazno){
            try {
                con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

                PreparedStatement statement2 = con.prepareStatement("MCGupdateOrderStatus2 '" + id + "'");

                ResultSet rs = statement2.executeQuery();


                if(rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WmsTabela1.this, "Završeno!", Toast.LENGTH_SHORT).show();
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
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WmsTabela1.this, "GREŠKA!", Toast.LENGTH_SHORT).show();
                }
            });
        }

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