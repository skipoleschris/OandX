package templemore.onx.version6

/**
 * @author Chris Turner
 */
trait Lines {
  this: TokensWithPositions =>

  def lines = rows.toList ::: columns.toList ::: List(leftToRightDiagonal) ::: List(rightToLeftDiagonal)

  private[this] def rows = buildLines((row: Int, position: Position) => position match {
    case Position(r, _) if r == row => true
    case _ => false
  })

  private[this] def columns = buildLines((column: Int, position: Position) => position match {
    case Position(_, c) if c == column => true
    case _ => false
  })

  private[this] def leftToRightDiagonal = tokensWithPositions.filter(_._2 match {
    case Position(0, 0) => true
    case Position(1, 1) => true
    case Position(2, 2) => true
    case _ => false
  })

  private[this] def rightToLeftDiagonal = tokensWithPositions.filter(_._2 match {
    case Position(0, 2) => true
    case Position(1, 1) => true
    case Position(2, 0) => true
    case _ => false
  })

  private[this] def buildLines(func: (Int, Position) => Boolean) =
    for(index <- 0 to 2) yield tokensWithPositions.filter(item => func(index, item._2))
}