package com.ruoyi.common.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * JSON解析处理
 *
 * @author ruoyi
 */
public class JSON {

    private JSON(){
        throw new IllegalStateException("Utility class");
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    public static String marshal(Object value) throws IOException {
        try {
            return OBJECT_WRITER.writeValueAsString(value);
        }  catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static byte[] marshalBytes(Object value) throws IOException {
        try {
            return OBJECT_WRITER.writeValueAsBytes(value);
        }  catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static <T> T unmarshal(File file, Class<T> valueType) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(file, valueType);
        } catch (JsonParseException | JsonMappingException e) {
            throw new IOException(e);
        }
    }

    public static <T> T unmarshal(InputStream is, Class<T> valueType) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(is, valueType);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static <T> T unmarshal(String str, Class<T> valueType) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(str, valueType);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws IOException {
        try {
            if (bytes == null) {
                bytes = new byte[0];
            }
            return OBJECT_MAPPER.readValue(bytes, 0, bytes.length, valueType);
        }  catch (IOException e) {
            throw new IOException(e);
        }
    }
}
