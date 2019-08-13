package collections01

import collection.{Iterator, SortedSet, SortedSetLike, mutable}
import collection.generic.CanBuildFrom
import scala.collection.mutable.{ArrayBuffer, StringBuilder}

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

  // This is VERY important and needs to be optimized for each implementation
  override def keysIteratorFrom(start: Pokemon): Iterator[Pokemon] = new Iterator[Pokemon]{
    private var current = Pokemon.toInt(start)
    private val end = entries.max + 1 // need the largest number within the list
    def hasNext: Boolean = {
      // keep looking until we find the next element or run out of set
      while( current != end && !self.contains(Pokemon.fromInt(current))) current += 1
      current != end
    }
    def next(): Pokemon = {
      if (hasNext) {
        val r = current; current += 1; Pokemon.fromInt(r)
      }
      else Iterator.empty.next()
    }
  }

//  override def addString(sb: StringBuilder, start: String, sep: String, end: String) = {
//    sb append start
//    var pre = ""
//    val max = nwords * WordLength
//    var i = 0
//    while (i != max) {
//      if (contains(i)) {
//        sb append pre append i
//        pre = sep
//      }
//      i += 1
//    }
//    sb append end
//  }

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
