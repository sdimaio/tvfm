package jexer.demos;

import jexer.TNumericField;
import jexer.TWidget;
import jexer.TKeypress;
import jexer.TField;
import jexer.bits.CellAttributes;

public class CalcDisplay extends TNumericField {
    private enum CalcState { FIRST, VALID, ERROR }
    private CalcState status;
    private String number;
    private char sign;
    private char operator;
    private double operand;

    public CalcDisplay(TWidget parent, int x, int y, int width) {
        super(parent, x, y, width, false, "0");
        clear();
    }

    public void clear() {
        status = CalcState.FIRST;
        number = "0";
        sign = ' ';
        operator = '=';
        setText(number);
    }

    public void calcKey(char key) {
        key = Character.toUpperCase(key);
        if (status == CalcState.ERROR && key != 'C') return;

        switch (key) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                checkFirst();
                if (number.equals("0")) number = "";
                number += key;
                break;
            case '.':
                checkFirst();
                if (!number.contains(".")) number += ".";
                break;
            case 'C':
                clear();
                break;
            case '+', '-', '*', '/', '=':
                if (status == CalcState.VALID) {
                    status = CalcState.FIRST;
                    double r = getDisplay();
                    switch (operator) {
                        case '+': setDisplay(operand + r); break;
                        case '-': setDisplay(operand - r); break;
                        case '*': setDisplay(operand * r); break;
                        case '/': if (r == 0) error(); else setDisplay(operand / r); break;
                    }
                }
                operator = key;
                operand = getDisplay();
                break;
            default:
                break;
        }
        setText(number);
    }

    private void checkFirst() {
        if (status == CalcState.FIRST) {
            status = CalcState.VALID;
            number = "0";
            sign = ' ';
        }
    }

    private void error() {
        status = CalcState.ERROR;
        number = "Error";
        sign = ' ';
    }

    private void setDisplay(double r) {
        number = String.format("%.10g", r);
        setText(number);
    }

    private double getDisplay() {
        try {
            return Double.parseDouble(sign + number);
        } catch (NumberFormatException e) {
            error();
            return 0;
        }
    }
}
