import java.util.ArrayList;

public class Biglietteria {
    private ArrayList<Cliente> coda = new ArrayList<>();

    public synchronized void entraInCoda(Cliente cliente) {
        coda.add(cliente);
        System.out.println(cliente.getNome() + " è entrato in coda. Persone in coda: " + coda.size());
        notify();
    }

    public synchronized Cliente prossimoCliente() {
        while (coda.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        // prende il primo e lo rimuove
        Cliente prossimo = coda.get(0);
        coda.remove(0);
        return prossimo;
    }

    public synchronized int getNumeroInCoda() {
        return coda.size();
    }
}
