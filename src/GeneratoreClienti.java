import java.util.ArrayList;
import java.util.Random;

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
        Random random = new Random();

        while (isEsecuzione) {
            int probabilita = random.nextInt(100); // 0-99

            if (probabilita<60) { // 60% di possibilita' per entrare
                String nome = nomi.get(random.nextInt(nomi.size()));
                Cliente cliente = new Cliente(nome, biglietteria);
                Thread t = new Thread(cliente);
                t.start();
            }

            try {
                Thread.sleep(1000 + random.nextInt(2000)); // 1-3 sec
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void chiudi() {
        isEsecuzione = false;
    }
}
