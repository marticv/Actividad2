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

/**
 * @author José Maria Herrera
 *
 * @constructor
 * TODO
 *
 * @param context
 * @param users
 */
class UsersAdapter(context: Context?, users: ArrayList<User?>?) : ArrayAdapter<User?>(
    context!!, 0, users!!) {

    private class ViewHolder {
        var name: TextView? = null
        var home: TextView? = null
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

        // Informamos los valores de los TextView si user no es null
        tvName.text = user!!.name
        tvHome.text = user.hometown

        //añadimos funcionalidad al boton
        val btSaludar = convertView.findViewById<View>(R.id.btSaludar) as Button

        // Defino una varieble para poder saber el contexto
        val finalConvertView = convertView

        //damos funcionalidad al boton
        btSaludar.setOnClickListener {
            val notificacion =
                Toast.makeText(finalConvertView.context, "hola ${user.name}", Toast.LENGTH_LONG)
            notificacion.show()
        }
        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView
    }
}