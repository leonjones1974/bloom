package uk.camsw.bloom

import uk.camsw.bloom.syntax.contains.{no => nope, _}

class ContainsSpec extends Spec {

  describe("Contains") {

    it("should contain a convenience possibly") {
      possibly(5) shouldBe Possibly(5)
    }

    it("should contain a convenience no") {
      nope[Int] shouldBe No
    }

    it("should introduce creation syntax") {
      1.possibly shouldBe possibly(1)
    }
  }
}
