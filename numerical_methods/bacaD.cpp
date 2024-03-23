//marianna gawron
#include "vectalg.h"
using namespace std;

long double norma(const Vector pDouble, unsigned n) {
    long double norm=0;
    for (int i=0 ;i<n; i++)
        norm += pDouble[i] * pDouble[i];

    return sqrt(norm);
}

void printmat(Vector row[], unsigned n){
    for (int i=0; i<n; i++){
        cout<< row[i]<<endl;
    }
    cout<<endl;
}

void ktyKrok( Vector row[], int i, unsigned n, Vector &s, Vector &solutions){
    // wybor elem glownego
    int elem_glowny = i;
    for (int j = i; j < n; j++) {
        if (row[j][i] / s[j] > row[elem_glowny][i] / s[elem_glowny])
            elem_glowny = j;
    }

    // zamiana na gore
    {
        Vector temp = row[i];
        row[i] = row[elem_glowny];
        row[elem_glowny] = temp;

        double temp2 = s[i];
        s[i] = s[elem_glowny];
        s[elem_glowny] = temp2;

        double temp3 = solutions[i];
        solutions[i] = solutions[elem_glowny];
        solutions[elem_glowny] = temp3;
    }


    // odejmowanie Ai ← Ai - aik / akk · Ak dla i = k + 1, . . . , n
    for(int r= i + 1; r < n; r++){
        double wsp = row[r][i] / row[i][i];
        for(int c=i ; c < n; c++){
            row[r][c] = row[r][c] - wsp * row[i][c];
        }
        solutions[r] = solutions[r] - wsp * solutions[i];
    }

}
Vector solveEquations(
        const Matrix & A,   // Macierz
        const Vector & b,   // Wektor
        double  eps         // dopuszczalny błąd
) {

    unsigned n = b.size();
    Vector s(n);
    Vector row[n];
    Vector solutions(n) ;
    solutions = b;

//tworze kopie macierzy a, jako tablica rzedow,
// licze max normy
    for (int i = 0; i < n; i++) {
        row[i] = Vector(n);
        for (int j = 0; j < n; j++) {
            row[i][j] = A(i, j);
        }
        s[i] = row[i].max_norm();
    }

    // n-1 krokow algortymu
    for (int i = 0; i < n - 1; i++) {
        ktyKrok(row, i,n,s,solutions);
    }

    // wyliczam wynik
    Vector wynik(n);

    for(unsigned i=n-1; i<4294967295; i--){
        wynik[i] = solutions[i];
        for(unsigned j=i+1; j<n; j++){
            wynik[i] -= row[i][j] * wynik[j];
        }
        wynik[i] /= row[i][i];
    }

    //poprawianie iteracyjne
//    rk = b − Axk
    Vector r(n);
    Vector e(n);

    if (eps<0) return wynik;
    int count =0;

    do{// ek = rozwiazanie układu Aek = rk ,
        count++;
        for (int i=0; i<n; i++){
            r[i] = solutions[i];
            for (int j=0; j<n; j++){
                r[i] -= row[i][j] * wynik[j];
            }
        }
        e = solveEquations(A,r, -10);
        // xk+1 = xk + ek
        for (int i=0; i<n; i++)
            wynik[i] += e[i];
    } while (norma(r,n)>eps && count <100);



    return wynik;

}



/*
int main(){

//    run3();
    cout.precision(17);
    int n = 0;
    double eps = 0;

    // wczytywanie danych
    cin >> n;
    Matrix a(n);
    Vector b(n);
    cin >> a >> b >> eps;

    cout<<a;

    Vector x = solveEquations(a, b, eps);

    auto residual = residual_vector(a, b, x);
    cout << "rozwiazanie = " << x << endl;
    cout << "rezydualny = " << residual << endl;
    cout << "blad = " << residual.max_norm()
         << " limit = " << eps << endl ;
    cout << "Test " << (residual.max_norm() < eps ? "":"nie ") << "zaliczony" << endl;
    return 0;
}



3
1 2 0 3 8 2 7 2 1
2 0 1
10
 */