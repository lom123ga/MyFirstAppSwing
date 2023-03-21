/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Stream;
import java.nio.ByteBuffer;
/**
 *
 * @author ssopt
 */
public class ByteUtils {
    public static final byte[] EMPTY_ARRAY = new byte[0];
    
    public static final byte[] EMPTY_BYTES = new byte[32];
    
    public static final String ZERO_HASH = Hex.encodeHexString(EMPTY_BYTES);
    
    public static byte[] merge(byte[]... bytes){
        Stream<Byte> m_stream = Stream.of();
        for (byte[] it : bytes){
           m_stream = Stream.concat(m_stream, Arrays.stream(ArrayUtils.toObject(it)));
        }
        return ArrayUtils.toPrimitive(m_stream.toArray(Byte[]::new));
    }
    
    public static byte[] toBytes(long val){
        return ByteBuffer.allocate(Long.BYTES).putLong(val).array();
    }
}
