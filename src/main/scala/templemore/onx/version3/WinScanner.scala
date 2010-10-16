package templemore.onx.version3

import Token._

/**
 * @author Chris Turner
 */
class WinScanner {

  def win_?(grid: Grid) = winningToken(grid) != null

  def winningToken(grid: Grid): Token = {
    var token = rowCheck(grid)
    if ( token != null ) return token

    token = columnCheck(grid)
    if ( token != null ) return token

    token = topToBottomDiagonalCheck(grid)
    if ( token != null ) return token

    return bottomToTopDiagonalCheck(grid)
  }

  private def rowCheck(grid: Grid): Token = {
    for ( row <- 0 to 2 ) {
      var noughtCount = 0
      var crossCount = 0
      for ( col <- 0 to 2 ) {
        grid.token(Position(row, col)) match {
          case Token.Nought => noughtCount += 1
          case Token.Cross => crossCount += 1
          case _ => {}
        }
      }

      if ( noughtCount == 3 ) return Token.Nought
      if ( crossCount == 3 ) return Token.Cross
    }
    null
  }

  private def columnCheck(grid: Grid): Token = {
    for ( col <- 0 to 2 ) {
      var noughtCount = 0
      var crossCount = 0
      for ( row <- 0 to 2 ) {
        grid.token(Position(row, col)) match {
          case Token.Nought => noughtCount += 1
          case Token.Cross => crossCount += 1
          case _ => {}
        }
      }

      if ( noughtCount == 3 ) return Token.Nought
      if ( crossCount == 3 ) return Token.Cross
    }
    null
  }

  private def topToBottomDiagonalCheck(grid: Grid): Token = {
    var noughtCount = 0
    var crossCount = 0
    for ( row <- 0 to 2; col <- 0 to 2 ) {
      if ( row == col ) {
        grid.token(Position(row, col)) match {
          case Token.Nought => noughtCount += 1
          case Token.Cross => crossCount += 1
          case _ => {}
        }
      }
    }

    if ( noughtCount == 3 ) Token.Nought
    else if ( crossCount == 3 ) Token.Cross
    else null
  }

  private def bottomToTopDiagonalCheck(grid: Grid): Token = {
    var noughtCount = 0
    var crossCount = 0
    for ( row <- 0 to 2; col <- 0 to 2 ) {
      if ( (row == 1 && col == 1) || (row == col + 2) || (col == row + 2)) {
        grid.token(Position(row, col)) match {
          case Token.Nought => noughtCount += 1
          case Token.Cross => crossCount += 1
          case _ => {}
        }
      }
    }

    if ( noughtCount == 3 ) Token.Nought
    else if ( crossCount == 3 ) Token.Cross
    else null
  }
}