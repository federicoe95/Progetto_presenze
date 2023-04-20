package socketpresenze;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {

	//	private static finale int ERROR = -1;
	private static final int OK = 0;

	private static Connection con1 = null;
	private static Statement stmt8 = null;
	private static Statement stmt9 = null;


	private static int menu(){
		int scelta;
		do{
			System.out.println("Quale operazione vuoi effettuare?");
			System.out.println("(1) Entrata");
			System.out.println("(2) Uscita"); 
			scelta = SimpleIo.getInt();
			if ((scelta !=1) && (scelta != 2)){
				System.out.println("ATTENZIONE!! Hai scelto un'operazione non disponibile, scegli un'operazione disponibile");
			}
		}while ((scelta !=1) && (scelta != 2));
		return scelta;
	}

	private static int menuC(){ //menù visualizzato dal controllore meglio noto come ADMIN
		int scelta;
		do{
			System.out.println("Quale operazione vuoi effettuare?");
			System.out.println("(1) Entrata");
			System.out.println("(2) Uscita");
			System.out.println("(3) Visualizza Dipendenti in Azienda");
			System.out.println("(4) Visualizza Dipendenti non entrati");
			scelta = SimpleIo.getInt();
			if ((scelta != 1) && (scelta != 2) && (scelta != 3)&& (scelta != 4)){
				System.out.println("ATTENZIONE!! Hai scelto un'operazione non disponibile, scegli un'operazione disponibile");
			}
		}while ((scelta < 1) && (scelta > 4));
		return scelta;
	}

	private static void apriConnessione() throws ClassNotFoundException, SQLException {		
		//potrei\dovrei passare i parametri della connessione come argomenti ... 
		//istanzio il driver
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//apro la connessione
		//con1 = DriverManager.getConnection("jdbc:mysql://db.poloaretino.tk:3306/emolo_IS","federicoemolo","Ciao95");
		con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/presenze?characterEncoding=latin1","root",null);
		//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contadb","root","root");
		//imposto il commit automatico a SI (ogni statement effetto la commit)
		con1.setAutoCommit(true);
		stmt8 = con1.createStatement();
		stmt9 = con1.createStatement();
	}

	public static void stampaPresenti() throws SQLException{
		String sql = "SELECT matricola, presenza FROM registro WHERE presenza =" + 1;
		ResultSet rs8 = stmt8.executeQuery(sql);
		while (rs8.next()) {
			System.out.println(rs8.getString("matricola"));	
		}
		rs8.close();
	}
	
	public static void stampaAssenti() throws SQLException{
		String sql = "SELECT matricola, presenza FROM registro WHERE presenza =" + 0;
		ResultSet rs9 = stmt9.executeQuery(sql);
		while (rs9.next()) {
			System.out.println(rs9.getString("matricola"));	
		}
		rs9.close();
	}
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		Socket s = null;
		DataInputStream input = null;
		DataOutputStream output = null;
		
		apriConnessione();
		
		boolean rimani = true;
		int contatoreSbagli;
		try { 
			s = new Socket("localhost", 123); 
			input = new DataInputStream(s.getInputStream()); 
			output = new DataOutputStream(s.getOutputStream());
			contatoreSbagli = 3; //VARIABILE DI CONTROLLO CHE MI PERMETTE DI IMPOSTARE UN NUMERO MASSIMO DI ERRORI DURANTE L'INSERIMENTO DELLE CREDENZIALI
			while (rimani) {		//IN CASO DI ESITO ERRATO IN TUTTI E 3 I TENTATIVI, IL CLIENT VIENE CHIUSO				
				System.out.println("Benvenuto hai " + contatoreSbagli + " tentativi!");
				System.out.println("Inserire Matricola");
				output.writeInt(SimpleIo.getInt()); 			//ID acquisito da tastiera
				System.out.println("Inserire PassWord");
				output.writeUTF(SimpleIo.Read());				//PW acquisita da tastiera
				output.flush();									//invio ID e PW al server
				int esito = input.readInt();					//il server mi dirà se ho inserito ID e PW corretti (vedi riga 75 Server.java)
				if (esito == OK){
					System.out.println("Accesso Effettuato");
					rimani = false;
				}else{											

					contatoreSbagli--;
					if (contatoreSbagli == 0) // SE HO ESAURITO I TENTATIVI...
					{
						System.out.println("Tentativi esauriti");
						System.out.println("La sessione verrà chiusa");
						rimani = false;
						input.close();
						output.close();
						s.close();
					}
					else
					{
						System.out.println("ID e/o PW inseriti non validi Tentativi rimasti:" + contatoreSbagli);
					}	
				}
			}
			System.out.println("----");
			String avviso;
			int idS = input.readInt();
			if (idS == 9999) {
				System.out.println("Benvenuto Controllore");
				output.writeBoolean(true);  // SE L'ADMIN HA EFFETTUATO L'ACCESSO LO COMUNICO AL SERVER
				int scelta = menuC();  // MENU DEL CONTROLLORE O ADMIN
				output.writeInt(scelta); // INVIO LA "SCELTA" O OPERAZIONE SCELTA DALL'ADMIN
				avviso = input.readUTF();
				System.out.println(avviso);
				if (scelta == 3){
//					System.out.println("LEGGO AVVISO DAL SERVER");
					avviso = input.readUTF();
					System.out.println(avviso);
//					output.writeInt(OK);
					stampaPresenti();
//					System.out.println("INVIO OK AL SERVER");
					output.writeInt(OK); //una volta che il client ha stampato la lista dei presenti/assenti gli mando l'OK
//					System.out.println("OK INVIATO");
				}else if (scelta == 4){
//					System.out.println("LEGGO AVVISO DAL SERVER");
					avviso = input.readUTF();
					System.out.println(avviso);
//					output.writeInt(OK);
					stampaAssenti();
//					System.out.println("INVIO OK AL SERVER");
					output.writeInt(OK); //una volta che il client ha stampato la lista degli assenti gli mando l'OK
//					System.out.println("OK INVIATO");
				}
				
				System.out.println("DISCONNESSIONE...");
			}else{
				System.out.println("Benvenuto Utente :" + idS);
				output.writeBoolean(false);
				int scelta = menu();
				output.writeInt(scelta);
				avviso = input.readUTF();
				System.out.println(avviso);
				System.out.println("DISCONNESSIONE...");

			}
		}

		catch (IOException e1) { 
			System.out.println("Errore nella chiusura dell'input\\output\\socket" + e1.getMessage());
			input.close();
			output.close();
			s.close();
		}
	}
}





