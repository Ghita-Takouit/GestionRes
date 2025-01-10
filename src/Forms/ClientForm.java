/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Forms;
import entities.Client;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import service.*; 
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextPane;


/**
 *
 * @author aeztic
 */
public class ClientForm extends javax.swing.JPanel {
     private ClientService cs;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> rowSorter;
    /**
     * Creates new form ClientForm
     */
    public ClientForm() {
        initComponents();
        setupModernUI();
        cs = new ClientService();
        model = (DefaultTableModel) ClientListe.getModel();
        rowSorter = new TableRowSorter<>(model);
        ClientListe.setRowSorter(rowSorter);
        load();
        ClientListe.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int id = selectRow(evt);
            }
        });

        searchTxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchTxt.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchTxt.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchTxt.getText());
            }

            private void search(String str) {
                if (str.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 1, 2, 3,4));
                }
            }
        });
    }

    private void setupModernUI() {
        // Panel styling
        jPanel1.setBackground(new Color(245, 245, 247));
        jPanel2.setBackground(new Color(245, 245, 247));
        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel4.setBackground(new Color(255, 255, 255));

        // Input fields styling
        setupTextPane(nomTxt);
        setupTextPane(prenomTxt);
        setupTextPane(emailTxt);
        setupTextPane(telTxt);
        setupTextPane(searchTxt);

        // Buttons styling
        setupButton(addBtn, new Color(76, 175, 80));
        setupButton(editBtn, new Color(33, 150, 243));
        setupButton(deleteBtn, new Color(244, 67, 54));
        setupButton(clearBtn, new Color(158, 158, 158));

        // Table styling
        ClientListe.setRowHeight(35);
        ClientListe.setShowGrid(false);
        ClientListe.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ClientListe.setSelectionBackground(new Color(33, 150, 243, 50));
        ClientListe.getTableHeader().setBackground(new Color(245, 245, 247));
        ClientListe.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 12));
    }

    private void setupTextPane(JTextPane textPane) {
        textPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textPane.setFont(new java.awt.Font("Segoe UI", 0, 14));
    }

    private void setupButton(JButton button, Color backgroundColor) {
        button.setFont(new java.awt.Font("Segoe UI", 1, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2d.dispose();
    }

    private void refreshAdd(Client c) {
        model.addRow(new Object[]{
                c.getId(),
                c.getNom(),
                c.getPrenom(),
                c.getEmail(),
                c.getTelephone()
        });
    }
    
    public void load(){
        for(Client c : cs.findAll()){
            model.addRow(new Object[]{
                c.getId(),
                c.getNom(),
                c.getPrenom(),
                c.getEmail(),
                c.getTelephone()
            });
        }
    }

    private boolean validField(String txt){
        return txt != null && !txt.trim().isEmpty();
    }
   
    private void clearField(){
        nomTxt.setText("");
        prenomTxt.setText("");
        emailTxt.setText("");
        telTxt.setText("");
    }

    private int selectRow(MouseEvent evt) {
        int viewRow = ClientListe.rowAtPoint(evt.getPoint());
        if (viewRow >= 0) {
            int modelRow = ClientListe.convertRowIndexToModel(viewRow);
            int id = (int) model.getValueAt(modelRow, 0);
            nomTxt.setText((String) model.getValueAt(modelRow, 1));
            prenomTxt.setText((String) model.getValueAt(modelRow, 2));
            emailTxt.setText((String) model.getValueAt(modelRow, 3));
            telTxt.setText((String) model.getValueAt(modelRow, 4));
            return id;
        }
        return -1;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        nomTxt = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        prenomTxt = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        emailTxt = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        telTxt = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ClientListe = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchTxt = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("Nom :");

        jLabel2.setText("Prenom :");

        jLabel3.setText("Email :");

        jLabel4.setText("Telephone : ");

        addBtn.setBackground(new java.awt.Color(0, 153, 0));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("Ajouter");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(51, 153, 255));
        editBtn.setForeground(new java.awt.Color(255, 255, 255));
        editBtn.setText("Modifier");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Supprimer");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        clearBtn.setBackground(new java.awt.Color(102, 102, 102));
        clearBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearBtn.setText("Effacer les champs");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(nomTxt);

        jScrollPane4.setViewportView(prenomTxt);

        jScrollPane5.setViewportView(emailTxt);

        jScrollPane6.setViewportView(telTxt);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(95, 95, 95))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(jLabel4))))
                .addGap(24, 24, 24))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(addBtn)
                .addGap(40, 40, 40)
                .addComponent(editBtn)
                .addGap(34, 34, 34)
                .addComponent(deleteBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(clearBtn)
                .addGap(25, 25, 25))
        );

        ClientListe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nom", "Prenom", "Email", "Telephone"
            }
        ));
        jScrollPane1.setViewportView(ClientListe);

        jScrollPane3.setViewportView(searchTxt);

        jLabel5.setText("Recherche :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>                        

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        String nom = nomTxt.getText();
        String prenom = prenomTxt.getText();
        String telephone = telTxt.getText();
        String email = emailTxt.getText();
        boolean valid = true;

        if (!validField(nom)) {
            nomTxt.setBackground(Color.PINK);
            valid = false;
        }else {
            nomTxt.setBackground(Color.WHITE);
        }

        if (!validField(prenom)) {
            prenomTxt.setBackground(Color.PINK);
            valid = false;
        }else {
            prenomTxt.setBackground(Color.WHITE);
        }
        if (!validField(email)) {
            emailTxt.setBackground(Color.PINK);
            valid = false;
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
        emailTxt.setBackground(Color.PINK);
        JOptionPane.showMessageDialog(this, "Email invalide !");
        valid = false;
        } else if (cs.isDuplicate("email", email)) {
            emailTxt.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé !");
            valid = false;
        }
        else {
            emailTxt.setBackground(Color.WHITE);
        }
        if (!validField(telephone)) {
            telTxt.setBackground(Color.PINK);
            valid = false;
        } else if (!telephone.matches("\\d{10}")) {
        telTxt.setBackground(Color.PINK);
        JOptionPane.showMessageDialog(this, "Numéro de téléphone invalide (10 chiffres requis) !");
        valid = false;
        } else if (cs.isDuplicate("telephone", telephone)) {
            telTxt.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(this, "Ce numéro de téléphone est déjà utilisé !");
            valid = false;
        }else {
                telTxt.setBackground(Color.WHITE);
        }
        if (!valid) {
            JOptionPane.showMessageDialog(this , "Veuillez corriger les erreurs avant de continuer !");
            return;
        }
        Client c = new Client(nom , prenom, telephone , email);
        if(cs.create(c)){
            JOptionPane.showMessageDialog(this, "Client ajouté avec succès !");
            clearField();
            refreshAdd(c);
        }else {
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de l'ajout du client. Veuillez réessayer.");
                    
        } 
    }                                      

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        int selectedRow = ClientListe.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String nom = nomTxt.getText();
            String prenom = prenomTxt.getText();
            String email = emailTxt.getText();
            String telephone = telTxt.getText();
            boolean valid = true;

            if (!validField(nom)) {
                nomTxt.setBackground(Color.PINK);
                valid = false;
            } else {
                nomTxt.setBackground(Color.WHITE);
            }
            if (!validField(prenom)) {
                prenomTxt.setBackground(Color.PINK);
                valid = false;
            } else {
                prenomTxt.setBackground(Color.WHITE);
            }
            if (!validField(email)) {
                emailTxt.setBackground(Color.PINK);
                valid = false;
            } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            emailTxt.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(this, "Email invalide !");
            valid = false;
            } else if (cs.isDuplicate("email", email)) {
                emailTxt.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé !");
                valid = false;
            }else {
                emailTxt.setBackground(Color.WHITE);
            }
            if (!validField(telephone)) {
                telTxt.setBackground(Color.PINK);
                valid = false;
            } else if (!telephone.matches("\\d{10}")) {
            telTxt.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(this, "Numéro de téléphone invalide (10 chiffres requis) !");
            valid = false;
            } else if (cs.isDuplicate("telephone", telephone)) {
                telTxt.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, "Ce numéro de téléphone est déjà utilisé !");
                valid = false;
            }
            else {
                telTxt.setBackground(Color.WHITE);
            }
            if (!valid) {
                JOptionPane.showMessageDialog(this, "Veuillez corriger les erreurs avant de continuer !");
                return;
            }

            Client c = cs.findById(id);
            if (c != null) {
                c.setNom(nom);
                c.setPrenom(prenom);
                c.setEmail(email);
                c.setTelephone(telephone);
                if (cs.update(c)) {
                    JOptionPane.showMessageDialog(this, "Client modifié avec succès !");
                    model.setValueAt(nom, selectedRow, 1);
                    model.setValueAt(prenom, selectedRow, 2);
                    model.setValueAt(email, selectedRow, 3);
                    model.setValueAt(telephone, selectedRow, 4);
                    clearField();
                } else {
                    JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de la modification du client. Veuillez réessayer.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Client introuvable !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner le client à modifier.");
        } 
    }                                       

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        int selectedRow = ClientListe.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (cs.delete(id)) {
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Client supprimé avec succès !");
                    clearField();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du client.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.");
        }
    }                                         

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        clearField();
    }                                        


    // Variables declaration - do not modify                     
    private javax.swing.JTable ClientListe;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextPane emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextPane nomTxt;
    private javax.swing.JTextPane prenomTxt;
    private javax.swing.JTextPane searchTxt;
    private javax.swing.JTextPane telTxt;
    // End of variables declaration                   

    
}