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
package jexer.backend;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import jexer.bits.CellAttributes;

/**
 * This class uses an xterm/ANSI X3.64/ECMA-48 type terminal to provide a
 * screen, keyboard, and mouse to TApplication.
 */
public class ECMA48Backend extends GenericBackend {

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor will use System.in and System.out and UTF-8
     * encoding. On non-Windows systems System.in will be put in raw mode;
     * shutdown() will (blindly!) put System.in in cooked mode.
     *
     * @throws UnsupportedEncodingException if an exception is thrown when
     * creating the InputStreamReader
     */
    public ECMA48Backend() throws UnsupportedEncodingException {
        this(null, null, null);
    }

    /**
     * Public constructor.
     *
     * @param listener the object this backend needs to wake up when new
     * input comes in
     * @param input an InputStream connected to the remote user, or null for
     * System.in.  If System.in is used, then on non-Windows systems it will
     * be put in raw mode; shutdown() will (blindly!) put System.in in cooked
     * mode.  input is always converted to a Reader with UTF-8 encoding.
     * @param output an OutputStream connected to the remote user, or null
     * for System.out.  output is always converted to a Writer with UTF-8
     * encoding.
     * @param windowWidth the number of text columns to start with
     * @param windowHeight the number of text rows to start with
     * @param fontSize the size in points.  ECMA48 cannot set it, but it is
     * here to match the Swing API.
     * @throws UnsupportedEncodingException if an exception is thrown when
     * creating the InputStreamReader
     */
    @SuppressWarnings("this-escape")
    public ECMA48Backend(final Object listener, final InputStream input,
        final OutputStream output, final int windowWidth,
        final int windowHeight, final int fontSize)
        throws UnsupportedEncodingException {

        // Create a terminal and explicitly set stdin into raw mode
        terminal = new ECMA48Terminal(this, listener, input, output,
            windowWidth, windowHeight);
        ((ECMA48Terminal) terminal).setBackend(this);

        // Keep the terminal's sessionInfo so that TApplication can see it
        sessionInfo = ((ECMA48Terminal) terminal).getSessionInfo();

        // ECMA48Terminal is the screen too
        screen = (ECMA48Terminal) terminal;
    }

    /**
     * Public constructor.
     *
     * @param listener the object this backend needs to wake up when new
     * input comes in
     * @param input an InputStream connected to the remote user, or null for
     * System.in.  If System.in is used, then on non-Windows systems it will
     * be put in raw mode; shutdown() will (blindly!) put System.in in cooked
     * mode.  input is always converted to a Reader with UTF-8 encoding.
     * @param output an OutputStream connected to the remote user, or null
     * for System.out.  output is always converted to a Writer with UTF-8
     * encoding.
     * @throws UnsupportedEncodingException if an exception is thrown when
     * creating the InputStreamReader
     */
    public ECMA48Backend(final Object listener, final InputStream input,
        final OutputStream output) throws UnsupportedEncodingException {

        this(listener, input, output, false);
    }

    /**
     * Public constructor.
     *
     * @param listener the object this backend needs to wake up when new
     * input comes in
     * @param input an InputStream connected to the remote user, or null for
     * System.in.  If System.in is used, then on non-Windows systems it will
     * be put in raw mode; shutdown() will (blindly!) put System.in in cooked
     * mode.  input is always converted to a Reader with UTF-8 encoding.
     * @param output an OutputStream connected to the remote user, or null
     * for System.out.  output is always converted to a Writer with UTF-8
     * encoding.
     * @param readOnly if true, start this backend as read-only
     * @throws UnsupportedEncodingException if an exception is thrown when
     * creating the InputStreamReader
     */
    @SuppressWarnings("this-escape")
    public ECMA48Backend(final Object listener, final InputStream input,
        final OutputStream output,
        final boolean readOnly) throws UnsupportedEncodingException {

        this.readOnly = readOnly;

        // Create a terminal and explicitly set stdin into raw mode
        terminal = new ECMA48Terminal(this, listener, input, output);
        ((ECMA48Terminal) terminal).setBackend(this);

        // Keep the terminal's sessionInfo so that TApplication can see it
        sessionInfo = ((ECMA48Terminal) terminal).getSessionInfo();

        // ECMA48Terminal is the screen too
        screen = (ECMA48Terminal) terminal;
    }

    /**
     * Public constructor.
     *
     * @param listener the object this backend needs to wake up when new
     * input comes in
     * @param input the InputStream underlying 'reader'.  Its available()
     * method is used to determine if reader.read() will block or not.
     * @param reader a Reader connected to the remote user.
     * @param writer a PrintWriter connected to the remote user.
     * @param setRawMode if true, set System.in into raw mode with stty.
     * This should in general not be used.  It is here solely for Demo3,
     * which uses System.in.
     * @throws IllegalArgumentException if input, reader, or writer are null.
     */
    @SuppressWarnings("this-escape")
    public ECMA48Backend(final Object listener, final InputStream input,
        final Reader reader, final PrintWriter writer,
        final boolean setRawMode) {

        // Create a terminal and explicitly set stdin into raw mode
        terminal = new ECMA48Terminal(this, listener, input, reader, writer,
            setRawMode);
        ((ECMA48Terminal) terminal).setBackend(this);

        // Keep the terminal's sessionInfo so that TApplication can see it
        sessionInfo = ((ECMA48Terminal) terminal).getSessionInfo();

        // ECMA48Terminal is the screen too
        screen = (ECMA48Terminal) terminal;
    }

    /**
     * Public constructor.
     *
     * @param listener the object this backend needs to wake up when new
     * input comes in
     * @param input the InputStream underlying 'reader'.  Its available()
     * method is used to determine if reader.read() will block or not.
     * @param reader a Reader connected to the remote user.
     * @param writer a PrintWriter connected to the remote user.
     * @throws IllegalArgumentException if input, reader, or writer are null.
     */
    public ECMA48Backend(final Object listener, final InputStream input,
        final Reader reader, final PrintWriter writer) {

        this(listener, input, reader, writer, false);
    }

    /**
     * Check if backend will support incomplete image fragments over text
     * display.
     *
     * @return true if images can partially obscure text
     */
    @Override
    public boolean isImagesOverText() {
        return ((ECMA48Terminal) terminal).isImagesOverText();
    }

    /**
     * Check if backend is reporting pixel-based mouse position.
     *
     * @return true if single-pixel mouse movements are reported
     */
    @Override
    public boolean isPixelMouse() {
        return ((ECMA48Terminal) terminal).isPixelMouse();
    }

    /**
     * Set request for backend to report pixel-based mouse position.
     *
     * @param pixelMouse if true, single-pixel mouse movements will be
     * reported, if the backend supports it
     */
    @Override
    public void setPixelMouse(final boolean pixelMouse) {
        if (pixelMouse != ((ECMA48Terminal) terminal).isPixelMouse()) {
            ((ECMA48Terminal) terminal).setPixelMouse(pixelMouse);
        }
    }

    /**
     * Set the mouse pointer (cursor) style.
     *
     * @param mouseStyle the pointer style string, one of: "default", "none",
     * "hand", "text", "move", or "crosshair"
     */
    @Override
    public void setMouseStyle(final String mouseStyle) {
        ((ECMA48Terminal) terminal).setMouseStyle(mouseStyle);
    }

    /**
     * Convert a CellAttributes foreground color to an AWT Color.
     *
     * @param attr the text attributes
     * @return the AWT Color
     */
    public java.awt.Color attrToForegroundColor(final CellAttributes attr) {
        return ((ECMA48Terminal) terminal).attrToForegroundColor(attr);
    }

    /**
     * Convert a CellAttributes background color to an AWT Color.
     *
     * @param attr the text attributes
     * @return the AWT Color
     */
    public java.awt.Color attrToBackgroundColor(final CellAttributes attr) {
        return ((ECMA48Terminal) terminal).attrToBackgroundColor(attr);
    }

}
