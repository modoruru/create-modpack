@echo off
setlocal enabledelayedexpansion

set "BUILD_DIR=build"
set "MOD_DIR=mod"
set "MODPACK_DIR=modpack"
set "PACKWIZ=packwiz"

where %PACKWIZ% >nul 2>&1
if errorlevel 1 (
    echo ERROR: '%PACKWIZ%' is not installed or not in PATH
    exit /b 1
)

echo ==^> Cleaning %BUILD_DIR%\
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%BUILD_DIR%" || goto :error

echo ==^> Running Gradle build
pushd "%MOD_DIR%"
call gradlew.bat build
set "GRADLE_EXIT=%ERRORLEVEL%"
popd
if not "%GRADLE_EXIT%"=="0" (
    echo ERROR: Gradle build failed
    goto :error
)

echo ==^> Copying built jar to %BUILD_DIR%\mods\
mkdir "%BUILD_DIR%\mods" || goto :error
set "JAR_FOUND="
for %%F in ("%MOD_DIR%\build\libs\*.jar") do (
    echo %%~nF | findstr /i /e /c:"-sources" /c:"-javadoc" >nul
    if errorlevel 1 (
        copy /y "%%F" "%BUILD_DIR%\mods\" >nul
        set "JAR_FOUND=1"
    )
)
if not defined JAR_FOUND (
    echo ERROR: No jar found in %MOD_DIR%\build\libs\
    goto :error
)

echo ==^> Copying %MODPACK_DIR%\ contents to %BUILD_DIR%\
xcopy "%MODPACK_DIR%\*" "%BUILD_DIR%\" /E /I /Y /Q >nul
if errorlevel 1 (
    echo ERROR: Failed to copy modpack contents
    goto :error
)

echo ==^> Creating modpack
pushd "%BUILD_DIR%"
call %PACKWIZ% modrinth export
set "PACKWIZ_EXIT=%ERRORLEVEL%"
popd
if not "%PACKWIZ_EXIT%"=="0" (
    echo ERROR: packwiz export failed
    goto :error
)

echo ==^> Cleaning up
for /r "%BUILD_DIR%" %%F in (*) do (
    if /i not "%%~xF"==".mrpack" del /q "%%F"
)
for /f "delims=" %%D in ('dir /ad /b /s "%BUILD_DIR%" ^| sort /r') do (
    rd "%%D" 2>nul
)

echo ==^> Done
endlocal
exit /b 0

:error
endlocal
exit /b 1