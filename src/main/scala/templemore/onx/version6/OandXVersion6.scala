package templemore.onx.version6

import templemore.onx.version5. {Grid, Cross, Nought}

/**
 * @author Chris Turner
 */
object OandXVersion6 {

  def main(args: Array[String]) {
    Grid.randomness = true

    val grid = new GridActor().start
    val noughts = new PlayerActor(Nought).start
    val crosses = new PlayerActor(Cross).start
    val game = new GameActor(grid, noughts, crosses).start

    game ! StartGame
  }
}