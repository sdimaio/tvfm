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

import java.util.List;

import jexer.bits.CellAttributes;
import jexer.event.TInputEvent;

/**
 * This interface provides a screen, keyboard, and mouse to TApplication.  It
 * also exposes session information as gleaned from lower levels of the
 * communication stack.
 */
public interface Backend {

    /**
     * Get a SessionInfo, which exposes text width/height, language,
     * username, and other information from the communication stack.
     *
     * @return the SessionInfo
     */
    public SessionInfo getSessionInfo();

    /**
     * Get a Screen, which displays the text cells to the user.
     *
     * @return the Screen
     */
    public Screen getScreen();

    /**
     * Classes must provide an implementation that syncs the logical screen
     * to the physical device.
     */
    public void flushScreen();

    /**
     * Check if there are events in the queue.
     *
     * @return if true, getEvents() has something to return to the application
     */
    public boolean hasEvents();

    /**
     * Classes must provide an implementation to get keyboard, mouse, and
     * screen resize events.
     *
     * @param queue list to append new events to
     */
    public void getEvents(List<TInputEvent> queue);

    /**
     * Classes must provide an implementation that closes sockets, restores
     * console, etc.
     */
    public void shutdown();

    /**
     * Classes must provide an implementation that sets the window title.
     *
     * @param title the new title
     */
    public void setTitle(final String title);

    /**
     * Set listener to a different Object.
     *
     * @param listener the new listening object that run() wakes up on new
     * input
     */
    public void setListener(final Object listener);

    /**
     * Reload backend options from System properties.
     */
    public void reloadOptions();

    /**
     * Check if backend is read-only.
     *
     * @return true if user input events from the backend are discarded
     */
    public boolean isReadOnly();

    /**
     * Set read-only flag.
     *
     * @param readOnly if true, then input events will be discarded
     */
    public void setReadOnly(final boolean readOnly);

    /**
     * Check if backend will support incomplete image fragments over text
     * display.
     *
     * @return true if images can partially obscure text
     */
    public boolean isImagesOverText();

    /**
     * Check if backend is reporting pixel-based mouse position.
     *
     * @return true if single-pixel mouse movements are reported
     */
    public boolean isPixelMouse();

    /**
     * Set request for backend to report pixel-based mouse position.
     *
     * @param pixelMouse if true, single-pixel mouse movements will be
     * reported, if the backend supports it
     */
    public void setPixelMouse(final boolean pixelMouse);

    /**
     * Set the mouse pointer (cursor) style.
     *
     * @param mouseStyle the pointer style string, one of: "default", "none",
     * "hand", "text", "move", or "crosshair"
     */
    public void setMouseStyle(final String mouseStyle);

    /**
     * Convert a CellAttributes foreground color to an AWT Color.
     *
     * @param attr the text attributes
     * @return the AWT Color
     */
    public java.awt.Color attrToForegroundColor(final CellAttributes attr);

    /**
     * Convert a CellAttributes background color to an AWT Color.
     *
     * @param attr the text attributes
     * @return the AWT Color
     */
    public java.awt.Color attrToBackgroundColor(final CellAttributes attr);

}
