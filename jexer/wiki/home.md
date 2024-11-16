Jexer - Java Text User Interface Library
========================================

Jexer is a text-based windowing system loosely reminiscent of
Borland's [Turbo Vision](http://en.wikipedia.org/wiki/Turbo_Vision)
system.  Jexer works on both command-line [Xterm-like
terminals](terminals) and on X11/Mac/Windows GUI systems using Swing.

The screenshot below shows several open windows, widgets, terminal
windows, and image support:

![Screenshot](https://gitlab.com/AutumnMeowMeow/jexer/raw/master/screenshots/new_demo1.png)



Obtaining Jexer
---------------

Jexer is available on Maven Central:

```xml
<dependency>
  <groupId>com.gitlab.klamonte</groupId>
  <artifactId>jexer</artifactId>
  <version>1.6.0</version>
</dependency>
```

Binary release downloads are also available on
[SourceForge.](https://sourceforge.net/projects/jexer/files/jexer/)


Why I Do Not Post My Projects Anymore
-------------------------------------

[Why I Do Not Post My Projects Anymore](no-release-announcements)


Documentation
-------------

* [API docs](https://jexer.sourceforge.io/apidocs/api/index.html)
* [Project Goals and Design Guidelines](dev-standards)
* Writing Jexer applications:
  - Jexer's [high-level design.](high-level-design)
  - Jexer [java.lang.System properties.](jexer system properties)
  - Notes on [pixel-based functions.](pixel-operations)
* Xterm backend:
  - [Supported terminals.](terminals)
  - [Recommended fonts for Xterm.](fonts)
  - [Jexer image protocol.](jexer-images)
  - [Other Jexer-specific escape sequences.](jexer-sequences)
* [Known limitations.](limitations)
* [Latest Release Announcement.](latest-release)
* [Porting Jexer to another language.](porting)



Examples
--------

* [Hello World.](hello-world)
* The [official demo.](demo-application)
* Prototype [tiling window manager.](example-tiling-wm)
* Prototype [file viewer.](example-image-viewer)
* Prototype [Xterm video player.](example-xterm-video-player)



Widget Gallery
--------------

* [TButton](widget-tbutton)
* [TCalendar](widget-tcalendar)
* [TCheckBox](widget-tcheckbox)
* [TComboBox](widget-tcombobox)
* [TDirectoryList](widget-tdirectorylist)
* [TEditColorThemeWindow](widget-teditcolorthemewindow)
* [TEditorWidget](widget-teditorwidget)
* [TEditorWindow](widget-teditorwindow)
* [TExceptionDialog](widget-texceptiondialog)
* [TField](widget-tfield)
* [TFileOpenBox](widget-tfileopenbox)
* [TFontChooserWindow](widget-tfontchooserwindow)
* [THelpWindow](widget-thelpwindow)
* [THScroller](widget-thscroller)
* [TImage](widget-timage)
* [TImageWindow](widget-timagewindow)
* [TInputBox](widget-tinputbox)
* [TLabel](widget-tlabel)
* [TList](widget-tlist)
* [TMessageBox](widget-tmessagebox)
* [TPanel](widget-tpanel)
* [TPasswordField](widget-tpasswordfield)
* [TProgressBar](widget-tprogressbar)
* [TRadioGroup](widget-tradiogroup)
* [TScreenOptionsWindow](widget-tscreenoptionswindow)
* [TScrollableWidget](widget-tscrollablewidget)
* [TScrollableWindow](widget-tscrollablewindow)
* [TSpinner](widget-tspinner)
* [TSplitPane](widget-tsplitpane)
* [TStatusBar](widget-tstatusbar)
* [TTableWidget](widget-ttablewidget)
* [TTableWindow](widget-ttablewindow)
* [TTerminalWidget](widget-tterminalwidget)
* [TTerminalWindow](widget-tterminalwindow)
* [TText](widget-ttext)
* [TTreeView](widget-ttreeview)
* [TVScroller](widget-tvscroller)
* [TWindow](widget-twindow)


Optional Layout Managers
------------------------

Jexer defaults to absolute positioning of elements within a window.
However, additional layout managers that will respond correctly to
window resizing events are available:

* [BoxLayoutManager](layout-box)
* [StretchLayoutManager](layout-stretch)


Other
-----

* [Applications Wishlist](apps-wishlist)
* [Jexer Features Wishlist](wishlist)
* [TODOs for this Wiki](Wiki-TODO-List)
