package functional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltinFnInterfaces {

    public static void main(String[] args) {

        Supplier<String> strSupplier = () -> "Hello world";
        String str = strSupplier.get();

        Predicate<Integer> lt10 = value -> value < 10;
        Predicate<Integer> lt20 = value -> value < 20;

        printWhenLT(5, lt10);
        printWhenLT(15, lt10);
        printWhenLT(25, lt10);
        printWhenLT(10, lt20);
        printWhenLT(20, lt20);


        Consumer<String> consumer = in -> System.out.println(in);
        consumer.accept("Hello World");

        Predicate<Integer> gt5 = value -> value > 5;
        Predicate<Integer> combined = lt10.and(gt5);

        System.out.println(combined.test(5));
        System.out.println(combined.test(10));
        System.out.println(combined.test(7));


        Function<String, Integer> convertStringToInt = Integer::valueOf;
        Function<Integer, Integer> doubleInt = it -> it * 2;
        Function<Integer, String> convertIntToString = Object::toString;

        var composed = convertIntToString.compose(doubleInt).compose(convertStringToInt);
        System.out.println(composed.apply("10"));
    }

    static void printWhenLT(int value, Predicate<Integer> testValue) {
        if (testValue.test(value)) {
            System.out.println(value);
        }
    }


}
