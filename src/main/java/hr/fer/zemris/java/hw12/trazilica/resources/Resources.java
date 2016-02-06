package hr.fer.zemris.java.hw12.trazilica.resources;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Razred sluzi za pohranu resursa koji se koriste u programu;
 * Sadrzi informacije o {@link Vocabulary}, {@link StopWords},
 * {@link Document}; dostupnim dokumentima za racunanje slicnosti.
 * @author jelena
 *
 */
public class Resources {

	/**
	 * Instanca {@link StopWords}, rijeci koje su 
	 * stop-rijeci u ovom programu
	 */
	private StopWords stopWords;
	
	/**
	 * Rijecnik koristen u programu
	 */
	private Vocabulary vocabulary;
	
	/**
	 * Dokumenti koristeni u programi
	 */
	private List<Document> documents;
	
	/**
	 * Put do mape koja sadrzi dokumente 
	 */
	private String pathDocuments;
	
	/**
	 * Konstruktor razreda.
	 * Inicijalizira resurse-stopwords, vokabular i dokumente.
	 * @param pathStop put do dokumenta koji sadrzi stopwords
	 * @param pathDocuments put do mape s dokumentima
	 * @throws Exception kod problema s citanjem iz fileova
	 */
	public Resources(String pathStop, String pathDocuments) throws Exception {
		this.stopWords = new StopWords(pathStop);
		this.vocabulary = new Vocabulary();
		this.documents = new ArrayList<Document>();
		this.pathDocuments = pathDocuments;
		intitResources();
	}
	
	/**
	 * Metoda vraca dostupne dokumente
	 * @return lista dokumenata
	 */
	public List<Document> getDocuments() {
		return documents;
	}
	
	/**
	 * Metoda sluzi za inicijalizaciju vokabulara, stopwords i
	 * dokumenata. Za dokumente pohranjujemo sve potrebne informacije.
	 */
	private void intitResources() {
		try {
			Files.walkFileTree(Paths.get(pathDocuments), new ReadFilesVisitor());
			TfidfCalculator calculator = new TfidfCalculator(this);
			calculator.calculate(documents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Razred implementira sucelje {@link FileVisitor}.
	 * U slucaju nailaska na file, otvara ga, cita iz njega te
	 * inicijalizira dokumente te vokabular.
	 * @author jelena
	 *
	 */
	private class ReadFilesVisitor implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			BufferedReader br = new BufferedReader(
					 new InputStreamReader(
					 new BufferedInputStream(
					 new FileInputStream(file.toFile())),"UTF-8"));
			Document document = new Document(file.toString());
			String line;
			while((line = br.readLine()) != null) {
				List<String> words = parse(line.trim().toLowerCase());
				//dodaj rijeci iz linije
				addToVocabulary(words);
				addToDocument(words, document);
			}
			br.close();
			documents.add(document);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Metoda parsea rijeci iz proslijedenog stringa
		 * gdje rijeci=kontinuirani nizovi alphabetica.
		 * @param line string iz kojeg parseamo rijeci
		 * @return lista dobivenih rijeci
		 */
		private List<String> parse(String line) {
			List<String> words = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < line.length(); ++i) {
				if(Character.isLetter(line.charAt(i))) {
					sb.append(line.charAt(i));
				}else {
					if(sb.length() != 0) {
						words.add(sb.toString());
						sb.setLength(0);
					}
				}
			}
			return words;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

	/**
	 * Metoda dodaje rijeci u vokabular, ako one nisu
	 * elementi stopwords.
	 * @param line string iz kojeg vadimo rijeci
	 */
	public void addToVocabulary(List<String> line) {
		for(String word : line) {
			if(stopWords.isStopWord(word) == false) {
				vocabulary.addWord(word);
			}
		}
	}

	/**
	 * Metoda dodaje rijeci u dokument ako su one sadrzane u rijecniku.
	 * @param line string iz kojeg citamo rijeci
	 * @param document instanca {@link Document} u koju dodajemo rijeci
	 */
	public void addToDocument(List<String> line, Document document) {
		for(String word : line) {
			if(vocabulary.getIndexOf(word) > -1) {
				document.set(vocabulary.getIndexOf(word));
			}
		}
	}
	
	/**
	 * Metoda vraca {@link Vocabulary} koristen u
	 * programu.
	 * @return rijecnik koristenih rijeci
	 */
	public Vocabulary getVocabulary() {
		return vocabulary;
	}
}
