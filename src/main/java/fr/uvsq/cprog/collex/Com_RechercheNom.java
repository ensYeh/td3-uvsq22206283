package fr.uvsq.cprog.collex;

// commande pour rechercher le nom de machine associé à une adresse IP
public class Com_RechercheNom implements Commande{
    private Dns dns;
    private AdresseIP ip;

    public Com_RechercheNom(Dns dns, AdresseIP ip) {
        this.dns = dns;
        this.ip = ip;
    }

    @Override
    public void execute() {
        DnsItem item = dns.getItem(ip);
        if (item != null) {
            System.out.println(item.getNomMachine().getNomComplet());
        } else {
            System.out.println("ERREUR : IP introuvable !");
        }
    }
}