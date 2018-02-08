package ejercicioneodatis;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

/**
 *
 * @author jquesadaabeijon
 */
public class EjercicioNeoDatis {
public static ODB odb;
    public static void main(String[] args) throws Exception {
        
        ClasesyMetodos cm = new ClasesyMetodos();
        odb = ODBFactory.open(cm.ODB_NAME);
        //cm.step1(odb);
//        cm.displaySports(odb);
//        cm.step2(odb);
        cm.displayPlayers(odb);
//        cm.actualiza_por_nome_xogador(odb, null, null);
//        cm.xogadoresDeporte(odb, null);
//        cm.devoltar_equipos_con_xogadores_menos_dunha_cantidade(odb, 0);
//        cm.iguala_nomes_por_deporte(odb, null, null);
//        cm.nativeQueryXogadoresDeporte(odb, "ten");
//        cm.borra_xogadores_por_nome(odb, null);
//        cm.listar_xogadores_por_nome_e_deporte(odb, null, null);
//        cm.aumenta_salario_xogadores_equipo(odb, null, null, 0);
        odb.close();
    }   
}
