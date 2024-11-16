# Turbo Vision File Manager Reimmaginato - TVFM Java Edition

## Descrizione

Turbo Vision File Manager (TVFM) è un'applicazione storica originariamente sviluppata nel 1992 utilizzando Borland Pascal 7.0 e la libreria Turbo Vision. Era progettata per offrire agli utenti un'interfaccia testuale intuitiva e potente per gestire file e directory nel sistema operativo DOS. Ispirato ai file manager testuali come Norton Commander, TVFM sfruttava il sofisticato framework Turbo Vision, noto per il suo aspetto "a finestre" e l'approccio modulare.

## Revival in Java

Questo progetto mira a reimmaginare e riscrivere TVFM utilizzando **Jexer**, una libreria Java che replica l'interfaccia di Turbo Vision. La versione moderna di TVFM includerà le seguenti funzionalità:

- **Interfaccia Testuale Avanzata:** Utilizzando Jexer, riprodurremo l'interfaccia utente a finestre di Turbo Vision, mantenendo il look retrò e l'interazione basata sulla tastiera.
- **Compatibilità Multi-Piattaforma:** La nuova versione sarà scritta in Java, permettendo l'esecuzione su vari sistemi operativi, inclusi Linux (anche senza interfaccia grafica), macOS e Windows.
- **Gestione Completa dei File:** Supporto per navigare nel file system, copiare, spostare, rinominare e eliminare file, mantenendo l'esperienza utente dell'applicazione originale.
- **Estensibilità e Open Source:** Il progetto è modulare e open source, invitando contributi per nuove funzionalità e miglioramenti.
- **Supporto per Integrazione con Kubernetes (Futuro):** Una potenziale estensione futura per integrare l'API Client Kubernetes, offrendo un'interfaccia di gestione testuale per cluster Kubernetes, anche in ambienti privi di GUI.

## Storia e Contesto

TVFM è stato inizialmente creato nei primi anni '90 come demo per mostrare le capacità di Turbo Vision, un framework che ha stabilito uno standard per lo sviluppo di interfacce utente testuali. L'applicazione è stata progettata per essere utilizzata in ambienti DOS e dimostrava un eccellente design orientato agli oggetti, tipico del linguaggio Object Pascal.

Con il passare del tempo e la fine dell'era DOS, Turbo Vision è stato gradualmente abbandonato. Tuttavia, la sua estetica e funzionalità continuano a essere apprezzate, e progetti come Jexer mantengono vivo il suo spirito.

## Obiettivi del Progetto

1. **Migrazione e Modernizzazione:** Trasformare il codice originale in Object Pascal in Java, preservando l'interfaccia e le funzionalità, ma aggiornando l'architettura per il contesto moderno.
2. **Compatibilità e Accessibilità:** Creare un'applicazione che possa funzionare in ambienti server o terminali senza GUI, mantenendo l'aspetto e la sensazione classica.
3. **Open Source e Community:** Incoraggiare la partecipazione della community, accogliendo contributi e miglioramenti da parte degli sviluppatori.

## Come Contribuire

- **Clona il Repository:** Scarica il codice e prova l'applicazione nella tua console Java.
- **Segnalazione di Problemi:** Segnala bug, suggerisci miglioramenti e condividi le tue idee.
- **Pull Request:** Accettiamo contributi con nuove funzionalità, refactoring o miglioramenti delle prestazioni.

## Tecnologie Utilizzate

- **Java 17+**
- **Libreria Jexer TUI**
- **Maven/Gradle per la Gestione del Progetto**
- **GitHub Actions per l'Integrazione Continua (CI)**
- **API Client Kubernetes (Futuro)**

---

