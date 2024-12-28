package jexer.demos;

import jexer.TApplication;
import jexer.TDesktop;
import jexer.TWindow;
import jexer.bits.CellAttributes;

public class MultilineStringExample extends TWindow {

    public MultilineStringExample(final TApplication application) {
        super(application, "Multiline String Example", 0, 0, 40, 15);
    }

    @Override
    public void draw() {
        // Ottieni gli attributi normali per il testo dal tema
        CellAttributes normal = getTheme().getColor("ttext");
        // Testo multilinea da disegnare
        String text = "Hello, Jexer!\nThis is a new line.\nAnd another one!";
        // Disegna il testo multilinea
        drawMultilineString(2, 2, text, normal);
    }

    /**
     * Disegna una stringa multilinea sullo schermo.
     *
     * @param x          La coordinata orizzontale iniziale.
     * @param y          La coordinata verticale iniziale.
     * @param text       La stringa da disegnare (contenente newline `\n`).
     * @param attributes Gli attributi di cella da usare.
     */
    public void drawMultilineString(int x, int y, String text, CellAttributes attributes) {
        // Dividi la stringa in righe utilizzando '\n' come separatore
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            putStringXY(x, y + i, lines[i], attributes); // Stampa ogni riga
        }
    }

    public static void main(String[] args) throws Exception {
        // Avvia l'applicazione con un backend Swing
        TApplication app = new TApplication(TApplication.BackendType.XTERM);
        app.addFileMenu();
        // Aggiungi una finestra MultilineStringExample
        MultilineStringExample multilineExample = new MultilineStringExample(app);

        //MultilineStringExample(app));
        // Esegui l'applicazione

        app.run();
    }
}
