Jexer 1.6.0 Release
===================

I remain surprised to announce the release of [Jexer
1.6.0](https://gitlab.com/AutumnMeowMeow/jexer).  (Surprised because I
have been unable to code on anything serious for about 18 months, and
suddenly I could again.  Who knew?)  This version is "alpha": it has
quite a bit of new code and not a lot of testing.  Caveat emptor.

Jexer is an advanced text-plus-graphical windowing system framework
that can help new applications take full advantage of the terminal.
Or just look really retro and cool in their own GUI window.  Its major
features are:

  * MIT licensed.  100% Java.

  * Direct support for xterm-like
    [terminals](https://gitlab.com/AutumnMeowMeow/jexer/-/wikis/terminals):
    mouse, keyboard, 24-bit RGB color, UTF-8, fullwidth characters
    (CJK and emoji), and [sixel
    images.](https://vt100.net/docs/vt3xx-gp/chapter14.html)

  * A Swing-based GUI window that ships with a good-looking Terminus
    font.  Additional fonts can be specified for CJK, emoji, and
    fallback.

  * Image support, including both as [input in its terminal
    window](https://jexer.sourceforge.io/screenshots/overlapping_alpha.png),
    and as output to [the host
    terminal](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/sixel_images.png?raw=true)
    or [Swing
    window](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/snake_swing.png?raw=true).
    Jexer is the first system capable of managing multiple terminal
    windows displaying properly overlapping images.  Jexer currently
    supports up to 1024-color sixel images (which it cleverly uses to
    [look like a much higher bit
    depth](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/pca_match.png?raw=true)),
    and full-color PNG and JPG images using iTerm2 and Jexer image
    protocols.

  * Translucent windows, including images.

  * Text-based and
    [pixel-based](https://jexer.sourceforge.io/screenshots/pixel_demo.gif)
    display capabilities.

  * [Multiplexed
    terminals](https://xtermwm.sourceforge.io/screenshots.html), and
    [multihead
    applications](https://jexer.sourceforge.io/screenshots/multiscreen_1.png)
    with shared session support.

  * Draggable / resizable windows, menu bar, system-modal dialogs
    (message/input boxes and filename picker), and a full complement
    of widgets: button, text field, checkbox, combobox, list, radio
    button, scrollbars, data table, calendar picker, progress bar,
    text display, and simple text editor.  Plus layout manager support
    for resizable widgets and windows.

  * A terminal window capable of passing "vttest" (including VT100
    double-width / double-height), and supporting all of Jexer's
    features.  Jexer can run inside itself, with full keyboard, mouse,
    and image support.

  * Cut/copy/paste support to the system clipboard, including images.

  * Extensively documented in the code (Javadoc), a growing set of
    GitLab wiki pages, and ships with a demonstration application
    showing off all of its available widgets.

  * A more full-featured application demonstrating its capabilities in
    the [Xterm Window Manager](https://xtermwm.sourceforge.io), which
    includes virtual desktops, tiling and cascaded windows, plugins,
    and detachable/attachable shared sessions.


Find out more at the Jexer Sourceforge or GitLab project pages:

  * https://jexer.sourceforge.io/

  * https://gitlab.com/AutumnMeowMeow/jexer


Download
--------

GitLab: git clone https://gitlab.com/AutumnMeowMeow/jexer.git

Binary downloads: http://sourceforge.net/project/showfiles.php?group_id=2829121

On Maven:

    group: com.gitlab.klamonte
    artifact: jexer
    version: 1.6.0


Ugh, Java Sucks!
----------------

(Thor squint) But does it though?

More seriously, I initially picked D because it was sexy in 2013.  But
D circa 2013 brought too many headaches for me, so I switched to Java
because I wanted a cross-platform standard library that would be
stable over many years.  And Java is OK, it is a solid workhorse that
got the job done.  I was able to readily explore this space and
challenge some of the most advanced and/or popular terminals, and
along the way fill in many gaps in my own.

Yet in porting my initial work to Java I stumbled upon an unexpected
benefit: I found ways to accomplish all of what Jexer does _without
calling C directly_.  No termios, no ncurses, no forkpty(), and thus
no serious hurdles porting it to anything that can spawn programs and
read their output.  On Linux, BSD, or OSX, all you need is 'stty' and
'script' to make things work.  (And if you want resizable terminal
windows, add 'ptypipe'.)

So for those who want something like Jexer but in your own favorite
language, I encourage you to check out the [Porting
Jexer](https://gitlab.com/AutumnMeowMeow/jexer/wikis/porting) page on
the wiki: it has pointers to where the key features are, and a
potential roadmap if you wanted to take part or all of it into your
own hands.  I licensed Jexer as MIT, stuck with simple Java 1.6, and
thoroughly documented it in the hope that fans of other languages
could more easily create or enhance their own text user interfaces.


Acknowledgments
---------------

There are a few folks I would like to say thank you to, in no
particular order: Wez Furlong, Nick Black, Christian Parpart, Joerg
Breitbart, Per Bothner, Thomas Wolff, Daniel Eklöf, İsmail Yılmaz,
James Holderness, Egmont Koblinger, and Nicholas Marriott.

💗
