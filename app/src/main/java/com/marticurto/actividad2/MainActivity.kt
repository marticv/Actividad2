package com.marticurto.actividad2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Creamos un intent para conectar con la activity del primer ejercicio
     *
     * @param view
     */
    fun initiateFirstExercise(view: View){
        val intent= Intent(this, Adaptadoresylistas::class.java)
        startActivity(intent)
    }
}