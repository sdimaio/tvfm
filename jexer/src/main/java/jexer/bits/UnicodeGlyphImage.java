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
package jexer.bits;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * UnicodeGlyphImage constructs a single character from the Unicode
 * block-drawing elements ("Symbols For Legacy Computing") from a bitmap
 * image.
 */
public class UnicodeGlyphImage {

    // ------------------------------------------------------------------------
    // Constants --------------------------------------------------------------
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Palette is used to manage the conversion of images between 24-bit RGB
     * color and a palette of paletteSize colors.
     */
    private class Palette {

        /**
         * ColorIdx records a RGB color and its palette index.
         */
        private class ColorIdx {

            /**
             * The 24-bit RGB color.
             */
            public int color;

            /**
             * The population count for this color.
             */
            public int count = 0;

            /**
             * Public constructor.
             *
             * @param color the 24-bit RGB color
             */
            public ColorIdx(final int color, final int index) {
                this.color = color;
                this.count = 0;
            }

            /**
             * Public constructor.  Count is set to 1, index to -1.
             *
             * @param color the 24-bit RGB color
             */
            public ColorIdx(final int color) {
                this.color = color;
                this.count = 1;
            }

            /**
             * Hash only on color.
             *
             * @return the hash
             */
            @Override
            public int hashCode() {
                return color;
            }

            /**
             * Generate a human-readable string for this entry.
             *
             * @return a human-readable string
             */
            @Override
            public String toString() {
                return String.format("color %06x count %d", color, count);
            }
        }

        /**
         * A bucket contains colors that will all be mapped to the same
         * weighted average color value.
         */
        private class Bucket {

            /**
             * The colors in this bucket.
             */
            private ArrayList<ColorIdx> colors;

            /**
             * The palette index for this bucket.  For now this points to a
             * simple average of all the colors, or black if no colors are in
             * this bucket.
             */
            public int index = 0;

            // The minimum and maximum, and "total" component values in this
            // bucket.
            private int minRed   = 0xFF;
            private int maxRed   = 0;
            private int minGreen = 0xFF;
            private int maxGreen = 0;
            private int minBlue  = 0xFF;
            private int maxBlue  = 0;

            // The last computed average() value.
            private int lastAverage = -1;

            /**
             * Public constructor.
             *
             * @param n the expected number of colors that will be in this
             * bucket
             */
            public Bucket(final int n) {
                reset(n);
            }

            /**
             * Reset the stats.
             *
             * @param n the expected number of colors that will be in this
             * bucket
             */
            private void reset(final int n) {
                colors      = new ArrayList<ColorIdx>(n);
                minRed      = 0xFF;
                maxRed      = 0;
                minGreen    = 0xFF;
                maxGreen    = 0;
                minBlue     = 0xFF;
                maxBlue     = 0;
                lastAverage = -1;
                index       = 0;
            }

            /**
             * Get the index associated with all of the colors in this
             * bucket.
             *
             * @return the index
             */
            public int getIndex() {
                return index;
            }

            /**
             * Add a color to the bucket.
             *
             * @param color the color to add
             */
            public void add(final ColorIdx color) {
                colors.add(color);

                int rgb   = color.color;
                int red   = (rgb >>> 16) & 0xFF;
                int green = (rgb >>>  8) & 0xFF;
                int blue  =  rgb         & 0xFF;
                if (red > maxRed) {
                    maxRed = red;
                }
                if (red < minRed) {
                    minRed = red;
                }
                if (green > maxGreen) {
                    maxGreen = green;
                }
                if (green < minGreen) {
                    minGreen = green;
                }
                if (blue > maxBlue) {
                    maxBlue = blue;
                }
                if (blue < minBlue) {
                    minBlue = blue;
                }
            }

            /**
             * Partition this bucket into two buckets, split along the color
             * with the maximum range.
             *
             * @return the other bucket
             */
            public Bucket partition() {
                int redDiff = Math.max(0, (maxRed - minRed));
                int greenDiff = Math.max(0, (maxGreen - minGreen));
                int blueDiff = Math.max(0, (maxBlue - minBlue));

                if ((redDiff > greenDiff) && (redDiff > blueDiff)) {
                    // Partition on red.
                    Collections.sort(colors, new Comparator<ColorIdx>() {
                        public int compare(ColorIdx c1, ColorIdx c2) {
                            int red1 = (c1.color >>> 16) & 0xFF;
                            int red2 = (c2.color >>> 16) & 0xFF;
                            return red1 - red2;
                        }
                    });
                } else if ((greenDiff > blueDiff) && (greenDiff > redDiff)) {
                    // Partition on green.
                    Collections.sort(colors, new Comparator<ColorIdx>() {
                        public int compare(ColorIdx c1, ColorIdx c2) {
                            int green1 = (c1.color >>> 8) & 0xFF;
                            int green2 = (c2.color >>> 8) & 0xFF;
                            return green1 - green2;
                        }
                    });
                } else {
                    // Partition on blue.
                    Collections.sort(colors, new Comparator<ColorIdx>() {
                        public int compare(ColorIdx c1, ColorIdx c2) {
                            int blue1 = c1.color & 0xFF;
                            int blue2 = c2.color & 0xFF;
                            return blue1 - blue2;
                        }
                    });
                }

                int oldN = colors.size();

                List<ColorIdx> newBucketColors;
                newBucketColors = colors.subList(oldN / 2, oldN);
                Bucket newBucket = new Bucket(newBucketColors.size());
                for (ColorIdx color: newBucketColors) {
                    newBucket.add(color);
                }

                List<ColorIdx> newColors;
                newColors = colors.subList(0, oldN - newBucketColors.size());
                reset(newColors.size());
                for (ColorIdx color: newColors) {
                    add(color);
                }
                assert (newBucketColors.size() + newColors.size() == oldN);
                return newBucket;
            }

            /**
             * Average the colors in this bucket.
             *
             * @return an averaged RGB value
             */
            public int average() {
                if (lastAverage != -1) {
                    return lastAverage;
                }

                // Compute the average color.
                long totalRed = 0;
                long totalGreen = 0;
                long totalBlue = 0;
                long count = 0;
                for (ColorIdx color: colors) {
                    int rgb = color.color;
                    int red   = (rgb >>> 16) & 0xFF;
                    int green = (rgb >>>  8) & 0xFF;
                    int blue  =  rgb         & 0xFF;
                    totalRed   += color.count * red;
                    totalGreen += color.count * green;
                    totalBlue  += color.count * blue;
                    count += color.count;
                }
                if (count == 0) {
                    lastAverage = 0xFF000000;
                    return lastAverage;
                }
                totalRed   = (int) (totalRed   / count);
                totalGreen = (int) (totalGreen / count);
                totalBlue  = (int) (totalBlue  / count);

                lastAverage = (int) ((0xFF << 24) | (totalRed   << 16)
                                                  | (totalGreen <<  8)
                                                  |  totalBlue);
                return lastAverage;
            }

            /**
             * Generate a human-readable string for this entry.
             *
             * @return a human-readable string
             */
            @Override
            public String toString() {
                return String.format("bucket %d colors avg RGB %06x index %d",
                    colors.size(), average(), index);
            }
        };

        /**
         * Number of colors in this palette is always 2.
         */
        private final int paletteSize = 2;

        /**
         * Map of colors used in the image by RGB.
         */
        private HashMap<Integer, ColorIdx> colorMap = null;

        /**
         * The image from the constructor.
         */
        private int [] rawImage;

        /**
         * The width of the image.
         */
        private int rawImageWidth;

        /**
         * The width of the image.
         */
        private int rawImageHeight;

        /**
         * The buckets produced by median cut.
         */
        private ArrayList<Bucket> buckets;

        /**
         * The RGB colors of the palette.
         */
        private int [] rgbColors;

        /**
         * Public constructor.
         *
         * @param image a bitmap image
         */
        public Palette(final BufferedImage image) {

            assert (image.getWidth() > 0);
            assert (image.getHeight() > 0);

            int numColors = paletteSize;

            rawImageWidth = image.getWidth();
            rawImageHeight = image.getHeight();
            int totalPixels = rawImageWidth * rawImageHeight;

            int [] rgbArray = image.getRGB(0, 0,
                rawImageWidth, rawImageHeight, null, 0, rawImageWidth);
            rawImage = rgbArray;
            colorMap = new HashMap<Integer, ColorIdx>(rawImageWidth * rawImageHeight);
            for (int i = 0; i < rgbArray.length; i++) {
                int colorRGB = rgbArray[i];
                if ((colorRGB & 0xFF000000) != 0xFF000000) {
                    // Partially-transparent pixels become black.
                    rgbArray[i] = 0xFF000000;
                    colorRGB = 0xFF000000;
                }

                ColorIdx colorIdx = colorMap.get(colorRGB);
                if (colorIdx == null) {
                    colorIdx = new ColorIdx(colorRGB);
                    colorMap.put(colorRGB, colorIdx);
                } else {
                    colorIdx.count++;
                }
            } // for (int i = 0; i < rgbArray.length; i++)

            assert (colorMap.size() > 0);
            medianCut();
        }

        /**
         * Perform median cut algorithm to generate a palette that fits
         * within the palette size.
         */
        public void medianCut() {

            // Populate the "total" bucket.
            Bucket bucket = new Bucket(colorMap.size());
            for (ColorIdx colorIdx: colorMap.values()) {
                bucket.add(colorIdx);

                int rgb = colorIdx.color;
                int red   = (rgb >>> 16) & 0xFF;
                int green = (rgb >>>  8) & 0xFF;
                int blue  =  rgb         & 0xFF;
            }

            int numColors = paletteSize;

            // Find the number of buckets we can have based on the palette
            // size.
            int log2 = 31 - Integer.numberOfLeadingZeros(numColors);
            int totalBuckets = 1 << log2;

            buckets = new ArrayList<Bucket>(totalBuckets);
            buckets.add(bucket);
            while (buckets.size() < totalBuckets) {
                int n = buckets.size();
                for (int i = 0; i < n; i++) {
                    buckets.add(buckets.get(i).partition());
                }
            }
            assert (buckets.size() == totalBuckets);

            // Buckets are partitioned.  Now assign them to the palette.
            int idx = 0;
            rgbColors = new int[buckets.size()];
            for (Bucket b: buckets) {
                int rgb = b.average() | 0xFF000000;
                b.index = idx;
                rgbColors[idx] = rgb;
                idx++;
            }
        }

        /**
         * Search through the palette and find the best RGB match in the
         * palette.
         *
         * @param red the red component
         * @param green the green component
         * @param blue the blue component
         * @return the palette index of the nearest color in RGB space
         */
        private int findNearestColor(final int red, final int green,
            final int blue) {

            int bestDistance = 0xFFFFFF;
            int rgbIdx = -1;
            for (int i = 0; i < rgbColors.length; i++) {
                int rgb = rgbColors[i];
                int red2   = (rgb >>> 16) & 0xFF;
                int green2 = (rgb >>>  8) & 0xFF;
                int blue2  =  rgb         & 0xFF;
                int distance = (red2 - red) * (red2 - red)
                                + (green2 - green) * (green2 - green)
                                + (blue2 - blue) * (blue2 - blue);
                if (rgbIdx < 0) {
                    bestDistance = distance;
                    rgbIdx = 0;
                    continue;
                }

                if (distance < bestDistance) {
                    bestDistance = distance;
                    rgbIdx = i;
                }
            }
            return rgbIdx;
        }

        /**
         * Dither an image to a paletteSize palette.  The dithered image
         * cells will contain indexes into the palette.
         *
         * @return the dithered image rgb data.  Every pixel is an index into
         * the palette.
         */
        public int [] ditherImage() {
            int [] rgbArray = rawImage;

            int height = rawImageHeight;
            int width = rawImageWidth;
            for (int imageY = 0; imageY < height; imageY++) {
                for (int imageX = 0; imageX < width; imageX++) {
                    int oldPixel = rgbArray[imageX + (width * imageY)];
                    int colorIdx = 0;
                    int color = oldPixel;

                    int red   = (color >>> 16) & 0xFF;
                    int green = (color >>>  8) & 0xFF;
                    int blue  =  color         & 0xFF;
                    colorIdx = findNearestColor(red, green, blue);

                    assert (colorIdx >= 0);
                    assert (colorIdx < rgbColors.length);
                    int newPixel = rgbColors[colorIdx];
                    rgbArray[imageX + (width * imageY)] = colorIdx;

                    int oldRed   = (oldPixel >>> 16) & 0xFF;
                    int oldGreen = (oldPixel >>>  8) & 0xFF;
                    int oldBlue  =  oldPixel         & 0xFF;

                    int newRed   = (newPixel >>> 16) & 0xFF;
                    int newGreen = (newPixel >>>  8) & 0xFF;
                    int newBlue  =  newPixel         & 0xFF;

                    int redError   = (  oldRed - newRed)   / 16;
                    int greenError = (oldGreen - newGreen) / 16;
                    int blueError  = ( oldBlue - newBlue)  / 16;

                    if (imageX < rawImageWidth - 1) {
                        int pXpY = rgbArray[imageX + 1 + (width * imageY)];
                        red   = ((pXpY >>> 16) & 0xFF) + (7 * redError);
                        green = ((pXpY >>>  8) & 0xFF) + (7 * greenError);
                        blue  = ( pXpY         & 0xFF) + (7 * blueError);
                        pXpY = (0xFF << 24) | ((red & 0xFF) << 16)
                             | ((green & 0xFF) << 8) | (blue & 0xFF);
                        rgbArray[imageX + 1 + (width * imageY)] = pXpY;
                        if (imageY < rawImageHeight - 1) {
                            int pXpYp = rgbArray[imageX + 1 + (width * (imageY + 1))];
                            red   = ((pXpYp >>> 16) & 0xFF) + redError;
                            green = ((pXpYp >>>  8) & 0xFF) + greenError;
                            blue  = ( pXpYp         & 0xFF) + blueError;
                            pXpYp = (0xFF << 24) | ((red & 0xFF) << 16)
                                  | ((green & 0xFF) << 8) | (blue & 0xFF);
                            rgbArray[imageX + 1 + (width * (imageY + 1))] = pXpYp;
                        }
                    } else if (imageY < rawImageHeight - 1) {
                        int pXmYp = rgbArray[imageX - 1 + (width * (imageY + 1))];
                        int pXYp = rgbArray[imageX + (width * (imageY + 1))];

                        red   = ((pXmYp >>> 16) & 0xFF) + (3 * redError);
                        green = ((pXmYp >>>  8) & 0xFF) + (3 * greenError);
                        blue  = ( pXmYp         & 0xFF) + (3 * blueError);
                        pXmYp = (0xFF << 24) | ((red & 0xFF) << 16)
                              | ((green & 0xFF) << 8) | (blue & 0xFF);
                        rgbArray[imageX - 1 + (width * (imageY + 1))] = pXmYp;

                        red   = ((pXYp >>> 16) & 0xFF) + (5 * redError);
                        green = ((pXYp >>>  8) & 0xFF) + (5 * greenError);
                        blue  = ( pXYp         & 0xFF) + (5 * blueError);
                        pXYp = (0xFF << 24) | ((red & 0xFF) << 16)
                             | ((green & 0xFF) << 8) | (blue & 0xFF);
                        rgbArray[imageX + (width * (imageY + 1))] = pXYp;
                    }
                } // for (int imageY = 0; imageY < height; imageY++)
            } // for (int imageX = 0; imageX < width; imageX++)

            return rgbArray;
        }
    }

    /**
     * The bitmap image this glyph is supposed to represent.
     */
    private BufferedImage image = null;

    /**
     * The reduced palette for the bitmap image.
     */
    private Palette palette = null;

    /**
     * The post-processed image data.
     */
    private int [] rgbArray = null;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor.
     *
     * @param cell a Cell to read image data from
     * @throws IllegalArgumentException if cell does not have an image
     */
    public UnicodeGlyphImage(final Cell cell) throws IllegalArgumentException {
        if (!cell.isImage()) {
            throw new IllegalArgumentException("cell does not have an image");
        }
        this.image = cell.getImage();

        palette = new Palette(image);

        // Dither the image.  We don't bother wrapping it in a BufferedImage.
        rgbArray = palette.ditherImage();

        /*
         * At this point rgbArray contains only 0's and 1's, and
         * palette.rgbColor[0] and palette.rgbColor[1] contain 24-bit ints
         * for background and foreground color, respectively.
         */
    }

    /**
     * Public constructor.
     *
     * @param image the bitmap image
     */
    public UnicodeGlyphImage(final BufferedImage image) {
        this.image = image;
        palette = new Palette(image);

        // Dither the image.  We don't bother wrapping it in a BufferedImage.
        rgbArray = palette.ditherImage();

        /*
         * At this point rgbArray contains only 0's and 1's, and
         * palette.rgbColor[0] and palette.rgbColor[1] contain 24-bit ints
         * for background and foreground color, respectively.
         */
    }

    // ------------------------------------------------------------------------
    // UnicodeGlyphImage ---------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Get the index used to map half blocks and quadrants.
     *
     * @param data the image data as a sequence of 0's and 1's
     * @param width the width of the image
     * @param height the height of the image
     */
    private int getQuadrantMapValue(final int [] data, final int width,
        final int height) {

        /*
         * Map the image area to the portions of a Unicode drawing "canvas"
         * and then literally count how many 1's are in each area: if the
         * count is above 50% total coverage for that area, then it is
         * foreground color.
         *
         * The map of image area to bits:
         *
         *   -----------------------
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *  |    0x01   |    0x02   |
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *   -----------------------
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *  |    0x04   |    0x08   |
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *   -----------------------
         */
        int foregroundMap = 0x00;
        int quadrantSize = height * width / 4 / 2;

        int count = 0;
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width / 2; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > quadrantSize) {
            foregroundMap |= 0x01;
        }

        count = 0;
        for (int y = 0; y < height / 2; y++) {
            for (int x = width / 2; x < width; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > quadrantSize) {
            foregroundMap |= 0x02;
        }

        count = 0;
        for (int y = height / 2; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > quadrantSize) {
            foregroundMap |= 0x04;
        }

        count = 0;
        for (int y = height / 2; y < height; y++) {
            for (int x = width / 2; x < width; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > quadrantSize) {
            foregroundMap |= 0x08;
        }
        return foregroundMap;
    }

    /**
     * Get the index used to map 6-dot Braille and sextants.
     *
     * @param data the image data as a sequence of 0's and 1's
     * @param width the width of the image
     * @param height the height of the image
     */
    private int getSextantMapValue(final int [] data, final int width,
        final int height) {

        /*
         * Map the image area to the portions of a Unicode drawing "canvas"
         * and then literally count how many 1's are in each area: if the
         * count is above 50% total coverage for that area, then it is
         * foreground color.
         *
         * The map of image area to bits:
         *
         *   -----------------------
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *  |    0x01   |    0x08   |
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *   -----------------------
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *  |    0x02   |    0x10   |
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *   -----------------------
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *  |    0x04   |    0x20   |
         *  |           |           |
         *  |           |           |
         *  |           |           |
         *   -----------------------
         */
        int foregroundMap = 0x00;
        int dotSize = height * width / 6 / 2;

        int count = 0;
        for (int y = 0; y < height / 3; y++) {
            for (int x = 0; x < width / 2; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x01;
        }

        count = 0;
        for (int y = 0; y < height / 3; y++) {
            for (int x = width / 2; x < width; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x08;
        }

        count = 0;
        for (int y = height / 3; y < height * 2 / 3; y++) {
            for (int x = 0; x < width / 2; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x02;
        }

        count = 0;
        for (int y = height / 3; y < height * 2 / 3; y++) {
            for (int x = width / 2; x < width; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x10;
        }

        count = 0;
        for (int y = height * 2 / 3; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x04;
        }

        count = 0;
        for (int y = height * 2 / 3; y < height; y++) {
            for (int x = width / 2; x < width; x++) {
                count += data[(y * width) + x];
            }
        }
        if (count > dotSize) {
            foregroundMap |= 0x20;
        }

        return foregroundMap;
    }

    /**
     * Determine the Unicode half-glyph that most closely matches this 2-bit
     * image.
     *
     * @param data the image data as a sequence of 0's and 1's
     * @param width the width of the image
     * @param height the height of the image
     */
    private int findHalfGlyph(final int [] data, final int width,
        final int height) {

        final int [] HALVES = {
            // 0x00 - Empty - only background
            ' ',
            // 0x01 - Upper left quadrant, treat like upper half.
            0x2580,
            // 0x02 - Upper right quadrant, treat like upper half.
            0x2580,
            // 0x03 - Full upper half - 0x2580 - ▀
            0x2580,
            // 0x04 - Bottom left quadrant, treat like bottom half.
            0x2584,
            // 0x05 - Full left half - 0x258c - ▌
            0x258c,
            // 0x06 - Upper right quadrant and lower left quadrant - treat
            // like full foreground block.
            0x2588,
            // 0x07 - Upper half and left half - treat like full foreground
            // block.
            0x2588,
            // 0x08 - Bottom right quadrant, treat like bottom half.
            0x2584,
            // 0x09 - Upper left quadrant and lower right quadrant - treat
            // like full foreground block.
            0x2588,
            // 0x0a - Full right half - 0x2590 - ▐
            0x2590,
            // 0x0b - Upper half and right half - treat like full foreground
            // block.
            0x2588,
            // 0x0c - Full bottom half - 0x2584 - ▄
            0x2584,
            // 0x0d - Bottom half and left half - treat like full foreground
            // block.
            0x2588,
            // 0x0e - Bottom half and right half - treat like full foreground
            // block.
            0x2588,
            // 0x0f - Full foreground block - 0x2588 - █
            0x2588,
        };

        return HALVES[getQuadrantMapValue(data, width, height)];
    }

    /**
     * Determine the Unicode quadrant glyph that most closely matches this
     * 2-bit image.
     *
     * @param data the image data as a sequence of 0's and 1's
     * @param width the width of the image
     * @param height the height of the image
     */
    private int findQuadrantGlyph(final int [] data, final int width,
        final int height) {

        final int [] QUADRANTS = {
            // 0x00 - Empty - only background
            ' ',
            // 0x01 - Upper left quadrant - 0x2598 - ▘
            0x2598,
            // 0x02 - Upper right quadrant - 0x259d - ▝
            0x259d,
            // 0x03 - Full upper half - 0x2580 - ▀
            0x2580,
            // 0x04 - Bottom left quadrant - 0x2596 - ▖
            0x2596,
            // 0x05 - Full left half - 0x258c - ▌
            0x258c,
            // 0x06 - Upper right quadrant and lower left quadrant - 0x259e - ▞
            0x259e,
            // 0x07 - Upper half and left half - 0x259b - ▛
            0x259b,
            // 0x08 - Bottom right quadrant - 0x2597 - ▗
            0x2584,
            // 0x09 - Upper left quadrant and lower right quadrant - 0x259a - ▚
            0x259a,
            // 0x0a - Full right half - 0x2590 - ▐
            0x2590,
            // 0x0b - Upper half and right half - 0x259c - ▜
            0x2588,
            // 0x0c - Full bottom half - 0x2584 - ▄
            0x2584,
            // 0x0d - Bottom half and left half - 0x2599 - ▙
            0x2599,
            // 0x0e - Bottom half and right half - 0x259f - ▟
            0x259f,
            // 0x0f - Full foreground block - 0x2588 - █
            0x2588,
        };
        return QUADRANTS[getQuadrantMapValue(data, width, height)];
    }

    /**
     * Create a single glyph using Unicode half-blocks that best represents
     * this entire image.
     *
     * @return a cell with character, foreground, and background color set
     */
    public Cell toHalfBlockGlyph() {
        Cell cell = null;

        if (false) {

            /*
             * Original fast version: do a median cut, count pixels, pick
             * glyph.
             */
            int ch = findHalfGlyph(rgbArray, image.getWidth(),
                image.getHeight());
            cell = new Cell(ch);
            cell.setBackColorRGB(palette.rgbColors[0]);
            cell.setForeColorRGB(palette.rgbColors[1]);

        } else {

            /*
             * New more accurate version: try left half, top half, and full
             * block, and whichever has the least relative difference to the
             * image is what we return.
             */
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage averageImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
            double rgbStdDev = 0.0;
            int ch = 0x258c;

            // Left half.
            int foreColorRGB = ImageUtils.rgbAverage(image.
                getSubimage(0, 0, width / 2, height));
            int backColorRGB = ImageUtils.rgbAverage(image.
                getSubimage(width / 2, 0, width / 2, height));
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width / 2; x++) {
                    averageImage.setRGB(x, y, foreColorRGB);
                }
                for (int x = width / 2; x < width; x++) {
                    averageImage.setRGB(x, y, backColorRGB);
                }
            }
            rgbStdDev = ImageUtils.rgbStdDev(image, averageImage);

            // Top half
            int newForeColorRGB = ImageUtils.rgbAverage(image.
                getSubimage(0, 0, width, height / 2));
            int newBackColorRGB = ImageUtils.rgbAverage(image.
                getSubimage(0, height / 2, width, height / 2));
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height / 2; y++) {
                    averageImage.setRGB(x, y, newForeColorRGB);
                }
                for (int y = height / 2; y < height; y++) {
                    averageImage.setRGB(x, y, newBackColorRGB);
                }
            }
            double newRgbStdDev = ImageUtils.rgbStdDev(image, averageImage);
            if (newRgbStdDev < rgbStdDev) {
                ch = 0x2580;
                foreColorRGB = newForeColorRGB;
                backColorRGB = newBackColorRGB;
            }

            // Full block
            int newColorRGB = ImageUtils.rgbAverage(image);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    averageImage.setRGB(x, y, newColorRGB);
                }
            }
            newRgbStdDev = ImageUtils.rgbStdDev(image, averageImage);
            if (newRgbStdDev < rgbStdDev) {
                ch = 0x2588;
                foreColorRGB = newColorRGB;
                backColorRGB = newColorRGB;
            }

            // Assemble the result.
            cell = new Cell(ch);
            cell.setBackColorRGB(backColorRGB);
            cell.setForeColorRGB(foreColorRGB);
        }

        return cell;
    }

    /**
     * Create a single glyph using Unicode quadrant-blocks that best
     * represents this entire image.
     *
     * @return a cell with character, foreground, and background color set
     */
    public Cell toQuadrantBlockGlyph() {
        int ch = findQuadrantGlyph(rgbArray, image.getWidth(),
            image.getHeight());
        Cell cell = new Cell(ch);
        cell.setBackColorRGB(palette.rgbColors[0]);
        cell.setForeColorRGB(palette.rgbColors[1]);
        return cell;
    }

    /**
     * Create a single glyph using Unicode 6-dot Braille blocks that best
     * represents this entire image, with one foreground color on a black
     * background.
     *
     * @return a cell with character and foreground color set
     */
    public Cell toSixDotGlyph() {
        int ch = getSextantMapValue(rgbArray, image.getWidth(),
            image.getHeight()) + 0x2800;
        Cell cell = new Cell(ch);

        // We use a single average color as foreground, and leave background
        // black.
        int   red = (((palette.rgbColors[0] >>> 16) & 0xFF)
                  +  ((palette.rgbColors[1] >>> 16) & 0xFF)) / 2;
        int green = (((palette.rgbColors[0] >>>  8) & 0xFF)
                  +  ((palette.rgbColors[1] >>>  8) & 0xFF)) / 2;
        int  blue = (( palette.rgbColors[0]         & 0xFF)
                  +  ( palette.rgbColors[1]         & 0xFF)) / 2;
        int rgb = 0xFF000000 | (red << 16) | (green << 8) | blue;

        cell.setForeColorRGB(rgb);
        return cell;
    }

    /**
     * Create a single glyph using Unicode 6-dot Braille blocks that best
     * represents this entire image.
     *
     * @return a cell with character, foreground, and background color set
     */
    public Cell toSixDotSolidGlyph() {
        int ch = getSextantMapValue(rgbArray, image.getWidth(),
            image.getHeight()) + 0x2800;
        Cell cell = new Cell(ch);
        cell.setBackColorRGB(palette.rgbColors[0]);
        cell.setForeColorRGB(palette.rgbColors[1]);
        return cell;
    }

    /**
     * Create a single glyph using Unicode 6-dot Braille blocks that best
     * represents this entire image.
     *
     * @return a cell with character, foreground, and background color set
     */
    public Cell toSextantBlockGlyph() {
        int ch = getSextantMapValue(rgbArray, image.getWidth(),
            image.getHeight());

        // The sextant map is almost but not quite a straight bit value like
        // Braille.  Empty (' '), left-half (0x258c), right-half (0x2590),
        // and full-block (0x2588) were omitted from the table where they
        // would otherwise be, so we need to add checks for those gaps.
        if (ch == 0) {
            ch = ' ';
        } else if (ch == 0x3F) {
            ch = 0x2588;
        } else if (ch == 0x07) {
            ch = 0x258c;
        } else if (ch == 0x38) {
            ch = 0x2590;
        } else {
            ch += 0x1FAFF;
            if (ch > 0x1fb3b) {
                ch -= 4;
            } else if (ch > 0x1fb27) {
                ch -= 3;
            } else if (ch > 0x1fb13) {
                ch -= 2;
            }
        }
        assert (ch <= 0x1fb3b);

        Cell cell = new Cell(ch);
        cell.setBackColorRGB(palette.rgbColors[0]);
        cell.setForeColorRGB(palette.rgbColors[1]);
        return cell;
    }

}
