package payroll
import org.scalatest._

class PaycheckTest extends FunSpec with ShouldMatchers {
	describe("")  {
	  it("") {
	    /*

object PaycheckSpec extends Specification("Paycheck") 
        with ScalaCheck with ArbitraryMoney { 

    implicit def arbitraryPaycheck: Arbitrary[Paycheck] = Arbitrary {
        Gen.sized {s => 
            for { 
                gross <- Arbitrary.arbitrary[Money]
                deductions <- Arbitrary.arbitrary[Money]
                if (gross >= deductions)
            } yield Paycheck(gross, gross - deductions, deductions)
        }
    }
    
    "Paycheck.plusGross" verifies { 
        (p: Paycheck, m: Money) => (p plusGross m) == 
            Paycheck(p.gross + m, p.net + m, p.deductions)
    }
    "Paycheck.plusDeductions" verifies { 
        (p: Paycheck, m: Money) => (p plusDeductions m) == 
            Paycheck(p.gross, p.net - m, p.deductions + m)
    }
	     */
	  }
	}
}