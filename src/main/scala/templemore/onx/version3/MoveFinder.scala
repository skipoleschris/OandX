package templemore.onx.version3

import scala.collection.mutable.Set
import Token._

/**
 * @author Chris Turner
 */
class MoveFinder(startWithRandom: Boolean) {

  def findBestPosition(grid: Grid, token: Token) = {
    val lines = buildLines(grid)

    var result = findWinningPosition(lines, token)

    if ( result == null ) {
      result = findBlockingPosition(lines, token)
    }

    if ( result == null ) {
      result = findDoubleWinPosition(lines, token)
    }

    if ( result == null ) {
      result = findDoubleBlockPosition(lines, token)
    }

    if ( result == null ) {
      result = findDoubleFreePosition(lines, token)
    }

    if ( result == null ) {
      result = findEmptyLinePosition(lines, token)
    }

    if ( result == null ) {
      result = findBestEmptyPosition(lines, token)
    }

    if ( result == null ) {
      throw new IllegalStateException
    }
    result
  }

  private def findWinningPosition(lines: Array[Line], token: Token): Position = {
    for ( line <- lines.toList ) {
      if ( line.hasOneEmptySpace_? && line.allSameToken_?(token) ) return line.emptyPosition
    }
    null;
  }

  private def findBlockingPosition(lines: Array[Line], token: Token): Position = findWinningPosition(lines, Token.flip(token))

  private def findDoubleWinPosition(lines: Array[Line], token: Token): Position = {
    var positions = Set[Position]()
    for ( line <- filterLinesWithTwoSpaces(lines, token) ) {
      for ( position <- line.emptyPositions.toList ) {
        if ( positions.contains(position) ) return position
        else positions.add(position)
      }
    }
    null
  }

  private def findDoubleBlockPosition(lines: Array[Line], token: Token) = findDoubleWinPosition(lines, Token.flip(token))

  private def findDoubleFreePosition(lines: Array[Line], token: Token): Position = {
    val candidates = filterLinesWithTwoSpaces(lines, token)
    for ( line <- candidates ) {
      for ( position <- line.emptyPositions.toArray ) {
        if ( position == Grid.Middle ) return position
      }
    }

    val emptyLinePositions = filterAllPositionsOnEmptyLines(lines)
    var firstMatch: Position = null
    for ( line <- candidates ) {
      for ( position <- line.emptyPositions.toArray ) {
        if ( emptyLinePositions.contains(position) ) return position
        if ( firstMatch == null ) firstMatch = position
      }
    }

    firstMatch
  }

  private def findEmptyLinePosition(lines: Array[Line], token: Token): Position = {
    val emptyLinePositions = filterAllPositionsOnEmptyLines(lines)

    if ( startWithRandom && emptyLinePositions.size > 5 ) {
      // Introduce some randomness
      val ordered = emptyLinePositions.toArray
      val random = new java.util.Random(System.currentTimeMillis)
      return ordered(random.nextInt(ordered.size))
    }

    if ( emptyLinePositions.contains(Grid.Middle) ) return Grid.Middle

    for ( position <- emptyLinePositions ) {
      if ( corner_?(position) ) return position
    }

    if ( !emptyLinePositions.isEmpty ) emptyLinePositions.toList.head
    else null
  }

  private def findBestEmptyPosition(lines: Array[Line], token: Token): Position = {
    var emptyPositions = Set[Position]()
    for ( line <- lines.toList ) {
      emptyPositions = emptyPositions ++ line.emptyPositions
    }
    if ( emptyPositions.contains(Grid.Middle) ) return Grid.Middle

    for ( position <- emptyPositions ) {
      if ( corner_?(position) ) return position
    }
    if ( !emptyPositions.isEmpty ) emptyPositions.toList.head
    else null
  }

  private def filterLinesWithTwoSpaces(lines: Array[Line], token: Token) = {
    var result = List[Line]()
    for ( line <- lines.toList ) {
      if ( line.hasTwoEmptySpace_? && line.allSameToken_?(token) ) result = line :: result
    }
    result.reverse
  }

  private def filterAllPositionsOnEmptyLines(lines: Array[Line]) = {
    var result = List[Position]()
    for ( line <- lines ) {
      if ( line.hasThreeEmptySpace_? ) {
        result = result ++ line.emptyPositions.toList.reverse
      }
    }
    result
  }

  private def corner_?(position: Position): Boolean = {
    position == Grid.TopLeft || position == Grid.TopRight ||
    position == Grid.BottomLeft || position == Grid.BottomRight
  }

  private def buildLines(grid: Grid): Array[Line] = {
    Array[Line](Line(0, -1, Array(grid.token(Grid.TopLeft),
                                  grid.token(Grid.TopMiddle),
                                  grid.token(Grid.TopRight))),
                Line(1, -1, Array(grid.token(Grid.MiddleLeft),
                                  grid.token(Grid.Middle),
                                  grid.token(Grid.MiddleRight))),
                Line(2, -1, Array(grid.token(Grid.BottomLeft),
                                  grid.token(Grid.BottomMiddle),
                                  grid.token(Grid.BottomRight))),
                Line(-1, 0, Array(grid.token(Grid.TopLeft),
                                  grid.token(Grid.MiddleLeft),
                                  grid.token(Grid.BottomLeft))),
                Line(-1, 1, Array(grid.token(Grid.TopMiddle),
                                  grid.token(Grid.Middle),
                                  grid.token(Grid.BottomMiddle))),
                Line(-1, 2, Array(grid.token(Grid.TopRight),
                                  grid.token(Grid.MiddleRight),
                                  grid.token(Grid.BottomRight))),
                Line(0, 0, Array(grid.token(Grid.TopLeft),
                                 grid.token(Grid.Middle),
                                 grid.token(Grid.BottomRight))),
                Line(2, 0, Array(grid.token(Grid.BottomLeft),
                                 grid.token(Grid.Middle),
                                 grid.token(Grid.TopRight))))
  }

  case class Line(row: Int, column: Int, tokens: Array[Token]) {

    def hasOneEmptySpace_? = countEmptySpaces == 1
    def hasTwoEmptySpace_? = countEmptySpaces == 2
    def hasThreeEmptySpace_? = countEmptySpaces == 3

    private def countEmptySpaces = {
      var empty = 0
      tokens.foreach { token => if ( token == null ) empty += 1 }
      empty
    }

    def emptyPosition = emptyPositions()(0)

    def emptyPositions(): Array[Position] = {
      var result = List[Position]()
      for ( i <- 0 to (tokens.length - 1) ) {
        if ( tokens(i) == null ) {
          if ( column == -1 ) result = Position(row, i) :: result
          else if ( row == -1 ) result = Position(i, column) :: result
          else if ( i == 0 ) result = Position(row, column) :: result
          else if ( i == 1 ) result = Position(1, 1) :: result
          else if ( row == 0 ) result = Position(2, 2) :: result
          else result = Position(column, row) :: result
        }
      }
      result.toArray
    }

    def allSameToken_?(token: Token) = tokens.forall { t => !((t != null) && (t != token)) }
  }
}