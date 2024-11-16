Pixel-Based Operations
======================

Input
-----

Pixel-based mouse coordinates are available on the Swing backend
natively, and on the ECMA48 backend if SGR-Pixel mouse mode (1016) is
supported.

Output
------

Jexer can place bitmaps over text and under text anywhere on screen.
This facility is a bit different than the rest of Jexer: it does not
utilitize windows or widgets on its own.  Instead "underlays" and
"overlays" are provided at the TWindow and TApplication level, to be
able to draw within windows and over the entire screen, respectively.

To create a bitmap:

```Java
import tackboard.Bitmap;

// When ready to put an image underneath all widgets:

Bitmap bitmap = new Bitmap(x, y, z, bufferedImage);
getWindow().addUnderlay(bitmap);

// When ready to remove the image:
bitmap.remove();
```

When using bitmaps within applications, the following options are
provided:

* TWindow.addUnderlay() will put another bitmap underneath the widgets
  of that window.

* TWindow.addOverlay() will put another bitmap on top of the widgets
  of that window.

* TApplication.addOverlay() will put another bitmap over the entire
  screen.

* TApplication.getOverlay() will return the Tackboard for the
  application.

Screenshot
----------

Below one can see a window with an underlay (the heart floating under
the text), an overlay (the other heart over it), and a custom mouse
bitmap placed on the application overlay:

![](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/xterm_pixel_mouse.gif?raw=true)
