package collections01

import collection.{Iterator, SortedSet, SortedSetLike, mutable}
import collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer

class Pokedex(val entries: Set[Int], val length: Int) extends SortedSet[Pokemon] with SortedSetLike[Pokemon, Pokedex]{ self =>
  import Pokedex._

  override def empty: Pokedex = Pokedex.empty

  override protected[this] def newBuilder: mutable.Builder[Pokemon, Pokedex] = Pokedex.newBuilder

  override implicit def ordering: Ordering[Pokemon] = Ordering[Pokemon]

  override def rangeImpl(from: Option[Pokemon], until: Option[Pokemon]): Pokedex = throw new Exception("Not Implemented")

  override def contains(elem: Pokemon): Boolean = entries.contains(Pokemon.toInt(elem))

  override def +(elem: Pokemon): Pokedex = {
    if (contains(elem)){
      this
    } else {
      new Pokedex(entries + Pokemon.toInt(elem), length + 1)
    }
  }

  override def -(elem: Pokemon): Pokedex = {
    if (contains(elem)){
      new Pokedex(entries - Pokemon.toInt(elem), length - 1)
    } else {
      this
    }
  }

  override def iterator: Iterator[Pokemon] = iteratorFrom(Pokemon.fromInt(0))

  override def keysIteratorFrom(start: Pokemon): Iterator[Pokemon] = new Iterator[Pokemon]{
    private var current = Pokemon.toInt(start)
    private val end = self.length
    def hasNext: Boolean = {
      while (current != end && self.contains(Pokemon.fromInt(current))) current += 1
      current != end
    }
    def next(): Pokemon = {
      println(current)
      if (hasNext) {
        val r = current; current += 1; Pokemon.fromInt(r)
      }
      else Iterator.empty.next()
    }
  }

  override def stringPrefix = "Pokedex"
}

object Pokedex{
  def empty: Pokedex = new Pokedex(Set[Int](), 0)

  def fromSeq(buf: Seq[Pokemon]): Pokedex = {
    val entries: Set[Int] = buf.map(Pokemon.toInt(_)).toSet
    new Pokedex(entries, entries.size)
  }

  def apply(entries: Pokemon*): Pokedex = fromSeq(entries)

  def newBuilder: mutable.Builder[Pokemon, Pokedex] = new ArrayBuffer[Pokemon] mapResult fromSeq

  // This makes sure one gets the correct typing when the builder is used
  implicit def canBuildFrom: CanBuildFrom[Pokedex, Pokemon, Pokedex] =
    new CanBuildFrom[Pokedex, Pokemon,Pokedex] {
      def apply(): mutable.Builder[Pokemon, Pokedex] = newBuilder
      // This allows for special building and dynamic typing
      def apply(from: Pokedex): mutable.Builder[Pokemon, Pokedex] = newBuilder
    }
}
