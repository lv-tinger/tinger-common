package org.tinger.common.serialize.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.tinger.common.serialize.Serializer;
import org.tinger.common.utils.IoUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Hessian2Serializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        Hessian2Output output = null;
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            output = new Hessian2Output(stream);
            output.writeObject(object);
            output.flush();
            return stream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                // ignore
            }
            IoUtils.close(stream);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Hessian2Input input = null;
        ByteArrayInputStream stream = null;

        try {
            stream = new ByteArrayInputStream(bytes);
            input = new Hessian2Input(stream);
            return input.readObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
            } catch (Exception e) {
                // ignore
            }
            IoUtils.close(stream);
        }
    }
}
