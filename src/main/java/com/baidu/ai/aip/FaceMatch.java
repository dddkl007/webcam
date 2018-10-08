package com.baidu.ai.aip;

import com.alibaba.fastjson.JSON;
import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.*;

import java.io.File;
import java.text.DateFormat;
import java.util.*;

/**
 * 人脸对比
 */
public class FaceMatch {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static void match() {
        List<String> paths = getFilePath();

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            String accessToken = AuthService.getAuth();
            int count = 0;
            byte[] bytes1 = null;
            byte[] bytes2 = null;

            while (true) {
                try {
                    if (count == 0) {
                        bytes1 = PhotoUtils.getPhotos(1);
                        count++;
                    } else {
                        bytes1 = PhotoUtils.getPhotos(2);
                    }

                    for (String path : paths) {
                        try {
                            bytes2 = FileUtil.readFileByBytes(path);
                            String image1 = Base64Util.encode(bytes1);
                            String image2 = Base64Util.encode(bytes2);
                            List<Map<String, Object>> images = new ArrayList<>();
                            Map<String, Object> map1 = new HashMap<>();
                            map1.put("image", image1);
                            map1.put("image_type", "BASE64");
                            map1.put("face_type", "LIVE");
                            map1.put("quality_control", "LOW");
                            map1.put("liveness_control", "NORMAL");

                            Map<String, Object> map2 = new HashMap<>();
                            map2.put("image", image2);
                            map2.put("image_type", "BASE64");
                            map2.put("face_type", "LIVE");
                            map2.put("quality_control", "LOW");
                            map2.put("liveness_control", "NORMAL");
                            images.add(map1);
                            images.add(map2);
                            String param = GsonUtils.toJson(images);
                            String result = HttpUtil.post(url, accessToken, "application/json", param);
                            result = result.replace("result:", "");
                            System.out.println(result);
                            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                            int errorCode = jsonObject.getInteger("error_code");
                            if (errorCode == 0) {
                                double score = jsonObject.getJSONObject("result").getDouble("score");
                                if (score >= 50) {
                                    String dateFormat = DateFormat.getInstance().format(new Date());
                                    System.out.println("时间：" + dateFormat + " " + score + " " + print(path));
                                    break;
                                } else {
//                            System.out.println("===========没有找到最相似的人");
                                }
                            } else {
//                                System.out.println("========比对失败" + path);
                            }
                            Thread.sleep(1 * 1000);
                        }catch(Exception ex1){

                        }
                    }
                    paths = getFilePath();
                    Thread.sleep(2 * 1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getFilePath(){
        List<String> paths = new ArrayList<>();
        String path = "D:\\人脸识别";
        File file = new File(path);
        File[]  files = file.listFiles();
        for (File file1 : files){
            paths.add(file1.getPath());
        }
        return  paths;
    }

    public static String print(String path){
        path = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".")) + " 到访";
        return path;
    }

    public static void main(String[] args) {
        FaceMatch.match();
//        getFilePath();
    }
}