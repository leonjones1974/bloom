package uk.camsw.bloom


import cats.syntax.semigroup._
import com.roundeights.hasher.Algo
import com.roundeights.hasher.Algo._
import uk.camsw.bloom.HashFunction.boundedHash
import uk.camsw.bloom.Implicits._

import scala.collection.immutable.BitSet

case class BloomFilter[A, K] private[bloom](
                                             private[bloom] val fHash: Seq[String => Int],
                                             private[bloom] val slots: BitSet = BitSet())(implicit keyId: Id[K], valueId: Id[A]) {

  def :+(obj: A): BloomFilter[A, K] = {
    val id = valueId(obj)
    copy(slots = fHash.foldLeft(slots)((bs, f) => bs |+| BitSet(f(id))))
  }

  def find(id: K): Contains[K] = if (fHash.forall(hash => slots(hash(keyId(id))))) Possibly(id) else No
}

object BloomFilter {

  val HashingAlgos = Set(md5, crc32, sha1)

  def apply[A, K](size: Int, algos: Set[Algo])(implicit keyId: Id[K], valueId: Id[A]): BloomFilter[A, K] = {
    require(size > 0, "Size must be greater than zero")

    val fHash = algos.map(algo => boundedHash(algo, 0 until size))
    new BloomFilter[A, K](fHash.toSeq)
  }
}



