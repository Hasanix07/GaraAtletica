/**
 * Rappresenta un partecipante generico a una gara o competizione.
 * Questa classe è astratta e implementa {@link Runnable}, quindi ogni
 * partecipante può essere eseguito in un thread separato.
 *
 * <p>Ogni partecipante ha un nome, una velocità base, una distanza percorsa
 * e un tempo di arrivo. Lo stato del partecipante può cambiare durante la
 * gara.
 *
 * Gli stati possibili sono definiti nell'enum {@link Stato}:
 *
 *   {@link Stato#NORMALE} - partecipa normalmente
 *   {@link Stato#RITIRATO} - si è ritirato dalla gara
 *   {@link Stato#FERMATO} - è temporaneamente fermo
 *   {@link Stato#RALLENTATO} - procede a metà velocità
 *
 *
 * @author hasanix
 * @version 5.0
 */
public abstract class Partecipante implements Runnable {


    /** Nome del partecipante */
    protected final String nome;


    /** Velocità base del partecipante */
    protected final double velocitaBase;


    /** Distanza percorsa finora dal partecipante */
    protected double distanza = 0.0;


    /** Tempo di arrivo accumulato durante la gara */
    protected double tempoArrivo = 0.0;


    /** Stato corrente del partecipante */
    protected Stato stato = Stato.NORMALE;


    /**
     * Enum che rappresenta i possibili stati di un partecipante durante la gara.
     */
    public enum Stato { NORMALE, RITIRATO, FERMATO, RALLENTATO }


    /**
     * Crea un nuovo partecipante con nome e velocità specificati.
     *
     * @param nome Nome del partecipante
     * @param velocitaBase Velocità base del partecipante
     */
    public Partecipante(String nome, double velocitaBase) {
        this.nome = nome;
        this.velocitaBase = velocitaBase;
    }


    /**
     * Restituisce il nome del partecipante.
     *
     * @return Nome del partecipante
     */
    public String getNome() { return nome; }


    /**
     * Restituisce la distanza percorsa finora dal partecipante.
     *
     * @return Distanza percorsa
     */
    public double getDistanza() { return distanza; }


    /**
     * Restituisce il tempo accumulato fino a questo punto della gara.
     *
     * @return Tempo di arrivo
     */
    public double getTempoArrivo() { return tempoArrivo; }


    /**
     * Restituisce lo stato corrente del partecipante.
     *
     * @return Stato corrente
     */
    public Stato getStato() { return stato; }


    /**
     * Imposta lo stato del partecipante.
     *
     * @param s Nuovo stato del partecipante
     */
    public void setStato(Stato s) { stato = s; }


    /**
     * Aggiorna la distanza percorsa dal partecipante in base alla sua
     * velocità base e allo stato corrente.
     *
     * Se lo stato è {@link Stato#RALLENTATO}, la velocità è dimezzata.
     * Aggiorna anche il tempo di arrivo di 1 unità.
     *
     */
    protected void muoviti() {
        double incremento = velocitaBase;
        if(stato == Stato.RALLENTATO) incremento *= 0.5;
        distanza += incremento;
        tempoArrivo += 1.0;
    }


    /**
     * Restituisce una rappresentazione testuale del partecipante,
     * indicando il nome, la distanza percorsa e lo stato corrente.
     *
     * @return Rappresentazione testuale del partecipante
     */
    @Override
    public String toString() {
        return String.format("%s - distanza: %.2f, stato: %s", nome, distanza, stato);
    }
}

