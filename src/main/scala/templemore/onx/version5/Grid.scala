package templemore.onx.version5

/**
 * @author Chris Turner
 */
class Grid(tokens: List[Option[Token]]) extends TokensWithPositions
                                        with Lines
                                        with LineQueryDSL
                                        with WinCheck
                                        with MoveFinder {

  require(tokens.length == 9)
  private val values = tokens zip Grid.positions

  def tokensWithPositions() = values

  def tokenAt(position: Position) = filterByPosition(position) match {
    case Nil => throw new IllegalArgumentException("Invalid position: " + position)
    case x :: xs => x._1
  }

  def apply(position: Position, token: Token) = {
    if ( occupied_?(position) ) throw new IllegalStateException
    new Grid(tokensWithSubsitution(position, token))
  }

  def full_? = !tokens.contains(None)

  override def toString = {
    def lineToString(line: List[Option[Token]]) = line.map(_ match {
      case Some(t) => t.symbol
      case _ => " "
    }).mkString(" ", " | ", " ")
    tokens.grouped(3).map(lineToString _).mkString("   |   |   \n", "\n   |   |\n-----------\n   |   |   \n", "\n   |   |   \n")
  }

  private[this] def filterByPosition(position: Position) = values.filter(_._2 == position)
  private[this] def occupied_?(position: Position) = tokenAt(position) != None
  private[this] def tokensWithSubsitution(position: Position, token: Token) =
    values.map { value => if ( value._2 == position ) Some(token) else value._1 }
}

object Grid {

  val positions = for (row <- 0 to 2; column <- 0 to 2) yield Position(row, column)

  def apply() = new Grid(List[Option[Token]](None, None, None,
                                          None, None, None,
                                          None, None, None))

  def apply(pattern: String) = {
    def toValues(pattern: String) = pattern.filter(_ != '\n').map(_ match {
      case 'O' => Some(Nought)
      case 'X' => Some(Cross)
      case _ => None
    })
    new Grid(toValues(pattern).toList)
  }
}

trait TokensWithPositions {

  def tokensWithPositions(): List[Pair[Option[Token], Position]]
}

