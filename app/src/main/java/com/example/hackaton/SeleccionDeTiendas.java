package com.example.hackaton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SeleccionDeTiendas extends AppCompatActivity {
    public static final String EXTRA_MESSAGE ="MESSAGE";
    public static final String EXTRA_MESSAGE1 ="MESSAGE";
    BDHelper DB;
    Button butonTiendas;
    RadioGroup rg;
    String seleccionTienda="",usuario="";
    ArrayList<String[]> tiendas=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {


         DB=new BDHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selecciontiendas);
        butonTiendas=findViewById(R.id.prueba);
        rg=findViewById(R.id.radiogroup);
        tiendas=DB.tiendas();
        for(int i=0;i<tiendas.size();i++){
            RadioButton RadioButton=new RadioButton(getApplicationContext());
            RadioButton.setId(i);
            RadioButton.setText(tiendas.get(i)[0]);
            RadioButton.setWidth(1000);
            RadioButton.setTextColor(Color.BLACK);
            RadioButton.setBackgroundColor(Color.YELLOW);
            Intent intent = getIntent();
            //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            //String usuario=message;
            rg.addView(RadioButton);
            RadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seleccionTienda= (String) RadioButton.getText();
                }
            });

        }

        butonTiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PantallaCompra.class);
                String message = seleccionTienda;
                String usuariomsg=usuario;
                intent.putExtra(EXTRA_MESSAGE, message);
                //intent.putExtra(EXTRA_MESSAGE1, usuariomsg);
                startActivity(intent);
            }
        });

    }



    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
