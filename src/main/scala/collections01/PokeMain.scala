package collections01

object PokeMain {

  def main(args: Array[String]): Unit = {
    val pd00 = Pokedex(Bulbasaur)
    println(pd00, pd00.entries)
    val pd01 = Pokedex(Charmander)
    println(pd01)
    val pd02 = Pokedex(Bulbasaur, Squirtle)
    println(pd02)
    println(pd00 union pd01)
  }
}
