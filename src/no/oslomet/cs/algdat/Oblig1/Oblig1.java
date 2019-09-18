package no.oslomet.cs.algdat.Oblig1;

////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
        //venstre = 0, right = tall - 1
        int venstre = 0;
        int right = a.length - 1;



        int oddeTall = 0; //Antall oddetall
        int parTall = 0; //Antall partall

        for (int i = 1; i < a.length; i++) { //Lager en for-løkke, og starter med i = 1

            if (parTall == 0 || oddeTall == 0) {
                kvikksortering_private(a, 0, a.length - 1);
            }
            //finner partallene fra høyre side
            if ((a[i] % 2) == 0) {
                right++;
                parTall++;
            }
            //finner oddetallene fra venstre side og bytter om plassene
            else {
                //Bytter om plassene
                int temp = a[venstre];
                a[venstre] = a[right];
                a[right] = temp;
                venstre++;
                oddeTall++;
            }

        }
            //Sorterer partallene i stigende rekkefølge
            kvikksortering(a, oddeTall, a.length);

            //Sorterer oddetallene i stigende rekkefølge
            kvikksortering(a, 0, oddeTall);
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
            while (venstre <= right && a[venstre] >= skilleverdi) {
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


        //Sorterer verdiene for å vite hvem som er minst
        int [] c = new int [3];


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
        //Returnerer m i posisjon 0, nm i posisjon 1, og tm i posisjon 2
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
