package com.autoai.android.toolkit.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataUtils {

    /**
     * Gzip 压缩数据
     *
     * @param data
     * @return
     */
    public static byte[] compressForGzip(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(data);
            gzip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return encode;
        } catch (IOException e) {
        }
        return null;
    }


    /**
     * Gzip解压数据
     *
     * @param data
     * @return
     */
    public static byte[] decompressForGzip(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }


    /**
     * 异或加密
     *
     * @param arr     需要加密的数据
     * @param keyWord 秘钥
     * @return 加密后的数据
     * @throws UnsupportedEncodingException
     */
    public static byte[] xoEncrypt(byte[] arr, String keyWord) throws UnsupportedEncodingException {
        byte[] keyarr = keyWord.getBytes("UTF-8");

        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = (byte) (arr[i] ^ keyarr[i % keyarr.length]);
        }
        return result;
    }

    /**
     * 异或加密解密
     *
     * @param text    需要解密数据
     * @param keyWord 秘钥
     * @return 解密后的数据
     * @throws UnsupportedEncodingException 异常
     */
    public static byte[] xorDecrypt(byte[] text, String keyWord) throws UnsupportedEncodingException {
        byte[] keyarr = keyWord.getBytes("UTF-8");
        for (int i = 0; i < text.length; i++) {
            text[i] = (byte) (text[i] ^ keyarr[i % keyarr.length]);
        }
        return text;
    }
}
