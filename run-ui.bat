@echo off
echo ========================================
echo  Social Network Graph Analysis Web UI
echo ========================================
echo.

REM Check if Maven is available
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo Maven found! Building and running the application...
    mvn spring-boot:run
) else (
    echo Maven not found. Please install Maven or use an IDE like IntelliJ IDEA.
    echo.
    echo Alternative options:
    echo 1. Install Maven from https://maven.apache.org/download.cgi
    echo 2. Open the project in IntelliJ IDEA and run SocialNetworkApplication.java
    echo 3. Use your IDE's built-in Maven support
    echo.
    echo The project structure is ready and all files have been created.
    echo You can find the web UI files in: src/main/resources/static/
    echo.
    pause
)
