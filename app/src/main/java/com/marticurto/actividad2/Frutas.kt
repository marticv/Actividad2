package com.marticurto.actividad2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.marticurto.actividad2.adaptadores.FruitsAdapter
import com.marticurto.actividad2.clases.Fruit
import kotlin.collections.ArrayList

private lateinit var lvFrutas:ListView

class Frutas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frutas)
        printList()
    }

    private fun printList(){
        // Creamos los datos
        val myFruitList = ArrayList<Fruit?>()
        myFruitList.add(Fruit(1,"Manzana"))
        myFruitList.add(Fruit(2,"Pera"))
        myFruitList.add(Fruit(3,"Platano"))
        myFruitList.add(Fruit(4,"Naranja"))
        myFruitList.add(Fruit(5,"Piña"))


        // Definimos el adaptador propio y lo ligamos al listView
        val fruitsAdapter = FruitsAdapter(this, myFruitList)
        lvFrutas = findViewById<View>(R.id.lvFrutas) as ListView
        lvFrutas.adapter = fruitsAdapter

    }

}