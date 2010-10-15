package templemore.onx.version2

import org.scalatest.matchers.MustMatchers
import org.scalatest. {FeatureSpec, GivenWhenThen}

/**
 * @author Chris Turner
 */
class WinScannerSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("The win scanner") {

    scenario("can find a horizontal win on the grid") {
      given("a grid with three matching tokens on a horizontal line")
      val grid = Grid.build("""|OOO
                               |---
                               |---""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report a win")
      scanner.isWin(grid) must be (true)

      and("the winning token should be a nought")
      scanner.winningToken(grid) must be (Token.nought)
    }

    scenario("can find a vertical win on the grid") {
      given("a grid with three matching tokens on a vertical line")
      val grid = Grid.build("""|O--
                               |O--
                               |O--""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report a win")
      scanner.isWin(grid) must be (true)

      and("the winning token should be a nought")
      scanner.winningToken(grid) must be (Token.nought)
    }

    scenario("can find a top to bottom diagonal win on the grid") {
      given("a grid with three matching tokens on a diagonal line")
      val grid = Grid.build("""|O--
                               |-O-
                               |--O""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report a win")
      scanner.isWin(grid) must be (true)

      and("the winning token should be a nought")
      scanner.winningToken(grid) must be (Token.nought)
    }

    scenario("can find a bottom to top diagonal win on the grid") {
      given("a grid with three matching tokens on a diagonal line")
      val grid = Grid.build("""|--O
                               |-O-
                               |O--""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report a win")
      scanner.isWin(grid) must be (true)

      and("the winning token should be a nought")
      scanner.winningToken(grid) must be (Token.nought)
    }

    scenario("finds no winner on an empty grid") {
      given("an empty grid")
      val grid = new Grid

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report no win")
      scanner.isWin(grid) must be (false)
    }

    scenario("finds no winner on a drawn grid") {
      given("a drawn grid")
      val grid = Grid.build("""|OXO
                               |XOX
                               |XOX""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report no win")
      scanner.isWin(grid) must be (false)
    }

    scenario("finds no winner on a partially complete grid") {
      given("a partially complete grid")
      val grid = Grid.build("""|OX-
                               |XOX
                               |--X""".stripMargin)

      and("a win scanner")
      val scanner = new WinScanner

      then("the scanner should report no win")
      scanner.isWin(grid) must be (false)
    }
  }
}