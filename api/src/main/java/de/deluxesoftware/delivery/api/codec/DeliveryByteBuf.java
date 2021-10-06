package de.deluxesoftware.delivery.api.codec;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryByteBuf {

    public final ByteBuf buf;

    public DeliveryByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public void writeString(String param) {
        if (param == null || param.isEmpty())
            throw new NullPointerException();
        this.buf.writeInt(param.length());
        this.buf.writeBytes(param.getBytes(StandardCharsets.UTF_8));
    }

    public String readString() {
        int length = this.buf.readInt();
        byte[] bytes = new byte[length];
        this.buf.readBytes(bytes, 0, length);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeList(List<String> param) {
        this.writeList(param, "'");
    }

    public void writeList(List<String> param, String delimeter) {
        String compressed = param.
                stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimeter));
        this.writeString(compressed);
    }

    public List<String> readList() {
        return this.readList("'");
    }

    public List<String> readList(String delimeter) {
        List<String> uncompressed = new ArrayList<>();
        Collections.addAll(uncompressed, this.readString().split("'"));
        return uncompressed;
    }

    public ByteBuf getBuf() {
        return this.buf;
    }

}
