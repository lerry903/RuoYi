package com.ruoyi.common.support;

import org.junit.Test;
import org.junit.Assert;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.TreeSet;

public class ConvertTest {

    @Test
    public void testToStr() {
        Assert.assertEquals("bar", Convert.toStr(null, "bar"));
        Assert.assertEquals("123", Convert.toStr(123, "bar"));
        Assert.assertEquals("foo", Convert.toStr("foo"));
    }

    @Test
    public void testToChar() {
        Assert.assertEquals('b', (char) Convert.toChar(null, 'b'));
        Assert.assertEquals('1', (char) Convert.toChar(1, 'b'));
        Assert.assertEquals('f', (char) Convert.toChar('f'));
    }

    @Test
    public void testToByte() {
        Assert.assertEquals(new Byte("8"), Convert.toByte(null, (byte) 8));
        Assert.assertEquals(new Byte("1"), Convert.toByte((byte) 1, (byte) 8));
        Assert.assertEquals(new Byte("8"), Convert.toByte("", (byte) 8));
        Assert.assertEquals(new Byte("123"), Convert.toByte("123", (byte) 8));
        Assert.assertEquals(new Byte("8"), Convert.toByte("foo", (byte) 8));
        Assert.assertEquals(new Byte("1"), Convert.toByte(1));
    }

    @Test
    public void testToShort() {
        Assert.assertEquals(new Short("8"), Convert.toShort(null, (short) 8));
        Assert.assertEquals(new Short("1"), Convert.toShort((short) 1, (short) 8));
        Assert.assertEquals(new Short("8"), Convert.toShort("", (short) 8));
        Assert.assertEquals(new Short("123"), Convert.toShort("123", (short) 8));
        Assert.assertEquals(new Short("8"), Convert.toShort("foo", (short) 8));
        Assert.assertEquals(new Short("1"), Convert.toShort(1));
    }

    @Test
    public void testToNumber() {
        Assert.assertEquals(8, Convert.toNumber(null, 8));
        Assert.assertEquals(8, Convert.toNumber("", 8));
        Assert.assertEquals(123L, Convert.toNumber("123", 8));
        Assert.assertEquals(8, Convert.toNumber("foo", 8));
        Assert.assertEquals(1, Convert.toNumber(1));
    }

    @Test
    public void testToInt() {
        Assert.assertEquals(new Integer("8"), Convert.toInt(null, 8));
        Assert.assertEquals(new Integer("1"), Convert.toInt(1L));
        Assert.assertEquals(new Integer("8"), Convert.toInt("", 8));
        Assert.assertEquals(new Integer("123"), Convert.toInt("123", 8));
        Assert.assertEquals(new Integer("8"), Convert.toInt("foo", 8));
        Assert.assertEquals(new Integer("1"), Convert.toInt(1));
    }

    @Test
    public void testToIntArray() {
        Assert.assertArrayEquals(new Integer[]{}, Convert.toIntArray(null, ""));
        Assert.assertArrayEquals(new Integer[]{0, 0, 0, 0}, Convert.toIntArray("a,b,c,d"));
    }

    @Test
    public void testToLongArray() {
        Assert.assertArrayEquals(new String[]{}, Convert.toLongArray(null, ""));
        Assert.assertArrayEquals(
                new String[]{null, null, null, null}, Convert.toLongArray("a,b,c,d"));
    }

    @Test
    public void testToStrArray() {
        Assert.assertArrayEquals(
                new String[]{"a", "b", "c", "d"}, Convert.toStrArray("a,b,c,d"));
    }

    @Test
    public void testToLong() {
        Assert.assertEquals(new Long("8"), Convert.toLong(null, 8L));
        Assert.assertEquals(new Long("1"), Convert.toLong(1L, 8L));
        Assert.assertEquals(new Long("8"), Convert.toLong("", 8L));
        Assert.assertEquals(new Long("123"), Convert.toLong("123", 8L));
        Assert.assertEquals(new Long("8"), Convert.toLong("foo", 8L));
        Assert.assertEquals(new Long("1"), Convert.toLong(1));
    }

    @Test
    public void testToDouble() {
        Assert.assertEquals(8, Convert.toDouble(null, 8D), 0);
        Assert.assertEquals(1, Convert.toDouble(1D, 8D), 0);
        Assert.assertEquals(8, Convert.toDouble("", 8D), 0);
        Assert.assertEquals(123, Convert.toDouble("123", 8D), 0);
        Assert.assertEquals(8, Convert.toDouble("foo", 8D), 0);
        Assert.assertEquals(1, Convert.toDouble(1), 0);
    }

    @Test
    public void testToFloat() {
        Assert.assertEquals(8, Convert.toFloat(null, 8F), 0);
        Assert.assertEquals(1, Convert.toFloat(1F, 8F), 0);
        Assert.assertEquals(8, Convert.toFloat("", 8F), 0);
        Assert.assertEquals(123, Convert.toFloat("123", 8F), 0);
        Assert.assertEquals(8, Convert.toFloat("foo", 8F), 0);
        Assert.assertEquals(1, Convert.toFloat(1), 0);
    }

    @Test
    public void testToBool() {
        Assert.assertFalse(Convert.toBool(null, false));
        Assert.assertFalse(Convert.toBool("", false));
        Assert.assertFalse(Convert.toBool("false", false));
        Assert.assertFalse(Convert.toBool("no", false));
        Assert.assertFalse(Convert.toBool("0", false));
        Assert.assertFalse(Convert.toBool("foo", false));

        Assert.assertTrue(Convert.toBool("true", false));
        Assert.assertTrue(Convert.toBool("yes", false));
        Assert.assertTrue(Convert.toBool("ok", false));
        Assert.assertTrue(Convert.toBool("1", false));
        Assert.assertTrue(Convert.toBool(true));
    }

    @Test
    public void testToEnum() {
        Assert.assertNull(Convert.toEnum(null, null));
    }

    @Test
    public void testToBigInteger() {
        Assert.assertEquals(BigInteger.TEN, Convert.toBigInteger(null, BigInteger.TEN));
        Assert.assertEquals(BigInteger.ONE, Convert.toBigInteger(1L, BigInteger.TEN));
        Assert.assertEquals(BigInteger.TEN, Convert.toBigInteger("", BigInteger.TEN));
        Assert.assertEquals(new BigInteger("123"), Convert.toBigInteger("123", BigInteger.TEN));
        Assert.assertEquals(BigInteger.TEN, Convert.toBigInteger("foo", BigInteger.TEN));
        Assert.assertEquals(BigInteger.TEN, Convert.toBigInteger(BigInteger.TEN));
    }

    @Test
    public void testToBigDecimal() {
        Assert.assertEquals(BigDecimal.TEN, Convert.toBigDecimal(null, BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.ONE, Convert.toBigDecimal(1L, BigDecimal.TEN));
        Assert.assertEquals(new BigDecimal("1.0"), Convert.toBigDecimal(1D, BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.ONE, Convert.toBigDecimal(1, BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.TEN, Convert.toBigDecimal("", BigDecimal.TEN));
        Assert.assertEquals(new BigDecimal("123"), Convert.toBigDecimal("123", BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.TEN, Convert.toBigDecimal("foo", BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.TEN, Convert.toBigDecimal(BigDecimal.TEN));
    }

    @Test
    public void testStr() {
        Assert.assertNull(Convert.str((Object) null, Charset.forName("UTF-8")));
        Assert.assertEquals("foo", Convert.utf8Str("foo"));
        Assert.assertEquals("foo", Convert.str("foo", "UTF-8"));

        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);

        Assert.assertEquals("", Convert.str((Object) byteBuffer, "UTF-8"));
        Assert.assertEquals("123", Convert.str((Object) 123, "UTF-8"));

        Assert.assertNull(Convert.str((ByteBuffer) null, "UTF-8"));
        Assert.assertEquals("", Convert.str(byteBuffer, (Charset) null));
        Assert.assertEquals("", Convert.str(byteBuffer, "UTF-8"));

        Assert.assertNull(Convert.str((byte[]) null, "UTF-8"));
        Assert.assertEquals("", Convert.str(new byte[]{}, (Charset) null));
        Assert.assertEquals("", Convert.str(new byte[]{}, Charset.forName("UTF-8")));
    }

    @Test
    public void testToSBC() {
        Set<Character> notConvertSet = new TreeSet<>();
        notConvertSet.add('a');
        notConvertSet.add('o');

        Assert.assertEquals("ｆo　o", Convert.toSBC("fo o", notConvertSet));
        Assert.assertEquals("ｆｏ　ｏ", Convert.toSBC("fo o"));
    }

    @Test
    public void testToDBC() {
        Set<Character> notConvertSet = new TreeSet<>();
        notConvertSet.add('a');
        notConvertSet.add('o');

        Assert.assertEquals("f oPo", Convert.toDBC("f\u3000o\uFF30o", notConvertSet));
        Assert.assertEquals("fo o", Convert.toDBC("fo o"));
    }
}
