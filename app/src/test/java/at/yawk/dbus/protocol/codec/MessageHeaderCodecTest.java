package at.yawk.dbus.protocol.codec;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.yawk.dbus.protocol.HeaderField;
import at.yawk.dbus.protocol.MessageBody;
import at.yawk.dbus.protocol.MessageHeader;
import at.yawk.dbus.protocol.MessageType;
import at.yawk.dbus.protocol.object.AlignableByteBuf;
import at.yawk.dbus.protocol.object.BasicObject;
import at.yawk.dbus.protocol.object.DbusObject;
import at.yawk.dbus.protocol.object.ObjectPathObject;
import at.yawk.dbus.protocol.object.SignatureObject;
import at.yawk.dbus.protocol.type.BasicType;
import at.yawk.dbus.protocol.type.TypeDefinition;
import cx.ath.matthew.utils.Hexdump;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;

/**
 * Created by ASUS on 2020/7/25.
 */
public class MessageHeaderCodecTest {
    @Test
    public void testMessageDecoer() throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(new byte[]{
                0x6C, 0x01, 0x00, 0x01, (byte) 0xE4, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x78, 0x00, 0x00, 0x00,
                0x01, 0x01, 0x6F, 0x00, 0x19, 0x00, 0x00, 0x00, 0x2F, 0x72, 0x65, 0x2F, 0x66, 0x72, 0x69, 0x64,
                0x61, 0x2F, 0x41, 0x67, 0x65, 0x6E, 0x74, 0x53, 0x65, 0x73, 0x73, 0x69, 0x6F, 0x6E, 0x2F, 0x32,
                0x35, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x01, 0x73, 0x00, 0x17, 0x00, 0x00, 0x00,
                0x72, 0x65, 0x2E, 0x66, 0x72, 0x69, 0x64, 0x61, 0x2E, 0x41, 0x67, 0x65, 0x6E, 0x74, 0x53, 0x65,
                0x73, 0x73, 0x69, 0x6F, 0x6E, 0x31, 0x32, 0x00, 0x08, 0x01, 0x67, 0x00, 0x05, 0x73, 0x28, 0x61,
                0x79, 0x29, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x01, 0x73, 0x00, 0x17, 0x00, 0x00, 0x00,
                0x43, 0x72, 0x65, 0x61, 0x74, 0x65, 0x53, 0x63, 0x72, 0x69, 0x70, 0x74, 0x57, 0x69, 0x74, 0x68,
                0x4F, 0x70, 0x74, 0x69, 0x6F, 0x6E, 0x73, 0x00, (byte) 0xDA, 0x00, 0x00, 0x00, 0x2F, 0x2F, 0x20, 0x63,
                0x6F, 0x6D, 0x2E, 0x79, 0x78, 0x69, 0x6D, 0x2E, 0x61, 0x6E, 0x74, 0x2E, 0x41, 0x70, 0x70, 0x6C,
                0x69, 0x63, 0x61, 0x74, 0x69, 0x6F, 0x6E, 0x43, 0x6F, 0x6E, 0x74, 0x65, 0x78, 0x74, 0x0A, 0x4A,
                0x61, 0x76, 0x61, 0x2E, 0x70, 0x65, 0x72, 0x66, 0x6F, 0x72, 0x6D, 0x28, 0x66, 0x75, 0x6E, 0x63,
                0x74, 0x69, 0x6F, 0x6E, 0x20, 0x28, 0x29, 0x20, 0x7B, 0x0A, 0x20, 0x20, 0x20, 0x20, 0x63, 0x6F,
                0x6E, 0x73, 0x74, 0x20, 0x41, 0x70, 0x70, 0x54, 0x61, 0x73, 0x6B, 0x20, 0x3D, 0x20, 0x4A, 0x61,
                0x76, 0x61, 0x2E, 0x75, 0x73, 0x65, 0x28, 0x22, 0x63, 0x6F, 0x6D, 0x2E, 0x6D, 0x68, 0x6F, 0x6F,
                0x6B, 0x2E, 0x6C, 0x6F, 0x63, 0x61, 0x6C, 0x66, 0x72, 0x69, 0x64, 0x61, 0x2E, 0x74, 0x61, 0x73,
                0x6B, 0x2E, 0x41, 0x70, 0x70, 0x54, 0x61, 0x73, 0x6B, 0x22, 0x29, 0x3B, 0x0A, 0x20, 0x20, 0x20,
                0x20, 0x41, 0x70, 0x70, 0x54, 0x61, 0x73, 0x6B, 0x2E, 0x74, 0x65, 0x73, 0x74, 0x48, 0x6F, 0x6F,
                0x6B, 0x47, 0x65, 0x74, 0x49, 0x6E, 0x74, 0x2E, 0x69, 0x6D, 0x70, 0x6C, 0x65, 0x6D, 0x65, 0x6E,
                0x74, 0x61, 0x74, 0x69, 0x6F, 0x6E, 0x20, 0x3D, 0x20, 0x66, 0x75, 0x6E, 0x63, 0x74, 0x69, 0x6F,
                0x6E, 0x20, 0x28, 0x29, 0x20, 0x7B, 0x0A, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x72,
                0x65, 0x74, 0x75, 0x72, 0x6E, 0x20, 0x35, 0x34, 0x33, 0x32, 0x31, 0x3B, 0x0A, 0x20, 0x20, 0x20,
                0x20, 0x7D, 0x0A, 0x7D, 0x29, 0x3B, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        });

        MessageHeaderCodec decoder = new MessageHeaderCodec();
        List<Object> out = new ArrayList<>();
        decoder.decode(null, buffer, out);
        Assert.assertEquals(out.size(),1);

        MessageHeader messageHeader= (MessageHeader) out.get(0);

        ByteBuf byteBuf=buffer.slice().order(messageHeader.getByteOrder());
        AlignableByteBuf decoding = AlignableByteBuf.decoding(byteBuf);

        DbusObject signature = messageHeader.getHeaderFields().get(HeaderField.SIGNATURE);
        if (signature == null) { throw new DecoderException("Non-empty body but missing signature header"); }

        List<TypeDefinition> types = signature.typeValue();
        List<DbusObject> bodyObjects = new ArrayList<>();

        for (TypeDefinition type : types) {
            DbusObject object = type.deserialize(decoding);
            bodyObjects.add(object);
        }

        MessageBody body = new MessageBody();
        body.setArguments(bodyObjects);
        out.add(body);

        Assert.assertEquals(out.size(),2);
    }
    @Test
    public void testMessageDecoer1() throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(new byte[]{
                0x6C, 0x01, 0x00, 0x01, 0x04, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x6B, 0x00, 0x00, 0x00,
                0x01, 0x01, 0x6F, 0x00, 0x19, 0x00, 0x00, 0x00, 0x2F, 0x72, 0x65, 0x2F, 0x66, 0x72, 0x69, 0x64,
                0x61, 0x2F, 0x41, 0x67, 0x65, 0x6E, 0x74, 0x53, 0x65, 0x73, 0x73, 0x69, 0x6F, 0x6E, 0x2F, 0x32,
                0x35, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x01, 0x73, 0x00, 0x17, 0x00, 0x00, 0x00,
                0x72, 0x65, 0x2E, 0x66, 0x72, 0x69, 0x64, 0x61, 0x2E, 0x41, 0x67, 0x65, 0x6E, 0x74, 0x53, 0x65,
                0x73, 0x73, 0x69, 0x6F, 0x6E, 0x31, 0x32, 0x00, 0x08, 0x01, 0x67, 0x00, 0x03, 0x28, 0x75, 0x29,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x01, 0x73, 0x00, 0x0A, 0x00, 0x00, 0x00,
                0x4C, 0x6F, 0x61, 0x64, 0x53, 0x63, 0x72, 0x69, 0x70, 0x74, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x01, 0x00, 0x00, 0x00,
        });

        MessageHeaderCodec decoder = new MessageHeaderCodec();
        List<Object> out = new ArrayList<>();
        decoder.decode(null, buffer, out);
        Assert.assertEquals(out.size(),1);

        MessageHeader messageHeader= (MessageHeader) out.get(0);

        ByteBuf byteBuf=buffer.slice().order(messageHeader.getByteOrder());
        AlignableByteBuf decoding = AlignableByteBuf.decoding(byteBuf);

        DbusObject signature = messageHeader.getHeaderFields().get(HeaderField.SIGNATURE);
        if (signature == null) { throw new DecoderException("Non-empty body but missing signature header"); }

        List<TypeDefinition> types = signature.typeValue();
        List<DbusObject> bodyObjects = new ArrayList<>();

        for (TypeDefinition type : types) {
            DbusObject object = type.deserialize(decoding);
            bodyObjects.add(object);
        }

        MessageBody body = new MessageBody();
        body.setArguments(bodyObjects);
        out.add(body);

        Assert.assertEquals(out.size(),2);
    }

    @Test
    public void testMessageHeaderCoder() throws Exception {
        MessageHeader inHeader = new MessageHeader();
        // defaults
        inHeader.setByteOrder(ByteOrder.BIG_ENDIAN);
        inHeader.setMajorProtocolVersion((byte) 1);
        inHeader.setSerial(1);

        inHeader.setMessageType(MessageType.METHOD_CALL);
        inHeader.setMessageBodyLength(5);
        inHeader.addHeader(HeaderField.PATH, ObjectPathObject.create("/org/freedesktop/UPower/devices/DisplayDevice"));
        inHeader.addHeader(HeaderField.DESTINATION, BasicObject.createString("org.freedesktop.UPower"));
        inHeader.addHeader(HeaderField.MEMBER, BasicObject.createString("org.freedesktop.DBus.Properties.Get"));
        inHeader.addHeader(HeaderField.SIGNATURE, SignatureObject.create(Arrays.asList(BasicType.UINT16,
                BasicType.UINT32)));

        MessageHeaderCodec codec = new MessageHeaderCodec();
        ByteBuf buffer = Unpooled.buffer();
        codec.encode(null, inHeader, buffer);
        System.out.println("buffer:\n" + Hexdump.format(buffer.array()));
        List<Object> out = new ArrayList<>();
        codec.decode(null, buffer, out);

        Assert.assertEquals(out.size(), 1);
        Assert.assertEquals(out.get(0), inHeader);
    }
}
