package be.rubenpieters.model

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatterBuilder

import be.rubenpieters.util.Intersperse

import scala.language.implicitConversions

/**
  * Created by ruben on 1/10/16.
  */

object DateTime3Separator {
  def parseToLocalDateTime(dateTime3Separator: DateTime3SeparatorLike, text: String): ParserRepr => LocalDateTime = parserRepr => {
    ParserReprOps.parse(
      (Intersperse.intersperse(dateTime3Separator.dateOrder.order, Literal(dateTime3Separator.dateSeparator))
        :+ Literal(dateTime3Separator.dateTimeSeparator))
        ++ Intersperse.intersperse(dateTime3Separator.timeOrder.order, Literal(dateTime3Separator.timeSeparator))
    )(parserRepr)
      .parseToLocalDateTime(text)
  }
}

trait DateTime3SeparatorLike {
  def dateOrder: DateOrder
  def timeOrder: TimeOrder
  def dateSeparator: String
  def dateTimeSeparator: String
  def timeSeparator: String
}

case class DateTime3Separator(dateOrder: DateOrder, timeOrder: TimeOrder, dateSeparator: String, dateTimeSeparator: String, timeSeparator: String)
  extends DateTime3SeparatorLike

case class DateTime1Separator(dateOrder: DateOrder, timeOrder: TimeOrder, separator: String)
  extends DateTime3SeparatorLike {
  override val dateSeparator = separator
  override def dateTimeSeparator: String = separator
  override def timeSeparator: String = separator
}


case class Date1Separator(dateOrder: DateOrder, separator: String)

object Date1Separator {
  def parseToLocalDate(date1Separator: Date1Separator, text: String): ParserRepr => LocalDate = parserRepr => {
    ParserReprOps.parse(Intersperse.intersperse(date1Separator.dateOrder.order, Literal(date1Separator.separator)))(parserRepr)
      .parseToLocalDate(text)
  }
}

sealed trait ParserRepr {
  def parseYear(): ParserRepr
  def parseMonth(): ParserRepr
  def parseDay(): ParserRepr
  def parseLiteral(literal: String): ParserRepr

  def parseToLocalDate(text: String): LocalDate

  def parseHour(): ParserRepr
  def parseMinute(): ParserRepr
  def parseSecond(): ParserRepr

  def parseToLocalDateTime(text: String): LocalDateTime
}

class JavaParserRepr extends ParserRepr {
  val builder = new DateTimeFormatterBuilder()

  override def parseYear(): ParserRepr = {
    builder.optionalStart()
    builder.appendPattern("yyyy")
    builder.optionalEnd()
    builder.optionalStart()
    builder.appendPattern("yy")
    builder.optionalEnd()
    this
  }
  override def parseMonth(): ParserRepr = {
    builder.appendPattern("MM")
    this
  }
  override def parseDay(): ParserRepr = {
    builder.appendPattern("dd")
    this
  }
  override def parseLiteral(literal: String): ParserRepr = {
    builder.appendLiteral(literal)
    this
  }

  override def parseToLocalDate(text: String): LocalDate = LocalDate.parse(text, builder.toFormatter)

  override def parseHour(): ParserRepr = {
    builder.appendPattern("kk")
    this
  }

  override def parseMinute(): ParserRepr = {
    builder.appendPattern("mm")
    this
  }

  override def parseSecond(): ParserRepr = {
    builder.appendPattern("ss")
    this
  }

  override def parseToLocalDateTime(text: String): LocalDateTime = LocalDateTime.parse(text, builder.toFormatter)
}

object ParserReprOps {
  def parse(fields: List[ParserReprField]): ParserRepr => ParserRepr = parserRepr => {
    fields.foldLeft(parserRepr)((acc, newField) =>
      ParserReprOps.parseReprField(newField)(parserRepr)
    )
  }

  def parseReprField(field: ParserReprField): ParserRepr => ParserRepr = parserRepr => {
    field match {
      case Year => parserRepr.parseYear()
      case Month => parserRepr.parseMonth()
      case Day => parserRepr.parseDay()
      case Hour => parserRepr.parseHour()
      case Minute => parserRepr.parseMinute()
      case Second => parserRepr.parseSecond()
      case Literal(literal) => parserRepr.parseLiteral(literal)
    }
  }
}

sealed trait ParserReprField
case class Literal(literal: String) extends ParserReprField

sealed trait DateTimeField extends ParserReprField

sealed trait DateField extends DateTimeField
case object Year extends DateField
case object Month extends DateField
case object Day extends DateField

sealed trait TimeField extends DateTimeField
case object Hour extends TimeField
case object Minute extends TimeField
case object Second extends TimeField

case class DateOrder(order: List[DateField]) {
  require(order.distinct.size == order.size, "date order should not contain duplicates")
}

case class TimeOrder(order: List[TimeField]) {
  require(order.distinct.size == order.size, "time order should not contain duplicates")
}

object DateOrder {
  implicit def dateTupleToOrder(tuple: (DateField, DateField, DateField)): DateOrder = {
    DateOrder(List(tuple._1, tuple._2, tuple._3))
  }

  implicit def dateTimeFieldTupleToOrder(tuple: (TimeField, TimeField, TimeField)): TimeOrder = {
    TimeOrder(List(tuple._1, tuple._2, tuple._3))
  }

  implicit class EnrichedDate1Separator(date1Separator: Date1Separator) {
    def parseToLocalDate(text: String): ParserRepr => LocalDate = Date1Separator.parseToLocalDate(date1Separator, text)
  }

  implicit class EnrichedDateTime3Separator(dateTime3SeparatorLike: DateTime3SeparatorLike) {
    def parseToLocalDateTime(text: String): ParserRepr => LocalDateTime = DateTime3Separator.parseToLocalDateTime(dateTime3SeparatorLike, text)
  }
}

