import java.util.ArrayList;

public class GeneratoreClienti implements Runnable {
    private Biglietteria biglietteria;
    private ArrayList<String> nomi = new ArrayList<String>();

    public GeneratoreClienti(Biglietteria biglietteria, ArrayList<String> nomi) {
        this.biglietteria = biglietteria;
        this.nomi = nomi;
        new Thread(this).start();
    }

    private boolean isEsecuzione = true;
    public void run() {
        while (isEsecuzione) {
            int probabilita = (int) (Math.random() * 101); // Genera una probabilita' casuale tra 0-100

            if (probabilita<60) { // 60% di possibilita' per entrare
                String nome = nomi.get((int) (Math.random() * nomi.size())); // Prende un nome casuale dal vettore nomi ovvero la lista di tutti i nomi
                Cliente cliente = new Cliente(nome, biglietteria); // Crea un nuovo cliente con il nome generato 
                Thread t = new Thread(cliente); // Crea un nuovo Thread (cliente)
                t.start(); // Avvia il nuovo Thread (cliente)
            }

            try {
                Thread.sleep(1000 + (int) (Math.random() * 2000)); // Genera un cliente ogni 1-3 secondi
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void chiudi() {
        isEsecuzione = false;
    }
}
