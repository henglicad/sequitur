package de.unisb.cs.st.sequitur.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import de.unisb.cs.st.sequitur.output.ObjectWriter;
import de.unisb.cs.st.sequitur.output.OutputSequence;


public class SequiturOutputStream extends FilterOutputStream {

    private static final class ByteWriter implements ObjectWriter<Byte> {

        public static final ByteWriter instance = new ByteWriter();

        public void writeObject(Byte object, ObjectOutputStream outputStream)
                throws IOException {
            outputStream.writeByte(object.intValue());
        }

    }


    private final OutputSequence<Byte> outSeq;

    public SequiturOutputStream(OutputStream out) {
        super(out);
        this.outSeq = new OutputSequence<Byte>(ByteWriter.instance);
    }

    @Override
    public void write(int b) throws IOException {
        this.outSeq.append(Byte.valueOf((byte)b));
    }

    @Override
    public void close() throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(this.out);
        this.outSeq.writeOut(objOut, true);
        objOut.close();
        super.close();
    }

}
