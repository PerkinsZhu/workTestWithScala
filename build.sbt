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

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.10"
libraryDependencies +="com.typesafe.akka" %% "akka-remote" % "2.5.10"

libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.6" % Test
)
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.7"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.0.7"
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic 使用logback只需要这一个配置即可
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"


//log4j配置
//libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.10.0"
//libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.10.0"
//libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.8.0-alpha2"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0"

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "3.4"
)
// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"

// https://mvnrepository.com/artifact/net.sf.ehcache/ehcache
libraryDependencies += "net.sf.ehcache" % "ehcache" % "2.10.4"
// https://mvnrepository.com/artifact/cglib/cglib
libraryDependencies += "cglib" % "cglib" % "3.2.6"

// https://mvnrepository.com/artifact/com.alibaba/fastjson
libraryDependencies += "com.alibaba" % "fastjson" % "1.2.46"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.17"
// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.3"
// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test

// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % "2.0.2-beta" % Test
