package sk.uniza.fri;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Arrays;
import java.util.Comparator;

public class Graf {

     private static final int INFINITY = Integer.MAX_VALUE/2;
     private int n; //pocet vrcholov
     private int pocetHran;
     private int H[][]; // vrchool,vrchol,cena hrany
     private int x[];   // posledny vrchol
     private int t[];   // celkovej ceny hran

     private int k[];    // komponent
     private int kostra[][];

     public Graf(int pocetVrcholov, int pocetHran) {
         this.n = pocetVrcholov;
         this.pocetHran = pocetHran;
         this.H = new int[1 + this.pocetHran][3];
         this.x = new int[pocetVrcholov+1];
         this.t = new int[pocetVrcholov+1];

         this.k = new int[pocetVrcholov+1];
         this.kostra = new int[this.n - 1][3];
     }

     public static Graf nacitajGraf(String nazovSuboru) {
         int pocVrcholov = 1;
         int pocHran = 0;
         //zistujem pocet vrcholov a pocet hran
         try {
             Scanner sc = new Scanner(new File(nazovSuboru));

             while (sc.hasNextLine()) {
                 int u = sc.nextInt();
                 int v = sc.nextInt();
                 int c = sc.nextInt();

                 //nacital som hranu, zvysim pocet
                 pocHran++;

                 //skontrolujem ci netreba zvysit pocet vrcholov
                 if (pocVrcholov < u) pocVrcholov = u;
                 if (pocVrcholov < v) pocVrcholov = v;
             }
             sc.close();
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }

         //vytvorenie a naplnenie grafu (vrchol,vrchol,cena)
        Graf graf = new Graf(pocVrcholov,pocHran);
         try {
             Scanner sc = new Scanner(new File(nazovSuboru));
             for (int j = 1; j <= pocHran; j++) {
                 int u = sc.nextInt();
                 int v = sc.nextInt();
                 int c = sc.nextInt();

                 graf.H[j][0] = u;
                 graf.H[j][1] = v;
                 graf.H[j][2] = c;
             }
         } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
         }
         return graf;
     }

     public void printInfo() {
         System.out.println("Pocet vrcholov: " + this.n);
         System.out.println("Pocet hran: " + this.pocetHran);
     }


     public static void zoradHrany(int[][] array,final int stlpec) {        //stlpec ktory porovnávam (3. -> cena)
         Arrays.sort(array, new Comparator<int[]>() {
             @Override
             public int compare(int[] first, int[] second) {
                 if (first[stlpec - 1] < second[stlpec - 1]) {
                     return -1;
                 } else if (first[stlpec - 1] > second[stlpec - 1]) {
                     return 1;
                 } else {
                     return 0;
                 }
             }
         });
     }

     public void algoritmusKruskal() {

         this.zoradHrany(this.H,3);

//         kontrolny vypis zoradenych hran
//         for (int i = 0; i < this.pocetHran + 1; i++) {
//             System.out.println(this.H[i][2]);
//         }

         // naplnenie komponentov 0
         for (int i = 0; i < this.n + 1; i++) {
             this.k[i] = 0;
         }

         // vytvorenie komponentu
         for (int i = 1; i < this.pocetHran+1; i++) {
             int pom = H[i][0];
             int pom2 = H[i][1];
             if (this.k[pom] == 0) {
                 this.k[pom] = pom;
             }
             if (this.k[pom2] == 0) {
                 this.k[pom2] = pom2;
             }
         }

//         kontrolny vypis komponentov
//         for (int i = 0; i <= this.n; i++) {
//             System.out.println(this.k[i]);
//         }

         int pom = 0;   // uklada kolko hran som uz nasiel
         int i = 0;     // iterator
         int v1,v2,min,max;
         //pokial nenajdem vsetky hrany kostry -> pocetVrchoolov - 1
         while(pom != this.n-1) {
             v1 = this.H[i][0];                       //vyberiem vrcholy najlacnejsej hrany
             v2 = this.H[i][1];
            // ak maju rozne komponenty nevytvaraju cyklus -> pridavam do kostry
             if (this.k[v1] != this.k[v2] && this.k[v1] != 0) {     // vrchol s hodnotou 0 neexistuje
                 //pridanie do kostry
                 this.kostra[pom][0] = v1;
                 this.kostra[pom][1] = v2;
                 this.kostra[pom][2] = this.H[i][2];

                 min = Math.min(v1,v2);
                 max = Math.max(v1,v2);
                 //update komponentov
                 for (int j = 0; j < this.n + 1; j++ ) {
                     if (this.k[j] == max) {
                         this.k[j] = min;
                     }
                 }
                 pom++;
             }
             i++;
         }
     }

    public void vypisKostru() {
        for (int j = 0; j < this.n - 1; j++ ) {
            System.out.print("{" + this.kostra[j][0] + "," + this.kostra[j][1] + "} " + this.kostra[j][2] + " -> ");
        }
    }
}