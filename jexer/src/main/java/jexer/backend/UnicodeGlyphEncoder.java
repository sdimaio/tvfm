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

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

import jexer.bits.Cell;
import jexer.bits.ImageUtils;
import jexer.bits.UnicodeGlyphImage;

/**
 * UnicodeGlyphEncoder turns a BufferedImage into single character from the
 * Unicode block-drawing elements ("Symbols For Legacy Computing").
 */
public class UnicodeGlyphEncoder {

    // ------------------------------------------------------------------------
    // Constants --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Available sets of glyphs to convert to.
     */
    public enum GlyphSet {

        /**
         * Converted to solid space (' ') character blocks using the text
         * cell's "average" color.
         */
        BLOCKS,

        /**
         * Converted to Unicode half-block glyphs.
         */
        HALVES,

        /**
         * Converted to Unicode sextant glyphs.
         */
        SEXTANTS,

        /**
         * Converted to Unicode quadrant-block glyphs.
         */
        QUADRANTS,

        /**
         * Converted to Unicode 6-dot Braille glyphs using the text cell's
         * "average" color.
         */
        SIXDOT,

        /**
         * Converted to Unicode 6-dot Braille glyphs with
         * foreground/background color.
         */
        SIXDOTSOLID,
    }

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Selected conversion method.
     */
    private GlyphSet glyphSet = GlyphSet.HALVES;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor.
     */
    @SuppressWarnings("this-escape")
    public UnicodeGlyphEncoder() {
        reloadOptions();
    }

    // ------------------------------------------------------------------------
    // UnicodeGlyphEncoder ---------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Reload options from System properties.
     */
    public void reloadOptions() {
        String str = System.getProperty("jexer.ECMA48.imageFallbackDisplayMode",
            "halves").toLowerCase();

        if (str.equals("solid")) {
            glyphSet = GlyphSet.BLOCKS;
        }
        if (str.equals("halves")) {
            glyphSet = GlyphSet.HALVES;
        }
        if (str.equals("sextants")) {
            glyphSet = GlyphSet.SEXTANTS;
        }
        if (str.equals("quadrants")) {
            glyphSet = GlyphSet.QUADRANTS;
        }
        if (str.equals("6dot")) {
            glyphSet = GlyphSet.SIXDOT;
        }
        if (str.equals("6dotsolid")) {
            glyphSet = GlyphSet.SIXDOTSOLID;
        }
    }

    /**
     * Create a T.416 RGB parameter sequence for a single RGB color.
     *
     * @param sb StringBuilder to append result to
     * @param colorRGB a 24-bit RGB value for foreground color
     * @param foreground if true, this is a foreground color
     * @return the string to emit to an ANSI / ECMA-style terminal,
     * e.g. "\033[42m"
     */
    private void colorRGB(final StringBuilder sb,
        final int colorRGB, final boolean foreground) {

        int colorRed     = (colorRGB >>> 16) & 0xFF;
        int colorGreen   = (colorRGB >>>  8) & 0xFF;
        int colorBlue    =  colorRGB         & 0xFF;

        if (foreground) {
            sb.append("\033[38;2;");
        } else {
            sb.append("\033[48;2;");
        }
        sb.append(String.format("%d;%d;%dm", colorRed, colorGreen, colorBlue));
    }

    /**
     * Create a string representing a bitmap.
     *
     * @param bitmap the bitmap data
     * @param textWidthPixels text cell width in pixels
     * @param textHeightPixels text cell height in pixels
     * @return the string to emit to an ANSI / ECMA-style terminal
     */
    public String toUnicodeGlyph(final BufferedImage bitmap,
        final int textWidthPixels, final int textHeightPixels) {

        StringBuilder sb = new StringBuilder(24);

        assert (bitmap != null);

        int cellColumns = bitmap.getWidth() / textWidthPixels;
        while (cellColumns * textWidthPixels < bitmap.getWidth()) {
            cellColumns++;
        }
        int cellRows = bitmap.getHeight() / textHeightPixels;
        while (cellRows * textHeightPixels < bitmap.getHeight()) {
            cellRows++;
        }

        Cell [][] cells = new Cell[cellColumns][cellRows];
        for (int x = 0; x < cellColumns; x++) {
            for (int y = 0; y < cellRows; y++) {
                int width = textWidthPixels;
                if ((x + 1) * textWidthPixels > bitmap.getWidth()) {
                    width = bitmap.getWidth() - (x * textWidthPixels);
                }
                int height = textHeightPixels;
                if ((y + 1) * textHeightPixels > bitmap.getHeight()) {
                    height = bitmap.getHeight() - (y * textHeightPixels);
                }

                Cell cell = new Cell();
                BufferedImage imageSlice = bitmap.getSubimage(x * textWidthPixels,
                    y * textHeightPixels, width, height);

                BufferedImage newImage;
                newImage = new BufferedImage(textWidthPixels, textHeightPixels,
                    BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics gr = newImage.getGraphics();
                gr.setColor(java.awt.Color.BLACK);
                gr.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
                gr.drawImage(imageSlice, 0, 0, null, null);
                gr.dispose();
                cell.setImage(newImage);
                cells[x][y] = cell;
            }
        }

        for (int y = 0; y < cellRows; y++) {
            for (int x = 0; x < cellColumns; x++) {
                Cell cell = cells[x][y];
                UnicodeGlyphImage unicode = null;
                unicode = new UnicodeGlyphImage(cell.getImage());
                switch (glyphSet) {
                case BLOCKS:
                    int rgb = ImageUtils.rgbAverage(cell.getImage());
                    cell.setChar(' ');
                    cell.setForeColorRGB(rgb);
                    cell.setBackColorRGB(rgb);
                    break;
                case HALVES:
                    cell.setTo(unicode.toHalfBlockGlyph());
                    break;
                case SEXTANTS:
                    cell.setTo(unicode.toSextantBlockGlyph());
                    break;
                case QUADRANTS:
                    cell.setTo(unicode.toQuadrantBlockGlyph());
                    break;
                case SIXDOT:
                    cell.setTo(unicode.toSixDotGlyph());
                    cell.setBackColorRGB(0);
                    break;
                case SIXDOTSOLID:
                    cell.setTo(unicode.toSixDotSolidGlyph());
                    break;
                }
                colorRGB(sb, cell.getBackColorRGB(), false);
                colorRGB(sb, cell.getForeColorRGB(), true);
                sb.append(Character.toChars(cell.getChar()));
            }
            if (y < cellRows - 1) {
                sb.append("\033[m\n");
            }
        }
        return sb.toString();
    }

    /**
     * Convert all filenames to sixel.
     *
     * @param args[] the filenames to read
     */
    public static void main(final String [] args) {
        if (args.length < 3) {
            System.err.println("USAGE: java jexer.backend.UnicodeGlyphEncoder { cell width } { cell height } { file1 [ file2 ... ] }");
            System.exit(-1);
        }

        UnicodeGlyphEncoder encoder = new UnicodeGlyphEncoder();
        int successCount = 0;
        int cellWidth = 10;
        int cellHeight = 20;

        try {
            cellWidth = Integer.parseInt(args[0]);
            cellHeight = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        for (int i = 2; i < args.length; i++) {
            try {
                BufferedImage image = ImageIO.read(new FileInputStream(args[i]));
                int count = 1;
                for (int j = 0; j < count; j++) {
                    // Emit the image.
                    System.out.println(encoder.toUnicodeGlyph(image,
                            cellWidth, cellHeight));
                } // for (int j = 0; j < count; j++)
            } catch (Exception e) {
                System.err.println("Error reading file:");
                e.printStackTrace();
            }

        } // for (int i = 0; i < args.length; i++)

        if (successCount == args.length) {
            System.exit(0);
        } else {
            System.exit(successCount);
        }
    }

}
