Internal Design:

  * Flow of events from Backend to TApplication to Screen
  * Threading between primary/secondary
  * God classes: TApplication, TWidget, TWindow

Platform Specifics:

  * POSIX: using ptypipe to achieve resizable TTerminalWindows
  * Linux console: using LCXterm for mouse support

Widgets Gallery
  * ~~Widget template:~~
    * ~~Screenshot(s)~~
    * ~~Example usage~~
    * ~~Link to Javadoc~~
  * For each widget:
    * Fix checkstyle issues

Howto's:

  * ~~Hello World~~

  * Prototype tiling WM
    - How it works

  * Prototype file viewer
    - How it works

  * Longer article: how to make a simple email sender
    - Main application
    - Add menu for email compose function
    - Create window for email compose
    - Put code to send the message and close the window

  * Dialog boxes: TMessageBox, TInputBox, TFileOpenDialog

  * Creating a new widget:
    - Adding cut/copy/paste support

  * MultiScreen / MultiBackend
    - Creating a telnet server that exposes shared screen

  * Using TWindowBackend to run an application inside a window

  * Using a Backend alone (like jermit's Qodem UI)

😻