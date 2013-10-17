package mydsl
import org.scalatest._

// http://www.amazon.com/Programming-Scala-Scalability-Functional-Objects/dp/0596155956/ref=sr_1_4?ie=UTF8&qid=1381962239&sr=8-4&keywords=dean+wampler

// internal DSL -- no parser
// external DSL -- use parsers

/*
 * 
 * apply gives majic parens operator behavior
 * 
 infix operator
 
 http://www.scala-lang.org/old/node/118
 
 for 2.weeks create implicit Int to Duration and Duration.weeks() method
 
 return this from methods that need to return the object type (builder pattern)
   or have the builder return whatever type is needed by the DSL
   
   def is (builder: XBuilder) = apply(builder)
   
   // http://examples.oreilly.com/9780596155964/
 
 */
class DslTest extends FunSpec with ShouldMatchers {
  
  case class Person(name: String) {
    def nameLen = name.length
  }
  
  object Person {
    def apply(person: Person) = person.nameLen
  }
  
	describe("when apply") {
	  it("should") {
	    
	    import Person._
	    
	    val me = Person("todd")
//	    val i: Int = me
	  }
	}
}
