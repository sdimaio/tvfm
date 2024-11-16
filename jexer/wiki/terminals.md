Terminal Features
=================

Jexer can use the following features of a terminal if available:

* Mouse clicks.

* Mouse motion, including dragging and resizing windows, and
  SGR-Pixels mode (1016).
  [DECRQM/DECRPM](https://vt100.net/docs/vt510-rm/DECRQM.html) is used
  to detect SGR-Pixels support.

* 24-bit ("true color") RGB.

* Image support via sixel or iTerm2.  (So far as I know, Jexer is the
  first text windowing system with image support.)  There is currently
  [no documented
  means](https://gitlab.com/gnachman/iterm2/issues/8940) of detecting
  iTerm2 image support, so Jexer will emit iTerm2 images when
  jexer.ECMA48.iTerm2Images=true, or when it detects (via XTVERSION)
  WezTerm, mintty, or iTerm2.  (Note that I cannot easily test iTerm2
  itself; the last time I tried it it did not fare well with either
  sixel or iTerm2 images.)

Terminals
---------

Most popular X11 terminals can run Jexer, but only a few support all
of Jexer's features.  Jexer is actively developed against
[xterm](https://invisible-island.net/xterm/) and
[wezterm](https://wezfurlong.org/wezterm/) .  The table below lists
the terminals last tested against Jexer:

| Terminal       | Environment        | Mouse Click | Mouse Cursor | Images |
| -------------- | ------------------ | ----------- | ------------ | ------ |
| xterm          | X11                | yes         | yes          | yes    |
| wezterm        | X11, Windows       | yes         | yes          | yes(7) |
| foot(3)        | Wayland            | yes         | yes          | yes    |
| contour(3)     | X11                | yes         | yes          | yes    |
| mintty         | Windows            | yes         | yes          | yes    |
| mlterm         | X11                | yes         | yes          | yes    |
| RLogin         | Windows            | yes         | yes          | yes    |
| Windows Terminal(6) | Windows       | yes         | yes          | yes    |
| alacritty(3b)  | X11                | yes         | yes          | yes    |
| gnome-terminal | X11                | yes         | yes          | no     |
| iTerm2         | Mac                | yes         | yes          | no(5)  |
| kitty(3)       | X11                | yes         | yes          | no(8)  |
| lcxterm        | CLI, Linux console | yes         | yes          | no     |
| rxvt-unicode   | X11                | yes         | yes          | no(2)  |
| xfce4-terminal | X11                | yes         | yes          | no     |
| zutty          | X11                | yes         | yes          | no(2)  |
| DomTerm(3)     | Web                | yes         | no           | yes    |
| darktile       | X11                | yes         | no           | no(5)  |
| konsole        | X11                | yes         | no           | no     |
| yakuake        | X11                | yes         | no           | no     |
| screen         | CLI                | yes(1)      | yes(1)       | no(2)  |
| tmux           | CLI                | yes(1)      | yes(1)       | no     |
| putty          | X11, Windows       | yes         | no           | no(2)  |
| qodem(3)       | CLI, Linux console | yes         | yes(4)       | no     |
| qodem-x11(3)   | X11                | yes         | no           | no     |
| yaft           | Linux console (FB) | no          | no           | yes    |
| Linux          | Linux console      | no          | no           | no(2)  |
| MacTerm        | Mac                | no          | no           | no(2)  |

1 - Requires mouse support from host terminal.

2 - Also fails to filter out sixel data, leaving garbage on screen.

3 - Latest in repository.

3b - Latest in repository, using graphics PR branch.

4 - Requires TERM=xterm-1003 before starting.

5 - Sixel images can crash terminal.

6 - Windows Terminal 1.22 Preview, released on August 27, 2024.

7 - Both sixel and iTerm2 images.

8 - Kitty image protocol support will never be in Jexer, as a matter
    of principle.  Should that terminal adopt iTerm2 or sixel, then
    images could work.  (Image support for kitty was removed on
    February 10, 2022.)

When running on the raw Linux console,
[LCXterm](https://gitlab.com/AutumnMeowMeow/lcxterm) or
[Qodem](http://qodem.sourceforge.net) are required if one wishes to
use the mouse.  [GPM](https://github.com/telmich/gpm) is also
required.


Terminal Widgets
----------------

The table below lists embeddable widgets tested against Jexer recently:

| Terminal       | Language | Mouse Click | Mouse Cursor | Images | Link |
| -------------- | -------- | ----------- | ------------ | ------ | ---- |
| jexer (ECMA48) | Java     | yes         | yes          | yes    | https://gitlab.com/AutumnMeowMeow/jexer
| upp-components Terminal | C++  | yes    | yes          | yes    | https://github.com/ismail-yilmaz/upp-components/tree/master/CtrlLib/Terminal
| xterm.js(2)    | TypeScript | yes       | yes          | yes    | https://xtermjs.org
| gowid          | Go       | yes         | yes          | no(1)  | https://github.com/gcla/gowid
| urwid          | Python   | no          | no           | no     | https://github.com/urwid/urwid
| JediTerm       | Java     | yes         | no           | no     | https://github.com/JetBrains/jediterm

1 - Also fails to filter out sixel data, leaving garbage on screen.

2 - Using jerch's xterm-addon-image: https://github.com/jerch/xterm-addon-image
