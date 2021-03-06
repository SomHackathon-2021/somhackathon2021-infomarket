package com.example.hackaton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    public static final String DBNAME="hackaton.db";

    public BDHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

         MyDB.execSQL("create table users(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text,password text,monedero double)");
        MyDB.execSQL("Insert into users(nombre,password,monedero) values('amaury','amaury',100)");

        MyDB.execSQL("create table categoria(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre varchar(50));");
        MyDB.execSQL("insert into categoria(nombre) values('Fruteria'),('SuperMercado')");

        MyDB.execSQL("create table vendedor(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_tienda varchar(100),propietario varchar(100),idcategoria varchar(100))");
        MyDB.execSQL("insert into vendedor(nombre_tienda,propietario,idcategoria) values('Fruteria Javier','Javier',1),('SuperMercado Amaury','Amaury','2')");

        MyDB.execSQL("Create table productos(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre varchar(100),descripcion varchar(100),valor double,idTienda int)");
        MyDB.execSQL("insert into productos(nombre,descripcion,valor,idTienda) values('Piña','Fruta piña',1.34,1),('Manzana','Fruta Manzana',2.34,1),('Pañales','Pañales bebe',5.23,2),('Arroz','Arroz blanco',1.12,2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop table if exists users");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("nombre",username);
        contentValues.put("password",password);
        long result=MyDB.insert("users",null,contentValues);

        if(result==1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where nombre = ? ",new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    };

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from users where nombre = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String[]> tiendas(){
        ArrayList<String[]> tiendas=new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select nombre_tienda,propietario from vendedor",null);
        if(cursor.moveToFirst()){
            do{
                String[] tienda=new String[2];

                tienda[0]=cursor.getString(0);
                tienda[1]=cursor.getString(1);

                tiendas.add(tienda);
            }while(cursor.moveToNext());
        }
        return tiendas;
    }
    public ArrayList<String[]> datosTienda(String nombreTienda){
        ArrayList<String[]>datos=new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        int idTienda=0;
        Cursor cursor = MyDB.rawQuery("select id from vendedor where nombre_tienda='" + nombreTienda+"'",null);
        if(cursor.moveToFirst()){
            do{
                idTienda=cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        SQLiteDatabase MyDB1 = this.getReadableDatabase();
        Cursor cursor1 = MyDB1.rawQuery("select nombre,descripcion,valor from productos where idTienda=" + idTienda,null);
        if(cursor1.moveToFirst()){
            do{
                String[] datoTienda=new String[3];
                datoTienda[0]=cursor1.getString(0);
                datoTienda[1]=cursor1.getString(1);
                datoTienda[2]=String.valueOf(cursor1.getDouble(2));
                datos.add(datoTienda);
            }while(cursor1.moveToNext());
        }

        return datos;
    }


    public double cobro(double precio){
      double total=precio;
      double monedero=0;
        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = MyDB.rawQuery("select monedero from users where id=1",null);
        if(cursor.moveToFirst()){
            do{
                monedero=cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        total=monedero-precio;



      return total;
    };

}
