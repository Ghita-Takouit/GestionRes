/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Forms;

import entities.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import service.ChambreService;
import service.ClientService;
import service.ReservationService;
import java.sql.SQLException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.mail.MessagingException;
import utils.EmailSender;
/**
 *
 * @author aeztic
 */
public class ReservationForm extends javax.swing.JPanel {
    private List<Reservation> reservations;
    private ReservationService res;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> rowSorter;
    /**
     * Creates new form ReservationForm
     */
    public ReservationForm() {
        initComponents();
        res = new ReservationService();
        model = (DefaultTableModel) reservationListe.getModel();
        rowSorter = new TableRowSorter<>(model);
        reservationListe.setRowSorter(rowSorter);
        load();
        

        reservationListe.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int id = selectRow(evt);
                System.out.print(id);
            }
        });
       
        ItemsClientBox();
        ItemsChambreBox();
        
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
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 1, 2, 3));
                }
            }
        });
    }

    private void ItemsClientBox() {
    clientBox.removeAllItems();
    clientBox.addItem("Choisir un client");
    ClientService clientService = new ClientService();
    List<Client> clients = clientService.findAll();
    for (Client client : clients) {
        clientBox.addItem(client.getNom()); 
    }
    }

    private void ItemsChambreBox() {
        chambreBox.removeAllItems();
        chambreBox.addItem("Choisir une chambre");
        ChambreService chambreService = new ChambreService();
        List<Chambre> chambres = chambreService.findAll();
        for (Chambre chambre : chambres) {
            chambreBox.addItem(String.valueOf(chambre.getnumChambre()));
        }
    }

    
  private void refreshAdd(Reservation r) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String formattedDateDebut = sdf.format(r.getDateDebut());
    String formattedDateFin = sdf.format(r.getDateFin());

    String clientNames = r.getClients().stream()
                           .map(Client::getNom)   
                           .collect(Collectors.joining(", "));  

    String chambreNumbers = r.getChambres().stream()
                              .map(Chambre::getnumChambre)   
                              .map(String::valueOf)   
                              .collect(Collectors.joining(", "));  

    model.addRow(new Object[]{
            r.getId(),
            formattedDateDebut,  
            formattedDateFin,    
            clientNames,        
            chambreNumbers      
    });
}
    
    public void load(){
        
       reservations = res.findAll();
        for(Reservation r : reservations){
            model.addRow(new Object[]{
                r.getId(),
                r.getDateDebut(),
                r.getDateFin(),
                r.getClients(),
                r.getChambres(),
                
            });
        }
    }
    
    private void clearField(){
        DateDebut.setDate(null);
        DateFin.setDate(null);
        chambreBox.setSelectedIndex(0);
        clientBox.setSelectedIndex(0);
    }
    
    
    private boolean validField(String txt){
        return txt != null && !txt.trim().isEmpty();
    }

    private int selectRow(MouseEvent evt) {
        int viewRow = reservationListe.rowAtPoint(evt.getPoint());
        if (viewRow >= 0) {
            int modelRow = reservationListe.convertRowIndexToModel(viewRow);
            Object idValue = model.getValueAt(modelRow, 0);
            if (idValue instanceof Integer) {
                return (Integer) idValue;
            }
        }
        return -1;
    }

       
    private boolean validateFields(Date DDebut, Date DFin, String clientlib, String chambrelib) {
        boolean valid = true;

        // Validate dates
        if (DDebut == null || DFin == null) {
            valid = false;
            JOptionPane.showMessageDialog(null, "Les dates sont invalides !");
        }

        // Validate client and chambre
        if (clientlib == null || clientlib.isEmpty()) {
            clientBox.setBackground(Color.PINK);
            valid = false;
        } else {
            clientBox.setBackground(Color.WHITE);
        }

        if (chambrelib == null || chambrelib.isEmpty()) {
            chambreBox.setBackground(Color.PINK);
            valid = false;
        } else {
            chambreBox.setBackground(Color.WHITE);
        }

        return valid;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DateDebut = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        DateFin = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        chambreBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        clientBox = new javax.swing.JComboBox<>();
        addBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        generateBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reservationListe = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTxt = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("Date Debut :");

        jLabel2.setText("Date Fin :");

        jLabel3.setText("Chambre :");

        jLabel4.setText("Client :");

        addBtn.setBackground(new java.awt.Color(0, 153, 0));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("Ajouter");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 51, 0));
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Supprimer");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        generateBtn.setBackground(new java.awt.Color(0, 204, 255));
        generateBtn.setForeground(new java.awt.Color(255, 255, 255));
        generateBtn.setText("Générer PDF");
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(clientBox, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chambreBox, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(DateDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))))
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(generateBtn)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DateDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chambreBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(addBtn)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn)
                .addGap(49, 49, 49)
                .addComponent(generateBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reservationListe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Date Debut", "Date Fin", "Client", "Chambre"
            }
        ));
        jScrollPane1.setViewportView(reservationListe);

        jScrollPane2.setViewportView(searchTxt);

        jLabel5.setText("Rechercher :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 68, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try {
        if (clientBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (chambreBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une chambre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String clientName = (String) clientBox.getSelectedItem();
        ClientService clientService = new ClientService();
        Client client = clientService.findByName(clientName);

        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client introuvable. Veuillez vérifier la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numChambre = Integer.parseInt((String) chambreBox.getSelectedItem());
        ChambreService chambreService = new ChambreService();
        Chambre chambre = chambreService.findByNum(numChambre);

        if (chambre == null) {
            JOptionPane.showMessageDialog(this, "Chambre introuvable. Veuillez vérifier la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.Date dateDebut = DateDebut.getDate();
        java.util.Date dateFin = DateFin.getDate();

        // Vérifier les dates
        if (dateDebut == null || dateFin == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dateFin.before(dateDebut)) {
            JOptionPane.showMessageDialog(this, "La date de fin doit être après la date de début.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<Client> clientsList = new ArrayList<>();
        clientsList.add(client);  

        List<Chambre> chambresList = new ArrayList<>();
        chambresList.add(chambre);  

        Reservation reservation = new Reservation(dateDebut, dateFin, clientsList, chambresList);
                ReservationService reservationService = new ReservationService();
        boolean success = reservationService.create(reservation);

        if (success) {
            // Envoyer l'email de confirmation
            try {
                EmailSender.sendReservationConfirmation(reservation);
                JOptionPane.showMessageDialog(this, 
                    "Réservation ajoutée avec succès et email envoyé!", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearField();
                refreshAdd(reservation);
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Réservation ajoutée mais erreur d'envoi d'email: " + e.getMessage(), 
                    "Attention", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
      int selectedRow = reservationListe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int reservationId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reservation?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ReservationService reservationService = new ReservationService();
            if (reservationService.delete(reservationId)) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Reservation deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete reservation", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = reservationListe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
                Paragraph title = new Paragraph("Détails de la Réservation", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                // Get reservation details
                int reservationId = (int) model.getValueAt(selectedRow, 0);
                ReservationService reservationService = new ReservationService();
                Reservation reservation = reservationService.findById(reservationId);

                if (reservation != null) {
                    // Reservation Section
                    Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                    Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
                    
                    // Reservation Details
                    document.add(new Paragraph("Informations de Réservation", sectionFont));
                    document.add(new Paragraph("ID: " + reservation.getId(), contentFont));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    document.add(new Paragraph("Date de début: " + sdf.format(reservation.getDateDebut()), contentFont));
                    document.add(new Paragraph("Date de fin: " + sdf.format(reservation.getDateFin()), contentFont));
                    document.add(new Paragraph("\n"));

                    // Client Details
                    document.add(new Paragraph("Informations Client", sectionFont));
                    for (Client client : reservation.getClients()) {
                        document.add(new Paragraph("Nom: " + client.getNom(), contentFont));
                        document.add(new Paragraph("Téléphone: " + client.getTelephone(), contentFont));
                        document.add(new Paragraph("Email: " + client.getEmail(), contentFont));
                    }
                    document.add(new Paragraph("\n"));

                    // Chambre Details
                    document.add(new Paragraph("Informations Chambre", sectionFont));
                    for (Chambre chambre : reservation.getChambres()) {
                        document.add(new Paragraph("Numéro de chambre: " + chambre.getnumChambre(), contentFont));
                        document.add(new Paragraph("Type: " + chambre.getCategorie(), contentFont));
                        document.add(new Paragraph("Prix: " + chambre.getPrix() + " €", contentFont));
                    }

                    // Footer
                    document.add(new Paragraph("\n"));
                    Paragraph footer = new Paragraph("Document généré le " + sdf.format(new Date()), contentFont);
                    footer.setAlignment(Element.ALIGN_CENTER);
                    document.add(footer);
                }

                document.close();

                JOptionPane.showMessageDialog(this, 
                    "PDF généré avec succès!\nEmplacement: " + filePath, 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la génération du PDF: " + e.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }                                           

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateDebut;
    private com.toedter.calendar.JDateChooser DateFin;
    private javax.swing.JButton addBtn;
    private javax.swing.JComboBox<String> chambreBox;
    private javax.swing.JComboBox<String> clientBox;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton generateBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable reservationListe;
    private javax.swing.JTextPane searchTxt;
    // End of variables declaration//GEN-END:variables
}

