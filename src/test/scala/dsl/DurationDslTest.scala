package dsl
import org.scalatest._
import payroll.dsl.Duration

class DurationDslTest extends FunSpec with ShouldMatchers {

  val d = Duration(1)
  
  it("gets weeks") {
	  d.weeks should be(5)
  }
  
  it("gets years") {
    d.years should be(260)
  }
}