package uk.camsw

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

package object bloom {
  trait PropertyDrivenTest extends PropSpec with Matchers with GeneratorDrivenPropertyChecks with Checkers {
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
}
