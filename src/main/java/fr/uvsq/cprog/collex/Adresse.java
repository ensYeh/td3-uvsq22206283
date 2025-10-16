package fr.uvsq.cprog.collex;

public class AdresseIP {

    private String ip; // stocke l'IP en chaîne de caractères

    public AdresseIP(String ip) {
        if (!estValide(ip)) {
            throw new IllegalArgumentException("Adresse IP invalide : " + ip);
        }
        this.ip = ip;
    }

    private boolean estValide(String ip) {
        // vérification que l'IP a 4 octets entre 0 et 255
        String regex = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
        if (!ip.matches(regex)) return false;

        String[] octets = ip.split("\\.");
        for (String octet : octets) {
            int val = Integer.parseInt(octet);
            if (val < 0 || val > 255) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ip;
    }

    public String getIp() {
        return ip;
    }
}