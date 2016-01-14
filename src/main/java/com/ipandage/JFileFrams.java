package com.ipandage;

import java.awt.FileDialog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.Socket;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class JFileFrams extends JFrame {
    private Socket s;
    // private String path = "E:\\doc";   //创建文件夹目录
    private static String date = new SimpleDateFormat("yyyyMMddHHmmss")//创建时间
            .format(new Date());
    private static String doc = "E:\\doc\\" + date + ".doc";
    String fileName = null;
    DataInputStream dis = null;

    public static void main(String[] args) {
        JFileFrams jf = new JFileFrams();
        jf.show();
    }

    public JFileFrams() {
        try {
            jbInit();
            this.setDefaultCloseOperation(3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        this.getContentPane().setLayout(null);
        btnSanCuan.setText("分析");
        btnSanCuan.addActionListener(new JFileFram_btnSanCuan_actionAdapter(
                this));
        this.setSize(380, 250);
        this.setLocation(300, 300);
        btnLiuLang.addActionListener(new JFileFram_btnLiuLang_actionAdapter(
                this));
        texIp.setText("");
        texIp.setBounds(new Rectangle(141, 11, 182, 27));
        texPort.setText("8888");
        texPort.setBounds(new Rectangle(62, 51, 82, 27));
        lblIp.setText("分析文件输出路径:");
        lblIp.setBounds(new Rectangle(27, 10, 107, 26));

        lblPort.setText("Port:");
        lblPort.setBounds(new Rectangle(23, 51, 32, 26));

        btnLiuLang.setBounds(new Rectangle(54, 137, 90, 30));
        btnLiuLang.setText("浏览");
        jTextField1.setText("file");
        jTextField1.setEditable(false);
        jTextField1.setBounds(new Rectangle(56, 99, 228, 28));
        this.getContentPane().add(lblIp);
        this.getContentPane().add(texIp);
        this.getContentPane().add(btnLiuLang);
        this.getContentPane().add(jTextField1);
        this.getContentPane().add(btnSanCuan);
        // this.getContentPane().add(lblPort);
        // this.getContentPane().add(texPort);
        this.getContentPane().add(button);
        btnSanCuan.setBounds(new Rectangle(198, 139, 89, 29));
    }

    JButton btnSanCuan = new JButton();
    JButton btnLiuLang = new JButton();
    JButton button = new JButton();
    JTextField texIp = new JTextField();
    JTextField texPort = new JTextField();
    JLabel lblIp = new JLabel();
    JLabel lblPort = new JLabel();
    File f = null;
    JTextField jTextField1 = new JTextField();
    DataOutputStream out = null;

    public void btnLiuLang_actionPerformed(ActionEvent e) {
//	        JFileChooser chooser = new JFileChooser();
//	        chooser.showOpenDialog(this);
        FileDialog fd = new FileDialog(this, "上传文件", FileDialog.LOAD);
        fd.show();
        String jfPath = fd.getDirectory() + fd.getFile();
        if ("null".equals(jfPath) == false) {     //浏览文件不选情况
            jTextField1.setText(jfPath);
            //JOptionPane.showMessageDialog(null, fd.getFile(), "标题条文字串", JOptionPane.ERROR_MESSAGE);
            try {
                out.writeUTF(fd.getFile());      //文件名发送到服务器
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void btnSanCuan_actionPerformed(ActionEvent e) {
        String filePath = jTextField1.getText();
        if ("file".equals(filePath)) {           //检查是否选择文件
            JOptionPane.showMessageDialog(this, "请选择文件", "提示", 2);
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(jTextField1.getText());
            BufferedInputStream bis = new BufferedInputStream(fis);
            int n;
            while ((n = bis.read()) != -1) {  //写文件
                out.write(n);
            }
            bis.close();
            fis.close();
            out.flush();
            out.close();
            JOptionPane.showMessageDialog(this, "上传成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

class JFileFram_btnSanCuan_actionAdapter implements ActionListener {
    private JFileFrams adaptee;

    JFileFram_btnSanCuan_actionAdapter(JFileFrams adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSanCuan_actionPerformed(e);
    }
}

class JFileFram_btnLiuLang_actionAdapter implements ActionListener {
    private JFileFrams adaptee;

    JFileFram_btnLiuLang_actionAdapter(JFileFrams adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLiuLang_actionPerformed(e);
    }

}
