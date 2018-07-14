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
libraryDependencies += "junit" % "junit" % "4.12"

// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % "2.0.2-beta"
// https://mvnrepository.com/artifact/org.scalaz/scalaz-core
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.0-M22"

// https://mvnrepository.com/artifact/org.typelevel/cats-core
libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "cn.playscala" % "play-mongo_2.12" % "0.1.0"

// https://mvnrepository.com/artifact/com.carrotsearch/junit-benchmarks
libraryDependencies += "com.carrotsearch" % "junit-benchmarks" % "0.2.1"
// https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core
libraryDependencies += "org.openjdk.jmh" % "jmh-core" % "1.21"
// https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess
libraryDependencies += "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.21"
// https://mvnrepository.com/artifact/com.h2database/h2
libraryDependencies += "com.h2database" % "h2" % "1.4.197"


enablePlugins(JmhPlugin)


// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "6.3.0"
// https://mvnrepository.com/artifact/org.elasticsearch.client/elasticsearch-rest-high-level-client
libraryDependencies += "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.3.0"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.11.0"

libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-http-streams_2.12" % "6.2.9"
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-http_2.12" % "6.2.9"
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-core_2.12" % "6.2.9"

// https://mvnrepository.com/artifact/org.jsoup/jsoup
libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
