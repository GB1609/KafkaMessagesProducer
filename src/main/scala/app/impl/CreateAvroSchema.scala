package app.impl

import com.typesafe.scalalogging.LazyLogging
import org.kitesdk.data.spi.JsonUtil

import java.io.{BufferedWriter, File, PrintWriter}
import scala.io.Source

object CreateAvroSchema extends LazyLogging {
  def main(args: Array[String]): Unit = {
    val url = this.getClass.getClassLoader.getResource(args(0))
    val source = Source.fromURL(url)
    val json = try source.mkString finally source.close()
    val avroSchema = JsonUtil.inferSchema(JsonUtil.parse(json), args(1)).toString()
    logger.info(s"avro schema=\n$avroSchema")
    val outputName = s"/generated_${args(1)}"
    val outputFile = s"src/main/resources/generated_schema/$outputName.avsc"
    val bufferedPrintWriter = new BufferedWriter(new PrintWriter(new File(outputFile)))
    bufferedPrintWriter.write(avroSchema, 0, avroSchema.length)
    bufferedPrintWriter.close()
  }
}
