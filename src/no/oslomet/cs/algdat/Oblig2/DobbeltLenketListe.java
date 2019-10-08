package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {


    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    private Node<T>finnNode(int indeks){
        Node<T> returnereNode; // Returnere noden med den gitte indeksen/posisjonen.

        // Dersom indeksen er mindre enn ​antall / 2, så ​skal letingen etter noden starte fra hode og
        // gå mot høyre ved hjelp av neste-pekere.
        if (indeks < antall/2){
            returnereNode = hode; // Starter fra hode
            for (int i = 0; i < indeks; i++){ // Går mot høyre ved hjelp av neste-pekere
                returnereNode = returnereNode.neste;
            }
        }
        // Hvis ikke, skal​ letingen starte fra halen og gå mot venstre ved hjelp av forrige-pekere.
        else{
            returnereNode = hale; // Starter fra halen
            for ( int i = antall - 1; i > indeks; i--){ // Går mot venstre ved hjelp av forrige-pekere
                returnereNode = returnereNode.forrige;
            }
        }
        return returnereNode;
    }

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;

    }

    public DobbeltLenketListe(T[] a) {
        this(); //standardkonstruktør

        //Tester om tabellen er null
        if (a == null) {
            throw new NullPointerException("Tabellen a er null!");
        }

        //
        hode = hale = new Node<>(null); // dette vil da være en midlertidig node

        for (T verdi : a) {
            if (verdi != null) { //hvis veriden til noden ikke er like null
                hale = hale.neste = new Node<>(verdi, hale, null); //ny node bakerst
                antall++; //antallet node med verdi blir telt
            }
        }

        if (antall == 0) { //hvis antall noder er 0
            hode = hale = null; //ingen hode og ingen hale, satt til null, finnes ingen verdier
        }
        else {
            (hode = hode.neste).forrige = null; // fjerner den midlertidige noden
        }
    }

    public Liste<T> subliste(int fra, int til){
        throw new NotImplementedException();
    }

    @Override
    public int antall() {

        antall = 0;

        for (Node n = hode; n != null; n = n.neste) {
            antall++;
        }
        return antall;
    }

    @Override
    public boolean tom() {

        boolean tom = false;

        if (antall == 0 && hode == null && hale == null) {
            return true;
        }
        else {
            return tom;
        }
    }

    @Override
    public boolean leggInn(T verdi) {

        if (verdi == null)
            throw new NullPointerException("Null verdier er ikke tillat!");

        if (antall == 0) {
            hode = hale = new Node<T>(verdi, null, null);  // tom liste
        }
        else {
            hale = hale.neste = new Node<T>(verdi, hale, null); // legges bakerst
            }

        //endringer++;              // burde ha med endringer, men vet ikk hva den skal brukes til
        antall++;                  // en mer i listen
        return true;               // vellykket innlegging
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);

        if (indeks == 0) {
            hode = new Node<T>(verdi, hode, null);;

            if (antall == 0) {
                hale = hode;
            }
        }

        else if (indeks == antall) {
            hale = hale.neste = new Node<T>(verdi, null, hale);;
        } //legges bakerst

        else {
            Node<T> node = hode;

            for (int i = 1; i < indeks; i++) {
                node.neste = new Node<T>(verdi, null, node.neste);
            }
            antall++;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        boolean tilstede = true;
        if(indeksTil(verdi) == -1){
            tilstede = false;
        }
        return tilstede;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false); // Bruker metoden indekskontroll () som blir arvet fra Liste,
        //  og bruker false som andre parameter i indekskontroll
        return finnNode(indeks).verdi; // Returnerer
    }

    @Override
    public int indeksTil(T verdi) {

        int m = 0; //Hjelpevariabel som viser indeks
        Node<T> j = hode;
        boolean finnesIkke = true;

        if(verdi != null) {

            while (j != null) {

                if (j.verdi.equals(verdi)) {
                    finnesIkke = false;
                    break; //Dersom verdien finnes skal lokken brytes
                } else {
                    j = j.neste;
                    m++;
                }
            }

        }
         else {
             m=-1;
        }

         if(finnesIkke){
             m=-1;
         }
        return m; // Retunerer indeksen eller eventuelt -1
    }


    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false); // Bruker metoden indekskontroll () som blir arvet fra Liste,
                                              //  og bruker false som andre parameter i indekskontroll

        //Null-verdier skal ikke kunne legges inn, og erstatter verdien på plass: indeks med "nyverdi".
        Objects.requireNonNull(nyverdi, "Null-verdi kan ikke legges inn");

        Node <T> returnereNode = finnNode(indeks); //Indeks sjekkes

        //Returnerer det som lå der før
        T verdi = returnereNode.verdi;
        returnereNode.verdi = nyverdi;

        endringer++; // Antall ednringer i listen økes

        return verdi; //Returnerer den gamle verdien, altså det som lå der før
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {


        for(Node<T> m = hode; m != null; m = m.neste) //Lokke som går igjennom alle verdiene i arrayet
        {
            Node<T> temp = m; //Hjelpe variabel til bruk for sletting

            temp.verdi = null;
            temp.neste = null;
            temp.forrige = null;

        }

        hode = null;
        hale = null;
        antall=0;
        endringer++;
    }

    @Override
    public String toString() {

        StringBuilder string = new StringBuilder();

        string.append('['); //legger til på starten av løkka

        if (!tom()) { //fra metoden i oppg 1, hvis ikke listen er tom, fortsetter den videre

            Node n = hode; //fra private Node lager vi n
            string.append(n.verdi); //i String legges verdi til node n fra lista

            n = n.neste; //henter neste node inn i stringbuilderen.

            while (n != null)  // tar med resten hvis det er resterende noder
            {
                string.append(',').append(' ').append(n.verdi); //legger til komma, mellomrom og node verdi i nye stringen
                n = n.neste; //går den til neste node, går gjennom while-løkke til det ikke er flere noder igjen
            }
        }

        string.append(']'); //legger til siste element i stringen

        return string.toString(); //returnerer stringen fra metoden tostring.
    }

    public String omvendtString() {
        StringBuilder string = new StringBuilder();

        string.append('['); //legger til på starten av løkka

        if (!tom()) { //fra metoden i oppg 1, hvis ikke listen er tom, fortsetter den videre

            Node n = hale; //fra private Node lager vi n, starter fra hale (siste node i linketliste)
            string.append(n.verdi); //i String legges verdi til node n fra lista

            n = n.forrige; //henter forrige node inn i stringbuilderen.

            while (n != null)  // tar med resten hvis det er resterende noder
            {
                string.append(',').append(' ').append(n.verdi); //legger til komma, mellomrom og node verdi i nye stringen
                n = n.forrige; //går tilbake til forrige node, går gjennom while-løkke til det ikke er flere noder igjen
            }
        }

        string.append(']'); //legger til siste element i stringen

        return string.toString(); //returnerer stringen fra metoden tostring.

    }

    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException();

    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            //instansvariabler for iterator
            denne = hode; //denne vil da være like hode (starter på første i liste)
            fjernOK = false; //når hasnext kalles vil fjernOK bli true;
            iteratorendringer = endringer; // teller endringer siden begge øker i remove() - metoden
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){

            if (!fjernOK) {
                throw new IllegalStateException("Ikke gyldig tilstand!");
            }
            else if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException("Antall endringer (" + endringer + ") er ikke lik iteratorEndringer (" + iteratorendringer + ")");

            }

            fjernOK = false;

            if (antall == 1) {
                //null ut hode og hale???
                hode = hale = null;
            }

            else if(denne == null) {
                //hale må oppdateres
                hale = hale.forrige;
                hale.neste = null;
            }

            else if (denne.forrige == hode) {
                //hode må oppdateres
                hode = hode.neste;
                hode.forrige = null;
            }
            else {
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }

            antall--;
            endringer++;
            iteratorendringer++;

        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

    public static void main(String[]  args) {

    }

} // class DobbeltLenketListe


