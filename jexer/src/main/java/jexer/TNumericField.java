package jexer;

import jexer.bits.CellAttributes;
import jexer.bits.StringUtils;
import jexer.event.TKeypressEvent;

/**
 * TNumericField implements a text field that only accepts numeric input
 * and automatically formats the numbers.
 *
 */
public class TNumericField extends TField {

    /**
     * Public constructor.
     *
     * @param parent the parent widget
     * @param x the column relative to the parent
     * @param y the row relative to the parent
     * @param width the visible width of the text field
     * @param fixed if true, the text cannot exceed the display width
     */
    public TNumericField(final TWidget parent, final int x, final int y,
                         final int width, final boolean fixed) {
        this(parent, x, y, width, fixed, "");
    }

    /**
     * Public constructor with initial text.
     *
     * @param parent the parent widget
     * @param x the column relative to the parent
     * @param y the row relative to the parent
     * @param width the visible width of the text field
     * @param fixed if true, the text cannot exceed the display width
     * @param text the initial text
     */
    public TNumericField(final TWidget parent, final int x, final int y,
                         final int width, final boolean fixed, final String text) {
        super(parent, x, y, width, fixed, text, null, null);
    }

    /**
     * Overrides the onKeypress method to filter only numeric input.
     *
     * @param keypress the keypress event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        int ch = keypress.getKey().getChar();

        // Allow only digits, decimal point, and minus sign
        if (Character.isDigit(ch) || ch == '.' || ch == '-') {
            // Extra check: allow the minus sign only at the beginning
            if (ch == '-' && position != 0) {
                return;
            }

            // Allow only one decimal point
            if (ch == '.' && text.contains(".")) {
                return;
            }

            // If the character is valid, call the parent method
            super.onKeypress(keypress);
        }
        // Ignore any other input
    }

    /**
     * Draws the numeric field, aligning text to the right and formatting the number.
     */
    @Override
    public void draw() {
        // Determine the color based on the active state
        CellAttributes fieldColor = (isAbsoluteActive()) ?
                getTheme().getColor("tfield.active") :
                getTheme().getColor("tfield.inactive");

        // Format the numeric text
        String formattedText = formatNumber(text);

        // Right-align the text
        int padding = getWidth() - StringUtils.width(formattedText);
        hLineXY(0, 0, getWidth(), backgroundChar, fieldColor);
        putStringXY(padding, 0, formattedText, fieldColor);

        // Update the cursor position
        updateCursor();
    }

    /**
     * Formats the text as a number with thousand separators.
     *
     * @param text the text to format
     * @return the formatted text
     */
    private String formatNumber(String text) {
        try {
            double value = Double.parseDouble(text);
            return String.format("%,f", value).trim();
        } catch (final NumberFormatException e) {
            return String.format("Error parsing value from string %s", text).trim();
        }
    }
}
