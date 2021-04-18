package com.example.hackaton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText username,password;
    Button botonRegistrar,botonLogin;
    BDHelper DB;
    String usuario="";
    public static final String EXTRA_MESSAGE ="MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.etUsuario);
        password=findViewById(R.id.etContrasena) ;

        botonLogin=findViewById(R.id.btnLogin);
        botonRegistrar=findViewById(R.id.btnRegistrar);
        DB = new BDHelper(this);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("") || pass.equals("") ){
                    displayToast("Los campos estan vacios, por favor introduzca algo");
                }else{
                    Boolean checkuser=DB.checkusername(user);
                    if(checkuser==false){
                        Boolean insert = DB.insertData(user,pass);
                        if(insert==true){
                            displayToast("Registro correcto");
                        }else{
                            displayToast("Registro incorrecto");
                        }
                    }
                    else{
                        displayToast("El usuario ya existe");
                    }
                }
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("") || pass.equals("") ){
                    displayToast("Los campos estan vacios, por favor introduzca algo");
                }else{
                    Boolean checkuser=DB.checkusername(user);
                    if(checkuser==true){
                        displayToast("Logeado correctamente");
                        Intent intent = new Intent(getApplicationContext(),SeleccionDeTiendas.class);
                        String message = user;
                        //intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);

                    }
                }
            }
        });

    }
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }







}