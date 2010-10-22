package templemore.onx.version4

/**
 * @author Chris Turner
 */
object OandXVersion4 {

  def main(args: Array[String]) {
    Grid.startWithRandom = true
    var grid = Grid()
    var token: Token = Nought

    while ( !grid.full_? && !grid.win_? ) {
        println("Player: " + token.symbol)
        grid = grid(grid.findBestPosition(token).get, token)
        println(grid.toString);
        token = token.flip
    }

    if ( grid.win_? ) {
        println("Game won by: " + grid.winningToken.get.symbol)
    }
    else {
        println("Game drawn")
    }
  }
}