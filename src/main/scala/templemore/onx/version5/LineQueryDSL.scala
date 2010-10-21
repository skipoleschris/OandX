package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait LineQueryDSL {
  this: Lines =>

  type Line = List[Pair[Option[Token], Position]]

  def find = new ContentMatcher(lines)

  // Matcher that filters lines down to those that have a certain number
  // of elements matching a particular criteria
  class ContentMatcher(lines: List[Line]) {

    private var count: Int = _
    private var atLeastOne = false

    def linesHaving(count: Int) = { this.count = count; this }

    def linesHaving(rule: MatchRule) = { rule match {
        case AtLeastOne => atLeastOne = true
        case _ => throw new IllegalStateException
      }
      this
    }

    def tokens(rule: MatchRule) = rule match {
      case Identical => new LineSelector(lines.filter(tokenFrequencies(_).find(_._2 == count).isDefined))
      case _ => throw new IllegalStateException
    }

    def tokenMatching(token: Token) = new LineSelector(lines.filter(tokenFrequency(_, token) == count))

    def tokensMatching(token: Token) = tokenMatching(token)

    def position(rule: MatchRule) = rule match {
      case Empty => {
        if ( atLeastOne ) new LineSelector(lines.filter(_.count(_._1 == None) > 0))
        else new LineSelector(lines.filter(_.count(_._1 == None) == count))
      }
      case _ => throw new IllegalStateException
    }

    def positions(rule: MatchRule) = position(rule)
  }

  sealed trait MatchRule
  case object Identical extends MatchRule
  case object Empty extends MatchRule
  case object AtLeastOne extends MatchRule

  // Selector that picks lines from a supplied list based on some quantity
  // criteria. Also allows multiple content matchers to be chained together
  class LineSelector(lines: List[Line]) {

    def select(bound: SelectorBound) = bound match {
      case First => new LineWrapper(lines.headOption)
      case _ => throw new IllegalStateException
    }

    def selectMultiple(bound: SelectorBound) = bound match {
      case All => new LinesWrapper(lines)
      case _ => throw new IllegalStateException
    }

    def and(count: Int) = new ContentMatcher(lines).linesHaving(count)
  }

  sealed trait SelectorBound
  case object First extends SelectorBound
  case object All extends SelectorBound

  // Wrapper around a single line that allows tokens or positions to
  // be extracted based on some defined criteria
  class LineWrapper(line: Option[Line]) {

    def takeToken(index: Int) = line match {
      case Some(l) => {
        val tokens = l.map(_._1).filter(_ != None)
        if ( tokens.length >= index ) tokens(index - 1) else None
      }
      case _ => None
    }

    def take(rule: PositionRule) = rule match {
      case EmptyPosition => line match {
        case Some(l) => l.find(_._1 == None) match {
          case Some(e) => Some(e._2)
          case _ => None
        }
        case _ => None
      }
      case _ => throw new IllegalStateException
    }
  }

  // Wrapper around a number of lines that allows tokens or positions to
  // be extracted based on some defined criteria
  class LinesWrapper(lines: List[Line]) {

    def take(rule: PositionRule) = rule match {
      case HighestFrequencyEmptyPosition => highestMultipleFrequency(emptyPositions(lines))
      case MiddleEmptyPosition => emptyPositions(lines).find(_ == Position(1, 1))
      case CornerEmptyPosition => emptyPositions(lines).distinct.find(corner_?(_))
      case RandomEmptyPosition => random(emptyPositions(lines).distinct)
      case _ => throw new IllegalStateException
    }
  }

  sealed trait PositionRule
  case object EmptyPosition extends PositionRule
  case object HighestFrequencyEmptyPosition extends PositionRule
  case object MiddleEmptyPosition extends PositionRule
  case object CornerEmptyPosition extends PositionRule
  case object RandomEmptyPosition extends PositionRule

  // Helper functions

  def tokenFrequencies(line: Line) = {
    Map(Some(Nought) -> line.count(_._1 == Some(Nought)),
        Some(Cross) -> line.count(_._1 == Some(Cross)),
        None -> line.count(_._1 == None))
  }

  def tokenFrequency(line: Line, token: Token) = line.map(_._1).count(_ == Some(token))

  def emptyPositions(lines: List[Line]) = lines.flatMap(_.filter(_._1 == None).map(_._2))

  def corner_?(position: Position) = position match {
    case Position(0, c) if c == 0 || c == 2 => true
    case Position(2, c) if c == 0 || c == 2 => true
    case _ => false
  }

  def highestMultipleFrequency[T](items: List[T]): Option[T] = {
    var frequencies = Map[T, Int]()
    items.foreach { item =>
      var count = 1
      if ( frequencies.contains(item) ) count = count + frequencies(item)
      frequencies = frequencies + Pair(item, count)
    }

    var max = 0
    frequencies.foreach(entry => if (entry._2 > max) max = entry._2)

    if ( max < 2 ) None
    else frequencies.filter(_._2 == max).map(_._1).headOption
  }

  def random[T](items: List[T]): Option[T] = {
    if ( items.isEmpty ) None
    else {
      val random = new java.util.Random(System.currentTimeMillis)
      Some(items(random.nextInt(items.size)))
    }
  }
}
