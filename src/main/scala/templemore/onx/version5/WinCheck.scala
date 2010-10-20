package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait WinCheck {
  this: LineQueryDSL =>

  def win_? = winningToken != None

  def winningToken(): Option[Token] = find the token on first line having all positions identical()
}