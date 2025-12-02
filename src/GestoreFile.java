import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


/**
 * Classe responsabile della gestione della scrittura su file dei dati della gara.
 *
 * In particolare, permette di salvare su file il podio finale degli atleti,
 * includendo la posizione e il tempo di arrivo di ciascun partecipante.
 *
 *
 * @author hasanix
 * @version 5.0
 */
public class GestoreFile {


    /**
     * Salva su un file di testo la classifica finale (podio) della gara.
     *
     * Ogni riga del file contiene la posizione, il nome dell'atleta e il tempo di arrivo.
     * In caso di errore durante la scrittura, viene stampato un messaggio di errore
     * sulla console.
     *
     *
     * @param podio Lista degli atleti ordinati per posizione finale
     * @param nomeFile Nome del file su cui scrivere il podio
     */
    public void scriviPodio(List<AtletaV5> podio, String nomeFile) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(nomeFile))) {
            int i = 1;
            for(AtletaV5 a : podio){
                pw.printf("%d. %s | Tempo: %.1f s%n", i++, a.getNome(), a.getTempoArrivo());
            }
            System.out.println("✅ Podio salvato su file: " + nomeFile);
        } catch(IOException e) {
            System.err.println("Errore scrittura podio: " + e.getMessage());
        }
    }
}

