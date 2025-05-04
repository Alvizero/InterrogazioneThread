import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

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

    private String generaCodice() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String codice = "";

        for(int i=0; i<6; i++) {
            int indice = (int) (Math.random() * caratteri.length());
            codice += caratteri.charAt(indice);
        }
        return codice;
    }

    private boolean isEsecuzione = true;
    public void run() {
        FileManager.creaCartella(DIRECTORY_PATH); // Verifica anche l'esistenza della cartella
        Random random = new Random();
    
        while(isEsecuzione) {
            Cliente cliente = biglietteria.prossimoCliente();
    
            if (cliente != null) {
                String tratta = tratte.get(random.nextInt(tratte.size()));
                String codice = generaCodice();
                
                // DATA
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
                LocalDateTime dataOra = LocalDateTime.now();
                String dataFormattata = dataOra.format(formatoData);

                System.out.println("Servendo cliente: " + cliente.getNome() + "Tratta: " + tratta + " Codice: " + codice + " Ora: " + dataFormattata  + " Persone in Coda: " + biglietteria.getNumeroInCoda() + "\n");
    
                String dati = cliente.getNome() + ";" + tratta + ";" + dataFormattata  + ";" + codice;
                FileManager.scriviSuFile(FILE_PATH, dati, true);
                
                try {
                    Thread.sleep(1000 + random.nextInt(3000)); // 1-4 sec
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
