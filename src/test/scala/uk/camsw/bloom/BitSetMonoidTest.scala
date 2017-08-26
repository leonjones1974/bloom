package uk.camsw.bloom

import org.scalacheck._

import scala.collection.immutable.BitSet
import BitSetImplicits._

/**
  * TODO: LJ - Cats appears to provide a test framework for the monoid laws.
  * Return to this and work out what that classpath needs to look like
  */
class BitSetMonoidTest extends PropertyDrivenTest {

  val emptyBitSet = Gen.const(BitSet.empty)

  def randomBitSet = for {
    l <- Gen.choose(0, 1000)
    xs <- Gen.listOfN(l, Gen.choose(0, 1000)).map(_.toSet)
  } yield xs.foldLeft(BitSet())(_ + _)

  property("the zero identity is the empty bit set") {
    forAll(emptyBitSet) {
      _ shouldBe BitSet.empty
    }
  }

  property("the combination of 2 bit sets should be a bitwise OR") {
    forAll(randomBitSet, randomBitSet) { (bs1, bs2) =>
      val combined = bs1 combine bs2
      (bs1 subsetOf combined) && (bs2 subsetOf combined) shouldBe true
      combined -- bs1 -- bs2 shouldBe empty
    }
  }

  property("left identity + x == x") {
    forAll(emptyBitSet, randomBitSet) { (emptyBs, bs1) =>
      (bs1 combine emptyBs) shouldBe bs1
    }
  }
  property("x + right identity == x") {
    forAll(randomBitSet, emptyBitSet) { (bs1, emptyBs) =>
      (bs1 combine emptyBs) shouldBe bs1
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

