package com.farmagro.tomapedido.modelo;

public class RespuestaArchivo {
    private int iCodRespuesta;
    private String sContenido;
    private String sNomArchivo;
    private String sRespuesta;

    public int getiCodRespuesta() {
        return iCodRespuesta;
    }

    public void setiCodRespuesta(int iCodRespuesta) {
        this.iCodRespuesta = iCodRespuesta;
    }

    public String getsContenido() {
        return sContenido;
    }

    public void setsContenido(String sContenido) {
        this.sContenido = sContenido;
    }

    public String getsNomArchivo() {
        return sNomArchivo;
    }

    public void setsNomArchivo(String sNomArchivo) {
        this.sNomArchivo = sNomArchivo;
    }

    public String getsRespuesta() {
        return sRespuesta;
    }

    public void setsRespuesta(String sRespuesta) {
        this.sRespuesta = sRespuesta;
    }
}
