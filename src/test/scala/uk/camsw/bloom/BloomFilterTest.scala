package uk.camsw.bloom

import com.roundeights.hasher.Algo

class BloomFilterTest extends PropertyDrivenTest {

  val simpleHash = HashFunction.boundedHash(Algo.md5, 0 to 10000)
  val hashN: Int => String => Int = n => _ => n

  property("the filter should indicate possible inclusion for all added values") {
    forAll() { (s: String) =>
      BloomFilter(Seq(simpleHash)) + s should possiblyContain(s)
    }
  }

  property("the filter should add a hash for each algo provided") {
    val updated = BloomFilter(Seq(hashN(1), hashN(2))) + "aValue"
    updated.slots(1) && updated.slots(2) shouldBe true
  }
}
