package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Dns {
    private List<DnsItem> items;   // liste des entrées DNS
    private Path fichierDns;       // fichier texte contenant la base de données DNS


    // constructeur qui initialise la base de données DNS à partir d'un fichier
    public Dns(Path fichierDns) throws IOException {
        this.fichierDns = fichierDns;
        this.items = new ArrayList<>();
        chargerBase(); 
    }

    //  charge la base de données à partir du fichier texte
    private void chargerBase() throws IOException {
        if (!Files.exists(fichierDns)) {
            Files.createFile(fichierDns);
        }
        List<String> lignes = Files.readAllLines(fichierDns);
        for (String ligne : lignes) {
            if (!ligne.trim().isEmpty()) {
                String[] parts = ligne.split(" ");
                AdresseIP ip = new AdresseIP(parts[1]);
                NomMachine nom = new NomMachine(parts[0]);
                items.add(new DnsItem(nom, ip));
            }
        }
    }

    //  recupérer un item par nom de machine
    public DnsItem getItem(NomMachine nomMachine) {
        for (DnsItem item : items) {
            if (item.getNomMachine().equals(nomMachine)) {
                return item;
            }
        }
        return null; // si pas trouvé
    }

    // recupérer un item par adresse IP
    public DnsItem getItem(AdresseIP adresseIP) {
        for (DnsItem item : items) {
            if (item.getAdresseIP().equals(adresseIP)) {
                return item;
            }
        }
        return null; // si pas trouvé
    }

    // recupérer tous les items d’un domaine
    public List<DnsItem> getItems(String domaine) {
        List<DnsItem> result = new ArrayList<>();
        for (DnsItem item : items) {
            if (item.getNomMachine().getDomaine().equals(domaine)) {
                result.add(item);
            }
        }
        return result;
    }

    // ajouter un nouvel item

    public void addItem(AdresseIP ip, NomMachine nomMachine) throws IOException {
        // verification de l'unicité
        if (getItem(ip) != null) {
            throw new IllegalArgumentException("ERREUR : L'adresse IP existe déjà !");
        }
        if (getItem(nomMachine) != null) {
            throw new IllegalArgumentException("ERREUR : Le nom de machine existe déjà !");
        }

        DnsItem item = new DnsItem(nomMachine, ip);
        items.add(item);

        // mise à jour du fichier
        List<String> lignes = new ArrayList<>();
        for (DnsItem i : items) {
            lignes.add(i.getNomMachine().getNomComplet() + " " + i.getAdresseIP().getIp());
        }
        Files.write(fichierDns, lignes);
    }
}
