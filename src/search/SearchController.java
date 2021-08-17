package search;

import java.util.*;

public class SearchController {

    private final Scanner scanner;
    private boolean isSearching = true;
    private final Map<String, List<Integer>> map;
    private final String[] data;

    public SearchController(Scanner scanner, String fileName) {
        this.scanner = scanner;
        DataOperations dataOperations = new DataOperations();
        dataOperations.initData(fileName);
        map = dataOperations.getMap();
        data = dataOperations.getDataHolder();
    }

    public void run() {
        String menu = "\n=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit";

        while (isSearching) {
            System.out.println(menu);
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    search();
                    break;

                case "2":
                    printAll();
                    break;

                case "0":
                    close();
                    break;

                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
        }
    }

    private void search() {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String choice = scanner.nextLine().toUpperCase();

        switch (choice) {
            case "ALL":
                searchAll();
                break;

            case "ANY":
                searchAny();
                break;

            case "NONE":
                searchNone();
                break;

            default:
                System.out.println("Unknown strategy!\n");
        }
    }

    /**
     * print lines containing all the words from the query
     */
    private void searchAll() {
        String[] words = getQuery();
        Set<Integer> possibleIndexes = new HashSet<>();

        //loop through each word and check if it is in map
        for (String word : words) {
            if (map.containsKey(word.toLowerCase())) {
                //word is in map, add it's position to possible matches
                possibleIndexes.addAll(map.get(word.toLowerCase()));
            }
        }

        boolean foundMatch = false;

        //check if possible index to see if that index has whole query
        for (int idx : possibleIndexes) {
            String line = data[idx];
            boolean isMatch = true;

            //loop through words queried and see if each line contains ALL words in query
            for (String currentWord : words) {
                if (!line.toLowerCase().contains(currentWord.toLowerCase())) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                System.out.println(line);
                foundMatch = true;
            }
        }

        if (!foundMatch) {
            System.out.println("No matching results found.");
        }
    }

    /**
     *  print the lines containing at least one word from the query.
     */
    private void searchAny() {
        String[] words = getQuery();
        Set<Integer> seenIndexes = new HashSet<>();
        boolean matchFound = false;

        for (String word : words) {
            if (map.containsKey(word.toLowerCase())) {
                List<Integer> indexes = map.get(word.toLowerCase());
                for (int idx : indexes) {
                    if (!seenIndexes.contains(idx)) {
                        System.out.println(data[idx]);
                        matchFound = true;
                    }
                }
                seenIndexes.addAll(indexes);
            }
        }

        if (!matchFound) {
            System.out.println("No matching people found.");
        }
    }

    /**
     * print lines that do not contain words from the query at all
     */
    private void searchNone() {
        String[] words = getQuery();
        Set<Integer> seenIndexes = new HashSet<>();
        boolean matchFound = false;

        for (String word : words) {
            if (map.containsKey(word.toLowerCase())) {
                seenIndexes.addAll(map.get(word.toLowerCase()));
            }
        }

        //print all indexes not in seenIndexes
        for (int i = 0; i < data.length; i++) {
            if (!seenIndexes.contains(i)) {
                System.out.println(data[i]);
                matchFound = true;
            }
        }

        if (!matchFound) {
            System.out.println("No matching people found.");
        }
    }

    private String[] getQuery() {
        System.out.println("\nEnter a name or email to search all suitable people.");
        String query = scanner.nextLine();
        return query.split("\\s+".trim());
    }

    private void printAll() {
        System.out.println("\n=== List of people ===");
        for (String line : data) {
            System.out.println(line);
        }
    }

    private void close() {
        this.isSearching = false;
        System.out.println("\nBye!");
    }

}
