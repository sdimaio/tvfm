Jexer Image Sequence
====================



Summary
-------

Jexer is a text-based windowing toolkit with widgets for many common
functions such as lists, comboboxes, text fields, radioboxes, and so
on.  Jexer also supports image data in its cells, and uses this for a
variety of functions: images, sixel support in its terminal widget,
and additional font support.

The most widely supported image format for ECMA48-type terminals (DEC
VT series, xterm, other terminal emulators) is sixel, a palette-based
format with a typical limit of 256-1024 colors.  Sixel encoding is
expensive in both CPU and bandwidth, and a single color palette does
not generalize well to all kinds of images.  Jexer therefore desires a
24-bit color image format to overcome these limitations.

A group including developers of several widely-used terminal emulators
has been working on defining the needs and limitations for a standard
that can be implemented in current-generation terminal emulators.  The
discussion has been primarily captured here:
https://gitlab.freedesktop.org/terminal-wg/specifications/issues/12 .
As of this time of writing, no clear solution has emerged from that
discussion, nor even agreement on what kind of data structure an image
truly represents.

This document outlines Jexer's current solution.  If a real standard
emerges in the future, Jexer will switch to that as its preferred
method.



Design Goals
------------

Jexer's primary goal as a project is to expose the capabilities of
stock xterm to applications.  If a way to achieve its aims exists in
xterm's existing sequences, Jexer will use that.  But since xterm does
not provide a non-palettized 24-bit image format, Jexer does something
new.  However, this design is intended to align most closely with the
functions xterm already implements.  Therefore:

* Once the image has been decoded into a bitmap, the bitmap is placed
  on screen exactly as already done for sixel, and the cursor is moved
  in a way that is already supported by sixel.

* A way is provided for an application to put pixel data on screen
  without requiring a third-party image library.  This is to support
  libraries like ncurses.

* No new flags are added to the state machine.

* Announcement of this feature implies only this single feature, not
  an indication that the terminal has an assortment of multiple
  non-xterm extensions.

With the goals above, it is hoped that this protocol (or one informed
by it) could feasibly be adopted by xterm itself and ncurses.



Performance
-----------

In researching this area, one interesting surprise discovery was that
the CPU overhead for encoding/decoding image data to wire format had a
much smaller effect on total throughout versus collecting the image
data from the DCS/OSC sequence in the ECMA48 state parser.  In other
words, wire formats with fewer bytes to transfer won out strongly over
image formats with fewer CPU cycles to compute.  It was later found
that Jexer had an O(n^2) performance hit computing the termination of
the OSC sequence; fixing that eliminated most of the performance gap
between the image formats.  However, terminals written in other
languages have also experienced severe performance issues with long
DCS/OSC sequences, and had to make significant changes to their state
parser to handle the long sequences.  Because of these results,
Jexer's default image format for its ECMA48 backend for a couple years
was JPG.  Now it is PNG due to the significant visual artifacts of
JPG.



Detection
---------

Jexer detects terminal support for this protocol via the Primary
Device Attributes (DA) / DECID (ESC Z, or 0x9A) sequence.  A terminal
that supports this feature will respond by including the parameter
"444" for images.  A recap of the parameters xterm supports is listed
below, with the Jexer images feature response included:

| VT220 (and higher) Response | Description                                |
|-----------------------------|--------------------------------------------|
| 1                           | 132-columns                                |
| 2                           | Printer                                    |
| 3                           | ReGIS graphics                             |
| 4                           | Sixel graphics                             |
| 6                           | Selective erase                            |
| 8                           | User-defined keys                          |
| 9                           | National Replacement Character sets        |
| 1 5                         | Technical characters                       |
| 1 6                         | Locator port                               |
| 1 7                         | Terminal state interrogation               |
| 1 8                         | User windows                               |
| 2 1                         | Horizontal scrolling                       |
| 2 2                         | ANSI color, e.g., VT525                    |
| 2 8                         | Rectangular editing                        |
| 2 9                         | ANSI text locator (i.e., DEC Locator mode) |
| 4 4 4                       | Jexer Images                               |



Wire Protocol
-------------

The following new sequences are used to transfer image data from
Jexer's ECMA48 backend to the terminal:

| Sequence                                   | Description                 |
|--------------------------------------------|-----------------------------|
| OSC 4 4 4 ; 0 ; Pw ; Ph ; Ps ; {data} BEL  | Display RGB image at cursor |
| OSC 4 4 4 ; 0 ; Pw ; Ph ; Ps ; {data} ST   | Display RGB image at cursor |
| OSC 4 4 4 ; 1 ; Ps ; {data} BEL            | Display PNG image at cursor |
| OSC 4 4 4 ; 1 ; Ps ; {data} ST             | Display PNG image at cursor |
| OSC 4 4 4 ; 2 ; Ps ; {data} BEL            | Display JPG image at cursor |
| OSC 4 4 4 ; 2 ; Ps ; {data} ST             | Display JPG image at cursor |



For the OSC 4 4 4 sequence:

* Pw is the width in pixels.  Valid range is 1 to 10000.

* Ph is the height in pixels.  Valid range is 1 to 10000.

* Ps is the scrolling option.  Valid values are 1 (scroll) or 0 (no scroll).

* The image is processed as shown below:

  - The pixels are drawn starting at the upper-left corner of the text
    cursor position.

  - All pixels in the text cells that are not covered by the image
    itself are set to black (RGB 0/0/0).

  - Pixels that would be drawn to the right of the visible region on
    screen are discarded.

  - If Ps is 1, then:

    a. The screen is scrolled up if the image overflows into the
       bottom text row.

    b. The cursor's final position is on the same column as the
       starting cursor position, and on the row immediately below the
       image.

  - If Ps is 0, then:

    a. The screen is never scrolled.

    b. Pixels that would be drawn below the visible region on screen
       are discarded.

    c. The cursor's final position is at the same column and row as
       the starting cursor position, i.e. the cursor does not move at
       all.

* If the first parameter is 0, then {data} is a base64-encoded string
  of big-endian 8-bit red/blue/green values.  Every 3 octets denotes
  one pixel.  Data must contain a total of Pw * Ph * 3 octets.

* If the first parameter is 1, then {data} is a base64-encoded string
  of a PNG file.

* If the first parameter is 2, then {data} is a base64-encoded string
  of a JPG file.

* If any field is invalid, then nothing is drawn and the cursor is not
  moved.



Implementation Notes
--------------------

Jexer's terminal widget is capable of decoding any image format
supported by javax.image.ImageIO, but scans the file data for PNG and
JPG headers before attempting decoding.  If the header does not match
the stated image type, then it is discarded.  At this time ImageIO
supports the following formats: PNG, JPG, BMP, WBMP, and GIF.

Additional image formats may be supported in the future.  An
application will always be able to detect image format support via
this sequence:

  1. Place the cursor at the top-left position on screen (1, 1).

  2. Draw a small image with scrolling enabled (Ps = 1).

  3. Check cursor position via DSR 6 (CSI 6 n).  If the cursor has
     moved, then the image format is supported, and the image is on
     screen.

Jexer's ECMA48 backend always defines scroll=0, i.e. no scrolling.

Jexer's terminal widget will discard (and not scroll) any PNG/JPG
images that exceed 10000 pixels in width or height.


References
----------

* [Xterm control sequences](https://invisible-island.net/xterm/ctlseqs/ctlseqs.html)

* [VT510 Device Attribute responses](https://vt100.net/docs/vt510-rm/DA1.html)