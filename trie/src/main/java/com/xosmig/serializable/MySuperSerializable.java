package com.xosmig.serializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Implementing this interface allows an object to be serialized and deserialized (surprise, huh?)
 * in a binary format.
 */
public interface MySuperSerializable {
    /**
     * Serialize in binary format.
     */
    void serialize(OutputStream out) throws IOException;

    /**
     * Replaces an object by the deserialized one.
     */
    void deserialize(InputStream in) throws IOException;
}
