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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String[] grener() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()  // konstruktør
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext() {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

    } // BladnodeIterator

} // ObligSBinTre
