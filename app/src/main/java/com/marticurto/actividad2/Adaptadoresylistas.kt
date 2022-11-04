package com.marticurto.actividad2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import com.marticurto.actividad2.adaptadores.UsersAdapter
import com.marticurto.actividad2.clases.User
import java.util.ArrayList

private lateinit var spSimple: Spinner
private lateinit var lvSimple1: ListView
private lateinit var btPropio: Button
private lateinit var btSimple1: Button
private lateinit var btSimple2: Button
private lateinit var btFrutas: Button


class Adaptadoresylistas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptadoresylistas)

        //asociamos views a variables
        btSimple1=findViewById(R.id.btSimple1)
        btSimple2=findViewById(R.id.btSimple2)
        btPropio=findViewById(R.id.btPropio)
        btFrutas=findViewById(R.id.btFrutas)
        lvSimple1=findViewById(R.id.lvSimple1)


        //EL spinner siempre sera visible
        llamadaAdaptadorSimpleSpinner()

        //damos funcionalidad al boton simple1
        btSimple1.setOnClickListener {
            llamadaAdaptadorSimpleListViewConstructor1()
        }

        //damos funcionalidad al boton simple2
        btSimple2.setOnClickListener {
            llamadaAdaptadorSimpleListViewConstructor2()
        }

        //damos funcionalidad al boton propio
        btPropio.setOnClickListener {

            llamadaAdaptadorPropioListView()
        }

    }

    /**
     * Rellena un spinner con los datos predeterminados
     *
     */
    private fun llamadaAdaptadorSimpleSpinner(){
        //creamos el array con los datos a visualizar y una variable para controlar el spinner
        var options:Array<String> = arrayOf("hola","adios","Martí Curto")
        spSimple=findViewById(R.id.spSimple)

        //definimos el adaptador y lo pasamos al spinner
        var myAdapter: ArrayAdapter<String> = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, options)
        spSimple.adapter=myAdapter
    }

    /**
     * Rellenamos el adaptador simple con datos predeterminados
     *
     */
    private fun llamadaAdaptadorSimpleListViewConstructor1(){
        //creamos el array con los datos y la variable para controlar el lv
        var options:Array<String> = arrayOf("hola","adios","viernes")
        lvSimple1 =findViewById(R.id.lvSimple1)

        //creamos el adaptador y lo pasamos al listview
        var adapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,options)
        lvSimple1.adapter=adapter
    }

    /**
     * Rellenamos el adaptador simple con datos predeterminados
     *
     */
    private fun llamadaAdaptadorSimpleListViewConstructor2(){
        //creamos el array con los datos y la variable para controlar el lv
        var options:Array<String> = arrayOf("hola","adios")
        lvSimple1 =findViewById(R.id.lvSimple1)

        //creamos el adaptador y lo pasamos al listview
        var adapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,options)
        lvSimple1.adapter=adapter
    }

    private fun llamadaAdaptadorPropioListView() {
        // Creamos los datos
        val arrayOfUsers_jmh = ArrayList<User?>()
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        arrayOfUsers_jmh.add(User("Nathan", "San Diego"))
        // Definimos el adaptador propio. En este caso no posee layout.
        val adapter_jmh = UsersAdapter(this, arrayOfUsers_jmh)
        // Attach the adapter to a ListView
        val listView_jmh = findViewById<View>(R.id.lvSimple1) as ListView
        listView_jmh.adapter = adapter_jmh
        // Limpiar el adaptador
        //adapter_jmh.clear();
    }

    fun initiateFruitsActivity(view: View){
        val intent= Intent(this, Frutas::class.java)
        startActivity(intent)
    }


}