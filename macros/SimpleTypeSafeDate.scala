package se.mejsla

import scala.reflect.macros.Context
import language.experimental.macros

object SimpleTypeSafeDate {

  def df(format: String): String = macro dfMacro
  
  
  def dfMacro(c: Context)(format: c.Expr[String]): c.Expr[String] = {
    import c.universe._
    
    format.tree match {
      
      // is input a string constant and parseable as a dateformat?
      case Literal(Constant(format: String)) => {
        new java.text.SimpleDateFormat(format)
      }

      case _ => throw new IllegalArgumentException("Can only handle strings as parameters")
    }

    // just return original AST
    format
  }


}
