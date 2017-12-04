import java.util.List;

/**
 * CSV Reader demo app
 *
 * @author Yevhen Danchenko
 */
public class App {
    /**
     * Entry point
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String fileName = args[0];
        CSVReader reader = new CSVReader(fileName);
        // Print entire csv data into console
        printDataSet(reader.getHeaders(), reader.getCsvData());
        // Print all values in the first column
        System.out.println(reader.getColumn(0));
    }

    /**
     * Print the data set to console
     *
     * @param columnNames the column names
     * @param dataSet     the data to display
     */
    private static void printDataSet(List columnNames, List<List<String>> dataSet) {
        // Iterate over the data set lines (rows)
        for (int i = 0; i < dataSet.size(); i++) {
            System.out.println("\n====== [ Row # " + i + " ] ==========================");
            // Iterate over the data set items (columns)
            for (int j = 0; j < dataSet.get(i).size(); j++) {
                System.out.println("column [ " + columnNames.get(j) + " ] = " + dataSet.get(i).get(j));
            }
        }
    }
}