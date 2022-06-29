package functional;

import java.util.function.Consumer;
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
    }

    static void printWhenLT(int value, Predicate<Integer> testValue) {
        if (testValue.test(value)) {
            System.out.println(value);
        }
    }


}
