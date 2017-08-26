name := "bloom"
organization := "uk.camsw"
scalaVersion := "2.11.8"
version := "1.0.0-SNAPSHOT"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

(testOptions in Test) += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/scalatest-report")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"
libraryDependencies += "org.pegdown" % "pegdown" % "1.0.2" % "test"


