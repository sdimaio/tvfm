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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * TTYSessionInfo queries environment variables and the tty window size for
 * the session information.  The username is taken from user.name, language
 * is taken from user.language, and text window size from 'stty size'.
 */
public class TTYSessionInfo implements SessionInfo {

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * User name.
     */
    private String username = "";

    /**
     * Language.
     */
    private String language = "";

    /**
     * Text window width.  Default is 80x24 (same as VT100-ish terminals).
     */
    private int windowWidth = 80;

    /**
     * Text window height.  Default is 80x24 (same as VT100-ish terminals).
     */
    private int windowHeight = 24;

    /**
     * Time at which the window size was refreshed.
     */
    private long lastQueryWindowTime;

    /**
     * The time this session was started.
     */
    private long startTime = System.currentTimeMillis();

    /**
     * The number of seconds since the last user input event from this
     * session.
     */
    private int idleTime = Integer.MAX_VALUE;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor.
     */
    @SuppressWarnings("this-escape")
    public TTYSessionInfo() {
        // Populate lang and user from the environment
        username = System.getProperty("user.name");
        language = System.getProperty("user.language");
        queryWindowSize();
    }

    // ------------------------------------------------------------------------
    // SessionInfo ------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Get the time this session was started.
     *
     * @return the number of millis since midnight, January 1, 1970 UTC
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Get the time this session was idle.
     *
     * @return the number of seconds since the last user input event from
     * this session
     */
    public int getIdleTime() {
        return idleTime;
    }

    /**
     * Set the time this session was idle.
     *
     * @param seconds the number of seconds since the last user input event
     * from this session
     */
    public void setIdleTime(final int seconds) {
        idleTime = seconds;
    }

    /**
     * Username getter.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Username setter.
     *
     * @param username the value
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Language getter.
     *
     * @return the language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Language setter.
     *
     * @param language the value
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /**
     * Text window width getter.
     *
     * @return the window width
     */
    public int getWindowWidth() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            // Always use 80x25 for Windows (same as DOS)
            return 80;
        }
        return windowWidth;
    }

    /**
     * Text window height getter.
     *
     * @return the window height
     */
    public int getWindowHeight() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            // Always use 80x25 for Windows (same as DOS)
            return 25;
        }
        return windowHeight;
    }

    /**
     * Re-query the text window size.
     */
    public void queryWindowSize() {
        if (lastQueryWindowTime == 0) {
            lastQueryWindowTime = System.currentTimeMillis();
        } else {
            long nowTime = System.currentTimeMillis();
            if (nowTime - lastQueryWindowTime < 1000) {
                // Don't re-spawn stty if it hasn't been a full second since
                // the last time.
                return;
            }
        }
        if (System.getProperty("os.name").startsWith("Linux")
            || System.getProperty("os.name").startsWith("Mac OS X")
            || System.getProperty("os.name").startsWith("SunOS")
            || System.getProperty("os.name").startsWith("FreeBSD")
        ) {
            // Use stty to get the window size
            sttyWindowSize();
        }
    }

    // ------------------------------------------------------------------------
    // TTYSessionInfo ---------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Call 'stty size' to obtain the tty window size.  windowWidth and
     * windowHeight are set automatically.
     */
    private void sttyWindowSize() {
        String [] cmd = {
            "/bin/sh", "-c", "stty size < /dev/tty"
        };
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = in.readLine();
            if ((line != null) && (line.length() > 0)) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                int rc = Integer.parseInt(tokenizer.nextToken());
                if (rc > 0) {
                    windowHeight = rc;
                }
                rc = Integer.parseInt(tokenizer.nextToken());
                if (rc > 0) {
                    windowWidth = rc;
                }
            }
            while (true) {
                BufferedReader err = new BufferedReader(
                        new InputStreamReader(process.getErrorStream(),
                            "UTF-8"));
                line = err.readLine();
                if ((line != null) && (line.length() > 0)) {
                    System.err.println("Error output from stty: " + line);
                }
                try {
                    process.waitFor();
                    break;
                } catch (InterruptedException e) {
                    // SQUASH
                }
            }
            int rc = process.exitValue();
            if (rc != 0) {
                System.err.println("stty returned error code: " + rc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
