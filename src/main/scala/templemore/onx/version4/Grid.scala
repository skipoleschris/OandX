package templemore.onx.version4

/**
 * @author Chris Turner
 */
class Grid(values: List[List[Option[Token]]]) extends GridPosition with GridLines with WinScanner {

  import Grid._
  println("New grid: " + values)

  def token(position: Position) = column(position, row(position))

  def apply(position: Position, token: Token) = {
    if ( occupied_?(position) ) throw new IllegalStateException
    def replaceCell = cellsBefore(position) ::: List(Some(token)) ::: cellsAfter(position)
    new Grid(rowsBefore(position) ::: List(replaceCell) ::: rowsAfter(position))
  }

  def full_?(): Boolean = !values.flatten.contains(None)

  override def toString = {
    def lineToString(line: List[Option[Token]]) = line.map(_ match {
      case Some(t) => t.symbol
      case _ => " "
    }).mkString(" ", " | ", " ")
    lines.map(lineToString(_)).mkString("   |   |   \n", "\n   |   |\n-----------\n   |   |   \n", "\n   |   |   \n")
  }

  protected def structure = values

  private[this] def row(position: Position) = values.drop(position.row).head
  private[this] def column(position: Position, row: List[Value]) = row.drop(position.column).head
  private[this] def rowsBefore(position: Position) = values.take(position.row)
  private[this] def rowsAfter(position: Position) = values.drop(position.row + 1)
  private[this] def cellsBefore(position: Position) = row(position).take(position.column)
  private[this] def cellsAfter(position: Position) = row(position).drop(position.column + 1)
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