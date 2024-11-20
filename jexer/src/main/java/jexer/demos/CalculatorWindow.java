package jexer.demos;

import jexer.TAction;
import jexer.TApplication;
import jexer.TWindow;
import jexer.TButton;
import jexer.event.TKeypressEvent;
import jexer.TKeypress;
import jexer.event.TCommandEvent;

public class CalculatorWindow extends TWindow {
    private CalcDisplay display;

    public CalculatorWindow(TApplication app) {
        super(app, "Calculator", 28, 16);
        display = new CalcDisplay(this, 2, 2, 21);
        addButton("C", 2, 4);
        addButton("<-", 8, 4);
        addButton("%", 14, 4);
        addButton("±", 20, 4);

        addButton("7", 2, 6);
        addButton("8", 8, 6);
        addButton("9", 14, 6);
        addButton("/", 20, 6);

        addButton("4", 2, 8);
        addButton("5", 8, 8);
        addButton("6", 14, 8);
        addButton("*", 20, 8);

        addButton("1", 2, 10);
        addButton("2", 8, 10);
        addButton("3", 14, 10);
        addButton("-", 20, 10);

        addButton("0", 2, 12);
        addButton(".", 8, 12);
        addButton("=", 14, 12);
        addButton("+", 20, 12);
    }

    private void addButton(String title, int x, int y) {
        // TButton button = super.addButton(title, x, y, new TCommandEvent(TKeypress.kbEnter));
        // button.setAction(() -> display.calcKey(title.charAt(0)));

        super.addButton(title, x, y,
                new TAction() {
                    public void DO() {
                        display.calcKey(title.charAt(0));
                    }
                }
        );
    }
}
