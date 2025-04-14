public class Cliente implements Runnable {
    private String nome;
    private Biglietteria biglietteria;

    public Cliente(String nome, Biglietteria biglietteria) {
        this.nome = nome;
        this.biglietteria = biglietteria;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void run() {
        biglietteria.entraInCoda(this);
    }
}
