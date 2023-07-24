package hdw.generator;

import hdw.generator.generator.VKCaptchaGenerator;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HDWcaptchaGenerator {

    public static void main(String[] args) throws IOException, FontFormatException {

        String customFontPath = "C:/Users/whail/IdeaProjects/HDWcaptchaGenerator/src/main/resources/Humingson Rough Tilted.ttf";
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(customFontPath));
        Font FONT = customFont.deriveFont(Font.PLAIN, 30);

        new Thread(() -> {
            int targetFileCount = 1000000;
            File folder = new File("vk-captcha/all");

            while (folder.listFiles().length < targetFileCount) {
                VKCaptchaGenerator.generateCaptcha("абвгдеёжзийклмнопрстуфхцчшщъыьэюяВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                        new Font("Arial", Font.PLAIN, 30),
                        folder);
            }
        }).start();
        new Thread(() -> {
            int targetFileCount = 1000000;
            File folder = new File("vk-captcha/int");

            while (folder.listFiles().length < targetFileCount) {
                VKCaptchaGenerator.generateCaptcha("0123456789",
                        FONT,
                        folder);
            }
        }).start();
        new Thread(() -> {
            int targetFileCount = 1000000;
            File folder = new File("vk-captcha/eng");

            while (folder.listFiles().length < targetFileCount) {
                VKCaptchaGenerator.generateCaptcha("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
                        FONT,
                        folder);
            }
        }).start();
        new Thread(() -> {
            int targetFileCount = 1000000;
            File folder = new File("vk-captcha/rus");

            while (folder.listFiles().length < targetFileCount) {
                VKCaptchaGenerator.generateCaptcha("абвгдеёжзийклмнопрстуфхцчшщъыьэюяВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ",
                        new Font("Arial", Font.PLAIN, 30),
                        folder);
            }
        }).start();
    }
}