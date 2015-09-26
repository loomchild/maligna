@echo off

set SCRIPT_DIR=%~d0%~p0
set PROJECT_HOME=%SCRIPT_DIR%..\..
set BIN_DIR="%PROJECT_HOME%\bin"
set EXAMPLE_DIR="%PROJECT_HOME%\example"

%BIN_DIR%\maligna parse -c txt %EXAMPLE_DIR%/txt/poznan-pl.txt %EXAMPLE_DIR%/txt/poznan-de.txt  |  %BIN_DIR%\maligna modify -c split-sentence  |  %BIN_DIR%\maligna modify -c trim  |  %BIN_DIR%\maligna align -c viterbi -a oracle,normal -d %EXAMPLE_DIR%/align/human/poznan-oracle.al -n char -s iterative-band  >  poznan-oracle.al
