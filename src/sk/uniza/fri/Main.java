package sk.uniza.fri;

public class Main {

    public static void main(String[] args) {
        Graf g = Graf.nacitajGraf("C:\\Users\\garek\\Documents\\Skola\\ATG\\ATG_DAT\\ShortestPath\\SlovRep.hrn");
        g.printInfo();
        g.algoritmusKruskal();
        g.vypisKostru();
    }
}
