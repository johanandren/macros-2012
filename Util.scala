package se

package object mejsla {

 def time(f: => Any)(times: Int = 1) {
    def toText(ns: Long) =
      if (ns >= 1000000) 
        (ns / 1000000) + " ms"
      else
        ns + " ns"

    var totalTime = 0L
    for (i <- 0 until times) {  
      val before = System.nanoTime
      val res = f
      val time = System.nanoTime - before
      totalTime += time
    }
    println(times + " invocations, total: " + toText(totalTime))
      //", avg: " + toText(totalTime / times)) 
  }

  type Name = String
  def microbench(funcs: Seq[(Name, () => Unit)], times: Int = 100000) {
    // vm warmup
    print("VM warmup...")
    for (
      (_, f) <- funcs.par;
      i <- 0 to 100000
    ) { f() }
    println("done")
    
    println("Microbenchmark...")
    for ((name, f) <- funcs) {
      println(name + ":")
      time(f)(times)
    }
  }

  /** Indents the output of showRaw(tree) 
    * Sample usage:
    * val reified = universe.reify(if("a" == 1) 1 else 2)
    * indent(universe.showRaw(reified.tree))
    */
  def indent(text: String): String = {
    
    val builder = StringBuilder.newBuilder
    var level = 0
    def addIndent() { builder.append("  " * level) }
    for (char <- text) { 
      char match {
        case ')' => {
          builder.append('\n')
          level -= 1
          addIndent()
          builder.append(')')
        }
        case '(' => {
          builder.append('(')
          builder.append('\n')
          level += 1
          addIndent()
        }
        case x => {
          builder.append(x)
        }
      }
    }
  
    builder.toString
  }

}
