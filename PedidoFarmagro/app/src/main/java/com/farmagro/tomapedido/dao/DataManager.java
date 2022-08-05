package com.farmagro.tomapedido.dao;

import com.farmagro.tomapedido.TableDefinition.BodegaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.CabPedidoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ClienteTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ContadoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.CreditoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.DescuentoClienteTableDefinition;
import com.farmagro.tomapedido.TableDefinition.DetPedidoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ExistenciaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.LetraTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ProductoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ProveedorTableDefinition;
import com.farmagro.tomapedido.TableDefinition.TmpDetalleTableDefinition;
import com.farmagro.tomapedido.TableDefinition.UsuarioTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorBodegaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorCorrelativoTableDefinition;
import com.farmagro.tomapedido.TableDefinition.VendedorMailTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ZonaTableDefinition;
import com.farmagro.tomapedido.TableDefinition.ZonaVendedorTableDefinition;
import com.farmagro.tomapedido.impl.BodegaDao;
import com.farmagro.tomapedido.impl.CabPedidoDao;
import com.farmagro.tomapedido.impl.ClienteDao;
import com.farmagro.tomapedido.impl.ContadoDao;
import com.farmagro.tomapedido.impl.CreditoDao;
import com.farmagro.tomapedido.impl.DescuentoClienteDAO;
import com.farmagro.tomapedido.impl.DetPedidoDao;
import com.farmagro.tomapedido.impl.ExistenciaDao;
import com.farmagro.tomapedido.impl.LetraDao;
import com.farmagro.tomapedido.impl.ProductoDao;
import com.farmagro.tomapedido.impl.ProveedorDao;
import com.farmagro.tomapedido.impl.TmpDetalleDao;
import com.farmagro.tomapedido.impl.UsuarioDao;
import com.farmagro.tomapedido.impl.VendedorBodegaDao;
import com.farmagro.tomapedido.impl.VendedorCorrelativoDao;
import com.farmagro.tomapedido.impl.VendedorMailDao;
import com.farmagro.tomapedido.impl.ZonaDao;
import com.farmagro.tomapedido.impl.ZonaVendedorDao;

import com.farmagro.tomapedido.modelo.Bodega;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Contado;
import com.farmagro.tomapedido.modelo.Credito;
import com.farmagro.tomapedido.modelo.DescuentoCliente;
import com.farmagro.tomapedido.modelo.DetPedido;
import com.farmagro.tomapedido.modelo.Existencia;
import com.farmagro.tomapedido.modelo.Letra;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.Proveedor;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.VendedorCorrelativo;
import com.farmagro.tomapedido.modelo.VendedorMail;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.modelo.ZonaVendedor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    //private BodegaDao bodegaDao = new BodegaDao(new BodegaTableDefinition(), this.database);
    private Context context;
    private SQLiteDatabase database;
    private UsuarioDao usuarioDao;
    private BodegaDao bodegaDao;
    private CabPedidoDao cabpedidoDao;
    private ClienteDao clienteDao;
    private ContadoDao contadoDao;
    private CreditoDao creditoDao;
    private DetPedidoDao detpedidoDao;
    private ExistenciaDao existenciaDao;
    private LetraDao letraDao;
    private ProductoDao productoDao;
    private ProveedorDao proveedorDao;
    private TmpDetalleDao tmpdetalleDao;
    private ZonaVendedorDao zonaVendedorDao;
    private ZonaDao zonaDao;
    private VendedorBodegaDao vendedorBodegaDao;
    private VendedorCorrelativoDao vendedorCorrelativoDao;
    private VendedorMailDao vendedorMailDao;
    private DescuentoClienteDAO descuentoClienteDAO;

    //region Encapsulados

    public CabPedidoDao getCabpedidoDao() {
        return this.cabpedidoDao;
    }

    public void setCabpedidoDao(CabPedidoDao cabpedidoDao2) {
        this.cabpedidoDao = cabpedidoDao2;
    }

    public DetPedidoDao getDetpedidoDao() {
        return this.detpedidoDao;
    }

    public void setDetpedidoDao(DetPedidoDao detpedidoDao2) {
        this.detpedidoDao = detpedidoDao2;
    }

    public TmpDetalleDao getTmpdetalleDao() {
        return this.tmpdetalleDao;
    }

    public void setTmpdetalleDao(TmpDetalleDao tmpdetalleDao2) {
        this.tmpdetalleDao = tmpdetalleDao2;
    }

    public ProveedorDao getProveedorDao() {
        return this.proveedorDao;
    }

    public void setProveedorDao(ProveedorDao proveedorDao2) {
        this.proveedorDao = proveedorDao2;
    }

    public ContadoDao getContadoDao() {
        return this.contadoDao;
    }

    public void setContadoDao(ContadoDao contadoDao2) {
        this.contadoDao = contadoDao2;
    }

    public CreditoDao getCreditoDao() {
        return this.creditoDao;
    }

    public void setCreditoDao(CreditoDao creditoDao2) {
        this.creditoDao = creditoDao2;
    }

    public LetraDao getLetraDao() {
        return this.letraDao;
    }

    public void setLetraDao(LetraDao letraDao2) {
        this.letraDao = letraDao2;
    }

    public ClienteDao getClienteDao() {
        return this.clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao2) {
        this.clienteDao = clienteDao2;
    }

    public ExistenciaDao getExistenciaDao() {
        return this.existenciaDao;
    }

    public void setExistenciaDao(ExistenciaDao existenciaDao2) {
        this.existenciaDao = existenciaDao2;
    }

    public BodegaDao getBodegaDao() {
        return this.bodegaDao;
    }

    public void setBodegaDao(BodegaDao bodegaDao2) {
        this.bodegaDao = bodegaDao2;
    }

    public ProductoDao getProductoDao() {
        return this.productoDao;
    }

    public void setProductoDao(ProductoDao productoDao2) {
        this.productoDao = productoDao2;
    }

    public UsuarioDao getUsuarioDao() {
        return this.usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao2) {
        this.usuarioDao = usuarioDao2;
    }

    public ZonaVendedorDao getZonaVendedorDao() {
        return zonaVendedorDao;
    }

    public void setZonaVendedorDao(ZonaVendedorDao zonaVendedorDao) {
        this.zonaVendedorDao = zonaVendedorDao;
    }

    public ZonaDao getZonaDao() {
        return zonaDao;
    }

    public void setZonaDao(ZonaDao zonaDao) {
        this.zonaDao = zonaDao;
    }

    public VendedorBodegaDao getVendedorBodegaDao() {
        return vendedorBodegaDao;
    }

    public void setVendedorBodegaDao(VendedorBodegaDao vendedorBodegaDao) {
        this.vendedorBodegaDao = vendedorBodegaDao;
    }

    public VendedorCorrelativoDao getVendedorCorrelativoDao() {
        return vendedorCorrelativoDao;
    }

    public void setVendedorCorrelativoDao(VendedorCorrelativoDao vendedorCorrelativoDao) {
        this.vendedorCorrelativoDao = vendedorCorrelativoDao;
    }

    public VendedorMailDao getVendedorMailDao() {
        return vendedorMailDao;
    }

    public void setVendedorMailDao(VendedorMailDao vendedorMailDao) {
        this.vendedorMailDao = vendedorMailDao;
    }

    public DescuentoClienteDAO getDescuentoClienteDAO() {
        return descuentoClienteDAO;
    }

    public void setDescuentoClienteDAO(DescuentoClienteDAO descuentoClienteDAO) {
        this.descuentoClienteDAO = descuentoClienteDAO;
    }

    //endregion

    //region Consultando Registros

    public Usuario getUsuario() {
        return (Usuario) getUsuarioDao().get(String.valueOf("1"));
    }

    public List<DetPedido> getDetPedidoByIdList(long idPedido) {
        List<DetPedido> lista = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM DETPEDIDO WHERE idPedido = " + idPedido, (String[]) null);
        if (cs.moveToFirst()) {
            do {
                DetPedido bean = new DetPedido();
                bean.setIdDetalle(Long.valueOf(cs.getLong(cs.getColumnIndex("idDetalle"))));
                bean.setIdPedido(Long.valueOf(cs.getLong(cs.getColumnIndex("idPedido"))));
                bean.setCodproducto(cs.getString(cs.getColumnIndex("codproducto")));
                bean.setDesproducto(cs.getString(cs.getColumnIndex("desproducto")));
                bean.setPrecio(Float.valueOf(cs.getFloat(cs.getColumnIndex("precio"))));
                bean.setCantidad(Float.valueOf(cs.getFloat(cs.getColumnIndex("cantidad"))));
                bean.setDescuento(Float.valueOf(cs.getFloat(cs.getColumnIndex("descuento"))));
                bean.setTotal(Float.valueOf(cs.getFloat(cs.getColumnIndex("total"))));
                bean.setUndventa(cs.getString(cs.getColumnIndex("undventa")));
                bean.setIgv(Float.valueOf(cs.getFloat(cs.getColumnIndex("igv"))));
                bean.setCodbodega(cs.getString(cs.getColumnIndex("codbodega")));
                lista.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return lista;
    }

    public List<Bodega> getBodegaList() {
        List<Bodega> lista = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM BODEGA ORDER BY nombre ASC", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Bodega bean = new Bodega();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setNombre(cs.getString(cs.getColumnIndex("nombre")));
                lista.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return lista;
    }

    public List<TmpDetalle> getTmpDetalleList() {
        List<TmpDetalle> lista = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM TMPDETALLE", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                TmpDetalle bean = new TmpDetalle();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodproducto(cs.getString(cs.getColumnIndex("codproducto")));
                bean.setDesproducto(cs.getString(cs.getColumnIndex("desproducto")));
                bean.setPrecio(Float.valueOf(cs.getFloat(cs.getColumnIndex("precio"))));
                bean.setCantidad(Float.valueOf(cs.getFloat(cs.getColumnIndex("cantidad"))));
                bean.setDescuento(Float.valueOf(cs.getFloat(cs.getColumnIndex("descuento"))));
                bean.setTotal(Float.valueOf(cs.getFloat(cs.getColumnIndex("total"))));
                bean.setUndventa(cs.getString(cs.getColumnIndex("undventa")));
                bean.setIgv(Float.valueOf(cs.getFloat(cs.getColumnIndex("igv"))));
                bean.setVcodalmacen(cs.getString(cs.getColumnIndex("vcodalmacen")));
                bean.setDescuentoestructurado(cs.getString(cs.getColumnIndex("descuentoestructurado")));
                lista.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return lista;
    }

    public List<Existencia> getStockById(String codproducto, String campo) {
        List<Existencia> lista = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM EXISTENCIA WHERE codProducto = '" + codproducto + "'", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Existencia bean = new Existencia();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));
                bean.setUnidadVenta(cs.getString(cs.getColumnIndex("unidadVenta")));
                bean.setCodProducto(cs.getString(cs.getColumnIndex("codProducto")));
                bean.setvCodBodega(cs.getString(cs.getColumnIndex("vCodBodega")));
                bean.setCantTransito(cs.getString(cs.getColumnIndex("cantTransito")));
                bean.setdCanDisponible(cs.getString(cs.getColumnIndex("dCanDisponible")));
                lista.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return lista;
    }

    public int getTotalClientes() {
        Cursor cs = getDatabase().rawQuery("SELECT count(*) FROM CLIENTE", (String[]) null);
        cs.moveToFirst();
        int total = cs.getInt(0);
        cs.close();
        return total;
    }

    public int getTotalZonaVendedor(String codUsu) {
        Cursor cs = getDatabase().rawQuery("SELECT count(*) FROM ZonaVendedor WHERE iCodUsu = '" + codUsu + "'", (String[]) null);
        cs.moveToFirst();
        int total = cs.getInt(0);
        cs.close();
        return total;
    }

    public List<Credito> getCreditoList() {
        List<Credito> creditos = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM CREDITO ", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Credito bean = new Credito();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodcondicion(cs.getString(cs.getColumnIndex("codcondicion")));
                bean.setDescondicion(cs.getString(cs.getColumnIndex("descondicion")));
                bean.setDiasneto(cs.getString(cs.getColumnIndex("diasneto")));
                creditos.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return creditos;
    }

    public List<Letra> getLetraList() {
        List<Letra> letras = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM LETRA ORDER BY id desc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Letra bean = new Letra();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodcondicion(cs.getString(cs.getColumnIndex("codcondicion")));
                bean.setDescondicion(cs.getString(cs.getColumnIndex("descondicion")));
                bean.setDiasneto(cs.getString(cs.getColumnIndex("diasneto")));
                letras.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return letras;
    }

    public List<Contado> getContadoList() {
        List<Contado> contados = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM CONTADO ", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Contado bean = new Contado();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodcondicion(cs.getString(cs.getColumnIndex("codcondicion")));
                bean.setDescondicion(cs.getString(cs.getColumnIndex("descondicion")));
                bean.setDiasneto(cs.getString(cs.getColumnIndex("diasneto")));
                contados.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return contados;
    }

    public CabPedido getPedidoById(long id) {
        CabPedido bean = new CabPedido();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM CABPEDIDO WHERE id =" + id, (String[]) null);
        if (cs.moveToFirst()) {
            bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
            bean.setNumero(cs.getString(cs.getColumnIndex("numero")));
            bean.setCliente(cs.getString(cs.getColumnIndex("cliente")));
            bean.setNombcliente(cs.getString(cs.getColumnIndex("nombcliente")));
            bean.setFechaprom(cs.getString(cs.getColumnIndex("fechaprom")));
            bean.setTipoventa(cs.getString(cs.getColumnIndex("tipoventa")));
            bean.setFormapago(cs.getString(cs.getColumnIndex("formapago")));
            bean.setDocumento(cs.getString(cs.getColumnIndex("documento")));
            bean.setMoneda(cs.getString(cs.getColumnIndex("moneda")));
            bean.setNivelprecio(cs.getString(cs.getColumnIndex("nivelprecio")));
            bean.setAlmacen(cs.getString(cs.getColumnIndex("almacen")));
            bean.setCondicionpago(cs.getString(cs.getColumnIndex("condicionpago")));
            bean.setLetras(cs.getString(cs.getColumnIndex("letras")));
            bean.setInicioletra(cs.getString(cs.getColumnIndex("inicioletra")));
            bean.setSeparacion(cs.getString(cs.getColumnIndex("separacion")));
            bean.setDireccion(cs.getString(cs.getColumnIndex("direccion")));
            bean.setCoddefault(cs.getString(cs.getColumnIndex("coddefault")));
            bean.setCodigotransp(cs.getString(cs.getColumnIndex("codigotransp")));
            bean.setNombretransp(cs.getString(cs.getColumnIndex("nombretransp")));
            bean.setDirecccobro(cs.getString(cs.getColumnIndex("direcccobro")));
            bean.setMonflete(cs.getString(cs.getColumnIndex("monflete")));
            bean.setOrdencompra(cs.getString(cs.getColumnIndex("ordencompra")));
            bean.setFechaorden(cs.getString(cs.getColumnIndex("fechaorden")));
            bean.setObservacion(cs.getString(cs.getColumnIndex("observacion")));
            bean.setEstado(cs.getString(cs.getColumnIndex("estado")));
            bean.setTotalmercaderia(cs.getString(cs.getColumnIndex("totalmercaderia")));
            bean.setTotaligv(cs.getString(cs.getColumnIndex("totaligv")));
            bean.setTotalfacturar(cs.getString(cs.getColumnIndex("totalfacturar")));
            bean.setTotalunidad(cs.getString(cs.getColumnIndex("totalunidad")));
            bean.setDialibre(cs.getString(cs.getColumnIndex("dialibre")));
            bean.setZona(cs.getString(cs.getColumnIndex("zona")));
            bean.setRequiereoc(cs.getString(cs.getColumnIndex("requiereoc")));
            bean.setLatitud(cs.getString(cs.getColumnIndex("latitud")));
            bean.setLongitud(cs.getString(cs.getColumnIndex("longitud")));
            bean.setTpedido(cs.getString(cs.getColumnIndex("tpedido")));
            bean.setFoto(cs.getString(cs.getColumnIndex("foto")));
            bean.setDescuento(cs.getString(cs.getColumnIndex("descuento")));
            bean.setDirecciongps(cs.getString(cs.getColumnIndex("direcciongps")));
        }
        cs.close();
        return bean;
    }

    public List<CabPedido> getPedidosPendList() {
        List<CabPedido> pedidos = new ArrayList<>();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM CABPEDIDO ", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                CabPedido bean = new CabPedido();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setNumero(cs.getString(cs.getColumnIndex("numero")));
                bean.setCliente(cs.getString(cs.getColumnIndex("cliente")));
                bean.setNombcliente(cs.getString(cs.getColumnIndex("nombcliente")));
                bean.setFechaprom(cs.getString(cs.getColumnIndex("fechaprom")));
                bean.setTipoventa(cs.getString(cs.getColumnIndex("tipoventa")));
                bean.setFormapago(cs.getString(cs.getColumnIndex("formapago")));
                bean.setDocumento(cs.getString(cs.getColumnIndex("documento")));
                bean.setMoneda(cs.getString(cs.getColumnIndex("moneda")));
                bean.setNivelprecio(cs.getString(cs.getColumnIndex("nivelprecio")));
                bean.setAlmacen(cs.getString(cs.getColumnIndex("almacen")));
                bean.setCondicionpago(cs.getString(cs.getColumnIndex("condicionpago")));
                bean.setLetras(cs.getString(cs.getColumnIndex("letras")));
                bean.setInicioletra(cs.getString(cs.getColumnIndex("inicioletra")));
                bean.setSeparacion(cs.getString(cs.getColumnIndex("separacion")));
                bean.setDireccion(cs.getString(cs.getColumnIndex("direccion")));
                bean.setCoddefault(cs.getString(cs.getColumnIndex("coddefault")));
                bean.setCodigotransp(cs.getString(cs.getColumnIndex("codigotransp")));
                bean.setNombretransp(cs.getString(cs.getColumnIndex("nombretransp")));
                bean.setDirecccobro(cs.getString(cs.getColumnIndex("direcccobro")));
                bean.setMonflete(cs.getString(cs.getColumnIndex("monflete")));
                bean.setOrdencompra(cs.getString(cs.getColumnIndex("ordencompra")));
                bean.setFechaorden(cs.getString(cs.getColumnIndex("fechaorden")));
                bean.setObservacion(cs.getString(cs.getColumnIndex("observacion")));
                bean.setEstado(cs.getString(cs.getColumnIndex("estado")));
                bean.setTotalmercaderia(cs.getString(cs.getColumnIndex("totalmercaderia")));
                bean.setTotaligv(cs.getString(cs.getColumnIndex("totaligv")));
                bean.setTotalfacturar(cs.getString(cs.getColumnIndex("totalfacturar")));
                bean.setTotalunidad(cs.getString(cs.getColumnIndex("totalunidad")));
                bean.setDialibre(cs.getString(cs.getColumnIndex("dialibre")));
                bean.setZona(cs.getString(cs.getColumnIndex("zona")));
                bean.setRequiereoc(cs.getString(cs.getColumnIndex("requiereoc")));
                bean.setLatitud(cs.getString(cs.getColumnIndex("latitud")));
                bean.setLongitud(cs.getString(cs.getColumnIndex("longitud")));
                bean.setTpedido(cs.getString(cs.getColumnIndex("tpedido")));
                bean.setFoto(cs.getString(cs.getColumnIndex("foto")));
                bean.setDescuento(cs.getString(cs.getColumnIndex("descuento")));
                bean.setDirecciongps(cs.getString(cs.getColumnIndex("direcciongps")));
                pedidos.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return pedidos;
    }

    public Producto getProductoByCodigoAndAlmacen(String codigo, String almacen) {
        Producto bean = new Producto();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM PRODUCTO WHERE codproducto = '" + codigo + "' And vCodbodega = '" + almacen +"'", (String[]) null);
        if (cs.moveToFirst()) {
            bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
            bean.setCodproducto(cs.getString(cs.getColumnIndex("codproducto")));
            bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));
            bean.setStockproducto(cs.getString(cs.getColumnIndex("stockproducto")));
            bean.setPreciodol(cs.getString(cs.getColumnIndex("preciodol")));
            bean.setPreciosol(cs.getString(cs.getColumnIndex("preciosol")));
            bean.setIgvdol(cs.getString(cs.getColumnIndex("igvdol")));
            bean.setIgvsol(cs.getString(cs.getColumnIndex("igvsol")));
            bean.setUnidadventa(cs.getString(cs.getColumnIndex("unidadventa")));
            bean.setPreciorealdol(cs.getString(cs.getColumnIndex("preciorealdol")));
            bean.setPreciorealsol(cs.getString(cs.getColumnIndex("preciorealsol")));
            bean.setTipo(cs.getString(cs.getColumnIndex("tipo")));
        }
        cs.close();
        return bean;
    }

    public Producto getProductoByCodigo(String codigo) {
        Producto bean = new Producto();
        Cursor cs = getDatabase().rawQuery("SELECT * FROM PRODUCTO WHERE codproducto = '" + codigo + "'", (String[]) null);
        if (cs.moveToFirst()) {
            bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
            bean.setCodproducto(cs.getString(cs.getColumnIndex("codproducto")));
            bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));
            bean.setStockproducto(cs.getString(cs.getColumnIndex("stockproducto")));
            bean.setPreciodol(cs.getString(cs.getColumnIndex("preciodol")));
            bean.setPreciosol(cs.getString(cs.getColumnIndex("preciosol")));
            bean.setIgvdol(cs.getString(cs.getColumnIndex("igvdol")));
            bean.setIgvsol(cs.getString(cs.getColumnIndex("igvsol")));
            bean.setUnidadventa(cs.getString(cs.getColumnIndex("unidadventa")));
            bean.setPreciorealdol(cs.getString(cs.getColumnIndex("preciorealdol")));
            bean.setPreciorealsol(cs.getString(cs.getColumnIndex("preciorealsol")));
            bean.setTipo(cs.getString(cs.getColumnIndex("tipo")));
        }
        cs.close();
        return bean;
    }

    public List<Producto> getProductoList(String input, int tipo, String nprecio) {
        String query;
        List<Producto> productos = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("Select codproducto, descripcion, id,igvdol,igvsol, preciodol, preciosol, preciorealdol, preciorealsol, stockproducto,tipo,unidadventa,vCodBodega From PRODUCTO ") + " WHERE Cast(stockproducto as decimal(18,2)) > 0 And codproducto like ";
        } else {
            query = String.valueOf("Select codproducto, descripcion, id,igvdol,igvsol, preciodol, preciosol, preciorealdol, preciorealsol, stockproducto,tipo,unidadventa,vCodBodega From PRODUCTO ") + " WHERE Cast(stockproducto as decimal(18,2)) > 0 And descripcion like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' and tipo = '" + nprecio + "' order by descripcion ASC", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Producto bean = new Producto();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodproducto(cs.getString(cs.getColumnIndex("codproducto")));
                bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));
                bean.setStockproducto(cs.getString(cs.getColumnIndex("stockproducto")));
                bean.setPreciodol(cs.getString(cs.getColumnIndex("preciodol")));
                bean.setPreciosol(cs.getString(cs.getColumnIndex("preciosol")));
                bean.setIgvdol(cs.getString(cs.getColumnIndex("igvdol")));
                bean.setIgvsol(cs.getString(cs.getColumnIndex("igvsol")));
                bean.setUnidadventa(cs.getString(cs.getColumnIndex("unidadventa")));
                bean.setPreciorealdol(cs.getString(cs.getColumnIndex("preciorealdol")));
                bean.setPreciorealsol(cs.getString(cs.getColumnIndex("preciorealsol")));
                bean.setTipo(cs.getString(cs.getColumnIndex("tipo")));
                bean.setvCodBodega(cs.getString(cs.getColumnIndex("vCodBodega")));
                productos.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return productos;
    }

    public List<Proveedor> getTransportistaList(String input, int tipo) {
        String query;
        List<Proveedor> proveedores = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM PROVEEDOR ") + " WHERE codigo like ";
        } else {
            query = String.valueOf("SELECT * FROM PROVEEDOR ") + " WHERE descripcion like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by descripcion asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Proveedor bean = new Proveedor();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
                bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));
                bean.setvRuc(cs.getString(cs.getColumnIndex("vRuc")));

                proveedores.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return proveedores;
    }

    public List<Cliente> getClienteList(String input, int tipo) {
        String query;
        List<Cliente> clientes = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM CLIENTE ") + " WHERE codigo like ";
        } else {
            query = String.valueOf("SELECT * FROM CLIENTE ") + " WHERE nombre like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by nombre asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Cliente bean = new Cliente();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
                bean.setNombre(cs.getString(cs.getColumnIndex("nombre")));
                bean.setDireccion(cs.getString(cs.getColumnIndex("direccion")));
                bean.setDirembarque(cs.getString(cs.getColumnIndex("dirembarque")));
                bean.setSaldo(cs.getString(cs.getColumnIndex("saldo")));
                bean.setSaldoDolar(cs.getString(cs.getColumnIndex("saldoDolar")));
                bean.setLimiteCredido(cs.getString(cs.getColumnIndex("limiteCredido")));
                bean.setCondicionPago(cs.getString(cs.getColumnIndex("condicionPago")));
                bean.setNivelPrecio(cs.getString(cs.getColumnIndex("nivelPrecio")));
                bean.setDocGenerar(cs.getString(cs.getColumnIndex("docGenerar")));
                bean.setMonedaNivel(cs.getString(cs.getColumnIndex("monedaNivel")));
                bean.setDiasLibre(cs.getString(cs.getColumnIndex("diasLibre")));
                bean.setRubro8(cs.getString(cs.getColumnIndex("rubro8")));
                bean.setRubro9(cs.getString(cs.getColumnIndex("rubro9")));
                bean.setRubro10(cs.getString(cs.getColumnIndex("rubro10")));
                bean.setRequiereOC(cs.getString(cs.getColumnIndex("requiereOC")));

                bean.setvNomZona(cs.getString(cs.getColumnIndex("vNomZona")));
                bean.setvEstado(cs.getString(cs.getColumnIndex("vEstado")));
                bean.setvCobJudicial(cs.getString(cs.getColumnIndex("vCobJudicial")));
                bean.setdLimCreDolar(cs.getString(cs.getColumnIndex("dLimCreDolar")));
                bean.setdLimCreLocal(cs.getString(cs.getColumnIndex("dLimCreLocal")));
                bean.setdLimCreInsDolar(cs.getString(cs.getColumnIndex("dLimCreInsDolar")));
                bean.setdSalVenDol(cs.getString(cs.getColumnIndex("dSalVenDol")));
                bean.setiCantLetraPendiente(cs.getString(cs.getColumnIndex("iCantLetraPendiente")));
                clientes.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return clientes;
    }

    public TmpDetalle getTmpDetalleById(String codProducto) {
        TmpDetalle bean = null;
        Cursor cs = getDatabase().rawQuery("SELECT * FROM TMPDETALLE WHERE codproducto = '" + codProducto + "'", (String[]) null);
        if (cs.moveToFirst()) {
            bean = new TmpDetalle();
            bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
            bean.setDesproducto(cs.getString(cs.getColumnIndex("desproducto")));
            bean.setPrecio(Float.valueOf(cs.getFloat(cs.getColumnIndex("precio"))));
            bean.setCantidad(Float.valueOf(cs.getFloat(cs.getColumnIndex("cantidad"))));
            bean.setDescuento(Float.valueOf(cs.getFloat(cs.getColumnIndex("descuento"))));
            bean.setTotal(Float.valueOf(cs.getFloat(cs.getColumnIndex("total"))));
            bean.setUndventa(cs.getString(cs.getColumnIndex("undventa")));
            bean.setIgv(Float.valueOf(cs.getFloat(cs.getColumnIndex("igv"))));
            bean.setVcodalmacen(cs.getString(cs.getColumnIndex("vcodalmacen")));
            bean.setDescuentoestructurado(cs.getString(cs.getColumnIndex("descuentoestructurado")));
        }
        cs.close();
        return bean;
    }

    public List<ZonaVendedor> getZonaVendedorList(String input, int tipo) {
        String query;
        List<ZonaVendedor> zonavendedors = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM ZonaVendedor ") + " WHERE vCodZona like ";
        } else {
            query = String.valueOf("SELECT * FROM ZonaVendedor ") + " WHERE iCodUsu like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by vCodZona asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                ZonaVendedor bean = new ZonaVendedor();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setiCodUsu(cs.getString(cs.getColumnIndex("iCodUsu")));
                bean.setvCodZona(cs.getString(cs.getColumnIndex("vCodZona")));
                bean.setvDescripcion(cs.getString(cs.getColumnIndex("vDescripcion")));
                bean.setvNombre(cs.getString(cs.getColumnIndex("vNombre")));
                zonavendedors.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return zonavendedors;
    }

    public List<Zona> getZonaList(String input, int tipo) {
        String query;
        List<Zona> zonas = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM Zona ") + " WHERE codigo like ";
        } else {
            query = String.valueOf("SELECT * FROM Zona ") + " WHERE descripcion like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by nombre asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                Zona bean = new Zona();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
                bean.setDescripcion(cs.getString(cs.getColumnIndex("descripcion")));

                zonas.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return zonas;
    }

    public List<VendedorBodega> getVendedorBodegaList(String input, int tipo) {
        String query;
        List<VendedorBodega> vendedorBodegas = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT VendedorBodega.id, VendedorBodega.iCodUsu,VendedorBodega.vCodBodega,Bodega.nombre FROM VendedorBodega Inner Join Bodega On VendedorBodega.vCodBodega = Bodega.vCodBodega ") + " WHERE VendedorBodega.vCodBodega like ";
        } else {
            query = String.valueOf("SELECT VendedorBodega.id, VendedorBodega.iCodUsu,VendedorBodega.vCodBodega,Bodega.nombre FROM VendedorBodega Inner Join Bodega On VendedorBodega.vCodBodega = Bodega.vCodBodega ") + " WHERE VendedorBodega.iCodUsu like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by VendedorBodega.vCodBodega asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                VendedorBodega bean = new VendedorBodega();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setiCodUsu(cs.getString(cs.getColumnIndex("iCodUsu")));
                bean.setvCodBodega(cs.getString(cs.getColumnIndex("vCodBodega")));
                bean.setvDescripcion(cs.getString(cs.getColumnIndex("nombre")));
                vendedorBodegas.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return vendedorBodegas;
    }

    public List<VendedorCorrelativo> getVendedorCorrelativoList(String input, int tipo) {
        String query;
        List<VendedorCorrelativo> vendedorCorrelativos = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM VendedorCorrelativo ") + " WHERE vSerie like ";
        } else {
            query = String.valueOf("SELECT * FROM VendedorCorrelativo ") + " WHERE iCodUsu like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by vSerie asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                VendedorCorrelativo bean = new VendedorCorrelativo();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setiCodUsu(cs.getString(cs.getColumnIndex("iCodUsu")));
                bean.setvSerie(cs.getString(cs.getColumnIndex("vSerie")));
                bean.setiCorrelativo(cs.getString(cs.getColumnIndex("iCorrelativo")));
                vendedorCorrelativos.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return vendedorCorrelativos;
    }

    public List<VendedorMail> getVendedorMailList(String input, int tipo) {
        String query;
        List<VendedorMail> vendedorMails = new ArrayList<>();
        if (tipo == 0) {
            query = String.valueOf("SELECT * FROM VendedorMail ") + " WHERE vMail like ";
        } else {
            query = String.valueOf("SELECT * FROM VendedorMail ") + " WHERE iCodUsu like ";
        }
        Cursor cs = getDatabase().rawQuery(String.valueOf(query) + "'%" + input + "%' order by vMail asc", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                VendedorMail bean = new VendedorMail();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setiCodUsu(cs.getString(cs.getColumnIndex("iCodUsu")));
                bean.setvMail(cs.getString(cs.getColumnIndex("vMail")));
                vendedorMails.add(bean);
            } while (cs.moveToNext());
        }
        cs.close();
        return vendedorMails;
    }

    public DescuentoCliente DevolverDescuentoPorCLiente(String vCodCliente) {
        DescuentoCliente bean = null;
        Cursor cs = getDatabase().rawQuery("SELECT * FROM DescuentoCliente WHERE vCodCliente = '" + vCodCliente + "'", (String[]) null);
        if (cs.moveToFirst()) {
            do {
                bean = new DescuentoCliente();
                bean.setId(Long.valueOf(cs.getLong(cs.getColumnIndex("id"))));
                bean.setvCodCliente(cs.getString(cs.getColumnIndex("vCodCliente")));
                bean.setiCodDescuento(cs.getString(cs.getColumnIndex("iCodDescuento")));
                bean.setvDescripcion(cs.getString(cs.getColumnIndex("vDescripcion")));
                bean.setDeDescuento(cs.getString(cs.getColumnIndex("deDescuento")));
            } while (cs.moveToNext());
        }
        cs.close();
        return bean;
    }

    //endregion

    //region Actualizando Registros

    public long updateCabPedido(CabPedido bean) {
        try {
            getDatabase().beginTransaction();
            getDatabase().execSQL("UPDATE CABPEDIDO SET numero = ?,cliente = ?,nombcliente = ?,fechaprom = ?,tipoventa = ?,formapago = ?,documento = ?,moneda = ?,nivelprecio = ?,almacen = ?,condicionpago = ?,letras = ?,inicioletra = ?,separacion = ?,direccion = ?,coddefault = ?,codigotransp = ?,nombretransp = ?,direcccobro = ?,monflete = ?,ordencompra = ?,fechaorden = ?,observacion = ?,zona = ?,estado = ?,totalmercaderia = ?,totaligv = ?,totalfacturar = ?,totalunidad = ?,dialibre = ?,requiereoc = ?, latitud = ?, longitud = ?, tpedido = ?, foto = ?, descuento = ?, direcciongps = ? WHERE id = ?", new Object[]{bean.getNumero(), bean.getCliente(), bean.getNombcliente(), bean.getFechaprom(), bean.getTipoventa(), bean.getFormapago(), bean.getDocumento(), bean.getMoneda(), bean.getNivelprecio(), bean.getAlmacen(), bean.getCondicionpago(), bean.getLetras(), bean.getInicioletra(), bean.getSeparacion(), bean.getDireccion(), bean.getCoddefault(), bean.getCodigotransp(), bean.getNombretransp(), bean.getDirecccobro(), bean.getMonflete(), bean.getOrdencompra(), bean.getFechaorden(), bean.getObservacion(), bean.getZona(), bean.getEstado(), bean.getTotalmercaderia(), bean.getTotaligv(), bean.getTotalfacturar(), bean.getTotalunidad(), bean.getDialibre(), bean.getRequiereoc(), bean.getLatitud(), bean.getLongitud(), bean.getTpedido(), bean.getFoto(), bean.getDescuento(), bean.getDirecciongps(), bean.getId()});
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR UPDATE-CABPEDIDO", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    //endregion

    //region Guardando Registros

    public long saveUsuario(Usuario usuario) {
        long result = 0;
        try {
            getDatabase().beginTransaction();
            result = getUsuarioDao().save(usuario);
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getDatabase().endTransaction();
        return result;
    }

    public long saveDetPedido(DetPedido bean) {
        try {
            getDatabase().beginTransaction();
            getDatabase().execSQL("INSERT INTO DETPEDIDO(idPedido,codproducto,desproducto,precio,cantidad,descuento,total,undventa,igv,codbodega)VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[]{bean.getIdPedido(), bean.getCodproducto(), bean.getDesproducto(), bean.getPrecio(), bean.getCantidad(), bean.getDescuento(), bean.getTotal(), bean.getUndventa(), bean.getIgv(), bean.getCodbodega()});
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-DETPEDIDO", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveCabPedido(CabPedido bean) {
        long result = 0;
        try {
            getDatabase().beginTransaction();
            getDatabase().execSQL("INSERT INTO CABPEDIDO(numero,cliente,nombcliente,fechaprom,tipoventa,formapago,documento,moneda,nivelprecio,almacen,condicionpago,letras,inicioletra,separacion,direccion,coddefault,codigotransp,nombretransp,direcccobro,monflete,ordencompra,fechaorden,observacion,zona,estado,totalmercaderia,totaligv,totalfacturar,totalunidad,dialibre,requiereoc,latitud,longitud,tpedido,foto, descuento, direcciongps)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{bean.getNumero(), bean.getCliente(), bean.getNombcliente(), bean.getFechaprom(), bean.getTipoventa(), bean.getFormapago(), bean.getDocumento(), bean.getMoneda(), bean.getNivelprecio(), bean.getAlmacen(), bean.getCondicionpago(), bean.getLetras(), bean.getInicioletra(), bean.getSeparacion(), bean.getDireccion(), bean.getCoddefault(), bean.getCodigotransp(), bean.getNombretransp(), bean.getDirecccobro(), bean.getMonflete(), bean.getOrdencompra(), bean.getFechaorden(), bean.getObservacion(), bean.getZona(), bean.getEstado(), bean.getTotalmercaderia(), bean.getTotaligv(), bean.getTotalfacturar(), bean.getTotalunidad(), bean.getDialibre(), bean.getRequiereoc(), bean.getLatitud(), bean.getLongitud(), bean.getTpedido(), bean.getFoto(), bean.getDescuento(), bean.getDirecciongps()});
            getDatabase().setTransactionSuccessful();
            Cursor cs = getDatabase().rawQuery("SELECT ifnull(max(id),1) as id FROM CABPEDIDO", (String[]) null);
            if (cs.moveToFirst()) {
                result = cs.getLong(cs.getColumnIndex("id"));
            }
            cs.close();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-CABPEDIDO", e.toString());
        }
        getDatabase().endTransaction();
        return result;
    }

    public long saveExistencia(List<Existencia> existencias) {
        try {
            getDatabase().beginTransaction();
            for (Existencia bean : existencias) {
                getDatabase().execSQL("INSERT INTO EXISTENCIA(codProducto,descripcion,unidadVenta,cantTransito,dCanDisponible,vCodBodega)VALUES(?,?,?,?,?,?)", new Object[]{bean.getCodProducto(), bean.getDescripcion(), bean.getUnidadVenta(), bean.getCantTransito(), bean.getdCanDisponible(), bean.getvCodBodega()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-Existencia", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveTmpDetalle(TmpDetalle bean) {
        try {
            getDatabase().beginTransaction();
            getDatabase().execSQL("INSERT INTO TMPDETALLE(codproducto,desproducto,precio,cantidad,descuento,total,undventa,igv, vcodalmacen, descuentoestructurado) VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[]{bean.getCodproducto(), bean.getDesproducto(), bean.getPrecio(), bean.getCantidad(), bean.getDescuento(), bean.getTotal(), bean.getUndventa(), bean.getIgv(), bean.getVcodalmacen(), bean.getDescuentoestructurado()});
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-TMPDETALLE", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long updateTmpDetalle(TmpDetalle bean) {
        try {
            getDatabase().beginTransaction();
            getDatabase().execSQL("UPDATE TMPDETALLE SET codproducto = ?,desproducto = ?,precio = ?,cantidad = ?,descuento = ?,total = ?,undventa = ?,igv = ?, vcodalmacen = ?, descuentoestructurado = ? WHERE id = ?", new Object[]{bean.getCodproducto(), bean.getDesproducto(), bean.getPrecio(), bean.getCantidad(), bean.getDescuento(), bean.getTotal(), bean.getUndventa(), bean.getIgv(), bean.getVcodalmacen(), bean.getDescuentoestructurado(), bean.getId()});
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR UPDATE-TMPDETALLE", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveBodega(List<Bodega> bodegas) {
        try {
            getDatabase().beginTransaction();
            for (Bodega bean : bodegas) {
                getDatabase().execSQL("INSERT INTO BODEGA(nombre,vCodbodega)VALUES(?,?)", new Object[]{bean.getNombre(),bean.getvCodbodega()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-BODEGA", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveContado(List<Contado> contados) {
        try {
            getDatabase().beginTransaction();
            for (Contado bean : contados) {
                getDatabase().execSQL("INSERT INTO CONTADO(codcondicion,descondicion,diasneto)VALUES(?,?,?)", new Object[]{bean.getCodcondicion(), bean.getDescondicion(), bean.getDiasneto()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-CONTADO", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveCredito(List<Credito> creditos) {
        try {
            getDatabase().beginTransaction();
            for (Credito bean : creditos) {
                getDatabase().execSQL("INSERT INTO CREDITO(codcondicion,descondicion,diasneto)VALUES(?,?,?)", new Object[]{bean.getCodcondicion(), bean.getDescondicion(), bean.getDiasneto()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-CREDITO", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveLetra(List<Letra> letras) {
        try {
            getDatabase().beginTransaction();
            for (Letra bean : letras) {
                getDatabase().execSQL("INSERT INTO LETRA(codcondicion,descondicion,diasneto)VALUES(?,?,?)", new Object[]{bean.getCodcondicion(), bean.getDescondicion(), bean.getDiasneto()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-LETRA", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveProveedor(List<Proveedor> proveedores) {
        try {
            getDatabase().beginTransaction();
            for (Proveedor bean : proveedores) {
                getDatabase().execSQL("INSERT INTO PROVEEDOR(codigo,descripcion,vRuc)VALUES(?,?,?)", new Object[]{bean.getCodigo(), bean.getDescripcion(), bean.getvRuc()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-Proveedor", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveProducto(List<Producto> productos) {
        try {
            getDatabase().beginTransaction();
            for (Producto bean : productos) {
                getDatabase().execSQL("INSERT INTO PRODUCTO(codproducto,descripcion,stockproducto,preciodol,preciosol,igvdol,igvsol,unidadventa,preciorealdol,preciorealsol,tipo,vCodBodega)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{bean.getCodproducto(), bean.getDescripcion(), bean.getStockproducto(), bean.getPreciodol(), bean.getPreciosol(), bean.getIgvdol(), bean.getIgvsol(), bean.getUnidadventa(), bean.getPreciorealdol(), bean.getPreciorealsol(), bean.getTipo(), bean.getvCodBodega()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-Producto", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveZonaVendedor(List<ZonaVendedor> lstZonaVendedor) {
        try {
            getDatabase().beginTransaction();
            for (ZonaVendedor bean : lstZonaVendedor) {
                getDatabase().execSQL("INSERT INTO ZONAVENDEDOR(iCodUsu,vNombre,vCodZona,vDescripcion)VALUES(?,?,?,?)", new Object[]{bean.getiCodUsu(), bean.getvNombre(), bean.getvCodZona(), bean.getvDescripcion()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-ZonaVendedor", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveCliente(List<Cliente> clientes) {
        try {
            getDatabase().beginTransaction();
            for (Cliente bean : clientes) {
                getDatabase().execSQL("INSERT INTO CLIENTE(codigo,nombre,direccion,dirembarque,saldo,saldoDolar,limiteCredido,condicionPago,nivelPrecio,docGenerar,monedaNivel,diasLibre,rubro8,rubro9,rubro10,requiereOC,vNomZona,vEstado,vCobJudicial,dLimCreDolar,dLimCreLocal,dLimCreInsDolar,dSalVenDol,iCantLetraPendiente)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{bean.getCodigo(), bean.getNombre(), bean.getDireccion(), bean.getDirembarque(), bean.getSaldo(), bean.getSaldoDolar(), bean.getLimiteCredido(), bean.getCondicionPago(), bean.getNivelPrecio(), bean.getDocGenerar(), bean.getMonedaNivel(), bean.getDiasLibre(), bean.getRubro8(), bean.getRubro9(), bean.getRubro10(), bean.getRequiereOC(),bean.getvNomZona(),bean.getvEstado(),bean.getvCobJudicial(),bean.getdLimCreDolar(),bean.getdLimCreLocal(),bean.getdLimCreInsDolar(),bean.getdSalVenDol(),bean.getiCantLetraPendiente()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-Cliente", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveZona(List<Zona> zonas) {
        try {
            getDatabase().beginTransaction();
            for (Zona bean : zonas) {
                getDatabase().execSQL("INSERT INTO ZONA(codigo,descripcion)VALUES(?,?)", new Object[]{bean.getCodigo(),bean.getDescripcion()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-ZONA", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveVendedorBodega(List<VendedorBodega> lstVendedorBodega) {
        try {
            getDatabase().beginTransaction();
            for (VendedorBodega bean : lstVendedorBodega) {
                getDatabase().execSQL("INSERT INTO VendedorBodega(iCodUsu,vCodBodega,vDescripcion)VALUES(?,?,?)", new Object[]{bean.getiCodUsu(), bean.getvCodBodega(), bean.getvDescripcion()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-VendedorBodega", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveVendedorCorrelativo(List<VendedorCorrelativo> lstVendedorCorrelativo) {
        try {
            getDatabase().beginTransaction();
            for (VendedorCorrelativo bean : lstVendedorCorrelativo) {
                getDatabase().execSQL("INSERT INTO VendedorCorrelativo(iCodUsu,vSerie,iCorrelativo)VALUES(?,?,?)", new Object[]{bean.getiCodUsu(), bean.getvSerie(), bean.getiCorrelativo()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-VendedorCorrelativo", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveVendedorMail(List<VendedorMail> lstVendedorMail) {
        try {
            getDatabase().beginTransaction();
            for (VendedorMail bean : lstVendedorMail) {
                getDatabase().execSQL("INSERT INTO VendedorMail(iCodUsu,vMail)VALUES(?,?)", new Object[]{bean.getiCodUsu(), bean.getvMail()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-VendedorMail", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }

    public long saveDescuentoCliente(List<DescuentoCliente> lstDescCliente) {
        try {
            getDatabase().beginTransaction();
            for (DescuentoCliente bean : lstDescCliente) {
                getDatabase().execSQL("INSERT INTO DescuentoCliente(iCodDescuento,vCodCliente,vDescripcion,deDescuento)VALUES(?,?,?,?)", new Object[]{bean.getiCodDescuento(), bean.getvCodCliente(), bean.getvDescripcion(), bean.getDeDescuento()});
            }
            getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("FARMAGRO ERROR SAVE-DescuentoCliente", e.toString());
        }
        getDatabase().endTransaction();
        return 0;
    }


    //endregion

    //region Eliminacion de registros

    public boolean deleteDetalleById(long idPedido) {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM DETPEDIDO WHERE idPedido = " + idPedido);
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteTmpDetalle() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM TMPDETALLE ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='TMPDETALLE' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteTmpDetalleById(String codigo) {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM TMPDETALLE WHERE codproducto = '" + codigo + "'");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteProveedor() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM PROVEEDOR ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='PROVEEDOR' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteContado() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM CONTADO ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='CONTADO' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteCredito() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM CREDITO ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='CREDITO' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteLetra() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM LETRA ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='LETRA' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteCliente() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM CLIENTE ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='BODEGA' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteBodega() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM BODEGA ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='BODEGA' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deletePedidos() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM DETPEDIDO ");
        getDatabase().execSQL("DELETE FROM CABPEDIDO ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='DETPEDIDO' ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='CABPEDIDO' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deletePedidoById(long id) {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM DETPEDIDO WHERE idPedido = " + id);
        getDatabase().execSQL("DELETE FROM CABPEDIDO WHERE id = " + id);
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteExistencia() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM EXISTENCIA ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='EXISTENCIA' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteProducto() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM PRODUCTO ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='PRODUCTO' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteUsuario() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM USUARIO ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteZonaVendedor() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM ZonaVendedor ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='ZonaVendedor' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteZona() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM Zona ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='Zona' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteVendedorBodega() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM VendedorBodega ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='VendedorBodega' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteVendedorCorrelativo() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM VendedorCorrelativo ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='VendedorCorrelativo' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteVendedorMail() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM VendedorMail ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='VendedorMail' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }

    public boolean deleteDescuentoCliente() {
        getDatabase().beginTransaction();
        getDatabase().execSQL("DELETE FROM DescuentoCliente ");
        getDatabase().execSQL("DELETE FROM sqlite_sequence where name='DescuentoCliente' ");
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
        return false;
    }
    //endregion

    //region Gestion de Conexiones

    public DataManager(Context context2) {
        setContext(context2);
        setDatabase(new OpenHelper(context2, "FARMAGRODATABASE.db", (SQLiteDatabase.CursorFactory) null, 2).getWritableDatabase());
        usuarioDao = new UsuarioDao(new UsuarioTableDefinition(), this.database);
        bodegaDao = new BodegaDao(new BodegaTableDefinition(), this.database);
        cabpedidoDao = new CabPedidoDao(new CabPedidoTableDefinition(), this.database);
        clienteDao = new ClienteDao(new ClienteTableDefinition(), this.database);
        contadoDao = new ContadoDao(new ContadoTableDefinition(), this.database);
        creditoDao = new CreditoDao(new CreditoTableDefinition(), this.database);
        detpedidoDao = new DetPedidoDao(new DetPedidoTableDefinition(), this.database);
        existenciaDao = new ExistenciaDao(new ExistenciaTableDefinition(), this.database);
        letraDao = new LetraDao(new LetraTableDefinition(), this.database);
        productoDao = new ProductoDao(new ProductoTableDefinition(), this.database);
        proveedorDao = new ProveedorDao(new ProveedorTableDefinition(), this.database);
        tmpdetalleDao = new TmpDetalleDao(new TmpDetalleTableDefinition(), this.database);
        zonaVendedorDao= new ZonaVendedorDao(new ZonaVendedorTableDefinition(), this.database);
        zonaDao= new ZonaDao(new ZonaTableDefinition(), this.database);
        vendedorBodegaDao= new VendedorBodegaDao(new VendedorBodegaTableDefinition(), this.database);
        vendedorCorrelativoDao= new VendedorCorrelativoDao(new VendedorCorrelativoTableDefinition(), this.database);
        vendedorMailDao= new VendedorMailDao(new VendedorMailTableDefinition(), this.database);
        descuentoClienteDAO = new DescuentoClienteDAO(new DescuentoClienteTableDefinition(), this.database);
    }

    private void openDb() {
        if (!getDatabase().isOpen()) {
            setDatabase(SQLiteDatabase.openDatabase(Environment.getDataDirectory() + "/data/user/0/com.farmagro.tomapedido/databases/FARMAGRODATABASE.db", (SQLiteDatabase.CursorFactory) null, 0));
        }
    }

    private void closeDb() {
        if (getDatabase().isOpen()) {
            getDatabase().close();
        }
    }

    private void resetDb() {
        closeDb();
        SystemClock.sleep(500);
        openDb();
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void setDatabase(SQLiteDatabase database2) {
        this.database = database2;
    }

    //endregion
}
