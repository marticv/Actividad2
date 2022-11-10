package com.marticurto.actividad2.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.marticurto.actividad2.R
import com.marticurto.actividad2.clases.Moneda
import java.util.ArrayList

class MonedaAdapter(context: Context?, monedas: ArrayList<Moneda>) : ArrayAdapter<Moneda?>(
    context!!, 0, monedas!! as List<Moneda?>
) {
    private class ViewHolder {
        var tvIniciales: TextView? = null
        var tvValor: TextView? = null
    }

    // El método getView se llamará tantas veces como registros tengan los datos a visualizar.
    // Si el array usado posee 10 valores el getView se llamará 10 veces
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // En a variable position tenemos la posición del array que estoy pintando.
        // El getItem es un método propio del ArrayAdapter, en este caso el tipo de adaptador usado es el de la clase "User"
        // por lo tanto el getItem nos devolverá un objeto de tipo "User" que está en la posición "position"
        // En los usos básicos de adaptadores en los spinner se usa un ArrayAdapter<string>
        // //, por lo tanto el getItem nos devolvería un String
        var convertView = convertView
        val moneda = getItem(position)
        // Validamos si nos pasan por parámetro la vista a visualizar
        // en caso que esté vacía usaremos la vista (el layout) que hemos creado para visualizar los elementes
        // el inflater se encarga de pintarlo.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moneda, parent, false)
        }
        // Creamos las variables que apuntan a los TextView definidos en el layout "item_user.xml"
        val tvIniciales = convertView!!.findViewById<View>(R.id.tvIniciales) as TextView
        val tvValor = convertView.findViewById<View>(R.id.tvValue) as TextView
        // Informamos los valores de los TextView
        tvIniciales.text = moneda!!.iniciales
        tvValor.text = moneda.valor

        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView
    }
}