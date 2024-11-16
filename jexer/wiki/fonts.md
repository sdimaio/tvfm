Fonts
=====

Jexer utilizes glyphs from [Code Page 437](https://en.wikipedia.org/wiki/Code_page_437), [DEC Special Graphics](https://en.wikipedia.org/wiki/DEC_Special_Graphics), [ISO 8859-1](https://en.wikipedia.org/wiki/ISO/IEC_8859-1) (for DEC [National Replacement Character Sets](https://en.wikipedia.org/wiki/National_Replacement_Character_Set) used by TTerminalWindow), and [Unicode Box Drawing](https://en.wikipedia.org/wiki/Box_Drawing_\(Unicode_block\)) .

Two fonts that are known to look good using all of these glyphs are [UNI-VGA](http://www.inp.nsk.su/~bolkhov/files/fonts/univga/) and [Terminus](http://terminus-font.sourceforge.net/).

UNI-VGA
-------

I run xterm with UNI-VGA like this:

```
xterm -class UXterm -u8 -geometry 80x25 -fn -bolkhov-vga-medium-r-normal--16-160-75-75-c-80-iso10646-1
```

Terminus
--------

I run xterm with Terminus like this:

```
xterm -class UXterm -u8 -geometry 80x25 -fn -xos4-terminus-medium-r-normal--20-200-72-72-c-100-iso10646-1
```

😻