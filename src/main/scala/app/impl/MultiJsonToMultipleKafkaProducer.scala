package app.impl

import app.GenericProducer
import message.CreateMessage
import org.apache.avro.Schema.Parser
import org.apache.kafka.clients.producer.ProducerRecord
import producer.impl.SingleAvroKafkaProducer

import scala.io.Source

object MultiJsonToMultipleKafkaProducer extends GenericProducer {

  override def main(args: Array[String]): Unit = {

    val config = init(args(0))
    val clientID: String = config.getString("client-id")
    val server: String = config.getString("server")
    val schemaRegistry: String = config.getString("schema-registry")

    logger.info("Begin data send")


    val topics = config.getString("topic").split(",")

    val avroSchemas = {
      val inputSchemas = config.getString("avro-schema")
      if (inputSchemas.contains(","))
        inputSchemas.split(",") else s"$inputSchemas,".split(",")
    }

    //Read avro schema file
    val schemas = avroSchemas.map(as => {
      val source = Source.fromURL(this.getClass.getClassLoader.getResource(as))
      new Parser().parse(try source.mkString finally source.close())
    })

    val messages = config.getString("message-json").split(",")

    val producers = avroSchemas.map(_ => SingleAvroKafkaProducer(server, schemaRegistry, clientID).getProducer)

    val totalCounts = schemas.map(_ => 0)

    // Create avro generic record object
    var total = 0
    val windowDuration = config.getInt("window-duration")
    val numWindow = config.getInt("num-window")
    val r = scala.util.Random
    val split = config.getInt("split")

    (1 to numWindow).foreach(i => {
      val msgNumber = r.nextInt(config.getInt("random-rows-number")) + config.getInt("min-rows-number")
      val splitMessage = msgNumber / split
      val splitDuration = windowDuration / split
      val counts = schemas.map(_ => 0)
      (1 to split).foreach(_ => {
        (1 to splitMessage).foreach(_ => {
          val rP = r.nextInt(schemas.length)
          counts(rP) = counts(rP) + 1
          val toSend = CreateMessage.createMessage(messages(rP))(schemas(rP))
          val genericAvroMessage = new ProducerRecord(topics(rP), config.getString("key"), toSend)
          producers(rP).send(genericAvroMessage).get()
          producers(rP).flush()
        })
        if (split > 1) Thread.sleep(splitDuration + 500)
      })

      logger.info("Produced: " + splitMessage * split + " messages.")
      logger.info(s"Total messages for window $i:")
      val schemaIndex = schemas.zipWithIndex
      counts.zipWithIndex.foreach(c => logger.info(s"SCHEMA:${schemaIndex(c._2)._1.getFullName} => NUMBER:${c._1}"))
      total = total + counts.sum
      if (i < numWindow) Thread.sleep(windowDuration)
    })

    logger.info("----------------------------------------")
    logger.info("End of Kafka Producer")
    logger.info("----------------------------------------")
    logger.info(s"TOTAL NUMBER OF MESSAGES= $total")
  }
}


