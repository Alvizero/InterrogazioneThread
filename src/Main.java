import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Biglietteria biglietteria = new Biglietteria();

        ArrayList<String> tratte = new ArrayList<String>(FileManager.caricaSuVettore("tratte.txt"));
        ArrayList<String> nomi = new ArrayList<String>(FileManager.caricaSuVettore("nomi.txt"));

        ServizioBiglietteria servizio = new ServizioBiglietteria(biglietteria, tratte);
        GeneratoreClienti generatore = new GeneratoreClienti(biglietteria, nomi);

        Thread tServizio = new Thread(servizio);
        Thread tGeneratore = new Thread(generatore);

        tServizio.start();
        tGeneratore.start();

        try {
            Thread.sleep(20000); // dopo 20 secondi chiude
            servizio.chiudi();
            generatore.chiudi();
            tServizio.interrupt();
            tGeneratore.interrupt();
            System.out.println("Biglietteria chiusa dopo 20 secondi.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
