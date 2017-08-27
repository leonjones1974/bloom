package uk.camsw.bloom

import uk.camsw.bloom.HashFunction._

class HashFunctionTest extends PropertyDrivenTest {

  property("bound transforms any integer to be within bounds") {
    forAll { (n: Int, r: Range) =>
      bound(r)(n) should (be >= r.min and be <= r.max)
    }
  }

  property("for any bounded hashing function the int hash should be within the given range") {
    forAll(algos, randomStrings) { (algo, s) =>
      boundedHash(algo, 0 to 999)(s) should (be >= 0 and be <= 999)
    }
  }

  // TODO: Check collision rate

}
