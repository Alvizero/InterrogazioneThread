import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> tratte = new ArrayList<>(FileManager.caricaSuVettore("File/tratte.txt"));
        ArrayList<String> nomi = new ArrayList<>(FileManager.caricaSuVettore("File/nomi.txt"));

        Biglietteria biglietteria = new Biglietteria();
        ServizioBiglietteria servizio = new ServizioBiglietteria(biglietteria, tratte);
        GeneratoreClienti generatore = new GeneratoreClienti(biglietteria, nomi);

        try {
            Thread.sleep(20000); // dopo 20 secondi chiude
            servizio.chiudi();
            generatore.chiudi();
            System.out.println("Biglietteria chiusa dopo 20 secondi.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
