package uk.camsw.bloom

class BloomFilterProperties extends PropertyChecks {

  property("the filter should indicate possible inclusion for all added values") {
    forAll() { (s: String) =>
      BloomFilter(Seq(simpleHash(10000))) + s should possiblyContain(s)
    }
  }

  property("the filter should add a hash for each algo provided") {
    val updated = BloomFilter(Seq(hashN(1), hashN(2))) + "aValue"
    updated.slots(1) && updated.slots(2) shouldBe true
  }

}
