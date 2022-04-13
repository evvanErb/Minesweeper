package com.evvanErb.minesweeper;
import com.evvanErb.minesweeper.view.terminalview.TerminalView;
import com.evvanErb.minesweeper.view.javafxview.JavaFXView;

public class Main {

    public static void main(String[] args) {
        //TerminalView terminalView = new TerminalView();
        //terminalView.main();

        JavaFXView javaFXView = new JavaFXView();
        javaFXView.main(new String[] {});
    }
}
