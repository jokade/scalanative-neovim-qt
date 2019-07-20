package nvimqt

import scalanative._
import unsafe._
import cxx._

@Cxx(namespace = "NeovimQt")
@include("<gui/shell.h>")
class ShellOptions {

}
object ShellOptions {
  @constructor
  def apply(): ShellOptions = extern
}
