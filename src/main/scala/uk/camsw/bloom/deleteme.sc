import algebra.Monoid
import cats._
import cats.implicits._
import scala.collection.immutable.BitSet

val slots: BitSet = BitSet.empty
val bs = BitSet.fromBitMask(Array.fill(1000)(0l))
val bs2 = BitSet.fromBitMask(Array.fill(1000)(0l))

bs == bs2

//
//"".hashCode
//bs(0)
//bs(1)
//val bs2 = bs + 3
//bs2(0)
//bs2(3)
//(bs2 + 10023)
//
//val x =  Monoid[String]
//x.empty
//x.combine("a", "b")




