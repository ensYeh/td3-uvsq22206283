package fr.uvsq.cprog.collex;

public class Com_Quitter implements Commande{
    @Override
    public void execute() {
        System.out.println("Fin du programme !");
        System.exit(0);
    }
}