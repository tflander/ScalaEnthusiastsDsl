Step 1:

Going rogue.  Rather than following the author, I refactored the test and code to remove redundancy

old test:

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


new test:

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


Step 0:

Pulled code from the book http://www.amazon.com/Programming-Scala-Scalability-Functional-Objects/dp/0596155956/ref=sr_1_4?ie=UTF8&qid=1381962239&sr=8-4&keywords=dean+wampler

...I presume this is fair use.  I claim no copyright rights.

I got the code to compile and the API test to run green as a scalatest

Commented out the internal DSL test
Commented out the external DSL code and tests

