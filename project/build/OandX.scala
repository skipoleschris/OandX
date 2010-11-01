import sbt._

class OandXProject(info: ProjectInfo) extends DefaultProject(info) {

  def extraResources = "LICENSE"

  // Test dependencies
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
  val testng = "org.testng" % "testng" % "5.10" classifier "jdk15"
}