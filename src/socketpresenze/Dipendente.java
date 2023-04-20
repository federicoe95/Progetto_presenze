package socketpresenze;


public class Dipendente {
	private int matricola;
	private String password;
	private String ingressouscita;
	
	Dipendente(int matricola, String password){
		this.matricola = matricola;
		this.password = password;
	}

	Dipendente(int matricola, String password, String ingressouscita){
		this.matricola = matricola;
		this.password = password;
		this.ingressouscita = ingressouscita;
	}

	public int getMatricola() {
		return matricola;
	}
	
	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getIngressouscita() {
		return ingressouscita;
	}

	public void setIngressouscita(String ingressouscita) {
		this.ingressouscita = ingressouscita;
	}
	
}
