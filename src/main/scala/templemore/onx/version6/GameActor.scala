package templemore.onx.version6

import actors.Actor
import templemore.onx.version5.Token

/**
 * @author Chris Turner
 */
class GameActor(grid: Actor,
                noughts: Actor,
                crosses: Actor) extends Actor {

  private var currentPlayer = noughts
  private var otherPlayer = crosses

  def act(): Unit = {
    loop {
      react {
        case StartGame => {
          grid ! Initialise(this)
          currentPlayer ! MakeMove(grid)
        }
        case MoveComplete => {
          grid ! Display
          grid ! CheckResult
        }
        case GameWon(token) => {
          println("Game won by: " + token.symbol)
          shutdown
        }
        case GameDrawn => {
          println("Game drawn")
          shutdown
        }
        case NextMove => {
          val temp = currentPlayer
          currentPlayer = otherPlayer
          otherPlayer = temp
          currentPlayer ! MakeMove(grid)
        }
      }
    }
  }

  private def shutdown = {
    grid ! Exit
    noughts ! Exit
    crosses ! Exit
    exit
  }
}

sealed trait GameEvent
case object StartGame extends GameEvent
case object MoveComplete extends GameEvent
case class GameWon(token: Token) extends GameEvent
case object GameDrawn extends GameEvent
case object NextMove extends GameEvent
case object Exit extends GameEvent