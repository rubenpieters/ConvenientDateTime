package be.rubenpieters.model

import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

import be.rubenpieters.util.Intersperse

import scala.language.implicitConversions

/**
  * Created by ruben on 1/10/16.
  */

case class DateTime3Separator()

case class DateTime2Separator()

case class DateTime1Separator()


case class Date1Separator(dateOrder: DateOrder, separator: String) {

}

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
}

class JavaParserRepr extends ParserRepr {
  val builder = new DateTimeFormatterBuilder()

  override def parseYear(): ParserRepr = {
    builder.appendPattern("yyyy")
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
      case Literal(literal) => parserRepr.parseLiteral(literal)
    }
  }
}

sealed trait ParserReprField
case class Literal(literal: String) extends ParserReprField

sealed trait DateField extends ParserReprField
case object Year extends DateField
case object Month extends DateField
case object Day extends DateField

case class DateOrder(order: List[DateField]) {
  require(order.distinct.size == order.size, "date order should not contain duplicates")
}

object DateOrder {
  implicit def dateTupleToOrder[A <: DateField, B <: DateField, C <: DateField](tuple: (A, B, C)): DateOrder = {
    DateOrder(List(tuple._1, tuple._2, tuple._3))
  }
}

