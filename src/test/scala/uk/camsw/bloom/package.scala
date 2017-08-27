package uk.camsw

import com.roundeights.hasher.Algo
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}
import org.scalatest.{Matchers, PropSpec}

import scala.collection.immutable.BitSet
import scala.util.Random

package object bloom {

  trait PropertyDrivenTest extends PropSpec
    with Matchers
    with GeneratorDrivenPropertyChecks
    with Checkers
    with CustomGenerators
    with CustomMatchers {

    implicit override val generatorDrivenConfig = PropertyCheckConfig(maxDiscarded = 0)

    val random = new Random()
    val emptyBitSet = BitSet()
  }

  trait CustomMatchers {

    class FilterPossiblyContains(expected: String) extends Matcher[BloomFilter] {
      def apply(left: BloomFilter) = {
        MatchResult(
          (left contains expected) == Possibly,
          s"""Bloom filter does not contain "$expected"""",
          s"""Bloom filter possibly contains "$expected""""
        )
      }
    }

    def possiblyContain(expected: String) = new FilterPossiblyContains(expected)
  }

  object CustomMatchers extends CustomMatchers

  trait CustomGenerators {

    def randomStrings: Gen[String] = for {
      n <- Gen.choose(0, 50)
      s <- Gen.listOfN(n, Gen.alphaChar).map(_.mkString)
    } yield s

    def listOfRandomStrings(n: Int) =
      Gen.listOfN(n, randomStrings)

    def randomBitSet: Gen[BitSet] = for {
      l <- Gen.choose(0, 1000)
      xs <- Gen.listOfN(l, Gen.choose(0, 1000)).map(_.toSet)
    } yield xs.foldLeft(BitSet())(_ + _)

    implicit val ranges: Arbitrary[Range] = Arbitrary(for {
      min <- Gen.choose(-1000, 1000)
      max <- Gen.choose(1001, Int.MaxValue)
    } yield Range(min, max))

    implicit val algos: Arbitrary[Algo] = Arbitrary(Gen.oneOf(BloomFilter.HashingAlgos.toSeq))
  }

  object CustomGenerators extends CustomGenerators
}
