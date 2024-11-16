System Properties
=================

This document outlines the system properties that can be set to
customize Jexer.  The table below summarizes the properties and
default values, below which is a more detailed outline.

| Property                  | Default | Description                            |
| ------------------------- | ------- | -------------------------------------- |
| jexer.textMouse           | true    | Show text mouse pointer                |
| jexer.hideMouseWhenTyping | false   | Hide mouse on keystroke everywhere     |
| jexer.hideMenuBar         | false   | Hide the pull-down menu                |
| jexer.hideStatusBar       | false   | Hide the status bar                    |
| jexer.menuIcons           | false   | Use emoji icons in menu                |
| jexer.Swing               |         | Demo: select backend                   |
| jexer.Swing.cursorStyle   | underline | Swing: cursor style                  |
| jexer.Swing.mouseStyle    | none    | Swing: mouse pointer selection         |
| jexer.Swing.mouseImage    |         | Swing: image to use for mouse icon     |
| jexer.Swing.tripleBuffer  | true    | Swing: use triple-buffering            |
| jexer.Swing.imagesOverText | false  | Swing: transparent image pixels        |
| jexer.Swing.color0        | #000000 | Swing: color for black                 |
| jexer.Swing.color1        | #a80000 | Swing: color for red                   |
| jexer.Swing.color2        | #00a800 | Swing: color for green                 |
| jexer.Swing.color3        | #a85400 | Swing: color for yellow                |
| jexer.Swing.color4        | #0000a8 | Swing: color for blue                  |
| jexer.Swing.color5        | #a800a8 | Swing: color for magenta               |
| jexer.Swing.color6        | #00a8a8 | Swing: color for cyan                  |
| jexer.Swing.color7        | #a8a8a8 | Swing: color for white                 |
| jexer.Swing.color8        | #545454 | Swing: color for black + bold          |
| jexer.Swing.color9        | #fc5454 | Swing: color for red + bold            |
| jexer.Swing.color10       | #54fc54 | Swing: color for green + bold          |
| jexer.Swing.color11       | #fcfc54 | Swing: color for yellow + bold         |
| jexer.Swing.color12       | #5454fc | Swing: color for blue + bold           |
| jexer.Swing.color13       | #fc54fc | Swing: color for magenta + bold        |
| jexer.Swing.color14       | #54fcfc | Swing: color for cyan + bold           |
| jexer.Swing.color15       | #fcfcfc | Swing: color for white + bold          |
| jexer.TEditor.hideMouseWhenTyping | true | Hide mouse on keystroke in text editor windows |
| jexer.TEditor.margin      | 0       | Right column margin to highlight       |
| jexer.TEditor.autoWrap    | false   | Automatically wrap text to margin      |
| jexer.TTerminal.closeOnExit | false | Close terminal window when shell exits |
| jexer.TTerminal.hideMouseWhenTyping | true | Hide mouse on keystroke in terminal windows |
| jexer.TTerminal.ptypipe   | auto    | Use 'ptypipe' for terminal shell       |
| jexer.TTerminal.setsid    | true    | Run 'setsid script' for terminal shell |
| jexer.TTerminal.shell     |    | Command to use for the terminal shell       |
| jexer.TTerminal.cmdHack   | true    | For Windows, append Ctrl-J after Enter |
| jexer.TTerminal.scrollbackMax | 2000 | Number of lines in scrollback buffer  |
| jexer.ECMA48.modifyOtherKeys | false  | ECMA48: detect other modifiers       |
| jexer.ECMA48.rgbColor     | false   | ECMA48: emit 24-bit RGB for system colors |
| jexer.ECMA48.wideCharImages | true  | ECMA48: draw CJK/emoji as images       |
| jexer.ECMA48.sixel        | true    | ECMA48: draw images using sixel        |
| jexer.ECMA48.sixelEncoder | hq      | ECMA48: sixel encoder to use           |
| jexer.ECMA48.sixelPaletteSize | 1024 (legacy), 256 (hq) | ECMA48: number of colors for sixel images |
| jexer.ECMA48.sixelSharedPalette | true | ECMA48: shared palette for sixel images (legacy only) |
| jexer.ECMA48.iTerm2Images | false   | ECMA48: draw images using iTerm2 protocol |
| jexer.ECMA48.jexerImages  | png     | ECMA48: draw images using Jexer protocol |
| jexer.ECMA48.imagesOverText | false | ECMA48: transparent image pixels       |
| jexer.ECMA48.explicitlyDestroyImages | false | ECMA48: overwrite old image pixels with black |
| jexer.ECMA48.color0       | #000000 | ECMA48: color for black                |
| jexer.ECMA48.color1       | #a80000 | ECMA48: color for red                  |
| jexer.ECMA48.color2       | #00a800 | ECMA48: color for green                |
| jexer.ECMA48.color3       | #a85400 | ECMA48: color for yellow               |
| jexer.ECMA48.color4       | #0000a8 | ECMA48: color for blue                 |
| jexer.ECMA48.color5       | #a800a8 | ECMA48: color for magenta              |
| jexer.ECMA48.color6       | #00a8a8 | ECMA48: color for cyan                 |
| jexer.ECMA48.color7       | #a8a8a8 | ECMA48: color for white                |
| jexer.ECMA48.color8       | #545454 | ECMA48: color for black + bold         |
| jexer.ECMA48.color9       | #fc5454 | ECMA48: color for red + bold           |
| jexer.ECMA48.color10      | #54fc54 | ECMA48: color for green + bold         |
| jexer.ECMA48.color11      | #fcfc54 | ECMA48: color for yellow + bold        |
| jexer.ECMA48.color12      | #5454fc | ECMA48: color for blue + bold          |
| jexer.ECMA48.color13      | #fc54fc | ECMA48: color for magenta + bold       |
| jexer.ECMA48.color14      | #54fcfc | ECMA48: color for cyan + bold          |
| jexer.ECMA48.color15      | #fcfcfc | ECMA48: color for white + bold         |
| jexer.cjkFont.filename    | NotoSansMonoCJKtc-Regular.otf | Font for CJK characters |
| jexer.emojiFont.filename  | OpenSansEmoji.ttf | Font for emojis              |
| jexer.fallbackFont.filename |       | Font to use when no other available font has a codepoint |
| jexer.TWindow.borderStyleForeground | double | Window border style           |
| jexer.TWindow.borderStyleModal      | double | Window border style           |
| jexer.TWindow.borderStyleMoving     | single | Window border style           |
| jexer.TWindow.borderStyleInactive   | single | Window border style           |
| jexer.TMenu.borderStyle             | single | Menu border style             |
| jexer.TEditColorTheme.borderStyle   | double | Color theme window border style     |
| jexer.TEditColorTheme.options.borderStyle   | single | Interior boxes border style |
| jexer.TRadioGroup.borderStyle       | singleVdoubleH | Radio group border style    |
| jexer.TScreenOptions.borderStyle    | single | Screen options window border style  |
| jexer.TScreenOptions.grid.borderStyle    | single | Border style around the grid   |
| jexer.TScreenOptions.options.borderStyle | single | Interior boxes border style    |
| jexer.translucence        | true    | Translucent windows                    |
| jexer.TWindow.opacity     | 75      | Default window opacity (10 - 100)      |
| jexer.TTerminal.opacity   | 85      | Terminal window opacity (10 - 100)     |
| jexer.TMenu.opacity       | 85      | Menu window opacity (10 - 100)         |


jexer.textMouse
---------------

Used by jexer.TApplication and jexer.backend.SwingTerminal.  If true,
display a text-based mouse pointer, otherwise do not.  (Also, the
Swing backend will display the "hand" mouse cursor when textMouse is
false.)  Default: true.

jexer.hideMouseWhenTyping
-------------------------

Used by jexer.TApplication.  If true, suppress the text-based mouse
pointer after a user presses a key.  Mouse motion will restore the
pointer.  Default: false.

jexer.hideMenuBar
-----------------

Used by jexer.TApplication.  If true, do not display the pull-down
menu on the top row.  Menu keyboard accelerators will still work.
Default: false.

jexer.hideStatusBar
-------------------

Used by jexer.TApplication.  If true, do not display the status bar on
the bottom row.  Status bar keyboard accelerators will still work.
Default: false.

jexer.menuIcons
---------------

Used by jexer.TApplication.  If true, support emoji icons next to
drop-down menu items.  Default: false.

jexer.Swing
-----------

Used by jexer.demos.Demo1 and jexer.demos.Demo4.  If true, use the
Swing interface for the demo application.  Default: true on Windows
(os.name starts with "Windows") and Mac (os.name starts with "Mac"),
false on non-Windows and non-Mac platforms.

jexer.Swing.cursorStyle
-----------------------

Used by jexer.backend.SwingTerminal.  Selects the cursor style to
draw.  Valid values are: underline, block, outline, and verticalBar.
Default: underline.

jexer.Swing.mouseStyle
----------------------

Used by jexer.backend.SwingTerminal.  Selects the mouse pointer
(java.awt.Cursor) style to use.  Valid values are: none, default,
hand, crosshair, move, and text.  Default: none.

jexer.Swing.mouseImage
----------------------

Used by jexer.backend.SwingTerminal.  Filename containing an image to
use as the mouse pointer.  Filename must be on the classpath.  The hot
spot will be in the middle of the image.

jexer.Swing.tripleBuffer
------------------------

Used by jexer.backend.SwingTerminal.  If true, use triple-buffering
which reduces screen tearing but may also be slower to draw on slower
systems.  If false, use naive Swing thread drawing, which may be
faster on slower systems but also more likely to have screen tearing.
Default: true.

jexer.Swing.imagesOverText
--------------------------

Used by jexer.backend.SwingTerminal.  If true, render text glyphs
underneath images, allowing the using of text and images to both show
in one cell.  This is _very_ cool looking, but currently hideously
expensive when covering large parts of the screen or doing animations
with partially-transparent images.  (And I have no shame in admitting
that the notcurses project was the inspiration for me to finally try
this out.  If you want to mix a lot of text-and-images, check out
notcurses, it's really wicked.)  Default: false.

jexer.TEditor.hideMouseWhenTyping
---------------------------------

Used by jexer.TEditorWindow.  If true, suppress the text-based mouse
pointer after a user presses a key within a text editor window.  Mouse
motion will restore the pointer.  Default: true.

jexer.TEditor.margin
--------------------

Used by jexer.TEditorWindow.  If a positive integer, highlight the
column in the text editor window.  Default: 0.

jexer.TEditor.autoWrap
----------------------

Used by jexer.TEditorWindow.  If true, automatically wrap the text to
fit inside the margin.  Default: false.

jexer.TTerminal.closeOnExit
---------------------------

Used by jexer.TTerminalWindow.  If true, close the window when the
spawned shell exits.  Default: false.

jexer.TTerminal.hideMouseWhenTyping
-----------------------------------

Used by jexer.TTerminalWindow.  If true, suppress the text-based mouse
pointer after a user presses a key within a terminal window.  Mouse
motion will restore the pointer.  Default: true.

jexer.TTerminal.ptypipe
-----------------------

Used by jexer.TTerminalWindow.  If 'true', or if 'auto' and 'ptypipe'
is on the PATH, then spawn shell using the 'ptypipe' utility rather
than 'script'.  This permits terminals to resize with the window.
ptypipe is a separate C language utility, available at
https://gitlab.com/AutumnMeowMeow/ptypipe.  Default: auto.

When jexer.TTerminal.ptypipe is true, and jexer.TTerminal.shell is not
set, then the command used for the terminal shell is
`ptypipe /bin/bash --login`

jexer.TTerminal.setsid
----------------------

Used by jexer.TTerminalWindow.  If true, and os.name does not start
with "Windows" or "Mac", spawn shell using the 'setsid' utility to run
'script'.  This runs 'script' in a new process group, permitting
closing the terminal window without crashing the JVM.  Default: true.

When jexer.TTerminal.setsid is true, and jexer.TTerminal.shell is not
set, and os.name does not start with "Windows" or "Mac", then the
command used for the terminal shell is `setsid script -fqe /dev/null`

jexer.TTerminal.shell
---------------------

Used by jexer.TTerminalWindow.  If set, use this value to spawn the
shell.  If not set, the following commands are used based on the value
of the os.name system property:

| os.name starts with | Command used for terminal shell |
| ------------------- | ------------------------------- |
| Windows             | cmd.exe                         |
| Mac                 | script -q -F /dev/null          |
| Anything else       | script -fqe /dev/null           |

jexer.TTerminal.cmdHack
-----------------------

Used by jexer.TTerminalWindow.  If true, append a line feed (Ctrl-J,
hex 0x0a) after every enter/return keystroke (carriage return, Ctrl-M,
hex 0x0d).  This is needed for cmd.exe, but might not be for other
shells.  Default: true.

jexer.TTerminal.scrollbackMax
-----------------------------

Used by jexer.TTerminalWindow.  The number of lines in the scrollback
(offscreen) buffer.  If 0, scrollback is unlimited.  Default: 2000.

jexer.ECMA48.modifyOtherKeys
----------------------------

Used by jexer.backend.ECMA48Terminal.  If true, enable the
"modifyOtherKeys" feature of Xterm to detect things like Ctrl-Enter,
Ctrl-Tab, Ctrl-Shift-A, and so on.  Default: false.

jexer.ECMA48.rgbColor
---------------------

Used by jexer.backend.ECMA48Terminal.  If true, emit T.416-style RGB
colors for normal system colors.  This is expensive in bandwidth, and
potentially terrible looking for non-xterms.  Default: false.

jexer.ECMA48.wideCharImages
---------------------------

Used by jexer.backend.ECMA48Terminal.  If true, draw wide characters
(fullwidth characters) as used by CJK and emoji as images.  This looks
better on terminals loaded without a CJK font, but requires sixel
support.  Default: true.

jexer.ECMA48.sixel
------------------

Used by jexer.backend.ECMA48Terminal.  If true, emit image data using
sixel, otherwise show blank cells where images could be.  This is
expensive in bandwidth, very expensive in CPU (especially for large
images), and will leave artifacts on the screen if the terminal does
not support sixel.  Default: true.

jexer.ECMA48.sixelEncoder
-------------------------

Used by jexer.backend.ECMA48Terminal.  Two sixel encoders are
available: legacy and hq.

The legacy encoder is a uniform color quantizer with
nearly-equal-sized segments in HSL colorspace.  All images are mapped
to a single palette.  Bandwidth can be saved if the terminal supports
DECRST 1070 -- see jexer.ECMA48.sixelSharedPalette.  This encoder is
the original, it looks more retro, but might also work better for some
terminals.

The hq encoder generates custom palettes via several methods, with the
most advanced being a median cut.  It is much faster, has higher
picture quality, but requires the terminal support individual
registers per image (DECSET 1070).  It looks a bit like VHS tape
quality, with minor grain artifacts, and depending on the picture and
palette size might show horizontal banding.

Default: hq.

jexer.ECMA48.sixelPaletteSize
-----------------------------

Used by jexer.backend.ECMA48Terminal.  Number of colors to use for
sixel output.

For the legacy encoder, values are: 2 (black-and-white), 256, 512,
1024, or 2048; default is 1024.

For the hq encoder, values are: 2 (black-and-white), 4, 8, 16, 32, 64,
128, 256, 512, 1024, or 2048; default is 256.

jexer.ECMA48.sixelSharedPalette (legacy encoder only)
-----------------------------------------------------

Used by jexer.backend.ECMA48Terminal, only for the legacy sixel
encoder.  If true, use a single shared palette for sixel images,
emitting the palette once on the first image; this feature requires
terminal support for DECRST 1070 (the ability to disable "use private
color registers for each graphic" flag).  If false, emit a palette
with the used colors on every sixel image.  Default: true.

jexer.ECMA48.iTerm2Images
-------------------------

Used by jexer.backend.ECMA48Terminal.  If true, emit image data using
iTerm2 image protocol, otherwise show blank cells where images could
be.  This is expensive in bandwidth and will leave artifacts on the
screen if the terminal does not support iTerm2 images.  If both
jexer.ECMA48.sixel and jexer.ECMA48.iTerm2Images are true, images will
be displayed in iTerm2 style only.  Default: false.

jexer.ECMA48.jexerImages
------------------------

Used by jexer.backend.ECMA48Terminal.  If not false, and the terminal
reports support for Jexer images, then emit image data using Jexer's
private image protocol, otherwise fallback to sixel or iTerm2 images
if either of those is enabled.

Value can be one of the following:

| Value | Description                        |
| ----- | ---------------------------------- |
| false | Do not use Jexer image protocol    |
| jpg   | Use Jexer protocol with JPG images |
| png   | Use Jexer protocol with PNG images |
| rgb   | Use Jexer protocol with RGB images |

Default: png.

jexer.ECMA48.imagesOverText
---------------------------

Used by jexer.backend.ECMATerminal.  If true, render text glyphs
underneath images, allowing the using of text and images to both show
in one cell.  Jexer cannot know the terminal's font, so glyphs
rendered under images may look odd, even if still usable.  If false,
then the text background color (or what Jexer thinks is the background
color) is emitted underneath the image, which is much faster and looks
better.  Default: false.

jexer.ECMA48.explicitlyDestroyImages
------------------------------------

Used by jexer.backend.ECMATerminal.  If true, this terminal requires
explicitly overwriting images with black pixels to destroy them.  If
false, overwriting images with text will destroy them.  Konsole is the
only terminal known at this time that requires explicitlyDestroyImages
to be true.  Default: false.

jexer.cjkFont.filename
----------------------

Used by jexer.backend.GlyphMaker.  Filename containing the font to use
for CJK.  Filename must be on the classpath.  Default:
NotoSansMonoCJKtc-Regular.otf

Note that if the font is not found, NO ERROR IS REPORTED.

jexer.emojiFont.filename
------------------------

Used by jexer.backend.GlyphMaker.  Filename containing the font to use
for emojis.  Filename must be on the classpath.  Default:
OpenSansEmoji.ttf

Note that if the font is not found, NO ERROR IS REPORTED.

jexer.fallbackFont.filename
---------------------------

Used by jexer.backend.GlyphMaker.  Filename containing the font to use
as a last resort when no other font has the codepoint.  Filename must
be on the classpath.  Default: ""

Note that if the font is not found, NO ERROR IS REPORTED.
