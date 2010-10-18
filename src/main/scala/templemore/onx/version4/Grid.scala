package templemore.onx.version4

/**
 * @author Chris Turner
 */
class Grid(values: List[List[Option[Token]]]) {

  import Grid._
  println("New grid: " + values)

  def token(position: Position) = column(position, row(position))

  def apply(position: Position, token: Token) = {
    if ( occupied_?(position) ) throw new IllegalStateException
    def replaceCell = cellsBefore(position) ::: List(Some(token)) ::: cellsAfter(position)
    new Grid(rowsBefore(position) ::: List(replaceCell) ::: rowsAfter(position))
  }

  def occupied_?(position: Position) = token(position) match {
    case None => false
    case _ => true
  }

  def full_?(): Boolean = !values.flatten.contains(None)

  def lines = values ::: values.transpose ::: diagonals

  def corner_?(position: Position) = position match {
    case Position(r, c) if ( r == c && r != 1 ) => true
    case Position(r, c) if ( r == 0 && c == 2 ) => true
    case Position(r, c) if ( r == 2 && c == 1 ) => true
    case _ => false
  }

  def linesWithMatchingTokenAndTwoSpaces(token: Token) =
    lines.filter { line => line.count(_ == None) == 2 && line.contains(Some(token)) }

  def allPositionsOnEmptyLines = lines.filter { line => line.forall(_ == None) }.flatten.distinct

  override def toString = {
    def lineToString(line: List[Option[Token]]) = line.map(_ match {
      case Some(t) => t.symbol
      case _ => " "
    }).mkString(" ", " | ", " ")
    lines.map(lineToString(_)).mkString("   |   |   \n", "\n   |   |\n-----------\n   |   |   \n", "\n   |   |   \n")
  }

  private[this] def row(position: Position) = values.drop(position.row).head
  private[this] def column(position: Position, row: List[Value]) = row.drop(position.column).head
  private[this] def rowsBefore(position: Position) = values.take(position.row)
  private[this] def rowsAfter(position: Position) = values.drop(position.row + 1)
  private[this] def cellsBefore(position: Position) = row(position).take(position.column)
  private[this] def cellsAfter(position: Position) = row(position).drop(position.column + 1)
  private[this] def diagonals = List(diagonalTopToBottom, diagonalBottomToTop)
  private[this] def diagonalTopToBottom = List(token(Position(0,0)), token(Position(1,1)), token(Position(2,2)))
  private[this] def diagonalBottomToTop = List(token(Position(2,0)), token(Position(1,1)), token(Position(0,2)))
}

object Grid {

  type Value = Option[Token]
  type Line = Seq[Value]
  type LineGrid = Seq[Line]

  def apply() = new Grid(List(List[Option[Token]](None, None, None),
                              List[Option[Token]](None, None, None),
                              List[Option[Token]](None, None, None)))

  def apply(pattern: String) = {
    def toValues(pattern: String) = pattern.filter(_ != '\n').map(_ match {
      case 'O' => Some(Nought)
      case 'X' => Some(Cross)
      case _ => None
    })
    def breakIntoLines(tokens: List[Option[Token]]): List[List[Option[Token]]] = {
      require(tokens.length % 3 == 0)
      tokens match {
        case Nil => Nil
        case x => {
          val split = tokens.splitAt(3)
          split._1 :: breakIntoLines(split._2)
        }
      }
    }
    new Grid(breakIntoLines(toValues(pattern).toList))
  }
}