import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> tratte = new ArrayList<>(FileManager.caricaSuVettore("File/tratte.txt"));
        ArrayList<String> nomi = new ArrayList<>(FileManager.caricaSuVettore("File/nomi.txt"));

        Biglietteria biglietteria = new Biglietteria();
        ServizioBiglietteria servizio = new ServizioBiglietteria(biglietteria, tratte); // Thread che da il servizio di vendita dei biglietti
        GeneratoreClienti generatore = new GeneratoreClienti(biglietteria, nomi); // Thread che genera i clienti con nomi casuali

        try {
            Thread.sleep(20000); // dopo 20 secondi la biglietteria chiude
            servizio.chiudi();
            generatore.chiudi();
            System.out.println("Biglietteria chiusa.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
