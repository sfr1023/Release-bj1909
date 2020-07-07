package cn.qf.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by root on 2016/5/28.
 */
public class FileNameFilterImpl implements FilenameFilter {

    private String fileType;

    public FileNameFilterImpl(String fileType){
        this.fileType = fileType;
    }

    public boolean accept(File dir, String name) {
        boolean result = false;
        if(null != dir){
            //System.out.println("dir = [" + dir.getAbsolutePath() + "], name = [" + name + "]");
            result = name.endsWith(fileType);
        }
        return result;
    }

    public static void main(String[] args) {
        FileNameFilterImpl fnfi = new FileNameFilterImpl(".txt");

        File f = new File("F:\\sxt\\data");
        String[] fs = f.list(fnfi);
        if(null != fs && fs.length > 0){
            for(String sf :fs){
                System.out.println(sf);
            }
        }

    }
}
