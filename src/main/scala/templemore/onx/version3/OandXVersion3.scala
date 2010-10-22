package templemore.onx.version3

import Token._

/**
 * @author Chris Turner
 */
object OandXVersion3 {

  def main(args: Array[String]) {
    val grid = new Grid
    val winScanner = new WinScanner
    val moveFinder = new MoveFinder(true)
    var token = Token.Nought

    while ( !grid.full_? && !winScanner.win_?(grid) ) {
        println("Player: " + Token.symbol(token))
        grid.token(moveFinder.findBestPosition(grid, token), token)
        println(grid.toString);
        token = Token.flip(token)
    }

    if ( winScanner.win_?(grid) ) {
        println("Game won by: " + Token.symbol(winScanner.winningToken(grid)))
    }
    else {
        println("Game drawn")
    }
  }
}