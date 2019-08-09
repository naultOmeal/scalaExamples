package collections01

abstract class Pokemon extends Ordered[Pokemon]{
  def compare(that: Pokemon): Int = Pokemon.toInt(this) - Pokemon.toInt(that)
}

case object Bulbasaur extends Pokemon
case object Ivysaur extends Pokemon
case object Venusaur extends Pokemon
case object Charmander extends Pokemon
case object Charmeleon extends Pokemon
case object Charizard extends Pokemon
case object Squirtle extends Pokemon
case object Wartortle extends Pokemon
case object Blastoise extends Pokemon

object Pokemon{
  val fromInt: Int => Pokemon = Array(Bulbasaur, Ivysaur, Venusaur, Charmander, Charmeleon, Charizard, Squirtle, Wartortle, Blastoise)
  val toInt: Pokemon => Int = Map(Bulbasaur -> 0, Ivysaur -> 1, Venusaur -> 2, Charmander -> 3, Charmeleon -> 4, Charizard -> 5, Squirtle -> 6, Wartortle -> 7, Blastoise -> 8)
}
