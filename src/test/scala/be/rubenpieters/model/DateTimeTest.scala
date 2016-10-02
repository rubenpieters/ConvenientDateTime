package be.rubenpieters.model

import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}
import be.rubenpieters.model.DateOrder._

/**
  * Created by ruben on 2/10/16.
  */
class DateTimeTest extends FlatSpec with Matchers {

  "Date1Separator" should "be parsed in basic cases" in {
    val parsedDateYMD = Date1Separator.parseToLocalDate(Date1Separator((Year, Month, Day), "/"), "2016/10/02")
    val parsedDateMDY = Date1Separator.parseToLocalDate(Date1Separator((Month, Day, Year), "/"), "10/02/2016")

    parsedDateYMD(new JavaParserRepr) shouldEqual LocalDate.of(2016, 10, 2)
    parsedDateMDY(new JavaParserRepr) shouldEqual LocalDate.of(2016, 10, 2)
  }

}
