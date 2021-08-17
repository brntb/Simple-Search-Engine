package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SearchController controller;

        if (args.length == 2 && args[0].equals("--data")) {
            String fileName = args[1];
            controller = new SearchController(scanner, fileName);
            controller.run();
        } else {
            System.out.println("Invalid arguments given.");
            System.out.println("Valid argument: --data [filename]");
        }

    }
}
