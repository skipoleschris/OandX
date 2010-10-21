package templemore.onx.version6

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: LineQueryDSL =>

  private val moveRules = List(winningPosition _,
                               blockingPosition _,
                               doubleWinPosition _,
                               doubleBlockPosition _,
                               doubleFreeMiddlePosition _,
                               doubleFreePosition _,
                               randomEmptyLinePosition _,
                               emptyMiddlePositionOnEmptyLine _,
                               emptyCornerPositionOnEmptyLine _,
                               emptyPositionOnEmptyLine _,
                               emptyMiddlePosition _,
                               emptyCornerPosition _,
                               emptyPosition _)

  def findBestPosition(token: Token): Option[Position] = findPosition(moveRules, token)

  private def findPosition(rules: List[(Token) => Option[Position]], token: Token): Option[Position] = rules match {
    case Nil => None
    case x :: xs => x(token) match {
      case Some(p) => Some(p)
      case None => findPosition(xs, token)
    }
  }

  private def winningPosition(token: Token) =
    find linesHaving 1 position Empty and 2 tokensMatching token select First take EmptyPosition

  private def blockingPosition(token: Token) = winningPosition(token.flip)

  private def doubleWinPosition(token: Token) =
    find linesHaving 2 positions Empty and 1 tokenMatching token selectMultiple All take HighestFrequencyEmptyPosition

  private def doubleBlockPosition(token: Token) = doubleWinPosition(token.flip)

  private def doubleFreeMiddlePosition(token: Token) =
    find linesHaving 2 positions Empty and 1 tokenMatching token selectMultiple All take MiddleEmptyPosition

  private def doubleFreePosition(token: Token) =
    find linesHaving 2 positions Empty and 1 tokenMatching token select First take EmptyPosition

  private def randomEmptyLinePosition(token: Token) = {
    if ( Grid.randomness && numEmptyPositions > 5 ) {
      find linesHaving 3 positions Empty selectMultiple All take RandomEmptyPosition
    }
    else None
  }

  private def emptyMiddlePositionOnEmptyLine(token: Token) =
    find linesHaving 3 positions Empty selectMultiple All take MiddleEmptyPosition

  private def emptyCornerPositionOnEmptyLine(token: Token) =
    find linesHaving 3 positions Empty selectMultiple All take CornerEmptyPosition

  private def emptyPositionOnEmptyLine(token: Token) =
    find linesHaving 3 positions Empty selectMultiple All take RandomEmptyPosition

  private def emptyMiddlePosition(token: Token) =
    find linesHaving AtLeastOne position Empty selectMultiple All take MiddleEmptyPosition

  private def emptyCornerPosition(token: Token) =
    find linesHaving AtLeastOne position Empty selectMultiple All take CornerEmptyPosition

  private def emptyPosition(token: Token) =
    find linesHaving AtLeastOne position Empty selectMultiple All take RandomEmptyPosition

  // Provided by grid
  def numEmptyPositions(): Int
}