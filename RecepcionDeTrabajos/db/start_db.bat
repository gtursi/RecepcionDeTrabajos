cd %~dp0
@java -cp h2.jar org.h2.tools.Server -tcp -web
@if errorlevel 1 pause