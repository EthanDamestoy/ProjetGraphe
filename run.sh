#!/bin/bash
cd "$(dirname "$0")"
mkdir -p bin
find src -name "*.java" > sources.txt
javac -d bin -cp "lib/gs-core-2.0.jar:lib/gs-ui-swing-2.0.jar" @sources.txt
rm sources.txt
java -cp "bin:lib/gs-core-2.0.jar:lib/gs-ui-swing-2.0.jar" graphe.Controleur