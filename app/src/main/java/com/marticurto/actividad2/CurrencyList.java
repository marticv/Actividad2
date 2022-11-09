package com.marticurto.actividad2;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.marticurto.actividad2.adaptadores.MonedaAdapter;
import com.marticurto.actividad2.clases.Moneda;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CurrencyList extends AppCompatActivity {
    String apiUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    ListView lvMonedas;
    ProgressDialog progressDialog;
    TextView tvTitulo;
    Context context=CurrencyList.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute(apiUrl);
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CurrencyList.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... direccion) {
            String resultado = new String();
            try {
                URL url = new URL(direccion[0]);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.connect();

                StringBuilder respuesta;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"))) {
                    respuesta = new StringBuilder();
                    String respuestaLinea;
                    while ((respuestaLinea = br.readLine()) != null) {
                        respuesta.append(respuestaLinea.trim());
                    }
                    resultado = respuesta.toString();
                }
            } catch (Exception e) {
                resultado = "error";
            } finally {
                return resultado; // se lo pasa a postExecute()
            }
        }

        @Override
        protected void onPostExecute(String mensaje) {
            tvTitulo = findViewById(R.id.textView);
            if (!mensaje.equals("error")) {
                tvTitulo.setText("Source: Internet");
                Document document = convertirStringToXMLDocument(mensaje);
                NodeList listaItem = document.getElementsByTagName("Cube");
                ArrayList arrayList = new ArrayList<Moneda>();
                for (int i = 0; i < listaItem.getLength(); i++) {
                    Element element = (Element) listaItem.item(i);
                    String var_id = element.getAttribute("currency");
                    String var_value = element.getAttribute("rate");
                    if (!var_id.equals("") && var_id != null) {
                        Moneda item = new Moneda(var_id, var_value);
                        arrayList.add(item);
                    }
                }
                MonedaAdapter adapter = new MonedaAdapter(context, arrayList);
                //ArrayAdapter adapter =new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
                ListView lvMonedas = (ListView) findViewById(R.id.lvMonedas);
                lvMonedas.setAdapter(adapter);
            }else{
                tvTitulo.setText("error de conexion");
            }

            progressDialog.dismiss();
        }
    }



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
}