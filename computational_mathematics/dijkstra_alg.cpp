//Marianna Gawron
#include <iostream>
using namespace std;


int minDist(int dist[], bool sptSet[], int n){
    int min = INT_MAX, min_index;

    for (int v = 0; v < n; v++)
        if (!sptSet[v] && dist[v] <= min)
            min = dist[v], min_index = v;

    return min_index;
}


int dijkstra(int n, int **adj, int beg, int end)
{
    int dist[n];
    bool sptSet[n];
    for (int i = 0; i < n; i++)
        dist[i] = INT_MAX, sptSet[i] = false;

    dist[beg] = 0;

    for (int count = 0; count < n - 1; count++) {
        int u = minDist(dist, sptSet, n);

        sptSet[u] = true;

        for (int v = 0; v < n; v++)
            if (!sptSet[v] && adj[u][v] && dist[u] != INT_MAX
                && dist[u] + adj[u][v] < dist[v])
                dist[v] = dist[u] + adj[u][v];
    }
    return dist[end];
}

void wczyt(){
    int n;
    cin >> n;
    int **adj = new int*[n];

    for(int i=0; i<n; i++){
        adj[i] = new int[n];
        for (int j=0; j<n; j++){
            cin >> adj[i][j];
        }
    }
    int no_of_bakeries;
    cin >> no_of_bakeries;
    int bakery[no_of_bakeries];

    for (int i=0; i<no_of_bakeries; i++){
        cin >> bakery[i];
        bakery[i]-=1;
    }

    int toBakery,toGranny, min = INT16_MAX, count=0;
    for (int i = 0; i < no_of_bakeries; i++) {
        toBakery = dijkstra(n, adj, 0, bakery[i]);
        toGranny = dijkstra(n, adj, bakery[i],n-1);

        if (min > toGranny + toBakery) {
            count = 1;
            min = toGranny + toBakery;
        } else if(min == toGranny + toBakery) count++;

    }
    cout << "Najkrotsza droga przechodzaca przez piekarnie ma dlugosc: " << min << "\nDrog o takiej dlugosci jest "<<count;

}

int main() {
    wczyt();
    return 0;
}

/*
 - wczytanie rzeczy,
     -sasiedzi wierzcholka za pomoca listy sasiedztwa.
     -lista ciastkarni jako tablica
 - algorytm dijkstry z 1 do 2 i do 4 (kazdego elementu z tablicy ciastkarn)
 - algorytm dijkstry z kazdego z ciastkarn do elementu n

 5
0 2 2 1 1
2 0 1 2 1
2 1 0 2 1
1 2 2 0 2
1 1 1 2 0
2
2 4


 */
