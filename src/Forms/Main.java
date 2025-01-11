package Forms;

import javax.swing.*;
import javax.swing.border.*;
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
        setMinimumSize(new Dimension(800, 600));

        // Create and style the navbar
        JPanel navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(47, 53, 66));  // Changed to match sidebar color
        navbarPanel.setPreferredSize(new Dimension(800, 50));
        navbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel titleLabel = new JLabel("Gestion Reservation");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        navbarPanel.add(titleLabel);

        // Create and style the sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(47, 53, 66));
        sidebarPanel.setPreferredSize(new Dimension(200, 600));
        sidebarPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Create custom menu items
        JPanel clientButton = createMenuItem("Clients", "👥");
        JPanel chambreButton = createMenuItem("Chambres", "🏠");
        JPanel reservationButton = createMenuItem("Reservation", "👥");


        // Add action listeners to the menu items
        clientButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showClientForm();
            }
        });
        
        chambreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showChambreForm();
            }
        });
        
        reservationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showReservationForm();
            }
        });

        // Add menu items to sidebar
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(clientButton);
        sidebarPanel.add(chambreButton);
        sidebarPanel.add(reservationButton);

        // Create the content panel with CardLayout
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(241, 242, 246));
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Add forms to content panel
        ClientForm clientForm = new ClientForm();
        ChambreForm chambreForm = new ChambreForm();
        ReservationForm reservationForm = new ReservationForm();

        contentPanel.add(clientForm, "ClientForm");
        contentPanel.add(chambreForm, "ChambreForm");
        contentPanel.add(reservationForm, "ReservationForm");

        // Set the layout of the main frame
        setLayout(new BorderLayout());
        add(navbarPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createMenuItem(String text, String icon) {
        JPanel menuItem = new JPanel();
        menuItem.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        menuItem.setBackground(new Color(47, 53, 66));
        menuItem.setMaximumSize(new Dimension(200, 40));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        menuItem.add(iconLabel);
        menuItem.add(textLabel);

        menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(new Color(87, 101, 116));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(new Color(47, 53, 66));
            }
        });

        return menuItem;
    }

    private void showClientForm() {
        cardLayout.show(contentPanel, "ClientForm");
    }

    void showChambreForm() {
        cardLayout.show(contentPanel, "ChambreForm");
    }

    void showReservationForm() {
        cardLayout.show(contentPanel, "ReservationForm");
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