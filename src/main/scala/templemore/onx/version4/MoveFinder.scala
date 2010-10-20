package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: Grid =>

  private val finders = WinningPositionFinder :: BlockingPositionFinder ::
                        DoubleWinPositionFinder :: DoubleBlockPositionFinder ::
                        DoubleFreePositionFinder :: EmptyLinePositionFinder ::
                        BestEmptyPositionFinder :: Nil

  def findBestPosition(token: Token): Option[Position] = findPosition(finders, token)

  private def findPosition(finders: List[PositionFinder], token: Token): Option[Position] = finders match {
    case Nil => None
    case x :: xs => x.findPosition(token) match {
      case Some(p) => Some(p)
      case None => findPosition(xs, token)
    }
  }

  private trait PositionFinder {
    def findPosition(token: Token): Option[Position]

    protected def emptySpaces(line: List[Option[Token]]) = line.count(_ == None)
    protected def allSameToken(line: List[Option[Token]], token: Token) = line.forall(t => t == None || t == Some(token))
  }

  private case object WinningPositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      linesWithPositions.filter { line => emptySpaces(line._1) == 1 && allSameToken(line._1, token) } match {
        case Nil => None
        case x => emptyPosition(x)
      }
    }
  }

  private case object BlockingPositionFinder extends PositionFinder {
    def findPosition(token: Token) = WinningPositionFinder.findPosition(token.flip)
  }

  private case object DoubleWinPositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      val allEmptyPositions = emptyPositions(linesWithMatchingTokenAndTwoSpaces(token))
      allEmptyPositions.filter(position => allEmptyPositions.count(_ == position) > 1) match {
        case Nil => None
        case x :: xs => Some(x)
      }
    }
  }

  private case object DoubleBlockPositionFinder extends PositionFinder {
    def findPosition(token: Token) = DoubleWinPositionFinder.findPosition(token.flip)
  }

  private case object DoubleFreePositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      val allEmptyPositions = emptyPositions(linesWithMatchingTokenAndTwoSpaces(token))
      if ( allEmptyPositions.contains(Position(1, 1))) Some(Position(1, 1))
      else if ( allEmptyPositions.isEmpty ) None
      else Some(allEmptyPositions.head)
    }
  }

  private case object EmptyLinePositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      val emptyLinePositions = allPositionsOnEmptyLines
      if ( Grid.startWithRandom && emptyLinePositions.size > 5 ) {
        // Introduce some randomness
        val random = new java.util.Random(System.currentTimeMillis)
        Some(emptyLinePositions(random.nextInt(emptyLinePositions.size)))
      }
      else if ( emptyLinePositions.contains(Position(1, 1)) ) Some(Position(1, 1))
      else emptyLinePositions.filter(corner_?(_)) match {
        case Nil => if ( emptyLinePositions.isEmpty ) None else Some(emptyLinePositions.head)
        case x :: xs => Some(x)
      }
    }
  }

  private case object BestEmptyPositionFinder extends PositionFinder {
    def findPosition(token: Token) = {
      val positions = emptyPositions(linesWithPositions)
      if ( positions.contains(Position(1, 1)) ) Some(Position(1, 1))
      else positions.filter(corner_?(_)) match {
        case Nil => if ( positions.isEmpty ) None else Some(positions.head)
        case x :: xs => Some(x)
      }
    }
  }
}
