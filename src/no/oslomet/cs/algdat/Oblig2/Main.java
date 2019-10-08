package no.oslomet.cs.algdat.Oblig2;


//Main metode for testin av utskrift til oppgavene i Oblig 2

import no.oslomet.cs.algdat.Oblig2.DobbeltLenketListe;
import no.oslomet.cs.algdat.Oblig2.Liste;

public class Main {
    public static void main(String[] args) {

        //Oppgave 1 - Metodene antall() og tom()
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());

        //Oppgave 1 - Konstruktr dobbeltlenketliste
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste1 = new DobbeltLenketListe<>(s);
        System.out.println(liste1.antall() + " " + liste1.tom());

        //Oppgave 2a)
        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());

        //Oppgave 2b)
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>();
        System.out.println(liste2.toString() + " " + liste2.omvendtString());

        for (int i = 1; i <= 3; i++)
        {
            liste2.leggInn(i);
            System.out.println(liste2.toString() + " " + liste2.omvendtString());
        }

        //Oppgave 9 - Remove() metode
        DobbeltLenketListe<String> liste3 = new DobbeltLenketListe<>(new String[] {"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});
        liste3.fjernHvis(navn -> navn.charAt(0) == 'B'); // fjerner navn som starter med B
        System.out.println(liste3 + " " + liste3.omvendtString());


    }
}
