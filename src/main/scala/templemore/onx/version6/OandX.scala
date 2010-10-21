package templemore.onx.version6

/**
 * @author Chris Turner
 */
object OandX {

  def main(args: Array[String]) {
    Grid.randomness = true

    val grid = new GridActor().start
    val noughts = new PlayerActor(Nought).start
    val crosses = new PlayerActor(Cross).start
    val game = new GameActor(grid, noughts, crosses).start

    game ! StartGame
  }
}