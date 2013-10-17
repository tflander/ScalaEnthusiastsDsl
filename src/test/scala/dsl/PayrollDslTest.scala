package dsl
import org.scalatest._

class PayrollDslTest extends FunSpec with ShouldMatchers with BeforeAndAfter {

  import payroll.Employee
  import payroll.Name
  import payroll.Money
  import payroll.dsl.GrossPayBuilder
  import payroll.dsl.DeductionsBuilder
  import payroll.dsl.DeductionsBuilderDeductionHelper

  val halfPenny = 0.005
  val buck = Employee(Name("Buck", "Trends"), Money(80000))

  var grossPayBuilder: GrossPayBuilder = _
  var gross: DeductionsBuilder = _

  before {
    grossPayBuilder = new GrossPayBuilder(buck)
    grossPayBuilder.salary_for(1) // 1 day
    gross = new DeductionsBuilder(grossPayBuilder)
  }

  describe("deductions builder tests") {

    it("knows the employee") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross
      builder.employee should be(buck)
    }

    it("returns itself when calling currency") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross
      builder.currency should be theSameInstanceAs (builder)
    }

    it("modifies the paycheck when you create a deduction builder that deducts a percent of gross") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross
      builder.paycheck.gross.amount.toDouble should be(307.69 plusOrMinus halfPenny)
      builder.paycheck.net.amount.toDouble should be(276.92 plusOrMinus halfPenny)
    }

    it("modifies the paycheck when you add a deduction of a set amount") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross
      builder.paycheck.gross.amount.toDouble should be(307.69 plusOrMinus halfPenny)
      builder.paycheck.net.amount.toDouble should be(276.92 plusOrMinus halfPenny)
      builder.addDeductions(Money(10.))
      builder.paycheck.net.amount.toDouble should be(266.92 plusOrMinus halfPenny)
    }

    it("modifies the paycheck when you add a deduction as a percentage of gross") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross
      builder.paycheck.net.amount.toDouble should be(276.92 plusOrMinus halfPenny)
      builder.addDeductionsPercentageOfGross(10)
      builder.paycheck.net.amount.toDouble should be(246.15 plusOrMinus halfPenny)
    }

    it("applies deduction rules") {
      val builder = new DeductionsBuilderDeductionHelper(10.) percent_of gross

      def deductionRules(builder: DeductionsBuilder) = {
        builder.addDeductionsPercentageOfGross(10)
      }

      builder.paycheck.net.amount.toDouble should be(276.92 plusOrMinus halfPenny)
      val paycheck = builder.minus_deductions_for(deductionRules)
      builder.paycheck.net.amount.toDouble should be(246.15 plusOrMinus halfPenny)
    }

  }

  describe("Full DSL test") {
    it("should generate a payroll report") {

      import payroll._
      import payroll.dsl._
      import payroll.dsl.rules._

      val payrollCalculator = rules { employee =>
        employee salary_for 2.weeks minus_deductions_for { gross =>
          federalIncomeTax is (25. percent_of gross)
          stateIncomeTax is (5. percent_of gross)
          insurancePremiums are (500. in gross.currency)
          retirementFundContributions are (10. percent_of gross)
        }
      }

      val buck = Employee(Name("Buck", "Trends"), Money(80000))
      val jane = Employee(Name("Jane", "Doe"), Money(90000))

      val report = List(buck, jane).map { employee =>
        val check = payrollCalculator(employee)
        format("%s %s: %s", employee.name.first, employee.name.last, check)
      }

      report(0) should be("Buck Trends: Paycheck($3076.92,$1346.15,$1730.77)")
      report(1) should be("Jane Doe: Paycheck($3461.54,$1576.92,$1884.62)")

    }
  }
}