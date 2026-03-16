@echo off
cd /d "%~dp0"
mkdir bin 2>nul
dir /s /b src\*.java > sources.txt
javac -d bin -cp "lib/gs-core-2.0.jar;lib/gs-ui-swing-2.0.jar" @sources.txt
del sources.txt
java -cp "bin;lib/gs-core-2.0.jar;lib/gs-ui-swing-2.0.jar" graphe.Controleur