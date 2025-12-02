# 🏃‍♂️ Gara di Velocità - Versione 5

## Descrizione

`Gara di Velocità V5` è un'applicazione Java che simula una gara tra atleti, con gestione dei thread, anomalie casuali durante la corsa e registrazione della classifica.

Caratteristiche principali:

* Atleti che corrono su thread separati.
* Stati variabili per ogni atleta: normale, rallentato, fermo temporaneamente o ritirato.
* Calcolo del tempo reale con precisione decimale (due cifre).
* Visualizzazione della progressione della gara con barre e emoji.
* Classifica finale e podio dei primi 3, salvato su `podio.txt`.
* Possibilità di usare atleti automatici o inserire manualmente i partecipanti.

---

## Installazione

1. Clona il repository:

```bash
git clone https://github.com/tuo-username/gara-velocita-v5.git
cd gara-velocita-v5
```

2. Compila il progetto:

```bash
javac *.java
```

3. Avvia l'applicazione:

```bash
java GaraAppV5
```

---

## Utilizzo

1. Avvio: il programma ti chiederà di scegliere la lunghezza del percorso: 100m, 200m o personalizzata.

2. Selezione atleti: puoi usare quelli automatici o inserire nomi e velocità manualmente.

3. La gara viene avviata in tempo reale, mostrando per ogni atleta:

   * Nome e numero di maglia
   * Barra di progresso
   * Emoji in base allo stato (`🏃`, `🐢`, `⏸`, `❌`)
   * Distanza percorsa

4. Al termine della gara:

   * Viene stampata la classifica completa.
   * Viene mostrato il podio dei primi 3 atleti.
   * Il podio viene salvato su `podio.txt` con posizione, nome, numero di maglia e tempo di arrivo.

---

## Struttura delle classi

| Classe           | Descrizione                                                                                                           |
| ---------------- | --------------------------------------------------------------------------------------------------------------------- |
| **GaraAppV5**    | Classe principale che gestisce input utente, creazione degli atleti e avvio della gara.                               |
| **GiudiceV5**    | Gestisce i partecipanti, mostra l'avanzamento della gara, stampa classifica e podio e salva il podio su file.         |
| **AtletaV5**     | Estende `Partecipante`, implementa `Runnable`, gestisce il movimento, gli stati casuali e interagisce con il giudice. |
| **Partecipante** | Classe astratta per ogni partecipante, con nome, velocità, distanza percorsa, stato e calcolo del tempo.              |
| **GestoreFile**  | Gestisce la scrittura del podio su file `podio.txt`.                                                                  |

---

## Esempio di output

```text
🎽 Benvenuti alla Gara di Velocità Versione 5 🎽

Scegli il percorso:
1. 100 metri
2. 200 metri
3. Personalizzato
Scelta: 1
Vuoi usare atleti automatici? (s/n): s

🏁 Partenza tra 3...2...1... VIA!

Federico (#1) [█████---------------] 25.00/100.00 🏃
Giorgia (#2) [██-----------------] 8.00/100.00 🐢
...
❌ Atleta Luisa (#4) si ritira!

🏆 ----- Classifica Finale -----
1. Federico (#1) | Tempo: 12.45 s
2. Pietro (#3) | Tempo: 13.02 s
DNF - Luisa (#4)
-------------------------------

🥇 Podio Top 3 🥇
1. Federico (#1) | Tempo: 12.45 s
2. Pietro (#3) | Tempo: 13.02 s
3. Giorgia (#2) | Tempo: 13.80 s
-------------------------------
✅ Podio salvato su file: podio.txt
🎉 Gara terminata! 🎉
```

Vuoi che proceda con quello adesso?
