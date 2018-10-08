package com.baidu.ai.aip.utils;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author zhongzhiqiang
 * @date 2018/9/29  17:34
 */
public class PhotoUtils {
    private static Webcam webcam = Webcam.getDefault();
    public static byte[] getPhotos(int type){
        try {
//            Webcam webcam = Webcam.getDefault();
            if(type == 1) {
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                WebcamPanel panel = new WebcamPanel(webcam);
                panel.setFPSDisplayed(true);
                panel.setDisplayDebugInfo(true);
                panel.setImageSizeDisplayed(true);
                panel.setMirrored(true);
                JFrame window = new JFrame("Test webcam panel");
                window.add(panel);
                window.setResizable(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.pack();
                window.setVisible(false);
            }

            if(type == 1) {
                Thread.sleep(2000);
            }
            BufferedImage bufferedImage = webcam.getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
//            System.out.println(imageInByte);
//            System.exit(0);
            return imageInByte;
//            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }

    }

    public static void main(String[] args){
        getPhotos(1);
        getPhotos(2);
    }
}
