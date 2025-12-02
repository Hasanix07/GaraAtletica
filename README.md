Gara di Velocità – Versione 5

Questo progetto simula una gara di corsa tra più atleti. Ogni atleta corre in un thread separato, può rallentare, fermarsi o ritirarsi, e un giudice coordina la gara e stampa la classifica finale.

Come funziona

Scegli il percorso (100 m, 200 m o personalizzato).

Scegli se usare atleti automatici o inserirli manualmente.

Il giudice avvia la gara.

Gli atleti avanzano, con possibili rallentamenti o ritiri.

Alla fine, il giudice stampa la classifica e salva il podio su file se il percorso è breve.

File principali

Partecipante.java – classe base per ogni atleta

AtletaV5.java – atleta concreto che corre in un thread

GiudiceV5.java – coordina la gara e stampa risultati

GestoreFile.java – salva il podio su file

GaraAppV5.java – classe principale con il main
