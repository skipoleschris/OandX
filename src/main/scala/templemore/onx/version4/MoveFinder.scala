package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: Grid =>

  private val finders = WinningPositionFinder :: Nil

  def findBestPosition(token: Token): Option[Position] = findPosition(finders, token)

  private def findPosition(finders: List[PositionFinder], token: Token): Option[Position] = finders match {
    case Nil => None
    case x :: xs => x.findPosition(token) match {
      case Some(p) => Some(p)
      case None => findPosition(xs, token)
    }
  }

  trait PositionFinder {
    def findPosition(token: Token): Option[Position]

    protected def emptySpaces(line: List[Option[Token]]) = line.count(_ == None)
    protected def allSameToken(line: List[Option[Token]], token: Token) = line.forall(t => t == None || t == Some(token))
  }

  case object WinningPositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      println(linesWithPositions)
      linesWithPositions.filter { line => emptySpaces(line._1) == 1 && allSameToken(line._1, token) } match {
        case Nil => None
        case x => emptyPosition(x)
      }
    }
  }
}
