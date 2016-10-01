package com.xosmig.serializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MySuperSerializable {
    void serialize(OutputStream out) throws IOException;
    void deserialize(InputStream in) throws IOException;
}
