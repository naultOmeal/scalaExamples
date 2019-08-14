package collections02

import scala.collection.generic.CanBuildFrom
import scala.collection.{BitSet, Iterator, SortedSet, SortedSetLike, mutable}
import scala.collection.mutable.ArrayBuffer

abstract class Bitmask extends SortedSet[Int] with SortedSetLike[Int, Bitmask]{
  val length: Int
  val storage: SortedSet[Int]
  val bitCount: Int = storage.size

  override def empty: Bitmask = Bitmask.empty(this.length)
  override protected[this] def newBuilder: mutable.Builder[Int, Bitmask] = Bitmask.newBuilder(length)

  override implicit def ordering: Ordering[Int] = Ordering.Int
  override def keysIteratorFrom(start: Int): Iterator[Int] = storage.keysIteratorFrom(start)
  override def contains(elem: Int): Boolean = storage.contains(elem)
  override def iterator: Iterator[Int] = iteratorFrom(0)
  override def stringPrefix = "Bitmask"

  override def rangeImpl(from: Option[Int], until: Option[Int]): Bitmask = throw new Exception("Not Implemented")
  override def +(elem: Int): Bitmask = {
    require(elem >= 0 && elem < length)
    if(contains(elem)) this
    else Bitmask.BitmaskN(length, storage + elem)
  }
  override def -(elem: Int): Bitmask = {
    require(elem >= 0 && elem < length)
    if(contains(elem)) Bitmask.BitmaskN(length, storage - elem)
    else this
  }
}

object Bitmask{
  def empty(length: Int): Bitmask = BitmaskN(length, BitSet().empty)

  def all(length: Int): Bitmask = BitmaskN(length, BitSet(0 until length:_*))

  def apply(length: Int, elems: Int*): Bitmask = {
    require(length >= elems.size)
    BitmaskN(length, BitSet(elems:_*))
  }

  def fromSeq(length: Int, buf: Seq[Int]): Bitmask = {
    BitmaskN(length, BitSet(buf:_*))
  }

  def newBuilder(length: Int): mutable.Builder[Int, Bitmask] = new ArrayBuffer[Int].mapResult(f => fromSeq(length, f))
  def newBuilder(): mutable.Builder[Int, Bitmask] = new ArrayBuffer[Int].mapResult(f => fromSeq(f.max, f))

  // This makes sure one gets the correct typing when the builder is used
  implicit def canBuildFrom: CanBuildFrom[Bitmask, Int, Bitmask] =
    new CanBuildFrom[Bitmask, Int,Bitmask] {
      def apply(): mutable.Builder[Int, Bitmask] = throw new Exception("Not Implemented")
      // This allows for special building and dynamic typing
      def apply(from: Bitmask): mutable.Builder[Int, Bitmask] = newBuilder(from.length)
    }

  case class BitmaskN(length: Int, storage: SortedSet[Int]) extends Bitmask
}
