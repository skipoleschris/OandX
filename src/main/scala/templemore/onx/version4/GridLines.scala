package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait GridLines {
  this: Grid =>

  def lines = structure ::: structure.transpose ::: diagonals

  def linesWithMatchingTokenAndTwoSpaces(token: Token) =
    lines.filter { line => line.count(_ == None) == 2 && line.contains(Some(token)) }

  def allPositionsOnEmptyLines = lines.filter { line => line.forall(_ == None) }.flatten.distinct

  private[this] def diagonals = List(diagonalTopToBottom, diagonalBottomToTop)
  private[this] def diagonalTopToBottom = List(token(Position(0,0)), token(Position(1,1)), token(Position(2,2)))
  private[this] def diagonalBottomToTop = List(token(Position(2,0)), token(Position(1,1)), token(Position(0,2)))
}