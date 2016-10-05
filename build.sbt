lazy val buildSettings = Seq(
  organization := "be.rubenpieters",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8")
)

val scalatestVersion = "3.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % scalatestVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % "test"

libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.8.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.google.guava" % "guava" % "19.0"