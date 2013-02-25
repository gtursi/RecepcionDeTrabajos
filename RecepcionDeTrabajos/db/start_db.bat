cd %~dp0
@java -cp h2.jar org.h2.tools.Server -tcp -tcpAllowOthers -web -baseDir "C:/workspace/RecepcionDeTrabajos/db"
@if errorlevel 1 pause