package entities;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String Telephone;
    private String email;


    public Client(int id, String nom, String prenom, String Telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.Telephone = Telephone;
        this.email = email;
    }

    public Client( String nom, String prenom, String Telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.Telephone = Telephone;
        this.email = email;
    }

   


    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getTelephone() {
        return Telephone;
    }
    public String getEmail() {
        return email;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return ""+nom;
    }

}
