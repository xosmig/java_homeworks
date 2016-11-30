package com.xosmig.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;


public final class Serialization {
    private Serialization() {}

    public static void serializeObject(Object source, OutputStream os) throws IOException, IllegalAccessException {
        if (source != null) {
            serializeBoolean(true, os);
        } else {
            serializeBoolean(false, os);
            return;
        }

        Class<?> cl = source.getClass();
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            Class<?> type = field.getType();
            field.setAccessible(true);

            // skip static fields
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            if (type.equals(boolean.class)) {
                serializeBoolean(field.getBoolean(source), os);
            } else if (type.equals(byte.class)) {
                serializeByte(field.getByte(source), os);
            } else if (type.equals(char.class)) {
                serializeChar(field.getChar(source), os);
            } else if (type.equals(short.class)) {
                serializeShort(field.getShort(source), os);
            } else if (type.equals(int.class)) {
                serializeInt(field.getInt(source), os);
            } else if (type.equals(long.class)) {
                serializeLong(field.getLong(source), os);
            } else if (type.equals(float.class)) {
                serializeFloat(field.getFloat(source), os);
            } else if (type.equals(double.class)) {
                serializeDouble(field.getDouble(source), os);
            } else if (type.equals(String.class)) {
                serializeString((String) field.get(source), os);
            } else {
                serializeObject(field.get(source), os);
            }
        }
    }


    public static void serializeBoolean(boolean source, OutputStream os) throws IOException {
        os.write(source ? 1 : 0);
    }

    public static void serializeByte(byte source, OutputStream os) throws IOException {
        os.write(source);
    }

    public static void serializeChar(char source, OutputStream os) throws IOException {
        os.write(ByteBuffer.allocate(Character.SIZE / Byte.SIZE).putChar(source).array());
    }

    public static void serializeShort(short source, OutputStream os) throws IOException {
        os.write(ByteBuffer.allocate(Short.SIZE / Byte.SIZE).putShort(source).array());
    }

    public static void serializeInt(int source, OutputStream os) throws IOException {
        os.write(ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(source).array());
    }

    public static void serializeLong(long source, OutputStream os) throws IOException {
        os.write(ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(source).array());
    }

    public static void serializeFloat(float source, OutputStream os) throws IOException {
        serializeInt(Float.floatToRawIntBits(source), os);
    }

    public static void serializeDouble(double source, OutputStream os) throws IOException {
        serializeLong(Double.doubleToRawLongBits(source), os);
    }

    public static void serializeString(String source, OutputStream os) throws IOException {
        if (source != null) {
            serializeBoolean(true, os);
            serializeInt(source.length(), os);
            os.write(source.getBytes());
        } else {
            serializeBoolean(false, os);
        }
    }


    public static <T> T deserializeObject(InputStream is, Class<T> cl) throws
            IOException, IllegalAccessException, InstantiationException {
        boolean isPresent = deserializeBoolean(is);
        if (!isPresent) {
            return null;
        }

        Field[] fields = cl.getDeclaredFields();

        T result = cl.newInstance();

        for (Field field : fields) {
            Class<?> type = field.getType();
            field.setAccessible(true);

            // skip static fields
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            if (type.equals(boolean.class)) {
                field.setBoolean(result, deserializeBoolean(is));
            } else if (type.equals(byte.class)) {
                field.setByte(result, deserializeByte(is));
            } else if (type.equals(char.class)) {
                field.setChar(result, deserializeChar(is));
            } else if (type.equals(short.class)) {
                field.setShort(result, deserializeShort(is));
            } else if (type.equals(int.class)) {
                field.setInt(result, deserializeInt(is));
            } else if (type.equals(long.class)) {
                field.setLong(result, deserializeLong(is));
            } else if (type.equals(float.class)) {
                field.setFloat(result, deserializeFloat(is));
            } else if (type.equals(double.class)) {
                field.setDouble(result, deserializeDouble(is));
            } else if (type.equals(String.class)) {
                field.set(result, deserializeString(is));
            } else {
                field.set(result, deserializeObject(is, type));
            }
        }

        return result;
    }


    public static boolean deserializeBoolean(InputStream is) throws IOException {
        return is.read() == 1;
    }

    public static byte deserializeByte(InputStream is) throws IOException {
        return (byte) is.read();
    }

    public static char deserializeChar(InputStream is) throws IOException {
        final int size = Character.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getChar();
    }

    public static short deserializeShort(InputStream is) throws IOException {
        final int size = Short.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getShort();
    }

    public static float deserializeFloat(InputStream is) throws IOException {
        final int size = Float.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static double deserializeDouble(InputStream is) throws IOException {
        final int size = Double.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static int deserializeInt(InputStream is) throws IOException {
        final int size = Integer.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static long deserializeLong(InputStream is) throws IOException {
        final int size = Long.SIZE / Byte.SIZE;
        byte[] bytes = new byte[size];
        if (is.read(bytes) != size) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static String deserializeString(InputStream is) throws IOException {
        boolean isPresent = deserializeBoolean(is);
        if (isPresent) {
            int size = deserializeInt(is);
            byte[] bytes = new byte[size];
            if (is.read(bytes) != size) {
                throw new IOException();
            }
            return new String(bytes);
        } else {
            return null;
        }
    }
}
