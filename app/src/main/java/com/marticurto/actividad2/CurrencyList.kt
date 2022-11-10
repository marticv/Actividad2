package com.marticurto.actividad2

import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.widget.TextView
import android.os.Bundle
import android.widget.Toast
import android.os.AsyncTask
import com.marticurto.actividad2.clases.Moneda
import com.marticurto.actividad2.adaptadores.MonedaAdapter
import com.marticurto.actividad2.database.SQLiteDB
import android.content.ContentValues
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ListView
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class CurrencyList : AppCompatActivity() {
    var apiUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"
    var lvMonedas: ListView? = null
    var progressDialog: ProgressDialog? = null
    var tvTitulo: TextView? = null
    var btBorrar: Button? = null
    var context: Context = this@CurrencyList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)
        var tvTitulo=findViewById<TextView>(R.id.textView)
        val myAsyncTasks = MyAsyncTasks(tvTitulo)
        myAsyncTasks.execute(apiUrl)
        val btBorrar: Button
        btBorrar = findViewById(R.id.btBorrar)
        btBorrar.setOnClickListener {
            deleteData()
            Toast.makeText(context, "Base de datos borrada", Toast.LENGTH_LONG).show()
        }
    }

    inner class MyAsyncTasks(var tvTitulo:TextView) : AsyncTask<String?, String?, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@CurrencyList)
            progressDialog!!.setMessage("Please Wait")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        protected override fun doInBackground(vararg p0: String?): String? {
            var resultado = String()
            try {
                val url = URL(p0[0])
                val conexion = url.openConnection() as HttpURLConnection
                conexion.requestMethod = "GET"
                conexion.connect()
                var respuesta: StringBuilder
                BufferedReader(InputStreamReader(conexion.inputStream, "utf-8")).use { br ->
                    respuesta = StringBuilder()
                    var respuestaLinea: String
                    while (br.readLine().also { respuestaLinea = it } != null) {
                        respuesta.append(respuestaLinea.trim { it <= ' ' })
                    }
                    resultado = respuesta.toString()
                }
            } catch (e: Exception) {
                resultado = "error"
                val monedasFromDB = dataFromDB
                tvTitulo = findViewById(R.id.textView)
                tvTitulo.setText("Source: Database")
                val adapter = MonedaAdapter(context, monedasFromDB)
                val lvMonedas = findViewById<View>(R.id.lvMonedas) as ListView
                lvMonedas.adapter = adapter
            } finally {
                return resultado // se lo pasa a postExecute()
            }
        }

        override fun onPostExecute(mensaje: String) {
            tvTitulo = findViewById(R.id.textView)
            if (mensaje != "error") {
                tvTitulo.setText("Source: Internet")
                val document = convertirStringToXMLDocument(mensaje)
                val listaItem = document!!.getElementsByTagName("Cube")
                val arrayList: ArrayList<Moneda> = ArrayList<Moneda>()
                for (i in 0 until listaItem.length) {
                    val element = listaItem.item(i) as Element
                    val var_id = element.getAttribute("currency")
                    val var_value = element.getAttribute("rate")
                    if (var_id != "" && var_id != null) {
                        val item:Moneda = Moneda(var_id, var_value)
                        arrayList.add(item)
                    }
                }
                //pasamos los datos a la base de datos para evitar errores de coneion
                insertData(arrayList)
                val adapter = MonedaAdapter(context, arrayList)
                //ArrayAdapter adapter =new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
                val lvMonedas = findViewById<View>(R.id.lvMonedas) as ListView
                lvMonedas.adapter = adapter
            }
            progressDialog!!.dismiss()
        }
    }

    fun insertData(monedas: ArrayList<Moneda>) {
        //creamos la conexion a la base de datos
        val admin = SQLiteDB(context, "Administration", null, 1)
        val db = admin.readableDatabase

        //insertamos los datos del array a la base de datos
        for (i in monedas.indices) {
            val registro = ContentValues()
            registro.put("id", i)
            registro.put("currency", monedas[i].iniciales)
            registro.put("ratio", monedas[i].valor)
            db.insert("monedas", null, registro)
        }
    }

    //creamos la conexion a la base de datos
    val dataFromDB: ArrayList<Moneda>
        get() {
            //creamos la conexion a la base de datos
            val admin = SQLiteDB(context, "Administration", null, 1)
            val db = admin.readableDatabase

            //hacemos la consulta
            val query = "select * from monedas"
            val c = db.rawQuery(query, null)

            //creamos una lista donde guardamos las monedas
            val resultados = ArrayList<Moneda>()
            if (c.count > 0) {
                while (c.moveToNext()) {
                    val columnaNombre = c.getColumnIndex("currency")
                    val columnaCambio = c.getColumnIndex("ratio")
                    if (columnaNombre >= 0 && columnaCambio >= 0) {
                        val nombre = c.getString(columnaNombre)
                        val ratio = c.getString(columnaCambio)
                        val moneda = Moneda(nombre, ratio)
                        resultados.add(moneda)
                    }
                }
            }
            //cerramos conexion y devolvemos el resultado
            admin.close()
            return resultados
        }

    fun deleteData() {
        //creamos la conexion a la base de datos
        val admin = SQLiteDB(context, "Administration", null, 1)
        val db = admin.readableDatabase
        db.execSQL("delete from monedas")
        admin.close()
    }

    companion object {
        private fun convertirStringToXMLDocument(xmlString: String): Document? {
            val factory = DocumentBuilderFactory.newInstance()
            val builder: DocumentBuilder
            try {
                builder = factory.newDocumentBuilder()
                return builder.parse(InputSource(StringReader(xmlString)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}