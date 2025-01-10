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
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_DELETE);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
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
    public List<Reservation> findAll(){
        List<Reservation> reservations = new ArrayList<>();
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FINDALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<Client> clients = findClientsByReservationId(rs.getInt("id"));
                List<Chambre> chambres = findChambresByReservationId(rs.getInt("id"));
                reservations.add(new Reservation(
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        clients,
                        chambres
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private void saveClientsAndChambres(int reservationId, Reservation reservation) throws SQLException {
        try (Connection connection = Connexion.getCnx()) {
            for (Client client : reservation.getClients()) {
                String clientQuery = "INSERT INTO reservation_clients (reservation_id, client_id) VALUES (?, ?)";
                try (PreparedStatement clientStatement = connection.prepareStatement(clientQuery)) {
                    clientStatement.setInt(1, reservationId);
                    clientStatement.setInt(2, client.getId());
                    clientStatement.executeUpdate();
                }
            }
            for (Chambre chambre : reservation.getChambres()) {
                String chambreQuery = "INSERT INTO reservation_chambres (reservation_id, chambre_id) VALUES (?, ?)";
                try (PreparedStatement chambreStatement = connection.prepareStatement(chambreQuery)) {
                    chambreStatement.setInt(1, reservationId);
                    chambreStatement.setInt(2, chambre.getId());
                    chambreStatement.executeUpdate();
                }
            }
        }
    }

    private void deleteClientsAndChambres(int reservationId) throws SQLException {
        try (Connection connection = Connexion.getCnx()) {
            String deleteClientsQuery = "DELETE FROM reservation_clients WHERE reservation_id = ?";
            try (PreparedStatement deleteClientsStatement = connection.prepareStatement(deleteClientsQuery)) {
                deleteClientsStatement.setInt(1, reservationId);
                deleteClientsStatement.executeUpdate();
            }
            String deleteChambresQuery = "DELETE FROM reservation_chambres WHERE reservation_id = ?";
            try (PreparedStatement deleteChambresStatement = connection.prepareStatement(deleteChambresQuery)) {
                deleteChambresStatement.setInt(1, reservationId);
                deleteChambresStatement.executeUpdate();
            }
        }
    }

    private List<Client> findClientsByReservationId(int reservationId) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT client_id FROM reservation_clients WHERE reservation_id = ?";
        try (Connection connection = Connexion.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            ResultSet resultSet = statement.executeQuery();
            ClientService clientService = new ClientService();
            while (resultSet.next()) {
                clients.add(clientService.findById(resultSet.getInt("client_id")));
            }
        }
        return clients;
    }

    private List<Chambre> findChambresByReservationId(int reservationId) throws SQLException {
        List<Chambre> chambres = new ArrayList<>();
        String query = "SELECT chambre_id FROM reservation_chambres WHERE reservation_id = ?";
        try (Connection connection = Connexion.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            ResultSet resultSet = statement.executeQuery();
            ChambreService chambreService = new ChambreService();
            while (resultSet.next()) {
                chambres.add(chambreService.findById(resultSet.getInt("chambre_id")));
            }
        }
        return chambres;
    }







}

