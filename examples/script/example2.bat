@echo off

set SCRIPT_DIR=%~d0%~p0
set PROJECT_HOME=%SCRIPT_DIR%..\..
set BIN_DIR="%PROJECT_HOME%\bin"
set EXAMPLE_DIR="%PROJECT_HOME%\example"

%BIN_DIR%\maligna parse -c txt %EXAMPLE_DIR%\txt\poznan-pl.txt %EXAMPLE_DIR%\txt\poznan-de.txt  |  %BIN_DIR%\maligna modify -c split-sentence  |  %BIN_DIR%\maligna modify -c trim  >  poznan-split.al

type poznan-split.al  |  %BIN_DIR%\maligna align -c fb -a poisson -n word -s iterative-band  |  %BIN_DIR%\maligna select -c one-to-one  |  %BIN_DIR%\maligna select -c fraction -f 0.85  >  poznan-align-length.al

type poznan-split.al  |  %BIN_DIR%\maligna align -c fb -a poisson,translation -n word -s iterative-band -t poznan-align-length.al  >  poznan-align.al
