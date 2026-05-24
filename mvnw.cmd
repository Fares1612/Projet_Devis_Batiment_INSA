@REM Maven Wrapper - lance Maven sans installation globale
@echo off
setlocal EnableDelayedExpansion

if "%HOME%" == "" set "HOME=%HOMEDRIVE%%HOMEPATH%"

REM Ignorer JAVA_HOME si pointe vers javapath (Oracle)
if not "%JAVA_HOME%"=="" (
  echo !JAVA_HOME! | findstr /I "javapath" >nul && set "JAVA_HOME="
)
if not "%JAVA_HOME%"=="" (
  if not exist "!JAVA_HOME!\bin\java.exe" set "JAVA_HOME="
)

REM JDK Microsoft installe par winget
if "!JAVA_HOME!"=="" (
  if exist "C:\Program Files\Microsoft\jdk-17.0.19.10-hotspot\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.19.10-hotspot"
  )
)
if "!JAVA_HOME!"=="" (
  for /d %%D in ("C:\Program Files\Microsoft\jdk-*") do (
    if exist "%%D\bin\java.exe" set "JAVA_HOME=%%D"
  )
)

REM Sinon chercher java dans le PATH
if "!JAVA_HOME!"=="" (
  for /f "tokens=*" %%i in ('where java 2^>nul') do (
    set "JAVA_EXE=%%i"
    for %%j in ("%%~dpi..") do set "JAVA_HOME=%%~fj"
    goto JavaTrouve
  )
)
:JavaTrouve

if "!JAVA_HOME!"=="" (
  echo.
  echo ERREUR : JDK 17 introuvable.
  echo Installez : winget install Microsoft.OpenJDK.17
  echo Puis fermez et rouvrez le terminal.
  echo.
  exit /B 1
)

if not exist "!JAVA_HOME!\bin\java.exe" (
  echo ERREUR : JAVA_HOME invalide : !JAVA_HOME!
  exit /B 1
)

set MAVEN_PROJECTBASEDIR=%~dp0
if "%MAVEN_PROJECTBASEDIR:~-1%"=="\" set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
"!JAVA_HOME!\bin\java.exe" -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
exit /B %ERRORLEVEL%
