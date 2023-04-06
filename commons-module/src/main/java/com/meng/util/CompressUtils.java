package com.meng.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * compress utils
 *
 * @author : sunyuecheng
 */
public final class CompressUtils {


    /**
     * unzip file
     *
     * @param inputFilePath :
     * @param outputDir     :
     */
    public static void unzipFile(String inputFilePath, String outputDir) throws Exception {
        if (StringUtils.isEmpty(inputFilePath) || StringUtils.isEmpty(outputDir)) {
            throw new Exception("Invalid param.");
        }

        File srcFile = new File(inputFilePath);
        if (!srcFile.exists()) {
            throw new Exception("Input file does not exist.");
        }

        ZipFile zipFile = new ZipFile(srcFile);
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            if (entry.isDirectory()) {
                srcFile.mkdirs();
            } else {
                File targetFile = new File(outputDir + "/" + entry.getName());
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();

                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }

                fos.close();
                is.close();
            }
        }
    }

    /**
     * zip file
     *
     * @param inputFilePath  :
     * @param outputFilePath :
     * @return byte[] :
     */
    public static void zipFile(String inputFilePath, String outputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        File zipFile = new File(outputFilePath);
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        zipFile(zos, inputFile, "");
        zos.close();
        fos.close();
    }

    /**
     * zip file
     *
     * @param inputFilePath  :
     * @return byte[] :
     */
    public static byte[] zipFile(String inputFilePath) throws Exception {
        byte[] outputData = null;

        File inputFile = new File(inputFilePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zipFile(zos, inputFile, "");
        zos.close();
        outputData = baos.toByteArray();
        baos.close();

        return outputData;
    }

    private static void zipFile(ZipOutputStream zos, File inputFile, String parentDir) throws Exception {
        if (inputFile.isDirectory()) {
            parentDir = parentDir + inputFile.getName() + "/";
            ZipEntry zipEntry = new ZipEntry(parentDir);
            zos.putNextEntry(zipEntry);
            for (File file : inputFile.listFiles()) {
                zipFile(zos, file, parentDir);
            }
        } else {
            ZipEntry zipEntry = new ZipEntry(parentDir + inputFile.getName());
            zos.putNextEntry(zipEntry);
            byte[] buf = new byte[1024];

            FileInputStream fis = new FileInputStream(inputFile);
            BufferedInputStream bis = new BufferedInputStream(fis, buf.length);

            int len = -1;
            while ((len = bis.read(buf, 0, buf.length)) != -1) {
                zos.write(buf, 0, len);
            }
            bis.close();
            fis.close();
        }
    }

    /**
     * unzip data
     *
     * @param inputData :
     * @return byte[] :
     */
    public static byte[] unzipData(byte[] inputData) throws Exception {
        byte[] outputData = null;

        ByteArrayInputStream bais = new ByteArrayInputStream(inputData);
        ZipInputStream zis = new ZipInputStream(bais);
        while (zis.getNextEntry() != null) {
            byte[] buf = new byte[1024];
            int len = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = zis.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, len);
            }
            outputData = baos.toByteArray();
            baos.flush();
            baos.close();
        }
        zis.close();
        bais.close();

        return outputData;
    }

    /**
     * zip data
     *
     * @param inputData :
     * @return byte[] :
     */
    public static byte[] zipData(byte[] inputData) throws Exception {
        byte[] outputData = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry("zip");
        entry.setSize(inputData.length);
        zos.putNextEntry(entry);
        zos.write(inputData);
        zos.closeEntry();
        zos.close();
        outputData = baos.toByteArray();
        baos.close();

        return outputData;
    }

    private CompressUtils() {
    }
}
