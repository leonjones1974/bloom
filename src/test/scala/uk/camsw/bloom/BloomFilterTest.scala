package uk.camsw.bloom

import com.roundeights.hasher.Algo

class BloomFilterTest extends PropertyDrivenTest {

  val simpleHash = HashFunction.boundedHash(Algo.md5, 0 to 10000)

  property("the filter should indicate possible inclusion for all added values") {
    forAll() { (s: String) =>
      BloomFilter(Seq(simpleHash)) + s should possiblyContain(s)
    }
  }
}
