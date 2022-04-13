# Minesweeper
Java implementation of Minesweeper using JDK 17, JavaFX 17, and Maven 3.8.5

## Usage

To build and run, call `mvn clean install` followed by `mvn package` then run `java --module-path $PATH_TO_FX --add-modules javafx.controls -jar target/Minesweeper-1.0.jar` 
for the JavaFX version, or `java -jar target/Minesweeper-1.0.jar -t`
for the terminal version. The JavaFX Version requires that you have JavaFX installed with 
the path variable to the installation being `$PATH_TO_FX`. For more information on how to install and setup JavaFX see https://openjfx.io/openjfx-docs/#install-javafx