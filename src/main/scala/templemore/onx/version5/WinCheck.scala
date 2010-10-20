package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait WinCheck {
  this: LineQueryDSL =>

  def win_? = winningToken != None
  def winningToken(): Option[Token] = find linesHaving 3 tokens Identical select First takeToken 1
}