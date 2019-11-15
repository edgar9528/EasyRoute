package com.tdt.easyroute.Model;

public class DataTableWS {

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

    public static class Marcas
    {
        private String mar_cve_n;
        private String mar_desc_str;
        private String est_cve_str;

        public Marcas() {
        }

        public String getMar_cve_n() {
            return mar_cve_n;
        }

        public void setMar_cve_n(String mar_cve_n) {
            this.mar_cve_n = mar_cve_n;
        }

        public String getMar_desc_str() {
            return mar_desc_str;
        }

        public void setMar_desc_str(String mar_desc_str) {
            this.mar_desc_str = mar_desc_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }
    
    public static class Unidades
    {
        private String uni_cve_n;
        private String uni_simbolo_str;
        private String uni_desc_str;
        private String uni_divisible_n;
        private String uni_pesable_n;
        private String est_cve_str;

        public Unidades() {
        }

        public String getUni_cve_n() {
            return uni_cve_n;
        }

        public void setUni_cve_n(String uni_cve_n) {
            this.uni_cve_n = uni_cve_n;
        }

        public String getUni_simbolo_str() {
            return uni_simbolo_str;
        }

        public void setUni_simbolo_str(String uni_simbolo_str) {
            this.uni_simbolo_str = uni_simbolo_str;
        }

        public String getUni_desc_str() {
            return uni_desc_str;
        }

        public void setUni_desc_str(String uni_desc_str) {
            this.uni_desc_str = uni_desc_str;
        }

        public String getUni_divisible_n() {
            return uni_divisible_n;
        }

        public void setUni_divisible_n(String uni_divisible_n) {
            this.uni_divisible_n = uni_divisible_n;
        }

        public String getUni_pesable_n() {
            return uni_pesable_n;
        }

        public void setUni_pesable_n(String uni_pesable_n) {
            this.uni_pesable_n = uni_pesable_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }
    
    public static class NivelCliente
    {
        private String nvc_cve_n;
        private String nvc_desc_str;
        private String est_cve_str;
        private String nvc_nivel_n;

        public NivelCliente() {
        }

        public String getNvc_cve_n() {
            return nvc_cve_n;
        }

        public void setNvc_cve_n(String nvc_cve_n) {
            this.nvc_cve_n = nvc_cve_n;
        }

        public String getNvc_desc_str() {
            return nvc_desc_str;
        }

        public void setNvc_desc_str(String nvc_desc_str) {
            this.nvc_desc_str = nvc_desc_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getNvc_nivel_n() {
            return nvc_nivel_n;
        }

        public void setNvc_nivel_n(String nvc_nivel_n) {
            this.nvc_nivel_n = nvc_nivel_n;
        }
    }
    
    public static class MotivosNoVenta
    {
        private String mnv_cve_n;
        private String mnv_desc_str;

        public MotivosNoVenta() {
        }

        public String getMnv_cve_n() {
            return mnv_cve_n;
        }

        public void setMnv_cve_n(String mnv_cve_n) {
            this.mnv_cve_n = mnv_cve_n;
        }

        public String getMnv_desc_str() {
            return mnv_desc_str;
        }

        public void setMnv_desc_str(String mnv_desc_str) {
            this.mnv_desc_str = mnv_desc_str;
        }
    }
    
    public static class MotivosNoLectura
    {
        private String mnl_cve_n;
        private String mnl_desc_str;

        public MotivosNoLectura() {
        }

        public String getMnl_cve_n() {
            return mnl_cve_n;
        }

        public void setMnl_cve_n(String mnl_cve_n) {
            this.mnl_cve_n = mnl_cve_n;
        }

        public String getMnl_desc_str() {
            return mnl_desc_str;
        }

        public void setMnl_desc_str(String mnl_desc_str) {
            this.mnl_desc_str = mnl_desc_str;
        }
    }
    
    public static class Clientes
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_padre_n;
        private String cli_cvepadre_n;
        private String cli_razonsocial_str;
        private String cli_rfc_Str;
        private String cli_reqfact_n;
        private String cli_nombrenegocio_str;
        private String cli_nom_str;
        private String cli_app_str;
        private String cli_apm_str;
        private String cli_fnac_dt;
        private String cli_genero_str;
        private String lpre_cve_n;
        private String nota_cve_n;
        private String fpag_cve_n;
        private String cli_consigna_n;
        private String cli_especial_n;
        private String cli_credito_n;
        private String cli_montocredito_n;
        private String cli_plazocredito_n;
        private String cli_credenvases_n;
        private String cli_estcredito_str;
        private String cli_fba_n;
        private String cli_porcentajefba_n;
        private String rut_cve_n;
        private String nvc_cve_n;
        private String giro_cve_n;
        private String cli_email_str;
        private String cli_dirfact_n;
        private String cli_dirent_n;
        private String cli_Tel1_str;
        private String cli_tel2_str;
        private String emp_cve_n;
        private String cli_coordenadaini_str;
        private String est_cve_str;
        private String tcli_cve_n;
        private String cli_lun_n;
        private String cli_mar_n;
        private String cli_mie_n;
        private String cli_jue_n;
        private String cli_vie_n;
        private String cli_sab_n;
        private String cli_dom_n;
        private String frec_cve_n;
        private String cli_esvallejo_n;
        private String npro_cve_n;
        private String cli_huixdesc_n;
        private String cli_eshuix_n;
        private String cli_prospecto_n;
        private String cli_invalidafrecuencia_n;
        private String cli_invalidagps_n;
        private String cli_dobleventa_n;
        private String cli_comodato_n;
        private String seg_cve_n;
        private String cli_dispersion_n;
        private String cli_dispersioncant_n;
        private String cli_limitemes_n;

        public Clientes() {
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getCli_padre_n() {
            return cli_padre_n;
        }

        public void setCli_padre_n(String cli_padre_n) {
            this.cli_padre_n = cli_padre_n;
        }

        public String getCli_cvepadre_n() {
            return cli_cvepadre_n;
        }

        public void setCli_cvepadre_n(String cli_cvepadre_n) {
            this.cli_cvepadre_n = cli_cvepadre_n;
        }

        public String getCli_razonsocial_str() {
            return cli_razonsocial_str;
        }

        public void setCli_razonsocial_str(String cli_razonsocial_str) {
            this.cli_razonsocial_str = cli_razonsocial_str;
        }

        public String getCli_rfc_Str() {
            return cli_rfc_Str;
        }

        public void setCli_rfc_Str(String cli_rfc_Str) {
            this.cli_rfc_Str = cli_rfc_Str;
        }

        public String getCli_reqfact_n() {
            return cli_reqfact_n;
        }

        public void setCli_reqfact_n(String cli_reqfact_n) {
            this.cli_reqfact_n = cli_reqfact_n;
        }

        public String getCli_nombrenegocio_str() {
            return cli_nombrenegocio_str;
        }

        public void setCli_nombrenegocio_str(String cli_nombrenegocio_str) {
            this.cli_nombrenegocio_str = cli_nombrenegocio_str;
        }

        public String getCli_nom_str() {
            return cli_nom_str;
        }

        public void setCli_nom_str(String cli_nom_str) {
            this.cli_nom_str = cli_nom_str;
        }

        public String getCli_app_str() {
            return cli_app_str;
        }

        public void setCli_app_str(String cli_app_str) {
            this.cli_app_str = cli_app_str;
        }

        public String getCli_apm_str() {
            return cli_apm_str;
        }

        public void setCli_apm_str(String cli_apm_str) {
            this.cli_apm_str = cli_apm_str;
        }

        public String getCli_fnac_dt() {
            return cli_fnac_dt;
        }

        public void setCli_fnac_dt(String cli_fnac_dt) {
            this.cli_fnac_dt = cli_fnac_dt;
        }

        public String getCli_genero_str() {
            return cli_genero_str;
        }

        public void setCli_genero_str(String cli_genero_str) {
            this.cli_genero_str = cli_genero_str;
        }

        public String getLpre_cve_n() {
            return lpre_cve_n;
        }

        public void setLpre_cve_n(String lpre_cve_n) {
            this.lpre_cve_n = lpre_cve_n;
        }

        public String getNota_cve_n() {
            return nota_cve_n;
        }

        public void setNota_cve_n(String nota_cve_n) {
            this.nota_cve_n = nota_cve_n;
        }

        public String getFpag_cve_n() {
            return fpag_cve_n;
        }

        public void setFpag_cve_n(String fpag_cve_n) {
            this.fpag_cve_n = fpag_cve_n;
        }

        public String getCli_consigna_n() {
            return cli_consigna_n;
        }

        public void setCli_consigna_n(String cli_consigna_n) {
            this.cli_consigna_n = cli_consigna_n;
        }

        public String getCli_especial_n() {
            return cli_especial_n;
        }

        public void setCli_especial_n(String cli_especial_n) {
            this.cli_especial_n = cli_especial_n;
        }

        public String getCli_credito_n() {
            return cli_credito_n;
        }

        public void setCli_credito_n(String cli_credito_n) {
            this.cli_credito_n = cli_credito_n;
        }

        public String getCli_montocredito_n() {
            return cli_montocredito_n;
        }

        public void setCli_montocredito_n(String cli_montocredito_n) {
            this.cli_montocredito_n = cli_montocredito_n;
        }

        public String getCli_plazocredito_n() {
            return cli_plazocredito_n;
        }

        public void setCli_plazocredito_n(String cli_plazocredito_n) {
            this.cli_plazocredito_n = cli_plazocredito_n;
        }

        public String getCli_credenvases_n() {
            return cli_credenvases_n;
        }

        public void setCli_credenvases_n(String cli_credenvases_n) {
            this.cli_credenvases_n = cli_credenvases_n;
        }

        public String getCli_estcredito_str() {
            return cli_estcredito_str;
        }

        public void setCli_estcredito_str(String cli_estcredito_str) {
            this.cli_estcredito_str = cli_estcredito_str;
        }

        public String getCli_fba_n() {
            return cli_fba_n;
        }

        public void setCli_fba_n(String cli_fba_n) {
            this.cli_fba_n = cli_fba_n;
        }

        public String getCli_porcentajefba_n() {
            return cli_porcentajefba_n;
        }

        public void setCli_porcentajefba_n(String cli_porcentajefba_n) {
            this.cli_porcentajefba_n = cli_porcentajefba_n;
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getNvc_cve_n() {
            return nvc_cve_n;
        }

        public void setNvc_cve_n(String nvc_cve_n) {
            this.nvc_cve_n = nvc_cve_n;
        }

        public String getGiro_cve_n() {
            return giro_cve_n;
        }

        public void setGiro_cve_n(String giro_cve_n) {
            this.giro_cve_n = giro_cve_n;
        }

        public String getCli_email_str() {
            return cli_email_str;
        }

        public void setCli_email_str(String cli_email_str) {
            this.cli_email_str = cli_email_str;
        }

        public String getCli_dirfact_n() {
            return cli_dirfact_n;
        }

        public void setCli_dirfact_n(String cli_dirfact_n) {
            this.cli_dirfact_n = cli_dirfact_n;
        }

        public String getCli_dirent_n() {
            return cli_dirent_n;
        }

        public void setCli_dirent_n(String cli_dirent_n) {
            this.cli_dirent_n = cli_dirent_n;
        }

        public String getCli_Tel1_str() {
            return cli_Tel1_str;
        }

        public void setCli_Tel1_str(String cli_Tel1_str) {
            this.cli_Tel1_str = cli_Tel1_str;
        }

        public String getCli_tel2_str() {
            return cli_tel2_str;
        }

        public void setCli_tel2_str(String cli_tel2_str) {
            this.cli_tel2_str = cli_tel2_str;
        }

        public String getEmp_cve_n() {
            return emp_cve_n;
        }

        public void setEmp_cve_n(String emp_cve_n) {
            this.emp_cve_n = emp_cve_n;
        }

        public String getCli_coordenadaini_str() {
            return cli_coordenadaini_str;
        }

        public void setCli_coordenadaini_str(String cli_coordenadaini_str) {
            this.cli_coordenadaini_str = cli_coordenadaini_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getTcli_cve_n() {
            return tcli_cve_n;
        }

        public void setTcli_cve_n(String tcli_cve_n) {
            this.tcli_cve_n = tcli_cve_n;
        }

        public String getCli_lun_n() {
            return cli_lun_n;
        }

        public void setCli_lun_n(String cli_lun_n) {
            this.cli_lun_n = cli_lun_n;
        }

        public String getCli_mar_n() {
            return cli_mar_n;
        }

        public void setCli_mar_n(String cli_mar_n) {
            this.cli_mar_n = cli_mar_n;
        }

        public String getCli_mie_n() {
            return cli_mie_n;
        }

        public void setCli_mie_n(String cli_mie_n) {
            this.cli_mie_n = cli_mie_n;
        }

        public String getCli_jue_n() {
            return cli_jue_n;
        }

        public void setCli_jue_n(String cli_jue_n) {
            this.cli_jue_n = cli_jue_n;
        }

        public String getCli_vie_n() {
            return cli_vie_n;
        }

        public void setCli_vie_n(String cli_vie_n) {
            this.cli_vie_n = cli_vie_n;
        }

        public String getCli_sab_n() {
            return cli_sab_n;
        }

        public void setCli_sab_n(String cli_sab_n) {
            this.cli_sab_n = cli_sab_n;
        }

        public String getCli_dom_n() {
            return cli_dom_n;
        }

        public void setCli_dom_n(String cli_dom_n) {
            this.cli_dom_n = cli_dom_n;
        }

        public String getFrec_cve_n() {
            return frec_cve_n;
        }

        public void setFrec_cve_n(String frec_cve_n) {
            this.frec_cve_n = frec_cve_n;
        }

        public String getCli_esvallejo_n() {
            return cli_esvallejo_n;
        }

        public void setCli_esvallejo_n(String cli_esvallejo_n) {
            this.cli_esvallejo_n = cli_esvallejo_n;
        }

        public String getNpro_cve_n() {
            return npro_cve_n;
        }

        public void setNpro_cve_n(String npro_cve_n) {
            this.npro_cve_n = npro_cve_n;
        }

        public String getCli_huixdesc_n() {
            return cli_huixdesc_n;
        }

        public void setCli_huixdesc_n(String cli_huixdesc_n) {
            this.cli_huixdesc_n = cli_huixdesc_n;
        }

        public String getCli_eshuix_n() {
            return cli_eshuix_n;
        }

        public void setCli_eshuix_n(String cli_eshuix_n) {
            this.cli_eshuix_n = cli_eshuix_n;
        }

        public String getCli_prospecto_n() {
            return cli_prospecto_n;
        }

        public void setCli_prospecto_n(String cli_prospecto_n) {
            this.cli_prospecto_n = cli_prospecto_n;
        }

        public String getCli_invalidafrecuencia_n() {
            return cli_invalidafrecuencia_n;
        }

        public void setCli_invalidafrecuencia_n(String cli_invalidafrecuencia_n) {
            this.cli_invalidafrecuencia_n = cli_invalidafrecuencia_n;
        }

        public String getCli_invalidagps_n() {
            return cli_invalidagps_n;
        }

        public void setCli_invalidagps_n(String cli_invalidagps_n) {
            this.cli_invalidagps_n = cli_invalidagps_n;
        }

        public String getCli_dobleventa_n() {
            return cli_dobleventa_n;
        }

        public void setCli_dobleventa_n(String cli_dobleventa_n) {
            this.cli_dobleventa_n = cli_dobleventa_n;
        }

        public String getCli_comodato_n() {
            return cli_comodato_n;
        }

        public void setCli_comodato_n(String cli_comodato_n) {
            this.cli_comodato_n = cli_comodato_n;
        }

        public String getSeg_cve_n() {
            return seg_cve_n;
        }

        public void setSeg_cve_n(String seg_cve_n) {
            this.seg_cve_n = seg_cve_n;
        }

        public String getCli_dispersion_n() {
            return cli_dispersion_n;
        }

        public void setCli_dispersion_n(String cli_dispersion_n) {
            this.cli_dispersion_n = cli_dispersion_n;
        }

        public String getCli_dispersioncant_n() {
            return cli_dispersioncant_n;
        }

        public void setCli_dispersioncant_n(String cli_dispersioncant_n) {
            this.cli_dispersioncant_n = cli_dispersioncant_n;
        }

        public String getCli_limitemes_n() {
            return cli_limitemes_n;
        }

        public void setCli_limitemes_n(String cli_limitemes_n) {
            this.cli_limitemes_n = cli_limitemes_n;
        }
    }
    
    public static class Creditos
    {
        private String cred_cve_n;
        private String cred_referencia_str;
        private String cred_est_str;
        private String cli_cve_n;
        private String usu_cve_str;
        private String cred_fecha_dt;
        private String cred_descripcion_str;
        private String cred_vencimiento_dt;
        private String cred_monto_n;
        private String cred_abono_n;
        private String cred_engestoria_n;
        private String cred_esenvase_n;
        private String cred_especial_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_precio_n;
        private String prod_cant_n;
        private String prod_cantabono_n;
        private String cred_esdeasesor_n;
        private String cred_observaciones_str;
        private String trans_est_n;
        private String trans_fecha_dt;
        private String cred_cancelacion_dt;
        private String usu_cancelacion_str;
        private String cred_machinename_str;
        private String cred_domainuser_str;
        private String cred_cancelacionauditoria_n;
        private String cred_montoauditoria_n;
        private String cred_obsauditoria_str;


        public Creditos() {
        }

        public String getCred_cve_n() {
            return cred_cve_n;
        }

        public void setCred_cve_n(String cred_cve_n) {
            this.cred_cve_n = cred_cve_n;
        }

        public String getCred_referencia_str() {
            return cred_referencia_str;
        }

        public void setCred_referencia_str(String cred_referencia_str) {
            this.cred_referencia_str = cred_referencia_str;
        }

        public String getCred_est_str() {
            return cred_est_str;
        }

        public void setCred_est_str(String cred_est_str) {
            this.cred_est_str = cred_est_str;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getCred_fecha_dt() {
            return cred_fecha_dt;
        }

        public void setCred_fecha_dt(String cred_fecha_dt) {
            this.cred_fecha_dt = cred_fecha_dt;
        }

        public String getCred_descripcion_str() {
            return cred_descripcion_str;
        }

        public void setCred_descripcion_str(String cred_descripcion_str) {
            this.cred_descripcion_str = cred_descripcion_str;
        }

        public String getCred_vencimiento_dt() {
            return cred_vencimiento_dt;
        }

        public void setCred_vencimiento_dt(String cred_vencimiento_dt) {
            this.cred_vencimiento_dt = cred_vencimiento_dt;
        }

        public String getCred_monto_n() {
            return cred_monto_n;
        }

        public void setCred_monto_n(String cred_monto_n) {
            this.cred_monto_n = cred_monto_n;
        }

        public String getCred_abono_n() {
            return cred_abono_n;
        }

        public void setCred_abono_n(String cred_abono_n) {
            this.cred_abono_n = cred_abono_n;
        }

        public String getCred_engestoria_n() {
            return cred_engestoria_n;
        }

        public void setCred_engestoria_n(String cred_engestoria_n) {
            this.cred_engestoria_n = cred_engestoria_n;
        }

        public String getCred_esenvase_n() {
            return cred_esenvase_n;
        }

        public void setCred_esenvase_n(String cred_esenvase_n) {
            this.cred_esenvase_n = cred_esenvase_n;
        }

        public String getCred_especial_n() {
            return cred_especial_n;
        }

        public void setCred_especial_n(String cred_especial_n) {
            this.cred_especial_n = cred_especial_n;
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

        public String getProd_precio_n() {
            return prod_precio_n;
        }

        public void setProd_precio_n(String prod_precio_n) {
            this.prod_precio_n = prod_precio_n;
        }

        public String getProd_cant_n() {
            return prod_cant_n;
        }

        public void setProd_cant_n(String prod_cant_n) {
            this.prod_cant_n = prod_cant_n;
        }

        public String getProd_cantabono_n() {
            return prod_cantabono_n;
        }

        public void setProd_cantabono_n(String prod_cantabono_n) {
            this.prod_cantabono_n = prod_cantabono_n;
        }

        public String getCred_esdeasesor_n() {
            return cred_esdeasesor_n;
        }

        public void setCred_esdeasesor_n(String cred_esdeasesor_n) {
            this.cred_esdeasesor_n = cred_esdeasesor_n;
        }

        public String getCred_observaciones_str() {
            return cred_observaciones_str;
        }

        public void setCred_observaciones_str(String cred_observaciones_str) {
            this.cred_observaciones_str = cred_observaciones_str;
        }

        public String getTrans_est_n() {
            return trans_est_n;
        }

        public void setTrans_est_n(String trans_est_n) {
            this.trans_est_n = trans_est_n;
        }

        public String getTrans_fecha_dt() {
            return trans_fecha_dt;
        }

        public void setTrans_fecha_dt(String trans_fecha_dt) {
            this.trans_fecha_dt = trans_fecha_dt;
        }

        public String getCred_cancelacion_dt() {
            return cred_cancelacion_dt;
        }

        public void setCred_cancelacion_dt(String cred_cancelacion_dt) {
            this.cred_cancelacion_dt = cred_cancelacion_dt;
        }

        public String getUsu_cancelacion_str() {
            return usu_cancelacion_str;
        }

        public void setUsu_cancelacion_str(String usu_cancelacion_str) {
            this.usu_cancelacion_str = usu_cancelacion_str;
        }

        public String getCred_machinename_str() {
            return cred_machinename_str;
        }

        public void setCred_machinename_str(String cred_machinename_str) {
            this.cred_machinename_str = cred_machinename_str;
        }

        public String getCred_domainuser_str() {
            return cred_domainuser_str;
        }

        public void setCred_domainuser_str(String cred_domainuser_str) {
            this.cred_domainuser_str = cred_domainuser_str;
        }

        public String getCred_cancelacionauditoria_n() {
            return cred_cancelacionauditoria_n;
        }

        public void setCred_cancelacionauditoria_n(String cred_cancelacionauditoria_n) {
            this.cred_cancelacionauditoria_n = cred_cancelacionauditoria_n;
        }

        public String getCred_montoauditoria_n() {
            return cred_montoauditoria_n;
        }

        public void setCred_montoauditoria_n(String cred_montoauditoria_n) {
            this.cred_montoauditoria_n = cred_montoauditoria_n;
        }

        public String getCred_obsauditoria_str() {
            return cred_obsauditoria_str;
        }

        public void setCred_obsauditoria_str(String cred_obsauditoria_str) {
            this.cred_obsauditoria_str = cred_obsauditoria_str;
        }
    }
    
    public static class Direcciones
    {
        private String dir_cve_n;
        private String cli_cve_n;
        private String dir_alias_str;
        private String dir_calle_str;
        private String dir_noext_str;
        private String dir_noint_str;
        private String dir_entrecalle1_str;
        private String dir_entrecalle2_str;
        private String dir_colonia_str;
        private String dir_municipio_str;
        private String dir_estado_str;
        private String dir_pais_str;
        private String dir_codigopostal_str;
        private String dir_referencia_str;
        private String est_cve_str;
        private String usu_cve_str;
        private String dir_falta_dt;
        private String dir_tel1_str;
        private String dir_tel2_str;
        private String dir_encargado_str;
        private String dir_coordenada_str;
        private String usu_cvemodif_str;
        private String dir_fmodificacion_dt;


        public Direcciones() {
        }

        public String getDir_cve_n() {
            return dir_cve_n;
        }

        public void setDir_cve_n(String dir_cve_n) {
            this.dir_cve_n = dir_cve_n;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getDir_alias_str() {
            return dir_alias_str;
        }

        public void setDir_alias_str(String dir_alias_str) {
            this.dir_alias_str = dir_alias_str;
        }

        public String getDir_calle_str() {
            return dir_calle_str;
        }

        public void setDir_calle_str(String dir_calle_str) {
            this.dir_calle_str = dir_calle_str;
        }

        public String getDir_noext_str() {
            return dir_noext_str;
        }

        public void setDir_noext_str(String dir_noext_str) {
            this.dir_noext_str = dir_noext_str;
        }

        public String getDir_noint_str() {
            return dir_noint_str;
        }

        public void setDir_noint_str(String dir_noint_str) {
            this.dir_noint_str = dir_noint_str;
        }

        public String getDir_entrecalle1_str() {
            return dir_entrecalle1_str;
        }

        public void setDir_entrecalle1_str(String dir_entrecalle1_str) {
            this.dir_entrecalle1_str = dir_entrecalle1_str;
        }

        public String getDir_entrecalle2_str() {
            return dir_entrecalle2_str;
        }

        public void setDir_entrecalle2_str(String dir_entrecalle2_str) {
            this.dir_entrecalle2_str = dir_entrecalle2_str;
        }

        public String getDir_colonia_str() {
            return dir_colonia_str;
        }

        public void setDir_colonia_str(String dir_colonia_str) {
            this.dir_colonia_str = dir_colonia_str;
        }

        public String getDir_municipio_str() {
            return dir_municipio_str;
        }

        public void setDir_municipio_str(String dir_municipio_str) {
            this.dir_municipio_str = dir_municipio_str;
        }

        public String getDir_estado_str() {
            return dir_estado_str;
        }

        public void setDir_estado_str(String dir_estado_str) {
            this.dir_estado_str = dir_estado_str;
        }

        public String getDir_pais_str() {
            return dir_pais_str;
        }

        public void setDir_pais_str(String dir_pais_str) {
            this.dir_pais_str = dir_pais_str;
        }

        public String getDir_codigopostal_str() {
            return dir_codigopostal_str;
        }

        public void setDir_codigopostal_str(String dir_codigopostal_str) {
            this.dir_codigopostal_str = dir_codigopostal_str;
        }

        public String getDir_referencia_str() {
            return dir_referencia_str;
        }

        public void setDir_referencia_str(String dir_referencia_str) {
            this.dir_referencia_str = dir_referencia_str;
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

        public String getDir_falta_dt() {
            return dir_falta_dt;
        }

        public void setDir_falta_dt(String dir_falta_dt) {
            this.dir_falta_dt = dir_falta_dt;
        }

        public String getDir_tel1_str() {
            return dir_tel1_str;
        }

        public void setDir_tel1_str(String dir_tel1_str) {
            this.dir_tel1_str = dir_tel1_str;
        }

        public String getDir_tel2_str() {
            return dir_tel2_str;
        }

        public void setDir_tel2_str(String dir_tel2_str) {
            this.dir_tel2_str = dir_tel2_str;
        }

        public String getDir_encargado_str() {
            return dir_encargado_str;
        }

        public void setDir_encargado_str(String dir_encargado_str) {
            this.dir_encargado_str = dir_encargado_str;
        }

        public String getDir_coordenada_str() {
            return dir_coordenada_str;
        }

        public void setDir_coordenada_str(String dir_coordenada_str) {
            this.dir_coordenada_str = dir_coordenada_str;
        }

        public String getUsu_cvemodif_str() {
            return usu_cvemodif_str;
        }

        public void setUsu_cvemodif_str(String usu_cvemodif_str) {
            this.usu_cvemodif_str = usu_cvemodif_str;
        }

        public String getDir_fmodificacion_dt() {
            return dir_fmodificacion_dt;
        }

        public void setDir_fmodificacion_dt(String dir_fmodificacion_dt) {
            this.dir_fmodificacion_dt = dir_fmodificacion_dt;
        }
    }

    public static class ClientesVentaMes
    {
        private String rut_cve_n;
        private String cli_cve_n;
        private String cvm_vtaacum_n;

        public ClientesVentaMes() {
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getCvm_vtaacum_n() {
            return cvm_vtaacum_n;
        }

        public void setCvm_vtaacum_n(String cvm_vtaacum_n) {
            this.cvm_vtaacum_n = cvm_vtaacum_n;
        }
    }
    
    public static class Consignas
    {
        private String csgn_cve_str;
        private String emp_cve_n;
        private String alm_cve_n;
        private String cli_cve_n;
        private String cli_cveext_str;
        private String rut_cve_n;
        private String cli_nom_str;
        private String cli_app_str;
        private String cli_apm_str;
        private String cli_tel1_str;
        private String cli_tel2_str;
        private String dir_calle_str;
        private String dir_noext_str;
        private String dir_noint_str;
        private String dir_entrecalle1_str;
        private String dir_entrecalle2_str;
        private String dir_colonia_str;
        private String dir_municipio_str;
        private String dir_estado_str;
        private String dir_pais_str;
        private String dir_codigopostal_str;
        private String dir_referencia_str;
        private String dir_encargado_str;
        private String csgn_coordenada_str;
        private String usu_solicita_str;
        private String csgn_fsolicitud_dt;
        private String est_cve_str;
        private String csgn_finicio_dt;
        private String csgn_ffin_dt;
        private String csgn_fcobro_dt;
        private String csgn_fprorroga_dt;
        private String usu_autoriza_str;
        private String csgn_fautorizacion_dt;
        private String csgn_identificacion_str;
        private String csgn_compdom_str;
        private String csgn_pagare_str;
        private String usu_modifica_str;
        private String csgn_fmodificacion_dt;
        private String csgn_montopagare_n;
        private String csgn_observaciones_str;
        private String csgn_entrega_n;

        public Consignas() {
        }

        public String getCsgn_cve_str() {
            return csgn_cve_str;
        }

        public void setCsgn_cve_str(String csgn_cve_str) {
            this.csgn_cve_str = csgn_cve_str;
        }

        public String getEmp_cve_n() {
            return emp_cve_n;
        }

        public void setEmp_cve_n(String emp_cve_n) {
            this.emp_cve_n = emp_cve_n;
        }

        public String getAlm_cve_n() {
            return alm_cve_n;
        }

        public void setAlm_cve_n(String alm_cve_n) {
            this.alm_cve_n = alm_cve_n;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getCli_nom_str() {
            return cli_nom_str;
        }

        public void setCli_nom_str(String cli_nom_str) {
            this.cli_nom_str = cli_nom_str;
        }

        public String getCli_app_str() {
            return cli_app_str;
        }

        public void setCli_app_str(String cli_app_str) {
            this.cli_app_str = cli_app_str;
        }

        public String getCli_apm_str() {
            return cli_apm_str;
        }

        public void setCli_apm_str(String cli_apm_str) {
            this.cli_apm_str = cli_apm_str;
        }

        public String getCli_tel1_str() {
            return cli_tel1_str;
        }

        public void setCli_tel1_str(String cli_tel1_str) {
            this.cli_tel1_str = cli_tel1_str;
        }

        public String getCli_tel2_str() {
            return cli_tel2_str;
        }

        public void setCli_tel2_str(String cli_tel2_str) {
            this.cli_tel2_str = cli_tel2_str;
        }

        public String getDir_calle_str() {
            return dir_calle_str;
        }

        public void setDir_calle_str(String dir_calle_str) {
            this.dir_calle_str = dir_calle_str;
        }

        public String getDir_noext_str() {
            return dir_noext_str;
        }

        public void setDir_noext_str(String dir_noext_str) {
            this.dir_noext_str = dir_noext_str;
        }

        public String getDir_noint_str() {
            return dir_noint_str;
        }

        public void setDir_noint_str(String dir_noint_str) {
            this.dir_noint_str = dir_noint_str;
        }

        public String getDir_entrecalle1_str() {
            return dir_entrecalle1_str;
        }

        public void setDir_entrecalle1_str(String dir_entrecalle1_str) {
            this.dir_entrecalle1_str = dir_entrecalle1_str;
        }

        public String getDir_entrecalle2_str() {
            return dir_entrecalle2_str;
        }

        public void setDir_entrecalle2_str(String dir_entrecalle2_str) {
            this.dir_entrecalle2_str = dir_entrecalle2_str;
        }

        public String getDir_colonia_str() {
            return dir_colonia_str;
        }

        public void setDir_colonia_str(String dir_colonia_str) {
            this.dir_colonia_str = dir_colonia_str;
        }

        public String getDir_municipio_str() {
            return dir_municipio_str;
        }

        public void setDir_municipio_str(String dir_municipio_str) {
            this.dir_municipio_str = dir_municipio_str;
        }

        public String getDir_estado_str() {
            return dir_estado_str;
        }

        public void setDir_estado_str(String dir_estado_str) {
            this.dir_estado_str = dir_estado_str;
        }

        public String getDir_pais_str() {
            return dir_pais_str;
        }

        public void setDir_pais_str(String dir_pais_str) {
            this.dir_pais_str = dir_pais_str;
        }

        public String getDir_codigopostal_str() {
            return dir_codigopostal_str;
        }

        public void setDir_codigopostal_str(String dir_codigopostal_str) {
            this.dir_codigopostal_str = dir_codigopostal_str;
        }

        public String getDir_referencia_str() {
            return dir_referencia_str;
        }

        public void setDir_referencia_str(String dir_referencia_str) {
            this.dir_referencia_str = dir_referencia_str;
        }

        public String getDir_encargado_str() {
            return dir_encargado_str;
        }

        public void setDir_encargado_str(String dir_encargado_str) {
            this.dir_encargado_str = dir_encargado_str;
        }

        public String getCsgn_coordenada_str() {
            return csgn_coordenada_str;
        }

        public void setCsgn_coordenada_str(String csgn_coordenada_str) {
            this.csgn_coordenada_str = csgn_coordenada_str;
        }

        public String getUsu_solicita_str() {
            return usu_solicita_str;
        }

        public void setUsu_solicita_str(String usu_solicita_str) {
            this.usu_solicita_str = usu_solicita_str;
        }

        public String getCsgn_fsolicitud_dt() {
            return csgn_fsolicitud_dt;
        }

        public void setCsgn_fsolicitud_dt(String csgn_fsolicitud_dt) {
            this.csgn_fsolicitud_dt = csgn_fsolicitud_dt;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getCsgn_finicio_dt() {
            return csgn_finicio_dt;
        }

        public void setCsgn_finicio_dt(String csgn_finicio_dt) {
            this.csgn_finicio_dt = csgn_finicio_dt;
        }

        public String getCsgn_ffin_dt() {
            return csgn_ffin_dt;
        }

        public void setCsgn_ffin_dt(String csgn_ffin_dt) {
            this.csgn_ffin_dt = csgn_ffin_dt;
        }

        public String getCsgn_fcobro_dt() {
            return csgn_fcobro_dt;
        }

        public void setCsgn_fcobro_dt(String csgn_fcobro_dt) {
            this.csgn_fcobro_dt = csgn_fcobro_dt;
        }

        public String getCsgn_fprorroga_dt() {
            return csgn_fprorroga_dt;
        }

        public void setCsgn_fprorroga_dt(String csgn_fprorroga_dt) {
            this.csgn_fprorroga_dt = csgn_fprorroga_dt;
        }

        public String getUsu_autoriza_str() {
            return usu_autoriza_str;
        }

        public void setUsu_autoriza_str(String usu_autoriza_str) {
            this.usu_autoriza_str = usu_autoriza_str;
        }

        public String getCsgn_fautorizacion_dt() {
            return csgn_fautorizacion_dt;
        }

        public void setCsgn_fautorizacion_dt(String csgn_fautorizacion_dt) {
            this.csgn_fautorizacion_dt = csgn_fautorizacion_dt;
        }

        public String getCsgn_identificacion_str() {
            return csgn_identificacion_str;
        }

        public void setCsgn_identificacion_str(String csgn_identificacion_str) {
            this.csgn_identificacion_str = csgn_identificacion_str;
        }

        public String getCsgn_compdom_str() {
            return csgn_compdom_str;
        }

        public void setCsgn_compdom_str(String csgn_compdom_str) {
            this.csgn_compdom_str = csgn_compdom_str;
        }

        public String getCsgn_pagare_str() {
            return csgn_pagare_str;
        }

        public void setCsgn_pagare_str(String csgn_pagare_str) {
            this.csgn_pagare_str = csgn_pagare_str;
        }

        public String getUsu_modifica_str() {
            return usu_modifica_str;
        }

        public void setUsu_modifica_str(String usu_modifica_str) {
            this.usu_modifica_str = usu_modifica_str;
        }

        public String getCsgn_fmodificacion_dt() {
            return csgn_fmodificacion_dt;
        }

        public void setCsgn_fmodificacion_dt(String csgn_fmodificacion_dt) {
            this.csgn_fmodificacion_dt = csgn_fmodificacion_dt;
        }

        public String getCsgn_montopagare_n() {
            return csgn_montopagare_n;
        }

        public void setCsgn_montopagare_n(String csgn_montopagare_n) {
            this.csgn_montopagare_n = csgn_montopagare_n;
        }

        public String getCsgn_observaciones_str() {
            return csgn_observaciones_str;
        }

        public void setCsgn_observaciones_str(String csgn_observaciones_str) {
            this.csgn_observaciones_str = csgn_observaciones_str;
        }

        public String getCsgn_entrega_n() {
            return csgn_entrega_n;
        }

        public void setCsgn_entrega_n(String csgn_entrega_n) {
            this.csgn_entrega_n = csgn_entrega_n;
        }
    }
    
    public static class ConsignasDet
    {
        private String csgn_cve_str;
        private String csgn_entrega_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_cant_n;
        private String prod_vendido_n;
        private String prod_devuelto_n;
        private String prod_danado_n;
        private String prod_pagado_n;

        public ConsignasDet() {
        }

        public String getCsgn_cve_str() {
            return csgn_cve_str;
        }

        public void setCsgn_cve_str(String csgn_cve_str) {
            this.csgn_cve_str = csgn_cve_str;
        }

        public String getCsgn_entrega_n() {
            return csgn_entrega_n;
        }

        public void setCsgn_entrega_n(String csgn_entrega_n) {
            this.csgn_entrega_n = csgn_entrega_n;
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

        public String getProd_vendido_n() {
            return prod_vendido_n;
        }

        public void setProd_vendido_n(String prod_vendido_n) {
            this.prod_vendido_n = prod_vendido_n;
        }

        public String getProd_devuelto_n() {
            return prod_devuelto_n;
        }

        public void setProd_devuelto_n(String prod_devuelto_n) {
            this.prod_devuelto_n = prod_devuelto_n;
        }

        public String getProd_danado_n() {
            return prod_danado_n;
        }

        public void setProd_danado_n(String prod_danado_n) {
            this.prod_danado_n = prod_danado_n;
        }

        public String getProd_pagado_n() {
            return prod_pagado_n;
        }

        public void setProd_pagado_n(String prod_pagado_n) {
            this.prod_pagado_n = prod_pagado_n;
        }
    }

    public static class VisitaPreventa
    {
        private String visp_folio_str;
        private String cli_cve_n;
        private String rut_cve_n;
        private String visp_fecha_dt;
        private String visp_coordenada_str;
        private String usu_cve_str;
        private String trans_est_n;
        private String trans_fecha_dt;

        public VisitaPreventa() {
        }

        public String getVisp_folio_str() {
            return visp_folio_str;
        }

        public void setVisp_folio_str(String visp_folio_str) {
            this.visp_folio_str = visp_folio_str;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getVisp_fecha_dt() {
            return visp_fecha_dt;
        }

        public void setVisp_fecha_dt(String visp_fecha_dt) {
            this.visp_fecha_dt = visp_fecha_dt;
        }

        public String getVisp_coordenada_str() {
            return visp_coordenada_str;
        }

        public void setVisp_coordenada_str(String visp_coordenada_str) {
            this.visp_coordenada_str = visp_coordenada_str;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getTrans_est_n() {
            return trans_est_n;
        }

        public void setTrans_est_n(String trans_est_n) {
            this.trans_est_n = trans_est_n;
        }

        public String getTrans_fecha_dt() {
            return trans_fecha_dt;
        }

        public void setTrans_fecha_dt(String trans_fecha_dt) {
            this.trans_fecha_dt = trans_fecha_dt;
        }
    }

    public static class Preventa
    {
        private String prev_folio_str;
        private String cli_cve_n;
        private String rut_cve_n;
        private String prev_fecha_dt;
        private String lpre_cve_n;
        private String dir_cve_n;
        private String usu_cve_str;
        private String prev_coordenada_str;
        private String prev_condicionada_n;
        private String trans_est_n;
        private String trans_fecha_dt;
        private String rut_repcve_n;
        private String prev_comentario_str;

        public Preventa() {
        }

        public String getPrev_folio_str() {
            return prev_folio_str;
        }

        public void setPrev_folio_str(String prev_folio_str) {
            this.prev_folio_str = prev_folio_str;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getPrev_fecha_dt() {
            return prev_fecha_dt;
        }

        public void setPrev_fecha_dt(String prev_fecha_dt) {
            this.prev_fecha_dt = prev_fecha_dt;
        }

        public String getLpre_cve_n() {
            return lpre_cve_n;
        }

        public void setLpre_cve_n(String lpre_cve_n) {
            this.lpre_cve_n = lpre_cve_n;
        }

        public String getDir_cve_n() {
            return dir_cve_n;
        }

        public void setDir_cve_n(String dir_cve_n) {
            this.dir_cve_n = dir_cve_n;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getPrev_coordenada_str() {
            return prev_coordenada_str;
        }

        public void setPrev_coordenada_str(String prev_coordenada_str) {
            this.prev_coordenada_str = prev_coordenada_str;
        }

        public String getPrev_condicionada_n() {
            return prev_condicionada_n;
        }

        public void setPrev_condicionada_n(String prev_condicionada_n) {
            this.prev_condicionada_n = prev_condicionada_n;
        }

        public String getTrans_est_n() {
            return trans_est_n;
        }

        public void setTrans_est_n(String trans_est_n) {
            this.trans_est_n = trans_est_n;
        }

        public String getTrans_fecha_dt() {
            return trans_fecha_dt;
        }

        public void setTrans_fecha_dt(String trans_fecha_dt) {
            this.trans_fecha_dt = trans_fecha_dt;
        }

        public String getRut_repcve_n() {
            return rut_repcve_n;
        }

        public void setRut_repcve_n(String rut_repcve_n) {
            this.rut_repcve_n = rut_repcve_n;
        }

        public String getPrev_comentario_str() {
            return prev_comentario_str;
        }

        public void setPrev_comentario_str(String prev_comentario_str) {
            this.prev_comentario_str = prev_comentario_str;
        }
    }

    public static class PreventaDet
    {
        private String prev_folio_str;
        private String prev_num_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_envase_n;
        private String prod_cant_n;
        private String lpre_base_n;
        private String lpre_cliente_n;
        private String lpre_promo_n;
        private String lpre_precio_n;
        private String prod_promo_n;
        private String prod_pesos_n;
        private String prom_pesos_n;
        private String prom_cve_n;
        private String prod_subtotal_n;
        private String prev_kit_n;

        public String getPrev_folio_str() {
            return prev_folio_str;
        }

        public void setPrev_folio_str(String prev_folio_str) {
            this.prev_folio_str = prev_folio_str;
        }

        public String getPrev_num_n() {
            return prev_num_n;
        }

        public void setPrev_num_n(String prev_num_n) {
            this.prev_num_n = prev_num_n;
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

        public String getProd_envase_n() {
            return prod_envase_n;
        }

        public void setProd_envase_n(String prod_envase_n) {
            this.prod_envase_n = prod_envase_n;
        }

        public String getProd_cant_n() {
            return prod_cant_n;
        }

        public void setProd_cant_n(String prod_cant_n) {
            this.prod_cant_n = prod_cant_n;
        }

        public String getLpre_base_n() {
            return lpre_base_n;
        }

        public void setLpre_base_n(String lpre_base_n) {
            this.lpre_base_n = lpre_base_n;
        }

        public String getLpre_cliente_n() {
            return lpre_cliente_n;
        }

        public void setLpre_cliente_n(String lpre_cliente_n) {
            this.lpre_cliente_n = lpre_cliente_n;
        }

        public String getLpre_promo_n() {
            return lpre_promo_n;
        }

        public void setLpre_promo_n(String lpre_promo_n) {
            this.lpre_promo_n = lpre_promo_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getProd_promo_n() {
            return prod_promo_n;
        }

        public void setProd_promo_n(String prod_promo_n) {
            this.prod_promo_n = prod_promo_n;
        }

        public String getProd_pesos_n() {
            return prod_pesos_n;
        }

        public void setProd_pesos_n(String prod_pesos_n) {
            this.prod_pesos_n = prod_pesos_n;
        }

        public String getProm_pesos_n() {
            return prom_pesos_n;
        }

        public void setProm_pesos_n(String prom_pesos_n) {
            this.prom_pesos_n = prom_pesos_n;
        }

        public String getProm_cve_n() {
            return prom_cve_n;
        }

        public void setProm_cve_n(String prom_cve_n) {
            this.prom_cve_n = prom_cve_n;
        }

        public String getProd_subtotal_n() {
            return prod_subtotal_n;
        }

        public void setProd_subtotal_n(String prod_subtotal_n) {
            this.prod_subtotal_n = prod_subtotal_n;
        }

        public String getPrev_kit_n() {
            return prev_kit_n;
        }

        public void setPrev_kit_n(String prev_kit_n) {
            this.prev_kit_n = prev_kit_n;
        }
    }

    public static class PreventaEnv
    {
        private String prev_folio_str;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_inicial_n;
        private String prod_cargo_n;
        private String prod_abono_n;
        private String prod_regalo_n;
        private String prod_venta_n;
        private String prod_final_n;
        private String lpre_base_n;
        private String lpre_precio_n;
        private String trans_est_n;
        private String trans_fecha_dt;

        public PreventaEnv() {
        }

        public String getPrev_folio_str() {
            return prev_folio_str;
        }

        public void setPrev_folio_str(String prev_folio_str) {
            this.prev_folio_str = prev_folio_str;
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

        public String getProd_inicial_n() {
            return prod_inicial_n;
        }

        public void setProd_inicial_n(String prod_inicial_n) {
            this.prod_inicial_n = prod_inicial_n;
        }

        public String getProd_cargo_n() {
            return prod_cargo_n;
        }

        public void setProd_cargo_n(String prod_cargo_n) {
            this.prod_cargo_n = prod_cargo_n;
        }

        public String getProd_abono_n() {
            return prod_abono_n;
        }

        public void setProd_abono_n(String prod_abono_n) {
            this.prod_abono_n = prod_abono_n;
        }

        public String getProd_regalo_n() {
            return prod_regalo_n;
        }

        public void setProd_regalo_n(String prod_regalo_n) {
            this.prod_regalo_n = prod_regalo_n;
        }

        public String getProd_venta_n() {
            return prod_venta_n;
        }

        public void setProd_venta_n(String prod_venta_n) {
            this.prod_venta_n = prod_venta_n;
        }

        public String getProd_final_n() {
            return prod_final_n;
        }

        public void setProd_final_n(String prod_final_n) {
            this.prod_final_n = prod_final_n;
        }

        public String getLpre_base_n() {
            return lpre_base_n;
        }

        public void setLpre_base_n(String lpre_base_n) {
            this.lpre_base_n = lpre_base_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getTrans_est_n() {
            return trans_est_n;
        }

        public void setTrans_est_n(String trans_est_n) {
            this.trans_est_n = trans_est_n;
        }

        public String getTrans_fecha_dt() {
            return trans_fecha_dt;
        }

        public void setTrans_fecha_dt(String trans_fecha_dt) {
            this.trans_fecha_dt = trans_fecha_dt;
        }
    }

    public static class PreventaPagos
    {
        private String prev_folio_str;
        private String ppag_num_n;
        private String ppag_cobranza_n;
        private String fpag_cve_n;
        private String fpag_cant_n;

        public PreventaPagos() {
        }

        public String getPrev_folio_str() {
            return prev_folio_str;
        }

        public void setPrev_folio_str(String prev_folio_str) {
            this.prev_folio_str = prev_folio_str;
        }

        public String getPpag_num_n() {
            return ppag_num_n;
        }

        public void setPpag_num_n(String ppag_num_n) {
            this.ppag_num_n = ppag_num_n;
        }

        public String getPpag_cobranza_n() {
            return ppag_cobranza_n;
        }

        public void setPpag_cobranza_n(String ppag_cobranza_n) {
            this.ppag_cobranza_n = ppag_cobranza_n;
        }

        public String getFpag_cve_n() {
            return fpag_cve_n;
        }

        public void setFpag_cve_n(String fpag_cve_n) {
            this.fpag_cve_n = fpag_cve_n;
        }

        public String getFpag_cant_n() {
            return fpag_cant_n;
        }

        public void setFpag_cant_n(String fpag_cant_n) {
            this.fpag_cant_n = fpag_cant_n;
        }
    }

    public static class RetValInicioDia{
        String ret;
        String msj;
        String ds;

        public RetValInicioDia() {
        }

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getMsj() {
            return msj;
        }

        public void setMsj(String msj) {
            this.msj = msj;
        }

        public String getDs() {
            return ds;
        }

        public void setDs(String ds) {
            this.ds = ds;
        }
    }
    
    public static class Recargas{
        private String rec_cve_n;
        private String rec_folio_str;
        private String rec_falta_dt;
        private String usu_solicita_str;
        private String rec_observaciones_str;

        public Recargas() {
        }

        public String getRec_cve_n() {
            return rec_cve_n;
        }

        public void setRec_cve_n(String rec_cve_n) {
            this.rec_cve_n = rec_cve_n;
        }

        public String getRec_folio_str() {
            return rec_folio_str;
        }

        public void setRec_folio_str(String rec_folio_str) {
            this.rec_folio_str = rec_folio_str;
        }

        public String getRec_falta_dt() {
            return rec_falta_dt;
        }

        public void setRec_falta_dt(String rec_falta_dt) {
            this.rec_falta_dt = rec_falta_dt;
        }

        public String getUsu_solicita_str() {
            return usu_solicita_str;
        }

        public void setUsu_solicita_str(String usu_solicita_str) {
            this.usu_solicita_str = usu_solicita_str;
        }

        public String getRec_observaciones_str() {
            return rec_observaciones_str;
        }

        public void setRec_observaciones_str(String rec_observaciones_str) {
            this.rec_observaciones_str = rec_observaciones_str;
        }
    }

    public static class RecargasDet
    {
        private String rec_cve_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String prod_cant_n;

        public RecargasDet() {
        }

        public String getRec_cve_n() {
            return rec_cve_n;
        }

        public void setRec_cve_n(String rec_cve_n) {
            this.rec_cve_n = rec_cve_n;
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

        public String getProd_cant_n() {
            return prod_cant_n;
        }

        public void setProd_cant_n(String prod_cant_n) {
            this.prod_cant_n = prod_cant_n;
        }
    }
    
}
