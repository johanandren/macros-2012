package se.mejsla

import scala.reflect.macros.Context
import language.experimental.macros

object MacroToString {

  def macroToString[A](obj: A): String = macro toStringMacro[A]

  def toStringMacro[A](c: Context)(obj: c.Expr[A]): c.Expr[String] = {
    import c.universe._

    val tpe = obj.actualType
    val className = tpe.typeSymbol.name

    val declaredGetters = tpe.declarations.filter(_.asTerm.isGetter)

    // .+ is actually the method "$plus"
    val plus = "$plus"

    // start: "ClassName"
    val startLit = Literal(Constant(className + "("))
    val getterBody = declaredGetters.foldLeft(startLit: TermTree){ (soFar, getSym) =>
      // ((((soFar.+(", ")).+(fieldName)).+("=")).+(obj.getter))
      val name = Apply(Select(soFar, plus), List(Literal(Constant(getSym.name.toString + "="))))
      val namePlusAccess = Apply(Select(name, plus), List(Select(obj.tree, getSym.name)))
      // unless last getter, append a ", "
      if (getSym != declaredGetters.last) {
        Apply(Select(namePlusAccess, plus), List(Literal(Constant(", "))))
      } else {
        namePlusAccess
      }
    }

    // append end ")"
    val body = Apply(Select(getterBody, plus), List(Literal(Constant(")"))))
  

    c.Expr[String](body)
  }
}
