/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MVC.view.Dartboard;

import MVC.controller.BlockchainManager;
import MVC.view.Dartboard.Form.Form1;
import MVC.view.Dartboard.Form.Form2;
import MVC.view.Dartboard.Form.Form3;
import MVC.view.Dartboard.Form.Form4;
import MVC.view.Dartboard.Form.Form5;
import MVC.view.Dartboard.SubPanel.Panel;
import MVC.view.Dartboard.swing.EventNavigationBar;
import MVC.view.Dartboard.swing.NavigationBackgroundColor;
import MVC.view.LoginAndRegister.Swing.EventLogin;
import MVC.view.Message.GlassPanePopup;
import MVC.view.Message.MessageConect;
import MVC.view.Message.MessageNotconnect;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 *
 * @author ssopt
 */
public class Dartboard extends javax.swing.JFrame {

    private EventLogin event;
    
    private static boolean isConnect = false;
    
    private Form1 form1 = new Form1();
    private Form2 form2 = new Form2();
    private Form3 form3 = new Form3();
    private Form4 form4 = new Form4();
    private Form5 form5 = new Form5();
    
    public static boolean getConnect(){
        return isConnect;
    }
    
    private static String hash = "";
    
    public void setEventLogin(EventLogin event) {
        this.event = event;
    }
    /**
     * Creates new form Dartboard
     */
    public Dartboard() {
        initComponents();
        GlassPanePopup.install(this);
        setLocationRelativeTo(null);
        navigationBar1.addItem(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/item1.png")));
        navigationBar1.addItem(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/item2.png")));
        navigationBar1.addItem(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/item3.png")));
        navigationBar1.addItem(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/item4.png")));
        navigationBar1.addItem(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/item5.png")));
        navigationBar1.addEvent(new EventNavigationBar() {
            @Override
            public void beforeSelected(int index) {
                if (index == 0) {
                    form1 = new Form1();
                    panel1.display(form1, navigationBar1.getAnimator());
                } else if (index == 1) {
                    form2 = new Form2();
                    panel1.display(form2, navigationBar1.getAnimator());
                } else if (index == 2) {
                    form3 = new Form3();
                    form3.checkConnect();
                    panel1.display(form3, navigationBar1.getAnimator());
                } else if (index == 3) {
                    form4 = new Form4();
                    panel1.display(form4, navigationBar1.getAnimator());
                } else if (index == 4) {
                    form5 = new Form5();
                    panel1.display(form5, navigationBar1.getAnimator());
                }
            }

            @Override
            public void afterSelected(int index) {

            }
        });
        NavigationBackgroundColor nb = new NavigationBackgroundColor();
        nb.apply(getContentPane());
        nb.addColor(0, new Color(243, 255, 255));
        nb.addColor(1, new Color(243, 255, 255));
        nb.addColor(2, new Color(243, 255, 255));
        nb.addColor(3, new Color(243, 255, 255));
        nb.addColor(4, new Color(243, 255, 255));
        navigationBar1.setnavigationBackgroundColor(nb);
        
        button2.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button2.setBackground(new Color(236,244,255));
            }
            public void mouseExited(MouseEvent e) {
                button2.setBackground(new Color(204, 225, 246));
            }
        });
        
        button1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button1.setBackground(new Color(203,200,220));
            }
            public void mouseExited(MouseEvent e) {
                button1.setBackground(new Color(214, 214, 220));
            }
        });
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Terminate the program after the close button is clicked.
                shutdown();
                System.exit(0);
            }
        });
    }
    
   public void shutdown(){
    try{
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c","powershell.exe","ipfs shutdown");
        pb.start();
    }catch(IOException e){
                    
    }
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigationBar1 = new MVC.view.Dartboard.swing.NavigationBar();
        panel1 = new MVC.view.Dartboard.SubPanel.Panel();
        jPanel1 = new javax.swing.JPanel();
        enterHash = new MVC.view.LoginAndRegister.Swing.TextField();
        button1 = new MVC.view.LoginAndRegister.Swing.Button();
        button2 = new MVC.view.LoginAndRegister.Swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 550));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout navigationBar1Layout = new javax.swing.GroupLayout(navigationBar1);
        navigationBar1.setLayout(navigationBar1Layout);
        navigationBar1Layout.setHorizontalGroup(
            navigationBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        navigationBar1Layout.setVerticalGroup(
            navigationBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(241, 255, 255));

        enterHash.setBorder(null);
        enterHash.setForeground(new java.awt.Color(167, 164, 164));
        enterHash.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        enterHash.setHint("QmHash/basyHash");

        button1.setBackground(new java.awt.Color(214, 214, 220));
        button1.setForeground(new java.awt.Color(115, 115, 117));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/brower.png"))); // NOI18N
        button1.setText("Browse");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(204, 225, 246));
        button2.setForeground(new java.awt.Color(194, 204, 207));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MVC/view/Dartboard/icon/connect.png"))); // NOI18N
        button2.setMaximumSize(new java.awt.Dimension(30, 30));
        button2.setMinimumSize(new java.awt.Dimension(30, 30));
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(enterHash, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(enterHash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 6, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navigationBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navigationBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        navigationBar1.initSelectedIndex(2);
        hash = enterHash.getText();
    }//GEN-LAST:event_formWindowOpened

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        try{
            Thread.sleep(300);
        }catch(Exception e){
            
        }
        
        hash = enterHash.getText();
        
        navigationBar1.setSelectedIndex(1);
        if (hash != null){
            String nzme = "Name: " + BlockchainManager.FindImg(hash);
            form2.setName(nzme);
            
        }
        form2.setTable();
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        try{
            if (isConnect == false){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c","powershell.exe","ipfs daemon");
                pb.start();
                isConnect = true;
                GlassPanePopup.showPopup(new MessageConect());
                form3.checkConnect();
            }else{
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c","powershell.exe","ipfs shutdown");
                pb.start();
                isConnect = false;
                GlassPanePopup.showPopup(new MessageNotconnect());
                form3.checkConnect();
            }
        }catch(Exception e){
            
        }
        
        
    }//GEN-LAST:event_button2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dartboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dartboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dartboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dartboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dartboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private MVC.view.LoginAndRegister.Swing.Button button1;
    private MVC.view.LoginAndRegister.Swing.Button button2;
    private MVC.view.LoginAndRegister.Swing.TextField enterHash;
    private javax.swing.JPanel jPanel1;
    private MVC.view.Dartboard.swing.NavigationBar navigationBar1;
    private MVC.view.Dartboard.SubPanel.Panel panel1;
    // End of variables declaration//GEN-END:variables
}
