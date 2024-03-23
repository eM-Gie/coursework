// Marianna Gawron

#include <iostream>
#include <list>
#include <stack>
#include <utility>
using namespace std;

/*
void euler(){

    int k,N;
    cout << "wprowadz n i k: ";
    cin >> N >> k;

    list <int> adj_list[N];
        while(k>0){
            int v,u;
            cin >> v >> u;
            adj_list[u].push_back(v);
            adj_list[v].push_back(u);
            k--;
        }

        // z twierdzenie Eulera-Hierholzera:
        // graf jest eulerowski <==> wszystkie stopnie wierzcholkow sa parzyste.

        for (int i=0; i<N; i++){
            if(adj_list[i].size()%2!=0) {
                cout << "graf nie jest eulerowski  :C       \n";
                return;
            }
        }
        cout << "graf jest eulerowski :)      \n";

};
*/

int unvisited[10][10];

pair<int, int> getNeighbour(pair<int,int> a){
    int x = a.first;
    int y = a.second;
    if(unvisited[x][y+1]==0) return make_pair(x,y+1);
    if(unvisited[x][y-1]==0) return make_pair(x,y-1);
    if(unvisited[x+1][y]==0) return make_pair(x+1,y);
    if(unvisited[x-1][y]==0) return make_pair(x-1,y);

    return make_pair(-1,-1);
}


int main()
{

    string str;
    for(int i=0; i<10; i++){
        cin>> str;
        for (int j=0; j<10; j++){
            if (str[j] =='X')
                unvisited[i][j]=-1;
            else unvisited[i][j] = 0;
        }
    }
    stack<pair<int, int>> s;

    pair<int,int> curr = make_pair(0,0);
    unvisited[0][0]=1;
    while (1){
        pair<int, int> temp = getNeighbour(curr);

        if (temp.first==-1){
            unvisited[curr.first][curr.second]=-1;
            curr = s.top();
            s.pop();
        }
        else{ // czyli curr ma jakiegos sasiada
            unvisited[curr.first][curr.second]=1;
            s.push(curr);
            curr = temp;
        }
        if(curr.first==9 && curr.second==9) {
            cout<<"Wyjscie z labiryntu istnieje!\nTAK";
            return 0;
        }

    }


    return 0;
}
