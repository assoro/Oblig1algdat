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

    //Metode finnNode
    private Node<T> finnNode(int indeks){

        Node<T> returnereNode = hode;
        /* Dersom indeksen er mindre enn ​antall / 2, så ​skal letingen etter noden starte fra hode og
        gå mot høyre ved hjelp av neste-pekere */
        if (indeks < antall / 2) {
            for (int i = 0; i < indeks; i++)  // Går mot høyre ved hjelp av neste-pekere
                returnereNode = returnereNode.neste;
        }
        // Hvis ikke, skal​ letingen starte fra halen og gå mot venstre ved hjelp av forrige-pekere.
        else{
            returnereNode = hale; // Starter fra halen
            for ( int i = antall - 1; i > indeks; i--) // Går mot venstre ved hjelp av forrige-pekere
                returnereNode = returnereNode.forrige;
        }
        return returnereNode; // Returnerer noden med den gitte indeksen/posisjonen.
    } //Slutt metode finnNode

    /* Hvis indeksene fra og til ikke er lovlige, så kastes det unntak i metoden fratilKontroll ().
    Legger metoden inn som en privat metode i klassen DobbeltLenketListe​ og bytter ut
    ArrayIndexOutOfBoundsException​ med ​IndexOutOfBoundsException​ siden vi ikke har noen tabell (array) her.
    Bytter også ut ordet ​tablengde med ordet antall. */

    //Metoden sjekker om intervallet [fra:til> er lovlig
    private void fratilkontroll (int antall, int fra, int til){

        if (til > antall){ // Til er utenfor tabellen
            throw new IndexOutOfBoundsException( "til(" +til+ ") > antall" +antall+ ")");
        }
        if (fra < 0){ //Fra er negativ
            throw new IndexOutOfBoundsException( "fra(" +fra+ ") < 0 er negativ");
        }
        if (fra > til){ //Fra er større enn til - Illegal
            throw new IllegalArgumentException( "fra(" +fra+ ") > til" +til+ ") illegalt intervall");
        }

    } //Slutt metode fratilKontroll

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
    // Lager metoden ​Liste<T> subliste(int fra, int til)
    public Liste<T> subliste(int fra, int til){

        // Lager en ny liste
        DobbeltLenketListe <T> subliste = new DobbeltLenketListe<>();

        // Sjekker om indeksene fra og til er lovlige på metoden fratilKontroll()
        fratilkontroll(antall, fra, til);

        Node<T> returnereNode = finnNode(fra);

        // Verdiene i intervallet [fra:til>
        for (int i = fra; i < til; i++){
            subliste.leggInn(returnereNode.verdi); // Bruker metoden leggInn()
            returnereNode = returnereNode.neste;

        }
        return subliste; //Metoden returnerer sublisten  som inneholder verdiene fra intervallet [fra:til>
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

       /* if (indeks < 0) {
            throw new IndexOutOfBoundsException("Indeks er negativ!");
        }
        else if (indeks > antall)
            throw new IndexOutOfBoundsException("<Indeks større enn antall");*/

       indeksKontroll(indeks, false);

        if (indeks == 0 && antall == 0) {
            hode = hale = new Node<T>(verdi, hode, null);

        }

        else if (indeks == antall) {
            hale = hale.neste = new Node<T>(verdi, null, hale);
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
        indeksKontroll(indeks, false); /*Bruker metoden indekskontroll () som blir arvet fra Liste,
                                              og bruker false som andre parameter i indekskontroll*/

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

        if(verdi==null){
            return false;
        }

        Node<T> returnereNode=hode;

        //finne verdien
        while (returnereNode != null){
            if (returnereNode.verdi.equals(verdi))break;

            returnereNode=returnereNode.neste;
        }

        if (returnereNode==null){
            return false; //finner ikke verdien
        }
        else if (antall==1){
            hode=hale=null;
        } else if (returnereNode == hode) {

            hode=hode.neste;
            hode.forrige=null;
        } else if (returnereNode==hale){
            hale=hale.forrige;
            hale.neste=null;
        } else {
            returnereNode.forrige.neste=returnereNode.neste;
            returnereNode.neste.forrige=returnereNode.forrige;
        }
        returnereNode.verdi=null; //resirk
        returnereNode.forrige=returnereNode.neste=null; //resirk

        antall--; //antall blir en mindre
        endringer++;    //Antall blir en større

        return true; //Suksessfull fjerning


       // throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);

        Node<T> returnereNode=hode;

        if (antall==1){ //Kun en node
            hode=hale=null;
        } else if (indeks==0){ //fjern første
            hode=hode.neste;
            hode.forrige=null;
        } else if (indeks==antall-1){//fjern siste
            returnereNode=hale;
            hale=hale.forrige;
            hale.neste=null;
        }
        else {
            returnereNode=finnNode(indeks); //hjelpemetode
            returnereNode.forrige.neste=returnereNode.neste;
            returnereNode.neste.forrige=returnereNode.forrige;
        }
        T verdi=returnereNode.verdi; //returneres
        returnereNode.verdi=null; //resirk
        returnereNode.forrige=returnereNode.neste=null; //resirk

        antall--;   //en mindre
        endringer++; //en større

        return verdi;
        //throw new NotImplementedException();
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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
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

                indeksKontroll(indeks, false);
                denne=finnNode(indeks);
                fjernOK=false;  //sant når next()kalles
                iteratorendringer=endringer; //endringer telles

        }

        @Override
        public boolean hasNext(){
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next(){
            if (denne==null)throw new NoSuchElementException("Ingen flere verdier!");

            if (iteratorendringer != endringer) throw new ConcurrentModificationException("Listen ble endret!");

            fjernOK=true;

            T verdi=denne.verdi; //tar vare på "denne"
            denne=denne.neste; //Flytter "denne" til neste

            return verdi;

        }

        @Override
        public void remove(){
            if (!fjernOK) { //Hvis ikke fjernOK blir kalt
                throw new IllegalStateException("Ikke gyldig tilstand!");
            }
            else if (endringer != iteratorendringer) { //Hvis ikke endringene er like
                throw new ConcurrentModificationException("Antall endringer (" + endringer + ") er ikke lik iteratorEndringer (" + iteratorendringer + ")");
            }

            fjernOK = false; //alltid false

            if (antall == 1) { //Hvis antall er lik 1, dvs den eneste noden som skal fjernes
                hode = hale = null; //nuller ut
            }

            else if(denne == null) { //Hvis den siste fjernes
                hale = hale.forrige; //Hale oppdateres slik at forrige node blir satt til neste hale
                hale.neste = null; //nuller ut den siste node som var hale før oppdatering.
            }

            else if (denne.forrige == hode) { //Hvis første fjernes
                hode = hode.neste; //må hode oppdateres
                hode.forrige = null; //nuller ut første node som var hode før oppdatering
            }
            else { //hvis node inne i listen fjernes, må pekere oppdateres
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }

            antall--; //reduseres fordi noder fjernes fra listen
            endringer++; //endringer i listen økes
            iteratorendringer++;


        }

    } // class DobbeltLenketListeIterator

    //Lager ​public static <T> void sorter(Liste<T> liste, Comparator <? super T> c)​metoden
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {

        //Hvis listen er tom, kastes det et unntak
        if (liste == null) {
            throw new IllegalArgumentException(" Tom liste");
        }

        //Lager en for-løkke
        for (int tall = liste.antall(); tall > 0; tall--) {
            Iterator<T> iterator = liste.iterator(); // Henter iteratoren fra listen

            int verdi = 0;
            T minsteverdi = iterator.next();

            for (int i = 1; i < tall; i++) {
                T storsteverdi = iterator.next();

                // Sammenligner minsteverdi og største verdi for å se om minsteverdi er mindre enn null
                if (c.compare(minsteverdi, storsteverdi) < 0) {
                    verdi = i;
                    minsteverdi = storsteverdi;
                }
            }
            liste.leggInn(liste.fjern(verdi)); //Fjerner verdien
        }
    }

    public static void main(String[]  args) {

            //Oppgave 1 - Metodene antall() og tom()
            Liste<String> liste = new DobbeltLenketListe<>();
            System.out.println(liste.antall() + " " + liste.tom());

            //Oppgave 1 - Konstruktr dobbeltlenketliste
            String[] s = {"Ole", null, "Per", "Kari", null};
            Liste<String> liste1 = new DobbeltLenketListe<>(s);
            System.out.println(liste1.antall() + " " + liste1.tom());

            //Oppgave 2a)
            String[] s1 = {}, s2 = {"A"}, s3 = {null, "A", null, "B", null};
            DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
            DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
            DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
            System.out.println(l1.toString() + " " + l2.toString()
                    + " " + l3.toString() + " " + l1.omvendtString() + " "
                    + l2.omvendtString() + " " + l3.omvendtString());

            //Oppgave 2b)
            DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>();
            System.out.println(liste2.toString() + " " + liste2.omvendtString());

            for (int i = 1; i <= 3; i++) {
                liste2.leggInn(i);
                System.out.println(liste2.toString() + " " + liste2.omvendtString());
            }

            //Oppgave 9 - Remove() metode
            DobbeltLenketListe<String> liste3 = new DobbeltLenketListe<>(new String[]{"Birger", "Lars", "Anders", "Bodil", "Kari", "Per", "Berit"});
            liste3.fjernHvis(navn -> navn.charAt(0) == 'B'); // fjerner navn som starter med B
            System.out.println(liste3 + " " + liste3.omvendtString());

    }

} // class DobbeltLenketListe


