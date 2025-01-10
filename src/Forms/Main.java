package Forms;

import javax.swing.*;
import java.awt.*;

public class Main extends javax.swing.JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public Main() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Create the sidebar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.LIGHT_GRAY);
        sidebarPanel.setPreferredSize(new Dimension(150, 300));

        // Create buttons for the sidebar
        JButton clientFormButton = new JButton("Client Form");
        JButton ChambreFormButton = new JButton("Chambre Form");

        // Add action listeners to the buttons
        clientFormButton.addActionListener(e -> showClientForm());
        ChambreFormButton.addActionListener(e -> showChambreForm());

        // Add buttons to the sidebar panel
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(clientFormButton);
        sidebarPanel.add(ChambreFormButton);

        // Create the content panel with CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Create and add the ClientForm and OtherForm to the content panel
        ClientForm clientForm = new ClientForm();
        ChambreForm chambreForm = new ChambreForm();
        contentPanel.add(clientForm, "ClientForm");
        contentPanel.add(chambreForm, "ChambreForm");

        // Set the layout of the main frame
        setLayout(new BorderLayout());
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        pack();
    }

    private void showClientForm() {
        cardLayout.show(contentPanel, "ClientForm");
    }

    private void showChambreForm() {
        cardLayout.show(contentPanel, "ChambreForm");
    }

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}