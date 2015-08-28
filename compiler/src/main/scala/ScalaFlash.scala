import scala.tools.nsc.{Phase, Global, Settings}
import scala.tools.nsc.plugins.{PluginComponent, Plugin}

class ScalaFlash(val global: Global) extends Plugin {
  import global._

  override val name: String = "ScalaFlash"
  override val components: List[PluginComponent] = List[PluginComponent](Component)
  override val description: String = "Scala code to Flash AVM2 bytecode generator"

  private object Component extends PluginComponent {
    val global: ScalaFlash.this.global.type = ScalaFlash.this.global

    override val runsRightAfter = Some("refchecks")
    val runsAfter = List("refchecks")

    // Using the Scala Compiler 2.8.x the runsAfter should be written as below
    // val runsAfter = List[String]("refchecks");
    val phaseName = ScalaFlash.this.name


    def newPhase(_prev: Phase) = new DivByZeroPhase(_prev)

    class DivByZeroPhase(prev: Phase) extends StdPhase(prev) {
      override def name = "DivByZeroPhase"
      def apply(unit: CompilationUnit) {
        global.reporter.warning(null, "Hello warning1 !")

        for ( tree @ Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0)))) <- unit.body;
              if rcvr.tpe <:< definitions.IntClass.tpe)
        {
          global.reporter.error(tree.pos, "definitely division by zero1")
        }
      }
    }
  }

}

object ScalaFlash {

  def parseCode = {
    val a = (scala.tools.nsc.FatalError)
    println("Code parsed")


  }
}
