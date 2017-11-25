name := "workTestWithScala"

version := "1.0"

scalaVersion := "2.12.1"

// https://mvnrepository.com/artifact/com.typesafe.play/play-json_2.12
libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.6"
// https://mvnrepository.com/artifact/org.nd4j/nd4s_2.11 版本不适配 暂时无法使用 不过可以直接调用java的ND4J
//libraryDependencies += "org.nd4j" % "nd4s_2.11" % "0.9.1"
// https://mvnrepository.com/artifact/org.nd4j/nd4j-native-platform
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1"
// https://mvnrepository.com/artifact/au.com.bytecode/opencsv
libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.5.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10" % Test
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.6" % Test
)
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.7"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.0.7"