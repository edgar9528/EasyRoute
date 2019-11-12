package com.tdt.easyroute.Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL(Querys.Estatus.CreaEstatus);
        db.execSQL(Querys.Roles.CreaRoles);
        db.execSQL(Querys.Modulos.CreaModulos);
        db.execSQL(Querys.RolesModulos.CreaRolesModulos);
        db.execSQL(Querys.Usuarios.CreaUsuarios);
        db.execSQL(Querys.TipoRutas.CreaTipoRutas);
        db.execSQL(Querys.Rutas.CreaRutas);
        db.execSQL(Querys.Marcas.CreaMarcas);
        db.execSQL(Querys.Unidades.CreaUnidades);
        db.execSQL(Querys.CondicionesVenta.CreaCondicionesVenta);
        db.execSQL(Querys.Productos.CreaProductos);
        db.execSQL(Querys.ListaPrecio.CreaListaPrecios);
        db.execSQL(Querys.PrecioProductos.CreaPrecioProductos);
        db.execSQL(Querys.NivelCliente.CreaNivelCliente);
        db.execSQL(Querys.FormasPago.CreaFormasPago);
        db.execSQL(Querys.FrecuenciasVisita.CreaFrecuenciasVisita);
        db.execSQL(Querys.Empresas.CreaEmpresas);
        db.execSQL(Querys.Clientes.CreaClientes);
        db.execSQL(Querys.ClientesVentaMes.CreaClientesVentaMes);
        db.execSQL(Querys.MotivosNoVenta.CreaMotivosNoVenta);
        db.execSQL(Querys.MotivosNoLectura.CreaMotivosNoLectura);
        db.execSQL(Querys.Categorias.CreaCategorias);
        db.execSQL(Querys.Inventario.CreaInventario);
        db.execSQL(Querys.MovimientosInventario.CreaMovimientosInv);
        db.execSQL(Querys.Familias.CreaFamilias);
        db.execSQL(Querys.Presentaciones.CreaPresentaciones);
        db.execSQL(Querys.Direcciones.CreaDirecciones);
        db.execSQL(Querys.Ventas.VentaEnv);
        db.execSQL(Querys.FrecuenciaPunteo.FrecPunteo);
        db.execSQL(Querys.Consignas.CreaConsignas);
        db.execSQL(Querys.Consignas.CreaConsignasEntregaDet);


        //-- trabajo
        db.execSQL(Querys.Ventas.CreaVentas);
        db.execSQL(Querys.Ventas.VentasDet);
        db.execSQL(Querys.ConfiguracionHH.CreaConfiguracionHH);
        db.execSQL(Querys.BitacoraHH.CreaBitacoraHH);
        db.execSQL(Querys.Visitas.CreaVisitas);
        db.execSQL(Querys.Creditos.CreaCreditos);
        db.execSQL(Querys.Pagos.CreaPagos);
        db.execSQL(Querys.CargaInicialHH.CreaCargaInicialHH);
        db.execSQL(Querys.Sugerido.CreaSugerido);

        //SELECT date('now');


        String InsertBitacoraHHSesion =
        "Insert into bitacoraHH(usu_cve_str,bit_fecha_dt,bit_operacion_str,bit_comentario_str,rut_cve_n,bit_coordenada_str) values('SYSTEM',datetime('now','localtime'),'CREACION BASE DE DATOS','CREACION BASE DE DATOS TRABAJO','0','')";


        String s = "Insert into cargainicial(cini_cargado_n,cini_fecha_dt,usu_cve_str,est_cve_str,cini_comentario_str) values (0,datetime('now','localtime'),'SYSTEM','A','REGISTRO INICIAL')";

        db.execSQL(InsertBitacoraHHSesion);
        db.execSQL(s);

        //-- preventa

        db.execSQL(Querys.Preventa.CreaPreventa);
        db.execSQL(Querys.Preventa.PreventaDet);
        db.execSQL(Querys.Preventa.PreventaEnv);
        db.execSQL(Querys.Preventa.PreventaPagos);
        db.execSQL(Querys.Preventa.VisitaPreventa);

        //-- promociones
        db.execSQL(Querys.Promociones.CreaPromociones);
        db.execSQL(Querys.Promociones.CreatePromocionesKit);
        //----
        

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
