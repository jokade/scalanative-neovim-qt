import nvimqt.{Shell, ShellOptions, ShellWidget}
import qt.core.QString
import qt.widgets.{QApplication, QMainWindow}

import scala.scalanative.unsafe.Zone

object Main {
  def main(args: Array[String]): Unit = Zone{ implicit z =>
    QApplication(args)

    val win = QMainWindow()
    win.setWindowTitle("nvim-qt demo")

    val nvim = Shell(null,ShellOptions())
    win.setCentralWidget(nvim)

    win.show()
    QApplication.exec()
  }
}
