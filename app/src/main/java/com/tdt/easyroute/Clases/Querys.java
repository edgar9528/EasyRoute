package com.tdt.easyroute.Clases;

public class Querys {

    

            public static void CrearBase(String nombrebase,String contrasena)
            {
                /*
                String bd = nombrebase;
                String pwd =contrasena ;
                if (File.Exists(bd))
                    return;
                SqlCeEngine motor = new SqlCeEngine();
                motor.LocalConnectionString = String.Format("Data Source={0};Password={1};Case Sensitive=false;", bd, pwd);

                motor.CreateDatabase();
                motor.Dispose();
                motor = null;
                */

            }

            public static class BitacoraHH
            {
                public static String CreaBitacoraHH = "Create Table BitacoraHH(" +
                        "bit_cve_n integer primary key autoincrement not null," +
                        "usu_cve_str nvarchar(50) not null," +
                        "rut_cve_n int null," +
                        "cli_cve_n bigint null," +
                        "bit_fecha_dt datetime null," +
                        "bit_operacion_str nvarchar(250) null," +
                        "bit_comentario_str nvarchar (250) null," +
                        "bit_coordenada_str nvarchar(250) null," +
                        "trans_est_n tinyint null default 0," +
                        "trans_fecha_dt datetime null)";

                public static String DelBitacoraHH = "delete from bitacoraHH";
            }

            public static class CargaInicialHH
            {
                public static String CreaCargaInicialHH = "Create Table CargaInicial(" +
                        "cini_cve_n integer Primary Key autoincrement not null," +
                        "cini_cargado_n bit not null," +
                        "cini_fecha_dt datetime not null," +
                        "usu_cve_str nvarchar(50) not null," +
                        "est_cve_str nvarchar(10) not null," +
                        "cini_comentario_str nvarchar(250) null)";
            }

            public static class Categorias
            {
                public static String CreaCategorias = "CREATE TABLE Categorias( " +
                        "cat_cve_n integer primary key autoincrement NOT NULL, " +
                        "cat_desc_str nvarchar(250) NOT NULL)";

                public static String SelCategorias = "select cat_cve_n,cat_desc_str from Categorias";
                public static String DelCategorias = "delete from Categorias";
                public static String InsCategorias = "insert into Categorias (cat_cve_n,cat_desc_str) " +
                        "values ({0},'{1}')";
            }

            public static class Clientes
            {
                public static String CreaClientes = "CREATE TABLE Clientes( " +
                        "cli_cve_n integer primary key autoincrement NOT NULL, " +
                        "cli_cveext_str nvarchar(50) NULL, " +
                        "cli_padre_n bit not null default 0," +
                        "cli_cvepadre_n bigint null," +
                        "cli_razonsocial_str nvarchar(250) NULL, " +
                        "cli_rfc_str nvarchar(15) NULL, " +
                        "cli_reqfact_n bit NULL  DEFAULT (0), " +
                        "cli_nombrenegocio_str nvarchar(100) NULL, " +
                        "cli_nom_str nvarchar(50) NULL, " +
                        "cli_app_str nvarchar(50) NULL, " +
                        "cli_apm_str nvarchar(50) NULL, " +
                        "cli_fnac_dt datetime NULL, " +
                        "cli_genero_str nvarchar(1) NULL, " +
                        "lpre_cve_n int NULL, " +
                        "nota_cve_n int not null default 0," +
                        "fpag_cve_n int NULL, " +
                        "cli_consigna_n bit NULL  DEFAULT (0), " +
                        "cli_especial_n bit null default 0, " +
                        "cli_credito_n bit NULL   DEFAULT (0),  " +
                        "cli_montocredito_n decimal(18, 2) NULL   DEFAULT (0), " +
                        "cli_plazocredito_n tinyint NULL   DEFAULT (0), " +
                        "cli_credenvases_n decimal(18, 2) NULL   DEFAULT (0), " +
                        "cli_estcredito_str nvarchar(10) NULL, " +
                        "cli_fba_n bit not null default 0, " +
                        "cli_porcentajefba_n decimal(18,4) default 0, " +
                        "rut_cve_n int NULL, " +
                        "nvc_cve_n int NULL, " +
                        "giro_cve_n int NULL, " +
                        "cli_email_str nvarchar(100) NULL, " +
                        "cli_dirfact_n int NULL, " +
                        "cli_dirent_n int NULL, " +
                        "cli_tel1_str nvarchar(15) NULL, " +
                        "cli_tel2_str nvarchar(15) NULL, " +
                        "emp_cve_n int NULL, " +
                        "cli_coordenadaini_str nvarchar(50) NULL, " +
                        "est_cve_str nvarchar(10) NULL   DEFAULT ('A'), " +
                        "tcli_cve_n int NULL, " +
                        "cli_lun_n int NULL   DEFAULT (0), " +
                        "cli_mar_n int NULL   DEFAULT (0), " +
                        "cli_mie_n int NULL   DEFAULT (0), " +
                        "cli_jue_n int NULL   DEFAULT (0), " +
                        "cli_vie_n int NULL   DEFAULT (0), " +
                        "cli_sab_n int NULL   DEFAULT (0), " +
                        "cli_dom_n int NULL   DEFAULT (0), " +
                        "frec_cve_n int NULL, " +
                        "cli_esvallejo_n bit null default 0, " +
                        "npro_cve_n tinyint null default 0, " +
                        "cli_eshuix_n bit null default 0, " +
                        "cli_huixdesc_n decimal(18,4) null default 0, " +
                        "cli_prospecto_n bit not null default 0, " +
                        "cli_invalidafrecuencia_n bit NULL   DEFAULT (0),  " +
                        "cli_invalidagps_n bit NULL   DEFAULT (0),  " +
                        "cli_dobleventa_n bit NULL   DEFAULT (0),  " +
                        "cli_comodato_n bit NULL DEFAULT (0), " +
                        "cli_imagen_n bit NULL DEFAULT (0), " +
                        "cli_invalidacb_n bit NULL DEFAULT (0), " +
                        "seg_cve_n int null, " +
                        "cli_dispersion_n bit not null default 0," +
                        "cli_dispersioncant_n tinyint not null default 0," +
                        "cli_limitemes_n int not null default 0)";

                public static String SelClientes = "select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_rfc_Str,cli_reqfact_n,cli_nombrenegocio_str, " +
                        "cli_nom_str,cli_app_str,cli_apm_str,cli_fnac_dt,cli_genero_str,lpre_cve_n,fpag_cve_n,cli_consigna_n,cli_especial_n,cli_credito_n, " +
                        "cli_montocredito_n,cli_plazocredito_n,cli_estcredito_str,cli_fba_n,cli_porcentaje_fba,rut_cve_n,nvc_cve_n,giro_cve_n,cli_email_str,cli_dirfact_n, " +
                        "cli_dirent_n,cli_Tel1_str,cli_tel2_str,emp_cve_n,cli_coordenadaini_str,est_cve_str,tcli_cve_n,cli_lun_n,cli_mar_n,cli_mie_n, " +
                        "cli_jue_n,cli_vie_n,cli_sab_n,cli_dom_n,frec_cve_n,cli_dispersion_n,cli_dispersioncant_n,cli_limitemes_n from clientes";

                public static String DelClientes = "delete from clientes";

                public static String DelClientes2 = "delete from clientes where trans_est_n=3";

                public static String InsClientes = "insert into clientes (cli_cve_n,cli_cveext_str,cli_razonsocial_str," +
                        "cli_rfc_Str,cli_reqfact_n,cli_nombrenegocio_str,cli_nom_str,cli_app_str,cli_apm_str,cli_fnac_dt," +
                        "cli_genero_str,lpre_cve_n,fpag_cve_n,cli_consigna_n,cli_credito_n,cli_montocredito_n,cli_plazocredito_n," +
                        "cli_estcredito_str,cli_fba_n,cli_porcentajefba_n,rut_cve_n,nvc_cve_n,giro_cve_n,cli_email_str,cli_dirfact_n,cli_dirent_n,cli_Tel1_str," +
                        "cli_tel2_str,emp_cve_n,est_cve_str,tcli_cve_n,cli_lun_n,cli_mar_n,cli_mie_n,cli_jue_n,cli_vie_n,cli_sab_n," +
                        "cli_dom_n,frec_cve_n,cli_especial_n,cli_esvallejo_n,npro_cve_n,cli_huixdesc_n,cli_eshuix_n,cli_prospecto_n) " +
                        "values ({0},'{1}','{2}','{3}',{4}," +
                        "'{5}','{6}','{7}','{8}',{9},'{10}',{11},{12},{13}," +
                        "{14},{15},{16},'{17}',{18},{19},{20},{21}," +
                        "{22},'{23}',{24},{25},'{26}','{27}',{28},'{29}',{30},{31}," +
                        "{32},{33},{34},{35},{36},{37},{38},{39},{40},{41},{42},{43},{44})";

                public static String InsClientes3 = "insert into clientes (cli_cve_n,cli_cveext_str," +
                        "cli_razonsocial_str,cli_rfc_str,cli_reqfact_n,cli_nombrenegocio_str,cli_nom_str," +
                        "cli_app_str,cli_apm_str,cli_fnac_dt,cli_genero_str,lpre_cve_n,fpag_cve_n,cli_consigna_n," +
                        "cli_credito_n,cli_montocredito_n,cli_plazocredito_n,cli_credenvases_n,cli_estcredito_str," +
                        "cli_fba_n,cli_porcentajefba_n,rut_cve_n,nvc_cve_n,giro_cve_n,cli_email_str,cli_dirfact_n," +
                        "cli_dirent_n,cli_Tel1_str,cli_tel2_str,emp_cve_n,cli_coordenadaini_str,est_cve_str,tcli_cve_n," +
                        "cli_lun_n,cli_mar_n,cli_mie_n,cli_jue_n,cli_vie_n,cli_sab_n,cli_dom_n,frec_cve_n," +
                        "cli_especial_n,cli_esvallejo_n,npro_cve_n,cli_huixdesc_n,cli_eshuix_n,cli_prospecto_n," +
                        "cli_invalidafrecuencia_n,cli_invalidagps_n,cli_dobleventa_n,cli_comodato_n,seg_cve_n) " +
                        "values ({0},{1},{2},{3},{4}," +
                        "{5},{6},{7},{8},{9},{10},{11},{12},{13}," +
                        "{14},{15},{16},{17},{18},{19},{20},{21}," +
                        "{22},{23},{24},{25},{26},{27},{28},{29},{30},{31}," +
                        "{32},{33},{34},{35},{36},{37},{38},{39},{40},{41},{42}," +
                        "{43},{44},{45},{46},{47},{48},{49},{50},{51})";

                public static String InsClientes4 = "insert into clientes (cli_cve_n,cli_cveext_str," +
                        "cli_padre_n,cli_cvepadre_n,cli_razonsocial_str,cli_rfc_str,cli_reqfact_n,cli_nombrenegocio_str," +//7
                        "cli_nom_str,cli_app_str,cli_apm_str,cli_fnac_dt,cli_genero_str,lpre_cve_n,nota_cve_n,fpag_cve_n," +//15
                        "cli_consigna_n,cli_credito_n,cli_montocredito_n,cli_plazocredito_n,cli_credenvases_n,cli_estcredito_str," +//21
                        "cli_fba_n,cli_porcentajefba_n,rut_cve_n,nvc_cve_n,giro_cve_n,cli_email_str,cli_dirfact_n," +//28
                        "cli_dirent_n,cli_Tel1_str,cli_tel2_str,emp_cve_n,cli_coordenadaini_str,est_cve_str,tcli_cve_n," +//35
                        "cli_lun_n,cli_mar_n,cli_mie_n,cli_jue_n,cli_vie_n,cli_sab_n,cli_dom_n,frec_cve_n," + //43
                        "cli_especial_n,cli_esvallejo_n,npro_cve_n,cli_huixdesc_n,cli_eshuix_n,cli_prospecto_n," +
                        "cli_invalidafrecuencia_n,cli_invalidagps_n,cli_dobleventa_n,cli_comodato_n,seg_cve_n," +//54
                        "cli_dispersion_n,cli_dispersioncant_n,cli_limitemes_n) " +
                        "values ({0},'{1}',{2},{3},'{4}','{5}',{6},'{7}','{8}','{9}'," +
                        "'{10}','{11}','{12}',{13},{14},{15},{16},{17},{18},{19}," +
                        "{20},'{21}',{22},{23},{24},{25},{26},'{27}',{28},{29}," +
                        "'{30}','{31}',{32},'{33}','{34}',{35},{36},{37},{38},{39}," +
                        "{40},{41},{42},{43},{44},{45},{46},{47},{48},{49}," +
                        "{50},{51},{52},{53},{54},{55},{56},{57})";

                public static String UpClientes = "update clientes set cli_cveext_str='{1}',cli_razonsocial_str='{2}'," +
                        "cli_rfc_Str='{3}',cli_reqfact_n={4},cli_nombrenegocio_str='{5}',cli_nom_str='{6}',cli_app_str='{7}'," +
                        "cli_apm_str='{8}',cli_fnac_dt={9},cli_genero_str='{10}',lpre_cve_n={11},fpag_cve_n={12}," +
                        "cli_consigna_n={13},cli_credito_n={14},cli_montocredito_n={15},cli_plazocredito_n={16}," +
                        "cli_estcredito_str='{17}',cli_fba_n={18},cli_porcentajefba_n={19},rut_cve_n={20},nvc_cve_n={21}," +
                        "giro_cve_n={22},cli_email_str='{23}',cli_dirfact_n={24},cli_dirent_n={25},cli_Tel1_str='{26}'," +
                        "cli_tel2_str='{27}',emp_cve_n={28},est_cve_str='{28}',tcli_cve_n={30},cli_lun_n={31}," +
                        "cli_mar_n={32},cli_mie_n={33}, cli_jue_n={34},cli_vie_n={35},cli_sab_n={36},cli_dom_n={37}," +
                        "frec_cve_n={38},cli_especial_n={39},cli_esvallejo_n={40},npro_cve_n={41},cli_huixdesc_n={42}," +
                        "cli_eshuix_n={43} where cli_cve_n={0}";

            /*public static String UpClientes2 = "update clientes set cli_cveext_str={1},cli_razonsocial_str={2}," +
            "cli_rfc_Str={3},cli_reqfact_n={4},cli_nombrenegocio_str={5},cli_nom_str={6},cli_app_str={7}," +
            "cli_apm_str={8},cli_fnac_dt={9},cli_genero_str={10},lpre_cve_n={11},fpag_cve_n={12}," +
            "cli_consigna_n={13},cli_credito_n={14},cli_montocredito_n={15},cli_plazocredito_n={16}," +
            "cli_estcredito_str={17},rut_cve_n={18},nvc_cve_n={19},giro_cve_n={20},cli_email_str={21}," +
            "cli_dirfact_n={22},cli_dirent_n={23},cli_Tel1_str={24},cli_tel2_str={25},emp_cve_n={26}," +
            "est_cve_str={27},tcli_cve_n={28},cli_lun_n={29},cli_mar_n={30},cli_mie_n={31}, " +
            "cli_jue_n={32},cli_vie_n={33},cli_sab_n={34},cli_dom_n={35},frec_cve_n={36},cli_especial_n={37}, " +
            "cli_esvallejo_n={38},npro_cve_n={39},cli_huixdesc_n={40},cli_eshuix_n={41},cli_prospecto_n={42} where cli_cve_n={0}";*/

                public static String UpClientes2 = "update clientes set cli_cveext_str={1},cli_razonsocial_str={2}," +
                        "cli_rfc_Str={3},cli_reqfact_n={4},cli_nombrenegocio_str={5},cli_nom_str={6},cli_app_str={7}," +
                        "cli_apm_str={8},cli_fnac_dt={9},cli_genero_str={10},lpre_cve_n={11},fpag_cve_n={12}," +
                        "cli_consigna_n={13},cli_credito_n={14},cli_montocredito_n={15},cli_plazocredito_n={16}," +
                        "cli_credenvases_n={17},cli_estcredito_str={18},cli_fba_n={19},cli_porcentajefba_n={20}," +
                        "rut_cve_n={21},nvc_cve_n={22},giro_cve_n={23},cli_email_str={24},cli_dirfact_n={25}," +
                        "cli_dirent_n={26},cli_Tel1_str={27},cli_tel2_str={28},emp_cve_n={29}," +
                        "cli_coordenadaini_str={30},est_cve_str={31},tcli_cve_n={32},cli_lun_n={33}," +
                        "cli_mar_n={34},cli_mie_n={35}, cli_jue_n={36},cli_vie_n={37},cli_sab_n={38},cli_dom_n={39}," +
                        "frec_cve_n={40},cli_especial_n={41},cli_esvallejo_n={42},npro_cve_n={43},cli_huixdesc_n={44}," +
                        "cli_eshuix_n={45},cli_prospecto_n={46},cli_invalidafrecuencia_n={47},cli_invalidagps_n={48}," +
                        "cli_dobleventa_n={49},cli_comodato_n={50},seg_cve_n={51} where cli_cve_n={0}";

                public static String UpClientes4 = "update clientes set cli_cveext_str={1},cli_padre_n={2}," +
                        "cli_cvepadre_n={3},cli_razonsocial_str={4},cli_rfc_str={5},cli_reqfact_n={6},cli_nombrenegocio_str={7}," +
                        "cli_nom_str={8},cli_app_str={9},cli_apm_str={10},cli_fnac_dt={11},cli_genero_str={12},lpre_cve_n={13}," +
                        "nota_cve_n={14},fpag_cve_n={15},cli_consigna_n={16},cli_credito_n={17},cli_montocredito_n={18}," +
                        "cli_plazocredito_n={19},cli_credenvases_n={20},cli_estcredito_str={21},cli_fba_n={22}," +
                        "cli_porcentajefba_n={23},rut_cve_n={24},nvc_cve_n={25},giro_cve_n={26},cli_email_str={27}," +
                        "cli_dirfact_n={28},cli_dirent_n={29},cli_Tel1_str={30},cli_tel2_str={31},emp_cve_n={32}," +
                        "cli_coordenadaini_str={33},est_cve_str={34},tcli_cve_n={35},cli_lun_n={36}," +
                        "cli_mar_n={37},cli_mie_n={38}, cli_jue_n={39},cli_vie_n={40},cli_sab_n={41},cli_dom_n={42}," +
                        "frec_cve_n={43},cli_especial_n={44},cli_esvallejo_n={45},npro_cve_n={46},cli_huixdesc_n={47}," +
                        "cli_eshuix_n={48},cli_prospecto_n={49},cli_invalidafrecuencia_n={50},cli_invalidagps_n={51}," +
                        "cli_dobleventa_n={52},cli_comodato_n={53},seg_cve_n={54},cli_dispersion_n={55}," +
                        "cli_dispersioncant_n={56},cli_limitemes_n={57} where cli_cve_n={0}";

            }

            public static class ClientesVentaMes
            {
                public static String CreaClientesVentaMes="Create table ClientesVentaMes(" +
                        "rut_cve_n int not null," +
                        "cli_cve_n bigint not null," +
                        "cvm_vtaacum_n int not null)";

                public static String DelClientesVentaMes = "delete from ClientesVentaMes";

                public static String InsClientesVentaMes = "insert into ClientesVentaMes("+
                        "rut_cve_n,cli_cve_n,cvm_vtaacum_n) values ({0},{1},{2})";

                public static String UpdateClientesVentaMes = "update ClientesVentaMes set "+
                        "cvm_vtaacum_n={2} where cli_cve_n={0} and rut_cve_n={1}";

                public static String SelClienteConVtaMes = "Select c.*,coalesce(v.cvm_vtaacum_n,0) cvm_vtaacum_n from clientes c " +
                        "left join clientesventames v on c.cli_cve_n=v.cli_cve_n where c.cli_cve_n={0}";

                public static String SelClienteConVtaMesNivel = "Select c.*,coalesce(v.cvm_vtaacum_n,0) cvm_vtaacum_n, " +
                        "nvc.nvc_nivel_n from clientes c inner join nivelcliente nvc on c.nvc_cve_n=nvc.nvc_cve_n " +
                        "left join clientesventames v on c.cli_cve_n=v.cli_cve_n where c.cli_cve_n={0}";

            }

            public static class ConfiguracionHH
            {
                public static String CreaConfiguracionHH = "Create Table ConfiguracionHH(" +
                        "conf_cve_n integer primary key autoincrement not null," +
                        "rut_cve_n int not null," +
                        "emp_cve_n int not null," +
                        "usu_cve_str nvarchar(50) null," +
                        "camion_str nvarchar(20) null," +
                        "km_inicial_n int null," +
                        "km_final_n int null," +
                        "conf_preventa_n tinyint not null default 0," +
                        "conf_fechainicio_dt datetime null," +
                        "est_cve_str nvarchar(10) null," +
                        "conf_fechafin_dt datetime null," +
                        "conf_descarga_dt datetime null," +
                        "conf_auditoria_n bit null default 0)";

                public static String DesactivarPedidos = "update ConfiguracionHH set conf_descarga_dt=getdate() where est_cve_str='A'";

                public static String InsertConfiguracion = "Insert into ConfiguracionHH(rut_cve_n,emp_cve_n,usu_cve_str," +
                        "camion_str,km_inicial_n,conf_fechainicio_dt,conf_preventa_n,est_cve_str) values (" +
                        "{0},{1},'{2}','{3}',{4},datetime('now','localtime'),{5},'A')";

                public static String ReactivarPedidos = "update configuracionHH set conf_descarga_dt=null where est_cve_str='A' ";

                public static String ActivarPreventa = "update configuracionHH set conf_preventa_n=1 where est_cve_str='A' and conf_fechafin_dt is null";

                public static String DesactivarPreventa = "update configuracionHH set conf_preventa_n=0 where est_cve_str='A' and conf_fechafin_dt is null";

                public static String ReactivarDia = "update configuracionHH set conf_fechafin_dt=null where est_cve_str='A' and conf_fechafin_dt is not null";

                public static String ActivarAuditoria = "update configuracionHH set conf_auditoria_n=1 where est_cve_str='A' and conf_fechafin_dt is null";

                public static String DesactivarAuditoria = "update configuracionHH set conf_auditoria_n=0 where est_cve_str='A' and conf_fechafin_dt is null";
            }

            public static class CondicionesVenta
            {
                public static String CreaCondicionesVenta = "Create table CondicionesVenta(" +
                        "cov_cve_n integer primary key autoincrement not null, " +
                        "cov_desc_str nvarchar(50) not null, " +
                        "cov_reStringido_n bit not null default 0, " +
                        "cov_familias_str nvarchar(50) null, " +
                        "est_cve_str nvarchar(10) not null default 'A')";

                public static String SelectCondicionesVenta = "Select cov_cve_n,cov_desc_str," +
                        "cov_reStringido_n,cov_familias_str,est_cve_str from CondicionesVenta";

                public static String InsCondicionesVenta = "Insert into CondicionesVenta( " +
                        "cov_cve_n,cov_desc_str,cov_reStringido_n,cov_familias_str,est_cve_str) "+
                        "values ({0},'{1}',{2},'{3}','{4}')";

                public static String DelCondicionesVenta = "delete from CondicionesVenta";

                public static String UpdateCondicionesVenta = "Update CondicionesVenta set " +
                        "cov_desc_str={1},cov_reStringido_n={2},cov_familias_str={3},est_cve_str={4} " +
                        "where cov_cve_n={0}";
            }

            public static class Consignas
            {
                public static String CreaConsignas="CREATE TABLE Consignas( " +
                        "csgn_cve_str nvarchar(20) NOT NULL, " +
                        "csgn_entrega_n tinyint not null, " +
                        "emp_cve_n int NOT NULL, " +
                        "alm_cve_n int NOT NULL DEFAULT 1, " +
                        "cli_cve_n bigint NULL, " +
                        "cli_cveext_str nvarchar(50) NULL, " +
                        "rut_cve_n int NOT NULL, " +
                        "cli_nom_str nvarchar(50) NOT NULL, " +
                        "cli_app_str nvarchar(50) NOT NULL, " +
                        "cli_apm_str nvarchar(50) NULL, " +
                        "cli_tel1_str nvarchar(15) NOT NULL, " +
                        "cli_tel2_str nvarchar(15) NULL, " +
                        "dir_calle_str nvarchar(100) NOT NULL, " +
                        "dir_noext_str nvarchar(20) NOT NULL, " +
                        "dir_noint_str nvarchar(20) NULL, " +
                        "dir_entrecalle1_str nvarchar(100) NULL, " +
                        "dir_entrecalle2_str nvarchar(100) NULL, " +
                        "dir_colonia_str nvarchar(100) NOT NULL, " +
                        "dir_municipio_str nvarchar(100) NOT NULL, " +
                        "dir_estado_str nvarchar(100) NOT NULL, " +
                        "dir_pais_str nvarchar(100) NOT NULL, " +
                        "dir_codigopostal_str nvarchar(10) NOT NULL, " +
                        "dir_referencia_str nvarchar(100) NULL, " +
                        "dir_encargado_str nvarchar(100) NULL, " +
                        "csgn_coordenada_str nvarchar(50) NULL, " +
                        "usu_solicita_str nvarchar(50) NOT NULL, " +
                        "csgn_fsolicitud_dt datetime NOT NULL, " +
                        "est_cve_str nvarchar(10) NOT NULL DEFAULT 'S', " +
                        "csgn_finicio_dt datetime NOT NULL, " +
                        "csgn_ffin_dt datetime NOT NULL, " +
                        "csgn_fcobro_dt datetime NOT NULL, " +
                        "csgn_fprorroga_dt datetime NOT NULL, " +
                        "usu_autoriza_str nvarchar(50) NULL, " +
                        "csgn_fautorizacion_dt datetime NULL, " +
                        "csgn_identificacion_str nvarchar(500) NULL, " +
                        "csgn_compdom_str nvarchar(500) NULL, " +
                        "csgn_pagare_str nvarchar(500) NULL, " +
                        "usu_modifica_str nvarchar(50) NULL, " +
                        "csgn_fmodificacion_dt datetime NULL, " +
                        "csgn_montopagare_n decimal(18, 4) NULL, " +
                        "csgn_observaciones_str nvarchar(1000) NULL, " +
                        "CONSTRAINT PK_Consignas PRIMARY KEY (csgn_cve_str,csgn_entrega_n))";

                public static String InsertConsignas = "INSERT INTO Consignas (csgn_cve_str,csgn_entrega_n, " + //1
                        "emp_cve_n,alm_cve_n,cli_cve_n,cli_cveext_str,rut_cve_n " + //6
                        ",cli_nom_str,cli_app_str,cli_apm_str,cli_tel1_str,cli_tel2_str,dir_calle_str,dir_noext_str " +//13
                        ",dir_noint_str,dir_entrecalle1_str,dir_entrecalle2_str,dir_colonia_str,dir_municipio_str " +//18
                        ",dir_estado_str,dir_pais_str,dir_codigopostal_str,dir_referencia_str,dir_encargado_str " +//23
                        ",csgn_coordenada_str,usu_solicita_str,csgn_fsolicitud_dt,est_cve_str,csgn_finicio_dt " +//28
                        ",csgn_ffin_dt,csgn_fcobro_dt,csgn_fprorroga_dt,usu_autoriza_str,csgn_fautorizacion_dt " +//33
                        ",csgn_identificacion_str,csgn_compdom_str,csgn_pagare_str,usu_modifica_str " + //37
                        ",csgn_fmodificacion_dt,csgn_montopagare_n,csgn_observaciones_str) " + //40
                        "VALUES ('{0}',{1},{2},{3},{4},'{5}',{6},'{7}','{8}','{9}', " +
                        "'{10}','{11}','{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}', " +
                        "'{20}','{21}','{22}','{23}','{24}','{25}','{26}','{27}','{28}','{29}', " +
                        "'{30}','{31}','{32}','{33}','{34}','{35}','{36}','{37}','{38}',{39},'{40}')";

                public static String DeleteConsignas = "Delete from consignas";

                public static String CreaConsignasEntregaDet = "CREATE TABLE ConsignasEntregaDet( " +
                        "csgn_cve_str nvarchar(20) NOT NULL, " +
                        "csgn_entrega_n tinyint NOT NULL, " +
                        "prod_cve_n bigint NOT NULL, " +
                        "prod_sku_str nvarchar(50) NOT NULL, " +
                        "prod_cant_n decimal(18, 4) NOT NULL  DEFAULT 0, " +
                        "prod_vendido_n decimal(18, 4) NOT NULL  DEFAULT 0, " +
                        "prod_devuelto_n decimal(18, 4) NOT NULL  DEFAULT 0, " +
                        "prod_danado_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_pagado_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "CONSTRAINT PK_ConsignasEntregaDet PRIMARY KEY (csgn_cve_str,csgn_entrega_n,prod_cve_n)) ";

                public static String InsertConsignasDet = "INSERT INTO ConsignasEntregaDet (" +
                        "csgn_cve_str,csgn_entrega_n,prod_cve_n,prod_sku_str,prod_cant_n,prod_vendido_n," +
                        "prod_devuelto_n,prod_danado_n,prod_pagado_n) " +
                        "VALUES ('{0}',{1},{2},'{3}',{4},{5},{6},{7},{8})";

                public static String DeleteConsignasDet = "delete from consignasentregadet";
            }

            public static class Creditos
            {
                public static String CreaCreditos = "Create Table Creditos(" +
                        "cred_cve_n integer primary key autoincrement not null," +
                        "cred_referencia_str nvarchar(250) not null," +
                        "cli_cve_n bigint null default 0," +
                        "rut_cve_n int null default 0," +
                        "usu_cve_str nvarchar(50) null," +
                        "cred_fecha_dt datetime not null," +
                        "cred_descripcion_str nvarchar(250) null," +
                        "cred_vencimiento_dt datetime not null," +
                        "cred_monto_n decimal(18,4) not null," +
                        "cred_abono_n decimal(18,4) not null," +
                        "cred_engestoria_n bit not null default 0," +
                        "cred_esenvase_n bit not null default 0," +
                        "cred_especial_n bit not null default 0," +
                        "prod_cve_n bigint null," +
                        "prod_sku_str nvarchar(50) null," +
                        "prod_precio_n decimal(18,4) null," +
                        "prod_cant_n decimal(18,4) null," +
                        "prod_cantabono_n decimal(18,4) null," +
                        "trans_est_n tinyint null  default 0," +
                        "trans_fecha_dt datetime null)";

                public static String DeleteCreditos = "delete from creditos";
            }

            public static class Direcciones
            {
                public static String CreaDirecciones = "CREATE TABLE Direcciones( " +
                        "dir_cve_n bigint NOT NULL, " +
                        "cli_cve_n bigint NOT NULL, " +
                        "dir_alias_str nvarchar(50) NOT NULL, " +
                        "dir_calle_str nvarchar(100) NULL, " +
                        "dir_noext_str nvarchar(20) NULL, " +
                        "dir_noint_str nvarchar(20) NULL, " +
                        "dir_entrecalle1_str nvarchar(100) NULL, " +
                        "dir_entrecalle2_str nvarchar(100) NULL, " +
                        "dir_colonia_str nvarchar(100) NULL, " +
                        "dir_municipio_str nvarchar(100) NULL, " +
                        "dir_estado_str nvarchar(100) NULL, " +
                        "dir_pais_str nvarchar(100) NULL, " +
                        "dir_codigopostal_str nvarchar(10) NOT NULL, " +
                        "dir_referencia_str nvarchar(250) NULL, " +
                        "est_cve_str nvarchar(10) NOT NULL  DEFAULT ('A'), " +
                        "usu_cve_str nvarchar(50) NULL, " +
                        "dir_falta_dt datetime NULL DEFAULT (getdate()), " +
                        "dir_tel1_str nvarchar(15) NULL, " +
                        "dir_tel2_str nvarchar(15) NULL, " +
                        "dir_encargado_str nvarchar(100) NULL, " +
                        "dir_coordenada_str nvarchar(50) NULL, " +
                        "CONSTRAINT PK_Direcciones PRIMARY KEY (dir_cve_n ,cli_cve_n )) ";

                public static String DelDirecciones = "delete from Direcciones ";

                public static String SelDirecciones = "Select * from Direcciones";

                public static String InsDirecciones = "Insert into Direcciones(dir_cve_n,cli_cve_n," +
                        "dir_alias_str,dir_calle_str,dir_noext_str,dir_noint_str,dir_entrecalle1_str," +
                        "dir_entrecalle2_str,dir_colonia_str,dir_municipio_str,dir_estado_str,dir_pais_str," +
                        "dir_codigopostal_str,dir_referencia_str,est_cve_str,usu_cve_str,dir_falta_dt," +
                        "dir_tel1_str,dir_tel2_str,dir_encargado_str) values ({0},{1}," +
                        "'{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}'," +
                        "'{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}')";

                public static String InsDirecciones2 = "Insert into Direcciones(dir_cve_n,cli_cve_n," +
                        "dir_alias_str,dir_calle_str,dir_noext_str,dir_noint_str,dir_entrecalle1_str," +
                        "dir_entrecalle2_str,dir_colonia_str,dir_municipio_str,dir_estado_str,dir_pais_str," +//11
                        "dir_codigopostal_str,dir_referencia_str,est_cve_str,usu_cve_str,dir_falta_dt," +//16
                        "dir_tel1_str,dir_tel2_str,dir_encargado_str,dir_coordenada_str) values ({0},{1}," +
                        "'{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}'," +
                        "'{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}','{20}')";

            }

            public static class Empresas
            {
                public static String CreaEmpresas = "CREATE TABLE Empresas( " +
                        "emp_cve_n integer primary key autoincrement NOT NULL, " +
                        "emp_nom_str nvarchar(250) NOT NULL, " +
                        "emp_contacto_str nvarchar(250)  NULL, " +
                        "emp_rfc_str nvarchar(20)  NULL, " +
                        "emp_colonia_str nvarchar(250) NULL, " +
                        "emp_municipio_str nvarchar(250) NULL, " +
                        "emp_telefono1_str nvarchar(15) NULL, " +
                        "est_cve_str nvarchar(10) NULL)";

                public static String SelEmpresas = "select emp_cve_n,emp_nom_str,emp_contacto_str,emp_rfc_str,emp_colonia_str,emp_municipio_str,emp_telefono1_str,est_cve_str from Empresas";
                public static String DelEmpresas = "delete from Empresas";
                public static String InsEmpresas = "insert into Empresas (emp_cve_n,emp_nom_str,emp_contacto_str,emp_rfc_str,emp_colonia_str,emp_municipio_str,emp_telefono1_str,est_cve_str) " +
                        "values ({0},'{1}','{2}','{3}','{4}','{5}','{6}','{7}')";
            }

            public static class Estatus
            {
                public static String CreaEstatus = "CREATE TABLE Estatus( " +
                        "est_cve_str nvarchar(10) NOT NULL, " +
                        "est_desc_str nvarchar(250) NOT NULL, " +
                        "est_usu_n bit NOT NULL DEFAULT (0), " +
                        "est_ped_n bit NOT NULL DEFAULT (0), " +
                        "est_prod_n bit NOT NULL DEFAULT (0), " +
                        "est_prov_n bit NOT NULL DEFAULT (0), " +
                        "CONSTRAINT PK_Estatus_1 PRIMARY KEY (	est_cve_str ))";

                public static String SelEstatus = "select est_cve_str,est_desc_str,est_usu_n,est_ped_n,est_prod_n,est_prov_n from estatus";
                public static String DelEstatus = "delete from estatus";
                public static String InsEstatus = "insert into estatus (est_cve_str,est_desc_str,est_usu_n,est_ped_n,est_prod_n,est_prov_n) " +
                        "values ('{0}','{1}',{2},{3},{4},{5})";
            }

            public static class Familias
            {
                public static String CreaFamilias = "CREATE TABLE Familias(" +
                        "fam_cve_n integer primary key autoincrement NOT NULL," +
                        "fam_desc_str nvarchar(250) null," +
                        "fam_orden_n int null)";

                public static String SelFamilias = "select fam_cve_n,fam_desc_str,fam_orden_n from Familias";
                public static String DelFamilias = "delete from Familias";
                public static String InsFamilias = "insert into Familias (fam_cve_n,fam_desc_str,fam_orden_n) " +
                        "values ({0},'{1}','{2}')";
            }

            public static class FormasPago
            {
                public static String CreaFormasPago = "CREATE TABLE FormasPago( " +
                        "fpag_cve_n integer primary key autoincrement NOT NULL, " +
                        "fpag_desc_str nvarchar(20) NOT NULL, " +
                        "fpag_reqbanco_n bit NULL, " +
                        "fpag_reqreferencia_n bit NULL, " +
                        "est_cve_str nvarchar(10) NULL)";

                public static String SelFormasPago = "select fpag_cve_n,fpag_desc_str,fpag_reqbanco_n,fpag_reqreferencia_n,est_cve_str from FormasPago";
                public static String DelFormasPago = "delete from FormasPago";
                public static String InsFormasPago = "insert into FormasPago (fpag_cve_n,fpag_desc_str,fpag_reqbanco_n,fpag_reqreferencia_n,est_cve_str ) " +
                        "values ({0},'{1}',{2},{3},'{4}')";
            }

            public static class FrecuenciaPunteo
            {
                public static String FrecPunteo = "CREATE TABLE FrecPunteo(" +
                        "cli_cveext_str nvarchar(50)  NOT NULL," +
                        "sec int null default 0," +
                        "frec_cve_n int null default 0," +
                        "est_cve_n nvarchar(250) null default 'A'," +
                        "coor nvarchar(50) null," +
                        "diasem nvarchar(50) null )";

                public static String DelFrecPun = "delete from FrecPunteo";
            }

            public static class FrecuenciasVisita
            {
                public static String CreaFrecuenciasVisita = "CREATE TABLE FrecuenciasVisita( " +
                        "frec_cve_n integer primary key autoincrement NOT NULL, " +
                        "frec_desc_str nvarchar(250) NULL, " +
                        "frec_dias_n int NULL, " +
                        "est_cve_str nvarchar(10) NULL)";

                public static String SelFrecuenciasVisita = "select frec_cve_n,frec_desc_str,frec_dias_n,est_cve_str from FrecuenciasVisita";
                public static String DelFrecuenciasVisita = "delete from FrecuenciasVisita";
                public static String InsFrecuenciasVisita = "insert into FrecuenciasVisita (frec_cve_n,frec_desc_str,frec_dias_n,est_cve_str) " +
                        "values ({0},'{1}',{2},'{3}')";
            }

            public static class Inventario
            {
                public static String CreaInventario = "Create Table Inventario(" +
                        "rut_cve_n bigint not null," +
                        "prod_cve_n bigint not null," +
                        "inv_inicial_n decimal(18,4) not null default 0," +
                        "inv_vendido_n decimal(18,4) not null default 0," +
                        "inv_devuelto_n decimal(18,4) not null default 0," +
                        "inv_danado_n decimal(18,4) not null default 0," +
                        "inv_recarga_n decimal(18,4) not null default 0," +
                        "inv_recuperado_n decimal(18,4) not null default 0," +
                        "inv_prestado_n decimal(18,4) not null default 0," +
                        "inv_cancelado_n decimal(18,4) not null default 0," +
                        "constraint PK_Inventario Primary Key (rut_cve_n,prod_cve_n))";

                // Funcion utilizada en FrmCarga, este frm ya no se utiliza
                public static String InsertInventario = "insert into inventario(rut_cve_n,prod_cve_n,prod_cant_n) " +
                        "values ({0},{1},{2})";

                // Funcion utilizada en FrmCarga, este frm ya no se utiliza
                public static String UpdateInventario="update inventario set prod_cant_n={2} " +
                        "where rut_cve_n={0} and prod_cve_n={1}";

                public static String InsertInventario2enCero = "insert into inventario(rut_cve_n,prod_cve_n) " +
                        "values ({0},{1})";

                public static String UpdateInventario2 = "update inventario set inv_inicial_n={2},inv_vendido_n={2}," +
                        "inv_devuelto_n={2},inv_danado_n={2},inv_recarga_n={2} " +
                        "where rut_cve_n={0} and prod_cve_n={1}";

                public static String InsertMovimiento = "insert into MovimientosInv(rut_cve_n,prod_cve_n," +
                        "cli_cve_n,usu_cve_str,prod_cantant_n,prod_vendido_n,prod_devuelto_n,prod_cantnva_n," +
                        "movi_desc_str,movi_fecha_dt) values ({0},{1},{2},'{3}',{4},{5},{6},{7},'{8}',getdate())";

                public static String InsertCarga = "insert into cargainicial(cini_cargado_n,cini_fecha_dt," +
                        "usu_cve_str,est_cve_str,cini_comentario_str) values ({0},getdate(),'{1}','{2}','{3}')";

                public static String DesactivaCarga = "update cargainicial set est_cve_str='I'";

                public static String ActualizaInv = "update inventario set prod_cant_n=prod_cant_n{2}{3} where rut_cve_n={0} and prod_cve_n={1}";

                public static String ActualizaInvVen = "update inventario set inv_vendido_n=inv_vendido_n{2}{3} where rut_cve_n={0} and prod_cve_n={1}";

                public static String ActualizaInvDev = "update inventario set inv_devuelto_n=inv_devuelto_n{2}{3} where rut_cve_n={0} and prod_cve_n={1}";

                public static String ActualizaCancelado = "update inventario set inv_cancelado_n=inv_cancelado_n+({2}) where rut_cve_n={0} and prod_cve_n={1}";

                public static String ActualizaRecuperado = "update inventario set inv_recuperado_n=inv_recuperado_n+({2}) where rut_cve_n={0} and prod_cve_n={1}";

                public static String ActualizaPrestado = "update inventario set inv_prestado_n=inv_prestado_n+({2}) where rut_cve_n={0} and prod_cve_n={1}";

                public static String ConsultaInventario = "select iv.rut_cve_n,iv.prod_cve_n," +
                        "(iv.inv_inicial_n+iv.inv_recarga_n+iv.inv_recuperado_n-iv.inv_prestado_n" +
                        "-iv.inv_devuelto_n-iv.inv_vendido_n) prod_cant_n,iv.inv_devuelto_n, " +
                        "iv.inv_cancelado_n,iv.inv_prestado_n,iv.inv_recuperado_n,f.fam_orden_n," +
                        "p.prod_orden_n from inventario iv " +
                        "inner join productos p on iv.prod_cve_n=p.prod_cve_n " +
                        "inner join familias f on p.fam_cve_n=f.fam_cve_n " +
                        "where iv.rut_cve_n={0} order by f.fam_orden_n,p.prod_orden_n";

                public static String LayoutInventario = "select p.prod_cve_n,p.prod_sku_str," +
                        "p.prod_desc_str,0 prod_cant_n,0 prod_sug_n,c.cat_desc_str,id_envase_n,0 prod_devuelto_n," +
                        "0 prod_cancelado_n,0 prod_recuperado_n,0 prod_prestado_n,p.prod_vtamismodia_n, " +
                        "f.fam_orden_n,p.prod_orden_n,pp.lpre_precio_n from productos p " +
                        " inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                        "inner join familias f on p.fam_cve_n=f.fam_cve_n " +
                        "inner join (select * from precioproductos where lpre_cve_n=11) pp " +
                        "on p.prod_cve_n=pp.prod_cve_n order by f.fam_orden_n,p.prod_orden_n";

                public static String DelInventario = "delete from inventario";
            }

            public static class Liquidacion
            {
                public static String ImporteVentas = "select sum(prod_subtotal_n) total from ventasdet where ven_cve_n in " +
                        "(select ven_cve_n from ventas where rut_cve_n={0})";

                public static String VentasCredito= "Select sum(cred_monto_n)  from Creditos where " +
                        "cred_descripcion_str like 'VENTA%' and cred_referencia_str not like '%-%'";

                public static String VentasCredito2 = "select sum(cred_monto_n) from Creditos " +
                        "where cred_esenvase_n=0 and trans_est_n<>3";

                public static String ImportePagoVentas= "select  p.fpag_cve_n,fp.fpag_desc_str,sum(p.pag_abono_n) total " +
                        " from pagos p  inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                        " where p.pag_envase_n=0 and p.pag_referencia_str in (select ven_folio_str " +
                        "   from ventas where rut_cve_n={0}) group by p.fpag_cve_n,fp.fpag_desc_str";

                public static String ImportePagoCreditosLiquido = "select  p.fpag_cve_n,fp.fpag_desc_str,sum(p.pag_abono_n) " +
                        " from pagos p  inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                        " where p.pag_envase_n=0 and p.pag_referencia_str not in (select ven_folio_str " +
                        "  from ventas where rut_cve_n={0}) group by p.fpag_cve_n,fp.fpag_desc_str";

                public static String Cobranza = "Select p.rut_cve_n,sum(pag_abono_n) from Pagos p inner join clientes c " +
                        "on p.cli_cve_n=c.cli_cve_n where pag_cobranza_n=1 and pag_envase_n=0 group by p.rut_cve_n";

                public static String EnvasesDevueltos = "select p.prod_cve_n,pr.prod_sku_str, " +
                        "pr.prod_desc_str,sum(p.prod_abono_n) abono from pagos p " +
                        "inner join productos pr on p.prod_cve_n=pr.prod_cve_n" +
                        "where p.pag_envase_n=1 group by p.prod_cve_n,pr.prod_sku_str," +
                        "pr.prod_desc_str";

                public static String SaldoEnvaseLleno = "select p.id_envase_n,en.prod_sku_str, " +
                        "en.prod_desc_str,sum(iv.prod_cant_n) cantidad from inventario iv " +
                        "inner join productos p on iv.prod_cve_n=p.prod_cve_n " +
                        "inner join productos en on p.id_envase_n=en.prod_cve_n " +
                        "where p.cat_cve_n not in (select cat_cve_n " +
                        "from categorias  where cat_desc_str='ENVASE') " +
                        "and p.prod_reqenv_n=1 group by  " +
                        "p.id_envase_n,en.prod_sku_str,en.prod_desc_str";

                public static String SaldoTotalEnvase = "select iv.prod_cve_n,p.prod_sku_str,p.prod_desc_str," +
                        "prod_cant_n from inventario iv " +
                        "inner join productos p on iv.prod_cve_n=p.prod_cve_n " +
                        "where p.cat_cve_n in (select cat_cve_n from " +
                        "categorias where cat_desc_str='ENVASE')";

                public static String SaldoPorVentaSinEnvase ="select v.ven_folio_str,v.cli_cve_n,c.cli_cveext_str," +
                        "sum(vd.prod_subtotal_n ) total from ventas v " +
                        "inner join ventasdet vd on v.ven_folio_str=vd.ven_folio_str " +
                        "inner join clientes c on v.cli_cve_n=c.cli_cve_n " +
                        "where vd.prod_envase_n=0 and v.ven_est_str<>'C'  " +
                        "group by  v.ven_folio_str,v.cli_cve_n,c.cli_cveext_str";

                public static String VentasContado = "Select p.rut_cve_n,sum(pag_abono_n) from Pagos p inner join clientes c "+
                        "on p.cli_cve_n=c.cli_cve_n where pag_cobranza_n=0 and pag_envase_n=0 group by p.rut_cve_n";

                public static String PreventasTotal = "select sum(d.prod_subtotal_n) prod_subtotal_n " +
                        "from Preventa p inner join PreventaDet d on p.prev_folio_str=d.prev_folio_str " +
                        "where d.prod_envase_n=0 group by p.rut_cve_n";

                public static String PreventasContado = "select sum(pp.fpag_cant_n) fpag_cant_n " +
                        "from Preventa p inner join PreventaPagos pp on p.prev_folio_str=pp.prev_folio_str  " +
                        "where pp.ppag_cobranza_n=0 group by p.rut_cve_n";

                public static String PreventasCobranza = "select sum(pp.fpag_cant_n) fpag_cant_n " +
                        "from Preventa p inner join PreventaPagos pp on p.prev_folio_str=pp.prev_folio_str  " +
                        "where pp.ppag_cobranza_n=1 group by p.rut_cve_n";
            }

            public static class ListaPrecio
            {
                public static String CreaListaPrecios = "CREATE TABLE ListaPrecios( " +
                        "lpre_cve_n integer primary key autoincrement NOT NULL," +
                        "lpre_desc_str nvarchar(250) NULL," +
                        "est_cve_str nvarchar(10) NULL," +
                        "usu_cve_str nvarchar(50) null," +
                        "lpre_falta_dt datetime null," +
                        "lpre_esbase_n bit not null default 0," +
                        "lpre_base_n int not null default 0," +
                        "lpre_modificiacion_dt datetime null," +
                        "usu_modificiacion_str nvarchar(50) null," +
                        "lpre_nota_n bit not null default 0)";

                public static String SelListaPrecios = "select lpre_cve_n,lpre_desc_str,est_cve_str,lpre_nota_n from ListaPrecios";
                public static String DelListaPrecios = "delete from ListaPrecios";
                public static String InsListaPrecios = "insert into ListaPrecios (lpre_cve_n,lpre_desc_str,est_cve_str," +
                        "lpre_esbase_n,lpre_base_n,lpre_nota_n) values ({0},'{1}','{2}',{3},{4},{5})";

                public static String ListaPreciosPreLpreNota2 = "select s1.lpre_cve_n,s1.lpre_desc_str,s1.prod_cve_n,p.fam_cve_n," +
                        "f.fam_desc_str,p.pres_cve_n,pr.pres_desc_str,s1.prod_sku_str,p.prod_desc_str,cat.cat_desc_str," +
                        "p.id_envase_n,0 prod_cant_n, coalesce(pp2.lpre_precio_n,0.00) lpre_base_n," +
                        "coalesce(pp.lpre_precio_n,0.00)lpre_cliente_n,0.0 lpre_promo_n, " +
                        "coalesce(pp.lpre_precio_n,0.00) lpre_precio_n,coalesce(pp.lpre_precio_n,0.00) lpre_precio2_n, " +
                        "case when pp2.lpre_precio_n is null " +
                        "then 0 else (1-(coalesce(pp.lpre_precio_n,0.00)/coalesce(pp2.lpre_precio_n,0.00))) end lpre_desc_n," +
                        "cast(0 as bit) prod_promo_n,0 prom_cve_n,0 prom_contado_n,p.prod_vtaefectivo_n," +
                        "coalesce(np.lpre_precio_n,0) lpre_nota_n, coalesce(np.lpre_volumen_n,0) lpre_volumen_n, " +
                        "p.prod_vtavolumen_n,p.cov_cve_n,cv.cov_reStringido_n,cv.cov_familias_str from " +
                        "(select lp.lpre_cve_n,lpre_desc_str,prod_cve_n,prod_sku_str,prod_desc_str,prod_vtavolumen_n, " +
                        "case when lpre_base_n = 0 then lpre_cve_n else lpre_base_n end lpre_base_n from listaprecios lp, " +
                        "productos pr) s1 left join precioproductos pp on s1.lpre_cve_n=pp.lpre_cve_n and s1.prod_cve_n=pp.prod_cve_n " +
                        "inner join productos p on s1.prod_cve_n=p.prod_cve_n " +
                        "inner join presentaciones pr on p.pres_cve_n=pr.pres_cve_n " +
                        "inner join familias f on p.fam_cve_n=f.fam_cve_n " +
                        "inner join categorias cat on p.cat_cve_n=cat.cat_cve_n " +
                        "inner join condicionesventa cv on p.cov_cve_n=cv.cov_cve_n " +
                        "left join precioproductos pp2 on s1.lpre_base_n=pp2.lpre_cve_n and s1.prod_cve_n=pp2.prod_cve_n " +
                        "left join (select * from precioproductos where lpre_cve_n={1}) np on s1.prod_cve_n=np.prod_cve_n " +
                        "where s1.lpre_cve_n={0} and p.prod_est_str='A' and p.cat_cve_n not in ({2}) and " +
                        "pp.lpre_precio_n is not null order by f.fam_orden_n,p.prod_orden_n";

                public static String ListaPreciosRepLpreNota2 = "select s1.lpre_cve_n,s1.lpre_desc_str,s1.prod_cve_n," +
                        "p.fam_cve_n,f.fam_desc_str,p.pres_cve_n,pr.pres_desc_str,s1.prod_sku_str,p.prod_desc_str,cat.cat_desc_str," +
                        "p.id_envase_n,p.prod_manejainv_n,(iv.inv_inicial_n+iv.inv_recarga_n+iv.inv_recuperado_n-" +
                        "iv.inv_devuelto_n-iv.inv_vendido_n-iv.inv_prestado_n) prod_cantiv_n,0 prod_cant_n, " +
                        "coalesce(pp2.lpre_precio_n,0.00) lpre_base_n,coalesce(pp.lpre_precio_n,0.00)lpre_cliente_n," +
                        "0.0 lpre_promo_n, coalesce(pp.lpre_precio_n,0.00) lpre_precio_n, " +
                        "coalesce(pp.lpre_precio_n,0.00) lpre_precio2_n,case when pp2.lpre_precio_n is null " +
                        "then 0 else (1-(coalesce(pp.lpre_precio_n,0.00)/coalesce(pp2.lpre_precio_n,0.00))) end lpre_desc_n," +
                        "cast(0 as bit) prod_promo_n,0 prom_cve_n,0 prom_contado_n,p.prod_vtaefectivo_n," +
                        "coalesce(np.lpre_precio_n,0) lpre_nota_n, coalesce(np.lpre_volumen_n,0) lpre_volumen_n, " +
                        "p.prod_vtavolumen_n,p.cov_cve_n,cv.cov_reStringido_n,cv.cov_familias_str from " +
                        "(select lp.lpre_cve_n,lpre_desc_str,prod_cve_n,prod_sku_str,prod_desc_str,prod_vtavolumen_n, " +
                        "case when lpre_base_n = 0 then lpre_cve_n else lpre_base_n end lpre_base_n from listaprecios lp, " +
                        "productos pr) s1 left join precioproductos pp on s1.lpre_cve_n=pp.lpre_cve_n and s1.prod_cve_n=pp.prod_cve_n " +
                        "inner join productos p on s1.prod_cve_n=p.prod_cve_n " +
                        "inner join presentaciones pr on p.pres_cve_n=pr.pres_cve_n " +
                        "inner join familias f on p.fam_cve_n=f.fam_cve_n " +
                        "inner join inventario iv on p.prod_cve_n=iv.prod_cve_n " +
                        "inner join categorias cat on p.cat_cve_n=cat.cat_cve_n " +
                        "inner join condicionesventa cv on p.cov_cve_n=cv.cov_cve_n " +
                        "left join precioproductos pp2 on s1.lpre_base_n=pp2.lpre_cve_n and s1.prod_cve_n=pp2.prod_cve_n " +
                        "left join (select * from precioproductos where lpre_cve_n={1}) np on s1.prod_cve_n=np.prod_cve_n " +
                        "where s1.lpre_cve_n={0} and p.prod_est_str='A' and p.cat_cve_n not in ({2}) and " +
                        "(iv.inv_inicial_n+iv.inv_recarga_n+iv.inv_recuperado_n-iv.inv_devuelto_n-iv.inv_vendido_n-iv.inv_prestado_n)>0 " +
                        "and iv.rut_cve_n={3} and pp.lpre_precio_n is not null order by f.fam_orden_n,p.prod_orden_n";

            }

            public static class Login
            {
                public static String SeleccionarUsuario = "Select * from usuarios where usu_cve_str='{0}' and usu_pwd_str='{1}'";
                public static String SeleccionarUsuarioConRol = "Select u.*,r.rol_desc_str,r.rol_esadmin_n from usuarios u inner join roles r " +
                        "on u.rol_cve_n=r.rol_cve_n where upper(u.usu_cve_str)='{0}' and u.usu_pwd_str='{1}'";

                public static String PermisosEfectivos = "SELECT m.mod_cve_n, m.mod_desc_str, ISNULL(rm.rm_lectura_n, 0) AS lectura, " +
                        "ISNULL(rm.rm_escritura_n, 0) AS escritura, ISNULL(rm.rm_modificacion_n, 0) AS modificacion, ISNULL(rm.rm_eliminacion_n, 0) " +
                        " AS eliminacion FROM     dbo.Modulos AS m LEFT OUTER JOIN dbo.RolesModulos AS rm ON m.mod_cve_n = rm.mod_cve_n where rm.rol_cve_n={0} or rm.rol_cve_n is null";

                public static String PermisosEfectivos2 = "SELECT m.mod_cve_n, m.mod_desc_str, ISNULL(rm.rm_lectura_n, 0) AS lectura, " +
                        "ISNULL(rm.rm_escritura_n, 0) AS escritura, ISNULL(rm.rm_modificacion_n, 0) AS modificacion, " +
                        "ISNULL(rm.rm_eliminacion_n, 0)  AS eliminacion FROM  dbo.Modulos AS m left OUTER JOIN dbo.RolesModulos AS rm " +
                        "ON m.mod_cve_n = rm.mod_cve_n and (rm.rol_Cve_n is null or rm.rol_cve_n={0}) where (rm.rol_Cve_n is null or rm.rol_cve_n={0})";

                public static String PermisosEfectivos3 = "select s1.mod_cve_n,s1.mod_desc_str,rm.rm_lectura_n as lectura, " +
                        "rm.rm_escritura_n as escritura,rm.rm_modificacion_n as modificacion, " +
                        "rm.rm_eliminacion_n as eliminacion from (select m.mod_cve_n,m.mod_desc_str," +
                        "r.rol_Cve_n,r.rol_Desc_str from modulos m,roles r) s1 inner join " +
                        "rolesmodulos rm on s1.mod_cve_n=rm.mod_cve_n " +
                        "and s1.rol_Cve_n=rm.rol_cve_n where s1.rol_cve_n={0}";

                public static String PermisosEfectivosModulo = "select s1.mod_cve_n,s1.mod_desc_str,rm.rm_lectura_n as lectura, " +
                        "rm.rm_escritura_n as escritura,rm.rm_modificacion_n as modificacion, " +
                        "rm.rm_eliminacion_n as eliminacion from (select m.mod_cve_n,m.mod_desc_str," +
                        "r.rol_Cve_n,r.rol_Desc_str from modulos m,roles r) s1 inner join " +
                        "rolesmodulos rm on s1.mod_cve_n=rm.mod_cve_n " +
                        "and s1.rol_Cve_n=rm.rol_cve_n where s1.rol_cve_n={0} and s1.mod_desc_str='{1}'";

                public static String ObtenerRol = "select * from roles where rol_Cve_n={0}";

            }

            public static class Marcas
            {
                public static String CreaMarcas = "CREATE TABLE Marcas( " +
                        "mar_cve_n integer primary key autoincrement NOT NULL, " +
                        "mar_desc_str nvarchar(250) NOT NULL, " +
                        "est_cve_str nvarchar(10) NOT NULL) ";

                public static String SelMarcas = "select mar_cve_n,mar_desc_str,est_cve_str from Marcas";
                public static String DelMarcas = "delete from Marcas";
                public static String InsMarcas = "insert into Marcas (mar_cve_n,mar_desc_str,est_cve_str) " +
                        "values ({0},'{1}','{2}')";
            }

            public static class Modulos
            {
                public static String CreaModulos = "CREATE TABLE Modulos( " +
                        "mod_cve_n integer primary key autoincrement NOT NULL," +
                        "mod_desc_str nvarchar(250) NULL)";

                public static String SelModulos = "select mod_cve_n,mod_desc_str from Modulos";
                public static String DelModulos = "delete from Modulos";
                public static String InsModulos = "insert into Modulos (mod_cve_n,mod_desc_str) " +
                        "values ({0},'{1}')";
            }

            public static class MotivosNoLectura
            {
                public static String CreaMotivosNoLectura = "CREATE TABLE MotivosNoLectura( " +
                        "mnl_cve_n integer primary key autoincrement NOT NULL, " +
                        "mnl_desc_str nvarchar(250) NOT NULL)";

                public static String SelMotivosNoLectura = "select mnl_cve_n,mnl_desc_str from MotivosNoLectura";
                public static String DelMotivosNoLectura = "delete from MotivosNoLectura";
                public static String InsMotivosNoLectura = "insert into MotivosNoLectura (mnl_cve_n,mnl_desc_str) " +
                        "values ({0},'{1}')";
            }

            public static class MotivosNoVenta
            {
                public static String CreaMotivosNoVenta = "CREATE TABLE MotivosNoVenta( " +
                        "mnv_cve_n integer primary key autoincrement NOT NULL, " +
                        "mnv_desc_str nvarchar(250) NOT NULL)";

                public static String SelMotivosNoVenta = "select mnv_cve_n,mnv_desc_str from MotivosNoVenta";
                public static String DelMotivosNoVenta = "delete from MotivosNoVenta";
                public static String InsMotivosNoVenta = "insert into MotivosNoVenta (mnv_cve_n,mnv_desc_str) " +
                        "values ({0},'{1}')";
            }

            public static class MovimientosInventario
            {
                public static String CreaMovimientosInv = "Create Table MovimientosInv(" +
                        "movi_cve_n integer primary key autoincrement not null," +
                        "rut_cve_n bigint not null," +
                        "prod_cve_n bigint not null," +
                        "cli_cve_n bigint null," +
                        "usu_cve_str nvarchar(50) null," +
                        "prod_cantant_n decimal(18,4) null," +
                        "prod_vendido_n decimal(18,4) not null default 0," +
                        "prod_devuelto_n decimal(18,4) not null default 0," +
                        "prod_cantnva_n decimal(18,4) null," +
                        "movi_desc_str nvarchar(254) null," +
                        "movi_fecha_dt datetime not null)";

                public static String DelMovimientosInventario = "delete from MovimientosInv";
            }

            public static class NivelCliente
            {
                public static String CreaNivelCliente = "CREATE TABLE NivelCliente( " +
                        "nvc_cve_n integer primary key autoincrement NOT NULL, " +
                        "nvc_desc_str nvarchar(250) NOT NULL, " +
                        "est_cve_str nvarchar(10) NOT NULL, " +
                        "nvc_nivel_n int NOT NULL) ";

                public static String SelNivelCliente = "select nvc_cve_n,nvc_desc_str,est_cve_str,nvc_nivel_n from NivelCliente";
                public static String DelNivelCliente = "delete from NivelCliente";
                public static String InsNivelCliente = "insert into NivelCliente (nvc_cve_n,nvc_desc_str,est_cve_str,nvc_nivel_n) " +
                        "values ({0},'{1}','{2}',{3})";
            }

            public static class Pagos
            {

                public static String CreaPagos = " Create Table Pagos(" +
                        "pag_cve_n integer primary key autoincrement not null," +
                        "conf_cve_n int not null," +
                        "pag_referencia_str nvarchar(250) not null," +
                        "pag_cobranza_n bit not null default 0," +
                        "pag_abono_n decimal(18,4) null," +
                        "pag_fba_n bit null default 0," +
                        "pag_envase_n bit null default 0," +
                        "prod_cve_n bigint null," +
                        "prod_sku_str nvarchar(50) null," +
                        "prod_abono_n decimal(18,4) null," +
                        "pag_fecha_dt datetime not null," +
                        "fpag_cve_n int null," +
                        "fpag_referencia_str nvarchar(250) null," +
                        "fpag_banco_str nvarchar(250) null," +
                        "cli_cve_n bigint not null," +
                        "rut_cve_n int not null," +
                        "usu_cve_str nvarchar(50) not null," +
                        "trans_est_n tinyint null  default 0," +
                        "trans_fecha_dt datetime null," +
                        "pag_coordenada_str nvarchar(50) null," +
                        "pag_especie_n bit not null default 0)";

                public static String InsPago = "insert into pagos(conf_cve_n,pag_referencia_str,pag_abono_n," +
                        "pag_fba_n,pag_envase_n,prod_cve_n,prod_sku_str,prod_abono_n,pag_fecha_dt,fpag_cve_n,fpag_referencia_str," +
                        "fpag_banco_str,cli_cve_n,rut_cve_n,usu_cve_str,trans_est_n,pag_cobranza_n,pag_coordenada_str) values ({0},'{1}',{2}," +
                        "{3},{4},{5},'{14}',{6},getdate(),{7},'{8}'," +
                        "'{9}',{10},{11},'{12}',{13},{15},'{16}')";

                public static String InsPagoEnv = "insert into pagos(conf_cve_n,pag_referencia_str,pag_abono_n," +
                        "pag_fba_n,pag_envase_n,prod_cve_n,prod_sku_str,prod_abono_n,pag_fecha_dt,fpag_cve_n," +
                        "fpag_referencia_str,fpag_banco_str,cli_cve_n,rut_cve_n,usu_cve_str,trans_est_n," +
                        "pag_cobranza_n,pag_coordenada_str,pag_especie_n) values ({0},'{1}',{2}," +
                        "{3},{4},{5},'{14}',{6},getdate(),{7},'{8}'," +
                        "'{9}',{10},{11},'{12}',{13},{15},'{16}',{17})";

                public static String DelPagos = "delete from pagos";
            }

            public static class PrecioProductos
            {
                public static String CreaPrecioProductos = "CREATE TABLE PrecioProductos( " +
                        "lpre_cve_n int NOT NULL, " +
                        "prod_cve_n int NOT NULL, " +
                        "lpre_precio_n decimal(18, 4) NULL, " +
                        "lpre_volumen_n int null," +
                        "CONSTRAINT PK_PrecioProductos PRIMARY KEY  (lpre_cve_n,prod_cve_n )) ";

                public static String SelPrecioProductos = "select lpre_cve_n,prod_cve_n,lpre_precio_n,lpre_volumen_n " +
                        "from PrecioProductos";
                public static String DelPrecioProductos = "delete from PrecioProductos";
                public static String InsPrecioProductos = "insert into PrecioProductos (lpre_cve_n,prod_cve_n," +
                        "lpre_precio_n,lpre_volumen_n) values ({0},{1},{2},{3})";
            }

            public static class Presentaciones
            {
                public static String CreaPresentaciones = "CREATE TABLE Presentaciones( " +
                        "pres_cve_n integer primary key autoincrement NOT NULL, " +
                        "pres_desc_str nvarchar(250) NULL, " +
                        "pres_hectolitros_n decimal(18,4) NULL, " +
                        "est_cve_str nvarchar(10) NULL)";

                public static String SelPresentaciones = "select pres_cve_n,pres_desc_str,pres_hectolitros_n,est_cve_str from Presentaciones";
                public static String DelPresentaciones = "delete from Presentaciones";
                public static String InsPresentaciones = "insert into Presentaciones (pres_cve_n,pres_desc_str,pres_hectolitros_n,est_cve_str) " +
                        "values ({0},'{1}',{2},'{3}')";
            }

            public static class Productos
            {
                public static String CreaProductos = "CREATE TABLE Productos( " +
                        "prod_cve_n INTEGER primary key autoincrement NOT NULL, " +
                        "prod_sku_str nvarchar(50) NOT NULL, " +
                        "prod_desc_str nvarchar(250) NOT NULL, " +
                        "prod_dscc_str nvarchar(25) NOT NULL, " +
                        "cat_cve_n int NOT NULL, " +
                        "fam_cve_n int null," +
                        "retor_cve_str nvarchar(50) NULL, " +
                        "prod_orden_n int null default 0," +
                        "prod_reqenv_n bit NULL, " +
                        "id_envase_n bigint NULL, " +
                        "prod_unicompra_n int NOT NULL   DEFAULT ((0)), " +
                        "prod_factcompra_n int NOT NULL   DEFAULT ((1)), " +
                        "prod_univenta_n int NOT NULL, " +
                        "prod_factventa_n int NOT NULL   DEFAULT ((1)), " +
                        "prod_escompuesto_n bit NULL   DEFAULT ((0)), " +
                        "prod_manejainv_n bit null default 0," +
                        "prod_est_str nvarchar(10) NOT NULL, " +
                        "mar_cve_n int NULL, " +
                        "pres_cve_n int NULL, " +
                        "prod_ean_str nvarchar(50) NULL, " +
                        "prod_vtamismodia_n bit not null default 0," +
                        "prod_vtaefectivo_n bit not null default 0," +
                        "prod_vtavolumen_n bit not null default 0," +
                        "cov_cve_n int not null default 1)";

                public static String SelProductos = "select prod_cve_n,prod_sku_str,prod_desc_str,prod_dscc_str," +
                        "cat_cve_n,retor_cve_str,prod_reqenv_n,id_envase_n,prod_unicompra_n,prod_factcompra_n, " +
                        "prod_univenta_n,prod_factventa_n,prod_escompuesto_n,prod_manejainv_n,prod_est_str, " +
                        "mar_cve_n,pres_cve_n,prod_ean_str,fam_cve_n,prod_orden_n,prod_vtamismodia_n,prod_vtaefectivo_n, " +
                        "prod_vtavolumen_n,cov_cve_n from Productos";

                public static String DelProductos = "delete from Productos";

                public static String InsProductos = "insert into Productos (prod_cve_n,prod_sku_str,prod_desc_str," +
                        "prod_dscc_str,cat_cve_n,retor_cve_str,prod_reqenv_n,id_envase_n,prod_unicompra_n, " +
                        "prod_factcompra_n,prod_univenta_n,prod_factventa_n,prod_escompuesto_n,prod_manejainv_n, " +
                        "prod_est_str,mar_cve_n,pres_cve_n,prod_ean_str,fam_cve_n,prod_orden_n,prod_vtamismodia_n, " +
                        "prod_vtaefectivo_n,prod_vtavolumen_n,cov_cve_n) " +
                        "values ({0},'{1}','{2}','{3}',{4},'{5}',{6},{7},{8},{9}," +
                        "{10},{11},{12},{13},'{14}',{15},{16},'{17}',{18},{19}," +
                        "{20},{21},{22},{23})";

            }

            public static class Promociones
            {
                public static String CreaPromociones = "CREATE TABLE Promociones( " +
                        "prom_cve_n integer primary key autoincrement NOT NULL, " +
                        "prom_folio_str nvarchar(50) NOT NULL, " +
                        "prom_desc_str nvarchar(250) NOT NULL, " +
                        "tprom_cve_n int NOT NULL, " +
                        "prom_falta_dt datetime NOT NULL, " +
                        "prom_fini_dt datetime NULL, " +
                        "prom_ffin_dt datetime NULL, " +
                        "est_cve_str nvarchar(10) NULL, " +
                        "usu_cve_str nvarchar(50) NULL, " +
                        "usu_modificacion_str nvarchar(50) NULL, " +
                        "prom_fmodificacion_dt datetime NULL, " +
                        "prod_cve_n bigint NULL, " +
                        "prod_sku_str nvarchar(50) NULL, " +
                        "prom_nivel_n int NULL, " +
                        "lpre_cve_n int NULL, " +
                        "nvc_cve_n int NULL, " +
                        "nvc_cvehl_n int NULL, " +
                        "fam_cve_n int NULL, " +
                        "seg_cve_n int NULL, " +
                        "giro_cve_n int NULL, " +
                        "tcli_cve_n int NULL, " +
                        "lpre_precio_n decimal(18, 4) NULL, " +
                        "lpre_precio2_n decimal(18, 4) NULL, " +
                        "lpre_desc_n decimal(18, 4) NULL, " +
                        "prom_n_n decimal(18, 4) NULL, " +
                        "prom_m_n decimal(18, 4) NULL, " +
                        "prod_regalo_n bigint NULL, " +
                        "prod_skureg_str nvarchar(50) NULL, " +
                        "prom_story_n bit NULL, " +
                        "prom_proveedor_n bit NULL, " +
                        "prom_envase_n bit NULL default 0, " +
                        "prom_kit_n bit not null default 0)";

                public static String SelPromociones = "select prom_cve_n,prom_folio_str,prom_desc_str," +
                        "tprom_cve_n,prom_falta_dt,prom_fini_dt,prom_ffin_dt,est_cve_str,usu_cve_str, " +
                        "usu_modificacion_str,prom_fmodificacion_dt, " +
                        "prod_cve_n,prod_sku_str,prom_nivel_n,lpre_cve_n,nvc_cve_n,nvc_cvehl_n,fam_cve_n,seg_cve_n,giro_cve_n,tcli_cve_n,lpre_precio_n, " +
                        "lpre_desc_n,prom_n_n,prom_m_n,prod_regalo_n,prod_skureg_str,prom_story_n,prom_proveedor_n,prom_envase_n,prom_kit_n  from Presentaciones";

                public static String DelPromociones = "delete from Promociones";

                public static String InsPromociones = "insert into Promociones (prom_cve_n,prom_folio_str," +
                        "prom_desc_str,tprom_cve_n,prom_falta_dt,prom_fini_dt,prom_ffin_dt,est_cve_str," +
                        "usu_cve_str,usu_modificacion_str,prom_fmodificacion_dt,prod_cve_n,prod_sku_str, " +
                        "prom_nivel_n,lpre_cve_n,nvc_cve_n,nvc_cvehl_n,fam_cve_n,seg_cve_n,giro_cve_n," +
                        "tcli_cve_n,lpre_precio_n,lpre_precio2_n,lpre_desc_n,prom_n_n,prom_m_n,prod_regalo_n," +
                        "prod_skureg_str,prom_story_n,prom_proveedor_n,prom_envase_n,prom_kit_n) " +
                        "values ({0},'{1}','{2}',{3},'{4}','{5}','{6}','{7}','{8}','{9}','{10}'," +
                        "{11},'{12}',{13},{14},{15},{16},{17},{18},{19},{20}," +
                        "{21},{22},{23},{24},{25},{26},'{27}',{28},{29},{30},{31})";

                public static String CreatePromocionesKit = "create table PromocionesKit( " +
                        "prom_cve_n bigint not null, " +
                        "prod_cve_n bigint not null, " +
                        "prod_sku_str nvarchar(50) not null, " +
                        "prod_cant_n decimal(18,4) not null default 0, " +
                        "lpre_precio_n decimal(18,4) not null default 0, " +
                        "constraint PkPromoKit primary key (prom_cve_n,prod_cve_n))";

                public static String SelectPromocionesKit = "select prom_cve_n,prod_cve_n,"+
                        "prod_sku_str,prod_cant_n,lpre_precio_n from promocioneskit";

                public static String DeletePromocionesKit = "delete from promocioneskit";

                public static String InsertPromocionesKit = "insert into promocioneskit(" +
                        "prom_cve_n,prod_cve_n,prod_sku_str,prod_cant_n,lpre_precio_n) values(" +
                        "{0},{1},'{2}',{3},{4})";

            }

            public static class Preventa
            {
                public static String VisitaPreventa = "CREATE TABLE VisitaPreventa( " +
                        "visp_folio_str nvarchar(50) NOT NULL, " +
                        "cli_cve_n bigint NOT NULL, " +
                        "rut_cve_n int NOT NULL, " +
                        "visp_fecha_dt datetime NOT NULL, " +
                        "visp_coordenada_str nvarchar(50) NULL, " +
                        "usu_cve_str nvarchar(50) NOT NULL, " +
                        "trans_est_n int NULL, " +
                        "trans_fecha_dt datetime NULL, " +
                        "CONSTRAINT PK_VisitaPreventa PRIMARY KEY (visp_folio_str))";

                public static String DelVisitaPreventa = "delete from VisitaPreventa";

                public static String InsertVisitaPrev = "insert into visitapreventa(visp_folio_str,"  +
                        "cli_cve_n,rut_cve_n,visp_fecha_dt,visp_coordenada_str,usu_cve_str) values ('{0}',{1}," +
                        "{2},convert(datetime,'{3}',126),'{4}','{5}')";

                public static String InsertVisitaPrev2 = "insert into visitapreventa(visp_folio_str," +
                        "cli_cve_n,rut_cve_n,visp_fecha_dt,visp_coordenada_str,usu_cve_str) values ('{0}',{1}," +
                        "{2},'{3}','{4}','{5}')";

                public static String UpdateVisitaReparto = "update visitapreventa set trans_est_n=1 where visp_folio_str='{0}'";

            /*public static String NegociacionPreventa = "CREATE TABLE NegociacionPreventa( " +
	        "visp_folio_str nvarchar(50) NOT NULL, " +
	        "neg_num_n int NOT NULL, " +
	        "neg_saldo_n bit NOT NULL default 0, " +
	        "fpag_cve_n int NULL, " +
	        "neg_envase_n bit NOT NULL default 0, " +
	        "prod_cve_n bigint NULL, " +
	        "prod_sku_str nvarchar(50) NULL, " +
	        "neg_cant_n decimal(18, 4) NULL default 0, " +
	        "trans_est_n int NULL, " +
            "trans_fecha_dt datetime NULL, " +
            "CONSTRAINT PK_NegociacionPreventa PRIMARY KEY (visp_folio_str,neg_num_n))";

            public static String DelNegociacionPreventa = "delete from NegociacionPreventa";*/

                public static String CreaPreventa = "CREATE TABLE Preventa( " +
                        "prev_folio_str nvarchar(50) NOT NULL, " +
                        "cli_cve_n bigint NOT NULL, " +
                        "rut_cve_n int NOT NULL, " +
                        "prev_fecha_dt datetime NOT NULL, " +
                        "lpre_cve_n int NULL, " +
                        "dir_cve_n int NULL, " +
                        "usu_cve_str nvarchar(50) NOT NULL, " +
                        "prev_coordenada_str nvarchar(50) NULL, " +
                        "prev_condicionada_n bit NOT NULL Default 0, " +
                        "rut_repcve_n int NULL," +
                        "prev_comentario_str nvarchar(60) NULL," +
                        "trans_est_n int NULL, " +
                        "trans_fecha_dt datetime NULL, " +
                        "CONSTRAINT PK_Preventa PRIMARY KEY (prev_folio_str))";

                public static String InsPreventa = "insert into preventa(prev_folio_str,cli_cve_n,rut_cve_n," +
                        "prev_fecha_dt,lpre_cve_n,dir_cve_n,usu_cve_str,prev_coordenada_str,rut_repcve_n,prev_comentario_str)" +
                        "values ({0},{1},{2},getdate(),{3},{4},{5},{6},{7},{8})";

                public static String InsPreventaSinc = "insert into preventa(prev_folio_str," +
                        "cli_cve_n,rut_cve_n,prev_fecha_dt,lpre_cve_n,dir_cve_n,usu_cve_str," +
                        "prev_coordenada_str) values ('{0}',{1},{2},'{3}',{4},{5},'{6}','{7}')";

                public static String DelPreventa = "delete from Preventa";

                public static String PreventaDet = "CREATE TABLE PreventaDet( " +
                        "prev_folio_str nvarchar(50) NOT NULL, " +
                        "prev_num_n int NOT NULL, " +
                        "prod_cve_n bigint NOT NULL, " +
                        "prod_sku_str nvarchar(50) NULL, " +
                        "prod_envase_n bit NULL Default 0, " +
                        "prod_cant_n decimal(18, 4) NULL Default 0, " +
                        "lpre_base_n decimal(18, 4) NOT NULL Default 0, " +
                        "lpre_cliente_n decimal(18, 4) NOT NULL Default 0, " +
                        "lpre_promo_n decimal(18, 4) NOT NULL Default 0, " +
                        "lpre_precio_n decimal(18, 4) NOT NULL Default 0, " +
                        "prod_promo_n bit NULL, " +
                        "prom_cve_n bigint NULL, " +
                        "prod_subtotal_n decimal(18, 4) NULL Default 0, " +
                        "prev_kit_n bit not null default 0, " +
                        "CONSTRAINT PK_PreventaDet PRIMARY KEY (prev_folio_str ,prev_num_n ))";

                public static String DelPreventaDet = "delete from PreventaDet ";

                public static String InsPreventaDet = "insert into preventadet(prev_folio_str,prev_num_n," +
                        "prod_cve_n,prod_sku_str,prod_envase_n,prod_cant_n,lpre_base_n,lpre_cliente_n," +
                        "lpre_promo_n,lpre_precio_n,prod_promo_n,prom_cve_n,prod_subtotal_n,prev_kit_n)" +
                        "values ('{0}',{1},{2},'{3}',{4},{5},{6},{7},{8},{9},{10},{11},{12},{13})";

                public static String InsPreventaDet2 = "insert into preventadet(prev_folio_str," +
                        "prev_num_n,prod_cve_n,prod_sku_str,prod_envase_n,prod_cant_n,lpre_base_n," +
                        "lpre_cliente_n,lpre_promo_n,lpre_precio_n,prod_promo_n,prom_cve_n,prod_subtotal_n," +
                        "prev_kit_n) values ('{0}',{1},{2},'{3}',{4},{5},{6},{7},{8},{9},{10},{11},{12},{13})";

                public static String PreventaEnv ="CREATE TABLE PreventaEnv( " +
                        "prev_folio_str nvarchar(50) NOT NULL, " +
                        "prod_cve_n bigint NOT NULL, " +
                        "prod_sku_str nvarchar(50) NULL, " +
                        "prod_inicial_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_cargo_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_abono_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_regalo_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_venta_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "prod_final_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "lpre_base_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "lpre_precio_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "trans_est_n int NULL, " +
                        "trans_fecha_dt datetime NULL, " +
                        "CONSTRAINT PK_PreventaEnv PRIMARY KEY (prev_folio_str ,prod_cve_n ))";

                public static String DelPreventaEnv ="delete from PreventaEnv " ;

                public static String InsPreventaEnv = "insert into preventaenv(prev_folio_str,prod_cve_n,prod_sku_str," +
                        "prod_inicial_n,prod_cargo_n,prod_abono_n,prod_regalo_n,prod_venta_n,prod_final_n,lpre_base_n,lpre_precio_n) values ('{0}',{1},'{2}',"+
                        "{3},{4},{5},{6},{7},{8},{9},{10})";

                public static String InsPreventaEnv2 = "insert into preventaenv(prev_folio_str," +
                        "prod_cve_n,prod_sku_str,prod_inicial_n,prod_cargo_n,prod_abono_n,prod_regalo_n,prod_venta_n," +
                        "prod_final_n,lpre_base_n,lpre_precio_n) values ('{0}',{1},'{2}',{3},{4},{5},{6},{7},{8},{9},{10})";

                public static String PreventaPagos = "CREATE TABLE PreventaPagos( " +
                        "prev_folio_str nvarchar(50) NOT NULL, " +
                        "ppag_num_n tinyint NOT NULL, " +
                        "ppag_cobranza_n bit NOT NULL, " +
                        "fpag_cve_n int NOT NULL DEFAULT 0, " +
                        "fpag_cant_n decimal(18, 4) NOT NULL DEFAULT 0, " +
                        "CONSTRAINT PK_PreventaPagos PRIMARY KEY (prev_folio_str ,ppag_num_n, ppag_cobranza_n ))";

                public static String DelPreventaPagos = "delete from PreventaPagos ";

                public static String InsPreventaPagos = "insert into PreventaPagos(prev_folio_str,ppag_num_n,ppag_cobranza_n,fpag_cve_n,fpag_cant_n)" +
                        " values ('{0}',{1},{2},{3},{4})";

                public static String InsPreventaPagos2 = "insert into PreventaPagos(prev_folio_str," +
                        "ppag_num_n,ppag_cobranza_n,fpag_cve_n,fpag_cant_n) values ('{0}',{1},{2},{3},{4})";

                public static String ObtenerEnvasePreventa = "select s1.prod_cve_n,s1.prod_sku_str,coalesce(s2.prod_precio_n,s1.lpre_precio_n) prod_precio_n,coalesce(s2.prod_cant_n,0.0)prod_cant_n, " +
                        "coalesce(s2.prod_cantabono_n,0.0) prod_cantabono_n,coalesce(s2.prod_saldo_n,0.0) prod_saldo_n,coalesce(s2.prod_abono_n,0.0) prod_abono_n, " +
                        "coalesce(s2.prod_venta_n,0.0) prod_venta_n,coalesce(s2.prod_cargo_n,0.0) prod_cargo_n  from " +
                        "(select p.prod_cve_n,p.prod_sku_str,pp.lpre_precio_n from productos p inner join categorias c " +
                        "on p.cat_cve_n=c.cat_cve_n inner join precioproductos pp " +
                        "on p.prod_cve_n=pp.prod_cve_n where c.cat_desc_str='ENVASE' and pp.lpre_cve_n={1}) s1 left join (" +
                        "select prod_cve_n,prod_sku_str,max(prod_precio_n) prod_precio_n,sum(prod_cant_n) prod_cant_n," +
                        "sum(prod_cantabono_n) prod_cantabono_n,sum(prod_cant_n-prod_cantabono_n) prod_saldo_n,0.0 prod_abono_n," +
                        "0.0 prod_venta_n,0.0 prod_cargo_n from creditos where cli_cve_n={0} and cred_esenvase_n=1  " +
                        "group by prod_cve_n,prod_sku_str ) s2 on s1.prod_cve_n=s2.prod_cve_n";

                public static String ObtenerEnvasePreventa2 = "select s1.prod_cve_n,s1.prod_sku_str," +
                        "coalesce(s2.prod_precio_n,s1.lpre_precio_n) prod_precio_n,s1.lpre_precio_n,coalesce(s2.prod_cant_n,0.0) prod_cant_n, " +
                        "coalesce(s2.prod_cantabono_n,0.0) prod_cantabono_n,coalesce(s2.prod_saldo_n,0.0) prod_saldo_n," +
                        "coalesce(s2.prod_abono_n,0.0) prod_abono_n, 0.0 prod_regalo_n, " +
                        "coalesce(s2.prod_venta_n,0.0) prod_venta_n,coalesce(s2.prod_cargo_n,0.0) prod_cargo_n  " +
                        "from (select p.prod_cve_n,p.prod_sku_str,pp.lpre_precio_n from productos p " +
                        "inner join categorias c on p.cat_cve_n=c.cat_cve_n inner join precioproductos pp " +
                        "on p.prod_cve_n=pp.prod_cve_n where c.cat_desc_str='ENVASE' and pp.lpre_cve_n={2}) s1 " +
                        "left join (select c.prod_cve_n,c.prod_sku_str,s3.lpre_precio_n prod_precio_n," +
                        "sum(c.prod_cant_n) prod_cant_n,sum(c.prod_cantabono_n) prod_cantabono_n," +
                        "sum(c.prod_cant_n-c.prod_cantabono_n) prod_saldo_n,0.0 prod_abono_n,0.0 prod_venta_n," +
                        "0.0 prod_cargo_n from creditos c inner join " +
                        "(select p.prod_cve_n,p.prod_sku_str,pp.lpre_precio_n from productos p " +
                        "inner join categorias c on p.cat_cve_n=c.cat_cve_n inner join precioproductos pp " +
                        "on p.prod_cve_n=pp.prod_cve_n where c.cat_desc_str='ENVASE' and pp.lpre_cve_n={1}) s3 " +
                        "on c.prod_cve_n=s3.prod_cve_n " +
                        "where c.cli_cve_n={0} and c.cred_esenvase_n=1  " +
                        "group by c.prod_cve_n,c.prod_sku_str,s3.lpre_precio_n ) s2 on s1.prod_cve_n=s2.prod_cve_n";
            }

            public static class Roles
            {
                public static String CreaRoles = "CREATE TABLE Roles( " +
                        "rol_cve_n integer primary key autoincrement NOT NULL, " +
                        "rol_desc_str nvarchar(250) NOT NULL, " +
                        "rol_esadmin_n tinyint NOT NULL, " +
                        "est_cve_str nvarchar(10) NULL)";

                public static String SelRoles = "select rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str from Roles";
                public static String DelRoles = "delete from Roles";
                public static String InsRoles = "insert into Roles (rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str) " +
                        "values ({0},'{1}',{2},'{3}')";
            }

            public static class RolesModulos
            {
                public static String CreaRolesModulos = "CREATE TABLE RolesModulos( " +
                        "rol_cve_n int NOT NULL, " +
                        "mod_cve_n int NOT NULL, " +
                        "rm_lectura_n tinyint NOT NULL, " +
                        "rm_escritura_n tinyint NOT NULL, " +
                        "rm_modificacion_n tinyint NOT NULL, " +
                        "rm_eliminacion_n tinyint NOT NULL, " +
                        "CONSTRAINT PK_RolesModulos PRIMARY KEY(rol_cve_n ,mod_cve_n))";

                public static String SelRolesModulos = "select rol_cve_n,mod_cve_n,rm_lectura_n,rm_escritura_n,rm_modificacion_n,rm_eliminacion_n from RolesModulos";
                public static String DelRolesModulos = "delete from RolesModulos";
                public static String InsRolesModulos = "insert into RolesModulos (rol_cve_n,mod_cve_n,rm_lectura_n,rm_escritura_n,rm_modificacion_n,rm_eliminacion_n) " +
                        "values ({0},{1},{2},{3},{4},{5})";
            }

            public static class Rutas
            {

                public static String CreaRutas = "CREATE TABLE Rutas( " +
                        "rut_cve_n integer primary key autoincrement NOT NULL, " +
                        "rut_desc_str nvarchar(50) NOT NULL, " +
                        "rut_orden_n int not null default 0, " +
                        "trut_cve_n int NOT NULL, " +
                        "rut_idcteesp_n bigint null, " +
                        "rut_tel_str nvarchar(20) null, " +
                        "asesor_cve_str nvarchar(50) NULL, " +
                        "gerente_cve_str nvarchar(50) NULL, " +
                        "supervisor_cve_str nvarchar(50) NULL, " +
                        "est_cve_str nvarchar(10) NULL, " +
                        "tco_cve_n int NULL, " +
                        "rut_reparto_n int not null default 0, " +
                        "rut_especial_n bit not null default 0," +
                        "usu_modificacion_str nvarchar(50) null," +
                        "rut_modificacion_dt datetime null," +
                        "rut_prev_n int not null default 0," +
                        "rut_invalidafrecuencia_n bit not null default 0," +
                        "rut_productividad_n int NOT NULL default 75, " +
                        "rut_efectividad_n int NOT NULL default 40, " +
                        "rut_mayorista_n bit not null default 0," +
                        "rut_fiestasyeventos_n bit not null default 0," +
                        "rut_auditoria_n bit not null default 0," +
                        "rut_promoce_n bit not null default 0) ";

                public static String SelRutas = "select rut_cve_n,rut_desc_str,trut_cve_n,asesor_cve_str," +
                        "gerente_cve_str,supervisor_cve_str,est_cve_str,tco_cve_n,rut_prev_n,rut_invalidafrecuencia_n," +
                        "rut_productividad_n,rut_efectividad_n,rut_mayorista_n,rut_fiestasyeventos_n," +
                        "rut_auditoria_n,rut_promoce_n from Rutas";

                public static String DelRutas = "delete from Rutas";

                public static String InsRutas = "insert into Rutas (rut_cve_n,rut_desc_str,rut_orden_n," +
                        "trut_cve_n,asesor_cve_str,gerente_cve_str,supervisor_cve_str,est_cve_str,tco_cve_n,rut_prev_n) " +
                        "values ({0},'{1}',{2},{3},'{4}','{5}','{6}','{7}',{8},{9})";

                public static String InsRutas2 = "insert into Rutas (rut_cve_n,rut_desc_str,rut_orden_n," +
                        "trut_cve_n,asesor_cve_str,gerente_cve_str,supervisor_cve_str,est_cve_str,tco_cve_n," +
                        "rut_prev_n,rut_idcteesp_n,rut_invalidafrecuencia_n,rut_productividad_n,rut_tel_str," +
                        "rut_efectividad_n) values ({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14})";

                public static String InsRutas3 = "insert into Rutas (rut_cve_n,rut_desc_str,rut_orden_n," +
                        "trut_cve_n,asesor_cve_str,gerente_cve_str,supervisor_cve_str,est_cve_str,tco_cve_n," +
                        "rut_prev_n,rut_idcteesp_n,rut_invalidafrecuencia_n,rut_productividad_n,rut_tel_str," +
                        "rut_efectividad_n,rut_mayorista_n,rut_fiestasyeventos_n,rut_auditoria_n,rut_promoce_n) " +
                        "values ({0},'{1}',{2},{3},'{4}','{5}','{6}','{7}',{8},{9}," +
                        "{10},{11},{12},{13},{14},{15},{16},{17},{18})";
            }

            public static class Sugerido
            {
                public static String CreaSugerido = "Create table Sugerido(" +
                        "prod_cve_n bigint not null," +
                        "prod_sku_str nvarchar(50) null," +
                        "prod_sug_n decimal(18,4) not null default 0)";

                public static String DelSugerido = "delete from Sugerido";
            }

            public static class TipoPromociones
            {
                public static String ListarTipoPromociones = "CREATE TABLE TipoPromociones( " +
                        "tprom_cve_n integer primary key autoincrement NOT NULL, " +
                        "tprom_desc_str nvarchar(250) NOT NULL, " +
                        "tprom_falta_dt datetime NULL, " +
                        "est_cve_str nvarchar(10) NULL, " +
                        "usu_cve_str nvarchar(50) NULL )";

                public static String SelTipoPromociones = "select tprom_cve_n,tprom_desc_str," +
                        "tprom_falta_dt,est_cve_str,usu_cve_str  from TipoPresentaciones";

                public static String DelTipoPromociones = "delete from TipoPromociones";

                public static String InsTipoPromociones = "insert into TipoPromociones (tprom_cve_n,tprom_desc_str,tprom_falta_dt," +
                        "est_cve_str,usu_cve_str) values ({0},{1},{2},{3},{4})";

            }

            public static class TipoRutas
            {
                public static String CreaTipoRutas = "CREATE TABLE TipoRutas( " +
                        "trut_cve_n integer primary key autoincrement NOT NULL," +
                        "trut_desc_str nvarchar(250) NOT NULL," +
                        "est_cve_str nvarchar(10) NOT NULL)";

                public static String SelTipoRutas = "select trut_cve_n,trut_desc_str,est_cve_str from TipoRutas";
                public static String DelTipoRutas = "delete from TipoRutas";
                public static String InsTipoRutas = "insert into TipoRutas (trut_cve_n,trut_desc_str,est_cve_str) " +
                        "values ({0},'{1}','{2}')";
            }

            public static class Trabajo
            {
                public static String InsertBitacoraHHSesion = "Insert into bitacoraHH(usu_cve_str,bit_fecha_dt,bit_operacion_str,bit_comentario_str," +
                        "rut_cve_n,bit_coordenada_str) values('{0}',datetime('now','localtime'),'{1}','{2}',{3},'{4}')";

                public static String InsertBitacoraHHPedido = "Insert into bitacoraHH(usu_cve_str,rut_cve_n,cli_cve_n,bit_fecha_dt," +
                        "bit_operacion_str,bit_comentario_str,bit_coordenada_str) " +
                        "values('{0}',{1},{2},getdate(),'{3}','{4}','{5}')";

                public static String InsertVisita = "Insert into Visitas(vis_fecha_dt,usu_cve_str,cli_cve_n," +
                        "mnv_cve_n,mnl_cve_n,vis_operacion_str,vis_observacion_str,vis_coordenada_str) values(getdate(),'{0}',{1},{2},{3},'{4}','{5}','{6}')";

                public static String InsCreditos = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku_str,prod_precio_n,prod_cant_n," +
                        "prod_cantabono_n,trans_est_n,trans_fecha_dt) values ('{0}',{1}," +
                        "'{2}','{3}','{4}','{5}',{6}," +
                        "{7},{8},{9},{10},{11},'{15}',{12},{13},{14},3,null)";

                public static String InsCreditosPago = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku_str," +
                        "prod_precio_n,prod_cant_n,prod_cantabono_n,trans_est_n,trans_fecha_dt) " +
                        "values ('{0}',{1},'{2}',getdate(),'{3}',dateadd(day,15,getdate()),{4}," +
                        "0,0,{5},0,{6},'{9}',{7},{8},0,0,null)";

                //No utilizado hay que reordenar parametros
                public static String InsCreditosPago2 = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku_str," +
                        "prod_precio_n,prod_cant_n,prod_cantabono_n,trans_est_n,trans_fecha_dt) " +
                        "values ('{0}',{1},'{2}',getdate(),'{3}',dateadd(day,{4},getdate()),{5}," +
                        "0,0,{6},0,{7},'{8}',{9},{10},0,0,null)";

                public static String InsCreditosPago3 = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku_str," +
                        "prod_precio_n,prod_cant_n,prod_cantabono_n,trans_est_n,trans_fecha_dt) " +
                        "values ('{0}',{1},'{2}',getdate(),'{3}',dateadd(day,{10},getdate()),{4}," +
                        "0,0,{5},0,{6},'{9}',{7},{8},0,0,null)";

                public static String InsConsignaPago = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku_str,prod_precio_n,prod_cant_n," +
                        "prod_cantabono_n,trans_est_n,trans_fecha_dt) values ('{0}',{1}," +
                        "'{2}',getdate(),'{3}',dateadd(day,{9},getdate()),{4}," +
                        "0,0,{5},1,{6},'{10}',{7},{8},0,0,null)";

                public static String InsCreditosPagoEnv = "insert into Creditos(cred_referencia_str,cli_cve_n," +
                        "usu_cve_str,cred_fecha_dt,cred_descripcion_str,cred_vencimiento_dt,cred_monto_n," +
                        "cred_abono_n,cred_engestoria_n,cred_esenvase_n,cred_especial_n,prod_cve_n,prod_sku,str,prod_precio_n,prod_cant_n," +
                        "prod_cantabono_n,trans_est_n,trans_fecha_dt) values ('{0}',{1}," +
                        "'{2}',getdate(),'{3}',dateadd(day,15,getdate()),{4}," +
                        "0,0,{5},0,{6},'{9}',{7},{8},0,0,null)";

                public static String DelCreditos = "delete from Creditos ";
                public static String DelCreditos2 = "delete from Creditos where trans_est_n=3";
                public static String SelCreditos = "Select * from Creditos";

            }

            public static class Unidades
            {
                public static String CreaUnidades = "CREATE TABLE Unidades( " +
                        "uni_cve_n integer primary key autoincrement NOT NULL, " +
                        "uni_simbolo_str nvarchar(10) NOT NULL, " +
                        "uni_desc_str nvarchar(50) NOT NULL, " +
                        "uni_divisible_n tinyint NOT NULL, " +
                        "uni_pesable_n tinyint NOT NULL, " +
                        "est_cve_str nvarchar(10) NOT NULL)";

                public static String SelUnidades = "select uni_cve_n,uni_simbolo_str,uni_desc_str,uni_divisible_n,uni_pesable_n,est_cve_str from unidades";
                public static String DelUnidades = "delete from unidades";
                public static String InsUnidades = "insert into unidades (uni_cve_n,uni_simbolo_str,uni_desc_str,uni_divisible_n,uni_pesable_n,est_cve_str) " +
                        "values ({0},'{1}','{2}',{3},{4},'{5}')";
            }

            public static class Usuarios
            {
                public static String CreaUsuarios = "CREATE TABLE Usuarios( " +
                        "usu_cve_str nvarchar(50) NOT NULL," +
                        "usu_pwd_str nvarchar(50) NOT NULL," +
                        "usu_nom_str nvarchar(50) NOT NULL," +
                        "usu_app_str nvarchar(50) NOT NULL," +
                        "usu_apm_str nvarchar(50) NOT NULL," +
                        "rol_cve_n int NOT NULL," +
                        "est_cve_str nvarchar(10) NOT NULL," +
                        "usu_factivo_dt datetime NULL," +
                        "usu_bloqueado_n tinyint NOT NULL," +
                        "usu_fbloqueo_dt datetime NULL," +
                        "CONSTRAINT PK_Usuarios PRIMARY KEY (usu_cve_str ))";

                public static String SelUsuarios = "select usu_cve_str,usu_pwd_str,usu_nom_str,usu_app_str,usu_apm_str,rol_cve_n,est_cve_str,usu_factivo_dt,usu_bloqueado_n,usu_fbloqueo_dt from Usuarios";
                public static String DelUsuarios = "delete from Usuarios";
                public static String InsUsuarios = "insert into Usuarios (usu_cve_str,usu_pwd_str,usu_nom_str,usu_app_str,usu_apm_str,rol_cve_n,est_cve_str,usu_bloqueado_n,usu_fbloqueo_dt) " +
                        "values ('{0}','{1}','{2}','{3}','{4}',{5},'{6}',{7},'{8}')";
            }

            public static class Ventas
            {
                public static String CreaVentas = "CREATE TABLE Ventas(" +
                        //"ven_cve_n int  not null, " +
                        "ven_folio_str nvarchar(20) not null," +
                        "cli_cve_n bigint not null," +
                        "rut_cve_n int not null," +
                        "ven_fecha_dt datetime not null, " +
                        "ven_est_str nvarchar(10) not null default 'A'," +
                        "lpre_cve_n int not null, " +
                        "dir_cve_n int null, " +
                        "usu_cve_str nvarchar(50) not null," +
                        "ven_coordenada_str nvarchar(50) null," +
                        "ven_credito_n bit null default 0," +
                        //"ven_cobranza_n bit null default 0," +
                        //"ven_pagos_n bit null default 0," +
                        //"ven_devenvase_n bit null default 0," +
                        "prev_folio_str nvarchar(20) null," +
                        "ped_folio_str nvarchar(20) null," +
                        "csgn_cve_str nvarchar(20) null," +
                        "csgn_entrega_n tinyint null," +
                        "ven_consigna_n bit null default 0," +
                        "ven_pulest_n tinyint not null default 0," +
                        "ven_comentario_str nvarchar(60) null," +
                        "trans_est_n int null  default 0, " +
                        "trans_fecha_dt datetime null," +
                        "CONSTRAINT pk_ventas PRIMARY KEY (ven_folio_str))";

                public static String VentasDet = "CREATE TABLE VentasDet(" +
                        //"ven_cve_n int not null," +
                        "ven_folio_str nvarchar(20) not null," +
                        "vdet_cve_n int not null," +
                        "prod_cve_n bigint not null," +
                        "prod_sku_str nvarchar(50) null," +
                        "prod_envase_n bit not null default 0," +
                        "prod_cant_n decimal(18,4) not null default 0," +
                        "env_devueltos_n decimal(18,4) not null default 0," +
                        "lpre_base_n decimal(18,4) not null default 0," +
                        "lpre_cliente_n decimal(18,4) not null default 0," +
                        "lpre_promocion_n decimal(18,4) not null default 0," +
                        "lpre_precio_n decimal(18,4) not null default 0," +
                        "prod_subtotal_n decimal(18,4) not null default 0," +
                        "prod_promo_n bit not null default 0," +
                        "prom_cve_n bigint null default 0," +
                        "prod_regalo_n bit not null default 0," +
                        "prod_cobranza_n bit not null default 0," +
                        "vdet_dia_n bit not null default 1," +
                        "vdet_kit_n bit not null default 0," +
                        "CONSTRAINT pk_ventasdet PRIMARY KEY (ven_folio_str,vdet_cve_n))";

                public static String VentaEnv = "Create table VentaEnv(" +
                        "cli_cve_n bigint not null," +
                        "cli_cveext_str nvarchar(50) not null," +
                        "ven_folio_str nvarchar(50) not null," +
                        "prod_cve_n bigint not null," +
                        "prod_sku_str nvarchar(50) not null," +
                        "ven_cargo_n int null default 0," +
                        "ven_abono_n int null default 0," +
                        "ven_regalo_n int null default 0," +
                        "ven_venta_n int null default 0," +
                        "ven_consigna_n int null default 0," +
                        "lpre_base_n decimal(18,4) default 0," +
                        "lpre_precio_n decimal(18,4) default 0," +
                        "ven_prestamo_n int null default 0," +
                        "trans_est_n tinyint null  default 0," +
                        "trans_fecha_dt datetime null," +
                        "CONSTRAINT PK_VentaEnv PRIMARY KEY (cli_cve_n ,ven_folio_str,prod_cve_n ))";

            /*
             *    Sin uso detectado 2019-05-25 17:26
             * 
            public static String InsVentas = "insert into ventas (ven_folio_str,cli_cve_n,ven_fecha_dt," +
            "rut_cve_n,lpre_cve_n,dir_cve_n,usu_cve_str,ven_coordenada_str) values('{0}',{1}," +
            "getdate(),{2},{3},{4},'{5}','{6}')";*/

                public static String InsVentas2 = "insert into ventas (ven_folio_str,cli_cve_n,ven_fecha_dt," +
                        "rut_cve_n,lpre_cve_n,dir_cve_n,usu_cve_str,ven_coordenada_str,prev_folio_str,ven_pulest_n,ven_comentario_str)" +
                        "values({0},{1},getdate(),{2},{3},{4},{5},{6},{7},{8},{9})";

                public static String InsVentaDet = "insert into ventasdet (ven_folio_str,vdet_cve_n," +
                        "prod_cve_n,prod_sku_str,prod_envase_n,prod_cant_n,env_devueltos_n,lpre_base_n," +
                        "lpre_cliente_n,lpre_promocion_n,lpre_precio_n,prod_subtotal_n,prod_promo_n,prom_cve_n," +
                        "prod_regalo_n,prod_cobranza_n,vdet_kit_n)" +
                        " values ('{0}',{1},{2},'{3}',{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16})";

                public static String InsVentaDetEnv = "insert into ventasdet (ven_folio_str,vdet_cve_n," +
                        "prod_cve_n,prod_sku_str,prod_envase_n,prod_cant_n,env_devueltos_n,lpre_base_n,lpre_cliente_n," +
                        "lpre_promocion_n,lpre_precio_n,prod_subtotal_n,prod_promo_n,prom_cve_n,prod_regalo_n,prod_cobranza_n," +
                        "vdet_dia_n)" +
                        " values ('{0}',{1},{2},'{3}',{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16})";

                public static String InsVentaEnv = "insert into ventaenv (cli_cve_n,cli_cveext_str," +
                        "ven_folio_str,prod_cve_n,prod_sku_str,ven_cargo_n,ven_abono_n,ven_regalo_n," +
                        "ven_venta_n,ven_consigna_n,ven_prestamo_n,lpre_base_n,lpre_precio_n) values " +
                        "({0},'{1}','{2}',{3},'{4}',{5},{6},{7},{8},{9},{10},{11},{12})";

                public static String DelVentas = "delete from ventas";


            }

            public static class VentasDet
            {
                public static String DelVentasDet = "delete from ventasdet";
            }

            public static class VentasEnv
            {
                public static String DelVentaEnv = "delete from VentaEnv";
            }

            public static class Visitas
            {
                public static String CreaVisitas = "Create Table Visitas(" +
                        "vis_cve_n integer primary key autoincrement not null," +
                        "vis_fecha_dt datetime not null," +
                        "usu_cve_str nvarchar(50) not null," +
                        "cli_cve_n bigint not null," +
                        "mnv_cve_n int null," +
                        "mnl_cve_n int null," +
                        "vis_operacion_str nvarchar(250) null," +
                        "vis_observacion_str nvarchar(250) null," +
                        "vis_coordenada_str nvarchar(250) null," +
                        "trans_est_n tinyint null default 0," +
                        "trans_fecha_dt datetime null)";

                public static String DelVisitas = "delete from visitas";

            }

}
