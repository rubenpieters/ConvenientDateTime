package be.rubenpieters.datetime4s

import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder.ReducedPrinterParser
import java.time.format.{DateTimeFormatterBuilder, SignStyle, TextStyle}
import java.time.temporal.{ChronoField, TemporalField}

import be.rubenpieters.util.Intersperse

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

final case class AdHocPattern(pattern: String) extends FormatterField {
  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendPattern(pattern)
  }
}

sealed trait PredefinedPatternFormatterField extends FormatterField {
  def pattern: String

  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.appendPattern(pattern)
  }
}

final case class EmbeddedFormatterFieldList(list: List[FormatterField]) extends FormatterField {
  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    list.foreach(_.append(dateTimeFormatterBuilder))
  }
}

final case class OptionalFormatterFieldList(list: List[FormatterField]) extends FormatterField {
  override def append(dateTimeFormatterBuilder: DateTimeFormatterBuilder): Unit = {
    dateTimeFormatterBuilder.optionalStart()
    list.foreach(_.append(dateTimeFormatterBuilder))
    dateTimeFormatterBuilder.optionalEnd()
  }
}

object implicits {
  implicit class EnrichedString(val str: String) extends AnyVal {
    def lit: LiteralString = LiteralString(str)
    def pat: AdHocPattern = AdHocPattern(str)
  }

  implicit class EnrichedChar(val char: Char) extends AnyVal {
    def lit: LiteralChar = LiteralChar(char)
  }

  implicit class EnrichedListOfFields(val list: List[FormatterField]) extends AnyVal {
    def separator(str: String) = Intersperse.intersperse(list, str.lit)
    def separator(char: Char) = Intersperse.intersperse(list, char.lit)
    def emb: EmbeddedFormatterFieldList = EmbeddedFormatterFieldList(list)
    def opt: OptionalFormatterFieldList = OptionalFormatterFieldList(list)
  }

  implicit class EnrichedFormatterField(val formatterField: FormatterField) extends AnyVal {
    def opt: OptionalFormatterFieldList = OptionalFormatterFieldList(List(formatterField))
  }
}

// AUTOGENERATED BY FormatterFieldsCodeGen

case object Era1 extends PredefinedPatternFormatterField {
  override val pattern = "G"
}

case object Era2 extends PredefinedPatternFormatterField {
  override val pattern = "GG"
}

case object Era3 extends PredefinedPatternFormatterField {
  override val pattern = "GGG"
}

case object Era4 extends PredefinedPatternFormatterField {
  override val pattern = "GGGG"
}

case object Era5 extends PredefinedPatternFormatterField {
  override val pattern = "GGGGG"
}

case object Year1 extends PredefinedPatternFormatterField {
  override val pattern = "u"
}

case object Year2 extends PredefinedPatternFormatterField {
  override val pattern = "uu"
}

case object Year3 extends PredefinedPatternFormatterField {
  override val pattern = "uuu"
}

case object YearEP extends PredefinedPatternFormatterField {
  override val pattern = "u..u"
}

case object YearOfEra1 extends PredefinedPatternFormatterField {
  override val pattern = "y"
}

case object YearOfEra2 extends PredefinedPatternFormatterField {
  override val pattern = "yy"
}

case object YearOfEra3 extends PredefinedPatternFormatterField {
  override val pattern = "yyy"
}

case object YearOfEraEP extends PredefinedPatternFormatterField {
  override val pattern = "y..y"
}

case object WeekBasedYear1 extends PredefinedPatternFormatterField {
  override val pattern = "Y"
}

case object WeekBasedYear2 extends PredefinedPatternFormatterField {
  override val pattern = "YY"
}

case object WeekBasedYear3 extends PredefinedPatternFormatterField {
  override val pattern = "YYY"
}

case object WeekBasedYearEP extends PredefinedPatternFormatterField {
  override val pattern = "Y..Y"
}

case object QuarterOfYear1 extends PredefinedPatternFormatterField {
  override val pattern = "Q"
}

case object QuarterOfYear2 extends PredefinedPatternFormatterField {
  override val pattern = "QQ"
}

case object QuarterOfYear3 extends PredefinedPatternFormatterField {
  override val pattern = "QQQ"
}

case object QuarterOfYear4 extends PredefinedPatternFormatterField {
  override val pattern = "QQQQ"
}

case object QuarterOfYear5 extends PredefinedPatternFormatterField {
  override val pattern = "QQQQQ"
}

case object QuarterOfYearTxt1 extends PredefinedPatternFormatterField {
  override val pattern = "q"
}

case object QuarterOfYearTxt2 extends PredefinedPatternFormatterField {
  override val pattern = "qq"
}

case object QuarterOfYearTxt3 extends PredefinedPatternFormatterField {
  override val pattern = "qqq"
}

case object QuarterOfYearTxt4 extends PredefinedPatternFormatterField {
  override val pattern = "qqqq"
}

case object QuarterOfYearTxt5 extends PredefinedPatternFormatterField {
  override val pattern = "qqqqq"
}

case object MonthOfYear1 extends PredefinedPatternFormatterField {
  override val pattern = "M"
}

case object MonthOfYear2 extends PredefinedPatternFormatterField {
  override val pattern = "MM"
}

case object MonthOfYear3 extends PredefinedPatternFormatterField {
  override val pattern = "MMM"
}

case object MonthOfYear4 extends PredefinedPatternFormatterField {
  override val pattern = "MMMM"
}

case object MonthOfYear5 extends PredefinedPatternFormatterField {
  override val pattern = "MMMMM"
}

case object MonthOfYearTxt1 extends PredefinedPatternFormatterField {
  override val pattern = "L"
}

case object MonthOfYearTxt2 extends PredefinedPatternFormatterField {
  override val pattern = "LL"
}

case object MonthOfYearTxt3 extends PredefinedPatternFormatterField {
  override val pattern = "LLL"
}

case object MonthOfYearTxt4 extends PredefinedPatternFormatterField {
  override val pattern = "LLLL"
}

case object MonthOfYearTxt5 extends PredefinedPatternFormatterField {
  override val pattern = "LLLLL"
}

case object WeekOfWeekBasedYear1 extends PredefinedPatternFormatterField {
  override val pattern = "w"
}

case object WeekOfWeekBasedYear2 extends PredefinedPatternFormatterField {
  override val pattern = "ww"
}

case object WeekOfMonth1 extends PredefinedPatternFormatterField {
  override val pattern = "W"
}

case object DayOfMonth1 extends PredefinedPatternFormatterField {
  override val pattern = "d"
}

case object DayOfMonth2 extends PredefinedPatternFormatterField {
  override val pattern = "dd"
}

case object DayOfYear1 extends PredefinedPatternFormatterField {
  override val pattern = "D"
}

case object DayOfYear2 extends PredefinedPatternFormatterField {
  override val pattern = "DD"
}

case object DayOfYear3 extends PredefinedPatternFormatterField {
  override val pattern = "DDD"
}

case object AlignedDayOfWeekInMonth1 extends PredefinedPatternFormatterField {
  override val pattern = "F"
}

case object DayOfWeek1 extends PredefinedPatternFormatterField {
  override val pattern = "E"
}

case object DayOfWeek2 extends PredefinedPatternFormatterField {
  override val pattern = "EE"
}

case object DayOfWeek3 extends PredefinedPatternFormatterField {
  override val pattern = "EEE"
}

case object DayOfWeek4 extends PredefinedPatternFormatterField {
  override val pattern = "EEEE"
}

case object DayOfWeek5 extends PredefinedPatternFormatterField {
  override val pattern = "EEEEE"
}

case object LocalizedDayOfWeek1 extends PredefinedPatternFormatterField {
  override val pattern = "e"
}

case object LocalizedDayOfWeek2 extends PredefinedPatternFormatterField {
  override val pattern = "ee"
}

case object LocalizedDayOfWeek3 extends PredefinedPatternFormatterField {
  override val pattern = "eee"
}

case object LocalizedDayOfWeek4 extends PredefinedPatternFormatterField {
  override val pattern = "eeee"
}

case object LocalizedDayOfWeek5 extends PredefinedPatternFormatterField {
  override val pattern = "eeeee"
}

case object LocalizedDayOfWeekTxt1 extends PredefinedPatternFormatterField {
  override val pattern = "c"
}

case object LocalizedDayOfWeekTxt3 extends PredefinedPatternFormatterField {
  override val pattern = "ccc"
}

case object LocalizedDayOfWeekTxt4 extends PredefinedPatternFormatterField {
  override val pattern = "cccc"
}

case object LocalizedDayOfWeekTxt5 extends PredefinedPatternFormatterField {
  override val pattern = "ccccc"
}

case object AmPmOfDay1 extends PredefinedPatternFormatterField {
  override val pattern = "a"
}

case object ClockHourOfAmPm1 extends PredefinedPatternFormatterField {
  override val pattern = "h"
}

case object ClockHourOfAmPm2 extends PredefinedPatternFormatterField {
  override val pattern = "hh"
}

case object HourOfDay1 extends PredefinedPatternFormatterField {
  override val pattern = "H"
}

case object HourOfDay2 extends PredefinedPatternFormatterField {
  override val pattern = "HH"
}

case object ClockHourOfAmPm241 extends PredefinedPatternFormatterField {
  override val pattern = "k"
}

case object ClockHourOfAmPm242 extends PredefinedPatternFormatterField {
  override val pattern = "kk"
}

case object HourOfAmPm1 extends PredefinedPatternFormatterField {
  override val pattern = "K"
}

case object HourOfAmPm2 extends PredefinedPatternFormatterField {
  override val pattern = "KK"
}

case object MinuteOfHour1 extends PredefinedPatternFormatterField {
  override val pattern = "m"
}

case object MinuteOfHour2 extends PredefinedPatternFormatterField {
  override val pattern = "mm"
}

case object SecondOfMinute1 extends PredefinedPatternFormatterField {
  override val pattern = "s"
}

case object SecondOfMinute2 extends PredefinedPatternFormatterField {
  override val pattern = "ss"
}

case object FractionOfSecondEP extends PredefinedPatternFormatterField {
  override val pattern = "S..S"
}

case object MilliOfDay1 extends PredefinedPatternFormatterField {
  override val pattern = "A"
}

case object MilliOfDayEP extends PredefinedPatternFormatterField {
  override val pattern = "A..A"
}

case object NanoOfSecond1 extends PredefinedPatternFormatterField {
  override val pattern = "n"
}

case object NanoOfSecondEP extends PredefinedPatternFormatterField {
  override val pattern = "n..n"
}

case object NanoOfDay1 extends PredefinedPatternFormatterField {
  override val pattern = "N"
}

case object NanoOfDayEP extends PredefinedPatternFormatterField {
  override val pattern = "N..N"
}

case object TimeZoneId2 extends PredefinedPatternFormatterField {
  override val pattern = "VV"
}

case object TimeZoneName1 extends PredefinedPatternFormatterField {
  override val pattern = "z"
}

case object TimeZoneName2 extends PredefinedPatternFormatterField {
  override val pattern = "zz"
}

case object TimeZoneName3 extends PredefinedPatternFormatterField {
  override val pattern = "zzz"
}

case object TimeZoneName4 extends PredefinedPatternFormatterField {
  override val pattern = "zzzz"
}

case object LocalizedZoneOffset1 extends PredefinedPatternFormatterField {
  override val pattern = "O"
}

case object LocalizedZoneOffset4 extends PredefinedPatternFormatterField {
  override val pattern = "OOOO"
}

case object ZoneOffsetXz1 extends PredefinedPatternFormatterField {
  override val pattern = "X"
}

case object ZoneOffsetXz2 extends PredefinedPatternFormatterField {
  override val pattern = "XX"
}

case object ZoneOffsetXz3 extends PredefinedPatternFormatterField {
  override val pattern = "XXX"
}

case object ZoneOffsetXz4 extends PredefinedPatternFormatterField {
  override val pattern = "XXXX"
}

case object ZoneOffsetXz5 extends PredefinedPatternFormatterField {
  override val pattern = "XXXXX"
}

case object ZoneOffsetX1 extends PredefinedPatternFormatterField {
  override val pattern = "x"
}

case object ZoneOffsetX2 extends PredefinedPatternFormatterField {
  override val pattern = "xx"
}

case object ZoneOffsetX3 extends PredefinedPatternFormatterField {
  override val pattern = "xxx"
}

case object ZoneOffsetX4 extends PredefinedPatternFormatterField {
  override val pattern = "xxxx"
}

case object ZoneOffsetX5 extends PredefinedPatternFormatterField {
  override val pattern = "xxxxx"
}

case object ZoneOffsetZ1 extends PredefinedPatternFormatterField {
  override val pattern = "Z"
}

case object ZoneOffsetZ2 extends PredefinedPatternFormatterField {
  override val pattern = "ZZ"
}

case object ZoneOffsetZ3 extends PredefinedPatternFormatterField {
  override val pattern = "ZZZ"
}

case object ZoneOffsetZ4 extends PredefinedPatternFormatterField {
  override val pattern = "ZZZZ"
}

case object ZoneOffsetZ5 extends PredefinedPatternFormatterField {
  override val pattern = "ZZZZZ"
}
