package com.marticurto.actividad2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.marticurto.actividad2.adaptadores.FruitsAdapter
import com.marticurto.actividad2.clases.Fruit
import kotlin.collections.ArrayList



/**
 * @author Martí Curto Vendrell
 *
 */
class Frutas : AppCompatActivity() {
    private lateinit var lvFrutas:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frutas)
        printList()
    }

    /**
     * Rellana la lista con los diferentes item fruta incluidos en la funcion
     *
     */
    private fun printList(){
        // Creamos los datos
        val myFruitList = ArrayList<Fruit?>()
        myFruitList.add(Fruit(findFruit(0,0),"Coco"))
        myFruitList.add(Fruit(findFruit(0,1),"Aguacate"))
        myFruitList.add(Fruit(findFruit(0,2),"Melon"))
        myFruitList.add(Fruit(findFruit(0,3),"Platano"))
        myFruitList.add(Fruit(findFruit(1,0),"Naranja"))
        myFruitList.add(Fruit(findFruit(1,1),"Ciruel"))
        myFruitList.add(Fruit(findFruit(1,2),"Piña"))
        myFruitList.add(Fruit(findFruit(1,3),"Limon"))
        myFruitList.add(Fruit(findFruit(2,0),"Fresa"))
        myFruitList.add(Fruit(findFruit(2,1),"Pera"))
        myFruitList.add(Fruit(findFruit(2,2),"Sandia"))
        myFruitList.add(Fruit(findFruit(2,3),"Uva"))
        myFruitList.add(Fruit(findFruit(3,0),"Manzana"))
        myFruitList.add(Fruit(findFruit(3,1),"Chirimoya"))
        myFruitList.add(Fruit(findFruit(3,2),"Kiwi"))
        myFruitList.add(Fruit(findFruit(3,3),"Cereza"))


        // Definimos el adaptador propio y lo ligamos al listView
        val fruitsAdapter = FruitsAdapter(this, myFruitList)
        lvFrutas = findViewById<View>(R.id.lvFrutas) as ListView
        lvFrutas.adapter = fruitsAdapter
    }

    /**
     * Obtenemos un Bitmap recortado a partir de la imagen inicial
     *
     * @param fila
     * @param columna
     * @return
     */
    private fun findFruit(fila:Int,columna:Int):Bitmap{

        //creamos un bitmap para trabajar con la imagen inicial y la recortamos al gusto
        var imgFrutas: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.allfruits)

        imgFrutas = Bitmap.createBitmap(
            imgFrutas,
            columna * imgFrutas.width / 4,
            fila * imgFrutas.height / 4,
            imgFrutas.width / 4,
            imgFrutas.height / 4
        )
        return imgFrutas
    }
}