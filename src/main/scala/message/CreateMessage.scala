package message

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.typesafe.scalalogging.LazyLogging
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import tech.allegro.schema.json2avro.converter.JsonAvroConverter

import java.io.FileInputStream

object CreateMessage extends LazyLogging {

  val avroConverter = new JsonAvroConverter

  def createMessage(url: String, numMessage: Option[Int] = None)
                   (implicit schema: Schema): GenericRecord = {

    val jsonNode = readJSON(this.getClass.getClassLoader.getResource(url).getFile)

    val genericRecord: GenericRecord = avroConverter.convertToGenericDataRecord(jsonNode.toString.getBytes, schema)
    genericRecord
  }

  private def readJSON(path: String): JsonNode = {
    val mapper = new ObjectMapper()
    mapper.readTree(new FileInputStream(path))
  }
}
