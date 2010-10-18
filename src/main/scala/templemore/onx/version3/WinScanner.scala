package templemore.onx.version3

import Token._

/**
 * @author Chris Turner
 */
class WinScanner {

  def win_?(grid: Grid) = winningToken(grid) != null

  def winningToken(grid: Grid): Token = {
    for ( line <- grid.lines ) {
      var noughtCount = 0
      var crossCount = 0
      for ( token <- line.tokens ) {
        if (Token.Nought == token) noughtCount += 1
        else if (Token.Cross == token) crossCount += 1
      }
      if (noughtCount == 3) return Token.Nought
      if (crossCount == 3) return Token.Cross
    }
    return null
  }
}