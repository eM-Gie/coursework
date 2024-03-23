//Marianna Gawron - 1
import java.util.Scanner;

public class Source {

//zamienia miejscami odpowiednie elementy tabeli
    public static void swap(String[] tab, int a, int b){
        String temp = tab[a];
        tab[a] = tab[b];
        tab[b] = temp;
    }
// porownuje konkretne slowo z potencjalnym prefixem
    public static String findPrefix(String prefix, String word){
        String prefix2 = "";
        for(int j=0; j<word.length() && j<prefix.length(); j++)
            if(prefix.charAt(j)==word.charAt(j)) {
                prefix2 += prefix.charAt(j);
            }else {
                return prefix2;
            }
        return prefix2;
    }

    // uklada elementy tabeli w podpowiednim porzadku.
    // zaleznie od tego czy ilosc elemntow jest parzysta/ nieparzysta funkcja zamienia odpowiednie "od srodka" miejsca w tabeli
    // nastepnie rekursywnie (zgodnie z idea metody dziel i zwyciezaj) tasuje gorna czesc i golna czesc,
    // az nie dojdzie do sytuacji w ktorej w rozwazanym przedziale tabeli znajduja sie jeden element
    // zlozonosc: n log n

    public static String tasuj(String[] tab, int beg, int end, String prefix){
       if(end - beg>0) {
           int n = end - beg;
           int mid;

           if (n % 2 == 0 && n>2) {
               mid = n / 2;
               if (mid % 2 == 0) {
                   int rad = mid / 2;
                   for (int i = 0; i < rad; i++) {
                       prefix = findPrefix(prefix, tab[beg + mid + i]);
                       prefix = findPrefix(prefix, tab[beg + mid - rad + i]);
                       swap(tab, beg + mid + i, beg + mid - rad + i);
                   }
                   prefix = tasuj(tab, beg, beg + mid,prefix);
                   prefix = tasuj(tab, beg + mid, end,prefix);
               } else { //mid%2 == 1
                   int rad = mid / 2;
                   int i = 0;
                   while (i < rad) {
                       prefix = findPrefix(prefix, tab[beg + mid + rad - i]);
                       prefix = findPrefix(prefix, tab[beg + mid - 1 - i]);
                       prefix = findPrefix(prefix, tab[beg+mid-i]);

                       swap(tab, beg + mid + rad - i, beg + mid - 1 - i);
                       swap(tab, beg+mid-1-i, beg+mid-i);
                       i++;
                   }
                   prefix = tasuj(tab, beg,beg+mid+1,prefix);
                   prefix = tasuj(tab, beg+mid+1,end,prefix);
               }
           }else if(n>2) {
               mid = (n-1)/2;
               if(mid%2==0){
                   int rad = mid / 2;
                   int i = 0;
                   while (i < rad) {

                       prefix = findPrefix(prefix, tab[beg + mid + rad+1 - i]);
                       prefix = findPrefix(prefix, tab[beg + mid - i]);
                       prefix = findPrefix(prefix, tab[beg+mid+1-i]);

                       swap(tab, beg + mid + rad+1 - i, beg + mid - i);
                       swap(tab, beg+mid-i, beg+mid+1-i);
                       i++;
                   }
                   prefix = tasuj(tab, beg,beg+mid+2,prefix);
                   prefix = tasuj(tab, beg+mid+2,end,prefix);

               }else {//mid%2==1

                   int rad = mid/2+1;
                   for(int i=0; i<rad; i++) {
                       prefix = findPrefix(prefix, tab[beg + mid - i]);
                       prefix = findPrefix(prefix, tab[beg + mid + rad - i]);

                       swap(tab, beg + mid - i, beg + mid + rad - i);
                   }
                   prefix = tasuj(tab, beg, beg+mid+1,prefix);
                   prefix = tasuj(tab, beg+mid+1,end,prefix);
               }
           }
       }
       return prefix;
       }

    // wczytuje kolejne zestawy dantch
    public static void wejscie(){
        Scanner scanner = new Scanner(System.in);
        int ilePlyt  = scanner.nextInt();
        for(int i=0; i<ilePlyt; i++){

            int ilePiosenek = scanner.nextInt();
            String[] tab = new String[ilePiosenek];

            for (int j =0; j<ilePiosenek; j++)
                tab[j] = scanner.next();

            String prefix = tab[0];
            prefix = findPrefix(prefix, tab[tab.length-1]); // mozliwe ze nie zmieniamy jedynie pierwsze lub ostatnie

            prefix = tasuj(tab, 0, tab.length,prefix);
            printArray(tab,prefix);
        }
    }

    // wypisuje na standardowe wyjscie w odpowiednim formacie
    public static void printArray(String[] tab, String prefix){
        System.out.print(tab[0]);
        for(int i=1; i< tab.length; i++)
            System.out.print(" "+tab[i]);
        System.out.println();
        System.out.println(prefix);
    }

    public static void main(String[] args) {
//       test0();
         wejscie();
//        System.out.println(findPrefix("s,",""));
    }
}


/*
test jawny
3
4
FleetwoodMacGypsy FleetwoodMacDreams FlorenceSweetNothing FlorenceNoLight
5
Piosenka11 piosenka12 piosenka13 piosenka21 piosenka22
15
p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15


    public static void test0(){
        String[] tab = {"aa","sa","aa","aa","aa","aa","aa","aa","aa"};
        String prefix = tab[0];
        prefix = findPrefix(prefix, tab[tab.length-1]);
        printArray(tab,prefix);

        prefix = tasuj(tab, 0,tab.length,prefix);
        printArray(tab,prefix);
    }


 public static void  test1(){ // 4/8/16
        String[] tab = {"aa","bb","cc","dd","e","f","g","h","aa","bb","cc","dd","e","f","g","h"};

        String prefix = tab[0];
        printArray(tab,prefix);

        prefix = tasuj(tab, 0,tab.length,prefix);
        printArray(tab,prefix);
    }
    public static void  test2(){ // 6/12/24
        String[] tab = {"aa","bb","cc","dd","e","f","aa","bb","cc","dd","e","f","aa","bb","cc","dd","e","f","aa","bb","cc","dd","e","f"};
String prefix = tab[0];
    printArray(tab,prefix);

    prefix = tasuj(tab, 0,tab.length,prefix);
    printArray(tab,prefix);
    }
    public static void  test3(){ // 7/14 elementos
        String[] tab = {"aa","bb","cc","dd","e","f","g","aa","bb","cc","dd","e","f","g"};
        String prefix = tab[0];
        printArray(tab,prefix);

        prefix = tasuj(tab, 0,tab.length,prefix);
        printArray(tab,prefix);
    }
    public static void  test4(){ // 9/18 elementos
        String[] tab = {"aa","bb","cc","dd","ee","f","g","i","j","aa","bb","cc","dd","ee","f","g","i","j"};
        String prefix = tab[0];
        printArray(tab,prefix);

        prefix = tasuj(tab, 0,tab.length,prefix);
        printArray(tab,prefix);
    }
public static void  test5(){ // 4/8/16
    String[] tab = {"aaaa","aaaa","aa","aaa"};
    String prefix = tab[0];
    printArray(tab,prefix);
    prefix = tasuj(tab, 0,tab.length,prefix);
    printArray(tab,prefix);
}
*/