package com.example.mcg1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode4 extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    Connection con;
    int MY_PERMISSION_REQUEST = 0;
    ZXingScannerView scannerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        context = this;
    }

    @Override
    public void handleResult(Result result) {
        String code = result.getText();
        if(code.contains("&")){
            String[] arr = code.split("&");
            Inventar.tv_articleid.setText(arr[1]);
            // Inventar.tv_pom2.setText(arr[2]);
            Inventar.tv_naziv.setText(arr[5]);
            Inventar.tv_pom.setText(arr[4]);
            // Inventar.tv_pom3.setText((arr[6]));
        }else{

            try{
                con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
                if(con != null) {
                    PreparedStatement statement0 = con.prepareStatement("getMaterialInfo N'" + code + "'");
                    ResultSet rs0 = statement0.executeQuery();
                    //Log.d("konekcija", "uspesna");
                    if (!rs0.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Artikal nije pronadjen.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        PreparedStatement statement = con.prepareStatement("getMaterialInfo N'" + code + "'");
                        ResultSet rs = statement.executeQuery();
                        rs.next();
                        Premestanje.tv_pom.setText(rs.getString("OznakaArtikla"));
                        Premestanje.tv_naziv.setText(rs.getString("Naziv"));
                        //Inventar.tv_pom3.setText("");
                        //Inventar.tv_pom2.setText("");
                    }
                }
            }catch (Exception ex){
                Log.e("error", ex.getMessage());
            }







        }


        //Inventar.tv
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
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