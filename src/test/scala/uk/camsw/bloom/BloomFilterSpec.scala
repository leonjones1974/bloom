package uk.camsw.bloom

import uk.camsw.bloom.Id._

class BloomFilterSpec extends Spec {

  case class Employee(name: String, id: Int)

  val employee = Employee("Me", 43)

  implicit val id: Id[Employee] = Id[Employee](_.id.toString)

  describe("Bloom Filter") {

    it("returns a possibly when a key might exist") {
      val f = aBloomFilter[Employee, Int]() :+ employee
      f find employee.id shouldBe Possibly(employee.id)
    }

    it("the key can be a string") {
      val f = aBloomFilter[String, String]() :+ "abc"
      f find "abc" shouldBe Possibly("abc")
    }

    it("returns no when a key definitely does not exit") {
      aBloomFilter[String, Int]() find 123 shouldBe No
    }
  }
}
