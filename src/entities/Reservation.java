package entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Reservation {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private List<Client> clients;
    private List<Chambre> chambres;

    private static int count = 0;

    public Reservation(Date dateDebut, Date dateFin, List<Client> clients, List<Chambre> chambres){
        this.id = count++;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.clients=new ArrayList<>(clients);
        this.chambres=new ArrayList<>(chambres);
    }


    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = new ArrayList<>(clients);
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(List<Chambre> chambres) {
        this.chambres = new ArrayList<>(chambres);
    }

    public int getId(){
        return this.id;
    }

    public int getCount(){
        return count;
    }

















}
