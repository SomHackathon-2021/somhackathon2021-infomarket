package com.example.hackaton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaCompra extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    BDHelper DB;
    Button comprarboton;
    RadioGroup rg;
    ArrayList<String[]> datosTienda =new ArrayList<>();
    ArrayList<String[]> valoresProducto=new ArrayList<>();
    int total=0;
    int valores=0;
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compra);
        comprarboton =findViewById(R.id.prueba);
        rg=findViewById(R.id.checkboxs);
        Intent intent = getIntent();

        DB=new BDHelper(this);
        String message = intent.getStringExtra(SeleccionDeTiendas.EXTRA_MESSAGE);
        String tiendaElegida=message;
        //String message1 = intent.getStringExtra(SeleccionDeTiendas.EXTRA_MESSAGE1);
        String usuario=message;
        // Capture the layout's TextView and set the string as its text

        datosTienda =DB.datosTienda(tiendaElegida);

        for(int i=0;i<datosTienda.size();i++){
            CheckBox checkbox=new CheckBox(getApplicationContext());
            checkbox.setText(datosTienda.get(i)[0] + "     Precio: " + datosTienda.get(i)[2]);
            checkbox.setWidth(1000);
            checkbox.setId(i+1);
            checkbox.setTextColor(Color.BLACK);
            checkbox.setBackgroundColor(Color.YELLOW);
            //Campo de selccion de cantidad
            EditText cantidad=new EditText(getApplicationContext());
            cantidad.setId(i+1);
            cantidad.setHint("Introduzca la cantidad deseada");


            rg.addView(checkbox);
            rg.addView(cantidad);
            //cantidad.setVisibility(View.GONE);
            cantidad.setText("0");
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] valor=new String[2];
                    if(checkbox.isChecked()){
                        //cantidad.setVisibility(View.VISIBLE);
                        //Coger el precio del producto
                        //int precio=Integer.parseInt(checkbox.getText().toString().substring(checkbox.getText().toString().indexOf(":")+1));
                       // precio=precio*Integer.parseInt(cantidad.getText().toString());

                        //displayToast(String.valueOf(precio));
                        //displayToast(String.valueOf(cantidad.getId()));
                        valor[0]=checkbox.getText().toString().substring(checkbox.getText().toString().indexOf(":")+1);
                        valor[1]=String.valueOf(cantidad.getText());
                        valoresProducto.add(valor);
                        displayToast(checkbox.getText().toString().substring(checkbox.getText().toString().indexOf(":")+1));
                    }

                    if(!checkbox.isChecked()){
                        //cantidad.setVisibility(View.GONE);
                        if(valoresProducto.contains(valor)){
                            valoresProducto.remove(valores);
                        }
                    }

                }
            });
            comprarboton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double total=0.0;
                    if(valoresProducto.isEmpty()){
                        displayToast("Seleccione un producto");
                    }else{
                        for(int i=0;i<valoresProducto.size();i++){
                            Double valor1=Double.parseDouble(valoresProducto.get(i)[0].trim());
                            Double valor2=Double.parseDouble(valoresProducto.get(i)[1].trim());
                            total=total+(valor1*valor2);
                        }
                        TextView text=findViewById(R.id.textView3);
                        text.setText("El precio total es:" + total.toString());
                    }

                    TextView text=findViewById(R.id.monedero);
                    text.setText("El saldo del monedero es: " + String.valueOf(DB.cobro(total)));
                }
            });


        }





        }




    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
