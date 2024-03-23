//Marianna Gawron - 1

import java.util.Scanner;

public class Source{

    // LIST OF FUNCTIONS:
    // sort function (quickSort)
    // binarySearch (searches for last occurance of element)
    // triangle function (finds first ten elements that can form a triangle,
    // counts the nr of triangles that can be built using given lengths)
    // main function ( takes in data, for every set of data runs the triangle function)

// COMPLEXITY : n^2logn
// quick sort complexity: n^2 ,
// triangle zajmuje n^2 * (zlozonosc binary search) czyli n^2 * logn
// zatem zlozonosc calego programu jest n^2 * logn



    static int partition(int[] arr, int low, int high){

        int pivot = arr[high];

        int i = (low - 1);

        for(int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {

                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i+1];
        arr[i+1] = arr[high]; //=pivot
        arr[high] = temp;

        return (i + 1);
    }
    static void quickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }




       static int binarySearch(int[] arr, int l, int r, int x) {

           int mid=r;
           if(arr[r]<=x) return r;// jezeli takiej wartosci nie bedzie w arr to zwracamy najwiekszy indeks

        if (r >= l) {
                 mid = l + (r - l) / 2;

                if (arr[mid] == x) {
                    while (arr[mid + 1] == x)//zeby zlapac ostatni element w tablicy
                        mid ++;
                    return mid;
                }


                if (arr[mid] > x)
                    return binarySearch(arr, l, mid - 1, x);

                return binarySearch(arr, mid + 1, r, x);
            }

            //jezeli szykana liczba jest mniejsza of arr[l]
            return -1;
        }


    private static void triangles(int[] tab){
        int count =0, ile=0;

        for (int i=0; i<tab.length-2;i++)

            for(int j=i+1; j< tab.length-1; j++){

                int suma = tab[i]+tab[j];
                int k= binarySearch(tab, j+1, tab.length-1, suma-1);

                if(k!=-1){
                    ile += k - j;
                    int f=j+1;

                    // wyswietlanie pierwszych dziesieciu trojkatow
                    while(f<=k && count<10){
                        System.out.print("("+i+","+j+","+f+") ");
                        f++;
                        count++;
                    }
                }
            }
        if(ile==0){
            System.out.println("Triangles cannot be built");
        }else System.out.println("\nNumber of triangles: "+ile);
    }

        public static void main(String[] args) {


            //wczytywanie
            Scanner sc = new Scanner(System.in);
            int nr_of_sets = sc.nextInt();

            for(int i=0; i<nr_of_sets;i++){
                int n= sc.nextInt();
                System.out.println(i+1+": n= " +n);
                int[] tab= new int[n];
                for(int j=0; j<n; j++) {
                    tab[j] = sc.nextInt();
                }
                //sortujemy tablice
                quickSort(tab, 0,tab.length-1);
                // wyswietlamy tablice
                int count=0;
                for(int j=0; j<tab.length;j++) {
                    if(count==25) {
                        System.out.println();
                        count=0;
                    }
                    System.out.print(tab[j] + " ");
                    count++;
                }
                System.out.println();
                // liczymy trojkaty
                triangles(tab);
            }


    }
    }

// TESTY


// WEJSCIE
//        8
//        4
//        1 1 1 1
//        5
//        3 2 3 3 3
//        30
//        12 23 34 45 56 12 23 34 45 56 12 23 34 45 65 21 23 34 45 65 76 21 32 43 45 56 67 87 21 23
//26
//        12 23 34 45 56 12 23 34 45 56 12 23 34 45 65 21 23 34 45 65 76 21 32 43 45 56
//
//        25
//        12 23 34 45 56 12 23 34 45 56 12 23 34 45 65 21 23 34 45 65 76 21 32 43 45
//
//
//        51
//        45 14 17 35 38 41 12 27 29 48 25 42 26 37 32 22 30 49 8 11 33 39 3 50 7 44 18 31 36 43 1 2 28 13 20 9 24 15 21 16 6 4 47 46 19 23 10 40 34 5 23
//
//
//
//        76
//        23 34 45 56 12 23 34 45 56 12 23 34 45 65 21 23 34 45 65 76 21 32 43 45 56 45 14 17 35 38 41 12 27 29 48 25 42 26 37 32 22 30 49 8 11 33 39 3 50 7 44 18 31 36 43 1 2 28 13 20 9 24 15 21 16 6 4 47 46 19 23 10 40 34 5 23
//
//        75
//        34 45 56 12 23 34 45 56 12 23 34 45 65 21 23 34 45 65 76 21 32 43 45 56 45 14 17 35 38 41 12 27 29 48 25 42 26 37 32 22 30 49 8 11 33 39 3 50 7 44 18 31 36 43 1 2 28 13 20 9 24 15 21 16 6 4 47 46 19 23 10 40 34 5 23

//WYJSCIE:
//        1: n= 4
//        1 1 1 1
//        (0,1,2) (0,1,3) (0,2,3) (1,2,3)
//        Number of triangles: 4
//        2: n= 5
//        2 3 3 3 3
//        (0,1,2) (0,1,3) (0,1,4) (0,2,3) (0,2,4) (0,3,4) (1,2,3) (1,2,4) (1,3,4) (2,3,4)
//        Number of triangles: 10
//        3: n= 30
//        12 12 12 21 21 21 23 23 23 23 23 32 34 34 34 34 43 45 45 45 45 45 56 56 56
//        65 65 67 76 87
//        (0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,1,8) (0,1,9) (0,1,10) (0,2,3)
//        Number of triangles: 2583
//        4: n= 26
//        12 12 12 21 21 23 23 23 23 32 34 34 34 34 43 45 45 45 45 45 56 56 56 65 65
//        76
//        (0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,1,8) (0,2,3) (0,2,4) (0,2,5)
//        Number of triangles: 1813
//        5: n= 25
//        12 12 12 21 21 23 23 23 23 32 34 34 34 34 43 45 45 45 45 45 56 56 65 65 76
//        (0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,1,8) (0,2,3) (0,2,4) (0,2,5)
//        Number of triangles: 1584
//        6: n= 51
//        1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 23 24
//        25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49
//        50
//        (0,22,23) (1,2,3) (1,3,4) (1,4,5) (1,5,6) (1,6,7) (1,7,8) (1,8,9) (1,9,10) (1,10,11)
//        Number of triangles: 10226
//        7: n= 76
//        1 2 3 4 5 6 7 8 9 10 11 12 12 12 13 14 15 16 17 18 19 20 21 21 21
//        22 23 23 23 23 23 23 24 25 26 27 28 29 30 31 32 32 33 34 34 34 34 34 35 36
//        37 38 39 40 41 42 43 43 44 45 45 45 45 45 45 46 47 48 49 50 56 56 56 65 65
//        76
//        (0,11,12) (0,11,13) (0,12,13) (0,22,23) (0,22,24) (0,23,24) (0,26,27) (0,26,28) (0,26,29) (0,26,30)
//        Number of triangles: 37496
//        8: n= 75
//        1 2 3 4 5 6 7 8 9 10 11 12 12 12 13 14 15 16 17 18 19 20 21 21 21
//        22 23 23 23 23 23 24 25 26 27 28 29 30 31 32 32 33 34 34 34 34 34 35 36 37
//        38 39 40 41 42 43 43 44 45 45 45 45 45 45 46 47 48 49 50 56 56 56 65 65 76
//        (0,11,12) (0,11,13) (0,12,13) (0,22,23) (0,22,24) (0,23,24) (0,26,27) (0,26,28) (0,26,29) (0,26,30)
//        Number of triangles: 35822