//Marianna Gawron

//program wyliczajacy k-ty najmniejszy element tablicy jednowymiarowej
// funkcja main wywoluje wejscie, ktore pobiera odpowiednie dane oraz wywoluje select, ktora zwraca szukana wartosc
// dzialanie wszystkich wykorzystywanych metod opisane nizej
// program ma zlozonosc liniowa, oraz stala zlozonosc pamieciowa

import java.util.Scanner;

public class Source {
    static int p=50;
    public static void swap(int[] tab, int a, int b){
        int temp = tab[a];
        tab[a] = tab[b];
        tab[b] = temp;
    }
    public static void printaArray(int[] tab){
        for(int i=0; i<tab.length; i++){
            System.out.print(tab[i]+" ");
        }
        System.out.println();
    }

    // sortuj sortuje metoda insertion sort rozwazana tablice ( o okreslonym poczatku oraz koncu)
    // i zwraca szukany element.
    public static int sortuj(int[] tab, int ktoryElement, int pocz, int kon){
        for(int i=pocz; i<kon-1; i++){
            int min = i;
            for(int j=i+1; j<kon;  j++){
                if(tab[j]<tab[min])
                    min=j;
            }
            swap(tab,min,i);
        }
        return tab[pocz+ktoryElement];
    }
    //partition dzialajacy podobnie do wersji lomuto (z drobmymi zmianami), dzieli rozwazana tablice wzgledem okreslonego pivota
    // zwraca poczatek i koniec przedzialy s2
    static int[] partition(int[] tab, int pocz, int kon, int piv ) {

        int i=pocz;
        int j = kon-1;
        int ileRownych=0;
        while(i<=j){
            if(tab[i]==piv) ileRownych ++;
            if(tab[i] > piv){
                swap(tab, i,j);
                j--;
            }
            else {
                int k = i;
                while (k>0 && tab[k-1] == piv){
                    swap(tab, k, k-1);
                    k--;
                }
                i++;
            }
        }
        int[] wynik = {j+1,j - ileRownych+1};
        return wynik;
    }

    //metoda dziala zgodnie z algorytmem opisanym na wykladzie odnoszacym sie do "magicznych piatek"
    // z median podtablic piecioelementowych wybieramy mediane (rekursywne wywolanie select)
    // nastepnie wywoluje partition dla odpowiednio rozwazanej tablicy, wzgldem wczesniej wyliczonej mediany median
    // dzieli ono rozwazana tablice na trzy czesci : (s1) mniejsze od piwota (s2) rowne pivotowi (s3) wieksze od pivota
    //sprawdzam czy szukany element znajduje sie w obrebie s1, s2 czy s3.
    // jezeli do s2 to zwracam wartosc s2
    // jezeli do s1 / s3 to wywoluje rekursywnie select dla odpowiednich tablic s1 lub s3
    public static int select(int[] tab, int ktoryElement, int pocz, int kon){
        if(ktoryElement >= kon ){
            System.out.println(ktoryElement+1 +" brak");
            return -1;
        }

        if(kon - pocz < p) {
            return sortuj(tab, ktoryElement, pocz, kon);
        }


        // przenosimy mediany piatek na przod tabelki
        int med = pocz+2;
        int i=pocz;
        while(med < (kon - pocz) - (kon - pocz)%5){
            swap(tab,i,med);
            med = med+5;
            i++;
        }
        if((kon - pocz) % 5 == 1) swap( tab,i, kon -1);// przesuwany mediane z tego ostatniego przedzialu
        if((kon - pocz) % 5 == 2) swap( tab,i, kon -2);
        if((kon - pocz) % 5 == 3) swap( tab,i, kon-2);
        if((kon - pocz) % 5 == 4) swap( tab,i, kon-3);

        int q = (kon - pocz)/5 +1; // gdy tab.length%5 == 0 to jest o jedno wiecej niz potrzeba ale to w zasadzie nie przeszkadza za bardzo

       // ustawiam pivot, partitiion wzgledem pivota
        int piv = select(tab, q/2, pocz,pocz+q);

        int[]pomocnicza = partition(tab,pocz,kon, piv);// zwraca o jeden wiekszy niz srodek podzialu
        int s2end = pomocnicza[0];
        int s2beg = pomocnicza[1];

        if(pocz+ktoryElement < s2beg) {
            return select (tab,  ktoryElement, pocz, s2beg); // rekurencja
        }
        if(pocz+ktoryElement < s2end){
            return tab[s2end-1];
        }

        return select(tab,  pocz + ktoryElement - s2end, s2end, kon);
    }

    public static void wejscie(){
        Scanner scan = new Scanner(System.in);
        int setCount = scan.nextInt();
        for( int i=0; i<setCount; i++){
            int n = scan.nextInt();
            int[] tab = new int[n];
            for(int j=0; j<n; j++){
                tab[j] = scan.nextInt();
            }
            int m= scan.nextInt();
            for(int j=0; j<m; j++){
                int szukanyElement = scan.nextInt();
                if(szukanyElement> tab.length || szukanyElement <1) System.out.println(szukanyElement + " brak");
                else {
                    int wynik = select(tab, szukanyElement - 1, 0, tab.length);
                    if (wynik != -1)
                        System.out.println(szukanyElement + " " + wynik);
                }
            }
        }
    }

    public static void main(String[] args) {
//        testSort();
//        testSelect2();
        wejscie();
//        testPartition();
    }
}


/*
*
*
 3
5
1 2 3 4 5
3
1 2 3
5
5 3 4 4 3
5
2 5 1 3 4
10
1 1 1 1 1 1 1 1 1 1
5
1 10 0 20 11
*
*
*
*
*     public static void testSort(){
        int[] tab = {32,43,23,55,6,6,4,21,34,6,31};
        printaArray(tab);
        System.out.println(sortuj(tab, 4,0, tab.length));
        printaArray(tab);
    }
    public static void testSelect(){
        int[] tab = {32,43,23,55,6,6,4,21,34,6,31};
        System.out.println("nowa tabela----------------------");
        printaArray(tab);
        System.out.println(select(tab, 4,0, tab.length));
        sortuj(tab, 4,0, tab.length);
        printaArray(tab);
    }

* public static void testSelect2(){
        int[] tab = {23,55,6,31,31,31,6,4,21,34,6,31};
        System.out.println("nowa tabela----------------------");
        printaArray(tab);
        System.out.println(select(tab, 9,0, tab.length));
        sortuj(tab, 4,0, tab.length);
        printaArray(tab);
    }
    public static void testPartition(){
        int[] tab = {32,43,23,55,4,21,34,6,31};
        printaArray(tab);
        int[] a =  partition(tab, 4,tab.length, 31);
        System.out.println(a[0] + "    "+ a[1]);
        printaArray(tab);
    }
    public static void testPartition2(){
        int[] tab = {23,55,6,31,31,31,6,4,21,34,6,31};
        printaArray(tab);
        int[] a =  partition(tab, 0,tab.length, 21);
        System.out.println(a[0] + "    "+ a[1]);
        printaArray(tab);
    }
    public static void testPartition3(){
        int[] tab = {1,1,1,1,1,1,1,1,1,1};
        printaArray(tab);
        int a =  sortuj(tab, 4,0, tab.length);
        System.out.println(a);
        printaArray(tab);
    }
* */