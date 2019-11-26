package com.jibug.cetty.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
/**
 * @Description： TODO
 * @Author: zhoujumbo
 * @Date: ${Time} ${Date}
 */

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author jb.zhou
 * @Date 2019/7/4
 * @Version 1.0
 */

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void writeContent2ClassPathTxt(String fileName, String content){
        if(StringUtils.isEmpty(fileName) || StringUtils.isEmpty(content)){
            return;
        }
        try {
            Path path = Paths.get("/"+fileName+".txt");
            Files.write(path, content.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入文件到/target/classes
     * 相对当前目录 或者项目根目录
     * @param destFile
     */
    public static void writeFileToTarget(String destFile, List<String> lines) throws IOException {
        try {
             Path path = Paths.get("./" + destFile);
            //可以选择传入第三个OpenOptions参数,不传入默认CREATE和TRUNCATE_NEW,即如果文件不存在,创建该文件,如果存在,覆盖
            // Files.write(path, lines);
            // 如果不传第三个参数,则文件不存在的话默认会选择创建
            Files.write(path, lines);
            //还可以传入第三个参数,选择是否创建文件,是否在同名文件后拼接等
            //      Files.write(path,lines, StandardOpenOption.CREATE,StandardOpenOption.APPEND );
        } catch (IOException e) {
            throw e;
        }
    }
    public static void writeFileToTargetAppend(String destFile, List<String> lines) throws IOException {
        try {
            Path path = Paths.get("./" + destFile);
            Files.write(path,lines, StandardOpenOption.CREATE,StandardOpenOption.APPEND );
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 写入文件到指定绝对路径
     *
     * @param destPath
     */
    public static void writeFileToDest(String destPath, List<String> lines) throws IOException {
        try {
            Path path = Paths.get(destPath);
            Files.write(path, lines);
        } catch (IOException e) {
            throw e;
        }
    }
    public static void writeFileToDestAppend(String destPath, List<String> lines) throws IOException {
        try {
            Path path = Paths.get(destPath);
            Files.write(path, lines, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw e;
        }
    }
    /**
     * 字符缓冲流写入文件到指定绝对路径
     *
     * @param destPath
     */
    public static void writeBufferFileToDest(String destPath, List<String> lines) throws Exception {
        Path path = Paths.get(destPath);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            if (!Files.exists(path)){
                Files.createFile(path);
            }
            lines.stream().forEach(s -> {
                try {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                } catch (Exception e) {
                    log.error("写入失败");
                }
            });
        } catch (Exception e) {
            throw e;
        }

    }

    public static void writeBufferFileToDestAppend(String destPath, List<String> lines) throws Exception {
        Path path = Paths.get(destPath);
        try (
                FileWriter fw = new FileWriter(path.toFile(), true);
                BufferedWriter bw = new BufferedWriter(fw)){
            if (!Files.exists(path)){
                Files.createFile(path);
            }
            lines.stream().forEach(s -> {
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (Exception e) {
                    log.error("追加写入失败");
                }
            });
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 判断多级路径是否存在，不存在就创建
     *
     * @param filePath 支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
     */
    public static void isExistDir(String filePath) {
        String[] paths = {""};
        //切割路径
        try {
            String tempPath = new File(filePath).getCanonicalPath();//File对象转换为标准路径并进行切割，有两种windows和linux
            paths = tempPath.split("\\\\");//windows
            if(paths.length==1){paths = tempPath.split("/");}//linux
        } catch (IOException e) {
            throw new RuntimeException("切割路径错误"+e);
        }
        //判断是否有后缀
        boolean hasType = false;
        if(paths.length>0){
            String tempPath = paths[paths.length-1];
            if(tempPath.length()>0){
                if(tempPath.indexOf(".")>0){
                    hasType=true;
                }
            }
        }
        //创建文件夹
        String dir = paths[0];
        for (int i = 0; i < paths.length - (hasType?2:1); i++) {// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
            try {
                dir = dir + "/" + paths[i + 1];//采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
            } catch (Exception e) {
                throw new RuntimeException("文件夹创建发生异常"+e);
            }
        }
    }

    /**
     * 判断文件夹是否存在
     * @param filePath
     * @return
     */
    public static boolean isDirectoryPath(String filePath) {
        File file = new File(filePath);
        if(file.exists() && file.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * 判断路径是否存在
     * @param filePath
     * @return
     */
    public static boolean isExistsPath(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否存在
     * @param httpPath
     * @return
     */
    private static Boolean existHttpPath(String httpPath){
        URL httpurl = null;
        try {
            httpurl = new URL(new URI(httpPath).toASCIIString());
            URLConnection urlConnection = httpurl.openConnection();
            // urlConnection.getInputStream();
            Long TotalSize=Long.parseLong(urlConnection.getHeaderField("Content-Length"));
            if (TotalSize <= 0){
                return false;
            }
            return true;
        } catch (Exception e) {
            log.debug(httpurl + "文件不存在");
            return false;
        }
    }

    /**
     * 删除文件夹
     * @param folderPath folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) throws Exception {

        //删除完里面所有内容
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        //删除空文件夹
        myFilePath.delete();
    }

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     */
    public static boolean delAllFile(String path) throws Exception {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }

        if (!file.isDirectory()) {
            return flag;
        }

        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }

            if (temp.isFile()) {
                temp.delete();
            }

            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                //再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 取得文件前缀
     * @param fileName
     * @return
     */
    public static String getName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(".")).replace(" ", "");
    }

    /**
     * 取得文件后缀
     * @param fileName
     * @return
     */
    public static String getPostFix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length());
    }

    /**
     * 根据浏览器生产文件下载名称
     * @param userAgent
     * @param filename
     * @return
     * @throws Exception
     */
    public static String getDownLoadNameByUserAgent(String userAgent,String filename) throws Exception {
        String new_filename = URLEncoder.encode(filename, "UTF8");
        String rtn = "filename=\"" + new_filename + "\"";
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // IE浏览器，只能采用URLEncoder编码
            if (userAgent.indexOf("msie") != -1) {
                rtn = "filename=\"" + new_filename + "\"";
            } else if (userAgent.indexOf("opera") != -1) {          // Opera浏览器只能采用filename*
                rtn = "filename*=UTF-8''" + new_filename;
            } else if (userAgent.indexOf("safari") != -1 ) {        // Safari浏览器，只能采用ISO编码的中文输出
                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"),"ISO8859-1") + "\"";
            } else if (userAgent.indexOf("applewebkit") != -1 ) {   // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"),"ISO8859-1") + "\"";
            } else if (userAgent.indexOf("mozilla") != -1) {        // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"),"ISO8859-1") + "\"";
            }
        }
        return rtn;
    }

    /**
     * 文件名称中是否包含特殊字符
     * @param fileName 被检查字符串
     * @return 包含返回 true,不包含返回false
     */
    public static boolean checkName(String fileName){
        String[] strs = {"\\","/",":","*","?","\"","<",">","|"};
        Boolean flg = false;
        for(String str : strs){
            if (fileName.contains(str)) {
                flg = true;
                break;
            }
        }
        return flg;
    }


    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delFile(File file) {
        if(file.exists() && file.isFile())
            file.delete();
    }

    /**
     * 判断文件夹是否存在
     * @param filePath
     * @return
     */
    public static boolean isNotExistDir(String filePath) {
        File file = new File(filePath);
        if(file.isDirectory()) {
            if (file.exists()) {
                return false;
            }
        }
        return true;
    }
}



