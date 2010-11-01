package templemore.onx.version6

import actors.Actor
import templemore.onx.version5.{Grid, Token, Position}

/**
 * @author Chris Turner
 */
class GridActor extends Actor {

  private var game: GameActor = _
  private var grid: Grid = _

  def act(): Unit = {
    loop {
      react {
        case Initialise(g) => {
          game = g
          grid = Grid()
        }
        case AskForSuggestion(token, player) => player ! Suggestion(grid.findBestPosition(token).get, this, game)
        case PlaceToken(token, position) => grid = grid(position, token)
        case Display => println(grid)
        case CheckResult => {
          if ( grid.win_? ) game ! GameWon(grid.winningToken.get)
          else if ( grid.full_? ) game ! GameDrawn
          else game ! NextMove
        }
        case Exit => exit
      }
    }
  }
}

sealed trait GridEvent
case class Initialise(game: GameActor) extends GridEvent
case class AskForSuggestion(token: Token, player: Actor) extends GridEvent
case class PlaceToken(token: Token, position: Position) extends GridEvent
case object Display extends GridEvent
case object CheckResult extends GridEvent
