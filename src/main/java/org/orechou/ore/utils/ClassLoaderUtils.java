package org.orechou.ore.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * 类加载器工具
 * 用于加载项目下面指定包名下所有的class文件
 * TODO：若要管理第三方的bean，或许需要实现加载jar包下的class
 *
 * @Author OreChou
 * @Create 2017-12-07 21:29
 */
public class ClassLoaderUtils {

    /**
     * 获取类加载器
     * @return ClassLoader 类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 迭代获取指定包名下所有的 class 文件
     * @param packageName 包名
     * @return Class 集合
     */
    public static HashSet<Class<?>> getClassFromPackage(String packageName) {
        HashSet<Class<?>> classSet = new HashSet<>();
        // 将包名转换成文件夹名
        String packageDirName = packageName.replace('.', '/');
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                switch (url.getProtocol()) {
                    case "file":
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        getClassFromFileOrDirectory(classSet, filePath, packageName);
                        break;
                    case "jar":

                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classSet;
    }

    /**
     * 获取指定文件或文件夹下的class
     * @param classSet class 集合
     * @param filePath 文件或者文件夹的名称
     * @param packageName 该文件所属包名
     */
    public static void getClassFromFileOrDirectory(HashSet<Class<?>> classSet, String filePath, String packageName) {
        File dir = new File(filePath);
        // 若文件不存在，且文件不是目录，则直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 若存在，则筛选出其中所有的以.class结尾的文件和目录
        File[] files = dir.listFiles((file) -> ((file.isFile() && file.getName().endsWith(".class")) || file.isDirectory()));
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                // 带有包名的类名
                String className = packageName + "." + fileName.substring(0, fileName.lastIndexOf('.'));
                loadClass(classSet, className);
            } else {
                String subPackageName = packageName + "." + fileName;
                String subFilePath = filePath + "/" + fileName;
                getClassFromFileOrDirectory(classSet, subFilePath, subPackageName);
            }
        }
    }

    /**
     * 加载一个类（不初始化该类）
     * @param classSet
     * @param className
     */
    public static void loadClass(HashSet<Class<?>> classSet, String className) {
        Class<?> clazz;
        try {
            // 加载一个类，但不初始化它
            clazz = Class.forName(className, false, getClassLoader());
            classSet.add(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        HashSet<Class<?>> classes = getClassFromPackage("org.orechou.ore.annotation");
//        for (Class clazz : classes) {
//            System.out.println(clazz.getName());
//        }
//    }

}
