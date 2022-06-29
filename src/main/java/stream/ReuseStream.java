package stream;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReuseStream {

    public static void main(String[] args) {
        var words = List.of("foo", "bar", "home", "sword", "play", "animal", "sword", "car", "sun", "java");

//        Stream<String> stringStream = words.stream()
//                .filter(s -> s.length() > 3)
//                .map(String::toUpperCase);
//
//        var sortedList = stringStream.sorted().toList();
//        var distinctList = stringStream.distinct().toList();

        Supplier<Stream<String>> streamSupplier = () -> words.stream()
                .filter(s -> s.length() > 3)
                .map(String::toUpperCase);


        var sortedList = streamSupplier.get().sorted().toList();
        var distinctList = streamSupplier.get().distinct().toList();

        System.out.println(sortedList);
        System.out.println(distinctList);

    }

}
