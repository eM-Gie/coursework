//Marianna Gawron
#include <iostream>
#include <iomanip>
#include <vector>

using namespace std;


void SOR2(const vector<double> vectors[], const vector<double> &y, const vector<double> & _x0, int L,int M, double w ) {// Jacobi
    vector<double> x0 = _x0;
    auto n = y.size();

    for(int iter=0; iter < L; iter++) {
        for (int i = 0; i < n; i++) {
            auto s = y[i];
            for (int m =0; m<M; m++) {
                if(n-i<=vectors[m].size())
                    s -= vectors[m][i-M+m]*x0[i-M+m];
            }
            for (int m =0; m<M; m++) {
                if(i<=vectors[M-m-1].size())
                    s -= vectors[M-m-1][i]*x0[i+m+1];
            }

            x0[i] = (1-w) * x0[i] + w * s / vectors[M][i];
        }

    }

    for (int i=0; i<n; i++)
        cout <<x0[i] <<endl;
}

void input2(){
    int N,M,L;
    double w,x;

    cin>>N>>M;

    vector<double > y, x0;
    vector <double> vectors[M+1];

    double temp;
    for (int m=0; m<M+1;m++){// tyle bedzie ukosow
        for (int i=0; i<N-M+m; i++) {
            cin >> temp;
            vectors[m].push_back(temp);
        }
    }

    for (int i=0; i<N; i++){
        cin>>temp;
        y.push_back(temp);
    }
    for (int i=0; i<N; i++){
        cin>>temp;
        x0.push_back(temp);
    }

    cin>> w;
    cin>> L;

    SOR2(vectors, y, x0,L,M, w);

}


int main() {
    std::cout << setprecision(17) << std::fixed;
    input2();
    return 0;
}

/*
7
2
1 2 1 2 1
2 -1 3 1 3 -1
5 6 7 8 9 10 11
8 9 11 16 15 14 11
2 3 2 3 2 3 2
1.5
1

 7
0
5 6 7 8 9 10 11
8 9 11 16 15 14 11
2 3 2 3 2 3 2
2
1

 */