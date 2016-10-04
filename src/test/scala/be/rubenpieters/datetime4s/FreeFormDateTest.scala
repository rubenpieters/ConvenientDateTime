package be.rubenpieters.datetime4s

import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}
import ImplicitParseResult._

/**
  * Created by ruben on 4/10/2016.
  */
class FreeFormDateTest extends FlatSpec with Matchers {
  "FeeFormDate" should "parse correctly in basic case" in {
    val ffd = FreeFormDate(List(Year, LiteralString("/"), MonthOfYear, LiteralString("/"), DayOfMonth))

    FreeFormDate.parse[LocalDate](ffd, "2016/10/04") shouldEqual LocalDate.of(2016, 10, 4)
  }
}
