import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServizioBiglietteria implements Runnable {
    private static final String DIRECTORY_PATH = "File";
    private static final String FILE_PATH = DIRECTORY_PATH + "/biglietti.csv";

    private Biglietteria biglietteria;
    private ArrayList<String> tratte = new ArrayList<>();
    
    public ServizioBiglietteria(Biglietteria biglietteria, ArrayList<String> tratte) {
        this.biglietteria = biglietteria;
        this.tratte = tratte;
        new Thread(this).start();
    }

    // Genera un codice alfanumerico casuale per il biglietto
    private String generaCodice() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String codice = "";

        for(int i=0; i<6; i++) { // Genera un codice lungo 6 caratteri
            int indice = (int) (Math.random() * caratteri.length()); // Prende un carattere casuale tra quelli presenti nella stringa (caratteri)
            codice += caratteri.charAt(indice); // Aggiunge il carattere "generato" alla stringa del codice
        }
        
        return codice;
    }

    private boolean isEsecuzione = true;
    public void run() {
        FileManager.creaCartella(DIRECTORY_PATH); // Verifica che la cartella esista, altrimenti la crea
        
        while(isEsecuzione) {
            Cliente cliente = biglietteria.prossimoCliente(); // Prende il cliente piu' avanti in coda
    
            if (cliente != null) {
                String tratta = tratte.get((int) (Math.random() * tratte.size())); // Prende una tratta casuale dal vettore tratte ovvero la lista di tutte le tratte
                String codice = generaCodice();
                
                // DATA
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss"); // Formato in cui deve essere formattata la data
                LocalDateTime dataOra = LocalDateTime.now();
                String dataFormattata = dataOra.format(formatoData); 

                System.out.println("Servendo cliente: " + cliente.getNome() + " Tratta: " + tratta + " Codice: " + codice + " Ora: " + dataFormattata  + " Persone in Coda: " + biglietteria.getNumeroInCoda() + "\n");
    
                // Scrive le informazioni del biglietto nel file
                String dati = cliente.getNome() + ";" + tratta + ";" + dataFormattata  + ";" + codice;
                FileManager.scriviSuFile(FILE_PATH, dati, true);
                
                try {
                    Thread.sleep(1000 + (int) (Math.random() * 3000)); // il cliente ci mette 1-4 secondi per essere servito
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void chiudi() {
        isEsecuzione = false;
    }
}
