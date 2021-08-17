package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataOperations {

    private final Map<String, List<Integer>> map = new HashMap<>();
    private final List<String> dataHolder = new ArrayList<>();

    /**
     *
     * @param fileName      file name to read data from and store it into a map and array
     */
    public void initData(String fileName) {
        File file = new File(fileName);
        int currentLine = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (!line.isEmpty()) {
                    dataHolder.add(line);
                    String[] holder = line.split("\\s+");

                    for (String word : holder) {
                        word = word.toLowerCase();

                        if (map.containsKey(word)) {
                            List<Integer> indexes = map.get(word);
                            indexes.add(currentLine);
                            map.put(word, indexes);
                        } else {
                            List<Integer> list = new ArrayList<>();
                            list.add(currentLine);
                            map.put(word, list);
                        }
                    }
                    currentLine++;
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + fileName);
        }
    }

    public Map<String, List<Integer>> getMap() {
        return map;
    }

    public String[] getDataHolder() {
        String[] data = new String[dataHolder.size()];
        return dataHolder.toArray(data);
    }

}
