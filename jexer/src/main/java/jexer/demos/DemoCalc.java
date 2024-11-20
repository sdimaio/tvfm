package jexer.demos;

import jexer.TApplication;
import jexer.TPanel;
import jexer.TTabbedPane;

import java.io.UnsupportedEncodingException;

public class DemoCalc {
    public static void main(String[] args) throws UnsupportedEncodingException {
        TApplication app = new TApplication(TApplication.BackendType.XTERM);
        app.addFileMenu();
        // new CalculatorWindow(app);
// Crea il TTabbedPane e aggiungilo al desktop dell'applicazione
        TTabbedPane tTabbedPane = new TTabbedPane(app.getDesktop(), 1, 1, 30, 20);

        // Crea i pannelli per le schede con il TTabbedPane come widget padre
        TPanel calculatorPanel = new TPanel(tTabbedPane, 1, 1, 28, 18);
        TPanel anotherPanel = new TPanel(tTabbedPane, 1, 1, 28, 18);

        // Aggiungi contenuto ai pannelli (ad esempio, un campo di testo)
        calculatorPanel.addLabel("This is the Calculator tab.", 1, 1);
        anotherPanel.addLabel("This is another tab.", 1, 1);

        // Aggiungi i pannelli come schede
        tTabbedPane.addTab("Calculator", calculatorPanel);
        tTabbedPane.addTab("Another", anotherPanel);

        // Avvia l'applicazione
        app.run();
    }
}


