package com.marticurto.actividad2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/**
 * Creamos lo neceasio para que la app funcione
 *
 * @author Martí Curto Vendrell
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //asociamos variables a los botones del xml
        val btFirstExercise:Button = findViewById(R.id.btEjercicio1)
        val btSecondExercise:Button = findViewById(R.id.btEjercicio2)
        val btExtraExercise:Button = findViewById(R.id.btExtra)

        //damos funcionalidad a los botones
        btFirstExercise.setOnClickListener {
            initiateFirstExercise()
        }

        btSecondExercise.setOnClickListener {
            initiateSecondExercise()
        }

        btExtraExercise.setOnClickListener {
            initiateExtraActivity()
        }

    }

    /**
     * Crea un intent para conectar con la activity del primer ejercicio
     *
     */
    fun initiateFirstExercise(){
        val intent= Intent(this, Adaptadoresylistas::class.java)
        startActivity(intent)
    }

    /**
     * Crea un intent para conectar con la activity del segundo ejercicio
     *
     */
    fun initiateExtraActivity(){
        val intent = Intent(this, ExtraFrutas::class.java)
        startActivity(intent)
    }

    /**
     * Crea un intent para conectar con la activity del ejercicio extra
     *
     */
    fun initiateSecondExercise(){
        val intent=Intent(this,CurrencyList::class.java)
        startActivity((intent))
    }
}