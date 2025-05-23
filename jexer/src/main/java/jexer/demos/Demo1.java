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
package jexer.demos;

import jexer.TApplication;

/**
 * This class is the main driver for a simple demonstration of Jexer's
 * capabilities.
 */
public class Demo1 {

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Default constructor.
     */
    public Demo1() {}

    // ------------------------------------------------------------------------
    // Demo1 ------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Main entry point.
     *
     * @param args Command line arguments
     */
    public static void main(final String [] args) {
        try {
            // Swing is the default backend on Windows unless explicitly
            // overridden by jexer.Swing.
            TApplication.BackendType backendType = TApplication.BackendType.XTERM;
            if (System.getProperty("os.name").startsWith("Windows")) {
                backendType = TApplication.BackendType.SWING;
            }
            if (System.getProperty("os.name").startsWith("Mac")) {
                backendType = TApplication.BackendType.SWING;
            }
            if (System.getProperty("jexer.Swing") != null) {
                if (System.getProperty("jexer.Swing", "false").equals("true")) {
                    backendType = TApplication.BackendType.SWING;
                } else {
                    backendType = TApplication.BackendType.XTERM;
                }
            }
            DemoApplication app;
            if (backendType == TApplication.BackendType.SWING) {
                int fontSize = 20;
                try {
                    fontSize = Integer.parseInt(
                        System.getProperty("jexer.Swing.fontSize", "20"));
                    // Keep requested font size between 16 and 32 pt.
                    fontSize = Math.min(32, Math.max(16, fontSize));
                } catch (NumberFormatException e) {
                    // SQUASH
                }
                app = new DemoApplication(backendType, 90, 30, fontSize);
            } else {
                app = new DemoApplication(backendType);
            }
            (new Thread(app)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
