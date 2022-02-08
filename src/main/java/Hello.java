import com.opencsv.*;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hello {


    public static void main(String[] args) throws Exception {
        String[] hello=new String[]{"    Hello      2","World"};
        String[] erstens=new String[]{"erstens","    zweitens"};
        List<String[]> stringArray = Arrays.stream(new String[][]{hello,erstens}).toList();
        csvWriterAll(stringArray, Path.of("./twoColumn.csv"));
        Reader reader = Files.newBufferedReader(Path.of("./twoColumn.csv"));
        List<String[]> resualt=oneByOne(reader);
        for (String[] text :resualt
             ) {
            for (String s:text
                 ) {
                System.out.print(s+";");
            }
            System.out.println();
        }
        //System.out.println(resualt.toArray().);
    }

    public static String[] removeLeadingBlank(String[] s){
        for (int i=0;i<s.length;i++) {
            s[i]=s[i].stripLeading();
        }

        return s;
    }


    public static List<String[]> oneByOne(Reader reader) throws Exception {
        List<String[]> list = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            list.add(removeLeadingBlank(line));
        }
        reader.close();
        csvReader.close();
        return list;
    }

    public static void csvWriterAll(List<String[]> stringArray, Path path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        writer.writeAll(stringArray);
        writer.flush();
        writer.close();
    }

    public static List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public static String readAllExample() throws Exception {
        Reader reader = Files.newBufferedReader(Path.of("./twoColumn.csv"));
        return readAll(reader).toString();
    }
}
