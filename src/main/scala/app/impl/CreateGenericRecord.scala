package app.impl

import com.typesafe.scalalogging.LazyLogging
import message.CreateMessage

import scala.io.Source
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericRecord

object CreateGenericRecord extends LazyLogging {

  def main(args: Array[String]): Unit = {

    val messageJsonUrl = args(1)
    val avroSchemaUrl = args(0)
    val source = Source.fromURL(CreateGenericRecord.getClass.getClassLoader.getResource(avroSchemaUrl))
    implicit val schema: Schema = new Parser().parse(try source.mkString finally source.close())

    val genericRecord: GenericRecord = CreateMessage.createMessage(messageJsonUrl)
    logger.info("GENERIC RECORD:\n" + genericRecord.toString)
  }
}
