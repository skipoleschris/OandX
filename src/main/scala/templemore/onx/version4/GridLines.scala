package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait GridLines {
  this: Grid =>

  private[this] val linePositions = List(Position(0, -1), Position(1, -1), Position(2, -1),
                                         Position(-1, 0), Position(-1, 1), Position(-1, 2),
                                         Position(0, 0), Position(2, 0))

  def lines = structure ::: structure.transpose ::: diagonals

  def linesWithPositions = lines zip linePositions

  def emptyPositions(lines: List[Tuple2[List[Option[Token]], Position]]) =
    lines.filter(_._1 contains None).flatMap(empties => empties._1 zip positions(empties._2)).filter(_._1 == None).map(_._2)

  def emptyPosition(lines: List[Tuple2[List[Option[Token]], Position]]): Option[Position] = emptyPositions(lines) match {
    case Nil => None
    case x :: xs => Some(x)
  }

  def linesWithMatchingTokenAndTwoSpaces(token: Token) =
    linesWithPositions.filter { line => line._1.count(_ == None) == 2 && line._1.contains(Some(token)) }

  def allPositionsOnEmptyLines =
    linesWithPositions.filter { line => line._1.forall(_ == None) }.map(_._2).distinct.flatMap(positions(_))

  private[this] def diagonals = List(diagonalTopToBottom, diagonalBottomToTop)
  private[this] def diagonalTopToBottom = List(token(Position(0,0)), token(Position(1,1)), token(Position(2,2)))
  private[this] def diagonalBottomToTop = List(token(Position(2,0)), token(Position(1,1)), token(Position(0,2)))

  private[this] def positions(position: Position) = position match {
    case Position(row, -1) => List(Position(row, 0), Position(row, 1), Position(row, 2))
    case Position(-1, col) => List(Position(0, col), Position(1, col), Position(2, col))
    case Position(0, 0) => List(Position(0, 0), Position(1, 1), Position(2, 2))
    case Position(2, 0) => List(Position(2, 0), Position(1, 1), Position(0, 2))
    case _ => throw new IllegalStateException
  }
}