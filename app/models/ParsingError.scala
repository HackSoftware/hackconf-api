package models

case class ParsingError(
    conversationId: String,
    cause: Throwable
  ) extends RuntimeException(s"Failed to parse call with conversationId $conversationId", cause)