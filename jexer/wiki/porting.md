Porting Jexer
=============

One of Jexer's design goals is to be straightforward to port to
another language.  To that end, the following things were done:

* Jexer's license is MIT, enabling anyone to freely create their own
  open- or closed-source derivative works.

* Nearly all of Jexer's API, including private members and functions,
  is documented via Javadoc.

* Jexer uses no low-level C functions directly.  Instead it spawns
  'stty' and 'script' (or 'ptypipe') to accomplish the same tasks as
  termios, forkpty, and ioctl.  If a language can spawn a program and
  read/write to its standard in/out (stdin/stdout), then it can be
  made to do everything Jexer can do.

* Jexer is coded to be clear, not clever.  It uses only functions and
  language features available in Java 1.6 to make it very easy to see
  how it does its job.

This document outlines the locations of Jexer's key features, and a
potential roadmap for porting it to another language.  Jexer itself
has gone through this process before: it started its life as a D
language project.

Location Of Key Features
------------------------

| Feature                  | Source files / methods                         |
| ------------------------ | ---------------------------------------------- |
| xterm: mouse input       | backend.ECMA48Terminal.parseMouse()            |
| xterm: mouse input (SGR) | backend.ECMA48Terminal.parseMouseSGR()         |
| xterm: keyboard input    | backend.ECMA48Terminal.processChar()           |
| xterm: sixel output      | backend.HQSixelEncoder, backend.LegacySixelEncoder |
| xterm: set raw/cooked termios | backend.ECMA48Terminal.doStty()           |
| xterm: get screen size   | backend.TTYSessionInfo.sttyWindowSize()        |
| xterm: screen output     | backend.ECMA48Terminal.flushString()           |
| terminal: xterm widget   | tterminal.ECMA48                               |
| terminal: sixel input    | tterminal.SixelDecoder                         |
| screen: output functions | backend.LogicalScreen                          |
| screen: CJK output       | backend.GlyphMaker, backend.LogicalScreen.putFullwidthChar() |
| screen: mouse cursor     | TApplication.invertCell()                      |
| screen: pixel-based ops  | tackboard.Tackboard.draw()                     |
| input: keyboard          | TKeypress, event.TKeypressEvent                |
| input: mouse             | event.TMouseEvent                              |
| widget hierarchy root    | TWidget                                        |
| window movement/sizing   | TWindow                                        |
| drop-down menus          | TApplication, menu.TMenu, menu.TMenuItem       |
| cell colors              | bits.CellAttributes, bits.Color                |
| cell character / image   | bits.Cell                                      |
| CP437 to Unicode map     | bits.GraphicsChars                             |
| Swing color palette      | backend.SwingTerminal.setDOSColors()           |
| xterm color palette      | backend.ECMA48Terminal.rgbColor()              |


Porting Sequence
----------------

1. Basic Hello World.  Port CellAttributes, Cell, Color, Screen,
   LogicalScreen, ECMA48Terminal, either LegacySixelEncoder or
   HQSixelEncoder, TTYSessionInfo, TKeypress, TKeypressEvent, and
   TMouseEvent.  This gets you a screen to draw on (with image output
   support), mouse and keyboard input, and raw termios.  If you don't
   want a full windowing system, you can stop here.

2. If you want pixel-based logic rather than (or in addition to) text
   cell-based logic, port the tackboard package classes.  These are
   entirely unaware of the windowing system, and can place bitmaps
   anywhere on screen.

3. Basic windowing system.  Port TWidget, TWindow, Backend,
   GenericBackend, ECMA48Backend, and TApplication.  In TApplication,
   strip out the menu management and secondary widget handler code.
   In TWidget, comment out the convenience widget constructors
   (addLabel, addField, etc.) for now.

4. Add some widgets.  Port TLabel, TButton, and TField.  Build a demo
   application with static text, buttons, and editable text.

5. Add menus.  Port TMenu, TMenuItem, etc.  Put the menu handling back
   into TApplication.

6. Add system-modal widgets.  Port TMessageBox, TInputBox, and
   TFileOpenBox, and modify TApplication's primary/secondary event
   handlers.  System-modal widgets are very handy because they permit
   code in the primary thread to pause, ask for input (handled by the
   secondary thread), and continue on.  The infrastructure for this
   may not be a straightforward port in the target language.  For
   Java, I use two threads pulling off a common input queue; for D, I
   used two fibers that yielded to each other.  You might choose to
   push/pop worker threads/fibers on a stack rather than just have the
   two like Jexer.  Regardless, this may take some effort to work out
   the kinks.

7. Add the remaining widgets at leisure.  Pick and choose which
   widgets you want to have, and port them.  Many are small and easy
   (TPasswordField, TRadioButton, TCheckBox), but some are more
   complex (TTerminal, TTableWidget, TTreeView).



Workarounds For POSIX C Functions
---------------------------------

Jexer has an explicit design goal not to call C functions directly, so
that it can be a reference for languages that do not have easy access
to C functions.  The workarounds Jexer uses are summarized here:

* "stty size" is used in place of ioctl(TIOCGWINSZ), to determine the
  number of rows and columns of the screen when running against
  stdin/stdout (System.in/System.out).  Another option a language
  could consider is the "CSI 18 t" sequence: most xterm-like terminals
  will respond with a sequence containing the window rows and columns.

* "stty -ignbrk -brkint -parmrk -istrip -inlcr -igncr -icrnl -ixon
  -opost -echo -echonl -icanon -isig -iexten -parenb cs8 min 1 <
  /dev/tty" is used in place of tcgetattr() / cfmakeraw() /
  tcsetattr() to put stdin/stdout in raw mode.

* "stty sane cooked < /dev/tty" is used in place of calling
  tcsetattr() to restore the original termios.

* "script -qF /dev/null" (BSD) and "script -fqe /dev/null" (GNU) are
  used in place of forkpty() to spawn terminal shell windows because
  glibc puts stdin/stdout in buffered mode when it isn't a tty.

* Using "script" for forkpty() works, but introduces the issue that
  the shells inside terminal windows cannot detect the change when a
  user resizes a TTerminalWidget / TTerminalWindow.  Typically one
  would call ioctl(TIOCSWINSZ) for this purpose; Jexer instead uses
  the [ptypipe](https://gitlab.com/AutumnMeowMeow/ptypipe) utility for this
  case.  ptypipe spawns its arguments in a pty via forkpty(), and then
  listens for "CSI 8 ; {rows} ; {cols} t" in its stdin, and if found
  strips that out and calls ioctl(TIOCSWINSZ) instead.
  TTerminalWidget has to know both to spawn "ptypipe" instead of
  "script" and to issue the dtterm sequence to ptypipe when the user
  resizes the window, so the "jexer.TTerminal.ptypipe" system property
  must be set to "true".

* A Windows version of ptypipe is desired in the future to use the
  [Windows Pseudoconsole
  functions](https://docs.microsoft.com/en-us/windows/console/creating-a-pseudoconsole-session)
  to provide equivalent functionality of forkpty() (CreateProcess())
  and ioctl(TIOCSWINSZ) (ResizePseudoConsole()).



Sixel Output
------------

Jexer can use sixel, iTerm2, and Jexer image formats for: images,
VT100 double-width/double-height, and optionally font rendering for
CJK/emoji.  Early on in building sixel support, it was discovered that
if images are overwritten with other images or text cells, it could
result in a corrupted display.  (More specifically, the text would
always appear where and how it should be, but the image might leave
artifacts in the region it was originally drawn in.)  Jexer developed
a strategy that should work on any terminal with sixel/image support:

 * For text across the entire screen, only the cells that have changed
   are updated. This is similar to ncurses.

 * For rows that have image or text changes, and contain image data,
   all images on that row are redrawn.  Text updates can thus distort
   / destroy any images on the display, Jexer will be replacing them
   anyway.

 * The mouse cursor can move multiple rows in a single screen update,
   and Jexer does not always know which rows that contain image data
   might have changed between the last time the mouse was displayed,
   and its current position.  So it conservatively updates all rows
   between the old and new mouse pointer rows, inclusive.

 * For a row that is updated, the image will be displayed first, then
   the text. For performance, adjacent image cells are collected into
   contiguous blocks, and these contiguous blocks are cached.

With the above strategy, an image-supporting terminal is not required
to ensure any images in its display are maintained or coherent in some
manner after text has overwritten a portion of it.

See also the "Text Cells" section in [Jexer's high-level design
document,](high-level-design) which discusses the internal flow of
image data from source to the user-facing screen.

![jexer.backend.HQSixelEncoder](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/pca_match.png)

😻