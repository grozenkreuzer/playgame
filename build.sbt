name := """PlaySmart"""

version := "1.0"

scalaVersion := "2.12.2"

val InfoCryptNexusRepo = "Infocrypt Repo" at "http://11.11.11.97/nexus/content/repositories/thirdparty/"

resolvers           +=  InfoCryptNexusRepo

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice
libraryDependencies += "com.smart" % "smartclient" % "9.1.1.51" 
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % "test"

// https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.10"

