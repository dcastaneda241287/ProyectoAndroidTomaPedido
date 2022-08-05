package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "USUARIO")
public class Usuario {
    @Column(name = "id")
    @PrimaryKey
    private String id;
    @Column(name = "nombreusuario")
    private String nombreusuario;
    @Column(name = "codusuario")
    private String codusuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "cobrador")
    private String cobrador;
    @Column(name = "codusu")
    private String codusu;
    /*@Column(name = "bodega")
    private String bodega;

    @Column(name = "cobrador")
    private String cobrador;

    @Column(name = "desbodega")
    private String desbodega;
    @Column(name = "despuntofactura")
    private String despuntofactura;
    @Column(name = "deszona")
    private String deszona;
    @Column(name = "puntofactura")
    private String puntofactura;
*/
    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getCodusuario() {
        return this.codusuario;
    }

    public void setCodusuario(String codusuario2) {
        this.codusuario = codusuario2;
    }

    public String getNombreusuario() {
        return this.nombreusuario;
    }

    public void setNombreusuario(String nombreusuario2) {
        this.nombreusuario = nombreusuario2;
    }


    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave2) {
        this.clave = clave2;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha2) {
        this.fecha = fecha2;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

    public String getCodusu() {
        return codusu;
    }

    public void setCodusu(String codusu) {
        this.codusu = codusu;
    }
    /*

        public String getCobrador() {
            return this.cobrador;
        }

        public void setCobrador(String cobrador2) {
            this.cobrador = cobrador2;
        }



        public String getBodega1() {
            return this.bodega;
        }

        public void setBodega1(String bodega12) {
            this.bodega = bodega12;
        }


        public String getPuntofactura() {
            return this.puntofactura;
        }

        public void setPuntofactura(String puntofactura2) {
            this.puntofactura = puntofactura2;
        }

        public String getDespuntofactura() {
            return this.despuntofactura;
        }

        public void setDespuntofactura(String despuntofactura2) {
            this.despuntofactura = despuntofactura2;
        }

        public String getDeszona1() {
            return this.deszona;
        }

        public void setDeszona1(String deszona12) {
            this.deszona = deszona12;
        }

        public String getDesbodega() {
            return this.desbodega;
        }

        public void setDesbodega(String desbodega2) {
            this.desbodega = desbodega2;
        }
    */

}
