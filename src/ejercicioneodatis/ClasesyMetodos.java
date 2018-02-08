package ejercicioneodatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author jquesadaabeijon
 */
public class ClasesyMetodos {

    String ODB_NAME = "baseneodatis";

    public class Sport {

        private String name;

        public String getName() {
            return name;
        }

        public Sport(String name) {
            this.name = name;
        }
    }

    public class Player {

        private String name;
        private Date birthDate;
        private Sport favoriteSport;
        private int salario;

        public String getName() {
            return name;
        }

        public Sport getFavoriteSport() {
            return favoriteSport;
        }

        public int getSalario() {
            return salario;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Player(String name, Date birthDate, Sport favoriteSport, int salario) {
            this.name = name;
            this.birthDate = birthDate;
            this.favoriteSport = favoriteSport;
            this.salario = salario;
        }

    }

    public class Team {

        private String name;
        private List players;
        private Player player;

        public Team(String name) {
            this.name = name;
            players = new ArrayList();
        }

        public String getName() {
            return name;
        }

        public List getPlayers() {
            return players;
        }

        public Player getPlayer() {
            return player;
        }

        public void addPlayer(Player player) {
            players.add(player);
        }

    }

    public class Game {

        private Date when;
        private Sport sport;
        private Team team1;
        private Team team2;
        private String result;

        public Game(Date when, Sport sport, Team team1, Team team2) {
            this.when = when;
            this.sport = sport;
            this.team1 = team1;
            this.team2 = team2;
        }
    }

    /*
     Métodos
     */
    public void step1(ODB odb) throws Exception {
        try {
            // Create instance
            Sport sport = new Sport("volley-ball");
            Sport tennis = new Sport("tennis");
            // Store the object
            odb.store(sport);
            odb.store(tennis);
            odb.commit();
        } catch (Exception e) {

        }
    }

    public void displaySports(ODB odb) throws Exception {
        try {
            // Get all object of type Sport
            Objects<Sport> sports = odb.getObjects(Sport.class);
            // display each object
            Sport sport = null;
            while (sports.hasNext()) {
                sport = sports.next();
                System.out.println(sport.getName());
            }
        } catch (Exception e) {

        }
    }

    public void step2(ODB odb) throws Exception {

        // Create instance
        Sport volleyball = new Sport("volley-ball");
        Sport tennis = new Sport("tennis");

        // Create 4 players
        Player player1 = new Player("olivier", new Date(), volleyball, 1000);
        Player player2 = new Player("pierre", new Date(), volleyball, 1500);
        Player player3 = new Player("elohim", new Date(), volleyball, 2000);
        Player player4 = new Player("minh", new Date(), volleyball, 1300);
        Player player5 = new Player("luis", new Date(), tennis, 1600);
        Player player6 = new Player("carlos", new Date(), tennis, 2000);
        Player player7 = new Player("luis", new Date(), tennis, 1500);
        Player player8 = new Player("jose", new Date(), tennis, 3000);

        // Create two teams
        Team team1 = new Team("Paris");
        Team team2 = new Team("Montpellier");
        Team team3 = new Team("Bordeux");
        Team team4 = new Team("Lion");
        // Set players for team1
        team1.addPlayer(player1);
        team1.addPlayer(player2);
        // Set players for team2
        team2.addPlayer(player3);
        team2.addPlayer(player4);
        // Set players for team3
        team3.addPlayer(player5);
        team3.addPlayer(player6);
        // Set players for team4
        team4.addPlayer(player7);
        team4.addPlayer(player8);
        // Then create a volley ball and tennis game for the four teams
        Game game = new Game(new Date(), volleyball, team1, team2);
        Game game2 = new Game(new Date(), tennis, team3, team4);
        // Store the object
        try {
            odb.store(player1);
            odb.store(player2);
            odb.store(player3);
            odb.store(player4);
            odb.store(player5);
            odb.store(player6);
            odb.store(player7);
            odb.store(player8);
            odb.store(team1);
            odb.store(team2);
            odb.store(team3);
            odb.store(team4);
            odb.store(game);
            odb.store(game2);
            odb.commit();
        } catch (Exception e) {

        }
    }

    public void displayPlayers(ODB odb) throws Exception {
        try {
            // Get all object of type Sport
            Objects<Player> players = odb.getObjects(Player.class);
            // display each object
            Player player;
            while (players.hasNext()) {
                player = players.next();
                System.out.println("Nombre: " + player.getName());
                System.out.println("Deporte favorito: " + player.getFavoriteSport().getName());
                System.out.println("Salario: " + player.getSalario());
                System.out.println("--------------------");
            }
        } catch (Exception e) {

        }
    }

    public void actualiza_por_nome_xogador(ODB odb, String nome_antigo, String nome_novo) {
        try {
            nome_antigo = JOptionPane.showInputDialog("Introduce el nombre del jugador que quieres cambiar: ");
//            int salario = Integer.parseInt(JOptionPane.showInputDialog("Introduce el salario: "));
//            IQuery query = new CriteriaQuery(Player.class, Where.and().add(Where.equal("name", nome_antigo)).add(Where.equal("salario", salario)));
            IQuery query = new CriteriaQuery(Player.class, Where.equal("name", nome_antigo));
            Objects<Player> players = odb.getObjects(query);
            nome_novo = JOptionPane.showInputDialog("Introduce el nombre nuevo: ");
            // Gets the first player (there is only one!)

            while (players.hasNext()) {
                Player player = players.next();
                player.setName(nome_novo);
                // Actually updates the object
                odb.store(player);
            }
            // Commits the changes
            odb.commit();
            System.out.println("El nombre " + nome_antigo + " se ha cambiado por el nombre: " + nome_novo);
//            }
        } catch (Exception e) {

        }

    }

    public void xogadoresDeporte(ODB odb, String deporte) {
        try {
            deporte = JOptionPane.showInputDialog("Introduce un deporte para ver los jugadores.\n * 1. volley-ball\n * 2. tennis");
            CriteriaQuery query = new CriteriaQuery(Player.class, Where.equal("favoriteSport.name", deporte));
            Objects<Player> players = odb.getObjects(query);
            int i = 1;
            while (players.hasNext()) {
                Player player = players.next();
                System.out.println("Jugador " + i++ + ": " + player.getName());
            }
        } catch (Exception e) {
        }
    }

    public void devoltar_equipos_con_xogadores_menos_dunha_cantidade(ODB odb, int cantidade) {
        try {
            cantidade = Integer.parseInt(JOptionPane.showInputDialog("Introduce el salario máximo a mostrar: "));
            Objects<Team> teams = odb.getObjects(Team.class);
            CriteriaQuery query = new CriteriaQuery(Player.class, Where.le("salario", cantidade));
            Objects<Player> players = odb.getObjects(query);
            
            while (teams.hasNext()) {
                Team team = teams.next();
                Player player = players.next();
                System.out.println("Equipo: " + team.getName() + "\nJugador: " + player.getName() + "\nSalario: " + player.getSalario() + "\n-------------------");
            }
        } catch (Exception e) {

        }
    }
    
    public void iguala_nomes_por_deporte(ODB odb,String deport, String nomeNovo) {
        try {
            deport = JOptionPane.showInputDialog("Introduce el deporte que quieres modificar: ");
            IQuery query = new CriteriaQuery(Player.class, Where.equal("favoriteSport.name", deport));
            Objects<Player> players = odb.getObjects(query);
            nomeNovo = JOptionPane.showInputDialog("Introduce el nombre nuevo: ");
            // Gets the first player (there is only one!)

            while (players.hasNext()) {
                Player player = players.next();
                player.setName(nomeNovo);
                // Actually updates the object
                odb.store(player);
                System.out.println("Deporte: " + deport + "\nJugador: " + player.getName() + "\n-------------------");
            }
            // Commits the changes
            odb.commit();
            System.out.println("Se ha cambiado el nombre de todos los jugadores de " +deport+ " por: " +nomeNovo);

        } catch (Exception e) {

        }
    }
    
    public void nativeQueryXogadoresDeporte(ODB odb, final String dep) {
        try{
            IQuery query = new SimpleNativeQuery() {

            public boolean match(Player player) {
                return player.getFavoriteSport().getName().toLowerCase().startsWith(dep);
            }
        };

            Objects<Player> players = odb.getObjects(query);
            System.out.println("\nJugadores que juegan a tennis");
            // display each object
            int i = 1;
            while (players.hasNext()) {
                Player player = players.next();
                System.out.println("Jugador "+(i++) + ": " + player.getName());
            } 
        } catch (Exception e) {
            
        }
        
    }
    
    public void borra_xogadores_por_nome(ODB odb,String nome) {
        try {
            nome = JOptionPane.showInputDialog("Introduce el nombre del jugador que quieres borrar: ");
            IQuery query = new CriteriaQuery(Player.class, Where.equal("name", nome));
            Objects<Player> players = odb.getObjects(query);
            while (players.hasNext()) {
                Player player = players.next();
                odb.delete(player);
                System.out.println("El jugador " + nome + " ha sido borrado de la base de datos");
            }
            System.out.println("");
        } catch (Exception e) {
            
        }
    }
    
    public void listar_xogadores_por_nome_e_deporte(ODB odb,String nome,String depor) {
        try {
            depor = JOptionPane.showInputDialog("Introduce un deporte para ver los jugadores.\n * 1. volley-ball\n * 2. tennis");
            nome = JOptionPane.showInputDialog("Introduce un nombre: ");
            IQuery query = new CriteriaQuery(Player.class, Where.and().add(Where.equal("name", nome)).add(Where.equal("favoriteSport.name", depor)));
            Objects<Player> players = odb.getObjects(query);
            int i = 1;
            System.out.println("Jugadores con el mismo nombre que practican " +depor);
            while (players.hasNext()) {
                Player player = players.next();
                System.out.println("Jugador " + i++ + ": " + player.getName());
            }
        } catch (Exception e) {
            
        }
    }
    
    public void aumenta_salario_xogadores_equipo(ODB odb, String xog, String equi, int aumento) {
        try {
            xog = JOptionPane.showInputDialog("Introduce el nombre del jugador al que le quieres aumentar el salario: ");
            equi = JOptionPane.showInputDialog("Introduce el equipo en el que juega: ");
            aumento = Integer.parseInt(JOptionPane.showInputDialog("Introduce cuánto quieres aumentar el salario: "));
            CriteriaQuery query = new CriteriaQuery(Team.class, Where.and().add(Where.equal("name", equi)).add(Where.equal("player.name", xog)));
            Objects<Team> teams = odb.getObjects(query);
            Objects<Player> players = odb.getObjects(Player.class);
            while (teams.hasNext()) {
                Team team = teams.next();
                Player player = players.next();
                System.out.println("Equipo: " + team.getName() + "\nJugador: " + player.getName() + "\nSalario: " + (player.getSalario()+aumento) + "\n-------------------");
            }
        } catch (Exception e){
            
        }
    }
}
