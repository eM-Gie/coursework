//Marianna Gawron
#include <iostream>
#include <cmath>
#include <cstdio>

using namespace std;
typedef void (*FuncPointer)(const double* x, double* y, double* Df);

void printVector(const double* x, unsigned N){
    for(unsigned i=0;i<N;++i)
        printf("%17.17f ",x[i]);
    printf("\n");
}


bool check(const double* y, int n) {

    switch (n)
    {
        case 1:
            return (abs(y[0]) <= 1e-14 );
        case 2:
            return (abs(y[0]) <= 1e-14 && abs(y[1]) <= 1e-14);
        default:
//            cout <<"default\n";
            break;
    }
    return false;
}

void compute(double* xcurr, const double*y, const double*df) {
    //metoda croutea
    double d1 = ( y[0]- df[0]*y[1]/df[3] ) / ( df[0]*df[1]-df[4]*df[0]/df[3] );
    double d0 = y[1]/df[3] - d1* (df[4]/df[3]);

    xcurr[0] -= d0;
    xcurr[1] -= d1;

}

void compute2(double* xcurr, const double*y, const double*df) {
    //metoda croutea
    double d1 = ( y[0]- df[0]*y[1]/df[4] ) / ( df[0]*df[1]-df[5]*df[0]/df[4] );
    double d0 = y[1]/df[4] - d1* (df[5]/df[4]);

    xcurr[0] -= d0;
    xcurr[1] -= d1;
}
int findCurve(FuncPointer f, double* x, unsigned k, double h){
    double y[2]={10,10};
    double df[6] = {};
    double xcurr[3] = {x[0],x[1],x[2]};

    for (int i=1; i<=k; i++){
        xcurr[2]+=h;
        y[0]=10; y[1]=10;
        do{
            double yold[2] = {y[0], y[1]};
            f(xcurr, y, df); // podstawia y i df
            if(abs(yold[0])<abs(y[0]) && abs(yold[1])<abs(y[1])) return i;
            compute(xcurr, y, df);// zmienia wartosci xcurr
        }while(!check(y,2));

        printVector(xcurr, 3);
    }
    return 0;
}

// b
int findSurface(FuncPointer f, double* x, unsigned k1, unsigned k2, double h1, double h2){

    double y[1] = {0}, df[3] = {};
    for (int i=0; i<k1; i++){
        double xcurr[3] = {x[0],x[1],x[2]};
        xcurr[1]+=h1*(i+1);
        for (int j=0; j<k2;  j++){
            xcurr[2]+=h2;
            do{
                f(xcurr,y,df);
                xcurr[0]-= y[0]/df[0];
            }while(!check(y,1));
            printVector(xcurr,3);
        }
        cout<<"\n";
    }
    return 0;

}

int findFixedPoints(FuncPointer f, double* x, unsigned k1, unsigned k2, double h1, double h2){
    double y[2] = {10,10};
    double df[8] = {};
    double xcurr[3] = {x[0],x[1],x[2]};


    for( int i=1; i<=k1; i++)
    {
        xcurr[1]+=h1;

        for (int j=1; j<=k2; j++)
        {
            xcurr[2]+=h2;
            y[0] = 10;y[1] = 10;
            do
            {
                cout<<"kjhjk\n";
                double yold[2] = {y[0], y[1]};
                f(xcurr, y, df);
//                if (abs(yold[0])  < abs(y[0]) && abs(yold[1])  < abs(y[1])) return i*k1+j;
                double special[2] = {xcurr[0]-y[0],xcurr[1]-y[1]};
                compute2(xcurr, special, df);// zmienia wartosci xcurr

            } while(!( abs(y[0]-x[0]) <= 1e-14 && abs(y[1]-x[1]) <= 1e-14 ));
            printVector(xcurr,4);
        }
        cout<<"\n";
    }
    return 0;
}



void implicitCurve(const double* x, double* y, double* Df){
    // funkcja dana jest wzorem f(a,b,c) = (1-a^2-b^2-c^2,(a+b+c)/(a^2+b^2+c^2)-1)
    // zmienne pomocnicze
    const double n = x[0]*x[0] + x[1]*x[1] + x[2]*x[2];
    const double r = 1./n;
    const double s = (x[0] + x[1] + x[2])*r;

    // obliczam wartosc funkcji
    y[0] = 1. - n;
    y[1] = s - 1.;

    //obliczam pierwszy wiersz macierzy
    Df[0] = -2.*x[0];
    Df[1] = -2.*x[1];
    Df[2] = -2.*x[2];

    //obliczam drugi wiersz macierzy
    const double r2 = 2.*s*r;
    Df[3] = r - x[0]*r2;
    Df[4] = r - x[1]*r2;
    Df[5] = r - x[2]*r2;
}

void implicitSurface(const double* x, double* y, double* Df){
    // funkcja dana jest wzorem f(a,b,c) = (a+b+c)/(a^2+b^2+c^2)-1
    // zmienne pomocnicze
    const double n = x[0]*x[0] + x[1]*x[1] + x[2]*x[2];
    const double s = x[0] + x[1] + x[2];

    // obliczam wartosc funkcji
    *y = s/n - 1.;

    //obliczam pierwszy i jedyny wiersz macierzy
    const double r = 1./n;
    const double r2 = 2.*(*y)*r;
    Df[0] = r - x[0]*r2;
    Df[1] = r - x[1]*r2;
    Df[2] = r - x[2]*r2;
}

void henon(const double* x, double* y, double* Df){
    // funkcja dana jest wzorem henon(x,y,a,b) = (1+y-a*x^2,b*x)
    const double x2 = x[0]*x[0];

    y[0] = 1 + x[1] - x[2]*x2;
    y[1] = x[3]*x[0];

    //obliczam pierwszy wiersz macierzy
    Df[0] = -2*x[2]*x[0];
    Df[1] = 1.;
    Df[2] = -x2;
    Df[3] = 0.;

    //obliczam drugi wiersz macierzy
    Df[4] = x[3];
    Df[5] = 0.;
    Df[6] = 0.;
    Df[7] = x[0];
}



/*
int main() {

//    double x[3] = {0.25*(1.+sqrt(5.)),0.25*(1.-sqrt(5.)),0.5};
//    cout<<findSurface(implicitSurface,x,4,4,1./32,1./32);
//    return 0;

//    double x[3] = {0.25*(1.+sqrt(5.)),0.25*(1.-sqrt(5.)),0.5};
//    cout<< findCurve(implicitCurve,x,10,1./128);
//    printf("\n");
//    double x2[3] = {0.25*(1.-sqrt(5.)),0.25*(1.+sqrt(5.)),0.5};
//    int i = findCurve(implicitCurve,x2,10,3./32);
//    printf("%d",i);


    double x[4] = {-1.2807764064044151, -0.6403882032022076, 1.0000000000000000, 0.50000000000000000};
    findFixedPoints(henon,x,4,4,1./16,1./16);
    return 0;
    return 0;

}
*/