package com.marticurto.actividad2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marticurto.actividad2.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class ExtraFrutas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_frutas)

        //creamos variables
        val btShow:Button=findViewById(R.id.btSHow)
        var etFila:EditText=findViewById(R.id.etFila)
        var etColumna:EditText=findViewById(R.id.etColumna)

        //damos funcionalidad al boton
        btShow.setOnClickListener {
            val fila:Int=getEntry(etFila)
            val columna:Int=getEntry(etColumna)

            showFruit(fila, columna)
        }

    }

    /**
     * Recorta una porcion del bitmat y lo muestra en una imagen
     *
     * @param fila
     * @param columna
     */
    private fun showFruit(fila:Int,columna:Int){
        //creamos variable para trabajar con el imageView y la hacemos visible
        val ivFrutas = findViewById<ImageView>(R.id.ivRecorte)
        ivFrutas.visibility=View.VISIBLE

        //creamos un bitmap para trabajar con la imagen inicial y la recortamos al gusto
        var imgFrutas:Bitmap = BitmapFactory.decodeResource(resources, R.drawable.allfruits)

        imgFrutas = Bitmap.createBitmap(
            imgFrutas,
            columna * imgFrutas.width / 4,
            fila * imgFrutas.height / 4,
            imgFrutas.width / 4,
            imgFrutas.height / 4
        )
        //modificamos la imagen
        ivFrutas.setImageBitmap(imgFrutas)
    }

    /**
     * Comprueva que un valor sea menos a 3 y lo retorna con un valor maximo de 3
     *
     * @param entry int a comprobar
     * @return int menor a 3
     */
    private fun checkValue(entry:Int):Int{
        val result:Int
        if(entry>3) {
            result=3
        }else{
            result=entry
        }
        return result
    }

    /**
     * Obten el valor del edittext como int
     *
     * @param etEntry
     * @return int con el valor entre 0 y 3
     */
    private fun getEntry(etEntry:EditText):Int{
        //obtenemos el valor del editext
        val entry:String= etEntry.text.toString()
        var result: Int
        //lo pasamos a Int y en caso de que sea null devolvemos 0
        if(!entry.isNullOrEmpty()) {
            result= entry.toInt()
        }else{
            result=0
        }
        return checkValue(result)
    }
}