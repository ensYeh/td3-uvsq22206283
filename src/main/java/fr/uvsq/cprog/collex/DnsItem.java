package fr.uvsq.cprog.collex;

public class DnsItem {

    private NomMachine nomMachine; // NomMachine fait référence à la classe NomMachine
    private AdresseIP adresseIP;  // AdresseIP fait référence à la classe AdresseIP

    public DnsItem(NomMachine nomMachine, AdresseIP adresseIP) {
        this.nomMachine = nomMachine;
        this.adresseIP = adresseIP;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }

    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    @Override
    public String toString() {
        return getAdresseIP() + " " + getNomMachine();
    }
}