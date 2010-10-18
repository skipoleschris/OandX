package templemore.onx.version3

/**
 * @author Chris Turner
 */
class Grid {

  import Token._
  type Row = Array[Token]

  private val values = Array[Row](Array[Token](null, null, null),
                                  Array[Token](null, null, null),
                                  Array[Token](null, null, null))

  def token(position: Position) = values(position.row)(position.column)

  def token(position: Position, token: Token) = {
    if ( occupied_?(position) ) throw new IllegalStateException
    values(position.row).update(position.column, token)
  }

  def occupied_?(position: Position) = values(position.row)(position.column) != null

  def full_?(): Boolean = {
    for ( row <- 0 to 2; col <- 0 to 2 ) {
      if ( values(row)(col) == null ) return false
    }
    true
  }

  def lines: Array[Line] = {
    Array[Line](Line(0, -1, Array(token(Grid.TopLeft),
                                  token(Grid.TopMiddle),
                                  token(Grid.TopRight))),
                Line(1, -1, Array(token(Grid.MiddleLeft),
                                  token(Grid.Middle),
                                  token(Grid.MiddleRight))),
                Line(2, -1, Array(token(Grid.BottomLeft),
                                  token(Grid.BottomMiddle),
                                  token(Grid.BottomRight))),
                Line(-1, 0, Array(token(Grid.TopLeft),
                                  token(Grid.MiddleLeft),
                                  token(Grid.BottomLeft))),
                Line(-1, 1, Array(token(Grid.TopMiddle),
                                  token(Grid.Middle),
                                  token(Grid.BottomMiddle))),
                Line(-1, 2, Array(token(Grid.TopRight),
                                  token(Grid.MiddleRight),
                                  token(Grid.BottomRight))),
                Line(0, 0, Array(token(Grid.TopLeft),
                                 token(Grid.Middle),
                                 token(Grid.BottomRight))),
                Line(2, 0, Array(token(Grid.BottomLeft),
                                 token(Grid.Middle),
                                 token(Grid.TopRight))))
  }

  def corner_?(position: Position): Boolean = {
    position == Grid.TopLeft || position == Grid.TopRight ||
    position == Grid.BottomLeft || position == Grid.BottomRight
  }

  def linesWithMatchingTokenAndTwoSpaces(token: Token) = {
    var result = List[Line]()
    for ( line <- lines.toList ) {
      if ( line.emptySpaces == 2 && line.allSameToken_?(token) ) result = line :: result
    }
    result.reverse
  }

  def allPositionsOnEmptyLines = {
    var result = List[Position]()
    for ( line <- lines.toList ) {
      if ( line.emptySpaces == 3 ) {
        result = result ++ line.emptyPositions.toList.reverse
      }
    }
    result
  }

  override def toString = {
    var sb = ""
    for ( row <- 0 to 2 ) {
        sb = sb + "   |   |   \n"
        for ( col <- 0 to 2 ) {
            sb =sb + " "
            if ( values(row)(col) == null ) sb = sb + " "
            else sb = sb + Token.symbol(values(row)(col))
            sb = sb + " "
            if ( col < 2 ) sb = sb + "|"
        }
        sb = sb + "\n   |   |   \n"
        if ( row < 2 ) sb = sb + "-----------\n"
    }
    sb.toString
  }
}

object Grid {

  val TopLeft = Position(0, 0)
  val TopMiddle = Position(0, 1)
  val TopRight = Position(0, 2)
  val MiddleLeft = Position(1, 0)
  val Middle = Position(1, 1)
  val MiddleRight = Position(1, 2)
  val BottomLeft = Position(2, 0)
  val BottomMiddle = Position(2, 1)
  val BottomRight = Position(2, 2)

  import Token._

  def apply(pattern: String) = {
    val grid = new Grid
    val lines = pattern.split("\n")
    require(lines.length == 3)
    var row = 0;
    lines.foreach { line =>
      require(line.length == 3)
      var col = 0;
      line.foreach { token =>
        token match {
          case 'O' => grid.token(Position(row, col), Token.Nought)
          case 'X' => grid.token(Position(row, col), Token.Cross)
          case _ => {}
        }
        col += 1
      }
      row += 1
    }
    grid
  }
}