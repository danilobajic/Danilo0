package com.example.mcg1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Priprema extends AppCompatActivity {

    String username, id, order, task, kod, naziv, kolicina0, kolicina1, raf, ean, batch, box, status;
    TextView tv_naziv, tv_kod, tv_raf, tv_batch, tv_ean, tv_kol0, tv_box;
    EditText et_kol1;
    Button nazad_btn, unesi_btn;
    Connection con;
    int pom_kol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priprema);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        id = intent.getStringExtra("id");
        order = intent.getStringExtra("order");
        task = intent.getStringExtra("task");
        kod = intent.getStringExtra("kod");
        naziv = intent.getStringExtra("naziv");
        Log.d("naziv je", "naziv je" + naziv);
        kolicina0 = intent.getStringExtra("kolicina0");
        kolicina1 = intent.getStringExtra("kolicina1");
        raf = intent.getStringExtra("raf");
        batch = intent.getStringExtra("batch");
        ean = intent.getStringExtra("ean");

        box = intent.getStringExtra("box");
        status = intent.getStringExtra("status");

        pom_kol = Integer.parseInt(kolicina1);


        nazad_btn = (Button)findViewById(R.id.nazad_btn);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        unesi_btn = (Button)findViewById(R.id.unesi_btn);
        unesi_btn.setBackgroundColor(Color.rgb(0, 54, 125));

        tv_naziv = (TextView)findViewById(R.id.tv_naziv);
        tv_kod = (TextView)findViewById(R.id.tv_kod);
        tv_raf = (TextView)findViewById(R.id.tv_raf);
        tv_batch = (TextView)findViewById(R.id.tv_batch);
        tv_ean = (TextView)findViewById(R.id.tv_ean);
        tv_kol0 = (TextView)findViewById(R.id.tv_kol0);

        et_kol1 = (EditText) findViewById(R.id.et_kol1);
        tv_box = (TextView)findViewById(R.id.tv_box);

        tv_naziv.setText(naziv);
        tv_kod.setText(kod);
        tv_raf.setText(raf);
        tv_batch.setText(batch);
        tv_ean.setText(ean);
        tv_kol0.setText(kolicina0);
        tv_box.setText(box);
        if(pom_kol == 0){
            et_kol1.setText(kolicina0);
        }else{
            et_kol1.setText(kolicina1);
        }


    }

    public void Zapisi(View view){

        try {
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);

            PreparedStatement statement2 = con.prepareStatement("MCGupdateOrderItem1 " + order + ", " + et_kol1.getText());
            ResultSet rs = statement2.executeQuery();
            if(rs.next()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Priprema.this, "Uspešno!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
            Intent intent = new Intent(this, WmsTabela2.class);
            intent.putExtra("id", id);
            intent.putExtra("order", order);

            intent.putExtra("user", username);
            startActivity(intent);

        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }



    }



    public void Nazad(View view){
        Intent intent = new Intent(this, WmsTabela2.class);
        intent.putExtra("id", id);
        intent.putExtra("order", order);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WmsTabela2.class);
        intent.putExtra("id", id);
        intent.putExtra("user", username);
        intent.putExtra("order", order);
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