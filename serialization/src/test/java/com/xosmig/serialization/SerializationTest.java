package com.xosmig.serialization;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class SerializationTest {
    @Test
    public void serializeBooleanTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        boolean[] arr = new boolean[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = random.nextBoolean();
            Serialization.serializeBoolean(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeBoolean(is));
        }
    }


    @Test
    public void serializeByteTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        byte[] arr = new byte[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = (byte) ((random.nextBoolean() ? 1 : -1) * random.nextInt(Byte.MAX_VALUE));
            Serialization.serializeByte(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeByte(is));
        }
    }


    @Test
    public void serializeCharTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        char[] arr = new char[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = (char) random.nextInt(Character.MAX_VALUE + 1);
            Serialization.serializeChar(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeChar(is));
        }
    }


    @Test
    public void serializeShortTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        short[] arr = new short[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = (short) ((random.nextBoolean() ? 1 : -1) * random.nextInt(Short.MAX_VALUE));
            Serialization.serializeShort(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeShort(is));
        }
    }


    @Test
    public void serializeIntTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        int[] arr = new int[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = random.nextInt();
            Serialization.serializeInt(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeInt(is));
        }
    }


    @Test
    public void serializeLongTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        long[] arr = new long[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = random.nextLong();
            Serialization.serializeLong(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            assertEquals(arr[i], Serialization.deserializeLong(is));
        }
    }


    @Test
    public void serializeFloatTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        float[] arr = new float[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = random.nextFloat();
            Serialization.serializeFloat(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            // I need absolute equality
            assertTrue(arr[i] == Serialization.deserializeFloat(is));
        }
    }


    @Test
    public void serializeDoubleTest() throws Exception {
        final int n = 10;
        Random random = new Random();
        double[] arr = new double[n];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for(int i = 0; i < n; i++) {
            arr[i] = random.nextDouble();
            Serialization.serializeDouble(arr[i], os);
        }

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        for(int i = 0; i < n; i++) {
            // I need absolute equality
            assertTrue(arr[i] == Serialization.deserializeDouble(is));
        }
    }



    @Test
    public void serializeComplexTest() throws Exception {
        Random random = new Random();
        TestClassB b1 = new TestClassB(null, random.nextInt());
        TestClassB b2 = new TestClassB(b1, random.nextInt());
        TestClassB b3 = new TestClassB(b2, random.nextInt());

        TestClassA a1 = new TestClassA(
                random.nextInt(),
                random.nextDouble(),
                b3,
                "asdjghkdjsfhgj"
        );

        TestClassA a2 = new TestClassA(
                random.nextInt(),
                random.nextDouble(),
                null,
                null
        );

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Serialization.serializeObject(a1, os);
        Serialization.serializeObject(null, os);
        Serialization.serializeObject(a2, os);

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        TestClassA ad1 = Serialization.deserializeObject(is, TestClassA.class);
        TestAbstractClassWithoutEmptyConstructor null1 = Serialization.deserializeObject(is,
                TestAbstractClassWithoutEmptyConstructor.class);  // null, constructor mustn't be called
        TestClassA ad2 = Serialization.deserializeObject(is, TestClassA.class);

        assertEquals(a1, ad1);
        assertEquals(a2, ad2);
        assertTrue(null1 == null);
    }
}