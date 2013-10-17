package payroll.api
import payroll.Type2Money._
import payroll.Money
import payroll.Employee

object DeductionsCalculator {
  def federalIncomeTax(empl: Employee, gross: Money) = gross * .25

  def stateIncomeTax(empl: Employee, gross: Money) = gross * .05

  def insurancePremiums(empl: Employee, gross: Money) = Money(500)

  def retirementFundContributions(empl: Employee, gross: Money) = gross * .10
}