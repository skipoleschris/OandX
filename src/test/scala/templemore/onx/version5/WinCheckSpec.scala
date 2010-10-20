package templemore.onx.version5

import org.scalatest.FunSuite
import org.scalatest.matchers.MustMatchers

/**
 * @author Chris Turner
 */
class WinCheckSpec extends FunSuite with MustMatchers {

  test("the winning token can be found on a winning grid") {
    object TestGrid extends WinCheck with LineQueryDSL with Lines with TokensWithPositions {
      def tokensWithPositions() = List(Some(Nought), Some(Nought), Some(Cross),
                                       Some(Cross), Some(Nought), Some(Cross),
                                       Some(Nought), None, Some(Cross)) zip Grid.positions
    }

    TestGrid.win_? must be (true)
    TestGrid.winningToken must be (Some(Cross))
  }

  test("no winning token can be found on a drawn grid") {
    object TestGrid extends WinCheck with LineQueryDSL with Lines with TokensWithPositions {
      def tokensWithPositions() = List(Some(Nought), Some(Nought), Some(Cross),
                                       Some(Cross), Some(Nought), Some(Nought),
                                       Some(Nought), Some(Cross), Some(Cross)) zip Grid.positions
    }

    TestGrid.win_? must be (false)
    TestGrid.winningToken must be (None)
  }
}
