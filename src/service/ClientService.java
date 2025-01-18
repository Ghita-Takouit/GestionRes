package service;

import conn.Connexion;
import dao.IDAO;
import entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService implements IDAO<Client> {

    private final String SQL_INSERT = "INSERT INTO client (nom, prenom, telephone, email) VALUES(?, ?, ?, ?)";
    private final String SQL_DELETE = "DELETE FROM client WHERE id=?";
    private final String SQL_UPDATE = "UPDATE client SET nom=?, prenom=?, telephone=?, email=? WHERE id=?";
    private final String SQL_FIND = "SELECT * FROM client WHERE id=?";
    private final String SQL_FINDALL = "SELECT * FROM client";

    @Override
    public boolean create(Client obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getTelephone());
            ps.setString(4, obj.getEmail());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Client obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_UPDATE);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getTelephone());
            ps.setString(4, obj.getEmail());
            ps.setInt(5, obj.getId());
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
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Cannot delete or update a parent row: a foreign key constraint fails.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Client findById(int id) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FIND);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Client findByName(String nom) {
        String query = "SELECT * FROM client WHERE nom = ?";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(query);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FINDALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    public boolean isDuplicate(String field, String value) {
    String sql = "SELECT COUNT(*) AS count FROM client WHERE " + field + " = ?";
    try {
        PreparedStatement ps = Connexion.getCnx().prepareStatement(sql);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("count") > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
    }   
    
    
    
}
