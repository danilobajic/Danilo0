package com.example.mcg1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

public class Inventar extends AppCompatActivity {

    public static TextView tv_raf, tv_articleid, tv_pom, tv02, tv04, tv_naziv;
    Button scan_btn, unesi_btn, pregled_btn, kraj_btn, rucno_btn, btn_date;
    EditText et2;
    Connection con;
    String raf_str, oznaka, naziv, user, helpmi;
    Context context;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventar);
        sg = 0;
        tv_raf = findViewById(R.id.tv_raf);
        tv02 = findViewById(R.id.tv02);
        tv02.setVisibility(View.INVISIBLE);
        //tv03 = findViewById(R.id.tv03);

        tv04 = findViewById(R.id.tv04);
        tv04.setVisibility(View.INVISIBLE);
        tv_articleid = findViewById(R.id.tv_articleid);
       // tv_batchid = findViewById(R.id.tv_batchid);
        tv_naziv = findViewById(R.id.tv_naziv);
        tv_pom = findViewById(R.id.tv_pom);
        tv_pom.setVisibility(View.INVISIBLE);



        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR); month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        context = this;

        et2 = findViewById(R.id.et2);
        et2.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        //uzimam raf i usera od prethodnog activity-a
        raf_str = intent.getStringExtra("raf");
        user = intent.getStringExtra("user");
        tv_raf.setText("RAF: " + raf_str);
        scan_btn = findViewById(R.id.skeniraj_btn);
        scan_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        rucno_btn = findViewById(R.id.rucno_btn);
        rucno_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        unesi_btn = findViewById(R.id.unesi_btn);
        unesi_btn.setVisibility(View.INVISIBLE);
        unesi_btn.setBackgroundColor(Color.rgb(0, 54, 125));

        //btn_date = findViewById(R.id.date_btn);
        //btn_date.setVisibility(View.INVISIBLE);
        //btn_date.setBackgroundColor(Color.rgb(187, 38, 38));

        pregled_btn = findViewById(R.id.pregled_btn);
        pregled_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        kraj_btn = findViewById(R.id.kraj_btn);
        kraj_btn.setBackgroundColor(Color.rgb(0, 54, 125));
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
                String pomocni2 = tv_pom.getText().toString();
                Log.d("pomocni", "pomocni2 jeeeee: " + pomocni2);
                helpmi = pomocni2.replaceAll("\\s+", "");
                Log.d("pomocni", "pomocni jeeeee: " + helpmi);
                //tv_pom.setText(pomocni);
                et2.setText(helpmi);
            }
        });



        tv_articleid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et2.setText(tv_pom.getText());

                tv02.setVisibility(View.VISIBLE);

                tv04.setVisibility(View.VISIBLE);
                et2.setVisibility(View.VISIBLE);

                unesi_btn.setVisibility(View.VISIBLE);



                Log.d("nalll", "nalll: " + et2.getText().toString());
                if (et2.getText() == null) {
                    Log.d("jesteee", "jesteeee: " + "nulljeee");

                }



            }
        });
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCode.class));

            }
        });



        unesi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et2.getText().toString().matches("0") || et2.getText().toString().matches("-(.*)")){
                    Toast.makeText(Inventar.this,
                            "Kolicina ne moze biti 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{




                    try {
                        con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

                        PreparedStatement statement2 = con.prepareStatement("exec InsertMaterialQuantity2 N'" + tv_articleid.getText() + "', '" + "0" + "', '" + raf_str +"', N'" + user + "', " + helpmi + ", " + et2.getText().toString() + ", '" + "2001-01-01" + "'");
                        ResultSet rs2 = statement2.executeQuery();
                        if(rs2.next()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Inventar.this, "Zapisano!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
                        PreparedStatement statement3 = con.prepareStatement("updateKolicina N'" + tv_articleid.getText() + "', '" + raf_str + "', '" + "0" +"', " + et2.getText().toString());
                        //ResultSet rs3 = statement3.executeQuery();
                        Intent intent = new Intent(context, Inventar.class);
                        intent.putExtra("raf", raf_str);
                        intent.putExtra("user", user);
                        startActivity(intent);

                        }catch (Exception ex){
                            Log.e("error", ex.getMessage());
                        }
                }


            }
        });

    }





    public void Zavrsi(View v){

        new AlertDialog.Builder(this)
                .setTitle("ZAVRSI RAF")
                .setMessage("Zelite da zavrsite raf?")
                .setNegativeButton("NE", null)
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);



                            //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
                            PreparedStatement statement3 = con.prepareStatement("updateRaf '" + raf_str + "', " + 1);
                            ResultSet rs3 = statement3.executeQuery();
                            Intent intent = new Intent(context, Inventar.class);
                            intent.putExtra("raf", raf_str);
                            intent.putExtra("user", user);
                            startActivity(intent);

                        }catch (Exception ex){
                            Log.e("error", ex.getMessage());
                        }

                        Intent intent = new Intent(context, Raf.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }).create().show();

    }

    public void Rucno(View v){
        tv_pom.setText("");

        Intent intent = new Intent(this, Artikal.class);
        intent.putExtra("raf", raf_str);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Raf.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void Tabela(View v){
        Intent intent = new Intent(this, Tabela.class);
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