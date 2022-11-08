package com.marticurto.actividad2.clases

class Moneda(var iniciales:String,var valor:String){

    override fun toString(): String {
        return "Moneda:"+iniciales+" - Cambio:"+valor
    }
}
