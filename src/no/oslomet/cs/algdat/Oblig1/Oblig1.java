package no.oslomet.cs.algdat.Oblig1;

////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class Oblig1 {

    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////
    public static int maks(int[] a) {
        throw new NotImplementedException();
    }

    public static int ombyttinger(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a) {
        throw new NotImplementedException();
    }


    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 4 //////////////////////////////////////hei

    //Lager metoden public static void delsortering (int[] a)
    //Sorterer tabellen a
    public static void delsortering(int[] a, int tall) {

        //throw new NotImplementedException();

        Arrays.sort(a); //Sorterer tabellen i stigende rekkefølge

        //Indeksene som er nå fra høyre og venstre
        //venstre = 0, høyre = tall - 1
        int venstre = 0;
        int høyre = tall - 1;

        int oddeTall = 0; //Antall oddetall
        int antall = 0; //Antall delsorteringer

        for (int i = 1; i < a.length; i++) { //Lager en for-løkke, og starter med i = 1
            if ((a[i] & 1) != 0) //Hvis a[i] for alle array elementene og 1 er ikke lik 0
                antall++; //Teller opp delsorteringen

            //Finner partallene fra høyre siden
            while ((venstre < høyre) && a[høyre] % 2 == 0) {
                høyre--;
            }

            //Finner oddetallene fra venstre siden
            while (a[venstre] % 2 != 0) {
                oddeTall++;
                venstre++;
            }

            //Bytter om a[høyre] og a[venstre]
            //Bytter alle  oddetallene til venstre side og alle partallene til høyre
            if (venstre < høyre) {
                int temp = a[venstre];
                a[venstre] = a[høyre];
                a[høyre] = temp;
            }
        }
        //Sorterer partallene i stigende rekkefølge
        Arrays.sort(a, oddeTall, tall);

        //Sorterer oddetallene i stigende rekkefølge
        Arrays.sort(a, 0, oddeTall);
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int k) {
        throw new NotImplementedException();
    }

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        throw new NotImplementedException();
    }

    /// 7b)
    public static String flett(String... s) {
        throw new NotImplementedException();
    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {
        throw new NotImplementedException();
    }


    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {

        int tabell = a.length; //tabellens lengde
        //minst tre verdi
        if (tabell < 3 ) throw new IllegalArgumentException("a.length(" + tabell + ") < 3!");

        //Tre hjelpevariabler
        int m = 0; //minste verdi
        int nm = 1; //nest minste verdi
        int tm = 2; //tredje minste verdi

        //Tre hjelpevariabler
        int minst = a[m];
        int nestminst = a[nm];
        int tredjeminst = a[tm];

        for (int i = 3; i < tabell; i++) { ////Lager en for-løkke
            if (a[i] < tredjeminst) {
                if (a[i] < nestminst) {
                    if (a[i] < minst) {
                        tredjeminst = nestminst;
                        nestminst = minst;
                        minst = i;
                    }
                }
            }
        }
        //Returnerer m i psoisjon 0, nm i posisjon 1, og tm i posisjon 2
        return new int[] {m, nm, tm};
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static int bokstavNr(char bokstav) {
        throw new NotImplementedException();
    }

    public static boolean inneholdt(String a, String b) {
        throw new NotImplementedException();
    }

}  // Oblig1
