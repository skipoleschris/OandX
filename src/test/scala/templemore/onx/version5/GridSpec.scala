package templemore.onx.version5

import org.scalatest.matchers.MustMatchers
import org.scalatest. {FeatureSpec, GivenWhenThen}

/**
 * @author Chris Turner
 */
class GridSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("The OnX grid") {

    info("As a player")
    info("I want to add tokens to the OnX grid")
    info("So that I can play the game")

    scenario("is empty when first created") {
      given("a new grid")
      val grid = Grid()

      then("the grid is not full")
      grid.full_? must be (false)

      and("no tokens are present")
      for ( row <- 0 to 2; col <- 0 to 2 ) {
         grid.tokenAt(Position(row, col)) must be (None)
      }
    }

    scenario("can have a token added to it") {
      given("a new grid")
      val grid = Grid()

      when("a token is added at a given position")
      val resultGrid = grid(Position(1, 1), Nought)
      println(resultGrid)

      then("that position contains the token")
      resultGrid.tokenAt(Position(1, 1)) must be (Some(Nought))
    }

    scenario("is not full if positions remain empty") {
      given("a partially filled grid")
      var grid = Grid()
      grid = grid(Position(1, 1), Nought)
      grid = grid(Position(0, 0), Nought)
      grid = grid(Position(2, 2), Nought)

      then("the grid is not full")
      grid.full_? must be (false)
    }

    scenario("is full if there are no empty positions") {
      given("a fully populated grid")
      var grid = Grid()
      for ( row <- 0 to 2; col <- 0 to 2 ) {
         grid = grid(Position(row, col), Nought)
      }

      then("the grid is full")
      grid.full_? must be (true)
    }

    scenario("can not have a token added at an already filled positon") {
      given("a grid with a position populated")
      var grid = Grid()
      grid = grid(Position(1, 1), Nought)

      when("placing a token at a fille position")
      intercept[IllegalStateException] {
        grid(Position(1, 1), Nought)

        then("an exception is thrown")
      }
    }

    scenario("can be constructed in a pre-configured state from a string") {
      given("a string representation of a OnX grid")
      val spec = """|O X
                    |XOO
                    |  X""".stripMargin

      when("a grid is created from it")
      val grid = Grid(spec)

      then("the positions are populated correctly")
      grid.tokenAt(Position(0, 0)) must be (Some(Nought))
      grid.tokenAt(Position(0, 1)) must be (None)
      grid.tokenAt(Position(0, 2)) must be (Some(Cross))
      grid.tokenAt(Position(1, 0)) must be (Some(Cross))
      grid.tokenAt(Position(1, 1)) must be (Some(Nought))
      grid.tokenAt(Position(1, 2)) must be (Some(Nought))
      grid.tokenAt(Position(2, 0)) must be (None)
      grid.tokenAt(Position(2, 1)) must be (None)
      grid.tokenAt(Position(2, 2)) must be (Some(Cross))
    }
  }
}
