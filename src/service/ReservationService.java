package service;

import conn.Connexion;
import dao.IDAO;
import entities.Chambre;
import entities.Client;
import entities.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService implements IDAO<Reservation> {

    private final String SQL_INSERT = "INSERT INTO reservation (dateDebut, dateFin) VALUES(?, ?)";
    private final String SQL_DELETE = "DELETE FROM reservation WHERE id=?";
    private final String SQL_UPDATE = "UPDATE reservation SET dateDebut=?, dateFin=? WHERE id=?";
    private final String SQL_FIND = "SELECT * FROM reservation WHERE id=?";
    private final String SQL_FINDALL = "SELECT * FROM reservation";

    @Override
    public boolean create(Reservation obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(obj.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(obj.getDateFin().getTime()));
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int reservationId = generatedKeys.getInt(1);
                    saveClientsAndChambres(reservationId, obj);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reservation obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_UPDATE);
            ps.setDate(1, new java.sql.Date(obj.getDateDebut().getTime()));
            ps.setDate(2, new java.sql.Date(obj.getDateFin().getTime()));
            ps.setInt(3, obj.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
public boolean delete(int id) {
    Connection connection = null;
    try {
        connection = Connexion.getCnx();
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Failed to obtain a valid database connection.");
        }
        connection.setAutoCommit(false);

        // Delete related records in reservation_clients and reservation_chambres
        String deleteClientsQuery = "DELETE FROM reservation_client WHERE reservation_id = ?";
        try (PreparedStatement deleteClientsStatement = connection.prepareStatement(deleteClientsQuery)) {
            deleteClientsStatement.setInt(1, id);
            deleteClientsStatement.executeUpdate();
        }

        String deleteChambresQuery = "DELETE FROM reservation_chambre WHERE reservation_id = ?";
        try (PreparedStatement deleteChambresStatement = connection.prepareStatement(deleteChambresQuery)) {
            deleteChambresStatement.setInt(1, id);
            deleteChambresStatement.executeUpdate();
        }

        // Delete the reservation
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            boolean result = ps.executeUpdate() > 0;
            connection.commit();
            return result;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


    @Override
    public Reservation findById(int id) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FIND);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                List<Client> clients = findClientsByReservationId(id);
                List<Chambre> chambres = findChambresByReservationId(id);
                return new Reservation(
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        clients,
                        chambres
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            Connection connection = Connexion.getCnx();
            PreparedStatement ps = connection.prepareStatement(SQL_FINDALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date dateDebut = rs.getDate("dateDebut");
                Date dateFin = rs.getDate("dateFin");

                // Fetch clients and chambres
                List<Client> clients = findClientsByReservationId(id);
                List<Chambre> chambres = findChambresByReservationId(id);

                // Use the appropriate constructor
                Reservation reservation = new Reservation(id, dateDebut, dateFin, clients, chambres);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    private void saveClientsAndChambres(int reservationId, Reservation reservation) throws SQLException {
        try (Connection connection = Connexion.getCnx()) {
            for (Client client : reservation.getClients()) {
                String clientQuery = "INSERT INTO Reservation_Client (reservation_id, client_id) VALUES (?, ?)";
                try (PreparedStatement clientStatement = connection.prepareStatement(clientQuery)) {
                    clientStatement.setInt(1, reservationId);
                    clientStatement.setInt(2, client.getId());
                    clientStatement.executeUpdate();
                }
            }
            for (Chambre chambre : reservation.getChambres()) {
                String chambreQuery = "INSERT INTO Reservation_Chambre (reservation_id, chambre_id) VALUES (?, ?)";
                try (PreparedStatement chambreStatement = connection.prepareStatement(chambreQuery)) {
                    chambreStatement.setInt(1, reservationId);
                    chambreStatement.setInt(2, chambre.getId());
                    chambreStatement.executeUpdate();
                }
            }
        }
    }

    public void deleteClientsAndChambres(int reservationId) throws SQLException {
        try (Connection connection = Connexion.getCnx()) {
            String deleteClientsQuery = "DELETE FROM Reservation_Client WHERE reservation_id = ?";
            try (PreparedStatement deleteClientsStatement = connection.prepareStatement(deleteClientsQuery)) {
                deleteClientsStatement.setInt(1, reservationId);
                deleteClientsStatement.executeUpdate();
            }
            String deleteChambresQuery = "DELETE FROM Reservation_Chambre WHERE reservation_id = ?";
            try (PreparedStatement deleteChambresStatement = connection.prepareStatement(deleteChambresQuery)) {
                deleteChambresStatement.setInt(1, reservationId);
                deleteChambresStatement.executeUpdate();
            }
        }
    }

    private List<Client> findClientsByReservationId(int reservationId) throws SQLException {
    List<Client> clients = new ArrayList<>();
    String query = "SELECT client_id FROM Reservation_Client WHERE reservation_id = ?";
    try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
        ps.setInt(1, reservationId);
        try (ResultSet rs = ps.executeQuery()) {
            ClientService clientService = new ClientService();
            while (rs.next()) {
                clients.add(clientService.findById(rs.getInt("client_id")));
            }
        }
    }
    return clients;
    }

    private List<Chambre> findChambresByReservationId(int reservationId) throws SQLException {
        List<Chambre> chambres = new ArrayList<>();
        String query = "SELECT chambre_id FROM Reservation_Chambre WHERE reservation_id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                ChambreService chambreService = new ChambreService();
                while (rs.next()) {
                    chambres.add(chambreService.findById(rs.getInt("chambre_id")));
                }
            }
        }
        return chambres;
    }





}