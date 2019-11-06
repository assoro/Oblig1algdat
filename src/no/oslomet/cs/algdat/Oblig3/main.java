package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class main {
    public static void main(String[] args) {

        //oppgave3
           /* int[] a = {4,7,2,9,4,10,8,7,4,6,1};
            ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
            for (int verdi : a) {
                tre.leggInn(verdi);
            }
        System.out.println(tre);*/

           //Oppgave6
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
        for (char c : verdier) tre.leggInn(c);
        System.out.println(tre.høyreGren());


    }
}
