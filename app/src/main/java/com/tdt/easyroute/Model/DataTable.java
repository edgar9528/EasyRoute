package com.tdt.easyroute.Model;

public class DataTable {

    public static class Estatus{

        private String est_cve_str;
        private String est_desc_str;
        private String est_usu_n;
        private String est_ped_n;
        private String est_prod_n;
        private String est_prov_n;

        public Estatus() {
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getEst_desc_str() {
            return est_desc_str;
        }

        public void setEst_desc_str(String est_desc_str) {
            this.est_desc_str = est_desc_str;
        }

        public String getEst_usu_n() {
            return est_usu_n;
        }

        public void setEst_usu_n(String est_usu_n) {
            this.est_usu_n = est_usu_n;
        }

        public String getEst_ped_n() {
            return est_ped_n;
        }

        public void setEst_ped_n(String est_ped_n) {
            this.est_ped_n = est_ped_n;
        }

        public String getEst_prod_n() {
            return est_prod_n;
        }

        public void setEst_prod_n(String est_prod_n) {
            this.est_prod_n = est_prod_n;
        }

        public String getEst_prov_n() {
            return est_prov_n;
        }

        public void setEst_prov_n(String est_prov_n) {
            this.est_prov_n = est_prov_n;
        }
    }

    public static class Empresa{
        private String emp_cve_n;
        private String emp_nom_str;
        private String emp_contacto_str;
        private String emp_rfc_str;
        private String emp_colonia_str;
        private String emp_ciudad_str;
        private String emp_telefono1_str;
        private String est_cve_str;

        public Empresa() {
        }

        public String getEmp_cve_n() {
            return emp_cve_n;
        }

        public void setEmp_cve_n(String emp_cve_n) {
            this.emp_cve_n = emp_cve_n;
        }

        public String getEmp_nom_str() {
            return emp_nom_str;
        }

        public void setEmp_nom_str(String emp_nom_str) {
            this.emp_nom_str = emp_nom_str;
        }

        public String getEmp_contacto_str() {
            return emp_contacto_str;
        }

        public void setEmp_contacto_str(String emp_contacto_str) {
            this.emp_contacto_str = emp_contacto_str;
        }

        public String getEmp_rfc_str() {
            return emp_rfc_str;
        }

        public void setEmp_rfc_str(String emp_rfc_str) {
            this.emp_rfc_str = emp_rfc_str;
        }

        public String getEmp_colonia_str() {
            return emp_colonia_str;
        }

        public void setEmp_colonia_str(String emp_colonia_str) {
            this.emp_colonia_str = emp_colonia_str;
        }

        public String getEmp_ciudad_str() {
            return emp_ciudad_str;
        }

        public void setEmp_ciudad_str(String emp_ciudad_str) {
            this.emp_ciudad_str = emp_ciudad_str;
        }

        public String getEmp_telefono1_str() {
            return emp_telefono1_str;
        }

        public void setEmp_telefono1_str(String emp_telefono1_str) {
            this.emp_telefono1_str = emp_telefono1_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }

    public static class Ruta {

        private String rut_cve_n;
        private String rut_desc_str;
        private String rut_orden_n;
        private String trut_cve_n;
        private String asesor_cve_str;
        private String gerente_cve_str;
        private String supervisor_cve_str;
        private String est_cve_str;
        private String tco_cve_n;
        private String rut_prev_n;
        private String rut_capcamion_n;
        private String rut_idcteesp_n;
        private String rut_tel_str;
        private String rut_invalidafrecuencia_n;
        private String rut_productividad_n;
        private String rut_efectividad_n;
        private String rut_mayorista_n;
        private String rut_fiestasyeventos_n;
        private String rut_auditoria_n;
        private String rut_promoce_n;

        public Ruta() {
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getRut_desc_str() {
            return rut_desc_str;
        }

        public void setRut_desc_str(String rut_desc_str) {
            this.rut_desc_str = rut_desc_str;
        }

        public String getRut_orden_n() {
            return rut_orden_n;
        }

        public void setRut_orden_n(String rut_orden_n) {
            this.rut_orden_n = rut_orden_n;
        }

        public String getTrut_cve_n() {
            return trut_cve_n;
        }

        public void setTrut_cve_n(String trut_cve_n) {
            this.trut_cve_n = trut_cve_n;
        }

        public String getAsesor_cve_str() {
            return asesor_cve_str;
        }

        public void setAsesor_cve_str(String asesor_cve_str) {
            this.asesor_cve_str = asesor_cve_str;
        }

        public String getGerente_cve_str() {
            return gerente_cve_str;
        }

        public void setGerente_cve_str(String gerente_cve_str) {
            this.gerente_cve_str = gerente_cve_str;
        }

        public String getSupervisor_cve_str() {
            return supervisor_cve_str;
        }

        public void setSupervisor_cve_str(String supervisor_cve_str) {
            this.supervisor_cve_str = supervisor_cve_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getTco_cve_n() {
            return tco_cve_n;
        }

        public void setTco_cve_n(String tco_cve_n) {
            this.tco_cve_n = tco_cve_n;
        }

        public String getRut_prev_n() {
            return rut_prev_n;
        }

        public void setRut_prev_n(String rut_prev_n) {
            this.rut_prev_n = rut_prev_n;
        }

        public String getRut_capcamion_n() {
            return rut_capcamion_n;
        }

        public void setRut_capcamion_n(String rut_capcamion_n) {
            this.rut_capcamion_n = rut_capcamion_n;
        }

        public String getRut_idcteesp_n() {
            return rut_idcteesp_n;
        }

        public void setRut_idcteesp_n(String rut_idcteesp_n) {
            this.rut_idcteesp_n = rut_idcteesp_n;
        }

        public String getRut_tel_str() {
            return rut_tel_str;
        }

        public void setRut_tel_str(String rut_tel_str) {
            this.rut_tel_str = rut_tel_str;
        }

        public String getRut_invalidafrecuencia_n() {
            return rut_invalidafrecuencia_n;
        }

        public void setRut_invalidafrecuencia_n(String rut_invalidafrecuencia_n) {
            this.rut_invalidafrecuencia_n = rut_invalidafrecuencia_n;
        }

        public String getRut_productividad_n() {
            return rut_productividad_n;
        }

        public void setRut_productividad_n(String rut_productividad_n) {
            this.rut_productividad_n = rut_productividad_n;
        }

        public String getRut_efectividad_n() {
            return rut_efectividad_n;
        }

        public void setRut_efectividad_n(String rut_efectividad_n) {
            this.rut_efectividad_n = rut_efectividad_n;
        }

        public String getRut_mayorista_n() {
            return rut_mayorista_n;
        }

        public void setRut_mayorista_n(String rut_mayorista_n) {
            this.rut_mayorista_n = rut_mayorista_n;
        }

        public String getRut_fiestasyeventos_n() {
            return rut_fiestasyeventos_n;
        }

        public void setRut_fiestasyeventos_n(String rut_fiestasyeventos_n) {
            this.rut_fiestasyeventos_n = rut_fiestasyeventos_n;
        }

        public String getRut_auditoria_n() {
            return rut_auditoria_n;
        }

        public void setRut_auditoria_n(String rut_auditoria_n) {
            this.rut_auditoria_n = rut_auditoria_n;
        }

        public String getRut_promoce_n() {
            return rut_promoce_n;
        }

        public void setRut_promoce_n(String rut_promoce_n) {
            this.rut_promoce_n = rut_promoce_n;
        }
    }

    public static class Roles
    {
        private String rol_cve_n;
        private String rol_desc_str;
        private String rol_esadmin_n;
        private String est_cve_str;

        public Roles() {
        }

        public String getRol_cve_n() {
            return rol_cve_n;
        }

        public void setRol_cve_n(String rol_cve_n) {
            this.rol_cve_n = rol_cve_n;
        }

        public String getRol_desc_str() {
            return rol_desc_str;
        }

        public void setRol_desc_str(String rol_desc_str) {
            this.rol_desc_str = rol_desc_str;
        }

        public String getRol_esadmin_n() {
            return rol_esadmin_n;
        }

        public void setRol_esadmin_n(String rol_esadmin_n) {
            this.rol_esadmin_n = rol_esadmin_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }

    public static class RolesModulos
    {
        private String rol_cve_n;
        private String mod_cve_n;
        private String rm_lectura_n;
        private String rm_escritura_n;
        private String rm_modificacion_n;
        private String rm_eliminacion_n;

        public RolesModulos() {
        }

        public String getRol_cve_n() {
            return rol_cve_n;
        }

        public void setRol_cve_n(String rol_cve_n) {
            this.rol_cve_n = rol_cve_n;
        }

        public String getMod_cve_n() {
            return mod_cve_n;
        }

        public void setMod_cve_n(String mod_cve_n) {
            this.mod_cve_n = mod_cve_n;
        }

        public String getRm_lectura_n() {
            return rm_lectura_n;
        }

        public void setRm_lectura_n(String rm_lectura_n) {
            this.rm_lectura_n = rm_lectura_n;
        }

        public String getRm_escritura_n() {
            return rm_escritura_n;
        }

        public void setRm_escritura_n(String rm_escritura_n) {
            this.rm_escritura_n = rm_escritura_n;
        }

        public String getRm_modificacion_n() {
            return rm_modificacion_n;
        }

        public void setRm_modificacion_n(String rm_modificacion_n) {
            this.rm_modificacion_n = rm_modificacion_n;
        }

        public String getRm_eliminacion_n() {
            return rm_eliminacion_n;
        }

        public void setRm_eliminacion_n(String rm_eliminacion_n) {
            this.rm_eliminacion_n = rm_eliminacion_n;
        }
    }

    public static class Modulos
    {
        private String mod_cve_n;
        private String mod_desc_str;

        public Modulos() {
        }

        public String getMod_cve_n() {
            return mod_cve_n;
        }

        public void setMod_cve_n(String mod_cve_n) {
            this.mod_cve_n = mod_cve_n;
        }

        public String getMod_desc_str() {
            return mod_desc_str;
        }

        public void setMod_desc_str(String mod_desc_str) {
            this.mod_desc_str = mod_desc_str;
        }
    }
    
    public static class Usuarios
    {
        private String usu_cve_str;
        private String usu_pwd_str;
        private String usu_nom_str;
        private String usu_app_str;
        private String usu_apm_str;
        private String rol_cve_n;
        private String est_cve_str;
        private String usu_factivo_dt;
        private String usu_bloqueado_n;
        private String usu_fbloqueo_dt;

        public Usuarios() {
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getUsu_pwd_str() {
            return usu_pwd_str;
        }

        public void setUsu_pwd_str(String usu_pwd_str) {
            this.usu_pwd_str = usu_pwd_str;
        }

        public String getUsu_nom_str() {
            return usu_nom_str;
        }

        public void setUsu_nom_str(String usu_nom_str) {
            this.usu_nom_str = usu_nom_str;
        }

        public String getUsu_app_str() {
            return usu_app_str;
        }

        public void setUsu_app_str(String usu_app_str) {
            this.usu_app_str = usu_app_str;
        }

        public String getUsu_apm_str() {
            return usu_apm_str;
        }

        public void setUsu_apm_str(String usu_apm_str) {
            this.usu_apm_str = usu_apm_str;
        }

        public String getRol_cve_n() {
            return rol_cve_n;
        }

        public void setRol_cve_n(String rol_cve_n) {
            this.rol_cve_n = rol_cve_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getUsu_factivo_dt() {
            return usu_factivo_dt;
        }

        public void setUsu_factivo_dt(String usu_factivo_dt) {
            this.usu_factivo_dt = usu_factivo_dt;
        }

        public String getUsu_bloqueado_n() {
            return usu_bloqueado_n;
        }

        public void setUsu_bloqueado_n(String usu_bloqueado_n) {
            this.usu_bloqueado_n = usu_bloqueado_n;
        }

        public String getUsu_fbloqueo_dt() {
            return usu_fbloqueo_dt;
        }

        public void setUsu_fbloqueo_dt(String usu_fbloqueo_dt) {
            this.usu_fbloqueo_dt = usu_fbloqueo_dt;
        }
    }
    
    public static class TipoRuta
    {
        private String trut_cve_n;
        private String trut_desc_str;
        private String est_cve_str;

        public TipoRuta() {
        }

        public String getTrut_cve_n() {
            return trut_cve_n;
        }

        public void setTrut_cve_n(String trut_cve_n) {
            this.trut_cve_n = trut_cve_n;
        }

        public String getTrut_desc_str() {
            return trut_desc_str;
        }

        public void setTrut_desc_str(String trut_desc_str) {
            this.trut_desc_str = trut_desc_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }

    public static class CondicionesVenta
    {
        private String cov_cve_n;
        private String cov_desc_str;
        private String cov_restringido_n;
        private String cov_familias_str;
        private String est_cve_str;

        public CondicionesVenta() {
        }

        public String getCov_cve_n() {
            return cov_cve_n;
        }

        public void setCov_cve_n(String cov_cve_n) {
            this.cov_cve_n = cov_cve_n;
        }

        public String getCov_desc_str() {
            return cov_desc_str;
        }

        public void setCov_desc_str(String cov_desc_str) {
            this.cov_desc_str = cov_desc_str;
        }

        public String getCov_restringido_n() {
            return cov_restringido_n;
        }

        public void setCov_restringido_n(String cov_restringido_n) {
            this.cov_restringido_n = cov_restringido_n;
        }

        public String getCov_familias_str() {
            return cov_familias_str;
        }

        public void setCov_familias_str(String cov_familias_str) {
            this.cov_familias_str = cov_familias_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }

    public static class Productos
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String prod_dscc_str;
        private String cat_cve_n;
        private String fam_cve_n;
        private String retor_cve_str;
        private String prod_reqenv_n;
        private String id_envase_n;
        private String prod_unicompra_n;
        private String prod_factcompra_n;
        private String prod_univenta_n;
        private String prod_factventa_n;
        private String prod_escompuesto_n;
        private String prod_manejainv_n;
        private String prod_est_str;
        private String mar_cve_n;
        private String pres_cve_n;
        private String prod_ean_str;
        private String prod_orden_n;
        private String prod_vtamismodia_n;
        private String prod_vtaefectivo_n;
        private String prod_vtavolumen_n;
        private String cov_cve_n;

        public Productos() {
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getProd_sku_str() {
            return prod_sku_str;
        }

        public void setProd_sku_str(String prod_sku_str) {
            this.prod_sku_str = prod_sku_str;
        }

        public String getProd_desc_str() {
            return prod_desc_str;
        }

        public void setProd_desc_str(String prod_desc_str) {
            this.prod_desc_str = prod_desc_str;
        }

        public String getProd_dscc_str() {
            return prod_dscc_str;
        }

        public void setProd_dscc_str(String prod_dscc_str) {
            this.prod_dscc_str = prod_dscc_str;
        }

        public String getCat_cve_n() {
            return cat_cve_n;
        }

        public void setCat_cve_n(String cat_cve_n) {
            this.cat_cve_n = cat_cve_n;
        }

        public String getFam_cve_n() {
            return fam_cve_n;
        }

        public void setFam_cve_n(String fam_cve_n) {
            this.fam_cve_n = fam_cve_n;
        }

        public String getRetor_cve_str() {
            return retor_cve_str;
        }

        public void setRetor_cve_str(String retor_cve_str) {
            this.retor_cve_str = retor_cve_str;
        }

        public String getProd_reqenv_n() {
            return prod_reqenv_n;
        }

        public void setProd_reqenv_n(String prod_reqenv_n) {
            this.prod_reqenv_n = prod_reqenv_n;
        }

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_unicompra_n() {
            return prod_unicompra_n;
        }

        public void setProd_unicompra_n(String prod_unicompra_n) {
            this.prod_unicompra_n = prod_unicompra_n;
        }

        public String getProd_factcompra_n() {
            return prod_factcompra_n;
        }

        public void setProd_factcompra_n(String prod_factcompra_n) {
            this.prod_factcompra_n = prod_factcompra_n;
        }

        public String getProd_univenta_n() {
            return prod_univenta_n;
        }

        public void setProd_univenta_n(String prod_univenta_n) {
            this.prod_univenta_n = prod_univenta_n;
        }

        public String getProd_factventa_n() {
            return prod_factventa_n;
        }

        public void setProd_factventa_n(String prod_factventa_n) {
            this.prod_factventa_n = prod_factventa_n;
        }

        public String getProd_escompuesto_n() {
            return prod_escompuesto_n;
        }

        public void setProd_escompuesto_n(String prod_escompuesto_n) {
            this.prod_escompuesto_n = prod_escompuesto_n;
        }

        public String getProd_manejainv_n() {
            return prod_manejainv_n;
        }

        public void setProd_manejainv_n(String prod_manejainv_n) {
            this.prod_manejainv_n = prod_manejainv_n;
        }

        public String getProd_est_str() {
            return prod_est_str;
        }

        public void setProd_est_str(String prod_est_str) {
            this.prod_est_str = prod_est_str;
        }

        public String getMar_cve_n() {
            return mar_cve_n;
        }

        public void setMar_cve_n(String mar_cve_n) {
            this.mar_cve_n = mar_cve_n;
        }

        public String getPres_cve_n() {
            return pres_cve_n;
        }

        public void setPres_cve_n(String pres_cve_n) {
            this.pres_cve_n = pres_cve_n;
        }

        public String getProd_ean_str() {
            return prod_ean_str;
        }

        public void setProd_ean_str(String prod_ean_str) {
            this.prod_ean_str = prod_ean_str;
        }

        public String getProd_orden_n() {
            return prod_orden_n;
        }

        public void setProd_orden_n(String prod_orden_n) {
            this.prod_orden_n = prod_orden_n;
        }

        public String getProd_vtamismodia_n() {
            return prod_vtamismodia_n;
        }

        public void setProd_vtamismodia_n(String prod_vtamismodia_n) {
            this.prod_vtamismodia_n = prod_vtamismodia_n;
        }

        public String getProd_vtaefectivo_n() {
            return prod_vtaefectivo_n;
        }

        public void setProd_vtaefectivo_n(String prod_vtaefectivo_n) {
            this.prod_vtaefectivo_n = prod_vtaefectivo_n;
        }

        public String getProd_vtavolumen_n() {
            return prod_vtavolumen_n;
        }

        public void setProd_vtavolumen_n(String prod_vtavolumen_n) {
            this.prod_vtavolumen_n = prod_vtavolumen_n;
        }

        public String getCov_cve_n() {
            return cov_cve_n;
        }

        public void setCov_cve_n(String cov_cve_n) {
            this.cov_cve_n = cov_cve_n;
        }
    }

    public static class ListaPrecios
    {
        private String lpre_cve_n;
        private String lpre_desc_str;
        private String est_cve_str;
        private String lpre_esbase_n;
        private String lpre_base_n;
        private String lpre_nota_n;

        public ListaPrecios() {
        }

        public String getLpre_cve_n() {
            return lpre_cve_n;
        }

        public void setLpre_cve_n(String lpre_cve_n) {
            this.lpre_cve_n = lpre_cve_n;
        }

        public String getLpre_desc_str() {
            return lpre_desc_str;
        }

        public void setLpre_desc_str(String lpre_desc_str) {
            this.lpre_desc_str = lpre_desc_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getLpre_esbase_n() {
            return lpre_esbase_n;
        }

        public void setLpre_esbase_n(String lpre_esbase_n) {
            this.lpre_esbase_n = lpre_esbase_n;
        }

        public String getLpre_base_n() {
            return lpre_base_n;
        }

        public void setLpre_base_n(String lpre_base_n) {
            this.lpre_base_n = lpre_base_n;
        }

        public String getLpre_nota_n() {
            return lpre_nota_n;
        }

        public void setLpre_nota_n(String lpre_nota_n) {
            this.lpre_nota_n = lpre_nota_n;
        }
    }

    public static class PrecioProductos
    {
        private String lpre_cve_n;
        private String prod_cve_n;
        private String lpre_precio_n;
        private String lpre_volumen_n;

        public PrecioProductos() {
        }

        public String getLpre_cve_n() {
            return lpre_cve_n;
        }

        public void setLpre_cve_n(String lpre_cve_n) {
            this.lpre_cve_n = lpre_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getLpre_volumen_n() {
            return lpre_volumen_n;
        }

        public void setLpre_volumen_n(String lpre_volumen_n) {
            this.lpre_volumen_n = lpre_volumen_n;
        }
    }
    
    public static class FormasPago
    {
        private String fpag_cve_n;
        private String fpag_desc_str;
        private String fpag_reqbanco_n;
        private String fpag_reqreferencia_n;
        private String est_cve_str;

        public FormasPago() {
        }

        public String getFpag_cve_n() {
            return fpag_cve_n;
        }

        public void setFpag_cve_n(String fpag_cve_n) {
            this.fpag_cve_n = fpag_cve_n;
        }

        public String getFpag_desc_str() {
            return fpag_desc_str;
        }

        public void setFpag_desc_str(String fpag_desc_str) {
            this.fpag_desc_str = fpag_desc_str;
        }

        public String getFpag_reqbanco_n() {
            return fpag_reqbanco_n;
        }

        public void setFpag_reqbanco_n(String fpag_reqbanco_n) {
            this.fpag_reqbanco_n = fpag_reqbanco_n;
        }

        public String getFpag_reqreferencia_n() {
            return fpag_reqreferencia_n;
        }

        public void setFpag_reqreferencia_n(String fpag_reqreferencia_n) {
            this.fpag_reqreferencia_n = fpag_reqreferencia_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }
    
    public static class FrecuenciasVisita
    {
        private String frec_cve_n;
        private String frec_desc_str;
        private String frec_dias_n;
        private String est_cve_str;

        public FrecuenciasVisita() {
        }

        public String getFrec_cve_n() {
            return frec_cve_n;
        }

        public void setFrec_cve_n(String frec_cve_n) {
            this.frec_cve_n = frec_cve_n;
        }

        public String getFrec_desc_str() {
            return frec_desc_str;
        }

        public void setFrec_desc_str(String frec_desc_str) {
            this.frec_desc_str = frec_desc_str;
        }

        public String getFrec_dias_n() {
            return frec_dias_n;
        }

        public void setFrec_dias_n(String frec_dias_n) {
            this.frec_dias_n = frec_dias_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }
    
    public static class Categorias
    {
        private String cat_cve_n;
        private String cat_desc_str;

        public Categorias() {
        }

        public String getCat_cve_n() {
            return cat_cve_n;
        }

        public void setCat_cve_n(String cat_cve_n) {
            this.cat_cve_n = cat_cve_n;
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }
    }
    
    public static class Familias
    {
        private String fam_cve_n;
        private String fam_desc_str;
        private String fam_orden_n;

        public Familias() {
        }

        public String getFam_cve_n() {
            return fam_cve_n;
        }

        public void setFam_cve_n(String fam_cve_n) {
            this.fam_cve_n = fam_cve_n;
        }

        public String getFam_desc_str() {
            return fam_desc_str;
        }

        public void setFam_desc_str(String fam_desc_str) {
            this.fam_desc_str = fam_desc_str;
        }

        public String getFam_orden_n() {
            return fam_orden_n;
        }

        public void setFam_orden_n(String fam_orden_n) {
            this.fam_orden_n = fam_orden_n;
        }
    }
    
    public static class Presentaciones
    {
        private String pres_cve_n;
        private String pres_desc_str;
        private String pres_hectolitros_n;
        private String est_cve_str;

        public Presentaciones() {
        }

        public String getPres_cve_n() {
            return pres_cve_n;
        }

        public void setPres_cve_n(String pres_cve_n) {
            this.pres_cve_n = pres_cve_n;
        }

        public String getPres_desc_str() {
            return pres_desc_str;
        }

        public void setPres_desc_str(String pres_desc_str) {
            this.pres_desc_str = pres_desc_str;
        }

        public String getPres_hectolitros_n() {
            return pres_hectolitros_n;
        }

        public void setPres_hectolitros_n(String pres_hectolitros_n) {
            this.pres_hectolitros_n = pres_hectolitros_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }
    

    public static class Promociones
    {
        private String prom_cve_n;
        private String prom_folio_str;
        private String prom_desc_str;
        private String tprom_cve_n;
        private String prom_falta_dt;
        private String prom_fini_dt;
        private String prom_ffin_dt;
        private String est_cve_str;
        private String usu_cve_str;
        private String usu_modificacion_str;
        private String prom_fmodificacion_dt;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prom_nivel_n;
        private String lpre_cve_n;
        private String nvc_cve_n;
        private String nvc_cvehl_n;
        private String fam_cve_n;
        private String seg_cve_n;
        private String giro_cve_n;
        private String tcli_cve_n;
        private String lpre_precio_n;
        private String lpre_precio2_n;
        private String lpre_desc_n;
        private String prom_n_n;
        private String prom_m_n;
        private String prod_regalo_n;
        private String prod_skureg_str;
        private String prom_story_n;
        private String prom_proveedor_n;
        private String prom_envase_n;
        private String prom_kit_n;


        public Promociones() {
        }

        public String getProm_cve_n() {
            return prom_cve_n;
        }

        public void setProm_cve_n(String prom_cve_n) {
            this.prom_cve_n = prom_cve_n;
        }

        public String getProm_folio_str() {
            return prom_folio_str;
        }

        public void setProm_folio_str(String prom_folio_str) {
            this.prom_folio_str = prom_folio_str;
        }

        public String getProm_desc_str() {
            return prom_desc_str;
        }

        public void setProm_desc_str(String prom_desc_str) {
            this.prom_desc_str = prom_desc_str;
        }

        public String getTprom_cve_n() {
            return tprom_cve_n;
        }

        public void setTprom_cve_n(String tprom_cve_n) {
            this.tprom_cve_n = tprom_cve_n;
        }

        public String getProm_falta_dt() {
            return prom_falta_dt;
        }

        public void setProm_falta_dt(String prom_falta_dt) {
            this.prom_falta_dt = prom_falta_dt;
        }

        public String getProm_fini_dt() {
            return prom_fini_dt;
        }

        public void setProm_fini_dt(String prom_fini_dt) {
            this.prom_fini_dt = prom_fini_dt;
        }

        public String getProm_ffin_dt() {
            return prom_ffin_dt;
        }

        public void setProm_ffin_dt(String prom_ffin_dt) {
            this.prom_ffin_dt = prom_ffin_dt;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getUsu_modificacion_str() {
            return usu_modificacion_str;
        }

        public void setUsu_modificacion_str(String usu_modificacion_str) {
            this.usu_modificacion_str = usu_modificacion_str;
        }

        public String getProm_fmodificacion_dt() {
            return prom_fmodificacion_dt;
        }

        public void setProm_fmodificacion_dt(String prom_fmodificacion_dt) {
            this.prom_fmodificacion_dt = prom_fmodificacion_dt;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getProd_sku_str() {
            return prod_sku_str;
        }

        public void setProd_sku_str(String prod_sku_str) {
            this.prod_sku_str = prod_sku_str;
        }

        public String getProm_nivel_n() {
            return prom_nivel_n;
        }

        public void setProm_nivel_n(String prom_nivel_n) {
            this.prom_nivel_n = prom_nivel_n;
        }

        public String getLpre_cve_n() {
            return lpre_cve_n;
        }

        public void setLpre_cve_n(String lpre_cve_n) {
            this.lpre_cve_n = lpre_cve_n;
        }

        public String getNvc_cve_n() {
            return nvc_cve_n;
        }

        public void setNvc_cve_n(String nvc_cve_n) {
            this.nvc_cve_n = nvc_cve_n;
        }

        public String getNvc_cvehl_n() {
            return nvc_cvehl_n;
        }

        public void setNvc_cvehl_n(String nvc_cvehl_n) {
            this.nvc_cvehl_n = nvc_cvehl_n;
        }

        public String getFam_cve_n() {
            return fam_cve_n;
        }

        public void setFam_cve_n(String fam_cve_n) {
            this.fam_cve_n = fam_cve_n;
        }

        public String getSeg_cve_n() {
            return seg_cve_n;
        }

        public void setSeg_cve_n(String seg_cve_n) {
            this.seg_cve_n = seg_cve_n;
        }

        public String getGiro_cve_n() {
            return giro_cve_n;
        }

        public void setGiro_cve_n(String giro_cve_n) {
            this.giro_cve_n = giro_cve_n;
        }

        public String getTcli_cve_n() {
            return tcli_cve_n;
        }

        public void setTcli_cve_n(String tcli_cve_n) {
            this.tcli_cve_n = tcli_cve_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getLpre_precio2_n() {
            return lpre_precio2_n;
        }

        public void setLpre_precio2_n(String lpre_precio2_n) {
            this.lpre_precio2_n = lpre_precio2_n;
        }

        public String getLpre_desc_n() {
            return lpre_desc_n;
        }

        public void setLpre_desc_n(String lpre_desc_n) {
            this.lpre_desc_n = lpre_desc_n;
        }

        public String getProm_n_n() {
            return prom_n_n;
        }

        public void setProm_n_n(String prom_n_n) {
            this.prom_n_n = prom_n_n;
        }

        public String getProm_m_n() {
            return prom_m_n;
        }

        public void setProm_m_n(String prom_m_n) {
            this.prom_m_n = prom_m_n;
        }

        public String getProd_regalo_n() {
            return prod_regalo_n;
        }

        public void setProd_regalo_n(String prod_regalo_n) {
            this.prod_regalo_n = prod_regalo_n;
        }

        public String getProd_skureg_str() {
            return prod_skureg_str;
        }

        public void setProd_skureg_str(String prod_skureg_str) {
            this.prod_skureg_str = prod_skureg_str;
        }

        public String getProm_story_n() {
            return prom_story_n;
        }

        public void setProm_story_n(String prom_story_n) {
            this.prom_story_n = prom_story_n;
        }

        public String getProm_proveedor_n() {
            return prom_proveedor_n;
        }

        public void setProm_proveedor_n(String prom_proveedor_n) {
            this.prom_proveedor_n = prom_proveedor_n;
        }

        public String getProm_envase_n() {
            return prom_envase_n;
        }

        public void setProm_envase_n(String prom_envase_n) {
            this.prom_envase_n = prom_envase_n;
        }

        public String getProm_kit_n() {
            return prom_kit_n;
        }

        public void setProm_kit_n(String prom_kit_n) {
            this.prom_kit_n = prom_kit_n;
        }
    }

    public static class PromocionesKit
    {
        private String prom_cve_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_cant_n;
        private String lpre_precio_n;

        public PromocionesKit() {
        }

        public String getProm_cve_n() {
            return prom_cve_n;
        }

        public void setProm_cve_n(String prom_cve_n) {
            this.prom_cve_n = prom_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getProd_sku_str() {
            return prod_sku_str;
        }

        public void setProd_sku_str(String prod_sku_str) {
            this.prod_sku_str = prod_sku_str;
        }

        public String getProd_cant_n() {
            return prod_cant_n;
        }

        public void setProd_cant_n(String prod_cant_n) {
            this.prod_cant_n = prod_cant_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }
    }
    
}
