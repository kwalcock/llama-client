val scala211 = "2.11.12" // up to 2.11.12
val scala212 = "2.12.19" // up to 2.12.19
val scala213 = "2.13.13" // up to 2.13.13
val scala30  = "3.0.2"   // up to 3.0.2
val scala31  = "3.1.3"   // up to 3.1.3
val scala33  = "3.3.3"   // up to 3.3.3
val scala34  = "3.4.1"   // up to 3.4.1

val scala3   = scala31

ThisBuild / scalaVersion := scala212

name := "llama-client"

libraryDependencies ++= Seq(
  "de.kherud" % "llama" % "3.0.1"
)
