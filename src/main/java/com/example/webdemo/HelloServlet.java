package com.example.webdemo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.imageio.ImageIO;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");

//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");

        //设置基础
        Random rdm = new Random();
        int len = 4;
        int fontsize = 28;
        int width = len * fontsize + 10;
        int height = 50;
        String code = getCode();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = bufferedImage.getGraphics();
        //设置背景色
        g.setColor(Color.WHITE);
        //绘制填充矩形
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 150; i++) {
            g.setColor(new Color(rdm.nextInt(256), rdm.nextInt(256), rdm.nextInt(256)));
            int x1 = rdm.nextInt(width);
            int y1 = rdm.nextInt(height);
            int x2 = rdm.nextInt(width);
            int y2 = rdm.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        //绘制验证码
        g.setFont(new Font("宋体", Font.BOLD, fontsize));
        for (int i = 0; i < len; i++) {
            g.setColor(new Color(rdm.nextInt(256), rdm.nextInt(256), rdm.nextInt(256)));
            char c = code.charAt(i);
            g.drawString(c + "", i * fontsize + 10, fontsize + 8);
        }
        //输出验证码
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
    }

    private String getCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        char result = 0;
        int result2 = 0;
        for (int i = 0; i < 4; i++) {
            int rs = random.nextInt(3);
            switch(rs){
                case 0:
                    result = (char) ('a' + random.nextInt(26));
                    sb.append(result);
                    break;
                case 1:
                    result = (char)('A' + random.nextInt(26));
                    sb.append(result);
                    break;
                case 2:
                    result2 = random.nextInt(10);
                    sb.append(result2);
                    break;
            }
        }

        return sb.toString();
    }

    public void destroy() {
    }
}