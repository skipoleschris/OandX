package templemore.onx.version3

import org.scalatest.matchers.MustMatchers
import org.scalatest. {FeatureSpec, GivenWhenThen}

/**
 * @author Chris Turner
 */
class GridSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  import Token._

  feature("The OnX grid") {

    info("As a player")
    info("I want to add tokens to the OnX grid")
    info("So that I can play the game")

    scenario("is empty when first created") {
      given("a new grid")
      val grid = new Grid

      then("the grid is not full")
      grid.full_? must be (false)

      and("no tokens are present")
      for ( row <- 0 to 2; col <- 0 to 2 ) {
         grid.token(Position(row, col)) must be (null)
      }
    }

    scenario("can have a token added to it") {
      given("a new grid")
      val grid = new Grid

      when("a token is added at a given position")
      grid.token(Grid.Middle, Token.Nought)

      then("that position contains the token")
      grid.token(Grid.Middle) must be (Token.Nought)
    }

    scenario("is not full if positions remain empty") {
      given("a partially filled grid")
      val grid = new Grid
      grid.token(Grid.Middle, Token.Nought)
      grid.token(Grid.TopLeft, Token.Nought)
      grid.token(Grid.BottomRight, Token.Nought)

      then("the grid is not full")
      grid.full_? must be (false)
    }

    scenario("is full if there are no empty positions") {
      given("a fully populated grid")
      val grid = new Grid
      for ( row <- 0 to 2; col <- 0 to 2 ) {
         grid.token(Position(row, col), Token.Nought)
      }

      then("the grid is full")
      grid.full_? must be (true)
    }

    scenario("can not have a token added at an already filled positon") {
      given("a grid with a position populated")
      val grid = new Grid
      grid.token(Grid.Middle, Token.Nought)

      when("placing a token at a fille position")
      intercept[IllegalStateException] {
        grid.token(Grid.Middle, Token.Nought)

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
      grid.token(Grid.TopLeft) must be (Token.Nought)
      grid.occupied_?(Grid.TopMiddle) must be (false)
      grid.token(Grid.TopRight) must be (Token.Cross)
      grid.token(Grid.MiddleLeft) must be (Token.Cross)
      grid.token(Grid.Middle) must be (Token.Nought)
      grid.token(Grid.MiddleRight) must be (Token.Nought)
      grid.occupied_?(Grid.BottomLeft) must be (false)
      grid.occupied_?(Grid.BottomMiddle) must be (false)
      grid.token(Grid.BottomRight) must be (Token.Cross)
    }
  }
}
