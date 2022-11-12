package com.marticurto.actividad2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.marticurto.actividad2.adaptadores.MonedaAdapter;
import com.marticurto.actividad2.clases.Moneda;
import com.marticurto.actividad2.database.SQLiteDB;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLData;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CurrencyList extends AppCompatActivity {

    //creamos las variables necesarias para la conexion y los elementos del xml
    String apiUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    ListView lvMonedas;
    ProgressDialog progressDialog;
    TextView tvTitulo;
    Button btBorrar;
    Context context=CurrencyList.this;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        //creamos la conexion en segundo plano
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute(apiUrl);

        //creamos variable para el boton y le damos funcionalidad
        btBorrar=findViewById(R.id.btBorrar);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                Toast.makeText(context,"Base de datos borrada",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Classe para hacer y controlar la conexion a internet o a la base de datos
     */
    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        /**
         * funciones que se haran antes de que se ejecute la conexion
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //informamos al usuario de que tiene que esperar
            progressDialog = new ProgressDialog(CurrencyList.this);
            progressDialog.setMessage("Espere porfavor");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        /**
         * funciones que se haran sin que lo vea el usuario
         * @param direccion
         * @return string con la respuesta a la conexion
         */
        @Override
        protected String doInBackground(String... direccion) {
            String resultado = new String();
            //intamos conectarnos a la web dada para obtener el xml
            try {
                URL url = new URL(direccion[0]);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.connect();

                //creamos un String con la info de la web obtenida
                StringBuilder respuesta;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"))) {
                    respuesta = new StringBuilder();
                    String respuestaLinea;
                    while ((respuestaLinea = br.readLine()) != null) {
                        respuesta.append(respuestaLinea.trim());
                    }
                    resultado = respuesta.toString();
                }
            //en caso de error controlamos la conexion con la base de datos
            } catch (Exception e) {
                //creamos variables necesarias
                resultado = "error";
                tvTitulo =findViewById(R.id.textView);

                //obtenemos los datos de la base de datos y los pasamos al listView
                ArrayList<Moneda> monedasFromDB = getDataFromDB();
                MonedaAdapter adapter = new MonedaAdapter(context, monedasFromDB);
                if(monedasFromDB.size()==0) {
                    tvTitulo.setText("Error de conexion");
                }else{
                    tvTitulo.setText("Source: Database");
                }
                ListView lvMonedas = (ListView) findViewById(R.id.lvMonedas);
                lvMonedas.setAdapter(adapter);
            } finally {
                //devolvemos el resultado ya sea la info obtenida de internet o un mensaje de error
                return resultado;
            }
        }

        /**
         * funciones que se haran una vez se haya obtenido o no informacion de internet
         * @param mensaje
         */
        @Override
        protected void onPostExecute(String mensaje) {
            tvTitulo = findViewById(R.id.textView);
            //si se ha obtenido informaicon de internet la pasamos al listview
            if (!mensaje.equals("error")) {
                tvTitulo.setText("Source: Internet");

                //pasamos la info a un formato xml para trabajar con el
                Document document = convertirStringToXMLDocument(mensaje);
                //obtenemos una lista con los nodes "Cube" donde hay la info que queremos y la pasamos a un arraylist de moneda
                NodeList listaItem = document.getElementsByTagName("Cube");
                ArrayList arrayList = new ArrayList<Moneda>();
                for (int i = 0; i < listaItem.getLength(); i++) {
                    Element element = (Element) listaItem.item(i);
                    String var_id = element.getAttribute("currency");
                    String var_value = element.getAttribute("rate");
                    //controlamos que no haya ningun item "Cube" sin la info que queremos
                    if (!var_id.equals("") && var_id != null) {
                        Moneda item = new Moneda(var_id, var_value);
                        arrayList.add(item);
                    }
                }
                //pasamos los datos a la base de datos para evitar errores de conexion en proximos intentos
                insertData(arrayList);

                //llenamos la lista con la info de internet
                MonedaAdapter adapter = new MonedaAdapter(context, arrayList);
                ListView lvMonedas = (ListView) findViewById(R.id.lvMonedas);
                lvMonedas.setAdapter(adapter);
            }

            //cerramos el progressdialog para que el usuario vea la info en pantalla
            progressDialog.dismiss();
        }
    }

    /**
     * Obtenemos un documento en formato xml a partir de una String
     *
     * @param xmlString
     * @return
     */
    private static Document convertirStringToXMLDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder ;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * insertamos datos obtenido de internet a la base de datos sqlite
     * @param monedas
     */
    public void insertData(ArrayList<Moneda> monedas){
        //creamos la conexion a la base de datos
        SQLiteDB admin = new SQLiteDB(context,"Administration",null,1);
        SQLiteDatabase db=admin.getReadableDatabase();

        //insertamos los datos del array a la base de datos
        for(int i =0; i<monedas.size();i++){
            ContentValues registro =new ContentValues();
            registro.put("id",i);
            registro.put("currency",monedas.get(i).getIniciales());
            registro.put("ratio",monedas.get(i).getValor());
            db.insert("monedas",null,registro);
        }
        //cerramos conexion
        admin.close();
    }

    /**
     * Obtenemos datos de la base de datos sqlite
     * @return
     */
    public ArrayList<Moneda> getDataFromDB(){
        //creamos la conexion a la base de datos
        SQLiteDB admin = new SQLiteDB(context,"Administration",null,1);
        SQLiteDatabase db=admin.getReadableDatabase();

        //hacemos la consulta
        String query="select * from monedas";
        Cursor c =db.rawQuery(query,null);

        //creamos una lista donde guardamos las monedas
        ArrayList<Moneda> resultados = new ArrayList<Moneda>();
        if(c.getCount()>0) {
            while (c.moveToNext()) {
                int columnaNombre = c.getColumnIndex("currency");
                int columnaCambio = c.getColumnIndex("ratio");
                if (columnaNombre >= 0 && columnaCambio >= 0) {
                    String nombre = c.getString(columnaNombre);
                    String ratio = c.getString(columnaCambio);
                    Moneda moneda = new Moneda(nombre, ratio);
                    resultados.add(moneda);
                }
            }
        }
        //cerramos conexion y devolvemos el resultado
        admin.close();
        return resultados;
    }

    /**
     * borra los datos de la base de datos
     */
    public void deleteData(){
        //creamos la conexion a la base de datos
        SQLiteDB admin = new SQLiteDB(context,"Administration",null,1);
        SQLiteDatabase db=admin.getReadableDatabase();

        //ejecutamos la sentencia de borrado y cerramos conexion
        db.execSQL("delete from monedas");
        admin.close();
    }
}