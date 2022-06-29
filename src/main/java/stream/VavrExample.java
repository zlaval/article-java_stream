package stream;

import io.vavr.collection.List;

public class VavrExample {

    public static void main(String[] args) {
        List<String> words = List.of("foo", "bar", "home", "sword", "play", "animal", "sword", "car", "sun", "java");
        var result = words.filter(s -> s.length() > 3)
                .drop(2)
                .patch(2, List.of("goku", "kalel"), 4)
                .sorted()
                .map(String::toUpperCase);
        System.out.println(result);
    }
}
