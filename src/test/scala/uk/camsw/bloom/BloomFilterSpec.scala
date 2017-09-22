package uk.camsw.bloom

import uk.camsw.bloom.Key._

class BloomFilterSpec extends Spec {

  case class Employee(name: String, id: Int)

  val employee = Employee("Me", 43)
  implicit val id: Key[Employee] = _.id

  describe("Bloom Filter") {

    it("returns a possibly when a key might exist") {
      val f = aBloomFilter() :+ employee
      f.find(employee.id) shouldBe Possibly(employee.id.toString)
    }

    it("returns no when a key definitely does not exit") {
      aBloomFilter().find("anything") shouldBe No
    }
  }
}
