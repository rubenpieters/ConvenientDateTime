package be.rubenpieters.datetime4s

import java.time.{LocalDate, LocalDateTime}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

import be.rubenpieters.datetime4s.LiteralString

/**
  * Created by ruben on 4/10/2016.
  */
final case class FreeFormDate(fields: List[FormatterField])

object FreeFormDate {
  def parse[T : DateTimeParseResult](freeFormDate: FreeFormDate, text: String) = {
    val builder = new DateTimeFormatterBuilder()
    freeFormDate.fields.foreach(_.append(builder))
    DateTimeParseResult[T].parseWithDateTimeFormatter(text, builder.toFormatter)
  }
}


