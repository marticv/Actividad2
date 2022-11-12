package com.marticurto.actividad2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDB(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    /**
     * Controlamos que se hace al crear el objeto que controla la conxion, en nuestro caso crear una tabla con 3 campos
     *
     * @param db
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table monedas(id int primary key, currency text, ratio text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}