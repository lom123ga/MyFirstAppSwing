/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package MVC.view.Dartboard.Form;

import MVC.view.Dartboard.Dartboard;
import MVC.view.Dartboard.SubPanel.SubPanel;

import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Random;


/**
 *
 * @author ssopt
 */
public class Form3 extends SubPanel {

    /**
     * Creates new form Form3
     */
    
    
    
    public Form3() {
        initComponents();
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                try {
                    Random ran = new Random();
                    while (true) {
                        Thread.sleep(200);
                        URL url = new URL("http://example.com/file.txt");
                        long startTime = System.currentTimeMillis();
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        long endTime = System.currentTimeMillis();
                        long downloadTime = endTime - startTime;
                        int fileSize = connection.getContentLength();
                        double downloadSpeed = (double) fileSize / downloadTime + 30;
                        
                        int a[] = {1,-1};
                        
                        int in = (int) downloadSpeed ;
                        int out  = (int) downloadSpeed + a[ran.nextInt(2)]*ran.nextInt(5);
                  
                        
                        speedTest1.setValueWithAnimation(out);
                        speedTest2.setValueWithAnimation(in);
                       
                    }
                } catch (Exception e) {
                    System.out.println("error");
                }
            }
        }
        ).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtConnect = new javax.swing.JLabel();
        speedTest1 = new MVC.view.Dartboard.Form.SpeedTest();
        speedTest2 = new MVC.view.Dartboard.Form.SpeedTest();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setMaximumSize(new java.awt.Dimension(900, 408));
        setMinimumSize(new java.awt.Dimension(900, 408));
        setPreferredSize(new java.awt.Dimension(900, 408));

        txtConnect.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        txtConnect.setForeground(new java.awt.Color(236, 125, 136));
        txtConnect.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtConnect.setText("Fail to connect to IPFS server");

        speedTest1.setColor1(new java.awt.Color(103, 127, 252));
        speedTest1.setColor2(new java.awt.Color(255, 167, 150));

        speedTest2.setColor1(new java.awt.Color(255, 159, 144));
        speedTest2.setColor2(new java.awt.Color(135, 209, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(248, 158, 126));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DownLoad");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(118, 131, 252));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("UpLoad");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(97, 97, 97));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("NETWORK TRAFFIC");

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(speedTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(speedTest1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(txtConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(jLabel1)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(speedTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(speedTest1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void checkConnect(){
        if (Dartboard.getConnect() == true){
            txtConnect.setText("IPFS is connected");
            txtConnect.setForeground(new java.awt.Color(68, 193, 24));
        }else{
            txtConnect.setText("Fail to connect to IPFS server");
            txtConnect.setForeground(new java.awt.Color(236, 125, 136));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private MVC.view.Dartboard.Form.SpeedTest speedTest1;
    private MVC.view.Dartboard.Form.SpeedTest speedTest2;
    private javax.swing.JLabel txtConnect;
    // End of variables declaration//GEN-END:variables
}
