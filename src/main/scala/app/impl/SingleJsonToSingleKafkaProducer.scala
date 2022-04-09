package app.impl

import app.GenericProducer
import message.CreateMessage
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerRecord
import producer.impl.SingleAvroKafkaProducer

import java.util.InputMismatchException
import scala.io.Source

object SingleJsonToSingleKafkaProducer extends GenericProducer {
  override def main(args: Array[String]): Unit = {
    val config = init(args(0))
    val clientID: String = config.getString("client-id")
    val server: String = config.getString("server")
    val schemaRegistry: String = config.getString("schema-registry")

    val producer = SingleAvroKafkaProducer(server, schemaRegistry, clientID).getProducer
    logger.info("Begin data send")

    //Read avro schema file

    val schemaApp = config.getString("avro-schema")
    val messageJsonUrl = config.getString("message-json")
    if (schemaApp.contains(",") || messageJsonUrl.contains(","))
      throw new InputMismatchException("Check Input schema in application.conf")
    val source = Source.fromURL(this.getClass.getClassLoader.getResource(schemaApp))
    implicit val schema: Schema = new Parser().parse(try source.mkString finally source.close())

    // Create avro generic record object
    //    val genericRecord: GenericRecord = new GenericData.Record(schema)
    var total = 0
    val windowDuration = config.getInt("window-duration")
    val numWindow = config.getInt("num-window")
    val r = scala.util.Random

    (1 to numWindow).foreach(i => {
      var count = 0
      val msgNumber = r.nextInt(config.getInt("random-rows-number")) + config.getInt("min-rows-number")

      (1 to msgNumber).foreach(_ => {
        count = count + 1
        val genericRecord: GenericRecord = CreateMessage.createMessage(messageJsonUrl)
        val avroMessage = new ProducerRecord(config.getString("topic"), config.getString("key"), genericRecord)
        producer.send(avroMessage).get()
        producer.flush()
      }
      )

      logger.info("Produced: " + msgNumber + " messages.")
      logger.info(s"Total messages for window $i:")
      logger.info(s"$count")
      total = total + count
      if (i < numWindow) Thread.sleep(windowDuration + 1000)
    })

    logger.info("----------------------------------------")
    logger.info("End of Kafka Producer")
    logger.info("----------------------------------------")
    logger.info(s"TOTAL NUMBER= $total")
  }
}
