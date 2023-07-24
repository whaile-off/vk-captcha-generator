package hdw.generator.generator;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class VKCaptchaGenerator {

    private static final int CAPTCHA_WIDTH = 130;
    private static final int CAPTCHA_HEIGHT = 50;

    private static final int MIN_CAPTCHA_LENGTH = 4;
    private static final int MAX_CAPTCHA_LENGTH = 7;

    private static int getRandomCaptchaLength() {
        return new Random().nextInt(MAX_CAPTCHA_LENGTH - MIN_CAPTCHA_LENGTH + 1) + MIN_CAPTCHA_LENGTH;
    }

    public static void generateCaptcha(String CHARACTERS, Font FONT, File outputDir) {
        int captchaLength = getRandomCaptchaLength();

        BufferedImage captchaImage = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = captchaImage.createGraphics();

        GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, CAPTCHA_WIDTH, CAPTCHA_HEIGHT, Color.LIGHT_GRAY);
        g.setPaint(gradient);
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Random random = new Random();

        int numWaves = 10;
        for (int i = 0; i < numWaves; i++) {
            int x1 = 0;
            int y1 = random.nextInt(CAPTCHA_HEIGHT);
            int x2 = CAPTCHA_WIDTH;
            int y2 = random.nextInt(CAPTCHA_HEIGHT);
            g.setColor(Color.LIGHT_GRAY);
            g.draw(new Line2D.Double(x1, y1, x2, y2));
        }

        int numBlueLines = 4;
        for (int i = 0; i < numBlueLines; i++) {
            int lineWidth = random.nextInt(3) + 1;
            Color blueColor = new Color(28, 99, 128);
            g.setColor(blueColor);
            g.setStroke(new BasicStroke(lineWidth));

            int x1 = random.nextInt(CAPTCHA_WIDTH);
            int y1 = random.nextInt(CAPTCHA_HEIGHT);
            int x2 = random.nextInt(CAPTCHA_WIDTH);
            int y2 = random.nextInt(CAPTCHA_HEIGHT);

            int waveAmplitude = 8;
            int waveFrequency = 20;
            int numSegments = 20;

            for (int j = 0; j < numSegments; j++) {
                int x1Segment = x1 + j * (x2 - x1) / numSegments;
                int x2Segment = x1 + (j + 1) * (x2 - x1) / numSegments;

                int y1Segment = y1 + (int) (waveAmplitude * Math.sin(2 * Math.PI * j / waveFrequency));
                int y2Segment = y1 + (int) (waveAmplitude * Math.sin(2 * Math.PI * (j + 1) / waveFrequency));

                if (x1Segment < 0) {
                    x1Segment = 0;
                } else if (x1Segment > CAPTCHA_WIDTH) {
                    x1Segment = CAPTCHA_WIDTH;
                }

                if (x2Segment < 0) {
                    x2Segment = 0;
                } else if (x2Segment > CAPTCHA_WIDTH) {
                    x2Segment = CAPTCHA_WIDTH;
                }

                if (y1Segment < 0) {
                    y1Segment = 0;
                } else if (y1Segment > CAPTCHA_HEIGHT) {
                    y1Segment = CAPTCHA_HEIGHT;
                }

                if (y2Segment < 0) {
                    y2Segment = 0;
                } else if (y2Segment > CAPTCHA_HEIGHT) {
                    y2Segment = CAPTCHA_HEIGHT;
                }

                g.draw(new Line2D.Double(x1Segment, y1Segment, x2Segment, y2Segment));
            }
        }

        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < captchaLength; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            captchaText.append(randomChar);

            Color startColor = new Color(192, 192, 192);
            Color endColor = new Color(28, 99, 128);
            GradientPaint gradientColor = new GradientPaint(0, 0, startColor, 0, CAPTCHA_HEIGHT, endColor);
            g.setPaint(gradientColor);

            int x = (i * CAPTCHA_WIDTH / captchaLength) + random.nextInt(10);
            int y = CAPTCHA_HEIGHT / 2 + random.nextInt(10);
            int charWidth = g.getFontMetrics().charWidth(randomChar);
            int charHeight = g.getFontMetrics().getHeight();

            if (x + charWidth > CAPTCHA_WIDTH) {
                x = CAPTCHA_WIDTH - charWidth;
            }
            if (y + charHeight > CAPTCHA_HEIGHT) {
                y = CAPTCHA_HEIGHT - charHeight / 2;
            }

            g.setFont(FONT);
            g.drawString(Character.toString(randomChar), x, y);
        }

        g.dispose();

        saveCaptchaToFile(captchaImage, String.valueOf(captchaText), outputDir);
    }

    public static void saveCaptchaToFile(BufferedImage captchaImage, String captchaText, File outputDir) {
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String filename = captchaText + ".png";
        File outputFile = new File(outputDir, filename);

        try {
            ImageIO.write(captchaImage, "png", outputFile);
            System.out.println("Капча успешно сохранена в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении капчи: " + e.getMessage());
        }
    }
}