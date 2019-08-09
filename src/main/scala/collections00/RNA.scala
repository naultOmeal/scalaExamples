package collections00

import scala.collection.IndexedSeqLike
import collection.mutable.{Builder, ArrayBuffer}
import collection.generic.CanBuildFrom

final class RNA private (val groups: Array[Int], val length: Int) extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA] {
  import RNA._

  // Mandatory re-implementation of `newBuilder` in `IndexedSeq`
  // Access modifiers in Scala can be augmented with qualifiers.
  // A modifier of the form private[X] or protected[X] means that access is private or protected "up to" X,
  // where X designates some enclosing package, class or singleton object.
  override protected[this] def newBuilder: Builder[Base, RNA] = RNA.newBuilder
  // This points down to the companion object for the builder def

  // Mandatory implementation of `apply` in `IndexedSeq`
  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }

  // Optional re-implementation of foreach,
  // to make it more efficient.
  override def foreach[U](f: Base => U): Unit = {
    var i = 0
    var b = 0
    while (i < length) {
      b = if (i % N == 0) groups(i / N) else b >>> S
      f(Base.fromInt(b & M))
      i += 1
    }
  }
}

object RNA{
  private val S: Int = 2
  private val N: Int = 32 / S
  private val M: Int = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- buf.indices){
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    new RNA(groups, buf.length)
  }

  def apply(bases: Base*): RNA = fromSeq(bases)

  def newBuilder: Builder[Base, RNA] = new ArrayBuffer[Base] mapResult fromSeq

  // This makes sure one gets the correct typing when the builder is used
  implicit def canBuildFrom: CanBuildFrom[RNA, Base, RNA] =
    new CanBuildFrom[RNA, Base, RNA] {
      def apply(): Builder[Base, RNA] = newBuilder
      // This allows for special building and dynamic typing
      def apply(from: RNA): Builder[Base, RNA] = newBuilder
    }
}