/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MVC.view.LoginAndRegister;

import MVC.view.Dartboard.Dartboard;
import MVC.view.LoginAndRegister.Swing.EventLogin;

/**
 *
 * @author ssopt
 */
public class MainView extends javax.swing.JFrame {
    
    private Dartboard dartboard;
    /**
     * Creates new form LoginAndRegister
     */
    public MainView() {
        initComponents();
        dartboard = new Dartboard();
        EventLogin event = new EventLogin(){
          @Override
            public void loginDone() {
                mainPanel.removeAll();
                mainPanel.add(dartboard);
                mainPanel.revalidate();
                mainPanel.repaint();
            }

            @Override
            public void logOut() {
                mainPanel.removeAll();
                mainPanel.add(loginAndRegister1);
                mainPanel.revalidate();
                mainPanel.repaint();
            }  
        };
        loginAndRegister1.setEventLogin(event);
        dartboard.setEventLogin(event);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        loginAndRegister1 = new MVC.view.LoginAndRegister.Form.LoginAndRegister();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(203, 210, 171));
        mainPanel.setLayout(new java.awt.BorderLayout());

        loginAndRegister1.setBackground(new java.awt.Color(182, 214, 242));
        loginAndRegister1.setMinimumSize(new java.awt.Dimension(900, 550));
        mainPanel.add(loginAndRegister1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private MVC.view.LoginAndRegister.Form.LoginAndRegister loginAndRegister1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
