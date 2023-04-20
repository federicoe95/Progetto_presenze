package socketpresenze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Server {

	private static final int ERROR = -1;
	private static final int OK = 0;
//	private static boolean terminaServer = true;
	
	private static Connection con = null;
	private static Statement stmt = null;
	private static Statement stmt1 = null;
	private static Statement stmt2 = null;
	private static Statement stmt3 = null;
	private static Statement stmt4 = null;
	private static Statement stmt5 = null;
	private static Statement stmt6 = null; 
	private static Statement stmt7 = null;
	
	private static Map<Integer,Dipendente> map = new TreeMap<Integer, Dipendente>();
	private static Map<Integer,Dipendente> map1 = new TreeMap<Integer, Dipendente>();
	
	
	private static void apriConnessione() throws ClassNotFoundException, SQLException {		
		//potrei\dovrei passare i parametri della connessione come argomenti ... 
		//istanzio il driver
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//apro la connessione
		//con = DriverManager.getConnection("jdbc:mysql://db.poloaretino.tk:3306/emolo_IS","federicoemolo","Ciao95");
		//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contadb","root","root");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/presenze?characterEncoding=latin1","root",null);
		//imposto il commit automatico a SI (ogni statement effetto la commit)
		con.setAutoCommit(true);
		stmt = con.createStatement();
		stmt1 = con.createStatement();
		stmt2 = con.createStatement();
		stmt3 = con.createStatement();
		stmt4 = con.createStatement();
		stmt5 = con.createStatement();
		stmt6 = con.createStatement();
		stmt7 = con.createStatement();
	}
	
	private static void caricaDB() throws SQLException{
		String insert0 = "INSERT INTO users VALUES (\"9999\", \"pippo1\")";
		String insert1 = "INSERT INTO users VALUES (\"4560\", \"PLUT2O\")";	
		String insert2 = "INSERT INTO users VALUES (\"4561\", \"Plu3tO\")";	
		String insert3 = "INSERT INTO users VALUES (\"4562\", \"P932uuoo98io\")";	
		String insert4 = "INSERT INTO users VALUES (\"4563\", \"Pippo2\")";	
		String insert5 = "INSERT INTO users VALUES (\"4564\", \"Pippo3\")";	
		String insert6 = "INSERT INTO users VALUES (\"4565\", \"Pippo4\")";	
		String insert7 = "INSERT INTO users VALUES (\"4566\", \"Pippo5\")";	
		String insert8 = "INSERT INTO users VALUES (\"4567\", \"Pippo6\")";	
		String insert9 = "INSERT INTO users VALUES (\"4568\", \"Pippo7\")";	
		System.out.println("La insert SQL è questa: " + insert0);
		System.out.println("La insert SQL è questa: " + insert1);
		System.out.println("La insert SQL è questa: " + insert2);
		System.out.println("La insert SQL è questa: " + insert3);
		System.out.println("La insert SQL è questa: " + insert4);
		System.out.println("La insert SQL è questa: " + insert5);
		System.out.println("La insert SQL è questa: " + insert6);
		System.out.println("La insert SQL è questa: " + insert7);
		System.out.println("La insert SQL è questa: " + insert8);
		System.out.println("La insert SQL è questa: " + insert9);
		stmt.executeUpdate(insert0);
		stmt.executeUpdate(insert1);
		stmt.executeUpdate(insert2);
		stmt.executeUpdate(insert3);
		stmt.executeUpdate(insert4);
		stmt.executeUpdate(insert5);
		stmt.executeUpdate(insert6);
		stmt.executeUpdate(insert7);
		stmt.executeUpdate(insert8);
		stmt.executeUpdate(insert9);	
	}
	
	private static void svuotaTabella() throws SQLException {		
		String insert = "truncate table users";		
		stmt.executeUpdate(insert);	
	}

	private static void chiudiConnessione() throws SQLException{
		stmt.close();
		stmt1.close();
		stmt2.close();
		stmt3.close();
		stmt4.close();
		stmt5.close();
		stmt6.close();
		con.close();
	}
	
	private static void caricamentoFile(String percorso) throws IOException{ //carico le credenziali di tutti i dipendenti nel file
		File file = new File(percorso);
		file.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write("9999-pippo1");
		bw.newLine();
		bw.write("4560-PLUT2O");
		bw.newLine();
		bw.write("4561-Plu3tO");
		bw.newLine();
		bw.write("4562-P932uuoo98io");
		bw.newLine();
		bw.write("4563-Pippo2");
		bw.newLine();
		bw.write("4564-Pippo3");
		bw.newLine();
		bw.write("4565-Pippo4");
		bw.newLine();
		bw.write("4566-Pippo5");
		bw.newLine();
		bw.write("4567-Pippo6");
		bw.newLine();
		bw.write("4568-Pippo7");
		bw.newLine();
		bw.flush();
		bw.close();	
	}

	public static void caricaCredenziali(String percorso) throws IOException{ // metodo che crea un file vuoto per ogni utente presente nel file "accounts" 
		File file = new File(percorso);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String riga = br.readLine();
		System.out.println("Leggo le credenziali di accesso");
		while(riga != null){
			String Utente[] = riga.split("-");
			System.out.println("Carico Dati " + Utente[0] + "...");
			//String percorsoDipendenti = "C:\\Registro\\Dipendenti\\" + Utente[0] + ".txt";
			String percorsoDipendenti = ".\\Registro\\Dipendenti\\" + Utente[0] + ".txt";
			File fileDipendente = new File(percorsoDipendenti);
			if (fileDipendente.isFile()){ //se il file esiste non faccio niente
			}else{
				fileDipendente.createNewFile(); //altrimenti lo creo
			}	
			Dipendente dipendente = new Dipendente(Integer.parseInt(Utente[0]),Utente[1]); //matricola,password
			map.put(Integer.parseInt(Utente[0]), dipendente); //caricamente treemap key= matricola; value = OBJ_Dipendente
			riga = br.readLine(); //SCORRO
		}
		br.close();
	}

	public static void stampaTreemap(){  //METODO DI CONTROLLO PER VISUALIZZARE SE LA TREEMAP ERA STATA CARICATA IN MODO CORRETTO
		System.out.println("Inizio stampa TreeMap ->");
		Set<Integer> listaChiavi = map.keySet();
		Iterator<Integer> iter = listaChiavi.iterator();     
		while(iter.hasNext()) {
			Integer key = (Integer)iter.next(); 
			System.out.println("Stampa chiave");
			System.out.println(key + " -> " + map.get(key));
			System.out.println("Stampa matricola");
			System.out.println(map.get(key).getMatricola());
			System.out.println("Stampa password");
			System.out.println(map.get(key).getPassword());
		}
		System.out.println("TREEMAP STAMPATA");
	}

	public static String entrata(int id) throws IOException, SQLException{
		//PER SEGNARE L'ENTRATA DEVO CONTROLLARE CHE L'UTENTE SIA PRIMA USCITO O NON CI SIA NESSUNA OPERAZIONE
		String stato = null;
		int matricola;
		int presenza;

		String sql = "SELECT matricola, presenza FROM registro WHERE matricola =" + " " + id;
		ResultSet rs1 = stmt1.executeQuery(sql);
		if (rs1.next()) {  
			matricola = rs1.getInt("matricola"); 
			presenza = rs1.getInt("presenza");
			if ((presenza == 0)){ //se il flag è diverso da true vuol dire che posso entrare
				String update = "UPDATE registro SET presenza = 1 WHERE matricola =" + " " + id;		
				System.out.println("La insert SQL è questa: " + update);
				stmt4.executeUpdate(update);	
				stato = "ENTRATA EFFETTUATA"; //entrata segnata
			}else{
				stato = "ATTENZIONE PRIMA DI POTER REGISTRARE UN ALTRO ACCESSO ASSICURATI DI ESSERE USCITO!!";//L'UTENTE TOT NON PUO' ENTRARE POICHE' NON E' USCITO
			}
		}
		rs1.close();
		return stato;
	}

	public static String uscita(int id) throws IOException, SQLException{	
		String stato = null;
		int matricola;
		int presenza;

		String sql = "SELECT matricola, presenza FROM registro WHERE matricola =" + " " + id;
		ResultSet rs2 = stmt2.executeQuery(sql);
		if (rs2.next()) { 
			matricola = rs2.getInt("matricola");
			presenza = rs2.getInt("presenza");
			if ((presenza == 1)){ //se il flag è diverso da false vuol dire che posso uscire	
				String update = "UPDATE registro SET presenza = 0 WHERE matricola =" + " " + id;		
				System.out.println("La insert SQL è questa: " + update);
				stmt5.executeUpdate(update);
				stato = "USCITA EFFETTUATA"; //entrata segnata
			}else{
				stato = "ATTENZIONE PRIMA DI POTER REGISTRARE UN'USCITA ASSICURATI DI ESSERE ENTRATO!!";//L'UTENTE TOT NON PUò ENTRARE POICHè NON è USCITO
			}
		}
		rs2.close();
		return stato;
	}

	public static void controllaPresenti(String percorso) throws SQLException{
		String sql = "SELECT matricola from registro WHERE presenza = 1";
		ResultSet rs3 = stmt3.executeQuery(sql);
		while (rs3.next()) {
			System.out.println(rs3.getString("matricola"));
		}
	}
	
	public static void nonPresenti(String percorso){ //LEGGO I FILE DI OGNI DIPENDENTE E VERIFICO SE LA SUA ULTIMA OPERAZIONE è UN'USCITA, IN TAL CASO ESSO NON SARà PRESENTE IN AZIENDA
		try{
			String rigaPred = "";
			File file = new File(percorso);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String riga = br.readLine();
//			System.out.println("Leggo i dati dal file...");
			while(riga != null){
				String Dip[] = riga.split("-");
				System.out.println("Carico Dati di: " + Dip[0] + "...");
				String percorsoDipendenti = "C:\\Registro\\Dipendenti\\" + Dip[0] + ".txt";
				File fileDipendente = new File(percorsoDipendenti);
				if (fileDipendente.isFile()){ //se il file esiste lo devo leggere
					BufferedReader br1 = new BufferedReader(new FileReader(fileDipendente)); 
					String line = ""; //LO LEGGO
					rigaPred = ""; // MI SALVO LA RIGA CHE HO LETTO 
					System.out.println("Leggo il file con le operazioni dell'Utente: " + Dip[0]);
					while(line != null){ //SE LA RIGA è DIVERSA DA NULL (C'è ANCORA ROBA DA LEGGERE NON HO LETTO L'ULTIMA OPERAZIONE)
						rigaPred = line; // MI SALVO LA RIGA CHE HO LETTO (ho l'ultima riga utile NOT NULL)
						line = br1.readLine(); //QUINDI SCORRO FINO ALLA FINE
					}
					br1.close();
				}else{
					//SE IL FILE NON ESISTE VUOL DIRE CHE IL DIPENDENTE NON SI è MAI REGISTRATO
				}
				if (rigaPred.equals("")){
					//FILE VUOTO, NON LO LEGGO E VADO AVANTI
					riga = br.readLine();
				}else{
					String operation[] = rigaPred.split("\\.");
					String presenza = operation[0];
					String pres = presenza;
					Dipendente dipendente = new Dipendente(Integer.parseInt(Dip[0]),"0",pres); //matricola,password,presenza
					if (dipendente.getIngressouscita().equals("false")){ //se 
					map1.put(Integer.parseInt(Dip[0]), dipendente); //key= matricola; value = objDipendente
					}
					riga = br.readLine();
				}	
			}
			br.close();
		}catch (Exception e){
			e.printStackTrace();	
		}
	}

	public static void stampaPresenzeAssenze(String percorsoR) throws IOException{
		System.out.println("STAMPA LISTA");
		Set<Integer> listaChiavi1 = map1.keySet();
		Iterator<Integer> iter1 = listaChiavi1.iterator();
		File file = new File(percorsoR);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		while(iter1.hasNext()) {
			Integer key1 = (Integer)iter1.next(); 
			System.out.println(map1.get(key1).getMatricola());
			if (file.isFile()){
			
			} else {
				file.createNewFile();
			}
			pw.println(map1.get(key1).getMatricola());
			pw.flush();
		}
		pw.close();
	}	

	public static void stampaPresenti() throws SQLException{
		String sql = "SELECT matricola, presenza FROM registro WHERE presenza =" + 1;
		ResultSet rs6 = stmt6.executeQuery(sql);
		while (rs6.next()) {
			System.out.println(rs6.getString("matricola"));	
		}
		rs6.close();
	}

	public static void stampaAssenti() throws SQLException{
		String sql = "SELECT matricola, presenza FROM registro WHERE presenza =" + 0;
		ResultSet rs7 = stmt7.executeQuery(sql);
		while (rs7.next()) {
			System.out.println(rs7.getString("matricola"));	
		}
		rs7.close();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//		while (terminaServer){
		System.out.println("Server Starts");
		System.out.println("Mi connetto al DB");
		apriConnessione();
		System.out.println("Svuota tabella Users");
		svuotaTabella();
		System.out.println("Carico tuple nel DB");
		caricaDB();
		//String percorso = "C:\\Registro\\Credenziali\\accounts.txt";
		//String percorsoR = "C:\\Registro\\registro.txt"; //file che tiene conto del registro dei presenti/assenti
		
		String percorso =  ".\\Registro\\Credenziali\\accounts.txt";
		String percorsoR =  "..\\Registro\\registro.txt";

		DataInputStream is = null;
		DataOutputStream os = null;

		ServerSocket server = null;

		try {
			File file = new File(percorso);

			if (!file.isFile()){
				file.createNewFile();
				System.out.println("Creazione e caricamento Dati nel File... ");
				caricamentoFile(percorso);
				System.out.println("File creato e riempito");
			}
			System.out.println("Carico Credenziali nella Treemap");
			caricaCredenziali(percorso);
//			stampaTreemap();

			server = new ServerSocket(123);
			System.out.println("Attendo che un Client si colleghi");
			Socket socket = server.accept();
			System.out.println("Connessione avvenuta");

			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			
			int contatoreSbagli = 3;
			int idC = 1;
			String pwC = null;

			Boolean presente = false;
			int idT;
			String pwT;
			
			while (contatoreSbagli > 0){
				idC = is.readInt(); //CLIENT INSERITO DA CLIENT
				System.out.println("ID da Client inserito");
				pwC = is.readUTF(); //PW INSERITO DA CLIENT 
				System.out.println("PW da Client inserita");
				String sql = "select matricola, password from users order by matricola asc";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) { 
					idT = rs.getInt(1);
					pwT = rs.getString("password");
					if ((idT == idC) && (pwT.equals(pwC))){//SE IL CLIENT HA MESSO DATI CORRETTI
						presente = true;
						System.out.println("CLIENT ENTRATO!");
					}else{
						//System.out.println("IL CLIENT HA INSERITO ID O PW ERRATI");	
					} 	
				}
				rs.close();
				if (presente == true){
					contatoreSbagli = 0;
					os.writeInt(OK);
					os.writeInt(idC); // mando la matricola al client per il messaggio di benvenuto
					os.flush();
				}else{
					os.writeInt(ERROR);
					os.flush();
					contatoreSbagli--;
				}	
			}	

			String avviso;
			if (presente == true){ //SE L'UTENTE HA DIGITATO CORRETTAMENTE CREDENZIALI ESISTENTI
//				System.out.println("ASPETTO CHE IL CLIENT SCELGA");
				boolean isAdmin = is.readBoolean(); //ASPETTO CHE IL CLIENT MI DICA SE SI E' LOGGATO L'ADMIN O NO; 
				if (isAdmin == true){				//SE SI ASPETTO CHE SCELGA UN'OPERAZIONE TRAMITE IL SUO MENU
					int sceltaA = is.readInt();
					if (sceltaA == 1){
						avviso = entrata(idC);
						System.out.println(avviso);
						os.writeUTF(avviso);	
					}else
						if(sceltaA == 2){
							avviso = uscita(idC);
							System.out.println(avviso);
							os.writeUTF(avviso);
						}else
							if(sceltaA== 3){	
//								controllaPresenti(percorso);
								avviso = "STAMPA LISTA PRESENZE";
								os.writeUTF(avviso);
//								stampaPresenzeAssenze(percorsoR);
								System.out.println(avviso);
								stampaPresenti(); //PROVA DEL METODO DI STAMPA
//								os.writeUTF(avviso);
//								System.out.println("ASPETTO OK DA CLIENT");
								is.readInt();
//								System.out.println("OK RICEVUTO");
//								os.writeUTF(percorsoR); //mando il percorso di registro
							}else
								if(sceltaA == 4){
//									nonPresenti(percorso);
									avviso = "STAMPA LISTA ASSENZE";
//									stampaPresenzeAssenze(percorsoR);
									System.out.println(avviso);
									stampaAssenti();
									os.writeUTF(avviso);
//									os.writeUTF(percorsoR);
								}
				}else{								//SE NON SI è LOGGATO L'ADMIN SARà SICURAMENTE UN DIPENDENTE
					int sceltaC = is.readInt(); //opzioni del client
					if (sceltaC == 1){
						avviso = entrata(idC);
						System.out.println(avviso);
						os.writeUTF(avviso);				
					}else{
						avviso = uscita(idC);
						System.out.println(avviso);
						os.writeUTF(avviso);
					}
				}
			}else{
				System.out.println("IL CLIENT HA DIGITATO ID/PW NON ESISTENTI");
				System.out.println("LA SESSIONE VERRA CHIUSA");
			}
		}catch (IOException ioe){
  			ioe.printStackTrace();
		}finally {
			os.close();
			is.close();
			server.close();
			chiudiConnessione();
			System.out.println("DB chiuso");
			System.out.println("SERVER CHIUSO");
			System.out.println("V: 1.0 by Emolo Federico");
		}
	}
	
}
//}

//java -classpath "C:\Documents and Settings\Administrator\workspace\ProvaItinereEmoloIS\bin" socketpresenze.Client
