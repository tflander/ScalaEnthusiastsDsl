package payroll.api
import payroll.Type2Money._
import payroll.Money
import payroll.Employee

object DeductionsCalculator {
  def federalIncomeTax(implicit gross: Money) = gross * .25

  def stateIncomeTax(implicit gross: Money) = gross * .05

  def insurancePremiums(implicit gross: Money) = Money(500)

  def retirementFundContributions(implicit gross: Money) = gross * .10
}