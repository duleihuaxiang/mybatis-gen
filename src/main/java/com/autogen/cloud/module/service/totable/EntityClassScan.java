package com.autogen.cloud.module.service.totable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Spector on 2017/6/2.
 */
@Service
public class EntityClassScan {

    private static final Logger logger = LoggerFactory.getLogger(EntityClassScan.class);

    @Autowired
    private EntityPackage entityPackage;

    private String packagePath;
    private Set<Class<?>> allClasses = new HashSet<>();
    private Set<Class<?>> subClasses = new HashSet<>();
    private List<Class<?>> excludeClasses = new ArrayList<>();

    public void scan() {
        classScan();
        subClassScan();
    }

    public void scanAndGeneral() {
        classScan();
        subClassScan();
        generalTable();
    }

    private void classScan() {
        if (null == packagePath || 0 == packagePath.length()) {
            packagePath = "com.autogen.cloud.module.entity";
        }

        packagePath = packagePath.replace(".", "/");
        Enumeration<URL> resources = null;
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while(resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    getFileClass(packagePath, filePath, allClasses);
                }

            }
        } catch (IOException e) {
            logger.error("{} 加载失败：{}", packagePath, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 父类扫描
     */
    private void subClassScan() {
        allClasses.forEach(clazz -> {
            getSubClass(clazz);
        });

    }

    private void getSubClass(Class<?> childClass) {
        int count = 0;
        for (Class<?> allClass : allClasses) {
            if (childClass.isAssignableFrom(allClass) && !childClass.equals(allClass)) {
                getSubClass(allClass);
            } else {
                count++;
            }
        }
        if (count == allClasses.size()) {
            subClasses.add(childClass);
        }
    }

    /**
     * 获取class
     *
     * @param packagePath
     * @param filePath
     * @param classSet
     */
    private void getFileClass(String packagePath, String filePath, Set<Class<?>> classSet) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("包目录不存在:{}", packagePath);
        }

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean isDir = pathname.isDirectory();
                boolean classFile = pathname.getName().endsWith(".class");
                return isDir || classFile;
            }
        });

        if (null != files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileClass(packagePath + "/" + file.getName(), file.getAbsolutePath(), classSet);
                } else {
                    String name = file.getName();
                    name = name.substring(0, name.length() - 6);

                    String classpath = packagePath.replace('/', '.').concat("." + name);
                    try {
                        Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(classpath);
                        classSet.add(aClass);
                    } catch (ClassNotFoundException e) {
                        System.out.println("class file is not found: " + name);
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void removeNonRelationClass() {
        int length = excludeClasses.size();
        for (int i = 0; i < length; i++) {
            if (subClasses.contains(excludeClasses.get(i))) {
                subClasses.remove(excludeClasses.get(i));
            }
        }
    }

    public void excludeClass(Class<?>... clazz) {
        if (null != clazz) {
            excludeClasses = Arrays.asList(clazz);
        }
    }

    public void generalTable() {
        removeNonRelationClass();

        subClasses.forEach(clazz -> {
            entityPackage.setClazz(clazz);
            entityPackage.loadAllFields();
        });
    }

    public Set<Class<?>> getSubClasses() {
        return subClasses;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}
