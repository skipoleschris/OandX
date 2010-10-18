package templemore.onx.version3

import Token._

/**
 * @author Chris Turner
 */
case class Line(row: Int, column: Int, tokens: Array[Token]) {

  def emptySpaces = {
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
