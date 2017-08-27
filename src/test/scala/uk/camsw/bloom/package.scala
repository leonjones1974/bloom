package uk.camsw

import com.roundeights.hasher.Algo
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

import scala.collection.immutable.BitSet
import scala.util.Random
import Algo._

package object bloom {

  trait PropertyDrivenTest extends PropSpec
    with Matchers
    with GeneratorDrivenPropertyChecks
    with Checkers
    with CustomGenerators
    with CustomMatchers {

    implicit override val generatorDrivenConfig = PropertyCheckConfig(maxDiscarded = 0)
  }

  trait CustomMatchers {

    class FilterPossiblyContains(expected: String) extends Matcher[BloomFilter[_]] {
      def apply(left: BloomFilter[_]) = {
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
    def randomString(maxLength: Int): String =
      math.abs(Random.nextInt(Int.MinValue + 1 max maxLength)).toString

    val emptyFilter = BloomFilter()

    def randomStrings: Gen[String] = for {
      n <- Gen.choose(0, 50)
      s <- Gen.listOfN(n, Gen.alphaChar).map(_.mkString)
    } yield s

    val emptyBitSet = Gen.const(BitSet.empty)

    def randomBitSet: Gen[BitSet] = for {
      l <- Gen.choose(0, 1000)
      xs <- Gen.listOfN(l, Gen.choose(0, 1000)).map(_.toSet)
    } yield xs.foldLeft(BitSet())(_ + _)

    implicit val ranges: Arbitrary[Range] = Arbitrary(for {
      min <- Gen.choose(-1000, 1000)
      max <- Gen.choose(1001, Int.MaxValue)
    } yield Range(min, max))

    def algos: Gen[Algo] = Gen.oneOf(md5, crc32, sha1, sha384, bcrypt)
  }

  /**
    *  def md5 = withAlgo( new Algo( () => new MessageDigest("MD5") ) )

    /** SHA1 hashing algorithm */
    def sha1 = withAlgo( new Algo( () => new MessageDigest("SHA-1") ) )

    /** SHA256 hashing algorithm */
    def sha256 = withAlgo( new Algo( () => new MessageDigest("SHA-256") ) )

    /** SHA384 hashing algorithm */
    def sha384 = withAlgo( new Algo( () => new MessageDigest("SHA-384") ) )

    /** sha512 hashing algorithm */
    def sha512 = withAlgo( new Algo( () => new MessageDigest("SHA-512") ) )

    /** CRC32 algorithm */
    def crc32 = withAlgo( new Algo( () => new CRC32Digest ) )

    /** BCrypt hashing, using 10 rounds */
    def bcrypt = withAlgo( new Algo( () => new BCryptDigest(10) ) )
    */

  object CustomGenerators extends CustomGenerators
}
