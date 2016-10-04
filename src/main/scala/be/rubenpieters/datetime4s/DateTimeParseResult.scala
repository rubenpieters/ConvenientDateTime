package be.rubenpieters.datetime4s

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

import simulacrum.typeclass

/**
  * Created by ruben on 4/10/2016.
  */
@typeclass trait DateTimeParseResult[A] {
  def parseWithDateTimeFormatter(text: String, dateTimeFormatter: DateTimeFormatter): A
}

object ImplicitParseResult {
  implicit object LocalDateParseResult extends DateTimeParseResult[LocalDate] {
    override def parseWithDateTimeFormatter(text: String, dateTimeFormatter: DateTimeFormatter): LocalDate = {
      LocalDate.parse(text, dateTimeFormatter)
    }
  }

  implicit object LocalDateTimeParseResult extends DateTimeParseResult[LocalDateTime] {
    override def parseWithDateTimeFormatter(text: String, dateTimeFormatter: DateTimeFormatter): LocalDateTime = {
      LocalDateTime.parse(text, dateTimeFormatter)
    }
  }
}