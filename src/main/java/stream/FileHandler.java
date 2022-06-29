package stream;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class FileHandler {

    public static void main(String[] args) throws IOException, URISyntaxException {
        var fileHandler = new FileHandler();
        fileHandler.execute();
    }

    public void execute() throws IOException, URISyntaxException {
        var uri = getClass().getClassLoader().getResource("names.txt").toURI();
        Predicate<String> lengthPredicate = (s) -> s.length() < 5;
        var names = Files.readAllLines(Path.of(uri))
                .stream()
                .skip(20)
                .filter(lengthPredicate)
                .limit(10)
                .map(String::toLowerCase)
                .toList();

        System.out.println(names);
    }

}
