import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "2.12.10"
val versions = new Object {
  val kafkaVersion = "2.6.0"
  val kafkaAvroSerializerVersion = "5.3.0"
  val configVersion = "1.3.3"
  val loggingVersion = "3.7.2"
  val logbackVersion = "1.2.3"
  val allegroVersion="0.2.2"
  val kitesdkVersion="1.1.0"
}

lazy val kafkaProducer = (project in file("."))
  .settings(
    name := "CustomKafkaProducer"
  )

resolvers += "confluent" at "https://packages.confluent.io/maven/"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % versions.kafkaVersion
libraryDependencies += "io.confluent" % "kafka-avro-serializer" % versions.kafkaAvroSerializerVersion
libraryDependencies += "com.typesafe" % "config" % versions.configVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % versions.loggingVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % versions.logbackVersion
libraryDependencies += "tech.allegro.schema.json2avro" % "converter" % versions.allegroVersion
libraryDependencies += "org.kitesdk" % "kite-data-core" % versions.kitesdkVersion
