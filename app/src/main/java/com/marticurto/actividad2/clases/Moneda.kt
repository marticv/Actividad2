package com.marticurto.actividad2.clases

class Moneda(var iniciales:String,var valor:String){

    /**
     * Pasa el objeto moneda a String de la forma que deseamos
     *
     * @return
     */
    override fun toString(): String {
        return "Moneda:$iniciales - Cambio:$valor"
    }
}
