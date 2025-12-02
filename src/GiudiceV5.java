import java.util.List;
import java.util.ArrayList;


/**
 * Rappresenta il giudice di una gara di atleti, responsabile della gestione
 * dei partecipanti, del monitoraggio della gara e della stampa dei risultati.
 *
 * Il {@code GiudiceV5} gestisce la registrazione dei partecipanti, mostra
 * l'avanzamento in tempo reale durante la gara, segnala l'arrivo degli atleti
 * e stampa la classifica finale. Inoltre, può salvare il podio su file
 * se la lunghezza del percorso è inferiore o uguale a 100 metri.
 *
 * La classe utilizza sincronizzazione per garantire la corretta gestione
 * dei thread degli atleti durante la gara.
 *
 * @author hasanix
 * @version 5.0
 */
public class GiudiceV5 {


    /** Lista di tutti i partecipanti registrati */
    private final List<Partecipante> partecipanti = new ArrayList<>();


    /** Lista degli atleti arrivati (podio) */
    private final List<AtletaV5> podio = new ArrayList<>();


    /** Lista degli atleti ritirati */
    private final List<AtletaV5> ritirati = new ArrayList<>();


    /** Lock per sincronizzare l'accesso al podio */
    private final Object lockPodio = new Object();


    /** Lunghezza totale del percorso della gara */
    private final double lunghezzaPercorso;


    /** Gestore per la scrittura dei file */
    private final GestoreFile gestoreFile = new GestoreFile();


    /**
     * Crea un giudice per una gara con una lunghezza specificata.
     *
     * @param lunghezzaPercorso Lunghezza del percorso della gara
     */
    public GiudiceV5(double lunghezzaPercorso) {
        this.lunghezzaPercorso = lunghezzaPercorso;
    }


    /**
     * Restituisce la lunghezza del percorso della gara.
     *
     * @return Lunghezza del percorso
     */
    public double getLunghezzaPercorso() { return lunghezzaPercorso; }


    /**
     * Registra un partecipante alla gara.
     *
     * @param p Partecipante da registrare
     */
    public synchronized void registraPartecipante(Partecipante p) { partecipanti.add(p); }


    /**
     * Mostra l'avanzamento di un partecipante lungo il percorso.
     *
     * Viene stampata una barra di progresso con simboli che indicano lo stato
     * del partecipante: NORMALE, RALLENTATO, FERMATO o RITIRATO.
     *
     *
     * @param p Partecipante di cui mostrare l'avanzamento
     */
    public void mostraAvanzamento(Partecipante p) {
        int totalBar = 20;
        int progress = (int)((p.getDistanza()/lunghezzaPercorso)*totalBar);
        if(progress>totalBar) progress=totalBar;
        StringBuilder barra = new StringBuilder();
        for(int i=0;i<progress;i++) barra.append("█");
        for(int i=progress;i<totalBar;i++) barra.append("-");


        String icona;
        switch (p.getStato()) {
            case RITIRATO -> icona = "❌";
            case FERMATO -> icona = "⏸";
            case RALLENTATO -> icona = "🐢";
            default -> icona = "🏃";
        }


        System.out.printf("%s [%s] %.1f/%.1f %s%n", p.getNome(), barra.toString(), p.getDistanza(), lunghezzaPercorso, icona);
    }


    /**
     * Segnala l'arrivo di un partecipante.
     *
     * Aggiunge l'atleta al podio o alla lista dei ritirati, e notifica
     * eventuali thread in attesa della fine della gara.
     *
     *
     * @param p Partecipante che ha raggiunto l'arrivo o si è ritirato
     */
    public void segnalaArrivo(Partecipante p) {
        synchronized(lockPodio) {
            if(p instanceof AtletaV5) {
                AtletaV5 a = (AtletaV5)p;
                if(a.getStato() == Partecipante.Stato.RITIRATO) {
                    ritirati.add(a);
                } else if(!podio.contains(a)) {
                    podio.add(a);
                }
            }
            if(podio.size() + ritirati.size() == partecipanti.size()) {
                lockPodio.notifyAll();
            }
        }
    }


    /**
     * Attende la fine della gara finché tutti i partecipanti non hanno
     * completato il percorso o si sono ritirati.
     */
    private void attendiFineGara() {
        synchronized(lockPodio) {
            while(podio.size() + ritirati.size() < partecipanti.size()) {
                try { lockPodio.wait(); }
                catch(InterruptedException e){ Thread.currentThread().interrupt(); }
            }
        }
    }


    /**
     * Stampa la classifica finale della gara, indicando posizione e tempo
     * degli atleti arrivati, e segnando "DNF" per chi si è ritirato.
     */
    private void stampaClassifica() {
        System.out.println("\n🏆 ----- Classifica Finale -----");
        int i = 1;
        for(AtletaV5 a : podio) {
            System.out.printf("%d. %s | Tempo: %.1f s%n", i++, a.getNome(), a.getTempoArrivo());
        }
        for(AtletaV5 a : ritirati) {
            System.out.printf("DNF - %s%n", a.getNome());
        }
        System.out.println("-------------------------------");
    }


    /**
     * Avvia la gara.
     *
     * Tutti i partecipanti registrati vengono eseguiti in thread separati.
     * Alla fine della gara, stampa la classifica finale e salva il podio su
     * file se la lunghezza del percorso è <= 100 metri.
     *
     */
    public void avviaGara() {
        if(partecipanti.isEmpty()) {
            System.out.println("Nessun atleta registrato.");
            return;
        }


        System.out.println("\n🏁 Partenza tra 3...2...1... VIA!\n");


        List<Thread> threads = new ArrayList<>();
        synchronized(this){
            for(Partecipante p : partecipanti){
                Thread t = new Thread(p);
                threads.add(t);
                t.start();
            }
        }


        attendiFineGara();
        stampaClassifica();


        if(lunghezzaPercorso <= 100){
            gestoreFile.scriviPodio(podio, "podio.txt");
        } else {
            System.out.println("Podio non salvato: percorso > 100 metri.");
        }
    }
}

