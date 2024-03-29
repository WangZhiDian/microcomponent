package com.meng.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.regex.Pattern;
import static com.meng.define.CommonDefine.*;


/**
 * file utils
 *
 * @author : sunyuecheng
 */
public final class FileUtils {

    /**
     * read resource by str
     *
     * @param filePath :
     * @return String :
     * @throws Exception :
     */
    public static String readResourceByStr(String filePath) throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(
                FileUtils.class.getClassLoader().getResourceAsStream(filePath), DEFAULT_ENCODING);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuffer fileStrBuf = new StringBuffer();
            String tempStr = null;
            while ((tempStr = reader.readLine()) != null) {
                fileStrBuf.append(tempStr + System.lineSeparator());
            }
            reader.close();
            return fileStrBuf.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * read file by byte
     *
     * @param filePath :
     * @return byte[] :
     * @throws Exception :
     */
    public static byte[] readFileByByte(String filePath) throws Exception {
        if (!Pattern.matches(LINUX_PATH_REGEX, filePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, filePath)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("File do not exist.");
        }
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] fileByte = new byte[(int) file.length()];

            int byteRead = inputStream.read(fileByte);
            if (byteRead != file.length()) {
                throw new Exception("Read file error.");
            }

            return fileByte;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * read file by str
     *
     * @param filePath :
     * @return String :
     * @throws Exception :
     */
    public static String readFileByStr(String filePath) throws Exception {
        if (!Pattern.matches(LINUX_PATH_REGEX, filePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, filePath)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("File do not exist.");
        }

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), DEFAULT_ENCODING);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuffer fileStrBuf = new StringBuffer();
            String tempStr = null;
            while ((tempStr = reader.readLine()) != null) {
                fileStrBuf.append(tempStr + System.lineSeparator());
            }
            reader.close();
            return fileStrBuf.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * write file by byte
     *
     * @param filePath    :
     * @param fileContent :
     * @param append      :
     * @return boolean :
     * @throws Exception :
     */
    public static boolean writeFileByByte(String filePath, byte[] fileContent, boolean append) throws Exception {
        if (!Pattern.matches(LINUX_PATH_REGEX, filePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, filePath)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                throw new Exception("Make file dir error.");
            }
            if (!file.createNewFile()) {
                throw new Exception("Create file error.");
            }

        }

        if (fileContent != null && fileContent.length > 0) {
            try (OutputStream outputStream = new FileOutputStream(filePath, append)) {
                outputStream.write(fileContent);
            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    /**
     * write file by str
     *
     * @param filePath    :
     * @param fileContent :
     * @param append      :
     * @return boolean :
     * @throws Exception :
     */
    public static boolean writeFileByStr(String filePath, String fileContent, boolean append) throws Exception {
        if (!Pattern.matches(LINUX_PATH_REGEX, filePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, filePath)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                throw new Exception("Make file dir error.");
            }
            if (!file.createNewFile()) {
                throw new Exception("Create file error.");
            }

        }

        if (fileContent != null && fileContent.length() > 0) {
            OutputStreamWriter outputStreamWriter
                    = new OutputStreamWriter(new FileOutputStream(file, append), DEFAULT_ENCODING);
            try (BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                bufferedWriter.write(fileContent);
            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }

    /**
     * copy file
     *
     * @param srcFilePath :
     * @param dstFilePath :
     * @return boolean :
     * @throws Exception :
     */
    public static boolean copyFile(String srcFilePath, String dstFilePath) throws Exception {
        if ((!Pattern.matches(LINUX_PATH_REGEX, srcFilePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, srcFilePath))
                || (!Pattern.matches(LINUX_PATH_REGEX, dstFilePath)
                && !Pattern.matches(WINDOWS_PATH_REGEX, dstFilePath))) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new Exception("File do not exist.");
        }

        try (InputStream inputStream = new FileInputStream(srcFilePath);
             OutputStream outputStream = new FileOutputStream(dstFilePath)) {

            byte[] fileByte = new byte[READ_BUFFER_SIZE];

            int byteRead = 0;
            while ((byteRead = inputStream.read(fileByte)) != -1) {
                outputStream.write(fileByte, 0, byteRead);
            }

        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * copy directory
     *
     * @param srcDir :
     * @param dstDir :
     * @return boolean :
     * @throws Exception :
     */
    public static boolean copyDirectory(String srcDir, String dstDir) throws Exception {
        if ((!Pattern.matches(LINUX_PATH_REGEX, srcDir)
                && !Pattern.matches(WINDOWS_PATH_REGEX, srcDir))
                || (!Pattern.matches(LINUX_PATH_REGEX, dstDir)
                && !Pattern.matches(WINDOWS_PATH_REGEX, dstDir))) {
            throw new IllegalArgumentException("Invalid dir name.");
        }

        File srcFile = new File(srcDir);
        if (!srcFile.exists() || !srcFile.canRead()) {
            throw new Exception("Dir do not vaild.");
        }
        File dstFile = new File(dstDir);
        if (!dstFile.exists() && !dstFile.mkdirs()) {
            throw new Exception("Make dir error.");
        }

        File[] fileList = srcFile.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isFile()) {
                    copyFile(srcDir + File.separator + fileList[i].getName(),
                            dstDir + File.separator + fileList[i].getName());
                } else if (fileList[i].isDirectory()) {
                    copyDirectory(srcDir + File.separator + fileList[i].getName(),
                            dstDir + File.separator + fileList[i].getName());
                }
            }
        }

        return true;
    }

    /**
     * delete directory
     *
     * @param dirPath :
     * @return boolean :
     */
    public static boolean deleteDirectory(String dirPath) {
        if (StringUtils.isEmpty(dirPath)) {
            return false;
        }

        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            String[] childrenFiles = dir.list();
            if(childrenFiles != null) {
                for (String childrenFile : childrenFiles) {
                    boolean success = deleteDirectory(dirPath + File.separator + childrenFile);
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    private FileUtils() {
    }
}
