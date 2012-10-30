package se.mejsla

object ReflectionToString {

  // prints Type(fieldWithGetterA = valueA, fieldWithGetterB = valueB)
  def reflectionToString(obj: AnyRef): String = {
    import scala.reflect.runtime._

    val instanceMirror = currentMirror.reflect(obj)
    
    // the name of the class
    val builder = StringBuilder.newBuilder
    builder ++= instanceMirror.symbol.name.toString

    // each public getter if there are any
    val declaredGetters = instanceMirror.symbol.typeSignature.declarations.filter(_.asTerm.isGetter)
    if (declaredGetters.nonEmpty) {

      builder += '('
    
      builder ++= declaredGetters.map { getterSymbol =>
        val getterMirror = instanceMirror.reflectMethod(getterSymbol.asMethod) 
        getterSymbol.name + "=" + getterMirror()
      }.mkString(", ")

      builder += ')'
    }

    builder.toString
  }


}
