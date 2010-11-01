package templemore.onx.version6

import actors.Actor
import templemore.onx.version5. {Position, Token}

/**
 * @author Chris Turner
 */
class PlayerActor(token: Token) extends Actor {

  def act(): Unit = {
    loop {
      react {
        case MakeMove(grid) => {
          println("Player: " + token.symbol)
          grid ! AskForSuggestion(token, this)
        }
        case Suggestion(position, grid, game) => {
          grid ! PlaceToken(token, position)
          game ! MoveComplete
        }
        case Exit => exit
      }
    }
  }
}

sealed trait PlayerEvent
case class MakeMove(grid: Actor) extends PlayerEvent
case class Suggestion(position: Position, grid: Actor, game: Actor) extends PlayerEvent