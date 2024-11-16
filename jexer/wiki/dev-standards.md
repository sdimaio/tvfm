Jexer Project Goals And Code Design Standards
=============================================

Jexer project goals are to:

* Bring the best ideas of the DOS-era text-based systems to modern
  platforms.

* Demonstrate the capabilities of
  [xterm](https://invisible-island.net/xterm/), and encourage other
  terminals to support more of xterm's features.

* Be used across all classes of desktop/laptop/server hardware,
  especially machines that are either not able or not allowed to run
  latest-gen GUI-based interfaces.

* Be a resource for other text-based user interface projects,
  especially those not written in Java.

To these ends, my initial 1.0.0 release of Jexer conformed to the
standards outlined below.  Future maintainers may have different
ideas, but this is what is here now.



Code Deliverables
-----------------

* All code must be written in Java, and work with the Java 1.6
  standard.

* All code must rely solely on the Java standard library, at the
  version 1.6 standard.

* Features must not utilize code that duplicates function already
  available in the Java standard library version 1.6.

* All code must have full Javadoc documentation for all class methods,
  including private.

* All code must be licensed MIT.

* All user-visible strings must be localized.



Portability
-----------

* All features should work on both Windows and POSIX systems, where
  feasible.

* JNI must not be used.

* Standalone programs in non-Java languages may be used.  If so, their
  use must be an optional enhancement to an existing feature.



Build Systems
-------------

* An ant-based build system is required to support non-IDE workflows
  (including my own, which is Emacs + ant + command line).

* A maven-based build system is required to support uploading to Maven
  Central.



Test-Driven Development
-----------------------

Jexer was not built originally with unit tests.  TDD is desired for a
future 2.0 release.

😻