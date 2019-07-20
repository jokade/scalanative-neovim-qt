package nvimqt

import qt.core.QString
import qt.macros._
import qt.widgets.QWidget

import scalanative._
import unsafe._
import cxx._

@Qt
@include("<gui/shellwidget/shellwidget.h>")
class ShellWidget extends QWidget {

}

object ShellWidget {
  def fromFile(@ref file: QString): ShellWidget = extern
}
