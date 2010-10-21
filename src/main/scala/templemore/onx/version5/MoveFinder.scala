package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: LineQueryDSL =>

  private val moveRules = List(winningPosition _,
                               blockingPosition _,
                               doubleWinPosition _,
                               doubleBlockPosition _)

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


    // Double Free Position
//    find.the(EmptyMiddlePosition).on(Any).line(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token))
//    find.an(EmptyPosition).on(First).line(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token))

    // Empty Line Position
    //if ( random && freeSpaces > 5 ) {
//      find.a(RandomEmptyPosition).on(Any).line(Having).all(Positions).matching(None)
    //}
//    find.the(EmptyMiddlePosition).on(Any).line(Having).all(Positions).matching(None)
//    find.an(EmptyCornerPosition).on(Any).line(Having).all(Positions).matching(None)
//    find.an(EmptyPosition).on(First).line(Having).all(Positions).matching(None)

    // Best Empty Position
//    find.the(EmptyMiddlePosition).on(Any).line(Having).any(Position).matching(None)
//    find.an(EmptyCornerPosition).on(Any).line(Having).any(Position).matching(None)
//    find.an(EmptyPosition).on(First).line(Having).any(Position).matching(None)

//    None
//  }


}