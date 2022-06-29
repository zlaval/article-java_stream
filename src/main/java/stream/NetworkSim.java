package stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;

public class NetworkSim {

    private static final int CHUNK_SIZE_IN_BYTE = 5;

    class Message {
        byte[] data;
        int order;

        public Message(byte[] data, int order) {
            this.data = data;
            this.order = order;
        }
    }

    public static void main(String[] args) {
        var sim = new NetworkSim();
        sim.execute();
    }

    void execute() {
        var messages = generateMessages();
        var collector = getCollector();
        var comparator = getComparator(); //Comparator.comparingInt

        var result = messages.stream()
                .sorted(comparator)
                .map(m -> m.data)
                .collect(collector);

        System.out.println(new String(result, StandardCharsets.UTF_8));
    }

    private Comparator<Message> getComparator() {
        return (o1, o2) -> Integer.compare(o1.order, o2.order);
    }

    private List<Message> generateMessages() {
        byte[] inputData = "Welcome to the Java Stream article.".getBytes(StandardCharsets.UTF_8);
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < inputData.length; i += CHUNK_SIZE_IN_BYTE) {
            byte[] slice = Arrays.copyOfRange(inputData, i, Math.min(inputData.length, i + CHUNK_SIZE_IN_BYTE));
            messages.add(new Message(slice, messages.size()));
        }
        Collections.shuffle(messages);
        return messages;
    }

    private Collector<byte[], ByteArrayOutputStream, byte[]> getCollector() {
        return Collector.of(
                ByteArrayOutputStream::new,
                (accumulator, element) -> {
                    try {
                        accumulator.write(element);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                },
                (buffer1, buffer2) -> {
                    try {
                        buffer2.writeTo(buffer1);
                        return buffer1;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                },
                ByteArrayOutputStream::toByteArray
        );
    }

}
