start /B %~dp0jre\bin\javaw.exe -jar %~dp0main.jar -d32
@if errorlevel 1 pause