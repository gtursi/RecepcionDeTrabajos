cd %~dp0
@java -cp h2*.jar org.h2.tools.Server -tcpShutdown tcp://localhost:9092
@if errorlevel 1 pause