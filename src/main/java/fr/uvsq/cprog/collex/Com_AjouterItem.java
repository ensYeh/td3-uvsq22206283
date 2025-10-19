package fr.uvsq.cprog.collex;
import java.io.IOException;

// commande pour ajouter un nouvel item dans le DNS
public class Com_AjouterItem implements Commande{

    private Dns dns;
    private AdresseIP ip;
    private NomMachine nom;

    public Com_AjouterItem(Dns dns, AdresseIP ip, NomMachine nom) {
        this.dns = dns;
        this.ip = ip;
        this.nom = nom;
    }

    @Override
    public void execute() {
        // tente d'ajouter l'item au DNS et gère les exceptions
        try {
            dns.addItem(ip, nom);
            System.out.println("Machine ajoutée avec succès !");
        } catch (IOException e) {
            System.out.println("ERREUR : Impossible d’écrire dans le fichier !");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}