package templemore.onx.version2

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
      val grid = new Grid

      then("the grid is not full")
      grid.isFull must be (false)

      and("no tokens are present")
      for ( row <- 0 to (Grid.HEIGHT -1); col <- 0 to (Grid.WIDTH - 1) ) {
         grid.getToken(new Position(row, col)) must be (null)
      }
    }

    scenario("can have a token added to it") {
      given("a new grid")
      val grid = new Grid

      when("a token is added at a given position")
      grid.addToken(Grid.MIDDLE, Token.nought)

      then("that position contains the token")
      grid.getToken(Grid.MIDDLE) must be (Token.nought)
    }

    scenario("is not full if positions remain empty") {
      given("a partially filled grid")
      val grid = new Grid
      grid.addToken(Grid.MIDDLE, Token.nought)
      grid.addToken(Grid.TOP_LEFT, Token.nought)
      grid.addToken(Grid.BOTTOM_RIGHT, Token.nought)

      then("the grid is not full")
      grid.isFull must be (false)
    }

    scenario("is full if there are no empty positions") {
      given("a fully populated grid")
      val grid = new Grid
      for ( row <- 0 to (Grid.HEIGHT -1); col <- 0 to (Grid.WIDTH - 1) ) {
         grid.addToken(new Position(row, col), Token.nought)
      }

      then("the grid is full")
      grid.isFull must be (true)
    }

    scenario("can not have a token added at an already filled positon") {
      given("a grid with a position populated")
      val grid = new Grid
      grid.addToken(Grid.MIDDLE, Token.nought)

      when("placing a token at a fille position")
      intercept[IllegalStateException] {
        grid.addToken(Grid.MIDDLE, Token.nought)

        then("an exception is thrown")
      }
    }

    //TODO: Add test for get all lines method

    scenario("can be constructed in a pre-configured state from a string") {
      given("a string representation of a OnX grid")
      val spec = """|O X
                    |XOO
                    |  X""".stripMargin

      when("a grid is created from it")
      val grid = Grid.build(spec)

      then("the positions are populated correctly")
      grid.getToken(Grid.TOP_LEFT) must be (Token.nought)
      grid.isPositionOccupied(Grid.TOP_MIDDLE) must be (false)
      grid.getToken(Grid.TOP_RIGHT) must be (Token.cross)
      grid.getToken(Grid.MIDDLE_LEFT) must be (Token.cross)
      grid.getToken(Grid.MIDDLE) must be (Token.nought)
      grid.getToken(Grid.MIDDLE_RIGHT) must be (Token.nought)
      grid.isPositionOccupied(Grid.BOTTOM_LEFT) must be (false)
      grid.isPositionOccupied(Grid.BOTTOM_MIDDLE) must be (false)
      grid.getToken(Grid.BOTTOM_RIGHT) must be (Token.cross)
    }
  }
}