package uk.camsw.bloom

import com.roundeights.hasher.Algo

class BloomFilterProperties extends PropertyChecks {

  property("the filter should indicate possible inclusion for all added values") {
    forAll() { (size: Int, algo: Algo, s: String) =>
      whenever(size > 0) {
        val f = BloomFilter[String, String](size, Set(algo)) :+ s
        f.find(s) shouldBe Possibly(s)
      }
    }
  }

  property("the filter should add a hash for each algo provided") {
    val updated = BloomFilter[String, String](Seq(hashN(1), hashN(2))) :+ "aValue"
    updated.slots(1) && updated.slots(2) shouldBe true
  }

  property("creation of the filter with a size zero or less is not possible") {
    forAll() { (size: Int, algo: Algo) =>
      whenever(size <= 0) {intercept[IllegalArgumentException]{BloomFilter[String, String](size, Set(algo))}}
    }
  }

}
