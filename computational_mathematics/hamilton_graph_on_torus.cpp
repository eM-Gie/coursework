/*
 OPIS PROBLEMU
 Rozwazamy graf G = (V,E), gdzie
 V = {p = (x,y): 1 <= x,y <= n}
 oraz E = { (p1%N,p2%N) :
 p2.x=p1.x+1 && p2.y=p1.y+2 || p2.x=p1.x-1 && p2.y=p1.y+2
 || p2.x=p1.x+1 && p2.y=p1.y-2 || p2.x=p1.x-1 && p2.y=p1.y-2
 || p2.x=p1.x+2 && p2.y=p1.y+1 || p2.x=p1.x+2 && p2.y=p1.y-1
 || p2.x=p1.x-2 && p2.y=p1.y+1 || || p2.x=p1.x-2 && p2.y=p1.y-1}
} (ruchy podobne do ruchow skoczka).

 Naszym zadaniem jest sprawdzenie czy graf G posiada cykl hamiltona.
 */

#include <iostream>
#include <list>

using namespace std;


struct Point;
const int n = 4;
list <Point> adj_list[n + 1][n + 1];

int mod(int a){
    if(a<1) a+=n;
    if(a > n) a-=n;
    return a;
}
struct Point{
    int x,y;
    Point():x(0),y(0){}
    Point(int a, int b):x(mod(a)),y(mod(b)){}

    void show() const{
        cout <<"("<< x << ","<<y <<")";
    }
};

bool unused[n + 1 ][n + 1 ]; // unused[a.x][a.y]= true <=> point a isnt in usied in the circuit yet
Point circuit[n * n + 1 ];



void add_edge (Point u,Point v)
{
    adj_list[u.x][u.y].push_back(v);
    adj_list[v.x][v.y].push_back(u);
}


void createGraph()
{
    for (int i=1; i < n + 1; i++){
        for(int j=1; j < n + 1; j++){
            add_edge(Point(i,j), Point(i+2, j-1));
            add_edge(Point(i,j), Point(i+2, j+1));
            add_edge(Point(i,j), Point(i-1, j+2));
            add_edge(Point(i,j), Point(i+1, j+2));
        }
    }
}


bool hamilton(int k)
{
    for (int i=0; i<n*n; i++)
    if(k == n * n + 1){
        for(auto&& u : adj_list[circuit[1].x][circuit[1].y])
        {
            if (u.x == circuit[n * n].x && u.y == circuit[n * n].y ) {
                return true;
            }
        }
        return false;
    }
    else
    {
        for (auto &&u: adj_list[circuit[k - 1].x][circuit[k - 1].y])
        {
            if (unused[u.x][u.y]) {
                circuit[k] = u;
                unused[u.x][u.y] = false;
                if (hamilton(k + 1))
                    return true;
                unused[u.x][u.y] = true;
            }
        }
    }
    return false;
}


void startFrom(Point a){
    cout << "\n\n_____________________________*_________________________________\n" ;
    cout << "Starting from point ";
    a.show();
    cout << ", ";

    for (int i = 1; i < n + 1; ++i)
        for (int j=1; j < n + 1; ++j)
            unused[i][j] = true;

    unused[a.x][a.y] = false;
    circuit[1] = a;


    if (hamilton(2))
    {
        cout << "the knight CAN get to every square of the donut :) "<<endl<<endl;
        for (int i = 1; i < n * n + 1; ++i) {
            circuit[i].show();
            cout << " -> ";
        }
        circuit[1].show();

    }
    else
    {
        cout<<"the knight CAN'T get to every square of the donut :("<<endl<<endl;
    }

}

int main()
{
    createGraph();

    startFrom(Point(1,1));
    for(auto&& u : adj_list[1][1]){
        startFrom(u);
    }
}