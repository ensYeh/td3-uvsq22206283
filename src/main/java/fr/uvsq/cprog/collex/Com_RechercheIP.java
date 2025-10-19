package fr.uvsq.cprog.collex;

// commande pour rechercher l'adresse IP associée à un nom de machine
public class Com_RechercheIP implements Commande{

    private Dns dns;
    private NomMachine nom;

    public Com_RechercheIP(Dns dns, NomMachine nom) {
        this.dns = dns;
        this.nom = nom;
    }

    @Override
    public void execute() {
        DnsItem item = dns.getItem(nom);
        if (item != null) {
            System.out.println(item.getAdresseIP());
        } else {
            System.out.println("ERREUR : Nom introuvable !");
        }
    }
}