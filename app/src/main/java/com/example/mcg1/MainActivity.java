package com.example.mcg1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Context context;


    ActionBar actionBar;
    EditText username, password;
    Button loginbtn;
    Connection con;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = (Button)findViewById(R.id.login);
        //ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffef00"));
        loginbtn.setBackgroundColor(Color.rgb(0, 54, 125));
        context = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("IZLAZ")
                .setMessage("Zelite da napustite aplikaciju?")
                .setNegativeButton("NE", null)
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();

    }


    public void SQLfun(View v){
        //Log.d("dugme", "dugme radi");
        username = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_password);
        loginbtn = (Button)findViewById(R.id.login);
        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null){
                //Log.d("konekcija", "konekcija uspesna");
                user = username.getText().toString();
                String query = "select UserID from MCGdev.dbo.abcUser where UserName = N'" + username.getText() + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                String id = "string";
                //Log.d("query1", "query1 je: " + query);
                while(rs.next()){
                    //Log.d("while provera", "while je: uspesan");
                    id = rs.getString("userID");
                }
                //Log.d("id", "id je: " + id);
                String query2 = "select [Password] from MCGdev.dbo.abcPasswordHistory where UserID = " + id;
                Statement st2 = con.createStatement();
                ResultSet rs2 = st.executeQuery(query2);
                String sifra = "stringg";
                while(rs2.next()){
                    sifra = rs2.getString("Password");
                }
                Log.d("sifra", "sifra je: " + sifra);
                if(password.getText().toString().equals(sifra)){
                    Intent intent = new Intent(this, Meni.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    //Log.d("valja", "valja");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Dobrodosao " + user, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Neispravni podaci", Toast.LENGTH_SHORT).show();
                            //Log.d("ne valja", "ne valja");
                        }
                    });
                }
            }else{
                Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                //Log.d("konekcija", "konekcija neuspesna");
            }
        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }

    }



    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        Log.d("user", "user je: " + user);
        Log.d("pass", "pass je: " + password);
        Log.d("name", "name je: " + database);
        Log.d("ip", "ip je: " + server);
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