package no.oslomet.cs.algdat.Oblig1;

////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

//Linda Kadrijaj, S333751, s333751@oslomet.no
//Poorani Nagendran s331411, s331411@oslomet.no
//Rahujan Ravindran s331399, s331399@oslomet.no
//Asso Rostampoor s330533, s330533@oslomet.no
//Mohammad Awais Hameed, s331375, s331375@oslomet.no

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static javax.swing.table.DefaultTableModel.gcd;
import static no.oslomet.cs.algdat.Oblig1.Oblig1.kvikksortering;


public class Oblig1 {

    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////hei
    public static int maks(int[] a) {
        
if (a.length < 1)
throw new java.util.NoSuchElementException("Tabellen a er tom!");

int m = 0; // hjelpeverdi til å sammenligne med

for (int i=1;i<a.length;i++)
{

 
if (a[m] > a[i]) 
bytt(a,i,m); // Tallene bytter plass
    
m++;
    }
    
 return a[a.length-1]; // returnerer den bakerste verdien
        
    }
    
public static void bytt(int[] a, int i, int j) {
int temp = a[i]; a[i] = a[j]; a[j] = temp; }

    public static int ombyttinger(int[] a) {
            if (a.length < 1)
throw new java.util.NoSuchElementException("Tabellen a er tom!");

int m = 0; 
int j = 0;//Hjelpeverdi som øker med 1 hver gang det foretas en ombygging

for (int i=1;i<a.length;i++)
{
    
if (a[m] > a[i])
    
{bytt(a,i,m);
    j++; }

// j oppdateres    

m++;
    }
    

    return j; // returnerer antallet ombyttinger
    }
    
    /*
Oppgave 1
   
I en tabell med n verdier, blir det foretatt n - 1 sammenligninger.

Det blir gjort flest ombyttinger 
når den største verdien forekommer først i arrayet.

Det blir gjort færrest ombyttinger 
når den største verdien forekommer sist i arrayet.
    
For å finne gjennomsnittet av ombyttinger
må en først finne antallet permutasjoner. Arrayet "liste" kan settes opp på
4!=24 permutasjoner. Fra 1.1.6 i kompendiet har vi at formelen for gjennomstnitt 
kan uttrykkes slik; 1/2 + 1/3 + 1/n. Vi får dermed 6/12 + 4/12 + 3/12 = 13/12.
Gjennomsnittlig foretas altså 13/12 eller ca 1 ombyttinger i arrayet.
Videre kan en si at denne funksjonen er mer effektiv enn maks metodene vi har sett på før, 
ettersom den dominerende operasjonen utføres mindre.
    
*/

    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a) {
        
        for (int i=1;i<a.length;i++) 
    if (a[i-1] > a[i]) 
    throw new IllegalStateException("Tabellen er ikke sortert i stigende rekkfølge");

    int k =0;//Hjelpevariabel som teller antallet ulike verdier
 
     for(int i=0;i<a.length;i++){
            boolean lik = false;//boolean-variabel som settes til true når to verdier er like 
            for(int j=0;j<i;j++){
                if(a[i] == a[j]){
                    lik = true;
                    break;
                }
            }
            
            if(!lik){ //Dersom to verdier ikke er like skal k økes med 1
                
               k++; 
            }
                
   
    };
 
 return k;//Returnerer k
        
    
    }


    ///// Oppgave 3 //////////////////////////////////////hei
    public static int antallUlikeUsortert(int[] a) {

        //throw new NotImplementedException();


                int k =0;//Hjelpevariabel som teller antallet ulike verdier

                for(int i=0;i<a.length; i++){
                    boolean lik = false;//boolean-variabel som settes til true når to verdier er like
                    for(int j=0;j<i;j++){
                        if(a[i] == a[j]){
                            lik = true;
                            break;
                        }
                    }

                    if(!lik){ //Dersom to verdier ikke er like skal k økes med 1

                        k++;
                    }


                };

                return k;//Returnerer k
            };


    ///// Oppgave 4 //////////////////////////////////////

    //Lager metoden public static void delsortering (int[] a)

    public static void delsortering(int[] a) {

        //throw new NotImplementedException();

        //Indeksene som er nå fra høyre og venstre
        //venstre = 0, right = a.length - 1
        int venstre = 0;
        int right = a.length - 1;

        for (int i = 0; i < a.length; i++) { //Lager en for-løkke, og starter med i = 1

            //Finner partallene fra høyre siden
            while ((right >= 0) && (a[right] & 1) == 0) {
                right--;
            }

            //Finner oddetallene fra venstre siden
            while ((venstre < a.length) && (a[venstre] & 1) != 0) {
                venstre++;
            }
            while (true){
                if (venstre < right){
                    //Bytter om plassene
                    int temp = a[venstre];
                    a[venstre] = a[right];
                    a[right] = temp;
                    venstre++;
                    right--;
                }
                else break;
                while ((a[right] & 1) == 0) right --;
                while ((a[venstre] & 1) != 0) venstre++;
            }

        }
         //Sorterer partallene i stigende rekkefølge
        kvikksortering(a, venstre, a.length);

        //Sorterer oddetallene i stigende rekkefølge
        kvikksortering(a, 0, venstre);
    }

    //Bytter om plassene
    public static void bytte_plass( int [] a, int venstre, int right){
        int temp = a[venstre];
        a[venstre] = a[right];
        a[right] = temp;
    }

    //Lager en privat metode for kvikksortering
    private static void kvikksortering_private(int[] a, int venstre, int right){
        if(venstre >= right){
            return;//Blir returnert dersom venstre er større eller lik hoyre(right)
        }
        //Bruker metoden sParter, og midtverdien
        int midtverdi = sParter(a, venstre, right, ((venstre + right)/2));
        kvikksortering_private(a, midtverdi + 1, right); //Intervallet a[midtverdi+1:h] blir sortert ([v:h])
        kvikksortering_private(a, venstre, midtverdi - 1 ); //Intervallet a[v:midtverdi-1] blir sortert ([v:h])
    }

    //Lager en metode for kvikksorteing
    public static void kvikksortering (int[] a, int fra_venstre, int til_right){ //venstre = fra, høyre = til - 1
        kvikksortering_private(a, fra_venstre, til_right - 1); //fra_venstre = venstre, til_høyre - 1 = høyre
    }

    //parter metode
    private static int parter (int [] a, int venstre, int right, int skilleverdi){
        //Når (venstre >=  right) så stopper det
        while (true){
            while (venstre <= right && a[right] >= skilleverdi){
                right--; //venstre er stoppeverdi for høyre
            }
            while (venstre <= right && a[venstre] < skilleverdi) {
                venstre++; //right er stoppeverdi for venstre
            }

            //bytter om a[h] og a[v]
            if (venstre < right){
                bytte_plass(a, venstre++, right--);
            }
            else{
                return venstre;
            }
        }
    }
    //sParter metode
    public static int sParter(int[] a, int venstre, int right, int indeks) {
        bytte_plass(a,indeks, right); //Bytter skilleverdien a[indeks] helt bak
        int posisjon = parter(a, venstre, right - 1, a[right]); //Sepererer a[v:h-1]
        bytte_plass(a, posisjon, right);  //Bytter skilleverdien på rett plass
        return posisjon; //Skilleverdien sin posisjon blir levert
    }


    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {
        //throw new NotImplementedException();
        if (a.length>1){
            char temp = a[a.length-1];
            for (int i=a.length-1; i>0; i--){
                a[i]= a[i-1];
            }
            a[0]=temp;
        }
    }

    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int b) {
        int aLengde = a.length;

        //sjekker om lengden på a er mindre enn 2,
        //hvis den er det vil ingen rotasjon skje
        if (aLengde < 2) {
            return;
        }

        //sjekker om verdien d i indeks er negativt
        if ((b %= aLengde) < 0) {
            b = b + aLengde;
        }

        //hvis vi ønker a rotere et veldig stort tall
        //bruker man som regel gcd for å finne hvor mange steg vi skal flytte på.
        int gcd = 1;
        for(int i = 1; i <= aLengde && i <= b; ++i) {

            // Checks if i is factor of both integers
            if (aLengde % i == 0 && b % i == 0) {
                gcd = i;
            }
        }
        int antallFlytt = gcd;

        //i for - løkn bruker vi gcd til rotasjonen
        for (int k = 0; k < antallFlytt; k++) {
            char variabel = a[k];

            for (int i = k - b, j = k; i != k; i = i - b){


                if (i < 0) {
                    //sjekker fortegnet til i
                    i = i + aLengde;

                    //kopierer j
                    a[j] = a[i];

                    //oppdaterer j
                    j = i;
                }
            }
            //legger tilbak verdien i den midlertidige variabelen vi definerte først
            a[k + b] = variabel;
        }
    }

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        if (s == "" && t == "") {
            System.out.println("Tomme strenger!");
        }

        int minste = Math.min(s.length(), t.length());
        StringBuilder flettet = new StringBuilder();

        for (int i = 0; i < minste; i++){
            char[] a1 = s.toCharArray();
            char[] a2 = t.toCharArray();
            flettet.append(a1[i]).append(a2[i]);
        }

        flettet.append(s.substring(minste)).append(t.substring(minste));

        return flettet.toString();
    }


    /// 7b) Hei
    public static String flett(String... s) {

        //definert en tom (tilgjengelig) streng
        String flett = "";

        //sjekker om parameterstrengen er tom
        if (s.length!=0) {
            int sLengde = s[0].length();

            //finner den lengste verdien
            for (int i = 0; i < s.length-1; ++i) {

                if (s[i].length() <= s[i+1].length()) {
                    sLengde += s[i+1].length();
                }
            }

            //dobbel for-løkke for å legge til verdi fra hver streng til alle strengene er tomme
            for (int i = 0; i < sLengde; ++i) {
                for (int j = 0; j < s.length; ++j) {
                    if (s[j].length() > i) {
                        flett+= s[j].charAt(i);
                    }
                }
            }
        }
        //returnerer strengen vi definerte som ny streng fra parameterstrengene x antall
        return flett;

    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {

        //throw new NotImplementedException();

        int [] index = new int[a.length];
        int [] b = new int [a.length];

        for (int i=0; i<a.length; i++){
            a[i] = a[i];
            index[i]= i;
        }

        for (int i = 0; i < a.length - 1; i++)
        {
            int m = i;             // indeks til den minste (føreløpig)
            int  minverdi = a[i];  // verdien til den minste(føreløpig)

            for (int j = i + 1; j < a.length; j++)
            {
                if (a[j] < minverdi)
                {
                    minverdi = a[j];  // ny minste verdi
                    m = j;            // indeksen til ny minste verdi
                }
            }
            // bytter om a[i] og a[m]
            int temp = a[i];
            a[i] = a[m];
            a[m] = temp;

            int temp1 = index[i];
            index[i] = index[m];
            index[m] = temp1;

        }

        return index;

    }


    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {

        if(a.length<3) throw new NoSuchElementException(" Tabellen har mindre enn 3");
        int tabell = a.length; //tabellens lengde


        int [] returnerArray = {0,1,2};
        returnerArray = indekssortering(returnerArray);

        //Tre hjelpevariabler
        int m = returnerArray[0]; //minste verdi
        int nm = returnerArray[1]; //nest minste verdi
        int tm = returnerArray[2]; //tredje minste verdi

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
        //Returnerer m i posisjon 0, nm i posisjon 1, og tm i posisjon 2
        return new int[] {m, nm, tm};
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static int bokstavNr(char bokstav) {
        if (bokstav <= 'Z') return bokstav - 'A';  // A=0,B=1,C=2, osv.
        else if (bokstav == 'Ø') return 27;        // Ø=27
        else if (bokstav == 'Å') return 28;        // Å=28
        else return 26;                            // Æ=26
    }

    public static boolean inneholdt(String a, String b)  // 1. forslag
    {
        if (a.length() > b.length()) return false;  //a kan ikke ha flere bokstaver

        int[] antall = new int[29];  //29 bokstaver
        for (int i = 0; i < a.length(); i++) antall[bokstavNr(a.charAt(i))]++;
        for (int i = 0; i < b.length(); i++) antall[bokstavNr(b.charAt(i))]--;
        for (int i = 0; i < antall.length; i++) if (antall[i] > 0) return false;

        return true;
    }

    public static boolean inneholdt2(String a, String b)  // 2. forslag
    {
        if (a.length() > b.length()) return false; // a kan ikke ha flere bokstaver

        int[] antall = new int[256];

        int n = a.length(), m = b.length();

        for (int i = 0; i < n; i++) antall[a.charAt(i)]++;  //opp
        for (int i = 0; i < m; i++) antall[b.charAt(i)]--;  //ned

        for (int i = 0; i < antall.length; i++) if (antall[i] > 0) return false;

        return true;

    }

}  // Oblig1
