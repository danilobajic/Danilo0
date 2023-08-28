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

public class Prijem extends AppCompatActivity {


    String username, id, order, task, kod, naziv, kolicina, raf, zadrzi;
    TextView tv_naziv, tv_kod;
    EditText et_raf, et_kolicina;
    Button nazad_btn, unesi_btn;
    Connection con;
    Integer zadrzii;
    //trebace mi helpmi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijem);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        id = intent.getStringExtra("id");
        order = intent.getStringExtra("order");
        task = intent.getStringExtra("task");
        kod = intent.getStringExtra("kod");
        naziv = intent.getStringExtra("naziv");
        Log.d("naziv je", "naziv je" + naziv);
        kolicina = intent.getStringExtra("kolicina");
        raf = intent.getStringExtra("raf");
        zadrzii = Integer.parseInt(intent.getStringExtra("zadrzi"));

        //buttoni
        nazad_btn = (Button)findViewById(R.id.nazad_btn);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        unesi_btn = (Button)findViewById(R.id.unesi_btn);
        unesi_btn.setBackgroundColor(Color.rgb(0, 54, 125));

        tv_naziv = (TextView)findViewById(R.id.tv_naziv);
        tv_kod = (TextView)findViewById(R.id.tv_kod);

        tv_naziv.setText(naziv);
        tv_kod.setText(kod);

        et_raf = (EditText)findViewById(R.id.et_raf);
        et_kolicina = (EditText)findViewById(R.id.et_kolicina);

        et_raf.setText(raf);
        et_kolicina.setText(kolicina);


    }

    public void Zapisi(View view){

        try {
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);
            if(et_raf.getText().toString().isEmpty()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Prijem.this, "Raf ne sme biti prazan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                PreparedStatement statement2 = con.prepareStatement("MCGupdateOrderItem2 " + task + ", N'" + kod + "', '" + et_raf.getText() +"', " + et_kolicina.getText() + ", " + order);
                ResultSet rs = statement2.executeQuery();
                if(rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prijem.this, "Zapisano!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
                Intent intent = new Intent(this, WmsTabela1.class);
                intent.putExtra("id", id);
                intent.putExtra("order", order);

                intent.putExtra("user", username);
                startActivity(intent);
            }


        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }



    }


    public void Nazad(View view){
        Intent intent = new Intent(this, WmsTabela1.class);
        intent.putExtra("id", id);
        intent.putExtra("order", order);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WmsTabela1.class);
        intent.putExtra("id", id);
        intent.putExtra("order", order);
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