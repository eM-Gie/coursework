//Marianna Gawron -1
import java.util.Scanner;

public class Source {

    // Funkcje pomocnicze

    // swapRows zamienia miejscami wskazane wiersze w dwuwymiarowej tabeli, uzywane w sortowaniu
    public static void swapRows(String [][]tab,int a, int b){
//        System.out.println("swapping"+a+" "+b);
        String []temp = new String[tab[0].length];
        for(int i=0; i<temp.length; i++){
            temp[i] = tab[a][i];
            tab[a][i] = tab[b][i];
            tab[b][i] = temp[i];
        }
    }
    // changeCol "wyciaga" wskazana kolumne i wstawia ja na piierwszym miejscu, uzywane w funkcji "wejscie"
    public static void changeCol(String[][] tab, int col){
        String temp;
        for(int i=0; i<tab.length; i++) {
            temp = tab[i][col-1]; // col-1 bo wartosci sa podawane jako jedne wieksze
            for(int j=col-1; j>0; j--)
                tab[i][j] = tab[i][j-1];
            tab[i][0] = temp;
        }

    }
    // zwraca true jezeli podany string jest liczba
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    // funkcja porownuje dwa stringi zgodnie z okreslonym porzadkiem dir)
    public static boolean isSmaller(String a, String b, int dir){
//        System.out.println("comparing "+a+" "+b);
        if(isNumeric(a)) {
            double aa = Double.parseDouble(a);
            double bb = Double.parseDouble(b);
            if (dir == 1) {
                return aa < bb;
            } else return aa > bb;
        } else{
            if(dir==-1){
                return( a.compareTo(b) > 0 );
            } else return( a.compareTo(b) < 0 );
        }
    }
    // wyswietla tabele dwuwymiarowa w odpowiedni sposob
    public static void printTab(String[][] tab, int colCount){
        for(int s=0; s<tab.length; s++) {
            for (int j = 0; j < colCount; j++) {
                System.out.print(tab[s][j]);
                if(j!=colCount-1) System.out.print(",");
            }
            System.out.println();
        }
        System.out.println();
    }



    // funkcje sortujace

    // sortuje po piewszej kolumnie tabelki dwuwymiarowej metoda selection sort
    // iterujac po tabelce wybieramy wartosc najmniejsza ktora nastepnie zamieniamy z pierwsza wartoscia
    // korzysta z funkcji compare zeby mogla porownywac stringi
    //zlozonosc n^2
    public static int selectionSort(String[][] tab, int low, int high,int dir){
//        System.out.println("hello from selection "+low+" "+high);
            for(int i=low; i<high+1; i++){
                int min = i;
                for(int j=i; j<high+1; j++){
                    if(isSmaller(tab[j][0], tab[min][0], dir))
                        min = j;
                }
                swapRows(tab,min,i );
            }
            return high+1;
    }

// ustawia pivota na najwyzszy element, dzieli tablice a elementy wieksze mniejsze od pivota, potem pivota wsadza miedzy nimi
// porownuje po pierwszej kolumnie, zamienia cale wiersze
    static int partition(String[][] tab, int low, int high, int dir)
    {
        String pivot = tab[high][0];

        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if ( ! isSmaller(pivot,tab[j][0], dir) ) {
                i++;
                swapRows(tab, i,j);
            }
        }
        swapRows(tab,i+1, high );
        return i + 1;
    }

    //petla sie wykonuje dopoki ma jakies dzialania do wykonania
    //dziala analogicznie do quicksorta z partition w wersji lomuto, jednak nie wywoluje sie rekursywnie, ani nie korzysta ze stosu
    // jezeli spelnione sa odpowiednie warunki dzieli rozwazany przedzial dwa i koniec drugiego przedzialu znakuje tzn
    // wstawiaja znak "%" na poczatek stringa (w ten sposob zapamietuje nastepny przedzial ktory musi rozwazyc)
    // "%" jest usuwane z kolei gdy rozwazany przedzial zostanie posortowany
    // oczywiscie w przypadku przedzialow o dlugosci <=5 uzywany jest selection sort, ktory nastepnie ustwia lewy na nastepny element
    public static void quickSort( String[][] tab,int left, int right, int dir) {
        int i = 0; // mowi o tym czy sa jeszcze operacje do zrobienia

        while(left < right || i > 0) {

            if(right-left<=5 && left < right) {
                left = selectionSort(tab,left,right,dir);
            }
            if(left < right  ){
                    int piv = partition(tab, left, right, dir);

                        tab[right][0] = "%" + tab[right][0]; // jezeli przedzial wiekszy od 5 to oznaczam sobie koniec prawego przedzialu, ktory jeszcze trzeba zrobic
                    right = piv - 1;
                i++;
            }
            else {
                right = left = right + 2; // bo przeskakujemy przez starego pivota do poczatku nastepnego przedzialu

                while(right < tab.length)
                    if(tab[right][0].charAt(0) =='%'){
                            tab[right][0] = tab[right][0].substring(1);
                            break;
                    } else
                        right++;
                i--;
            }
        }

        i = 0;
        while(i < tab.length) {// usuwamy wszystkie pozostalosci bo selsction czasami nam bedzie psul
            if (tab[i][0].charAt(0) == '%') {
                tab[i][0] = tab[i][0].substring(1);
            }
            i++;
        }
    }

    public static void wejscie(){
        Scanner scan = new Scanner(System.in);
        int setCount = Integer.parseInt(scan.nextLine());
        for(int i=0; i<setCount; i++){
            String sth =scan.nextLine();

            //split the first line regrex ","
            String[] number = sth.split(",",3);
            int songCount =Integer.parseInt(number[0]) ;
            int whichColumn = Integer.parseInt(number[1]);
            int direction = Integer.parseInt(number[2]);


            // creating 2d array where rows comtain songs and columns keep song info
            String[][] tab = new String[songCount+1][100];
            int colCount=0;

            for(int j=0; j<songCount+1; j++){
                String row= scan.nextLine();

                String[] temp= row.split(",");
                colCount = temp.length;

                for(int k=0; k<colCount; k++) {
                    tab[j][k] = temp[k];
                }
            }
            //changing order of columns. whichCOlumn goes to front
            changeCol(tab, whichColumn);

            // sort
            quickSort(tab,1,tab.length-1, direction);
            printTab(tab,colCount);
        }
    }

    public static void main(String[] args) {
//        test1();
        wejscie();
//        test2();
    }
}


/*

1
11,2,1
mq,78,qa,24,dk,59,fs,50,av,36,zu,84,is,46
kn,52,bi,64,lv,73,aj,52,cp,6,fo,92,nu,20
yi,44,wa,96,pz,63,od,73,nm,6,pv,99,xd,41
kv,23,xr,69,bn,75,nr,31,lx,10,th,72,pw,39
zk,11,gr,17,gt,24,my,66,pb,87,ni,22,yr,5
el,66,ib,23,lf,47,ho,12,ud,69,fw,84,vz,13
vo,42,tx,97,di,40,fn,89,el,71,of,25,ve,80
zb,64,zq,56,va,26,db,81,lx,47,ua,58,oj,36
zv,26,ai,10,bt,5,os,89,zj,23,ki,58,js,53
sx,92,fa,16,cj,71,zx,53,zw,52,co,54,yl,73
mb,2,ny,72,uv,27,hl,31,el,78,ib,3,xf,80
fa,77,ya,79,ib,32,ar,27,nw,11,wi,78,zk,60


2
11,2,-1
se,89,qm,19,xs,39,cl,95,ds,4,rr
wo,10,hg,74,pc,42,cd,42,vp,65,fn
gx,0,gy,61,vo,31,mn,57,pu,95,yk
yt,40,pn,5,op,96,sb,16,ux,8,yu
er,5,ax,90,sd,38,nu,0,vp,88,qn
tm,36,zp,69,bz,38,ls,69,he,62,py
vz,61,bs,60,fq,13,ym,20,fv,45,mp
zx,28,wc,85,hx,25,xc,75,ft,62,qq
ml,84,ta,21,vr,12,pl,18,ac,96,xw
km,48,vz,1,pk,83,ot,18,jq,68,ng
sp,62,nw,67,xs,14,ab,25,er,96,ig
jo,96,vh,29,mq,97,od,26,og,82,di
13,1,-1
ml,70,lg
vl,81,cq
ai,76,gr
xm,67,we
ga,69,fo
la,79,bf
bk,4,dh
iv,58,wk
wa,87,cg
qk,40,kc
nr,55,us
yf,43,qn
lh,47,cd
qk,26,tz

4
5,1,-1
Album,Year,Songs,Length
aa,2006,28,122
bb,2022,17,73
cc,1999,15,56
dd,2022,17,73
ee,1999,15,56
5,1,1
Album,Year,Songs,Length
aa,2006,28,122
bb,2022,17,73
cc,1999,15,56
dd,2022,17,73
ee,1999,15,56
5,2,1
Album,Year,Songs,Length
aa,2006,28,122
bb,2022,17,73
cc,1999,15,56
dd,2022,17,73
ee,1999,15,56
10,2,-1
Album,Year,Songs,Length
aa,2006,28,122
bb,2022,17,73
dd,2022,17,73
cc,1999,15,56
ee,1999,15,56
aa,2006,28,122
bb,2022,17,73
cc,1999,15,56
dd,2022,17,73
ee,1999,15,56


1
10,2,-1
Album,Year,Songs,Length
aa,2006,28,122
bb,2022,17,73
dd,2022,17,73
cc,1999,15,56
ee,1999,15,56
aa,2006,28,122
bb,2022,17,73
cc,1999,15,56
dd,2022,17,73
ee,1999,15,56

1
3,2,1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56




3
3,1,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,2,1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,4,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56


    public static void test1(){
        String[][] tab = new String[1][100];

        String sth = "Californication,1999,15,56";
        for(int j=0; j<1; j++){
            System.out.println(sth);

            String[] temp= sth.split(",");
            for(int k=0; k<temp.length; k++) {
                tab[j][k] = temp[k];
                System.out.println(tab[j][k]);
            }
        }
    }


public static void test2(){
        int[] tab = {12,4,23,34,54,3};
        for(int i=0; i<tab.length; i++)
            System.out.print(tab[i]+" ");
    System.out.println();

//        quickSortIterative(tab,0, tab.length-1);
    for(int i=0; i<tab.length; i++)
        System.out.print(tab[i]+" ");
}

 */
