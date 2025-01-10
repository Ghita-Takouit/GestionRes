package service;

import conn.Connexion;
import dao.IDAO;
import entities.Categorie;
import entities.Chambre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements IDAO<Chambre> {

    private final String SQL_INSERT = "INSERT INTO chambre (numChambre, prix, telephone, categorie_id) VALUES(?, ?, ?, ?)";
    private final String SQL_DELETE = "DELETE FROM chambre WHERE id=?";
    private final String SQL_UPDATE = "UPDATE chambre SET numChambre=?, telephone=?, prix=?, categorie_id=? WHERE id=?";
    private final String SQL_FIND = "SELECT * FROM chambre WHERE id=?";
    private final String SQL_FINDALL = "SELECT * FROM chambre";

    @Override
    public boolean create(Chambre obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, obj.getnumChambre());     
            ps.setDouble(2, obj.getPrix());        
            ps.setString(3, obj.getTelephone());   
            ps.setInt(4, obj.getCategorie().getId());
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
    public boolean update(Chambre obj) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_UPDATE);
            ps.setInt(1, obj.getnumChambre());     
            ps.setString(2, obj.getTelephone());   
            ps.setDouble(3, obj.getPrix());        
            ps.setInt(4, obj.getCategorie().getId());
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Chambre findById(int id) {
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FIND);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categorie categorie = new CategorieService().findById(rs.getInt("categorie_id"));
                return new Chambre(
                        rs.getInt("id"),
                        rs.getInt("numChambre"),   
                        rs.getDouble("prix"),
                        rs.getString("telephone"),
                        categorie
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Chambre> findAll() {
        List<Chambre> chambres = new ArrayList<>();
        try (
                PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FINDALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categorie categorie = new CategorieService().findById(rs.getInt("categorie_id"));
                chambres.add(new Chambre(
                        rs.getInt("id"),
                        rs.getInt("numChambre"), 
                        rs.getDouble("prix"),
                        rs.getString("telephone"),
                        categorie
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }
    
    
    public boolean isDuplicate(String field, String value) {
    String sql = "SELECT COUNT(*) AS count FROM chambre WHERE " + field + " = ?";
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
