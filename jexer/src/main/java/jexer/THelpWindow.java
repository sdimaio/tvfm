/*
 * Jexer - Java Text User Interface
 *
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Autumn Lamonte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * @author Autumn Lamonte ♥
 * @version 1
 */
package jexer;

import java.util.ResourceBundle;

import jexer.bits.CellAttributes;
import jexer.event.TResizeEvent;
import jexer.help.THelpText;
import jexer.help.Topic;

/**
 * THelpWindow
 */
public class THelpWindow extends TWindow {

    // ------------------------------------------------------------------------
    // Constants --------------------------------------------------------------
    // ------------------------------------------------------------------------

    // Default help topic keys.

    /**
     * "Help On Help".
     */
    public static String HELP_HELP                      = "Help On Help";

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * The help text window.
     */
    private final THelpText helpText;

    /**
     * The "Contents" button.
     */
    private final TButton contentsButton;

    /**
     * The "Index" button.
     */
    private final TButton indexButton;

    /**
     * The "Previous" button.
     */
    private final TButton previousButton;

    /**
     * The "Close" button.
     */
    private final TButton closeButton;

    /**
     * The X position for the buttons.
     */
    private static final int BUTTON_OFFSET = 14;
    private final ResourceBundle i18n;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor.
     *
     * @param application TApplication that manages this window
     * @param topic the topic to start on
     */
    public THelpWindow(final TApplication application, final String topic) {
        this(application, application.helpFile.getTopic(topic));
    }

    /**
     * Public constructor.
     *
     * @param application TApplication that manages this window
     * @param topic the topic to start on
     */
    @SuppressWarnings("this-escape")
    public THelpWindow(final TApplication application, final Topic topic) {
        super(application, "", 1, 1, 78, 22, CENTERED | RESIZABLE);

        i18n = ResourceBundle.getBundle(THelpWindow.class.getName(),
            getLocale());
        setTitle(i18n.getString("windowTitle"));

        setMinimumWindowHeight(16);
        setMinimumWindowWidth(30);

        helpText = new THelpText(this, topic, 1, 1,
            getWidth() - BUTTON_OFFSET - 4, getHeight() - 4);

        setHelpTopic(topic);

        // Buttons
        previousButton = addButton(i18n.getString("previousButton"),
            getWidth() - BUTTON_OFFSET, 4,
            new TAction() {
                public void DO() {
                    if (application.helpTopics.size() > 1) {
                        Topic previous = application.helpTopics.remove(
                            application.helpTopics.size() - 2);
                        application.helpTopics.remove(application.
                            helpTopics.size() - 1);
                        setHelpTopic(previous);
                    }
                }
            });

        contentsButton = addButton(i18n.getString("contentsButton"),
            getWidth() - BUTTON_OFFSET, 6,
            new TAction() {
                public void DO() {
                    setHelpTopic(application.helpFile.getTableOfContents());
                }
            });

        indexButton = addButton(i18n.getString("indexButton"),
            getWidth() - BUTTON_OFFSET, 8,
            new TAction() {
                public void DO() {
                    setHelpTopic(application.helpFile.getIndex());
                }
            });

        closeButton = addButton(i18n.getString("closeButton"),
            getWidth() - BUTTON_OFFSET, 10,
            new TAction() {
                public void DO() {
                    // Don't copy anything, just close the window.
                    THelpWindow.this.close();
                }
            });

        // Save this for last: make the close button default action.
        activate(closeButton);

    }

    /**
     * Public constructor.
     *
     * @param application TApplication that manages this window
     */
    public THelpWindow(final TApplication application) {
        this(application, HELP_HELP);
    }

    // ------------------------------------------------------------------------
    // Event handlers ---------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Handle window/screen resize events.
     *
     * @param event resize event
     */
    @Override
    public void onResize(final TResizeEvent event) {
        if (event.getType() == TResizeEvent.Type.WIDGET) {

            previousButton.setX(getWidth() - BUTTON_OFFSET);
            contentsButton.setX(getWidth() - BUTTON_OFFSET);
            indexButton.setX(getWidth() - BUTTON_OFFSET);
            closeButton.setX(getWidth() - BUTTON_OFFSET);

            helpText.setDimensions(1, 1, getWidth() - BUTTON_OFFSET - 4,
                getHeight() - 4);
            helpText.onResize(new TResizeEvent(event.getBackend(),
                    TResizeEvent.Type.WIDGET, helpText.getWidth(),
                    helpText.getHeight()));

            return;
        } else {
            super.onResize(event);
        }
    }

    // ------------------------------------------------------------------------
    // TWindow ----------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Retrieve the background color.
     *
     * @return the background color
     */
    @Override
    public final CellAttributes getBackground() {
        return getTheme().getColor("thelpwindow.background");
    }

    /**
     * Retrieve the border color.
     *
     * @return the border color
     */
    @Override
    public CellAttributes getBorder() {
        if (inWindowMove) {
            return getTheme().getColor("thelpwindow.windowmove");
        }
        return getTheme().getColor("thelpwindow.background");
    }

    /**
     * Retrieve the color used by the window movement/sizing controls.
     *
     * @return the color used by the zoom box, resize bar, and close box
     */
    @Override
    public CellAttributes getBorderControls() {
        return getTheme().getColor("thelpwindow.border");
    }

    // ------------------------------------------------------------------------
    // THelpWindow ------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Set the topic to display.
     *
     * @param topic the topic to display
     */
    public void setHelpTopic(final String topic) {
        setHelpTopic(getApplication().helpFile.getTopic(topic));
    }

    /**
     * Set the topic to display.
     *
     * @param topic the topic to display
     */
    private void setHelpTopic(final Topic topic) {
        boolean separator = true;
        if ((topic == getApplication().helpFile.getTableOfContents())
            || (topic == getApplication().helpFile.getIndex())
        ) {
            separator = false;
        }

        getApplication().helpTopics.add(topic);
        helpText.setTopic(topic, separator);
    }

}
