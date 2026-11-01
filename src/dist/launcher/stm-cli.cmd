@echo off
rem stm-cli launcher for Windows.
setlocal
set "STM_CLI_HOME=%~dp0.."
if defined JAVA_HOME (
  set "JAVA=%JAVA_HOME%\bin\java.exe"
) else (
  set "JAVA=java"
)
"%JAVA%" %JAVA_OPTS% -jar "%STM_CLI_HOME%\lib\stm-cli.jar" %*
exit /b %ERRORLEVEL%
