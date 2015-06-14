@echo off
set /p movielistFilename="Movies' file name: "
set /p sqliteDbName="Database name (provide suffix, too): "
perl fetchContinuously.pl %movielistFilename% > generatedQueries.sql
runSqliteScript.py generatedQueries.sql %sqliteDbName%