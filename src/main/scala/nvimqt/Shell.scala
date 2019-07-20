package nvimqt

import qt.macros.Qt

import scalanative._
import unsafe._
import cxx._

@Cxx(namespace = "NeovimQt")
@include("<gui/shell.h>")
class Shell extends ShellWidget {

}
object Shell {
  @constructor
  def apply(connector: NeovimConnector, @ref opts: ShellOptions): Shell = extern
}
