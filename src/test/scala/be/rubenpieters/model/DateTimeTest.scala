package be.rubenpieters.model

import java.time.{LocalDate, LocalDateTime}

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

  "DateTime3Separator" should "be parsed in basic cases" in {
    val parsedDateYMDHMS = DateTime3Separator.parseToLocalDateTime(DateTime3Separator((Year, Month, Day), (Hour, Minute, Second), "/", "|", ":"), "2016/10/02|16:25:55")
    val parsedDateMDYSMH = DateTime3Separator.parseToLocalDateTime(DateTime3Separator((Month, Day, Year), (Second, Minute, Hour), "/", " ", "-"), "10/02/2016 55-25-16")

    parsedDateYMDHMS(new JavaParserRepr) shouldEqual LocalDateTime.of(2016, 10, 2, 16, 25, 55)
    parsedDateMDYSMH(new JavaParserRepr) shouldEqual LocalDateTime.of(2016, 10, 2, 16, 25, 55)
  }

}
