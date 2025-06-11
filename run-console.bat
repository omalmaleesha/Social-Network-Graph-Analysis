@echo off
echo ========================================
echo  Social Network Graph Analysis Console
echo ========================================
echo.

REM Compile Java files
echo Compiling Java files...
javac -d . src/main/java/edu/dsa/model/*.java src/main/java/edu/dsa/service/*.java src/main/java/edu/dsa/Main.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running the console application...
echo.

REM Run the console application
java edu.dsa.Main

echo.
echo Application finished.
pause
