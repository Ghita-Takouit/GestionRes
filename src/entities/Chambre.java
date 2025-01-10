package entities;

import entities.Categorie;

public class Chambre {
    private int id;
    private double prix;
    private int numChambre;
    private String telephone;
    private Categorie categorie;

    public Chambre( int numChambre, double prix, String telephone, Categorie categorie) {
        this.telephone = telephone;
        this.categorie = categorie;
        this.numChambre = numChambre;
        this.prix = prix;
    }
    
     public Chambre(int id,  int numChambre, double prix, String telephone, Categorie categorie) {
        this.id = id;
        this.telephone = telephone;
        this.categorie = categorie;
        this.numChambre = numChambre;
        this.prix = prix;
    }


    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    public double getPrix(){
        return prix;
    }
    
    public void setPrix(double prix){
        this.prix = prix;
    }
    
    public int getnumChambre(){
        return numChambre;
    } 
    
    public void setnumChambre(int numChambre){
        this.numChambre = numChambre;
    }
    
    
    
    @Override
    public String toString() {
        return "Chambre : "+"\nId : "+id+"\nCategorie : "+categorie.toString()+"\nTelephone : "+telephone + "\n numero Chambre :" + numChambre +"\n Prix:"+ prix;
    }




}
