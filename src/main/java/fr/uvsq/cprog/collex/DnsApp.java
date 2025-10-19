package fr.uvsq.cprog.collex;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// classe principale de l'application DNS
public class DnsApp {
    private DnsTUI tui;
    private Dns dns;

    public DnsApp(String cheminFichier) {
        try {
            // initialisation du DNS et de l'interface utilisateur
            this.dns = new Dns(Path.of(cheminFichier));
            this.tui = new DnsTUI(dns);
        } catch (IOException e) {
            System.out.println("ERREUR : Impossible de charger le fichier DNS !");
            System.exit(1);
        }
    }

    public void run() {
        System.out.println("La simulation DNS a démarré !");
        while (true) {
            Commande cmd = tui.nextCommande(); // lit la prochaine commande de l'utilisateur
            if (cmd != null) {
                cmd.execute();              
            } else {
                System.out.println("Commande invalide !");
            }
        }
    }

    public static void main( String[] args ) throws IOException {
        Dns dns = new Dns(Paths.get("Dns.txt"));
        Com_ListerDomaine cmd = new Com_ListerDomaine(dns, "uvsq.fr");
        cmd.execute();  

        String fichierDNS = "Dns.txt"; 
        DnsApp app = new DnsApp(fichierDNS);
        app.run();
    }
}