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

/**
 * Jexer - Java Text User Interface library
 *
 * <p>
 * This library is a text-based windowing system loosely reminiscent of
 * Borland's <a href="http://en.wikipedia.org/wiki/Turbo_Vision">Turbo
 * Vision</a> library.  Jexer's goal is to enable people to get up and
 * running with minimum hassle and lots of polish.
 * </p>
 */
module jexer {
    requires java.base;
    requires transitive java.desktop;
    requires lombok;
    requires org.slf4j;

    exports jexer;
    exports jexer.backend;
    exports jexer.bits;
    exports jexer.effect;
    exports jexer.event;
    exports jexer.help;
    exports jexer.io;
    exports jexer.layout;
    exports jexer.menu;
    exports jexer.net;
    exports jexer.tackboard;
    exports jexer.teditor;
    exports jexer.tterminal;
    exports jexer.ttree;
}
