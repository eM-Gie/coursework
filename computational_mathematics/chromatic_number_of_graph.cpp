//Marianna Gawron
/*
 OPIS PROBLEMU
 Rozwazamy graf G = (V,E), gdzie
 V = {1,2,....n} oraz E = podane w pliku

 Naszym zadaniem jest wyznaczyc liczbe chromatyczna grafu G i pokazac takie kolowranie.
 */

#include <iostream>
#include <list>
#include <fstream>
using namespace std;



void add_edge (list <int> adj[],int u,int v){

    adj[u].push_back(v);
    adj[v].push_back(u);
}

bool canColor (int k, int col, list <int> adj[], int const color[]){
    for (auto v : adj[k])
        if (color[v] == col)
            return false;

    return true;
}

bool colorGraph(int k, int no_of_colors,  int n, list <int> adj[], int color[]){
    if(k==n+1)
        return true;
    for(int col = 1; col <= no_of_colors; col++){
        if(canColor(k, col, adj, color)){
            color[k] = col;
            if(colorGraph(k+1, no_of_colors, n, adj, color))
                return true;
            color[k] = 0;

        }
    }
    return false;

}
bool isColorable(int no_of_colors, int n, list <int> adj[], int color[]){
    color[1] = 1;
    for (int i=2; i<n+1; i++)
        color[i] = 0;

    if (colorGraph(2, no_of_colors, n, adj,color))
        return true;
    else return false;
}


void dlaMalych(int n, list <int> adj_list[], int color[]){

    // ________________ sprawdzamy dla kolejnych ilosci kolorow czy pasuje ____________________________

    bool finito;
    int count =0;
    do {
        finito = isColorable(count, n, adj_list, color);
    }while (!finito && count++<n);

    cout << "\nPierwszy algorytm (z labsow) twierdzi ze mozemy pojechac " << count << " samochodami zeby nie bylo KABOOM\n";

    for(int col=1; col<count+1; col++){
        cout << "\nSamochod "<< col << ": ";
        for(int i=1; i<n+1; i++)
            if(color[i]==col)
                cout << i << " ";
    }
}

int getColor(int k, list <int> adj_list[], int const color[]){
    int col=0;
    bool finito = false;

    while(!finito){
        col++;
        finito = true;

        for ( auto i : adj_list[k]){
            if (col == color[i]) {
                finito = false;
            }
        }
    }
    return col;

}


void dlaDuzych(int n, list <int> adj_list[], int color[]){
    color[1] = 1;
    int max = 1;

    for (int i=2; i<n+1; i++){
        color[i] = 0;
    }
    for (int i=2; i<n+1; i++){
        color[i] = getColor(i,adj_list,color);

        if (color[i]>max) max = color[i];
    }

    cout << "\nDrui algorytm (z wykladu) twierdzi ze mozemy pojechac " << max << " samochodami zeby nie bylo KABOOM\n";

    for(int col=1; col<max+1; col++){
        cout << "\nSamochod "<< col << ": ";
        for(int i=1; i<n+1; i++)
            if(color[i]==col)
                cout << i << " ";
    }
}


int main() {


    string myText;

    ifstream MyReadFile("plik.txt");
    getline (MyReadFile, myText);

    //__________________ tworzymy graf _______________________________________
    int n = stoi(myText);
    list <int> adj_list[n+1];
    int color[n+1];

    while (getline (MyReadFile, myText)) {
        int i=0;
        string x,y;
        while (myText[i]!=' '){
            x+=myText[i];
            i++;
        }
        while(isdigit(myText[++i])){
            y+=myText[i];
        }

        int a = stoi(x), b = stoi(y);
        add_edge(adj_list,a,b);
    }
    MyReadFile.close();

    //__________decydujemy czy uzywac pierwszy (wyznaczajacy optymalne kolorowanie)__________
    // _________czy drugi (nieoptymalne, ale potencjalnie szybsze)  _______________________________________

    if (n<10){
        dlaMalych(n, adj_list, color );
    }else {
        dlaDuzych(n, adj_list, color );
    }

}
