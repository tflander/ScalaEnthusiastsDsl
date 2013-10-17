package api
import org.scalatest._
import payroll.Employee
import payroll.Name
import payroll.Money
import payroll.Paycheck
import payroll.api.DeductionsCalculator._

class ApiTest extends FunSpec with ShouldMatchers {

  describe("") {
    it("") {
      val buck = Employee(Name("Buck", "Trends"), Money(80000))
      val jane = Employee(Name("Jane", "Doe"), Money(90000))
      
      val messages = List(buck, jane).map { employee =>
        // Assume annual is based on 52 weeks.
        val biweeklyGross = employee.annualGrossSalary / Money(26.)

        val deductions = federalIncomeTax(employee, biweeklyGross) +
          stateIncomeTax(employee, biweeklyGross) +
          insurancePremiums(employee, biweeklyGross) +
          retirementFundContributions(employee, biweeklyGross)

        val check = Paycheck(biweeklyGross, biweeklyGross - deductions, deductions)

        format("%s %s: %s", employee.name.first, employee.name.last, check)
      }
      
      messages(0) should be ("Buck Trends: Paycheck($3076.92,$1346.15,$1730.77)")
      messages(1) should be ("Jane Doe: Paycheck($3461.54,$1576.92,$1884.62)")

    }
  }
}