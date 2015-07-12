lazy val commonSettings = Seq(
  name := "scalatest-embedded-kafka",
  organization := "net.manub",
  scalaVersion := "2.11.7",
  version := "0.1.0-SNAPSHOT",
  parallelExecution in Test := false,
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.5",
    "org.apache.kafka" %% "kafka" % "0.8.2.1",
    "org.apache.zookeeper" % "zookeeper" % "3.4.6",

    "com.typesafe.akka" %% "akka-actor" % "2.3.11" % "test",
    "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test"
  )
)

lazy val publishSettings = Seq(
  licenses +=("MIT", url("http://opensource.org/licenses/MIT")),
  publishMavenStyle := false,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := <scm>
    <url>https://github.com/manub/scalatest-embedded-kafka</url>
    <connection>scm:git:git@github.com:manub/scalatest-embedded-kafka.git</connection>
  </scm>
    <developers>
      <developer>
        <id>manub</id>
        <name>Emanuele Blanco</name>
        <url>http://twitter.com/manub</url>
      </developer>
    </developers>
)

lazy val releaseSettings = Seq(
  releaseVersionBump := sbtrelease.Version.Bump.Minor,
  releaseUseGlobalVersion := false
)

lazy val root = (project in file("."))
  .settings(publishSettings: _*)
  .settings(commonSettings: _*)
  .settings(releaseSettings: _*)