package fr.uvsq.cprog.collex;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.Scanner;

public class SimulationDnsTest {

    // ---------------------------
    // AdresseIP
    // ---------------------------
    @Test
    public void testAdresseIPValideToString() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        assertEquals("192.168.0.1", ip.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseIPRefuseOctetTropGrand() {
        new AdresseIP("256.0.0.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseIPRefuseOctetNegatif() {
        new AdresseIP("-1.0.0.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseIPRefuseFormatIncomplet() {
        new AdresseIP("192.168.1"); // seulement 3 octets
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseIPRefuseNonNumerique() {
        new AdresseIP("192.abc.0.1");
    }

    @Test
    public void testAdresseIPEgaliteHashCode() {
        AdresseIP a = new AdresseIP("10.0.0.1");
        AdresseIP b = new AdresseIP("10.0.0.1");
        AdresseIP c = new AdresseIP("10.0.0.2");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    // ---------------------------
    // NomMachine
    // ---------------------------
    @Test
    public void testNomMachineValideGettersToString() {
        NomMachine nom = new NomMachine("www.uvsq.fr");
        assertEquals("www.uvsq.fr", nom.getNomComplet());
        assertEquals("www", nom.getNom());
        assertEquals("uvsq.fr", nom.getDomaine());
        assertEquals("www.uvsq.fr", nom.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomMachineRefuseSansPoint() {
        new NomMachine("localhost");
    }

    @Test
    public void testNomMachineEgaliteHashCode() {
        NomMachine a = new NomMachine("api.uvsq.fr");
        NomMachine b = new NomMachine("api.uvsq.fr");
        NomMachine c = new NomMachine("www.uvsq.fr");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    // ---------------------------
    // DnsItem (constructeur: NomMachine, AdresseIP)
    // ---------------------------
    @Test
    public void testDnsItemConstructionGetters() {
        NomMachine nom = new NomMachine("www.uvsq.fr");
        AdresseIP ip = new AdresseIP("193.51.31.90");
        DnsItem item = new DnsItem(nom, ip);

        assertEquals(nom, item.getNomMachine());
        assertEquals(ip, item.getAdresseIP());
    }

    // ---------------------------
    // DnsTUI : tests de PARSING uniquement, sans exécuter les commandes ni toucher Dns
    // ---------------------------

    /** Remplace le Scanner de DnsTUI par un Scanner mémoire. */
    private static void injecterScanner(DnsTUI tui, String input) throws Exception {
        Field f = DnsTUI.class.getDeclaredField("scanner");
        f.setAccessible(true);
        f.set(tui, new Scanner(input));
    }

    @Test
    public void testTUICommandeQuitEtExit() throws Exception {
        DnsTUI tui = new DnsTUI(null); // dns null: OK, on ne fait pas execute()
        injecterScanner(tui, "exit\n");
        Commande c1 = tui.nextCommande();
        assertNotNull(c1);
        assertTrue(c1 instanceof Com_Quitter);

        injecterScanner(tui, "quit\n");
        Commande c2 = tui.nextCommande();
        assertNotNull(c2);
        assertTrue(c2 instanceof Com_Quitter);
    }

    @Test
    public void testTUICommandeAdd() throws Exception {
        DnsTUI tui = new DnsTUI(null);
        injecterScanner(tui, "add 1.2.3.4 a.b\n");
        Commande cmd = tui.nextCommande();
        assertNotNull(cmd);
        assertTrue(cmd instanceof Com_AjouterItem);
    }

    @Test
    public void testTUICommandeLsEtLsAvecOptionA() throws Exception {
        DnsTUI tui = new DnsTUI(null);

        injecterScanner(tui, "ls uvsq.fr\n");
        Commande c1 = tui.nextCommande();
        assertNotNull(c1);
        assertTrue(c1 instanceof Com_ListerDomaine);

        injecterScanner(tui, "ls -a uvsq.fr\n");
        Commande c2 = tui.nextCommande();
        assertNotNull(c2);
        assertTrue(c2 instanceof Com_ListerDomaine);
    }

    @Test
    public void testTUICommandeIpEtFqdn() throws Exception {
        DnsTUI tui = new DnsTUI(null);

        injecterScanner(tui, "193.51.31.90\n");
        Commande c1 = tui.nextCommande();
        assertNotNull(c1);
        assertTrue(c1 instanceof Com_RechercheNom);
        
        injecterScanner(tui, "www.uvsq.fr\n");
        Commande c2 = tui.nextCommande();
        assertNotNull(c2);
        assertTrue(c2 instanceof Com_RechercheIP);
    }

    @Test
    public void testTUICommandeInvalideRetourneNull() throws Exception {
        DnsTUI tui = new DnsTUI(null);
        injecterScanner(tui, "commande_inconnue arg1 arg2\n");
        Commande c = tui.nextCommande();
        assertNull(c);
    }
}
