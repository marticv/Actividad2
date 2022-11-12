package com.marticurto.actividad2.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.marticurto.actividad2.clases.Fruit
import com.marticurto.actividad2.R
import java.util.ArrayList

/**
 * Adaptador propio para añadir frutas a un ListView
 *
 * @constructor Contexto y Arraylist de frutas
 *
 * @param context
 * @param fruits
 */
class FruitsAdapter(context: Context?, fruits: ArrayList<Fruit?>?) : ArrayAdapter<Fruit?>(
    context!!, 0, fruits!!) {

    private class ViewHolder {
        var imagen: TextView? = null
        var name: TextView? = null
    }

    /**
     * Pinta cada una de las lineas que entramos como datos
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //obtenemos la informacion de la fruta a pintar
        val fruta = getItem(position)
        var convertView = convertView

        // Validamos si nos pasan por parámetro la vista a visualizar
        // en caso que esté vacía usaremos la vista (el layout) que hemos creado para visualizar los elementes
        // el inflater se encarga de pintarlo.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fruit, parent, false)
        }

        // Creamos las variables que apuntan a los TextView del layout "item_fruit.xml"
        val tvImagen = convertView!!.findViewById<View>(R.id.ivFruta) as ImageView
        val tvFruit = convertView.findViewById<View>(R.id.tvFruit) as TextView
        var tvQuantity = convertView.findViewById<View>(R.id.tvQuantity) as TextView
        val btRest = convertView.findViewById<View>(R.id.btRest) as Button
        val btAdd = convertView.findViewById<View>(R.id.btAdd) as Button

        // pasamos la info de la fruta a los imageView y textView
        if (fruta != null) {
            tvImagen.setImageBitmap(fruta.imagen)
        }
        if (fruta != null) {
            tvFruit.text = fruta.name
        }

        //Añadimos funcionalidad a los botones
        btRest.setOnClickListener {
            substractFruit(tvQuantity)
        }
        btAdd.setOnClickListener {
            addFruit(tvQuantity)
        }

        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView
    }

    /**
     * Suma 1 al numero del textView
     *
     * @param tv
     */
    private fun addFruit(tv:TextView){
        val entry:String= tv.text.toString()
        var result: Int=entry.toInt()
        result++
        tv.text=result.toString()
    }

    /**
     * Resta uno al numero del text view (minimo sera 0)
     *
     * @param tv
     */
    private fun substractFruit(tv:TextView){
        val entry:String= tv.text.toString()
        var result: Int=entry.toInt()
        if(result>0) {
            result--
            tv.text = result.toString()
        }
    }
}