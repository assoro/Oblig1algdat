package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;

        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
        endringer = 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Node<T> p = rot;    // p starter i roten
        Node<T> q = null;   // hjelpevariabel
        int cmp = 0;        // hjelpevariabel

        while (p != null) { // fortsetter til p er ute av treet
            q = p;                                // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);// bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;    // flytter p
        }

        p = new Node<>(verdi, q);  // q er forelder til ny node

        if (q == null) { // her setter vi p til rotnode
            rot = p;
        }
        else if (cmp < 0) { // hvis cmp er mindre enn null (sammenlikning), hvis inn node er mindre enn rotnoden/ forrige node
            q.venstre = p; // gå til venstre
            p.forelder = q; // forrie blir satt til forelder
        }

        else {
            q.høyre = p; // hvis inn node er større eller lik rotnode/ forrige node gå til høyre
            p.forelder = q; //forrige blir satt til forelder
        }

        antall++;     // øker ettersom en verdi blir lagt inn i treet
        endringer++;  // øker på endringer etter hver innleggelse

        return true;  // innleggingen var vellyket
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null)  //tester om treet inneholder verdi
            return false; //return false hvis ikke

        Node<T> p = rot; // setter rotnoden som p og starter ved rotnoden og går til venstre

        while (p != null) { //går videre i treet
            int cmp = comp.compare(verdi, p.verdi); //comparator som sammenligner verdien som komer inn med eksisterende verdi
            if (cmp < 0) p = p.venstre; //hvis ikke verdien er der fra før og den er mindre enn rotnoden// forrige node, skal den gå til venstre.
            else if (cmp > 0) p = p.høyre; //hvis den er lik eller større får den til høyre
            else return true; // return true hvis verdi er lagt til
        }

        return false; //false hvis treet inneholder verdi fra før
    }

    @Override
    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) {
            return false;  // treet har ingen nullverdier
        }

        Node<T> n = rot, m = null;   // m skal være forelder til n

        while (n != null)  // leter etter verdi
        {
            int cmp = comp.compare(verdi, n.verdi); // sammenligner

            if (cmp < 0) {
                m = n;
                n = n.venstre; // går til venstre
            } else if (cmp > 0) {
                m = n;
                n = n.høyre; // går til høyre
            } else break;    // den søkte verdien ligger i p
        }
        if (n == null)
            return false;   // finner ikke verdi

        if (n.venstre == null || n.høyre == null) { // Tilfelle 1) og 2)
            Node<T> b = n.venstre != null ? n.venstre : n.høyre;  // b er en verdi for barn

            if (n == rot)
                rot = b;

            else if (n == m.venstre) {
                m.venstre = b;
            } else m.høyre = b;

            if (b != null) {
                b.forelder = m;
            }
        } else  // Tilfelle 3)
        {
            Node<T> s = n, r = n.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            n.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != n) {
                s.venstre = r.høyre;
            }
            else {
                s.høyre = r.høyre;
                r.høyre.forelder = s;
            }
            r.forelder = s;
        }

      endringer++;
      antall--;   // det er nå én node mindre i treet
      return true;
    }

    public int fjernAlle(T verdi) {
        int antallfjernet = 0;

        while (fjern(verdi)) {
            antallfjernet++;
        }

        return antallfjernet;

    }

    @Override
    public int antall() {
        return antall;
    }

    public int antall(T verdi) {
        if (verdi == null || inneholder(verdi) == false) {
            return 0;
        }
        int antall = 0; // lager en teller
        int cmp = 0;
        Node<T> p = rot; // peker mot rotnode
        while (p != null) {

            cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) {
                p = p.venstre;
            } else {
                if (cmp == 0) {
                    antall++;
                }
                p = p.høyre;
            }

        }
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        if(!(tom())){

            int antall = antall();
            if(antall == 1){
                fjern(rot.verdi);
                endringer++;
            } else {
                Node<T> p = rot;
                while (p.venstre != null) {
                    p = p.venstre;
                }
                T verdi = p.verdi;

                for (int i = 0; i < antall; i++) {
                    verdi = p.verdi;
                    p = nesteInorden(p);
                    fjern(verdi);
                    endringer++;
                }
            }
        }
    }


    private static <T> Node<T> nesteInorden(Node<T> temp) {
      if (temp == null)
        return null;
      if (temp.høyre != null){
        temp= temp.høyre;

        while(temp.venstre != null)
          temp = temp.venstre;

        return temp;
      }
      Node y = temp.forelder;
      Node x = temp;
      while (y != null && x == y.høyre) {
        x = y;
        y = y.forelder;
      }
      return y;

    }

    @Override
    public String toString()
    {
      if (tom()) return "[]";                    // et tomt tre
      StringBuilder s = new StringBuilder();     // oppretter en StringBuilder
      s.append('[');                             // startparentes: [

      Node<T> p = rot;
      while (p.venstre != null) p = p.venstre;
      s.append(p.verdi);
      p = nesteInorden(p);
      while (p != null)
      {
        s.append(',').append(' ').append(p.verdi);   // legger inn p.verdi
        p = nesteInorden(p);

      }
      s.append(']');                             // avslutningsparentes: ]

      return s.toString();                       // returnerer tegnstrengen
    }

    public String omvendtString() {
            if (tom()) {
                return "[]";                    // et tomt tre
            }

            StringBuilder streng = new StringBuilder();
            Stack<Node<T>> stakk = new Stack<>();
            streng.append("[");

            Node<T> n = rot;

            while(n.venstre != null) {
                n = n.venstre;
            }

            for (int i = 0; i < antall; i++) {
                stakk.add(n);
                n = nesteInorden(n);
            }

            for (int i = 0; i < antall; i++) {
                streng.append(stakk.pop());

                if (i != (antall - 1)) {
                    streng.append(", ");
                }
            }

            streng.append("]");
            return streng.toString();

        }


    public String høyreGren() {

        //Bruker stringbuilder til å legge in grenens verdier i en tegnstreng
        StringBuilder streng = new StringBuilder();
        streng.append("[");

        //Definerer rotnoden i treet som n
        Node<T> n = rot;


        while (n != null) {  //Bruker while løkke til å sjekke at rotnoden eksisterer
            if (n == rot) {  //Hvis n r det samme som rotnoden...
                streng.append(n.verdi);  //legger til verdiene i strenger
            } else {
                streng.append(",").append(" ").append(n.verdi);  //legger til komma, mellomrom og verdiene.
            }

            if (n.høyre == null && n.venstre == null) { //hvis det ikke eksisterer verken høyre eller venstre
                break;
            }

            if (n.høyre != null) { //hvis hølyre ikke er null vil denne returnere verdiene til høyre
                n = n.høyre;
            } else { //ellers vil den returnere til venstre av treet
                n = n.venstre;
            }
        }

        streng.append("]"); //legger til siste klamme i strengen
        return streng.toString(); //skriver ut strengen med tostring
    }


    public String lengstGren() {

        if (rot == null) {
            return "[]";
        }

        //Lager en hjelpe-stack, til å bevege seg nedover treeet, og finne lengst vei
        ArrayDeque<Node<T>> stakk = new ArrayDeque<>();
        //Starter med rot-noden
        stakk.addFirst(rot);
        Node<T> n = rot;

        //Mens stakken IKKE er tom
        //Stacker opp verdier og fjerner alltid første(siste)verdi
        while (!stakk.isEmpty()) {
            n = stakk.removeLast();

            //Setter inn p i passende posisjon, om plassen er ledig eller ikke.
            if (n.høyre != null) {
                stakk.addFirst(n.høyre);
            }
            if (n.venstre != null) {
                stakk.addFirst(n.venstre);
            }
        }

        //hjelpeverdi- siste bladnode
        StringBuilder streng = new StringBuilder();
        ArrayDeque<Node<T>> stakk2 = new ArrayDeque<>();

        stakk2.addFirst(n);
        streng.append("[");

        while (n.forelder != null) {
            n = n.forelder;
            stakk2.addFirst(n);
        }

        streng.append(stakk2.pop());

        while (!stakk2.isEmpty()) {
            streng.append(", ").append(stakk2.pop());
        }
        streng.append("]");
        return streng.toString();

    }


    public String[] grener() {
        if(tom()) {
            return new String[0];
        }

        Liste<String> tabell = new TabellListe<>();
        StringBuilder streng = new StringBuilder();

        streng.append("[");


        if (!tom()) {
            grener(rot, tabell, streng);
        }

        String[] grener = new String[tabell.antall()];           // oppretter tabell

        int i = 0;
        for (String gren : tabell)
            grener[i++] = gren;                   // fra liste til tabell

        return grener;                          // returnerer tabellen
    }

    private void grener(Node<T> node, Liste<String> tabell, StringBuilder streng)
    {
        T verdi = node.verdi;

        int k = verdi.toString().length(); // lengden på verdi

        if (node.høyre == null && node.venstre == null){  // bladnode
            tabell.leggInn(streng.append(verdi).append(']').toString());

            // må fjerne det som ble lagt inn sist - dvs. k + 1 tegn
            streng.delete(streng.length() - k - 1, streng.length());
        }
        else {
            streng.append(node.verdi).append(',').append(' ');  // legger inn k + 2 tegn

            if (node.venstre != null){
                grener(node.venstre, tabell, streng);
            }
            if (node.høyre != null) {
                grener(node.høyre, tabell, streng);
            }

            streng.delete(streng.length() - k - 2, streng.length());   // fjerner k + 2 tegn
        }
    }

    public String bladnodeverdier() {
        if(tom()) return "[]";

        // Hjelpevariabler / stack
        ArrayDeque<Node<T>> stakk = new ArrayDeque<>();
        Node<T> p = rot;

        //Drar til nederste node - til venstre
        while (p.venstre != null){
            p = p.venstre;
        }

        //Sjekker om noden er en bladnode
        if(p.høyre == null && p.venstre == null){
            stakk.addLast(p);
        }

        //Itererer gjennom alle noder, og legger til bladnode(r) til hver gren,
        // og sjekker om de har nådd høyre(siste bladnode)
        //legger inn i stacken
        while (nesteInorden(p) != null){
            p = nesteInorden(p);
            if(p.venstre == null && p.høyre == null){
                stakk.addLast(p);
            }
        }

        //Legger til stack verdiene i en string
        String bladNode = "[";
        bladNode += stakk.pop();
        while (!(stakk.isEmpty())){
            bladNode += ", " + stakk.pop();
        }
        bladNode += "]";

        return bladNode;
    }

    public String postString() {StringBuilder sb = new StringBuilder();
        sb.append("[");

        if(!tom()){
            Node<T> p = rot;
            finnNodeIterativt(p, sb);
        }

        sb.append("]");

        return sb.toString();
    }

    private void finnNodeIterativt(Node<T> p, StringBuilder sb){
        if(p.venstre != null) finnNodeIterativt(p.venstre, sb);
        if(p.høyre != null) finnNodeIterativt(p.høyre, sb);
        if(p.venstre == null && p.høyre == null);

        sb.append(p.verdi);
        if(p != rot) sb.append(", ");

    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> n = rot, m = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator(){  // konstruktør
            //Dersom p er lik null så skjer det ingenting
            if (!tom()){
                while (n.venstre != null || n.høyre != null){
                    if (n.venstre != null){
                        n = n.venstre;
                    }
                    else{
                        n = n.høyre;
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return n != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Ingen fler bladnoder!");
            }

            else if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException("Endringer(" + endringer + ") != iteratorendringer(" + iteratorendringer + ")");
            }

            removeOK = true;

            m = n;
            T verdi = n.verdi;
            while(hasNext()) {
                n = nesteInorden(n);
                if (n == null)
                    return verdi;

                if (n.venstre == null && n.høyre == null) return verdi;
            }
            return verdi;
        }


        @Override
        public void remove() {
                if (!removeOK)
                    throw new IllegalStateException("Ikke lov å kalle på metoden!");

                removeOK = false;

                if (m.forelder == null)
                    rot = null;
                else
                if (m.forelder.venstre == m) m.forelder.venstre = null;
                else m.forelder.høyre = null;

                antall--;
                endringer++;
                iteratorendringer++;
            }




    } // BladnodeIterator

} // ObligSBinTre
