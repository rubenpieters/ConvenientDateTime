package be.rubenpieters.datetime4s

import java.time.{LocalDate, LocalDateTime}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.util.Locale

import be.rubenpieters.datetime4s.LiteralString

/**
  * Created by ruben on 4/10/2016.
  */
final case class FreeFormDate(fields: List[FormatterField], locale: Locale = Locale.getDefault(Locale.Category.FORMAT)) {
  val preBuiltFormatter = {
    val builder = new DateTimeFormatterBuilder()
    fields.foreach(_.append(builder))
    builder.toFormatter(locale)
  }
}

object FreeFormDate {
  def parse[T : DateTimeParseResult](freeFormDate: FreeFormDate, text: String) = {
    DateTimeParseResult[T].parseWithDateTimeFormatter(text, freeFormDate.preBuiltFormatter)
  }
}


