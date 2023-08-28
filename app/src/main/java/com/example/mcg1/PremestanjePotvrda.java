package com.example.mcg1;

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

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PremestanjePotvrda extends AppCompatActivity {

    String user, naziv, kod, kolicina, raf, sifra;
    TextView tv_naziv, tv_kod, tv_kol, tv_raf;
    EditText et_raf, et_kol;
    double kol1, kol2;
    Button nazad_btn, potvrdi_btn;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premestanje_potvrda);

        tv_naziv = (TextView)findViewById(R.id.tv_naziv);
        tv_kod = (TextView)findViewById(R.id.tv_kod);
        tv_kol = (TextView)findViewById(R.id.tv_kol);
        tv_raf = (TextView)findViewById(R.id.tv_raf);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        naziv = intent.getStringExtra("naziv");
        kod = intent.getStringExtra("kod");
        kolicina = intent.getStringExtra("kol");
        raf = intent.getStringExtra("raf");
        sifra = intent.getStringExtra("sifra");

        tv_naziv.setText(naziv);
        tv_kol.setText(kolicina);
        tv_kod.setText(kod);
        tv_raf.setText(raf);

        et_raf = (EditText)findViewById(R.id.et_raf);
        et_kol = (EditText)findViewById(R.id.et_kol);

        nazad_btn = (Button)findViewById(R.id.nazad_btn);
        nazad_btn.setBackgroundColor(Color.rgb(0, 54, 125));
        potvrdi_btn = (Button)findViewById(R.id.potvrdi_btn);
        potvrdi_btn.setBackgroundColor(Color.rgb(0, 54, 125));

    }



    public void Potvrdi(View view){
        kol1 = Double.parseDouble(tv_kol.getText().toString());
        kol2 = Double.parseDouble(et_kol.getText().toString());
        if(kol2 > kol1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(com.example.mcg1.PremestanjePotvrda.this, "KOL > DOSTUPNO!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            try {

                con = connectionClass(ConnectionClass.un, ConnectionClass.pass, ConnectionClass.db, ConnectionClass.ip);


                PreparedStatement statement2 = con.prepareStatement("MCGPremestiIzRafaURaf '" + kod + "', '" + raf + "', '" + et_raf.getText() +"', '2'," + et_kol.getText());
                ResultSet rs = statement2.executeQuery();
                if(rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(com.example.mcg1.PremestanjePotvrda.this, "Premesteno!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //updateKolicina @MaterialCode nvarchar(19),  @StorageBin nvarchar(8), @Batch nvarchar(10), @QUantity int
                Premestanje.tv_pom.setText(sifra);
                Premestanje.tv_naziv.setText(naziv);
                Intent intent = new Intent(this, Premestanje.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }catch (Exception ex){
                Log.e("error", ex.getMessage());
            }
        }





    }

    public void Nazad(View view){
        Intent intent = new Intent(this, Premestanje.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Premestanje.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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