package templemore.onx.version2

import org.scalatest.matchers.MustMatchers
import org.scalatest. {FeatureSpec, GivenWhenThen}

/**
 * @author Chris Turner
 */
class PositionSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("A position") {

    scenario("knows its row and column") {
      given("a position")
      val position = new Position(2, 3)

      then("it should know its row")
      position.getRow must be (2)

      and("it should know its column")
      position.getColumn must be (3)
    }

    scenario("should equal another position with the same row and column") {
      given("two identical positions")
      val position1 = new Position(1, 2)
      val position2 = new Position(1, 2)

      then("they should be equal")
      position1 must be (position2)
    }

    scenario("should not equal another position with different row and column") {
      given("two identical positions")
      val position1 = new Position(1, 2)
      val position2 = new Position(2, 1)

      then("they should be equal")
      position1 must not be (position2)
    }
  }
}