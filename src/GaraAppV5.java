import java.util.Random;
import java.util.Scanner;


/**
 * Classe principale dell'applicazione "Gara di Velocità Versione 5".
 *
 * Gestisce l'interazione con l'utente per:
 *   Scegliere la lunghezza del percorso (100m, 200m o personalizzato)
 *   Creare atleti automatici o personalizzati
 *   Avviare la gara tramite {@link GiudiceV5}
 *
 * La gara viene eseguita su thread separati per ciascun atleta, e al termine
 * viene mostrata la classifica finale. Se la lunghezza del percorso è <= 100 metri,
 * il podio viene salvato su file "podio.txt".
 *
 * L'applicazione utilizza input da console tramite {@link Scanner} e
 * genera velocità casuali per gli atleti tramite {@link Random}.
 *
 * @author hasanix
 * @version 5.0
 */
public class GaraAppV5 {


    /**
     * Metodo principale dell'applicazione.
     * Permette all'utente di scegliere la lunghezza del percorso e il tipo di
     * atleti (automatici o personalizzati). Successivamente, crea gli atleti
     * e avvia la gara tramite {@link GiudiceV5}.
     *
     * @param args Argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();


        System.out.println("🎽 Benvenuti alla Gara di Velocità Versione 5 🎽\n");


        System.out.println("Scegli il percorso:");
        System.out.println("1. 100 metri");
        System.out.println("2. 200 metri");
        System.out.println("3. Personalizzato");
        System.out.print("Scelta: ");
        int sceltaPercorso = scanner.nextInt();
        double lunghezza = switch (sceltaPercorso) {
            case 1 -> 100;
            case 2 -> 200;
            default -> {
                System.out.print("Inserisci lunghezza percorso in metri: ");
                yield scanner.nextDouble();
            }
        };


        GiudiceV5 giudice = new GiudiceV5(lunghezza);


        System.out.print("Vuoi usare atleti automatici? (s/n): ");
        String scelta = scanner.next();


        if(scelta.equalsIgnoreCase("s")) {
            String[] nomiAuto = {"federico","giorgia","pietro","luisa","francesco","patty","chadi"};
            for(String n : nomiAuto){
                double vel = 4.5 + random.nextDouble();
                new AtletaV5(n, vel, giudice);
            }
        } else {
            System.out.print("Quanti atleti vuoi inserire? (min 2, max 10): ");
            int n = scanner.nextInt();
            scanner.nextLine();


            for(int i=0;i<n;i++){
                System.out.printf("Nome atleta %d: ", i+1);
                String nome = scanner.nextLine();
                double vel = 4.5 + random.nextDouble();
                new AtletaV5(nome, vel, giudice);
            }
        }


        giudice.avviaGara();
        scanner.close();
        System.out.println("🎉 Gara terminata! 🎉");
    }
}

