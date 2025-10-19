package fr.uvsq.cprog.collex;
import java.util.Scanner;

// interface textuelle pour interagir avec le DNS
public class DnsTUI {
    private Dns dns;
    private Scanner scanner;

    public DnsTUI(Dns dns) {
        this.dns = dns;
        this.scanner = new Scanner(System.in);
    }

    // lit la prochaine commande utilisateur et la retourne sous forme d'objet Commande
    public Commande nextCommande() {
        System.out.print("> ");
        String ligne = scanner.nextLine().trim();
        
        // vérifie si l'utilisateur veut quitter
        if (ligne.equalsIgnoreCase("exit") || ligne.equalsIgnoreCase("quit")) {
            return new Com_Quitter();
        }
        String[] parts = ligne.split(" ");

        // analyse la ligne pour déterminer la commande appropriée
        try {
            if (parts[0].equalsIgnoreCase("add")) {
                AdresseIP ip = new AdresseIP(parts[1]);
                NomMachine nom = new NomMachine(parts[2]);
                return new Com_AjouterItem(dns, ip, nom);
            } else if (parts[0].equalsIgnoreCase("ls")) {
                boolean triParIP = parts.length > 1 && parts[1].equals("-a");
                String domaine = parts[triParIP ? 2 : 1];
                return new Com_ListerDomaine(dns, domaine); 
            } else if (ligne.matches("^(\\d{1,3}\\.){3}\\d{1,3}$")) {
                AdresseIP ip = new AdresseIP(ligne);
                return new Com_RechercheNom(dns, ip);
            } else if (ligne.contains(".")) {
                NomMachine nom = new NomMachine(ligne);
                return new Com_RechercheIP(dns, nom);
            }
        } catch (Exception e) {
            System.out.println("ERREUR : Commande invalide ou arguments incorrects !");
        }
        return null;
    }

    public void affiche(String message) {
        System.out.println(message);
    }
}