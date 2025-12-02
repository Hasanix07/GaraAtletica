import java.util.Random;


/**
 * Rappresenta un atleta partecipante a una gara controllata da un {@link GiudiceV5}.
 *
 * Questa classe estende {@link Partecipante} e implementa il metodo {@link #run()},
 * permettendo all'atleta di eseguire la gara in un thread separato. Durante la gara,
 * l'atleta può subire anomalie casuali che modificano il suo stato:
 *
 *   {@link Partecipante.Stato#NORMALE} - procede normalmente
 *   {@link Partecipante.Stato#RITIRATO} - si ritira dalla gara
 *   {@link Partecipante.Stato#FERMATO} - fermo temporaneamente
 *   {@link Partecipante.Stato#RALLENTATO} - procede a metà velocità
 *
 *
 * L'atleta interagisce con il {@link GiudiceV5} per registrarsi all'inizio della gara,
 * segnalare l'arrivo e mostrare l'avanzamento.
 *
 *
 * @author hasanix
 * @version 5.0
 */
public class AtletaV5 extends Partecipante {


    /** Giudice responsabile della gara */
    private final GiudiceV5 giudice;


    /** Generatore di numeri casuali per simulare anomalie */
    private final Random random = new Random();


    /**
     * Crea un nuovo atleta con nome, velocità base e giudice della gara.
     * Registra automaticamente l'atleta presso il giudice.
     *
     * @param nome Nome dell'atleta
     * @param velocitaBase Velocità base dell'atleta
     * @param giudice Giudice della gara
     */
    public AtletaV5(String nome, double velocitaBase, GiudiceV5 giudice) {
        super(nome, velocitaBase);
        this.giudice = giudice;
        giudice.registraPartecipante(this);
    }


    /**
     * Esegue la gara per l'atleta.
     *
     * L'atleta avanza lungo il percorso fino a raggiungere la distanza obiettivo
     * fornita dal giudice. Ad ogni passo, può subire anomalie casuali che cambiano
     * il suo stato:
     *
     *
     *   RITIRATO: termina la gara e segnala l'arrivo al giudice
     *   FERMATO: pausa temporanea di 2 secondi, poi riprende lo stato NORMALE
     *   RALLENTATO: la velocità è dimezzata per il passo corrente
     *   NORMALE: procede con la velocità base
     *
     * Dopo ogni passo, l'atleta segnala l'avanzamento al giudice. Se l'atleta non
     * si ritira, segnala l'arrivo al termine della gara.
     *
     */
    @Override
    public void run() {
        try {
            double distanzaObiettivo = giudice.getLunghezzaPercorso();


            while(distanza < distanzaObiettivo) {


                // Anomalie casuali
                double r = random.nextDouble();
                if(r < 0.02) stato = Stato.RITIRATO;
                else if(r < 0.04) stato = Stato.FERMATO;
                else if(r < 0.08) stato = Stato.RALLENTATO;
                else stato = Stato.NORMALE;


                if(stato == Stato.RITIRATO){
                    giudice.segnalaArrivo(this);
                    break;
                }
                else if(stato == Stato.FERMATO){
                    Thread.sleep(2000); // fermo per 2 secondi
                    stato = Stato.NORMALE; // riprende normale
                }
                else {
                    muoviti(); // NORMALE o RALLENTATO
                }


                Thread.sleep(1000); // passo successivo
                giudice.mostraAvanzamento(this);
            }


            if(stato != Stato.RITIRATO) giudice.segnalaArrivo(this);


        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

