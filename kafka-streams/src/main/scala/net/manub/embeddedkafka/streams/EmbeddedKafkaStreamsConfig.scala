package net.manub.embeddedkafka.streams

case class EmbeddedKafkaStreamsConfig(customStreamsProperties: Map[String, Any] = Map.empty)

object EmbeddedKafkaStreamsConfig {
  implicit val defaultStreamsConfig = EmbeddedKafkaStreamsConfig()
}
