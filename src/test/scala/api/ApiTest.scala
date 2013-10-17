package api
import org.scalatest._
import payroll.Employee
import payroll.Name
import payroll.Money
import payroll.Paycheck
import payroll.api.DeductionsCalculator._
import payroll.GrossSalary

class ApiTest extends FunSpec with ShouldMatchers {

  describe("API test") {
    it("should create a payroll report") {
      val buck = Employee(Name("Buck", "Trends"), GrossSalary(80000))
      val jane = Employee(Name("Jane", "Doe"), GrossSalary(90000))
      
      val messages = List(buck, jane).map { implicit employee =>
        implicit val biweeklyGross = employee.annualGrossSalary.biweeklyGross

        val deductions = federalIncomeTax +
          stateIncomeTax +
          insurancePremiums +
          retirementFundContributions

        val check = Paycheck(biweeklyGross, biweeklyGross - deductions, deductions)

        format("%s %s: %s", employee.name.first, employee.name.last, check)
      }
      
      messages(0) should be ("Buck Trends: Paycheck($3076.92,$1346.15,$1730.77)")
      messages(1) should be ("Jane Doe: Paycheck($3461.54,$1576.92,$1884.62)")

    }
  }
}