package service;

import conn.Connexion;
import dao.IDAO;
import entities.Categorie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IDAO<Categorie> {

    private final String SQL_INSERT = "INSERT INTO categorie (code, libelle) VALUES(?, ?)";
    private final String SQL_DELETE = "DELETE FROM categorie WHERE id=?";
    private final String SQL_UPDATE = "UPDATE categorie SET code=?, libelle=? WHERE id=?";
    private final String SQL_FIND = "SELECT * FROM categorie WHERE id=?";
    private final String SQL_FINDALL = "SELECT * FROM categorie";

    @Override
    public boolean create (Categorie obj){
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getCode());
            ps.setString(2, obj.getLibelle());
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
        public boolean update (Categorie obj){
            try {
                PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_UPDATE);
                ps.setString(1, obj.getCode());
                ps.setString(2, obj.getLibelle());
                ps.setInt(3, obj.getId());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean delete (int id){
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
    public Categorie findById(int id) {
        String query = "SELECT * FROM categories WHERE id = ?";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FIND);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("libelle")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        try  {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(SQL_FINDALL);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                categories.add(new Categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("libelle")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
















    }



