package fr.uvsq.cprog.collex;

// commande pour quitter le programme
public class Com_Quitter implements Commande{
    @Override
    public void execute() {
        System.out.println("Fin du programme !");
        System.exit(0);
    }
}