package fr.uvsq.cprog.collex;
import java.util.List;



// commande pour lister toutes les machines d'un domaine
public class Com_ListerDomaine implements Commande{
    private Dns dns;
    private String domaine;

    public Com_ListerDomaine(Dns dns, String domaine) {
        this.dns = dns;
        this.domaine = domaine;
    }

    @Override
    public void execute() {
        List<DnsItem> items = dns.getItems(domaine);
        if (items.isEmpty()) {
            System.out.println("Aucune machine dans le domaine " + domaine);
        } else {
            for (DnsItem item : items) {
                System.out.println(item);
            }
        }
    }
}
