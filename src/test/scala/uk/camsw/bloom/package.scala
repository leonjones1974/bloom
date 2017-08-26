package uk.camsw

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

package object bloom {
  trait PropertyDrivenTest extends PropSpec with Matchers with GeneratorDrivenPropertyChecks with Checkers {
    implicit override val generatorDrivenConfig = PropertyCheckConfig(maxDiscarded = 0)
  }
}
