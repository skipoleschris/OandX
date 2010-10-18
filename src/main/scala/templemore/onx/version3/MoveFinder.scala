package templemore.onx.version3

import scala.collection.mutable.Set
import Token._

/**
 * @author Chris Turner
 */
class MoveFinder(startWithRandom: Boolean) {

  private val finders = Array[PositionFinder](new WinningPositionFinder(),
                                              new BlockingPositionFinder(),
                                              new DoubleWinPositionFinder(),
                                              new DoubleBlockPositionFinder(),
                                              new DoubleFreePositionFinder(),
                                              new EmptyLinePositionFinder(),
                                              new BestEmptyPositionFinder())

  def findBestPosition(grid: Grid, token: Token): Position = {
    for ( finder <- finders ) {
        val position = finder.findPosition(grid, token)
        if ( position != null ) return position
    }
    throw new IllegalStateException()
  }

  trait PositionFinder {
    def findPosition(grid: Grid, token: Token): Position
  }

  private class WinningPositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position = {
      for ( line <- grid.lines.toList ) {
        if ( line.emptySpaces == 1 && line.allSameToken_?(token) ) return line.emptyPosition
      }
      null;
    }
  }

  private class BlockingPositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position =
      new WinningPositionFinder().findPosition(grid, Token.flip(token))
  }

  private class DoubleWinPositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position = {
      var positions = Set[Position]()
      for ( line <- grid.linesWithMatchingTokenAndTwoSpaces(token) ) {
        for ( position <- line.emptyPositions.toList ) {
          if ( positions.contains(position) ) return position
          else positions.add(position)
        }
      }
      null
    }
  }

  private class DoubleBlockPositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token) =
      new DoubleWinPositionFinder().findPosition(grid, Token.flip(token))
  }

  private class DoubleFreePositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position = {
      val candidates = grid.linesWithMatchingTokenAndTwoSpaces(token)
      for ( line <- candidates ) {
        for ( position <- line.emptyPositions.toArray ) {
          if ( position == Grid.Middle ) return position
        }
      }

      val emptyLinePositions = grid.allPositionsOnEmptyLines
      var firstMatch: Position = null
      for ( line <- candidates ) {
        for ( position <- line.emptyPositions.toArray ) {
          if ( emptyLinePositions.contains(position) ) return position
          if ( firstMatch == null ) firstMatch = position
        }
      }

      firstMatch
    }
  }

  private class EmptyLinePositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position = {
      val emptyLinePositions = grid.allPositionsOnEmptyLines

      if ( startWithRandom && emptyLinePositions.size > 5 ) {
        // Introduce some randomness
        val ordered = emptyLinePositions.toArray
        val random = new java.util.Random(System.currentTimeMillis)
        return ordered(random.nextInt(ordered.size))
      }

      if ( emptyLinePositions.contains(Grid.Middle) ) return Grid.Middle

      for ( position <- emptyLinePositions ) {
        if ( grid.corner_?(position) ) return position
      }

      if ( !emptyLinePositions.isEmpty ) emptyLinePositions.toList.head
      else null
    }
  }

  private class BestEmptyPositionFinder extends PositionFinder {
    def findPosition(grid: Grid, token: Token): Position = {
      var emptyPositions = Set[Position]()
      for ( line <- grid.lines.toList ) {
        emptyPositions = emptyPositions ++ line.emptyPositions
      }
      if ( emptyPositions.contains(Grid.Middle) ) return Grid.Middle

      for ( position <- emptyPositions ) {
        if ( grid.corner_?(position) ) return position
      }
      if ( !emptyPositions.isEmpty ) emptyPositions.toList.head
      else null
    }
  }
}