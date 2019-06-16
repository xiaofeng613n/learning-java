package com.xf.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-16 15:55
 * @Description:
 */
public class FileTest {





    //创建文件
    public static void createFile(String filePath) {

        Path path;

        path = Paths.get(filePath);

//        File file = new File(filePath);
//        path = file.toPath();

//        URI uri = URI.create(filePath);
//        path = Paths.get(filePath);

//        path = FileSystems.getDefault().getPath(filePath);

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public static void writeFile(String filePath, String source) {
        Path path = Paths.get(filePath);
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            bufferedWriter.write(source, 0, source.length());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //读取文件 Files.newBufferedReader
    public static void readFile(String filePath){

        Path path = Paths.get(filePath);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line = null;
            while ( (line = bufferedReader.readLine()) != null ) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;
        public FindJavaVisitor(List<Path> result){
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            String filePath = file.toFile().getAbsolutePath();
            if(filePath.matches(".*_[1|2]{1}\\.(?i)(jpg|jpeg|gif|bmp|png)")){
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result.add(file.getFileName());
            } return FileVisitResult.CONTINUE;
        }
    }

    //遍历一个目录和文件夹：Files.newDirectoryStream ， Files.walkFileTree

    //Files.copy(Path source, Path target, CopyOption options);
}
