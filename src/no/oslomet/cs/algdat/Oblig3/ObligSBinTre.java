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

        while (p != null) {
            q = p;                                // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);// bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;    // flytter p
        }
        p = new Node<>(verdi, q);  // q er forelder til ny node

        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;     // én verdi mer i treet
        endringer++;  // innlegging er en endring

        return true;  // vellykket innlegging
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
                stakk.add(n.høyre);
            }
            if (n.venstre != null) {
                stakk.add(n.venstre);
            }
        }

        //hjelpeverdi- siste bladnode
        StringBuilder streng = new StringBuilder();
        ArrayDeque<Node<T>> stakk2 = new ArrayDeque<>();

        stakk2.add(n);
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

        String[] tabell = new String[1];
        LinkedList<Node<T>> liste1 = new LinkedList<>();
        LinkedList<Node<T>> liste2 = new LinkedList<>();
        boolean falsk = false;

        Node<T> n = rot;
        int teller = 0;

        while(!falsk){
            StringJoiner string = new StringJoiner(", ","[","]");
            while(n.venstre != null || n.høyre != null){
                if(n.venstre != null) {
                    if(n.høyre != null) {
                        liste1.add(n.høyre);
                    }
                    n = n.venstre;
                } else {
                    n = n.høyre;
                }
            }
            while(n!=null) {
                liste2.add(n);
                n=n.forelder;
            }
            while(!liste2.isEmpty()){
                string.add(liste2.pollLast().toString());
            }
            if(tabell[tabell.length-1] != null){
                tabell = Arrays.copyOf(tabell, tabell.length+1);
            }
            tabell[teller++] = string.toString();

            if(!liste1.isEmpty()){
                n = liste2.pollLast();
            } else {
                falsk = true;
            }
        }
        return tabell;
    }

    public String bladnodeverdier() {
      if(antall == 0){
        return "[]";
      }
      StringJoiner sj = new StringJoiner(", ", "[", "]");
      Node<T> p = rot;
      while(p.venstre != null){
        p = p.venstre;
      }
      for(int i = 0; i < antall; i++){
        if((p.venstre == null) && (p.høyre == null)){
          sj.add(p.toString());
        }
        p = nesteInorden(p);
      }
      return sj.toString();

        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String postString() {
        Node<T> p = rot;
        Node<T> pn;

        if(tom()){
          return "[]";
        }

        StringJoiner sj = new StringJoiner(", ", "[", "]");

        while(p.høyre != null || p.venstre != null) {
          if(p.venstre == null){
            p = p.venstre;
          }

          else {
            p = p.høyre;
          }
        }

        while (p != null) {
            sj.add(p.toString());
        }

        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator(){  // konstruktør
            //Dersom p er lik null så skjer det ingenting
            if (p == null){
                return;
            }

            //Dersom p ikke er lik null
            if (p != null){
                while (p.venstre != null && p.høyre != null){
                    if (p.venstre != null){
                        p = p.venstre;
                    }
                    else{
                        p = p.høyre;
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            //Sjekker om det er flere noder igjen
            //Kaster en NoSuchElementException hvis det ikke er flere bladnoder igjen.
            // Hvis ikke, skal den returnere en bladnodeverdi.
            if(!hasNext()){
                throw new NoSuchElementException("Ingen flere noder igjen.");
            }
            //Sjekker om endringer er lik iteratorendriger
            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException("Det har blitt endret.");
            }

            removeOK = true; //Bladnoden kan trygt fjernes
            T verdi = p.verdi; //Holder veriden til p før p flyttes
            q = p;  //Setter q til det p er
            p = nesteInorden(p); //Flytter p

            return q.verdi; //Returnerer bladnodeverdi
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

    } // BladnodeIterator

} // ObligSBinTre
