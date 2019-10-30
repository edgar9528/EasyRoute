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

}
