package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait LineQueryDSL {
  this: Lines =>

  def find = new ContentMatcher

  class ContentMatcher {

    private var count: Int = _

    def linesHaving(count: Int) = { this.count = count; this }

    def tokens(rule: MatchRule) = rule match {
      case Identical => new LineSelector(lines.filter(tokenFrequencies(_).find(_._2 == count).isDefined))
    }
  }

  sealed trait MatchRule
  case object Identical extends MatchRule

  class LineSelector(lines: List[List[Pair[Option[Token], Position]]]) {

    def select(bound: SelectorBound) = bound match {
      case First => lines match {
        case Nil => new LineWrapper(None)
        case x :: xs => new LineWrapper(Some(x))
      }
    }
  }

  sealed trait SelectorBound
  case object First extends SelectorBound

  class LineWrapper(line: Option[List[Pair[Option[Token], Position]]]) {

    def takeToken(index: Int): Option[Token] = line match {
      case Some(l) => {
        val tokens = l.map(_._1).filter(_ != None)
        if ( tokens.length >= index ) tokens(index - 1) else None
      }
      case _ => None
    }
  }

  def tokenFrequencies(line: List[Pair[Option[Token], Position]]) = {
    Map(Some(Nought) -> line.count(_._1 == Some(Nought)),
        Some(Cross) -> line.count(_._1 == Some(Cross)),
        None -> line.count(_._1 == None))
  }

//  def firstLine(lines: List[List[Pair[Option[Token], Position]]]) = lines match {
//    case Nil => None
//    case x :: xs => Some(x)
//  }

//  def find = new SingleResultFilter
//
//
//  // Helper functions
//  private def queryAllTokens(lines: List[List[Pair[Option[Token], Position]]]) = lines.flatMap(_.map(_._1))
//
//  private def queryFirst[T](items: List[T]): Option[T] = items.filter(_ != None) match {
//    case Nil => None
//    case x :: xs => Some(x)
//  }
//
//  // Filter that extracts the correct information from the result
//  class SingleResultFilter {
//
//    def the(filter: ResultFilter) = chain(filter)
//    def a(filter: ResultFilter) = chain(filter)
//    def an(filter: ResultFilter) = chain(filter)
//
//      //case token => (lines: List[List[Pair[Option[Token], Position]]]) => { queryAllTokens(lines) }
//    private[this] def chain(filter: ResultFilter) = filter match {
//      case Token => new LineContentFilter[Token]((lines: List[List[Pair[Option[Token], Position]]]) => { queryFirst(queryAllTokens(lines)) getOrElse None })
//      case EmptyPosition => new LineContentFilter[Position]((lines: List[List[Pair[Option[Token], Position]]]) => { None })
//      case EmptyMiddlePosition => new LineContentFilter[Position]((lines: List[List[Pair[Option[Token], Position]]]) => { None })
//      case EmptyCornerPosition => new LineContentFilter[Position]((lines: List[List[Pair[Option[Token], Position]]]) => { None })
//      case RandomEmptyPosition => new LineContentFilter[Position]((lines: List[List[Pair[Option[Token], Position]]]) => { None })
//    }
//  }
//
//  sealed trait ResultFilter
//  case object Token extends ResultFilter
//  case object EmptyPosition extends ResultFilter
//  case object EmptyMiddlePosition extends ResultFilter
//  case object EmptyCornerPosition extends ResultFilter
//  case object RandomEmptyPosition extends ResultFilter
//
//  // Filter that extracts the correct set of lines from the result
//  class LineContentFilter[T](chain: (List[List[Pair[Option[Token], Position]]]) => Option[T]) {
//
//    def on(filter: LineSelector) = filter match {
//      case First => new LineFilter[T]((lines: List[List[Pair[Option[Token], Position]]]) => { chain(List(queryFirst(lines) getOrElse List())) })
//      case Any => new LineFilter[T]((lines: List[List[Pair[Option[Token], Position]]]) => { chain(lines) })
//  }
//    def shared(filter: SharingSelector) = new LineFilter[T]((lines: List[List[Pair[Option[Token], Position]]]) => { chain(lines) })
//  }
//
//  sealed trait LineSelector
//  case object First extends LineSelector
//  case object Any extends LineSelector
//  sealed trait SharingSelector
//  case object By extends SharingSelector
//
//  // Filter that picks lines matching a given criteria
//  class LineFilter[T](chain: (List[List[Pair[Option[Token], Position]]]) => Option[T]) {
//
//    def line(filter: HavingTrait) = new PositionMatcher[T](chain)
//    def lines(filter: HavingTrait) = new PositionMatcher[T](chain)
//  }
//
//  sealed trait HavingTrait
//  case object Having extends HavingTrait
//
//
//  // Matcher that defines the positions being matched on
//  class PositionMatcher[T](chain: (List[List[Pair[Option[Token], Position]]]) => Option[T]) {
//
//    def one(position: PositionTrait) = new TokenMatcher[T]
//    def two(position: PositionTrait) = new TokenMatcher[T]
//    def all(position: PositionTrait) = new TokenMatcher[T]
//    def any(position: PositionTrait) = new TokenMatcher[T]
//  }
//
//  sealed trait PositionTrait
//  case object Position extends PositionTrait
//  case object Positions extends PositionTrait
//
//   // Matcher that matches a specific token
//  class TokenMatcher[T] {
//
//    def matching(token: Option[Token]) = new AppendingMatcher[T]
//    def identical = () => new AppendingMatcher[T]
//  }
//
//  // Appending matcher
//  class AppendingMatcher[T] {
//
//    def go(): Option[T] = None
//    def and(pattern: AlsoTrait) = new PositionMatcher((x: List[List[Pair[Option[Token], Position]]]) => { None })
//
//  }
//
//  sealed trait AlsoTrait
//  case object Also extends AlsoTrait
}