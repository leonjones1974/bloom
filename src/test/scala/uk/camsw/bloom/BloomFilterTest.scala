package uk.camsw.bloom

class BloomFilterTest extends PropertyDrivenTest {

  property("the filter should indicate possible inclusion for all added values") {
    forAll(emptyFilter, randomStrings) { (f, s) =>
      (emptyFilter + s) should possiblyContain(s)
    }
  }
}
