package java.similarity.commands;

import java.similarity.resources.Document;
import java.similarity.resources.Resources;
import java.similarity.resources.TfidfCalculator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Razred implementira sucelje {@link ShellCommand}.
 * Reprezentira naredbu za izracunavanje slicnosti dostupnih
 * dokumenata i zadanog upita.
 * @author jelena
 *
 */
public class QueryCommand implements ShellCommand {

	/**
	 * Konstanta, ako broj manji od DELTA, smatramo ga
	 * jednakim 0
	 */
	public static final double DELTA = 1E-8;  
	
	/**
	 * Argumenti trenutnog queryja
	 */
	private List<String> arguments;
	
	/**
	 * Lista u koju spremamo 10, ili manje ako ne postoje,
	 * dokumenata najslicnijih queryju
	 */
	private List<Entry<String, Double>> similars;
	
	/**
	 * Konstruktor razreda
	 */
	public QueryCommand() {
		this.arguments = new ArrayList<String>();
	}
	
	@Override
	public ShellStatus executeCommand(String arguments, Resources res) {
		//za svaki query novi argumenti
		this.arguments = new ArrayList<String>();
		//query = dokument
		Document document =  new Document("query");
		
		String[] args = arguments.split("\\s+");
		//mapa u koju pohranjujemo dokumente i slicnosti
		Map<String, Double> docs = new HashMap<String, Double>();
		for(int i = 0; i < args.length; ++i) {
			String arg = args[i].trim();
			if(res.getVocabulary().contains(arg)) {
				this.arguments.add(arg);
				document.set(res.getVocabulary().getIndexOf(arg));
			}
		}
		
		//nema queryja
		if(arguments.length() == 0) {
			System.out.println("[]");
			return ShellStatus.CONTINUE;
		}
		
		//izracujaj tfidf
		TfidfCalculator calculator = new TfidfCalculator(res);
		ArrayList<Document> doc = new ArrayList<Document>();
		doc.add(document);
		calculator.calculate(doc);
		
		//racunaj slicnosti
		for(int i = 0; i < res.getDocuments().size(); ++i) {
			if(Math.abs((res.getDocuments().get(i).calculateSimilarity(document))) > DELTA) {
				docs.put(res.getDocuments().get(i).getPath(),
						res.getDocuments().get(i).calculateSimilarity(document));
			}
		}
		//sortiraj
		sortSimilar(docs);
		
		printSimilar();
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Metoda sortira elemente mape silazno prema vrijednosti valuea 
	 * pohranjenih za dane kljuceve.
	 * Metoda sortirano popunjava clansku varijablu similar.
	 * @param docs mapa s elementima za sortiranje
	 */
	private void sortSimilar(Map<String, Double> docs) {
		similars = new ArrayList<Map.Entry<String,Double>>(
				docs.entrySet());
		Collections.sort(similars, 
				new Comparator<Entry<String, Double>>() {

					@Override
					public int compare(Entry<String, Double> o1,
							Entry<String, Double> o2) {
						return o2.getValue().compareTo(
								o1.getValue());
					}
				});
	}

	/**
	 * Metoda ispisuje informacije o prvih 10 najslicnijih dokumenata nasem 
	 * queryju. ispisuje se redni broj, iznos izracunate slicnosti, te put do dokumenta.
	 */
	public void printSimilar() {
		int i = 0;
		System.out.println("Query is:" + arguments);
		for(Entry<String,Double> entry : similars) {
			System.out.print("[ " + i + "]"  + "(");
			System.out.printf("%1.4f", entry.getValue());
			System.out.print(") " + entry.getKey() + "\n");
			if(i == 9){
				break;
			}
			++i;
		}
	}

	/**
	 * Metoda vraca rezultate, listu koja sadrzi dokumente 
	 * cija je slicnost s queryjem veca od  0.
	 * @return rezultati queryja
	 */
	public List<Entry<String, Double>> results() {
		return similars;
	}
	
	@Override
	public String toString() {
		return this.arguments.toString();
	}

	@Override
	public String getName() {
		return "query";
	}

}
