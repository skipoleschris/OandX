package templemore.onx.version5

import org.scalatest. {FeatureSpec, GivenWhenThen}
import org.scalatest.matchers. {ShouldMatchers, MustMatchers}

/**
 * @author Chris Turner
 */
class MoveFinderSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("A move finder") {

    // Test to find that we can win if possible

    scenario("can find a horizontal winning position") {
      given("a grid with a horizontal winning position")
      val grid = Grid("""|OO-
                         |---
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("a winning position should be found")
      position must be (Some(Position(0, 2)))
    }

    scenario("can find a vertical winning position") {
      given("a grid with a vertical winning position")
      val grid = Grid("""|O--
                         |O--
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("a winning position should be found")
      position must be (Some(Position(2, 0)))
    }

    scenario("can find a diagonal winning position") {
      given("a grid with a diagonal winning position")
      val grid = Grid("""|---
                         |-O-
                         |O--""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("a winning position should be found")
      position must be (Some(Position(0, 2)))
    }

    // Test to find that if we can't win then we can at least block the opponent from winning

    scenario("can find a position to block an opponent from winning horizontally") {
      given("a grid with an opponent with a horizontal winning position")
      val grid = Grid("""|OO-
                         |---
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Cross)

      then("a blocking position should be found")
      position must be (Some(Position(0, 2)))
    }

    scenario("can find a position to block an opponent from winning vertically") {
      given("a grid with an opponent with a vertical winning position")
      val grid = Grid("""|O--
                         |O--
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Cross)

      then("a blocking position should be found")
      position must be (Some(Position(2, 0)))
    }

    scenario("can find a position to block an opponent from winning diagonally") {
      given("a grid with an opponent with a diagonal winning position")
      val grid = Grid("""|---
                         |-O-
                         |O--""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Cross)

      then("a blocking position shold be found")
      position must be (Some(Position(0, 2)))
    }

    // Test to find that if we can't win an block we can find a position that gives us a
    // certain win on the next go

    scenario("can find a position that gives us two positions to win on the next turn") {
      given("a grid with the ability to create two future winning positions")
      val grid = Grid("""|OX-
                         |-O-
                         |--X""".stripMargin)

      and("a set of valid possible positions")
      val possiblePositions = Set(Position(1, 0), Position(2, 0))

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("a blocking position shold be found")
      position must not be (None)
      possiblePositions must contain (position.get)
    }

    // Test to find that if we can't find a double win spot that we can block out opponent
    // from finding a double win spot

    scenario("can find a position that blocks an apponent from having two positions to win on the next turn") {
      given("a grid with the ability for an opponent to create two future winning positions")
      val grid = Grid("""|OX-
                         |-O-
                         |--X""".stripMargin)

      and("a set of valid possible positions")
      val possiblePositions = Set(Position(1, 0), Position(2, 0))

      when("the best position is requested")
      val position = grid.findBestPosition(Cross)

      then("a blocking position shold be found")
      position must not be (None)
      possiblePositions must contain (position.get)
    }

    // Tests to find that when there are no win or block options that we pick the best
    // alternative position

    scenario("can find the middle position when it is empty and on a line with only one token of the same kind") {
      given("a grid with a line through the middle contaning only one token of the same kind")
      val grid = Grid("""|O--
                         |---
                         |-X-""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("the middle position should be found")
      position must be (Some(Position(1, 1)))
    }

    scenario("can find a position on a line with only one token of the same kind") {
      given("a grid with a line contaning only one token of the same kind")
      val grid = Grid("""|O--
                         |-X-
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("the most optimal position should be found")
      position must be (Some(Position(0, 1)))
    }

    scenario("can find the middle position on an empty line") {
      given("a grid with an empty ling passing throught the middle position")
      val grid = Grid("""|---
                         |---
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("the middle position should be found")
      position must be (Some(Position(1, 1)))
    }

    scenario("can find a position on an empty line") {
      given("a grid with an empty line")
      val grid = Grid("""|---
                         |-X-
                         |---""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("the most optimal position should be found")
      position must be (Some(Position(0, 0)))
    }

    scenario("can find the middle position if free and no other options") {
      given("a grid with an empty line")
      val grid = Grid("""|OOX
                         |X-O
                         |XXO""".stripMargin)

      when("the best position is requested")
      val position = grid.findBestPosition(Nought)

      then("the middle position should be found")
      position must be (Some(Position(1, 1)))
    }
  }
}
