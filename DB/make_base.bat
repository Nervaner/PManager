@echo off
del test.fdb
"C:\Program Files\Firebird\Firebird_2_5\bin\isql.exe" -q -i create_tables.sql
echo Creation finished.
"C:\Program Files\Firebird\Firebird_2_5\bin\isql.exe" -q -i feel_tables.sql
echo Feeling finished.
