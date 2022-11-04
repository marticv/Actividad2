package com.marticurto.actividad2.adaptadores
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.marticurto.actividad2.R
import com.marticurto.actividad2.clases.User
import java.util.ArrayList

class UsersAdapter  // El constructor solo recibe dos parámentro, pero le pasa al padre/madre 3
// El layout que se le pasa es el 0 debido a que se le pasa uno propio más abajo al realizar el inflate
    (context: Context?, users: ArrayList<User?>?) : ArrayAdapter<User?>(
    context!!, 0, users!!
) {
    private class ViewHolder {
        var name: TextView? = null
        var home: TextView? = null
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
        val user = getItem(position)
        // Validamos si nos pasan por parámetro la vista a visualizar
        // en caso que esté vacía usaremos la vista (el layout) que hemos creado para visualizar los elementes
        // el inflater se encarga de pintarlo.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        }
        // Creamos las variables que apuntan a los TextView definidos en el layout "item_user.xml"
        val tvName = convertView!!.findViewById<View>(R.id.tvName) as TextView
        val tvHome = convertView.findViewById<View>(R.id.tvHome) as TextView
        // Informamos los valores de los TextView
        tvName.text = user!!.name
        tvHome.text = user.hometown
        //Podemos añadir eventos dentro de los elementos
        // En este caso he añadido un botón y creo el listener para que mustre un mensage con TOAST
        val button_jmh = convertView.findViewById<View>(R.id.buttonjmh) as Button
        // Defino una varieble para poder saber el contexto
        val finalConvertView_jmh = convertView
        button_jmh.setOnClickListener {
            val notificacion =
                Toast.makeText(finalConvertView_jmh.context, "hola", Toast.LENGTH_LONG)
            notificacion.show()
        }
        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView
    }
}