object CallReflectionToString {

  import se.mejsla._

  def main(args: Array[String]) {

    val instance = new MyClass(1, 2.4F, "Woho!")

    println("All three methods return the same string:")
    println(ReflectionToString.reflectionToString(instance))
    println(instance.toString)

    // warmup
    (1 to 10000).foreach { x =>
      val reflectedString = ReflectionToString.reflectionToString(instance)
      val nativeString = instance.toString
    }

    val iterations = 1000000
    println("Real toString")
    time { instance.toString }(iterations)

    println("Reflection toString")
    time { ReflectionToString.reflectionToString(instance) }(iterations)

  }

}
