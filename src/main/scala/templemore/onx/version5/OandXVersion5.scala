package templemore.onx.version5

/**
 * @author Chris Turner
 */
object OandXVersion5 {

  def main(args: Array[String]) {
    Grid.randomness = true
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