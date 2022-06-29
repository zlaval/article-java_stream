package stream;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExample {


    private List<String> words = Arrays.asList("foo", "bar", "home", "sword", "play", "animal", "sword", "car", "sun", "java");
    // private List<String> words = Arrays.asList("foo", "bar", "home", "sword");


    public static void main(String[] args) {
        var example = new StreamExample();
        example.operations();
    }

    class Word {
        String name;

        public Word(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "word = " + name;
        }
    }

    class Person {
        String name;
        List<String> phoneNumber;

        public Person(String name, List<String> phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }

    class Student {
        String name;
        Integer birth;

        public Student(String name, Integer birth) {
            this.name = name;
            this.birth = birth;
        }

        @Override
        public String toString() {
            return name + " [" + birth + "]";
        }
    }

    void operations() {
        words.stream().forEach(s -> System.out.println(s));

        var size = words.stream().count();

        var lessThenForCharacters = words.stream().filter(str -> str.length() < 4).toList();
        System.out.println(lessThenForCharacters);

        var orderedList = words.stream().sorted((a, b) -> a.compareTo(b)).toList();
        System.out.println(orderedList);

        List<Word> mappedToWord = words.stream().map(it -> new Word(it)).toList();
        System.out.println(mappedToWord);

        var people = Arrays.asList(
                new Person("zlaval", Arrays.asList("1234567", "3485765", "2348924")),
                new Person("zalerix", Arrays.asList("856774", "234652", "123653"))
        );

        var flatMapped = people.stream().flatMap(person -> person.phoneNumber.stream()).toList();
        System.out.println(flatMapped);

        boolean anyWordLongerThenFiveCharacter = words.stream().anyMatch(it -> it.length() > 5);
        System.out.println(anyWordLongerThenFiveCharacter);

        var distinctList = words.stream().distinct().toList();
        System.out.println(distinctList);

        var sum = IntStream.range(0, 100).reduce(100, (accumulator, number) -> accumulator += number);
        System.out.println(sum);

        words.stream()
                .map(String::toUpperCase)
                .peek(System.out::println)
                .toList();

        var combinedOperations = words.stream()
                .distinct()
                .filter(it -> it.length() > 3)
                .map(String::toUpperCase)
                .sorted()
                .map(it -> new Word(it + " is a word"))
                .toList();

        System.out.println(combinedOperations);


        words.stream()
                .sorted((a, b) -> {
                    System.out.println("Sort element, compare " + a + " to " + b);
                    return a.compareTo(b);
                })
                .map(str -> {
                    System.out.println("Map element: " + str);
                    return str;
                })
                .filter(str -> {
                    System.out.println("Filter element: " + str);
                    return str.length() > 3;
                })
                .forEach(str -> System.out.println("forEach loop: " + str));


        words.stream()
                .filter(str -> {
                    System.out.println("Filter element: " + str);
                    return str.length() > 3;
                })
                .map(str -> {
                    System.out.println("Map element: " + str);
                    return str;
                })
                .sorted((a, b) -> {
                    System.out.println("Sort element, compare " + a + " to " + b);
                    return a.compareTo(b);
                })
                .forEach(str -> System.out.println("forEach loop: " + str));


        var stats = IntStream.of(1, 3, 5, 7, 9).summaryStatistics();
        Stream<Integer> boxedStream = IntStream.of(1, 3, 5, 7, 9).boxed();

        System.out.println(stats);

        var students = List.of(
                new Student("Joe", 2013),
                new Student("Jill", 1987),
                new Student("Alize", 1991),
                new Student("Zalan", 1987),
                new Student("Eric", 2013),
                new Student("Alex", 2015),
                new Student("Laura", 1991),
                new Student("Brian", 2001),
                new Student("Deniell", 2013),
                new Student("Clark", 2005)
        );

        var studentsGroupedByBirthYear = students.stream().collect(Collectors.groupingBy(student -> student.birth));
        System.out.println(studentsGroupedByBirthYear);

        Double averageBirthYear = students.stream().collect(Collectors.averagingInt(s -> s.birth));
        System.out.println(averageBirthYear);

        var partitionedData = students.stream().collect(Collectors.partitioningBy(s -> s.birth < 2005));
        System.out.println(partitionedData);

        students.stream().mapToInt(s -> s.birth).max();
        students.stream().map(s -> s.birth).max(Integer::compareTo);
        students.stream().collect(Collectors.summingInt(s -> s.birth));
        students.stream().mapToInt(s -> s.birth).sum();
        students.stream().map(s -> s.name).collect(Collectors.joining(" -|- "));

        var studentsBornAfter2005 = students.stream().filter(s -> s.birth > 2005).collect(
                LinkedList::new,
                LinkedList::add,
                LinkedList::addAll
        );


        var names = students.parallelStream()
                .map(s -> s.name)
                .toList();
        System.out.println(names);


        var randomStrings = IntStream.range(1, 1_000_000)
                .boxed()
                .map(i -> UUID.randomUUID().toString()).toList();


        long streamStartTime = System.nanoTime();
        randomStrings.stream().sorted().toList();
        long streamEndTime = System.nanoTime();

        long ms = TimeUnit.NANOSECONDS.toMillis(streamEndTime - streamStartTime);
        System.out.printf("Sequential sort: %d ms\n", ms);

        System.out.println("=================================");

        long parallelStreamStartTime = System.nanoTime();
        randomStrings.parallelStream().sorted().toList();
        long parallelStreamEndTime = System.nanoTime();

        long pms = TimeUnit.NANOSECONDS.toMillis(parallelStreamEndTime - parallelStreamStartTime);
        System.out.println(String.format("Concurrent sort: %d ms", pms));

        var cores = ForkJoinPool.commonPool().getParallelism();
        System.out.println(cores);

        List.of("1", "2", "3", "4", "5", "6", "7").parallelStream()
                .map(s -> {
                    System.out.println("Map     " + s + " value in thread: " + Thread.currentThread().getName());
                    return s;
                })
                .forEach(s -> System.out.println("Foreach " + s + " value in thread: " + Thread.currentThread().getName()));
    }

}
