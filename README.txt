Implementation of Noughts and Crosses (Tic-Tac-Toe) Game

Two computer players compete to win the game. Initial moves are random otherwise the game
is always drawn.

Various versions are provided showing the transition from a Java version to a fully functional
Scala implementation.


Version 1
---------
Implemented in imperative Java style with unit tests in Java using TestNG.
This Java version suffers from problems common to many Java projects:
- Poor encapsulation
- Exposing of internal implementation details of the Grid to the Line and Win Scanners
- Nasty if result != null chain in LineScanner


Version 2
---------
Improved Java source code but with unit tests written in Scala using ScalaTest.
Tests are DSL based and are more readable and informative.
Improved Java code:
- Better encapsulates grid structures
- Simplifies the Line and Win Scanners
- Improves the LineScanner to use a chain of filters


Version 3
---------
The Java source code is converted to Scala. No major refactorings are applied
other than to remove unecessary boilerplate code. Style is still imperative


Version 4
---------
A more Scala style implementation, built around immutable data structures, no null
values and the goal of cleaner and simpler code:
- Grid structure is immutable, returning a new grid on each change
- Now using Option[Token] instead of null
- Most operations now switched to a functional style
- Win and Line Scanners still too complex and difficult to understand


Version 5
---------
A full Scala implementation using a functional programming style, higher order functions,
immutable data and a DSL


Version 6
---------
An advanced Scala implementation that also adds Actors into the mix


