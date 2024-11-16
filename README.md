# Turbo Vision File Manager Reimagined - TVFM Java Edition

## Description

Turbo Vision File Manager (TVFM) is a historical application originally developed in 1992 using [Borland Pascal 7.0](https://en.wikipedia.org/wiki/Turbo_Pascal) and the Borland's [Turbo
Vision](http://en.wikipedia.org/wiki/Turbo_Vision) library. It was designed to offer users an intuitive and powerful text-based interface for managing files and directories in the DOS operating system. Inspired by text-based file managers like Norton Commander, TVFM utilized the sophisticated Turbo Vision UI framework, which is known for its "windowed" look and modular approach.

## Java Revival

This project aims to reimagine and rewrite TVFM using **Jexer**, a Java library that replicates the Turbo Vision interface. The modern version of TVFM will include the following features:

- **Advanced Textual Interface:** Utilizing [Jexer](https://github.com/sdimaio/jexer), we will reproduce the window-based user interface of Turbo Vision, maintaining the retro look and keyboard-driven interaction.
- **Cross-Platform Compatibility:** The new version will be written in Java, allowing it to run on various operating systems, including Linux (even without a graphical user interface), macOS, and Windows.
- **Full File Management:** Support for navigating the file system, copying, moving, renaming, and deleting files, all while preserving the user experience of the original application.
- **Extensibility and Open Source:** The project is modular and open source, inviting contributions for new features and improvements.
- **Kubernetes Integration Support (Future):** A potential future extension to integrate with Kubernetes Client API, offering a text-based management interface for Kubernetes clusters, even in environments without GUI.

## Background and History

TVFM was initially created in the early 1990s as a demo to showcase the capabilities of Turbo Vision, a framework that set a standard for text-based user interface development. The application was designed for use in DOS environments and demonstrated excellent object-oriented design, typical of Object Pascal language.

As time passed and the DOS era came to an end, Turbo Vision was gradually abandoned. However, its aesthetics and functionality continue to be appreciated, and projects like Jexer keep its spirit alive.

## Project Goals

1. **Migration and Modernization:** Transform the original Object Pascal code into Java, preserving the interface and functionality while updating the architecture for the modern context.
2. **Compatibility and Accessibility:** Create an application that can run in server or terminal environments without a GUI, maintaining the classic retro look and feel.
3. **Open Source and Community:** Encourage community participation, welcoming contributions and improvements from developers.

## How to Contribute

- **Clone the Repository:** Download the code and try the application in your Java console.
- **Issue Reporting:** Report bugs, suggest enhancements, and share your ideas.
- **Pull Requests:** We welcome contributions with new features, refactoring, or performance improvements.

## Technologies Used

- **Java 17+**
- **Jexer TUI Library**
- **Maven/Gradle for Project Management**
- **GitHub Actions for Continuous Integration (CI)**
- **Kubernetes Client API (Future)**

---

