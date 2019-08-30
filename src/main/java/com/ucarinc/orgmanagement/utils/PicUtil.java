package com.ucarinc.orgmanagement.utils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 11:36 2019/8/7
 **/
public class PicUtil {
    /***
     * @author wtf
     * @description  图片保存，
     * @date 2019/8/13 16:20
     * @param base64List 图片base64链表
     * @param picName 图片名称，带后缀
     * @return java.util.List<java.lang.String> 返回绝对路径
    */
    public List<String> toPic(List<String> base64List, List<String> picName, String dirName){
        Properties props = new Properties();
        // 读取配置文件
        try {
            props.load(this.getClass().getResourceAsStream("/config/picConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (base64List.size() > 0 && picName.size() > 0){
            List<String> picUrls = new ArrayList<String>();
            for (int i = 0; i<base64List.size(); i++){
                BASE64Decoder decoder = new BASE64Decoder();
                String base64Pic = base64List.get(i);
                String baseValue = base64Pic.replaceAll(" ", "+");
                byte[] b = null;
                try {
                    String [] strs = picName.get(i).split("[.]");
                    if (strs[1].equals("png")){
                        b = decoder.decodeBuffer(baseValue.replace("data:image/png;base64,", ""));
                    } else if (strs[1].equals("jpeg")){
                        b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", ""));
                    } else if (strs[1].equals("jpg")){
                        b = decoder.decodeBuffer(baseValue.replace("data:image/jpg;base64,", ""));
                    } else {
                        return null;
                    }
                    for(int y = 0; y<b.length; y++){
                       if (b[y] < 0){
                           b[y] += 256;
                       }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file = new File("");
                String filePath = null;
                try {
                    filePath = file.getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String imgFilePath = props.getProperty("picUrlUpline") + dirName;
                File imgFile = new File(imgFilePath);
                if (!imgFile.exists()){
                    imgFile.mkdir();
                }
                imgFilePath += "/" + picName.get(i);
                OutputStream out = null;
                try {
                    out = new FileOutputStream(imgFilePath);
                    out.write(b);
                    out.flush();
                    out.close();
                    String picUrl = props.getProperty("picUrlPre") + dirName + "/" + picName.get(i);
                    System.out.println(picUrl);
                    picUrls.add(picUrl);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            HashSet h = new HashSet(picUrls);
            picUrls.clear();
            picUrls.addAll(h);
            return picUrls;
        }else {
            return null;
        }
    }
}
