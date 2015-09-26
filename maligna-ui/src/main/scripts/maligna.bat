@echo off

set SCRIPT_DIR=%~d0%~p0
set PROJECT_HOME=%SCRIPT_DIR%..
set JARS=%PROJECT_HOME%\lib\*
set CLASS=net.loomchild.maligna.ui.console.Maligna

java -cp "%CLASSPATH%";"%PROJECT_HOME%\build\classes";"%JARS%" %CLASS% %* 
