name := "scala-utils"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.3"

publishTo := Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/content/repositories/snapshots")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false  }

pomExtra := (
  <url>http://kaiyin.co.vu</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/kindlychung/scala-utils</url>
      <connection>https://github.com/kindlychung/scala-utils</connection>
    </scm>
    <developers>
      <developer>
        <id>kindlychung</id>
        <name>Kaiyin Zhong</name>
        <url>http://kaiyin.co.vu</url>
      </developer>
    </developers>)