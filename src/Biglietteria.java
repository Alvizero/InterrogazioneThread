import java.util.ArrayList;

public class Biglietteria {
    private ArrayList<Cliente> coda = new ArrayList<>(2);

    // Aggiunge il thread (cliente) in coda
    public synchronized void entraInCoda(Cliente cliente) {
        coda.add(cliente);
        System.out.println(cliente.getNome() + " è entrato in coda. Persone in coda: " + coda.size());
        notify(); // Risveglia i thread che sono in attesa
    }

    // Restituisce il cliente piu' avanti in coda
    public synchronized Cliente prossimoCliente() {
        while (coda.isEmpty()) { // Se la coda e' vuota il thread aspetta finche' qualcuno entra
            try {
                wait(); // Se la coda e' vuota il thread si mette in pausa
            } catch (InterruptedException e) {
                return null;
            }
        }

        // Prende il primo cliente della coda e lo rimuove
        Cliente prossimoCliente = coda.get(0);
        coda.remove(0);
        return prossimoCliente;
    }

    // Restituisce il numero di clienti in coda
    public synchronized int getNumeroInCoda() {
        return coda.size();
    }
}
