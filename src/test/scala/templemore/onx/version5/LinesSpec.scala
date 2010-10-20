package templemore.onx.version5

import org.scalatest.matchers.MustMatchers
import org.scalatest.FunSuite

/**
 * @author Chris Turner
 */
class LinesSpec extends FunSuite with MustMatchers {

  test("a grid structure can be converted into lines") {
    object TestGrid extends Lines with TokensWithPositions {
      def tokensWithPositions() = List(Some(Nought), None, Some(Cross),
                                       Some(Cross), None, Some(Nought),
                                       Some(Nought), None, Some(Cross)) zip Grid.positions
    }

    val lines = TestGrid.lines
    println(lines)

    // O-X
    lines(0) must be (List((Some(Nought), Position(0, 0)),
                           (None, Position(0, 1)),
                           (Some(Cross), Position(0, 2))))

    // X-O
    lines(1) must be (List((Some(Cross), Position(1, 0)),
                           (None, Position(1, 1)),
                           (Some(Nought), Position(1, 2))))

    // O-X
    lines(2) must be (List((Some(Nought), Position(2, 0)),
                           (None, Position(2, 1)),
                           (Some(Cross), Position(2, 2))))

    // OXO
    lines(3) must be (List((Some(Nought), Position(0, 0)),
                           (Some(Cross), Position(1, 0)),
                           (Some(Nought), Position(2, 0))))

    // ---
    lines(4) must be (List((None, Position(0, 1)),
                           (None, Position(1, 1)),
                           (None, Position(2, 1))))

    // XOX
    lines(5) must be (List((Some(Cross), Position(0, 2)),
                           (Some(Nought), Position(1, 2)),
                           (Some(Cross), Position(2, 2))))

    // O-X
    lines(6) must be (List((Some(Nought), Position(0, 0)),
                           (None, Position(1, 1)),
                           (Some(Cross), Position(2, 2))))

    // O-X
    lines(7) must be (List((Some(Cross), Position(0, 2)),
                           (None, Position(1, 1)),
                           (Some(Nought), Position(2, 0))))
  }
}