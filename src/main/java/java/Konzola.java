package java;

import java.trazilica.shell.Session;
import java.trazilica.shell.environment.SearchEnvironment;

/**
 * Glavni program za testiranje slicnosti dokumenata s zadanim querijem.
 * Interakcija s korisnikom vrsi se putem konzole.
 * Na raspolaganju su naredbe query-argumenti su lista rijeci za koje zelimo naci dokumente najslicnije njima.
 *									 primjer: "query sport je zabavan"
 *							  results-ispisuje rezultate dobivene naredbom query(poslijednjom u nizu)
 *							  type i-ispisuje sadrzaj i-tog po redu najslicnijeg dokumenta queriju
 *							  exit-izlaz iz programa
 * @author jelena
 *
 */
public class Konzola {
	
	/**
	 * Metoda se poziva prilikom pokretanja programa.
	 * @param args argumenti komandne linije;
	 * 		put do mape s dokumentima koristenim u programu
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Unesite samo put do mape sa člancima");
		}
		
		Session session = new Session(args[0], new SearchEnvironment());
		
		session.doWork();
	}
}
