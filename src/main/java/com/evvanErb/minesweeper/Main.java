package com.evvanErb.minesweeper;
import com.evvanErb.minesweeper.view.terminalview.TerminalView;
import com.evvanErb.minesweeper.view.javafxview.JavaFXView;

public class Main {

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("-t")) {
            TerminalView terminalView = new TerminalView();
            terminalView.main();
        }
        else {
            JavaFXView javaFXView = new JavaFXView();
            javaFXView.main(new String[]{});
        }
    }
}
