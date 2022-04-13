# Minesweeper
Java implementation of Minesweeper using JDK 17, JavaFX 17, and Maven 3.8.5

## Usage

To build and run, call `mvn clean install` followed by `mvn package` then run `java --module-path $PATH_TO_FX --add-modules javafx.controls -jar target/Minesweeper-1.0.jar` 
for the JavaFX version, or `java -jar target/Minesweeper-1.0.jar -t`
for the terminal version. The JavaFX Version requires that you have JavaFX installed with 
the path variable to the installation being `$PATH_TO_FX`. For more information on how to install and setup JavaFX see https://openjfx.io/openjfx-docs/#install-javafx

## JavaFX Gameplay

Left click on a cell to reveal it. If it is a mine you lose. If it is a not a mine, it will reveal itself
and if it does not have any adjacent mines, it will reveal its neighbors. If it has mines adjacent, it will display a number
that represents how many mines are adjacent to it. To win, reveal all cells that are not mines. You can right click on a cell
to flag it as a mine to help you remember where mines are.