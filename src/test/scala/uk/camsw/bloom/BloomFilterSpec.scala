package uk.camsw.bloom

import uk.camsw.bloom.Key._

class BloomFilterSpec extends Spec {

  case class Employee(name: String, id: Int)
  val employee = Employee("Me", 43)
  implicit val id: Key[Employee] = _.id

  describe("Bloom Filter") {

    it("can be used with members of key") {
      aBloomFilter() + employee should possiblyContain[Employee](employee.id)
      aBloomFilter() + employee should possiblyContain[Employee](employee)
    }

    it("can be used in a monadic fashion") {
      val f = aBloomFilter[Int]() + 123 + 999
      println(f contains "123")
      println(f contains "999")
      println(f contains "1343")


      for {
        c1 <- f contains 123
        c2 <- f contains 1343
        c3 <- f contains 999
      } yield ???
    }
  }
}
