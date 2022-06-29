package functional;


@FunctionalInterface
interface Converter<T, U> {
    U convert(T from);
}

public class FunctionalInterfaceExample {

    public static void main(String[] args) {

        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return Integer.valueOf(from);
            }
        };
        System.out.println(converter.convert("10"));

        Converter<String, Integer> fnConverter = (String from) -> Integer.valueOf(from);
        System.out.println(fnConverter.convert("10"));

        Converter<String, Integer> rfConverter = Integer::valueOf;
        System.out.println(rfConverter.convert("10"));

        effectivelyFinal();
    }

    static void effectivelyFinal() {
        int count = 10;
        Converter<Integer, String> converter = from -> String.valueOf(from * count);
        System.out.println(converter.convert(10));
        //count++;
    }


}
