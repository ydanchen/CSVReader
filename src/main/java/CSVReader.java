import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides an access to data stored in *.csv files.
 * <p>Headers, rows and columns can be accessed independently
 *
 * @author Yevhen Danchenko
 */
public class CSVReader {
    private final static String DELIMITER_COLON = ",";
    private String fileName;
    private String delimiter;
    private List<String> csvHeaders;
    private List<List<String>> csvRawData;
    private List<List<String>> csvData;

    /**
     * Constructor
     * <p>Colon symbol will be used as a delimiter
     *
     * @param fileName the *.csv file name to read and parse
     */
    public CSVReader(String fileName) {
        this(fileName, DELIMITER_COLON);
    }

    /**
     * Constructor
     *
     * @param fileName  the *.csv file name to read and parse
     * @param delimiter the delimiter.
     */
    public CSVReader(String fileName, String delimiter) {
        this.csvRawData = new ArrayList<>();
        this.fileName = fileName;
        this.delimiter = delimiter;
        processFile();
    }

    /**
     * Returns list of headers (column names) as List of Strings
     *
     * @return list of column names
     */
    public List<String> getHeaders() {
        return csvHeaders;
    }

    /**
     * Returns all csv data except headers
     *
     * @return the list of lists
     */
    public List<List<String>> getCsvData() {
        return csvData;
    }

    /**
     * Returns the index of the column name or -1 if there is no such column
     *
     * @param columnName the column name to find
     * @return the index of the column
     */
    public int getColumnIndex(String columnName) {
        return csvHeaders.indexOf(columnName);
    }

    /**
     * Returns all values in the column specified by index
     *
     * @param columnIndex the index of column
     * @return list of all values in the column
     */
    public List<String> getColumn(int columnIndex) {
        return csvData.stream()
                .map(e -> e.get(columnIndex))
                .collect(Collectors.toList());
    }

    /**
     * Returns all values in the column specified by name
     *
     * @param columnName the name of the column
     * @return list of all values in the column
     */
    public List<String> getColumn(String columnName) {
        return getColumn(getColumnIndex(columnName));
    }

    /**
     * Return all values in a row specified by index
     *
     * @param index the index of the row
     * @return list of all values in the row
     */
    public List<String> getRow(int index) {
        return csvData.get(index);
    }

    // =====================================
    // Private methods
    // =====================================

    /**
     * Split line into tokens by delimiter
     *
     * @param line line to split
     * @return array of tokens
     */
    private String[] getTokens(String line) {
        return line.split(delimiter);
    }

    /**
     * Read csv file into internal collection, normalize it and separate into list of
     * columns and list of data.
     * <p>Empty lines in the original file will be ignored.
     * <p>First line of the file will be used for headers
     */
    private void processFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    csvRawData.add(Arrays.asList(getTokens(line)));
                }
            }
            csvHeaders = csvRawData.get(0);
            csvData = csvRawData.stream()
                    .skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}