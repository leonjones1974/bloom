package uk.camsw.bloom

import uk.camsw.bloom.BitSetImplicits._

import scala.collection.immutable.BitSet

/**
  * TODO: LJ - Cats appears to provide a test framework for the monoid laws.
  * Return to this and work out what that classpath needs to look like
  */
class BitSetMonoidTest extends PropertyDrivenTest {

  property("the zero identity is the empty bit set") {
    bitSetMonoid.empty shouldBe BitSet()
  }

  property("the combination of 2 bit sets should be a bitwise OR") {
    forAll(randomBitSet, randomBitSet) { (bs1, bs2) =>
      val combined = bs1 combine bs2
      (bs1 subsetOf combined) && (bs2 subsetOf combined) shouldBe true
      combined -- bs1 -- bs2 shouldBe empty
    }
  }

  property("left identity + x == x") {
    forAll(randomBitSet) { (bs1) =>
      (BitSet() combine bs1) shouldBe bs1
    }
  }
  property("x + right identity == x") {
    forAll(randomBitSet) { (bs1) =>
      (bs1 combine BitSet()) shouldBe bs1
    }
  }

  property("the combination of bit sets should be associative") {
    forAll(randomBitSet, randomBitSet, randomBitSet) {
      (bs1, bs2, bs3) => {
        bs1.combine(bs2).combine(bs3) shouldEqual bs1.combine(bs2.combine(bs3))
      }
    }
  }
}

