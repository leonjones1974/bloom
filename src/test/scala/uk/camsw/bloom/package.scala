package uk.camsw

import com.roundeights.hasher.Algo
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}
import org.scalatest.{FunSpec, Matchers, PropSpec}
import uk.camsw.bloom.BloomFilter.HashingAlgos

import scala.collection.immutable.BitSet
import scala.util.Random

package object bloom {

  val random = new Random()
  val emptyBitSet = BitSet()
  def aBloomFilter[A](size: Int = 1000) = BloomFilter[A](size, HashingAlgos)

  trait Spec extends FunSpec
    with Matchers
    with CustomMatchers
    with CustomGenerators

  trait PropertyChecks extends PropSpec
    with Matchers
    with GeneratorDrivenPropertyChecks
    with Checkers
    with CustomGenerators
    with CustomMatchers {

    implicit override val generatorDrivenConfig = PropertyCheckConfiguration()

  }

  trait CustomMatchers {
  }

  object CustomMatchers extends CustomMatchers

  trait CustomGenerators {

    def randomStrings: Gen[String] = for {
      n <- Gen.choose(0, 50)
      s <- Gen.listOfN(n, Gen.alphaChar).map(_.mkString)
    } yield s

    def listOfRandomStrings(n: Int): Gen[List[String]] =
      Gen.listOfN(n, randomStrings)

    def randomBitSet: Gen[BitSet] = for {
      l <- Gen.choose(0, 1000)
      xs <- Gen.listOfN(l, Gen.choose(0, 1000)).map(_.toSet)
    } yield xs.foldLeft(BitSet())(_ + _)

    implicit val ranges: Arbitrary[Range] = Arbitrary(for {
      min <- Gen.choose(-1000, 1000)
      max <- Gen.choose(1001, Int.MaxValue)
    } yield Range(min, max))

    val hashN: Int => String => Int = n => _ => n

    def simpleHash(size: Int, algo: Algo = Algo.md5) = HashFunction.boundedHash(algo, 0 until size)


    implicit val algos: Arbitrary[Algo] = Arbitrary(Gen.oneOf(HashingAlgos.toSeq))
  }

  object CustomGenerators extends CustomGenerators

}
