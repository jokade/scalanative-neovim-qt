cmake_minimum_required(VERSION 3.14)
project(nvim_qt_widget)

set(CMAKE_CXX_STANDARD 11)

set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/neovim-qt/cmake")
set(CMAKE_INCLUDE_CURRENT_DIR ON)
set(CMAKE_MACOSX_RPATH OFF)

# homebrew/macports/fink etc.
include(MacOSXPaths)

# Qt
set(CMAKE_AUTOMOC ON)
find_package(Qt5Core REQUIRED)
find_package(Qt5Widgets REQUIRED)
find_package(Qt5Network REQUIRED)
#find_package(Qt5Test REQUIRED)

# msgpack
option(USE_SYSTEM_MSGPACK "Use system msgpack libraries " OFF)
if(USE_SYSTEM_MSGPACK)
	find_package(Msgpack REQUIRED)
else()
	add_subdirectory(neovim-qt/third-party)
endif()
include_directories(${MSGPACK_INCLUDE_DIRS})

if(CMAKE_BUILD_TYPE STREQUAL "Release")
    add_definitions(-DQT_NO_DEBUG_OUTPUT)
endif()

set(SRCDIR neovim-qt/src)
set(AUTODIR ${SRCDIR}/auto)
set(GUIDIR ${SRCDIR}/gui)
set(SWDIR ${GUIDIR}/shellwidget)

configure_file(${SRCDIR}/version.h.in ${CMAKE_CURRENT_BINARY_DIR}/version.h)

set(SOURCES ${AUTODIR}/neovimapi0.cpp ${AUTODIR}/neovimapi1.cpp ${AUTODIR}/neovimapi2.cpp ${AUTODIR}/neovimapi3.cpp ${AUTODIR}/neovimapi4.cpp ${AUTODIR}/neovimapi6.cpp
  ${SRCDIR}/neovimconnector.cpp ${SRCDIR}/neovimconnectorhelper.cpp ${SRCDIR}/msgpackrequest.cpp ${SRCDIR}/msgpackiodevice.cpp ${SRCDIR}/function.cpp ${SRCDIR}/util.cpp
  ${GUIDIR}/shell.cpp ${GUIDIR}/popupmenu.cpp ${GUIDIR}/popupmenumodel.cpp ${GUIDIR}/input.cpp
  ${SWDIR}/shellcontents.cpp ${SWDIR}/helpers.cpp ${SWDIR}/shellwidget.cpp ${SWDIR}/konsole_wcwidth.cpp)

add_library(neovimqt SHARED ${SOURCES})
target_include_directories(neovimqt PUBLIC neovim-qt/src neovim-qt/src/gui/shellwidget)
target_link_libraries(neovimqt Qt5::Widgets Qt5::Network msgpackc)

install(TARGETS neovimqt)
install(DIRECTORY neovim-qt/src/ DESTINATION include/neovimqt FILES_MATCHING PATTERN *.h) 
