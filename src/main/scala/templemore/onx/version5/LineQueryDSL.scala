package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait LineQueryDSL {
  this: Lines =>

  def find = new SingleResultFilter

  // Filter that extracts the correct information from the result
  class SingleResultFilter {

    def the(filter: ResultFilter) = apply(filter)
    def a(filter: ResultFilter) = apply(filter)
    def an(filter: ResultFilter) = apply(filter)

    private[this] def apply(filter: ResultFilter) = new LineContentFilter
  }

  sealed trait ResultFilter
  case object token extends ResultFilter
  case object emptyPosition extends ResultFilter
  case object emptyMiddlePosition extends ResultFilter
  case object emptyCornerPosition extends ResultFilter
  case object randomEmptyPosition extends ResultFilter

  // Filter that extracts the correct set of lines from the result
  class LineContentFilter {

    def on(filter: LineSelector) = new LineFilter
    def shared(filter: SharingSelector) = new LineFilter
  }

  sealed trait LineSelector
  case object first extends LineSelector
  case object any extends LineSelector
  sealed trait SharingSelector
  case object by extends SharingSelector

  // Filter that picks lines matching a given criteria
  class LineFilter {

    def line(filter: HavingTrait) = new LineContentMatcher
    def lines(filter: HavingTrait) = new LineContentMatcher
  }

  sealed trait HavingTrait
  case object having extends HavingTrait


  // Matcher that defines the criteria being matched on
  class LineContentMatcher {

    def one(position: PositionTrait) = this
    def two(position: PositionTrait) = this
    def all(position: PositionTrait) = this
    def any(position: PositionTrait) = this

    def and(pattern: AlsoTrait) = this

    def matching(token: Option[Token]) = this
    def identical = () => { None }

  }

  sealed trait PositionTrait
  case object position extends PositionTrait
  case object positions extends PositionTrait

  sealed trait AlsoTrait
  case object also extends AlsoTrait
}