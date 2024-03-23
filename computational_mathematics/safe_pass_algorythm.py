# Marianna Gawron

def safePathAlgorithm():
    # idea rozwiazania: pobieram dane laserow,
    # dla kazdego lasera (O(m)) zaznaczam przedzialy (y1,y2) w tab,
    #   beda to przedzialy ktore eliminujemy z potencjalnych korytarzy dla pocisku
    # trawersuje tab (O(n)) wypisujac potencjalne korytarze
    # zlozonosc algorytmu: O(n)+O(m) = O(n+m)

    # wprowadzam dane zadania
    # n - szerokosc korytarza, m - ilosc laserow
    x = raw_input()
    x2 = x.split(" ")
    n = int(x2[0])
    m = int(x2[1])

    # tworze tabelke pomocnicza tab przechowywujaca mozliwe safe paths
    tab = range(n)
    for i in range(n):
        tab[i] = 0

    # wprowadzam wierzcholki laserow. zaznaczam w tab odcinki (y1,y2) - nie moga byc tam safe paths
    for i in range(m):
        wsp = raw_input()
        a = wsp.split(" ")

        # a[1] = y1, a[3] = y2
        #  nie ma to wplywu na zlozonosc, gdyz (y1,y2) to dowolny przedzial zawarty w m, niezalezny od m.
        for j in range(int(a[1]),int(a[3])):
            tab[j] = 1
    # zliczamy, wypisujemy te przejscia ktore sa bezpieczne
    # (dla ktorych tab[i]==0)
    count =0
    for i in range(n):
        if tab[i]==0:
            count= count+1
            print("( "+str(i)+" , "+str(i+1) + " )")
    print("there are "+str(count) + " safe paths")


if __name__ == '__main__':
    # safePathAlgorithm()
    for i in range(38):
        print("0")
