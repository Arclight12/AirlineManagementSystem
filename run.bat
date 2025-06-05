@echo off
javac -cp "lib/postgresql-42.7.2.jar" src/*.java
java -cp "src;lib/postgresql-42.7.2.jar" MainGUI