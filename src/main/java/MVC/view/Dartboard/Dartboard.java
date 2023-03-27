/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MVC.view.Dartboard;

import MVC.view.Dartboard.Form.Form1;
import MVC.view.Dartboard.Form.Form2;
import MVC.view.Dartboard.Form.Form3;
import MVC.view.Dartboard.Form.Form4;
import MVC.view.Dartboard.Form.Form5;
import MVC.view.Dartboard.SubPanel.Panel;
import MVC.view.Dartboard.swing.EventNavigationBar;
import MVC.view.Dartboard.swing.NavigationBackgroundColor;
import MVC.view.LoginAndRegister.Swing.EventLogin;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ssopt
 */
public class Dartboard extends javax.swing.JFrame {

    private EventLogin event;
    
    public void setEventLogin(EventLogin event) {
        this.event = event;
    }
    /**
     * Creates new form Dartboard
     */
    public Dartboard() {
        initComponents();
        setLocationRelativeTo(null);
        navigationBar1.addItem(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\item1.png"));
        navigationBar1.addItem(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\item2.png"));
        navigationBar1.addItem(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\item3.png"));
        navigationBar1.addItem(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\item4.png"));
        navigationBar1.addItem(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\item5.png"));
        navigationBar1.addEvent(new EventNavigationBar() {
            @Override
            public void beforeSelected(int index) {
                if (index == 0) {
                    panel1.display(new Form1(), navigationBar1.getAnimator());
                } else if (index == 1) {
                    panel1.display(new Form2(), navigationBar1.getAnimator());
                } else if (index == 2) {
                    panel1.display(new Form3(), navigationBar1.getAnimator());
                } else if (index == 3) {
                    panel1.display(new Form4(), navigationBar1.getAnimator());
                } else if (index == 4) {
                    panel1.display(new Form5(), navigationBar1.getAnimator());
                }
            }

            @Override
            public void afterSelected(int index) {

            }
        });
        NavigationBackgroundColor nb = new NavigationBackgroundColor();
        nb.apply(getContentPane());
        nb.addColor(0, new Color(212, 207, 233));
        nb.addColor(1, new Color(207, 233, 225));
        nb.addColor(2, new Color(210, 230, 233));
        nb.addColor(3, new Color(220, 242, 230));
        nb.addColor(4, new Color(226, 222, 233));
        navigationBar1.setnavigationBackgroundColor(nb);
        
        button2.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button2.setBackground(new Color(236,244,255));
            }
            public void mouseExited(MouseEvent e) {
                button2.setBackground(new Color(204, 225, 246));
            }
        });
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
        textField1 = new MVC.view.LoginAndRegister.Swing.TextField();
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

        jPanel1.setBackground(new java.awt.Color(211, 225, 234));

        textField1.setBorder(null);
        textField1.setForeground(new java.awt.Color(167, 164, 164));
        textField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField1.setHint("QmHash/basyHash");

        button1.setBackground(new java.awt.Color(173, 173, 183));
        button1.setForeground(new java.awt.Color(21, 24, 37));
        button1.setText("Browse");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(204, 225, 246));
        button2.setForeground(new java.awt.Color(194, 204, 207));
        button2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ssopt\\OneDrive\\Documents\\NetBeansProjects\\MyFirstAppSwing\\src\\main\\java\\MVC\\view\\Dartboard\\icon\\connect.png")); // NOI18N
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
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    }//GEN-LAST:event_formWindowOpened

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
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
    private javax.swing.JPanel jPanel1;
    private MVC.view.Dartboard.swing.NavigationBar navigationBar1;
    private MVC.view.Dartboard.SubPanel.Panel panel1;
    private MVC.view.LoginAndRegister.Swing.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
