package com.tdt.easyroute.Model;

import java.io.Serializable;
import java.util.Comparator;

public class DataTableLC {

    public static class VentasDet {
        private String ven_folio_str;
        private String vdet_cve_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_envase_n;
        private String prod_cant_n;
        private String env_devueltos_n;
        private String lpre_base_n;
        private String lpre_cliente_n;
        private String lpre_promocion_n;
        private String lpre_precio_n;
        private String prod_subtotal_n;
        private String prod_promo_n;
        private String prom_cve_n;
        private String prod_regalo_n;
        private String prod_cobranza_n;
        private String vdet_dia_n;
        private String vdet_kit_n;

        public VentasDet() {
        }

        public String getVen_folio_str() {
            return ven_folio_str;
        }

        public void setVen_folio_str(String ven_folio_str) {
            this.ven_folio_str = ven_folio_str;
        }

        public String getVdet_cve_n() {
            return vdet_cve_n;
        }

        public void setVdet_cve_n(String vdet_cve_n) {
            this.vdet_cve_n = vdet_cve_n;
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

        public String getEnv_devueltos_n() {
            return env_devueltos_n;
        }

        public void setEnv_devueltos_n(String env_devueltos_n) {
            this.env_devueltos_n = env_devueltos_n;
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

        public String getLpre_promocion_n() {
            return lpre_promocion_n;
        }

        public void setLpre_promocion_n(String lpre_promocion_n) {
            this.lpre_promocion_n = lpre_promocion_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getProd_subtotal_n() {
            return prod_subtotal_n;
        }

        public void setProd_subtotal_n(String prod_subtotal_n) {
            this.prod_subtotal_n = prod_subtotal_n;
        }

        public String getProd_promo_n() {
            return prod_promo_n;
        }

        public void setProd_promo_n(String prod_promo_n) {
            this.prod_promo_n = prod_promo_n;
        }

        public String getProm_cve_n() {
            return prom_cve_n;
        }

        public void setProm_cve_n(String prom_cve_n) {
            this.prom_cve_n = prom_cve_n;
        }

        public String getProd_regalo_n() {
            return prod_regalo_n;
        }

        public void setProd_regalo_n(String prod_regalo_n) {
            this.prod_regalo_n = prod_regalo_n;
        }

        public String getProd_cobranza_n() {
            return prod_cobranza_n;
        }

        public void setProd_cobranza_n(String prod_cobranza_n) {
            this.prod_cobranza_n = prod_cobranza_n;
        }

        public String getVdet_dia_n() {
            return vdet_dia_n;
        }

        public void setVdet_dia_n(String vdet_dia_n) {
            this.vdet_dia_n = vdet_dia_n;
        }

        public String getVdet_kit_n() {
            return vdet_kit_n;
        }

        public void setVdet_kit_n(String vdet_kit_n) {
            this.vdet_kit_n = vdet_kit_n;
        }
    }

    public static class Pagos
    {
        private String pag_cve_n;
        private String conf_cve_n;
        private String pag_referencia_str;
        private String pag_cobranza_n;
        private String pag_abono_n;
        private String pag_fba_n;
        private String pag_envase_n;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_abono_n;
        private String pag_fecha_dt;
        private String fpag_cve_n;
        private String fpag_referencia_str;
        private String fpag_banco_str;
        private String cli_cve_n;
        private String rut_cve_n;
        private String usu_cve_str;
        private String trans_est_n;
        private String trans_fecha_dt;
        private String pag_coordenada_str;
        private String pag_especie_n;


        public Pagos() {
        }

        public String getPag_cve_n() {
            return pag_cve_n;
        }

        public void setPag_cve_n(String pag_cve_n) {
            this.pag_cve_n = pag_cve_n;
        }

        public String getConf_cve_n() {
            return conf_cve_n;
        }

        public void setConf_cve_n(String conf_cve_n) {
            this.conf_cve_n = conf_cve_n;
        }

        public String getPag_referencia_str() {
            return pag_referencia_str;
        }

        public void setPag_referencia_str(String pag_referencia_str) {
            this.pag_referencia_str = pag_referencia_str;
        }

        public String getPag_cobranza_n() {
            return pag_cobranza_n;
        }

        public void setPag_cobranza_n(String pag_cobranza_n) {
            this.pag_cobranza_n = pag_cobranza_n;
        }

        public String getPag_abono_n() {
            return pag_abono_n;
        }

        public void setPag_abono_n(String pag_abono_n) {
            this.pag_abono_n = pag_abono_n;
        }

        public String getPag_fba_n() {
            return pag_fba_n;
        }

        public void setPag_fba_n(String pag_fba_n) {
            this.pag_fba_n = pag_fba_n;
        }

        public String getPag_envase_n() {
            return pag_envase_n;
        }

        public void setPag_envase_n(String pag_envase_n) {
            this.pag_envase_n = pag_envase_n;
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

        public String getProd_abono_n() {
            return prod_abono_n;
        }

        public void setProd_abono_n(String prod_abono_n) {
            this.prod_abono_n = prod_abono_n;
        }

        public String getPag_fecha_dt() {
            return pag_fecha_dt;
        }

        public void setPag_fecha_dt(String pag_fecha_dt) {
            this.pag_fecha_dt = pag_fecha_dt;
        }

        public String getFpag_cve_n() {
            return fpag_cve_n;
        }

        public void setFpag_cve_n(String fpag_cve_n) {
            this.fpag_cve_n = fpag_cve_n;
        }

        public String getFpag_referencia_str() {
            return fpag_referencia_str;
        }

        public void setFpag_referencia_str(String fpag_referencia_str) {
            this.fpag_referencia_str = fpag_referencia_str;
        }

        public String getFpag_banco_str() {
            return fpag_banco_str;
        }

        public void setFpag_banco_str(String fpag_banco_str) {
            this.fpag_banco_str = fpag_banco_str;
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

        public String getPag_coordenada_str() {
            return pag_coordenada_str;
        }

        public void setPag_coordenada_str(String pag_coordenada_str) {
            this.pag_coordenada_str = pag_coordenada_str;
        }

        public String getPag_especie_n() {
            return pag_especie_n;
        }

        public void setPag_especie_n(String pag_especie_n) {
            this.pag_especie_n = pag_especie_n;
        }
    }

    public static class Creditos
    {
        private String cred_cve_n;
        private String cred_referencia_str;
        private String cli_cve_n;
        private String rut_cve_n;
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
        private String trans_est_n;
        private String trans_fecha_dt;
        private String cred_saldo_n;

        public Creditos() {
        }

        public String getCred_saldo_n() {
            return cred_saldo_n;
        }

        public void setCred_saldo_n(String cred_saldo_n) {
            this.cred_saldo_n = cred_saldo_n;
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

    public static class RutaTipo
    {
        String rut_desc_str;
        String trut_desc_str;
        String trut_cve_n;
        String rut_cve_n;
        String asesor_cve_str;

        public RutaTipo() {
        }

        public String getRut_desc_str() {
            return rut_desc_str;
        }

        public void setRut_desc_str(String rut_desc_str) {
            this.rut_desc_str = rut_desc_str;
        }

        public String getTrut_desc_str() {
            return trut_desc_str;
        }

        public void setTrut_desc_str(String trut_desc_str) {
            this.trut_desc_str = trut_desc_str;
        }

        public String getTrut_cve_n() {
            return trut_cve_n;
        }

        public void setTrut_cve_n(String trut_cve_n) {
            this.trut_cve_n = trut_cve_n;
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getAsesor_cve_str() {
            return asesor_cve_str;
        }

        public void setAsesor_cve_str(String asesor_cve_str) {
            this.asesor_cve_str = asesor_cve_str;
        }
    }

    public static class EnvaseAut
    {
        String prod_cve_n;
        String prod_sku_str;
        String prod_cant_n;

        public EnvaseAut() {
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
    }

    public static class InvP
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String prod_cant_n;
        private String prod_sug_n;
        private String cat_desc_str;
        private String id_envase_n;
        private String prod_devuelto_n;
        private String prod_cancelado_n;
        private String prod_recuperado_n;
        private String prod_prestado_n;
        private String prod_vtamismodia_n;
        private String fam_orden_n;
        private String prod_orden_n;
        private String lpre_precio_n;


        public InvP() {
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

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_devuelto_n() {
            return prod_devuelto_n;
        }

        public void setProd_devuelto_n(String prod_devuelto_n) {
            this.prod_devuelto_n = prod_devuelto_n;
        }

        public String getProd_cancelado_n() {
            return prod_cancelado_n;
        }

        public void setProd_cancelado_n(String prod_cancelado_n) {
            this.prod_cancelado_n = prod_cancelado_n;
        }

        public String getProd_recuperado_n() {
            return prod_recuperado_n;
        }

        public void setProd_recuperado_n(String prod_recuperado_n) {
            this.prod_recuperado_n = prod_recuperado_n;
        }

        public String getProd_prestado_n() {
            return prod_prestado_n;
        }

        public void setProd_prestado_n(String prod_prestado_n) {
            this.prod_prestado_n = prod_prestado_n;
        }

        public String getProd_vtamismodia_n() {
            return prod_vtamismodia_n;
        }

        public void setProd_vtamismodia_n(String prod_vtamismodia_n) {
            this.prod_vtamismodia_n = prod_vtamismodia_n;
        }

        public String getFam_orden_n() {
            return fam_orden_n;
        }

        public void setFam_orden_n(String fam_orden_n) {
            this.fam_orden_n = fam_orden_n;
        }

        public String getProd_orden_n() {
            return prod_orden_n;
        }

        public void setProd_orden_n(String prod_orden_n) {
            this.prod_orden_n = prod_orden_n;
        }

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }
    }

    public static class InvAdd
    {
        private String prod_lleno_n;
        private String prod_vacio_n;

        public InvAdd() {
        }

        public String getProd_lleno_n() {
            return prod_lleno_n;
        }

        public void setProd_lleno_n(String prod_lleno_n) {
            this.prod_lleno_n = prod_lleno_n;
        }

        public String getProd_vacio_n() {
            return prod_vacio_n;
        }

        public void setProd_vacio_n(String prod_vacio_n) {
            this.prod_vacio_n = prod_vacio_n;
        }
    }
    
    public static class Inv
    {
        String rut_cve_n;
        String prod_cve_n;
        String prod_cant_n;
        String inv_devuelto_n;
        String inv_cancelado_n;
        String inv_prestado_n;
        String inv_recuperado_n;
        String fam_orden_n;
        String prod_orden_n;

        public Inv() {
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getProd_cant_n() {
            return prod_cant_n;
        }

        public void setProd_cant_n(String prod_cant_n) {
            this.prod_cant_n = prod_cant_n;
        }

        public String getInv_devuelto_n() {
            return inv_devuelto_n;
        }

        public void setInv_devuelto_n(String inv_devuelto_n) {
            this.inv_devuelto_n = inv_devuelto_n;
        }

        public String getInv_cancelado_n() {
            return inv_cancelado_n;
        }

        public void setInv_cancelado_n(String inv_cancelado_n) {
            this.inv_cancelado_n = inv_cancelado_n;
        }

        public String getInv_prestado_n() {
            return inv_prestado_n;
        }

        public void setInv_prestado_n(String inv_prestado_n) {
            this.inv_prestado_n = inv_prestado_n;
        }

        public String getInv_recuperado_n() {
            return inv_recuperado_n;
        }

        public void setInv_recuperado_n(String inv_recuperado_n) {
            this.inv_recuperado_n = inv_recuperado_n;
        }

        public String getFam_orden_n() {
            return fam_orden_n;
        }

        public void setFam_orden_n(String fam_orden_n) {
            this.fam_orden_n = fam_orden_n;
        }

        public String getProd_orden_n() {
            return prod_orden_n;
        }

        public void setProd_orden_n(String prod_orden_n) {
            this.prod_orden_n = prod_orden_n;
        }
    }
    
    public static class Inventario
    {
        private String rut_cve_n;
        private String prod_cve_n;
        private String inv_inicial_n;
        private String inv_vendido_n;
        private String inv_devuelto_n;
        private String inv_danado_n;
        private String inv_recarga_n;
        private String inv_recuperado_n;
        private String inv_prestado_n;
        private String inv_cancelado_n;

        public Inventario() {
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getInv_inicial_n() {
            return inv_inicial_n;
        }

        public void setInv_inicial_n(String inv_inicial_n) {
            this.inv_inicial_n = inv_inicial_n;
        }

        public String getInv_vendido_n() {
            return inv_vendido_n;
        }

        public void setInv_vendido_n(String inv_vendido_n) {
            this.inv_vendido_n = inv_vendido_n;
        }

        public String getInv_devuelto_n() {
            return inv_devuelto_n;
        }

        public void setInv_devuelto_n(String inv_devuelto_n) {
            this.inv_devuelto_n = inv_devuelto_n;
        }

        public String getInv_danado_n() {
            return inv_danado_n;
        }

        public void setInv_danado_n(String inv_danado_n) {
            this.inv_danado_n = inv_danado_n;
        }

        public String getInv_recarga_n() {
            return inv_recarga_n;
        }

        public void setInv_recarga_n(String inv_recarga_n) {
            this.inv_recarga_n = inv_recarga_n;
        }

        public String getInv_recuperado_n() {
            return inv_recuperado_n;
        }

        public void setInv_recuperado_n(String inv_recuperado_n) {
            this.inv_recuperado_n = inv_recuperado_n;
        }

        public String getInv_prestado_n() {
            return inv_prestado_n;
        }

        public void setInv_prestado_n(String inv_prestado_n) {
            this.inv_prestado_n = inv_prestado_n;
        }

        public String getInv_cancelado_n() {
            return inv_cancelado_n;
        }

        public void setInv_cancelado_n(String inv_cancelado_n) {
            this.inv_cancelado_n = inv_cancelado_n;
        }
    }

    public static class DtEnv
    {
        private String prod_cve_n;

        public DtEnv() {
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }
    }
    
    public static class BitacoraHH
    {
        private String bit_cve_n;
        private String usu_cve_str;
        private String rut_cve_n;
        private String cli_cve_n;
        private String bit_fecha_dt;
        private String bit_operacion_str;
        private String bit_comentario_str;
        private String bit_coordenada_str;
        private String trans_est_n;
        private String trans_fecha_dt;

        public BitacoraHH() {
        }

        public String getBit_cve_n() {
            return bit_cve_n;
        }

        public void setBit_cve_n(String bit_cve_n) {
            this.bit_cve_n = bit_cve_n;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
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

        public String getBit_fecha_dt() {
            return bit_fecha_dt;
        }

        public void setBit_fecha_dt(String bit_fecha_dt) {
            this.bit_fecha_dt = bit_fecha_dt;
        }

        public String getBit_operacion_str() {
            return bit_operacion_str;
        }

        public void setBit_operacion_str(String bit_operacion_str) {
            this.bit_operacion_str = bit_operacion_str;
        }

        public String getBit_comentario_str() {
            return bit_comentario_str;
        }

        public void setBit_comentario_str(String bit_comentario_str) {
            this.bit_comentario_str = bit_comentario_str;
        }

        public String getBit_coordenada_str() {
            return bit_coordenada_str;
        }

        public void setBit_coordenada_str(String bit_coordenada_str) {
            this.bit_coordenada_str = bit_coordenada_str;
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

    public static class Clientes1
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_prospecto_n;
        private String est_cve_str;
        private String visitado;
        private String conventa;
        private String concobranza;
        private String noventa;

        public Clientes1() {
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

        public String getCli_prospecto_n() {
            return cli_prospecto_n;
        }

        public void setCli_prospecto_n(String cli_prospecto_n) {
            this.cli_prospecto_n = cli_prospecto_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getVisitado() {
            return visitado;
        }

        public void setVisitado(String visitado) {
            this.visitado = visitado;
        }

        public String getConventa() {
            return conventa;
        }

        public void setConventa(String conventa) {
            this.conventa = conventa;
        }

        public String getConcobranza() {
            return concobranza;
        }

        public void setConcobranza(String concobranza) {
            this.concobranza = concobranza;
        }

        public String getNoventa() {
            return noventa;
        }

        public void setNoventa(String noventa) {
            this.noventa = noventa;
        }
    }

    public static class ClientesSaldo
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_nombrenegocio_str;
        private String cli_razonsocial_str;
        private double cred_monto_n;
        private double pag_abono_n;
        private double saldo;

        public ClientesSaldo() {
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

        public String getCli_nombrenegocio_str() {
            return cli_nombrenegocio_str;
        }

        public void setCli_nombrenegocio_str(String cli_nombrenegocio_str) {
            this.cli_nombrenegocio_str = cli_nombrenegocio_str;
        }

        public String getCli_razonsocial_str() {
            return cli_razonsocial_str;
        }

        public void setCli_razonsocial_str(String cli_razonsocial_str) {
            this.cli_razonsocial_str = cli_razonsocial_str;
        }

        public double getCred_monto_n() {
            return cred_monto_n;
        }

        public void setCred_monto_n(double cred_monto_n) {
            this.cred_monto_n = cred_monto_n;
        }

        public double getPag_abono_n() {
            return pag_abono_n;
        }

        public void setPag_abono_n(double pag_abono_n) {
            this.pag_abono_n = pag_abono_n;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }
    }

    public static class Saldos
    {

        private String cli_cve_n;
        private String cli_cveext_str;
        private String cred_referencia_str;
        private String cred_monto_n;
        private String abono;
        private String saldo;

        public Saldos() {
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

        public String getCred_referencia_str() {
            return cred_referencia_str;
        }

        public void setCred_referencia_str(String cred_referencia_str) {
            this.cred_referencia_str = cred_referencia_str;
        }

        public String getCred_monto_n() {
            return cred_monto_n;
        }

        public void setCred_monto_n(String cred_monto_n) {
            this.cred_monto_n = cred_monto_n;
        }

        public String getAbono() {
            return abono;
        }

        public void setAbono(String abono) {
            this.abono = abono;
        }

        public String getSaldo() {
            return saldo;
        }

        public void setSaldo(String saldo) {
            this.saldo = saldo;
        }
    }

    public static class PreventaPagos
    {
        private String prev_folio_str;
        private String ppag_num_n;
        private String ppag_cobranza_n;
        private String fpag_cve_n;
        private String fpag_cant_n;
        private String fpag_desc_str;
        private String fpag_reqreferencia_n;
        private String fpag_reqbanco_n;

        public PreventaPagos() {
        }

        public String getFpag_reqreferencia_n() {
            return fpag_reqreferencia_n;
        }

        public void setFpag_reqreferencia_n(String fpag_reqreferencia_n) {
            this.fpag_reqreferencia_n = fpag_reqreferencia_n;
        }

        public String getFpag_reqbanco_n() {
            return fpag_reqbanco_n;
        }

        public void setFpag_reqbanco_n(String fpag_reqbanco_n) {
            this.fpag_reqbanco_n = fpag_reqbanco_n;
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

        public String getFpag_desc_str() {
            return fpag_desc_str;
        }

        public void setFpag_desc_str(String fpag_desc_str) {
            this.fpag_desc_str = fpag_desc_str;
        }
    }

    public static class FrecPunteo
    {
        private String cli_cveext_str;
	    private String sec;
        private String frec_cve_n;
        private String est_cve_n;
        private String coor;
	    private String diasem;

        public FrecPunteo() {
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getSec() {
            return sec;
        }

        public void setSec(String sec) {
            this.sec = sec;
        }

        public String getFrec_cve_n() {
            return frec_cve_n;
        }

        public void setFrec_cve_n(String frec_cve_n) {
            this.frec_cve_n = frec_cve_n;
        }

        public String getEst_cve_n() {
            return est_cve_n;
        }

        public void setEst_cve_n(String est_cve_n) {
            this.est_cve_n = est_cve_n;
        }

        public String getCoor() {
            return coor;
        }

        public void setCoor(String coor) {
            this.coor = coor;
        }

        public String getDiasem() {
            return diasem;
        }

        public void setDiasem(String diasem) {
            this.diasem = diasem;
        }
    }

    public static class ClientesOrdenar
    {
        private String cli_cveext_str;
        private String sec;
        private String frec_cve_n; 
        private String est_cve_n;
        private String coor; 
        private String diasem;
        private String cli_razonsocial_str;
        private String cli_nombrenegocio_str; 
        private String frec_desc_str; 
        private String frec_dias_n;

        public ClientesOrdenar() {
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getSec() {
            return sec;
        }

        public void setSec(String sec) {
            this.sec = sec;
        }

        public String getFrec_cve_n() {
            return frec_cve_n;
        }

        public void setFrec_cve_n(String frec_cve_n) {
            this.frec_cve_n = frec_cve_n;
        }

        public String getEst_cve_n() {
            return est_cve_n;
        }

        public void setEst_cve_n(String est_cve_n) {
            this.est_cve_n = est_cve_n;
        }

        public String getCoor() {
            return coor;
        }

        public void setCoor(String coor) {
            this.coor = coor;
        }

        public String getDiasem() {
            return diasem;
        }

        public void setDiasem(String diasem) {
            this.diasem = diasem;
        }

        public String getCli_razonsocial_str() {
            return cli_razonsocial_str;
        }

        public void setCli_razonsocial_str(String cli_razonsocial_str) {
            this.cli_razonsocial_str = cli_razonsocial_str;
        }

        public String getCli_nombrenegocio_str() {
            return cli_nombrenegocio_str;
        }

        public void setCli_nombrenegocio_str(String cli_nombrenegocio_str) {
            this.cli_nombrenegocio_str = cli_nombrenegocio_str;
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
    }

    public static class Sugerido
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String id_envase_n;
        private String prod_sug_n;
        private String cat_desc_str;

        public Sugerido() {
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

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }
    }

    public static class Productos_Sug
    {
        String prod_cve_n;
        String prod_sku_str;
        String prod_desc_str;
        String id_envase_n;
        String fam_desc_str;
        String pres_desc_str;
        String cat_desc_str;

        public Productos_Sug() {
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

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getFam_desc_str() {
            return fam_desc_str;
        }

        public void setFam_desc_str(String fam_desc_str) {
            this.fam_desc_str = fam_desc_str;
        }

        public String getPres_desc_str() {
            return pres_desc_str;
        }

        public void setPres_desc_str(String pres_desc_str) {
            this.pres_desc_str = pres_desc_str;
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }
    }

    public static class ProductosTable
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String id_envase_n;
        private String prod_sug_n;

        public ProductosTable() {
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

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }
    }

    public static class SugPreventa
    {
        String prod_cve_n;
        String prod_sug_n;

        public SugPreventa() {
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }
    }

    public static class SugeridoTable
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_sug_n;

        public SugeridoTable() {
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

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }
    }

    public static class ImprimirSugerido
    {
        String fam_orden_n;
        String prod_orden_n;
        String prod_sku_str;
        String cat_desc_str;
        String prod_desc_str;
        String id_envase_n;
        String prod_sug_n;

        public ImprimirSugerido() {
        }

        public String getFam_orden_n() {
            return fam_orden_n;
        }

        public void setFam_orden_n(String fam_orden_n) {
            this.fam_orden_n = fam_orden_n;
        }

        public String getProd_orden_n() {
            return prod_orden_n;
        }

        public void setProd_orden_n(String prod_orden_n) {
            this.prod_orden_n = prod_orden_n;
        }

        public String getProd_sku_str() {
            return prod_sku_str;
        }

        public void setProd_sku_str(String prod_sku_str) {
            this.prod_sku_str = prod_sku_str;
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }

        public String getProd_desc_str() {
            return prod_desc_str;
        }

        public void setProd_desc_str(String prod_desc_str) {
            this.prod_desc_str = prod_desc_str;
        }

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_sug_n() {
            return prod_sug_n;
        }

        public void setProd_sug_n(String prod_sug_n) {
            this.prod_sug_n = prod_sug_n;
        }
    }

    public static class UsuarioConRol
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
        private String rol_desc_str;
        private String rol_esadmin_n;

        public UsuarioConRol() {
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
    }
    
    public static class CargaInicial
    {
        private String cini_cve_n;
	    private String cini_cargado_n;
        private String cini_fecha_dt;
        private String usu_cve_str;
        private String est_cve_str;
        private String cini_comentario_str;

        public CargaInicial() {
        }

        public String getCini_cve_n() {
            return cini_cve_n;
        }

        public void setCini_cve_n(String cini_cve_n) {
            this.cini_cve_n = cini_cve_n;
        }

        public String getCini_cargado_n() {
            return cini_cargado_n;
        }

        public void setCini_cargado_n(String cini_cargado_n) {
            this.cini_cargado_n = cini_cargado_n;
        }

        public String getCini_fecha_dt() {
            return cini_fecha_dt;
        }

        public void setCini_fecha_dt(String cini_fecha_dt) {
            this.cini_fecha_dt = cini_fecha_dt;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }

        public String getCini_comentario_str() {
            return cini_comentario_str;
        }

        public void setCini_comentario_str(String cini_comentario_str) {
            this.cini_comentario_str = cini_comentario_str;
        }
    }

    public static class SaldoVentaSE
    {
        String ven_folio_str;
        String cli_cve_n;
        String cli_cveext_str;
        String total;

        public SaldoVentaSE() {
        }

        public String getVen_folio_str() {
            return ven_folio_str;
        }

        public void setVen_folio_str(String ven_folio_str) {
            this.ven_folio_str = ven_folio_str;
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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class VentasContado
    {
        String rut_cve_n;
        String total;

        public VentasContado() {
        }

        public String getRut_cve_n() {
            return rut_cve_n;
        }

        public void setRut_cve_n(String rut_cve_n) {
            this.rut_cve_n = rut_cve_n;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class Dtproven
    {
        private String cat_desc_str;
        private String Sku;
        private String Descr;
        private String Descr_LPre;
        private String Total;

        public Dtproven() {
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }

        public String getSku() {
            return Sku;
        }

        public void setSku(String sku) {
            Sku = sku;
        }

        public String getDescr() {
            return Descr;
        }

        public void setDescr(String descr) {
            Descr = descr;
        }

        public String getDescr_LPre() {
            return Descr_LPre;
        }

        public void setDescr_LPre(String descr_LPre) {
            Descr_LPre = descr_LPre;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String total) {
            Total = total;
        }
    }

    public static class Dtprogral
    {
        private String cat_desc_str;
        private String Sku;
        private String Descr;
        private String  Total;

        public Dtprogral() {
        }

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }

        public String getSku() {
            return Sku;
        }

        public void setSku(String sku) {
            Sku = sku;
        }

        public String getDescr() {
            return Descr;
        }

        public void setDescr(String descr) {
            Descr = descr;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String total) {
            Total = total;
        }
    }

    public static class Dtcobros
    {
        private String Tpago;
        private String Monto;

        public Dtcobros() {
        }

        public String getTpago() {
            return Tpago;
        }

        public void setTpago(String tpago) {
            Tpago = tpago;
        }

        public String getMonto() {
            return Monto;
        }

        public void setMonto(String monto) {
            Monto = monto;
        }
    }

    public static class Arqueo_vc
    {
        private String cli_cveext_str;
        private String pag_abono_n;
        private String fpag_desc_str;

        public Arqueo_vc() {
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getPag_abono_n() {
            return pag_abono_n;
        }

        public void setPag_abono_n(String pag_abono_n) {
            this.pag_abono_n = pag_abono_n;
        }

        public String getFpag_desc_str() {
            return fpag_desc_str;
        }

        public void setFpag_desc_str(String fpag_desc_str) {
            this.fpag_desc_str = fpag_desc_str;
        }
    }

    public static class Arqueo_ccred
    {
        private String cli_cveext_str;
        private String cred_referencia_str;
        private String cred_monto_n;

        public Arqueo_ccred() {
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getCred_referencia_str() {
            return cred_referencia_str;
        }

        public void setCred_referencia_str(String cred_referencia_str) {
            this.cred_referencia_str = cred_referencia_str;
        }

        public String getCred_monto_n() {
            return cred_monto_n;
        }

        public void setCred_monto_n(String cred_monto_n) {
            this.cred_monto_n = cred_monto_n;
        }
    }

    public static class Arqueo_cobranza
    {
        private String cli_cveext_str;
        private String pag_abono_n;
        private String fpag_desc_str;

        public Arqueo_cobranza() {
        }

        public String getCli_cveext_str() {
            return cli_cveext_str;
        }

        public void setCli_cveext_str(String cli_cveext_str) {
            this.cli_cveext_str = cli_cveext_str;
        }

        public String getPag_abono_n() {
            return pag_abono_n;
        }

        public void setPag_abono_n(String pag_abono_n) {
            this.pag_abono_n = pag_abono_n;
        }

        public String getFpag_desc_str() {
            return fpag_desc_str;
        }

        public void setFpag_desc_str(String fpag_desc_str) {
            this.fpag_desc_str = fpag_desc_str;
        }
    }

    public static class Arqueo_rec
    {
        private String pag_abono_n;
        private String fpag_desc_str;

        public Arqueo_rec() {
        }

        public String getPag_abono_n() {
            return pag_abono_n;
        }

        public void setPag_abono_n(String pag_abono_n) {
            this.pag_abono_n = pag_abono_n;
        }

        public String getFpag_desc_str() {
            return fpag_desc_str;
        }

        public void setFpag_desc_str(String fpag_desc_str) {
            this.fpag_desc_str = fpag_desc_str;
        }
    }

    public static class Env
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String inv_inicial_n;
        private String inv_cargo_n;
        private String inv_abono_n;
        private String inv_venta_n;
        private String inv_fin_n;

        public Env() {
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

        public String getInv_inicial_n() {
            return inv_inicial_n;
        }

        public void setInv_inicial_n(String inv_inicial_n) {
            this.inv_inicial_n = inv_inicial_n;
        }

        public String getInv_cargo_n() {
            return inv_cargo_n;
        }

        public void setInv_cargo_n(String inv_cargo_n) {
            this.inv_cargo_n = inv_cargo_n;
        }

        public String getInv_abono_n() {
            return inv_abono_n;
        }

        public void setInv_abono_n(String inv_abono_n) {
            this.inv_abono_n = inv_abono_n;
        }

        public String getInv_venta_n() {
            return inv_venta_n;
        }

        public void setInv_venta_n(String inv_venta_n) {
            this.inv_venta_n = inv_venta_n;
        }

        public String getInv_fin_n() {
            return inv_fin_n;
        }

        public void setInv_fin_n(String inv_fin_n) {
            this.inv_fin_n = inv_fin_n;
        }
    }

    public static class EnvPrev {
        private String prod_sku_str;
        private String INI;
        private String CAR;
        private String ABO;
        private String VEN;
        private String FIN;

        public EnvPrev() {
        }

        public String getProd_sku_str() {
            return prod_sku_str;
        }

        public void setProd_sku_str(String prod_sku_str) {
            this.prod_sku_str = prod_sku_str;
        }

        public String getINI() {
            return INI;
        }

        public void setINI(String INI) {
            this.INI = INI;
        }

        public String getCAR() {
            return CAR;
        }

        public void setCAR(String CAR) {
            this.CAR = CAR;
        }

        public String getABO() {
            return ABO;
        }

        public void setABO(String ABO) {
            this.ABO = ABO;
        }

        public String getVEN() {
            return VEN;
        }

        public void setVEN(String VEN) {
            this.VEN = VEN;
        }

        public String getFIN() {
            return FIN;
        }

        public void setFIN(String FIN) {
            this.FIN = FIN;
        }
    }

    public static class SaldoEnvase
    {
        private String prod_cve_n;
        private String ven_cargo_n;
        private String ven_abono_n;
        private String ven_venta_n;

        public SaldoEnvase() {
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getVen_cargo_n() {
            return ven_cargo_n;
        }

        public void setVen_cargo_n(String ven_cargo_n) {
            this.ven_cargo_n = ven_cargo_n;
        }

        public String getVen_abono_n() {
            return ven_abono_n;
        }

        public void setVen_abono_n(String ven_abono_n) {
            this.ven_abono_n = ven_abono_n;
        }

        public String getVen_venta_n() {
            return ven_venta_n;
        }

        public void setVen_venta_n(String ven_venta_n) {
            this.ven_venta_n = ven_venta_n;
        }
    }
    
    public static class PedidosClientes
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_razonsocial_str;
        private String cli_nombrenegocio_str;
        private String cli_especial_n;
        private String cli_prospecto_n;
        private String est_cve_str;

        public PedidosClientes() {
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

        public String getCli_razonsocial_str() {
            return cli_razonsocial_str;
        }

        public void setCli_razonsocial_str(String cli_razonsocial_str) {
            this.cli_razonsocial_str = cli_razonsocial_str;
        }

        public String getCli_nombrenegocio_str() {
            return cli_nombrenegocio_str;
        }

        public void setCli_nombrenegocio_str(String cli_nombrenegocio_str) {
            this.cli_nombrenegocio_str = cli_nombrenegocio_str;
        }

        public String getCli_especial_n() {
            return cli_especial_n;
        }

        public void setCli_especial_n(String cli_especial_n) {
            this.cli_especial_n = cli_especial_n;
        }

        public String getCli_prospecto_n() {
            return cli_prospecto_n;
        }

        public void setCli_prospecto_n(String cli_prospecto_n) {
            this.cli_prospecto_n = cli_prospecto_n;
        }

        public String getEst_cve_str() {
            return est_cve_str;
        }

        public void setEst_cve_str(String est_cve_str) {
            this.est_cve_str = est_cve_str;
        }
    }

    public static class PedidosVisPre
    {
        private String visp_folio_str;
        private String cli_cve_n;
        private String rut_cve_n;
        private String visp_fecha_dt;
        private String visp_coordenada_str;
        private String usu_cve_str;
        private String trans_est_n;
        private String trans_fecha_dt;

        public PedidosVisPre() {
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

    public static class PedidosLv implements Serializable
    {
        private int icono;
        private String cli_cve_n;
        private String cli_est;
        private String cli_cveext_n;
        private String cli_nombre;
        private String cli_especial_n;

        public PedidosLv() {
        }

        public PedidosLv(int icono, String cli_cve_n, String cli_est, String cli_cveext_n, String cli_nombre, String cli_especial_n) {
            this.icono = icono;
            this.cli_cve_n = cli_cve_n;
            this.cli_est = cli_est;
            this.cli_cveext_n = cli_cveext_n;
            this.cli_nombre = cli_nombre;
            this.cli_especial_n = cli_especial_n;
        }

        public int getIcono() {
            return icono;
        }

        public void setIcono(int icono) {
            this.icono = icono;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getCli_est() {
            return cli_est;
        }

        public void setCli_est(String cli_est) {
            this.cli_est = cli_est;
        }

        public String getCli_cveext_n() {
            return cli_cveext_n;
        }

        public void setCli_cveext_n(String cli_cveext_n) {
            this.cli_cveext_n = cli_cveext_n;
        }

        public String getCli_nombre() {
            return cli_nombre;
        }

        public void setCli_nombre(String cli_nombre) {
            this.cli_nombre = cli_nombre;
        }

        public String getCli_especial_n() {
            return cli_especial_n;
        }

        public void setCli_especial_n(String cli_especial_n) {
            this.cli_especial_n = cli_especial_n;
        }
    }
    
    public static class PedidosVisitas
    {
        private String vis_cve_n;
        private String vis_fecha_dt;
        private String usu_cve_str;
        private String cli_cve_n;
        private String mnv_cve_n;
        private String mnl_cve_n;
        private String vis_operacion_str;
        private String vis_observacion_str;
        private String vis_coordenada_str;
        private String trans_est_n;
        private String trans_fecha_dt;

        public PedidosVisitas() {
        }

        public String getVis_cve_n() {
            return vis_cve_n;
        }

        public void setVis_cve_n(String vis_cve_n) {
            this.vis_cve_n = vis_cve_n;
        }

        public String getVis_fecha_dt() {
            return vis_fecha_dt;
        }

        public void setVis_fecha_dt(String vis_fecha_dt) {
            this.vis_fecha_dt = vis_fecha_dt;
        }

        public String getUsu_cve_str() {
            return usu_cve_str;
        }

        public void setUsu_cve_str(String usu_cve_str) {
            this.usu_cve_str = usu_cve_str;
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getMnv_cve_n() {
            return mnv_cve_n;
        }

        public void setMnv_cve_n(String mnv_cve_n) {
            this.mnv_cve_n = mnv_cve_n;
        }

        public String getMnl_cve_n() {
            return mnl_cve_n;
        }

        public void setMnl_cve_n(String mnl_cve_n) {
            this.mnl_cve_n = mnl_cve_n;
        }

        public String getVis_operacion_str() {
            return vis_operacion_str;
        }

        public void setVis_operacion_str(String vis_operacion_str) {
            this.vis_operacion_str = vis_operacion_str;
        }

        public String getVis_observacion_str() {
            return vis_observacion_str;
        }

        public void setVis_observacion_str(String vis_observacion_str) {
            this.vis_observacion_str = vis_observacion_str;
        }

        public String getVis_coordenada_str() {
            return vis_coordenada_str;
        }

        public void setVis_coordenada_str(String vis_coordenada_str) {
            this.vis_coordenada_str = vis_coordenada_str;
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

    public static class Estatus
    {
        private String est_cve_str;
        private String est_desc_str;

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
    }


    public static class Motivos
    {
        private String mov_cve_n;
        private String mov_desc_str;

        public Motivos() {
        }

        public String getMov_cve_n() {
            return mov_cve_n;
        }

        public void setMov_cve_n(String mov_cve_n) {
            this.mov_cve_n = mov_cve_n;
        }

        public String getMov_desc_str() {
            return mov_desc_str;
        }

        public void setMov_desc_str(String mov_desc_str) {
            this.mov_desc_str = mov_desc_str;
        }
    }

    public static class DtCliVenta implements Serializable
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_padre_n;
        private String cli_cvepadre_n;
        private String cli_razonsocial_str;
        private String cli_rfc_str;
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
        private String cli_tel1_str;
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
        private String cli_eshuix_n;
        private String cli_huixdesc_n;
        private String cli_prospecto_n;
        private String cli_invalidafrecuencia_n;
        private String cli_invalidagps_n;
        private String cli_dobleventa_n;
        private String cli_comodato_n;
        private String cli_imagen_n;
        private String cli_invalidacb_n;
        private String seg_cve_n;
        private String cli_dispersion_n;
        private String cli_dispersioncant_n;
        private String cli_limitemes_n;
        private String cvm_vtaacum_n;

        public DtCliVenta() {
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

        public String getCli_rfc_str() {
            return cli_rfc_str;
        }

        public void setCli_rfc_str(String cli_rfc_str) {
            this.cli_rfc_str = cli_rfc_str;
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

        public String getCli_eshuix_n() {
            return cli_eshuix_n;
        }

        public void setCli_eshuix_n(String cli_eshuix_n) {
            this.cli_eshuix_n = cli_eshuix_n;
        }

        public String getCli_huixdesc_n() {
            return cli_huixdesc_n;
        }

        public void setCli_huixdesc_n(String cli_huixdesc_n) {
            this.cli_huixdesc_n = cli_huixdesc_n;
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

        public String getCli_imagen_n() {
            return cli_imagen_n;
        }

        public void setCli_imagen_n(String cli_imagen_n) {
            this.cli_imagen_n = cli_imagen_n;
        }

        public String getCli_invalidacb_n() {
            return cli_invalidacb_n;
        }

        public void setCli_invalidacb_n(String cli_invalidacb_n) {
            this.cli_invalidacb_n = cli_invalidacb_n;
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

        public String getCvm_vtaacum_n() {
            return cvm_vtaacum_n;
        }

        public void setCvm_vtaacum_n(String cvm_vtaacum_n) {
            this.cvm_vtaacum_n = cvm_vtaacum_n;
        }
    }
    
    public static class DtCliVentaNivel
    {
        private String cli_cve_n;
        private String cli_cveext_str;
        private String cli_padre_n;
        private String cli_cvepadre_n;
        private String cli_razonsocial_str;
        private String cli_rfc_str;
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
        private String cli_tel1_str;
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
        private String cli_eshuix_n;
        private String cli_huixdesc_n;
        private String cli_prospecto_n;
        private String cli_invalidafrecuencia_n;
        private String cli_invalidagps_n;
        private String cli_dobleventa_n;
        private String cli_comodato_n;
        private String cli_imagen_n;
        private String cli_invalidacb_n;
        private String seg_cve_n;
        private String cli_dispersion_n;
        private String cli_dispersioncant_n;
        private String cli_limitemes_n;
        private String cvm_vtaacum_n;
        private String nvc_nivel_n;

        public DtCliVentaNivel() {
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

        public String getCli_rfc_str() {
            return cli_rfc_str;
        }

        public void setCli_rfc_str(String cli_rfc_str) {
            this.cli_rfc_str = cli_rfc_str;
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

        public String getCli_eshuix_n() {
            return cli_eshuix_n;
        }

        public void setCli_eshuix_n(String cli_eshuix_n) {
            this.cli_eshuix_n = cli_eshuix_n;
        }

        public String getCli_huixdesc_n() {
            return cli_huixdesc_n;
        }

        public void setCli_huixdesc_n(String cli_huixdesc_n) {
            this.cli_huixdesc_n = cli_huixdesc_n;
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

        public String getCli_imagen_n() {
            return cli_imagen_n;
        }

        public void setCli_imagen_n(String cli_imagen_n) {
            this.cli_imagen_n = cli_imagen_n;
        }

        public String getCli_invalidacb_n() {
            return cli_invalidacb_n;
        }

        public void setCli_invalidacb_n(String cli_invalidacb_n) {
            this.cli_invalidacb_n = cli_invalidacb_n;
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

        public String getCvm_vtaacum_n() {
            return cvm_vtaacum_n;
        }

        public void setCvm_vtaacum_n(String cvm_vtaacum_n) {
            this.cvm_vtaacum_n = cvm_vtaacum_n;
        }

        public String getNvc_nivel_n() {
            return nvc_nivel_n;
        }

        public void setNvc_nivel_n(String nvc_nivel_n) {
            this.nvc_nivel_n = nvc_nivel_n;
        }
    }

    public static class Venta
    {
        private String ven_folio_str;
        private String cli_cve_n;
        private String rut_cve_n;
        private String ven_fecha_dt;
        private String ven_est_str;
        private String lpre_cve_n;
        private String dir_cve_n;
        private String usu_cve_str;
        private String ven_coordenada_str;
        private String ven_credito_n;
        private String prev_folio_str;
        private String ped_folio_str;
        private String csgn_cve_str;
        private String csgn_entrega_n;
        private String ven_consigna_n;
        private String ven_pulest_n;
        private String ven_comentario_str;
        private String trans_est_n;
        private String trans_fecha_dt;

        public Venta() {
        }

        public String getVen_folio_str() {
            return ven_folio_str;
        }

        public void setVen_folio_str(String ven_folio_str) {
            this.ven_folio_str = ven_folio_str;
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

        public String getVen_fecha_dt() {
            return ven_fecha_dt;
        }

        public void setVen_fecha_dt(String ven_fecha_dt) {
            this.ven_fecha_dt = ven_fecha_dt;
        }

        public String getVen_est_str() {
            return ven_est_str;
        }

        public void setVen_est_str(String ven_est_str) {
            this.ven_est_str = ven_est_str;
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

        public String getVen_coordenada_str() {
            return ven_coordenada_str;
        }

        public void setVen_coordenada_str(String ven_coordenada_str) {
            this.ven_coordenada_str = ven_coordenada_str;
        }

        public String getVen_credito_n() {
            return ven_credito_n;
        }

        public void setVen_credito_n(String ven_credito_n) {
            this.ven_credito_n = ven_credito_n;
        }

        public String getPrev_folio_str() {
            return prev_folio_str;
        }

        public void setPrev_folio_str(String prev_folio_str) {
            this.prev_folio_str = prev_folio_str;
        }

        public String getPed_folio_str() {
            return ped_folio_str;
        }

        public void setPed_folio_str(String ped_folio_str) {
            this.ped_folio_str = ped_folio_str;
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

        public String getVen_consigna_n() {
            return ven_consigna_n;
        }

        public void setVen_consigna_n(String ven_consigna_n) {
            this.ven_consigna_n = ven_consigna_n;
        }

        public String getVen_pulest_n() {
            return ven_pulest_n;
        }

        public void setVen_pulest_n(String ven_pulest_n) {
            this.ven_pulest_n = ven_pulest_n;
        }

        public String getVen_comentario_str() {
            return ven_comentario_str;
        }

        public void setVen_comentario_str(String ven_comentario_str) {
            this.ven_comentario_str = ven_comentario_str;
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

    public static class ProductosPed
    {
        private String lpre_cve_n;
        private String lpre_desc_str;
        private String prod_cve_n;
        private String fam_cve_n;
        private String fam_desc_str;
        private String pres_cve_n;
        private String pres_desc_str;
        private String prod_sku_str;
        private String prod_desc_str;
        private String cat_desc_str;
        private String id_envase_n;
        private String prod_manejainv_n;
        private String prod_cantiv_n;
        private String prod_cant_n;
        private String lpre_base_n;
        private String lpre_cliente_n;
        private String lpre_promo_n;
        private String lpre_precio_n;
        private String lpre_precio2_n;
        private String lpre_desc_n;
        private String prod_promo_n;
        private String prom_cve_n;
        private String prom_contado_n;
        private String prod_vtaefectivo_n;
        private String lpre_nota_n;
        private String lprevolumen_n;
        private String prod_vtavolumen_n;
        private String cov_cve_n;
        private String cov_reStringido_n;
        private String cov_familias_str;
        private String prom_kit_n;
        private String subtotal;

        public ProductosPed() {
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

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
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

        public String getCat_desc_str() {
            return cat_desc_str;
        }

        public void setCat_desc_str(String cat_desc_str) {
            this.cat_desc_str = cat_desc_str;
        }

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }

        public String getProd_manejainv_n() {
            return prod_manejainv_n;
        }

        public void setProd_manejainv_n(String prod_manejainv_n) {
            this.prod_manejainv_n = prod_manejainv_n;
        }

        public String getProd_cantiv_n() {
            return prod_cantiv_n;
        }

        public void setProd_cantiv_n(String prod_cantiv_n) {
            this.prod_cantiv_n = prod_cantiv_n;
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

        public String getProd_promo_n() {
            return prod_promo_n;
        }

        public void setProd_promo_n(String prod_promo_n) {
            this.prod_promo_n = prod_promo_n;
        }

        public String getProm_cve_n() {
            return prom_cve_n;
        }

        public void setProm_cve_n(String prom_cve_n) {
            this.prom_cve_n = prom_cve_n;
        }

        public String getProm_contado_n() {
            return prom_contado_n;
        }

        public void setProm_contado_n(String prom_contado_n) {
            this.prom_contado_n = prom_contado_n;
        }

        public String getProd_vtaefectivo_n() {
            return prod_vtaefectivo_n;
        }

        public void setProd_vtaefectivo_n(String prod_vtaefectivo_n) {
            this.prod_vtaefectivo_n = prod_vtaefectivo_n;
        }

        public String getLpre_nota_n() {
            return lpre_nota_n;
        }

        public void setLpre_nota_n(String lpre_nota_n) {
            this.lpre_nota_n = lpre_nota_n;
        }

        public String getLprevolumen_n() {
            return lprevolumen_n;
        }

        public void setLprevolumen_n(String lprevolumen_n) {
            this.lprevolumen_n = lprevolumen_n;
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

        public String getCov_reStringido_n() {
            return cov_reStringido_n;
        }

        public void setCov_reStringido_n(String cov_reStringido_n) {
            this.cov_reStringido_n = cov_reStringido_n;
        }

        public String getCov_familias_str() {
            return cov_familias_str;
        }

        public void setCov_familias_str(String cov_familias_str) {
            this.cov_familias_str = cov_familias_str;
        }

        public String getProm_kit_n() {
            return prom_kit_n;
        }

        public void setProm_kit_n(String prom_kit_n) {
            this.prom_kit_n = prom_kit_n;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }
    }

    public static class EnvasesPed
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String lpre_precio_n;
        private String prod_cantiv_n;
        private String Entregado;
        private String Recibido;
        private String Regalo;
        private String Venta;
        private String lpre_base_n;
        private String Restante;
        private String SubAbonoEnv;
        private String Subtotal;

        public EnvasesPed() {
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

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getProd_cantiv_n() {
            return prod_cantiv_n;
        }

        public void setProd_cantiv_n(String prod_cantiv_n) {
            this.prod_cantiv_n = prod_cantiv_n;
        }

        public String getEntregado() {
            return Entregado;
        }

        public void setEntregado(String entregado) {
            Entregado = entregado;
        }

        public String getRecibido() {
            return Recibido;
        }

        public void setRecibido(String recibido) {
            Recibido = recibido;
        }

        public String getRegalo() {
            return Regalo;
        }

        public void setRegalo(String regalo) {
            Regalo = regalo;
        }

        public String getVenta() {
            return Venta;
        }

        public void setVenta(String venta) {
            Venta = venta;
        }

        public String getLpre_base_n() {
            return lpre_base_n;
        }

        public void setLpre_base_n(String lpre_base_n) {
            this.lpre_base_n = lpre_base_n;
        }

        public String getRestante() {
            return Restante;
        }

        public void setRestante(String restante) {
            Restante = restante;
        }

        public String getSubAbonoEnv() {
            return SubAbonoEnv;
        }

        public void setSubAbonoEnv(String subAbonoEnv) {
            SubAbonoEnv = subAbonoEnv;
        }

        public String getSubtotal() {
            return Subtotal;
        }

        public void setSubtotal(String subtotal) {
            Subtotal = subtotal;
        }
    }
    
    public static class EnvasesAdeudo
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String lpre_precio_n;
        private String prod_cantiv_n;
        private String Adeudo;
        private String Abono;
        private String Venta;
        private String Saldo;
        private String SubPagoEnv;
        private String SubTotal;

        public EnvasesAdeudo() {
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

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
        }

        public String getProd_cantiv_n() {
            return prod_cantiv_n;
        }

        public void setProd_cantiv_n(String prod_cantiv_n) {
            this.prod_cantiv_n = prod_cantiv_n;
        }

        public String getAdeudo() {
            return Adeudo;
        }

        public void setAdeudo(String adeudo) {
            Adeudo = adeudo;
        }

        public String getAbono() {
            return Abono;
        }

        public void setAbono(String abono) {
            Abono = abono;
        }

        public String getVenta() {
            return Venta;
        }

        public void setVenta(String venta) {
            Venta = venta;
        }

        public String getSaldo() {
            return Saldo;
        }

        public void setSaldo(String saldo) {
            Saldo = saldo;
        }

        public String getSubPagoEnv() {
            return SubPagoEnv;
        }

        public void setSubPagoEnv(String subPagoEnv) {
            SubPagoEnv = subPagoEnv;
        }

        public String getSubTotal() {
            return SubTotal;
        }

        public void setSubTotal(String subTotal) {
            SubTotal = subTotal;
        }
    }
    
    public static class Promociones5
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
        private String nvc_nivel_n;
        private String prom_contado_n;

        public Promociones5() {
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

        public String getNvc_nivel_n() {
            return nvc_nivel_n;
        }

        public void setNvc_nivel_n(String nvc_nivel_n) {
            this.nvc_nivel_n = nvc_nivel_n;
        }

        public String getProm_contado_n() {
            return prom_contado_n;
        }

        public void setProm_contado_n(String prom_contado_n) {
            this.prom_contado_n = prom_contado_n;
        }
    }
    
    public static class PedPromocionesKit
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
        private String prod_cant_n;
        private String lpre_precio_n;
        private String prom_nivel_n;
        private String lpre_cve_n;
        private String nvc_cve_n;
        private String nvc_cvehl_n;
        private String fam_cve_n;
        private String seg_cve_n;
        private String giro_cve_n;
        private String tcli_cve_n;
        private String prom_story_n;
        private String prom_proveedor_n;
        private String nvc_nivel_n;
        private String prom_contado_n;
        private String prom_veces_n;

        public PedPromocionesKit() {
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

        public String getNvc_nivel_n() {
            return nvc_nivel_n;
        }

        public void setNvc_nivel_n(String nvc_nivel_n) {
            this.nvc_nivel_n = nvc_nivel_n;
        }

        public String getProm_contado_n() {
            return prom_contado_n;
        }

        public void setProm_contado_n(String prom_contado_n) {
            this.prom_contado_n = prom_contado_n;
        }

        public String getProm_veces_n() {
            return prom_veces_n;
        }

        public void setProm_veces_n(String prom_veces_n) {
            this.prom_veces_n = prom_veces_n;
        }
    }

    public static class DgPagos
    {
        private String NoPago;
        private String fpag_cve_n;
        private String fpag_desc_str;
        private String fpag_cant_n;
        private String BancoP;
        private String ReferenciaP;

        public DgPagos() {
        }

        public String getNoPago() {
            return NoPago;
        }

        public void setNoPago(String noPago) {
            NoPago = noPago;
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

        public String getFpag_cant_n() {
            return fpag_cant_n;
        }

        public void setFpag_cant_n(String fpag_cant_n) {
            this.fpag_cant_n = fpag_cant_n;
        }

        public String getBancoP() {
            return BancoP;
        }

        public void setBancoP(String bancoP) {
            BancoP = bancoP;
        }

        public String getReferenciaP() {
            return ReferenciaP;
        }

        public void setReferenciaP(String referenciaP) {
            ReferenciaP = referenciaP;
        }
    }

    public static class DgAbonos
    {
        private String NoAbono;
        private String fpag_cve_n;
        private String fpag_desc_str;
        private String fpag_cant_n;
        private String BancoA;
        private String ReferenciaA;

        public DgAbonos() {
        }

        public String getNoAbono() {
            return NoAbono;
        }

        public void setNoAbono(String noAbono) {
            NoAbono = noAbono;
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

        public String getFpag_cant_n() {
            return fpag_cant_n;
        }

        public void setFpag_cant_n(String fpag_cant_n) {
            this.fpag_cant_n = fpag_cant_n;
        }

        public String getBancoA() {
            return BancoA;
        }

        public void setBancoA(String bancoA) {
            BancoA = bancoA;
        }

        public String getReferenciaA() {
            return ReferenciaA;
        }

        public void setReferenciaA(String referenciaA) {
            ReferenciaA = referenciaA;
        }
    }

    public static class EnvasesAdeudo2
    {
        private String cli_cve_n;
        private String prod_cve_n;
        private String adeudo;
        private String abono;

        public EnvasesAdeudo2() {
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getAdeudo() {
            return adeudo;
        }

        public void setAdeudo(String adeudo) {
            this.adeudo = adeudo;
        }

        public String getAbono() {
            return abono;
        }

        public void setAbono(String abono) {
            this.abono = abono;
        }
    }

    public static class EnvasesAdeudo3
    {
        private String cli_cve_n;
        private String prod_cve_n;
        private String abono;

        public EnvasesAdeudo3() {
        }

        public String getCli_cve_n() {
            return cli_cve_n;
        }

        public void setCli_cve_n(String cli_cve_n) {
            this.cli_cve_n = cli_cve_n;
        }

        public String getProd_cve_n() {
            return prod_cve_n;
        }

        public void setProd_cve_n(String prod_cve_n) {
            this.prod_cve_n = prod_cve_n;
        }

        public String getAbono() {
            return abono;
        }

        public void setAbono(String abono) {
            this.abono = abono;
        }
    }

    public static class AdeudoNormal
    {
        private String cred_cve_n;
        private String cred_referencia_str;
        private String cred_fecha_dt;
        private String cred_monto_n;
        private String cred_abono_n;
        private String abono;
        private String Saldo;

        public AdeudoNormal() {
        }

        public String getSaldo() {
            return Saldo;
        }

        public void setSaldo(String saldo) {
            Saldo = saldo;
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

        public String getCred_fecha_dt() {
            return cred_fecha_dt;
        }

        public void setCred_fecha_dt(String cred_fecha_dt) {
            this.cred_fecha_dt = cred_fecha_dt;
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

        public String getAbono() {
            return abono;
        }

        public void setAbono(String abono) {
            this.abono = abono;
        }
    }

    public static class Familias implements Comparable<Familias>
    {
        private String fam_cve_n;
        private String fam_desc_str;

        public Familias() {
        }

        public Familias(String fam_cve_n, String fam_desc_str) {
            this.fam_cve_n = fam_cve_n;
            this.fam_desc_str = fam_desc_str;
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


        @Override
        public int compareTo(Familias o) {
            return fam_desc_str.compareTo(o.getFam_desc_str());
        }
    }

    public static class PreventaPedidos
    {
        private String prev_folio_str;
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_envase_n;
        private String prod_cant_n;
        private String lpre_base_n;
        private String lpre_cliente_n;
        private String lpre_promo_n;
        private String lpre_precio_n;
        private String prod_promo_n;
        private String id_envase_n;

        public PreventaPedidos() {
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

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }
    }

    public static class CompPrevDet
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prev_cant_n;
        private String disp_cant_n;
        private String vta_cant_n;
        private String sin_inv;
        private String poco_inv;
        private String id_envase_n;

        public CompPrevDet() {
        }

        public CompPrevDet(String prod_cve_n, String prod_sku_str, String prev_cant_n, String disp_cant_n, String vta_cant_n, String sin_inv, String poco_inv, String id_envase_n) {
            this.prod_cve_n = prod_cve_n;
            this.prod_sku_str = prod_sku_str;
            this.prev_cant_n = prev_cant_n;
            this.disp_cant_n = disp_cant_n;
            this.vta_cant_n = vta_cant_n;
            this.sin_inv = sin_inv;
            this.poco_inv = poco_inv;
            this.id_envase_n = id_envase_n;
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

        public String getPrev_cant_n() {
            return prev_cant_n;
        }

        public void setPrev_cant_n(String prev_cant_n) {
            this.prev_cant_n = prev_cant_n;
        }

        public String getDisp_cant_n() {
            return disp_cant_n;
        }

        public void setDisp_cant_n(String disp_cant_n) {
            this.disp_cant_n = disp_cant_n;
        }

        public String getVta_cant_n() {
            return vta_cant_n;
        }

        public void setVta_cant_n(String vta_cant_n) {
            this.vta_cant_n = vta_cant_n;
        }

        public String getSin_inv() {
            return sin_inv;
        }

        public void setSin_inv(String sin_inv) {
            this.sin_inv = sin_inv;
        }

        public String getPoco_inv() {
            return poco_inv;
        }

        public void setPoco_inv(String poco_inv) {
            this.poco_inv = poco_inv;
        }

        public String getId_envase_n() {
            return id_envase_n;
        }

        public void setId_envase_n(String id_envase_n) {
            this.id_envase_n = id_envase_n;
        }
    }

    public static class PromocionesEnv
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
        private String prom_pesos_n;
        private String lpre_desc_n;
        private String prom_m_n;
        private String prom_n_n;
        private String prod_regalo_n;
        private String prod_skureg_str;
        private String prom_story_n;
        private String prom_proveedor_n;
        private String prom_envase_n;
        private String prom_kit_n;
        private String nvc_nivel_n;
        private String prom_contado_n;

        public PromocionesEnv() {
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

        public String getProm_pesos_n() {
            return prom_pesos_n;
        }

        public void setProm_pesos_n(String prom_pesos_n) {
            this.prom_pesos_n = prom_pesos_n;
        }

        public String getLpre_desc_n() {
            return lpre_desc_n;
        }

        public void setLpre_desc_n(String lpre_desc_n) {
            this.lpre_desc_n = lpre_desc_n;
        }

        public String getProm_m_n() {
            return prom_m_n;
        }

        public void setProm_m_n(String prom_m_n) {
            this.prom_m_n = prom_m_n;
        }

        public String getProm_n_n() {
            return prom_n_n;
        }

        public void setProm_n_n(String prom_n_n) {
            this.prom_n_n = prom_n_n;
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

        public String getNvc_nivel_n() {
            return nvc_nivel_n;
        }

        public void setNvc_nivel_n(String nvc_nivel_n) {
            this.nvc_nivel_n = nvc_nivel_n;
        }

        public String getProm_contado_n() {
            return prom_contado_n;
        }

        public void setProm_contado_n(String prom_contado_n) {
            this.prom_contado_n = prom_contado_n;
        }
    }

    public static class Prueba
    {
        private String nombre;
        private String edad;

        public Prueba() {
        }

        public Prueba(String nombre, String edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEdad() {
            return edad;
        }

        public void setEdad(String edad) {
            this.edad = edad;
        }
    }

    public static class ProdCondiciones
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_desc_str;
        private String fams;

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

        public String getFams() {
            return fams;
        }

        public void setFams(String fams) {
            this.fams = fams;
        }
    }

    public static class MovimientosEnv
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_recibidodia_n;
        private String prod_recibido_n;
        private String prod_abono_n;
        private String prod_venta_n;
        private String prod_ventaant_n;
        private String prod_ventadia_n;
        private String prod_regalo_n;
        private String prod_prestado_n;
        private String prod_cantiv_n;
        private String prod_pago_n;
        private String lpre_base_n;
        private String lpre_precio_n;

        public MovimientosEnv() {
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

        public String getProd_recibidodia_n() {
            return prod_recibidodia_n;
        }

        public void setProd_recibidodia_n(String prod_recibidodia_n) {
            this.prod_recibidodia_n = prod_recibidodia_n;
        }

        public String getProd_recibido_n() {
            return prod_recibido_n;
        }

        public void setProd_recibido_n(String prod_recibido_n) {
            this.prod_recibido_n = prod_recibido_n;
        }

        public String getProd_abono_n() {
            return prod_abono_n;
        }

        public void setProd_abono_n(String prod_abono_n) {
            this.prod_abono_n = prod_abono_n;
        }

        public String getProd_venta_n() {
            return prod_venta_n;
        }

        public void setProd_venta_n(String prod_venta_n) {
            this.prod_venta_n = prod_venta_n;
        }

        public String getProd_ventaant_n() {
            return prod_ventaant_n;
        }

        public void setProd_ventaant_n(String prod_ventaant_n) {
            this.prod_ventaant_n = prod_ventaant_n;
        }

        public String getProd_ventadia_n() {
            return prod_ventadia_n;
        }

        public void setProd_ventadia_n(String prod_ventadia_n) {
            this.prod_ventadia_n = prod_ventadia_n;
        }

        public String getProd_regalo_n() {
            return prod_regalo_n;
        }

        public void setProd_regalo_n(String prod_regalo_n) {
            this.prod_regalo_n = prod_regalo_n;
        }

        public String getProd_prestado_n() {
            return prod_prestado_n;
        }

        public void setProd_prestado_n(String prod_prestado_n) {
            this.prod_prestado_n = prod_prestado_n;
        }

        public String getProd_cantiv_n() {
            return prod_cantiv_n;
        }

        public void setProd_cantiv_n(String prod_cantiv_n) {
            this.prod_cantiv_n = prod_cantiv_n;
        }

        public String getProd_pago_n() {
            return prod_pago_n;
        }

        public void setProd_pago_n(String prod_pago_n) {
            this.prod_pago_n = prod_pago_n;
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
    }
    
    public static class EnvAjustados
    {
        private String cred_cve_n;
        private String cred_referencia_str;
        private String cli_cve_n;
        private String rut_cve_n;
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
        private String trans_est_n;
        private String trans_fecha_dt;
        private String prod_abono_n;
        private String prod_saldo_n;

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

        public String getProd_abono_n() {
            return prod_abono_n;
        }

        public void setProd_abono_n(String prod_abono_n) {
            this.prod_abono_n = prod_abono_n;
        }

        public String getProd_saldo_n() {
            return prod_saldo_n;
        }

        public void setProd_saldo_n(String prod_saldo_n) {
            this.prod_saldo_n = prod_saldo_n;
        }
    }

    public static class EnvFal
    {
        private String id_env;
        private String cant;

        public String getId_env() {
            return id_env;
        }

        public void setId_env(String id_env) {
            this.id_env = id_env;
        }

        public String getCant() {
            return cant;
        }

        public void setCant(String cant) {
            this.cant = cant;
        }
    }


    public static class EnvasesPreventa
    {
        private String prod_cve_n;
        private String prod_sku_str;
        private String prod_precio_n;
        private String lpre_precio_n;
        private String prod_cant_n;
        private String prod_cantabono_n;
        private String prod_saldo_n;
        private String prod_abono_n;
        private String prod_regalo_n;
        private String prod_venta_n;
        private String prod_cargo_n;
        private String prod_final_n;
        private String prod_subtotal_n;

        public EnvasesPreventa() {
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

        public String getLpre_precio_n() {
            return lpre_precio_n;
        }

        public void setLpre_precio_n(String lpre_precio_n) {
            this.lpre_precio_n = lpre_precio_n;
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

        public String getProd_saldo_n() {
            return prod_saldo_n;
        }

        public void setProd_saldo_n(String prod_saldo_n) {
            this.prod_saldo_n = prod_saldo_n;
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

        public String getProd_cargo_n() {
            return prod_cargo_n;
        }

        public void setProd_cargo_n(String prod_cargo_n) {
            this.prod_cargo_n = prod_cargo_n;
        }

        public String getProd_final_n() {
            return prod_final_n;
        }

        public void setProd_final_n(String prod_final_n) {
            this.prod_final_n = prod_final_n;
        }

        public String getProd_subtotal_n() {
            return prod_subtotal_n;
        }

        public void setProd_subtotal_n(String prod_subtotal_n) {
            this.prod_subtotal_n = prod_subtotal_n;
        }
    }

    public static class ProdAlm
    {
        private String prod_sku_str;
        private String prod_cant_n;

        public ProdAlm() {
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
    }
    
    public static class PrevEnv
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

        public PrevEnv() {
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

}
