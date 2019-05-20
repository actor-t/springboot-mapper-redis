package com.open.boot.core.utils;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.Assert;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RunWith(PowerMockRunner.class)
public class CommonUtilTest {

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(CommonUtil.isEmpty(""));
        Assert.assertTrue(CommonUtil.isEmpty(null));

        Assert.assertFalse(CommonUtil.isEmpty("foo"));
    }

    @Test
    public void testGetStringSimple() {
        Assert.assertEquals("foob***obar",
                CommonUtil.getStringSimple("foobar"));
    }

    @Test
    public void testGetYestoday() {
        Date date =
                new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        String data = new SimpleDateFormat("yyyyMMdd").format(date);
        Assert.assertEquals(data, CommonUtil.getYestoday());
    }

    @Test
    public void testGetCurrentDate() {
        Date date = new Date();
        String data = new SimpleDateFormat("yyyyMMdd").format(date);
        Assert.assertEquals(data, CommonUtil.getCurrentDate());
    }

    @Test
    public void testGetCurrentTime() {
        Date date = new Date();
        String data = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        Assert.assertEquals(data, CommonUtil.getCurrentTime());
    }

    @Test
    public void testGetGenderByIdNumber() {
        Assert.assertEquals(new Integer(0),
                CommonUtil.getGenderByIdNumber("abc"));

        Assert.assertEquals(new Integer(1),
                CommonUtil.getGenderByIdNumber("123456789012345"));
        Assert.assertEquals(new Integer(1),
                CommonUtil.getGenderByIdNumber("123456789012345678"));

        Assert.assertEquals(new Integer(2),
                CommonUtil.getGenderByIdNumber("000000000000000"));
        Assert.assertEquals(new Integer(2),
                CommonUtil.getGenderByIdNumber("000000000000000000"));
    }

    @Test
    public void testGetBirthdayByIdNumber() {
        Assert.assertEquals("", CommonUtil.getBirthdayByIdNumber("12345"));
        Assert.assertEquals("1978-90-12",
                CommonUtil.getBirthdayByIdNumber("123456789012345"));
        Assert.assertEquals("7890-12-34",
                CommonUtil.getBirthdayByIdNumber("123456789012345678"));
    }

    @Test
    public void testGenRandomNum() {
        Assert.assertEquals("", CommonUtil.genRandomNum(0));
        Assert.assertEquals(5, CommonUtil.genRandomNum(5).length());
        Assert.assertEquals(12, CommonUtil.genRandomNum(12).length());
    }

    @Test
    public void testGetMapValue() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("foo", "123");
        Assert.assertEquals("123", CommonUtil.getMapValue(hashMap, "foo"));
        Assert.assertEquals("", CommonUtil.getMapValue(hashMap, "baz"));
    }

    @Test
    public void testGetSpecifiedDayBefore() {
        Assert.assertEquals("2019-05-14",
                CommonUtil.getSpecifiedDayBefore("2019-05-15"));
    }

    @Test
    public void testGetSpecifiedDayAfter() {
        Assert.assertEquals("2019-05-16",
                CommonUtil.getSpecifiedDayAfter("2019-05-15"));
    }

    @PrepareForTest({CommonUtil.class, Calendar.class})
    @Test
    public void testGetTimesmorning() {
        Calendar day = Calendar.getInstance();
        day.set(2011, Calendar.MARCH, 27, 13, 24, 56);
        PowerMockito.mockStatic(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(day);

        Assert.assertEquals("11-03-27 00:00:00", CommonUtil.getTimesmorning());
    }

    @PrepareForTest({CommonUtil.class, Calendar.class})
    @Test
    public void testGetTimesnight() {
        Calendar day = Calendar.getInstance();
        day.set(2011, Calendar.MARCH, 27, 13, 24, 56);
        PowerMockito.mockStatic(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(day);

        Assert.assertEquals("11-03-28 00:00:00", CommonUtil.getTimesnight());
    }
}
