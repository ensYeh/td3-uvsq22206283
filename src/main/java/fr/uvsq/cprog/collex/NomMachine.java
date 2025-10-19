package fr.uvsq.cprog.collex;

public class NomMachine {

    private String nomComplet; // par exemple: www.uvsq.fr
    private String nom;         // le nom seul: www
    private String domaine;     // le domaine seul: uvsq.fr

    public NomMachine(String nomComplet) {
        if (!nomComplet.contains(".")) {
            throw new IllegalArgumentException("Nom de machine invalide : " + nomComplet);
        }
        this.nomComplet = nomComplet;
        String[] parts = nomComplet.split("\\.", 2);
        this.nom = parts[0];
        this.domaine = parts[1];
    }

    public String getNom() {
        return nom;
    }

    public String getDomaine() {
        return domaine;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    @Override
    public String toString() {
        return nomComplet;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NomMachine)) return false;
        NomMachine other = (NomMachine) obj;
        return nomComplet.equals(other.nomComplet);
    }

    @Override
    public int hashCode() {
        return nomComplet.hashCode();
    }
}