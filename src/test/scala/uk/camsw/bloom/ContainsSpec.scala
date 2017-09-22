package uk.camsw.bloom

class ContainsSpec extends Spec {

  describe("Contains") {
    it("should contain a convenience possibly") {
      Contains.possibly(5) shouldBe Possibly(5)
    }

    it("should contain a convenience no") {
      Contains.no[Int] shouldBe No
    }
  }
}
