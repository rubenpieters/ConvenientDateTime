package be.rubenpieters.datetime4s

import java.time.format.{DateTimeFormatterBuilder, SignStyle, TextStyle}
import java.time.temporal.{ChronoField, TemporalField}

/**
  * Created by ruben on 4/10/2016.
  */
sealed trait FormatterField {
  def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder)
}

final case class LiteralString(literal: String) extends FormatterField {
  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendLiteral(literal)
  }
}

final case class LiteralChar(literal: Char) extends FormatterField {
  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendLiteral(literal)
  }
}

sealed trait TemporalField1 extends FormatterField {
  def temporalField: TemporalField

  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendValue(temporalField)
  }
}

sealed trait TemporalFieldWidth extends FormatterField {
  def temporalField: TemporalField
  def width: Int

  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendValue(temporalField, width)
  }
}

sealed trait TemporalFieldMinMaxWidthSignStyle extends FormatterField {
  def temporalField: TemporalField
  def minWidth: Int
  def maxWidth: Int = minWidth
  def signStyle: SignStyle = SignStyle.NORMAL

  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendValue(temporalField, minWidth, maxWidth, signStyle)
  }
}

trait ChronoFieldYear {
  def temporalField = ChronoField.YEAR
}
case object Year extends TemporalField1 with ChronoFieldYear
final case class Year(override val minWidth: Int, override val maxWidth: Int, override val signStyle: SignStyle)
  extends TemporalFieldMinMaxWidthSignStyle with ChronoFieldYear

trait ChronoMonthOfYear {
  def temporalField = ChronoField.MONTH_OF_YEAR
}
case object MonthOfYear extends TemporalField1 with ChronoMonthOfYear
final case class MonthOfYear(override val minWidth: Int, override val maxWidth: Int, override val signStyle: SignStyle)
  extends TemporalFieldMinMaxWidthSignStyle with ChronoMonthOfYear

trait ChronoDayOfMonth {
  def temporalField = ChronoField.DAY_OF_MONTH
}
case object DayOfMonth extends TemporalField1 with ChronoDayOfMonth
final case class DayOfMonth(override val minWidth: Int, override val maxWidth: Int, override val signStyle: SignStyle)
  extends TemporalFieldMinMaxWidthSignStyle with ChronoDayOfMonth