package be.rubenpieters.datetime4s

import java.time.{LocalDate, LocalDateTime}
import java.time.format.SignStyle

import org.scalatest.{FlatSpec, Matchers}
import ImplicitParseResult._
import be.rubenpieters.datetime4s.implicits._

/**
  * Created by ruben on 4/10/2016.
  */
class FreeFormDateTest extends FlatSpec with Matchers {
  "FreeFormDate" should "parse correctly in basic case" in {
    val ffd = FreeFormDate(List(Year2, LiteralString("/"), MonthOfYear1, "/".lit, DayOfMonth1))

    FreeFormDate.parse[LocalDate](ffd, "16/10/04") shouldEqual LocalDate.of(2016, 10, 4)
  }

  "FreeFormDate" should "parse correctly in basic case with separator" in {
    val ffd = FreeFormDate(List(Year2, MonthOfYear1, DayOfMonth1).separator("/"))

    FreeFormDate.parse[LocalDate](ffd, "16/10/04") shouldEqual LocalDate.of(2016, 10, 4)
  }

  "FreeFormDate" should "parse date time case with two separators correctly" in {
    val ffd = FreeFormDate(List(
      List(Year2, MonthOfYear1, DayOfMonth1).separator("/").emb
      , " ".lit
      , List(HourOfDay1, MinuteOfHour1, SecondOfMinute1).separator("-").emb)
    )

    FreeFormDate.parse[LocalDateTime](ffd, "16/10/04 15-07-21") shouldEqual LocalDateTime.of(2016, 10, 4, 15, 7, 21)
  }

  "FreeFormDate" should "parse optional correctly" in {
    val ffd = FreeFormDate(List(
      List(Year2, MonthOfYear1, DayOfMonth1).separator("/").emb
      , " optional".lit.opt
    ))

    FreeFormDate.parse[LocalDate](ffd, "16/10/04 optional") shouldEqual LocalDate.of(2016, 10, 4)
    FreeFormDate.parse[LocalDate](ffd, "16/10/04") shouldEqual LocalDate.of(2016, 10, 4)
  }

  "FreeFormDate" should "parse optional list correctly" in {
    val ffd = FreeFormDate(List(
      List(Year2, MonthOfYear1, DayOfMonth1).separator("/").emb
      , List(" ".lit, "optional".lit).opt
    ))

    FreeFormDate.parse[LocalDate](ffd, "16/10/04 optional") shouldEqual LocalDate.of(2016, 10, 4)
    FreeFormDate.parse[LocalDate](ffd, "16/10/04") shouldEqual LocalDate.of(2016, 10, 4)
  }

  "FreeFormDate" should "parse nested optionals correctly" in {
    val ffd = FreeFormDate(List(
      List(Year2, MonthOfYear1, DayOfMonth1).separator("/").emb
      , List(" ".lit.opt, "optional".lit).opt
    ))

    FreeFormDate.parse[LocalDate](ffd, "16/10/04 optional") shouldEqual LocalDate.of(2016, 10, 4)
    FreeFormDate.parse[LocalDate](ffd, "16/10/04optional") shouldEqual LocalDate.of(2016, 10, 4)
  }
}
