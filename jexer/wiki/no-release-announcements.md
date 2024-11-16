Why I Do Not Post My Projects Anymore
=====================================

If you have never heard of [qodem - a ncurses-based clone of
Qmodem(tm) that has a lot of unique
features](http://qodem.sourceforge.net), [lcxterm - a terminal
"helper" that can supplement any other
terminal](https://lcxterm.sourceforge.io), [tjide - a Turbo
Pascal-like IDE for Java](https://tjide.sourceforge.io), [xtermwm - a
text-based desktop environment that can run entirely in a
terminal](https://xtermwm.sourceforge.io), or [jexer - possibly the
world's first mixed images-and-text
TUI](https://jexer.sourceforge.io), well this is why.

_Every_ time I have posted a project the following kinds of responses
have come:

* It looks like another project, therefore is a copy of another
  project, therefore this was a total waste of time.

* A more popular and/or funded project looks better / does it better /
  is easier to use / works on more platforms / etc.

* There is no imaginable use for such a thing.

* You should change it to behave { like it did three years ago, } it
  would be more like the "modern" projects that way.

The Jexer 1.6 announcement was the final straw.  Some asswipe decided
to retweet my thread to their 20k followers so they could get a
conversation started about some other (massive and/or funded)
products.  Then they worked to gas up the people complaining because
their terminals aren't compatible.

Let me be blunt: there exist only two projects _in the world_ right
now that can do this:

![WezTerm, translucent images](https://gitlab.com/AutumnMeowMeow/jexer/-/raw/master/screenshots/wezterm_translucent_images.png?raw=true "WezTerm, translucent images")

Jexer and [notcurses](https://github.com/dankamongmen/notcurses) are
on the bleeding edge.  Your terminals will get there eventually when
it becomes a priority for them.  Don't blame me, or the language, or
whatever else because your terminal didn't work and you didn't spend
five damn minutes [finding one that
does.](https://gitlab.com/AutumnMeowMeow/jexer/wikis/terminals)

In the past I would hang out and try to help the few people with
questions.  One time that kind of exchange turned into a whole
[Getting Started
Guide,](http://qodem.sourceforge.net/getting_started/getting_started.html)
a teeny bit of code, and then crickets for the next ten years.  I've
been here before enough times.  It's part of why I stopped writing
code for a couple years.

This time I opted to lock my Twitter account, block a good deal of the
professional Java Twitterverse, delete my Reddit threads, delete the
archived Jexer GitHub repo, clean up other things, and ultimately put
this page prominently on the Jexer README and Wiki.


What I Care About
-----------------

Someone much wiser than me said recently: _These are small things._

Jexer is a small thing.  _All_ of these projects are small things.  If
people aren't having fun, they should be looking for something else to
enjoy.

I don't care if someone I've never met uses Jexer, likes Jexer, or
neither.  I write my code to see what's possible, to prove right or
wrong the assumptions about where we can go, and maybe find a bit of
art along the way.  Sometimes amazing things come out.  _(For Jexer
1.6, I followed chafa's outline on using principal component analysis
to match colors, and I can't tell you just how beautiful that was. One
of *the* best moments of my last nine years of code.  And far *far*
away from a few Unicode dashboards on a "modern" dark background.)_ I
_really_ love it when I see people create art of their own.  Those
people know who they are.  I hope by now that they know who I am, and
my admiration for their creations.

I don't care if you work in IT developing commercial products.  You
make dashboards to manage a zillion Docker instances?  Cool, you do
you.  I'm over here, you're over there, maybe we cross paths, maybe
not.  May you all have wonderful weekends doing whatever you love.

I absolutely _loathe_ people who use other people's hobby art projects
to initiate conversations about their own commercial stuff.  Those
folks can seriously rot in hell.


When People Talk About Their Stuff
----------------------------------

Don't tell people "that's been done, you wasted your time."  Do you
think the people who came first would be happy to know that you are
using their project to shut down someone else's fun?  Do you think I
would have been happy if people pointed to
[xtermwm](https://gitlab.com/AutumnMeowMeow/xtermwm) in 2020 to tell
the brilliant developer behind
[vtm](https://github.com/netxs-group/vtm) to stop what he was doing?
I _love_ vtm and where it's going!  I would have been _heartbroken_ to
hear that he saw someone else's stuff and stopped enjoying it.  I have
to imagine that most authors feel the same way.  There is room for
_all_ of us to try to have fun.

Don't tell people "there is no imaginable use".  There _was_ use for
me: when I was programming oceanographic buoys over RS-232 from the
raw Linux console, I needed more features in the console, so I wrote
them into one of the few ncurses-based terminals out there.  (Another
is SyncTERM, and it is a really cool BBS client.)  There was no use
for GPUs outside of gaming, and now they are a core part of the global
compute infrastucture.  There is currently little use for [DOOM inside
Xterm,](https://gitlab.com/AutumnMeowMeow/xtermdoom) but it is fun,
and can help terminal authors find some performance bugs.

OK rant over.

The rest of this is for the people I _do_ care about: authors of
terminals and terminal programs.  I know it's unlikely you will use
Jexer directly: Java isn't anyone's natural fit language for
terminals.  But I hope if there was anything you saw and liked, that I
left enough knowledge transfer lying around for you to do it even
better. :-)

_Keep hacking on._


The Hard Parts: A Post-Mortem
=============================

Jexer has been off-and-on about 10 years of coding.  It's almost done
now.  I have pixel-based control of everything on screen and the
mouse, and I'm moving into gaming for my next chapter.  It's ready for
its post-mortem.  I did one of these [for qodem
too.](http://qodem.sourceforge.net/version1.html)

This document briefly outlines some of the harder bits encountered
putting together a tiling-and-cascading window manager with image
support:

* Clipping/offset widget screen drawing functions was a bit of a
  chore.  It took a couple weeks before it really settled on something
  stable, but once that was done I have had almost zero touch-ups
  since.

* Threading has always been a challenge.  Every new scale-up in
  performance requirements has exposed deadlocks, useless spinning,
  and other problems.  Where I got to for a decent solution was
  queues: multiple threads pass messages into a queue for that
  function (e.g. a repaint request), and some dedicated thread pulls
  off those requests (collapsing if possible multiple requests) and
  gets it done.  Queue is empty, thread sleeps.

  - A key insight later was: pass the actual data _as_ the "message to
    wake up".  That's obvious for things like input events, but less
    obvious that it works fine for even whole screens: my terminal
    widget reader thread composes its entire screen, and then passes
    that over to the queue that the screen display thread will pull
    from.

  - It's probably not fully solved though.  I occasionaly have
    deadlocks to work out between "putting things into the queue" and
    "waking up the thread that manages said queue".  I might have to
    resort to java.util.concurrency in the end, we will see.

  - Does it sound weird for a "Turbo Vision clone" to use
    massive-scale web service type functions?

* Multihead was very easy actually -- just fan out all operations to
  the list of screens/backends.  It did get harder for a while as
  throughput increased, but then got much simpler again and loads
  faster when I made a virtual screen that flushed to all the physical
  screens on their own threads once at the end.

* Images was by far the most difficult challenge here.  There were two
  very hard parts in particular:

  - Encoding images such that they fit within the text cell.  You go
    through a lot of bad scrolling screens before you figure it out.
    Also you find the key limits of xterm: 1000x1000 max, 1024 colors
    max, stay off the bottom row (unless you have DECSDM), and know
    whether you need to set the palette.  Then get enough performance
    to actually be usable.  That was several weeks.  (By contrast,
    iTerm2 et al are so trivial you could do them in a day each.)

  - Figuring out _exactly_ how much work you need to do.  There are
    _tons_ of bottlenecks upstream of the image encoder.  Do you have
    transparent images that need to blit over rather than replace?
    Are two images really the same?  You really do not want to be
    inserting full pixel scans inside your rendering loop, but it's so
    easy to slip into one when you _think_ you're just checking basic
    text attributes.

  - Did I say two?  The third hard part is getting fast enough for
    real animation work.  You know you're on the right track when you
    start digging into your data structures and/or linear algebra
    textbooks.

* Note that _decoding_ images and putting them on screen -- the job of
  the terminal emulator -- is absurdly easy in comparison to
  multiplexing them.  The whole [discussion
  here](https://gitlab.freedesktop.org/terminal-wg/specifications/-/issues/12)
  was so disappointing.  It took some patience not to say some things.
  But I will point out here that:

  1. Jexer's terminal emulator widget, which is capable of running
     Jexer _inside_ itself in a window _with image support_ (making it
     the first and currently only system able to do this), represents
     only _14.7%_ of the codebase.  What TE authors are doing is a
     _small_ part of what Jexer as a whole does.  Or what any other
     mature terminal multiplexer such as tmux does.  No TE author
     should hate on tmux, it's doing a _lot_ more work than they can
     imagine.

  2. The likelihood that your terminal has survived Jexer's output is
     inversely proportional to how popular you think it is.  The
     "quiet" ones out there are usually a lot more solid than the
     "loud" ones.  (And a special shout out to RLogin and yaft: you
     are the _only_ two terminals that I have not seen crash, fill the
     screen with garbage, or be rendered unusable by Jexer's output.
     Of course, I crashed my own terminals the most. :-) Really great
     job from you two.)

* Composing widgets into usable larger components was hard.  I can see
  why people like HTML/CSS: it is squarely focused on this widget
  composition problem and now there are tons of tools that work great
  for it.  I still prefer Jexer's approach over Swing, but there just
  doesn't seem to be a great way to put 10+ widgets in one container
  and not be cooking a pot of spaghetti with callbacks/observers/etc.
  A lot of my Jexer-based applications stalled out because I would
  lose so much momentum and energy just fiddling with screens.

* The human part is hard.  Two bullet points up I get ranty, which
  really isn't where I want to be when trying to have fun in a teeny
  niche.  The truth is that all of us in this space are doing it
  because we want to, not because we have to.  And sometimes people --
  even really influential ones -- [need to move
  on,](https://gitlab.gnome.org/GNOME/vte/-/issues/259) and whether we
  agree or not with them on obscure technical points, we feel their
  loss.  We should all want the "alumni" so to say to feel like their
  time in this space gave them more positive than negative.  Maybe
  they [will come
  back.](https://gitlab.com/AutumnMeowMeow/jexer/-/issues/75#note_818607025)
  Maybe not.  I hope in the broad sense that other authors are
  celebrated for their wins, rather than torn down for ... whatever.

_Keep hacking on._
