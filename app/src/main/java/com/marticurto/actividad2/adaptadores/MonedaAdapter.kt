package com.marticurto.actividad2.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.marticurto.actividad2.R
import com.marticurto.actividad2.clases.Moneda
import com.marticurto.actividad2.clases.User
import java.util.ArrayList

class MonedaAdapter   (context: Context?, monedas: ArrayList<Moneda?>?) : ArrayAdapter<Moneda?>(
    context!!, 0, monedas!!) {

    private class ViewHolder {
        var tvIniciales: TextView? = null
        var tvValor: TextView? = null
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
        //obtenemos la informacion de la moneda a pintar
        var convertView = convertView
        val moneda = getItem(position)

        // Validamos si nos pasan por parámetro la vista a visualizar
        // en caso que esté vacía usaremos la vista (el layout) que hemos creado para visualizar los elementes
        // el inflater se encarga de pintarlo.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moneda, parent, false)
        }

        // Creamos las variables que apuntan a los TextView definidos en el layout "item_moneda.xml"
        val tvIniciales = convertView!!.findViewById<View>(R.id.tvIniciales) as TextView
        val tvValor = convertView.findViewById<View>(R.id.tvValue) as TextView

        // Informamos los valores de los TextView controlando que moneda no sea null
        tvIniciales.text = moneda!!.iniciales
        tvValor.text = moneda.valor

        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView
    }
}