//Marianna Gawron


import jdk.swing.interop.SwingInterOpUtils;

// klasa Node2 implementujaca wezly,
// trzyma "wskazniki na dzieci oraz wartosc key
class Node2 {
    float key;
    Node2 left, right;

    public Node2(float item) {
        key = item;
        left = right = null;
    }
}

// wlasciwa klasa implementujaca drzewo bst
// przetrzymuje korzen drzewa oraz metody. posiada tez main

public class btsZadanie {

    static Node2 root;// korzen

    btsZadanie() {
        root = null;
    } // konstruktory

    btsZadanie(int value) {
        root = new Node2(value);
    }

    // wywoluluje funkcje insertRec
    void insertKey(float key) {
        root = theActualInsertMethod(root, key);
    }

    //wlasciwa funkcja wstawiajaca element dzialajaca rekursywnie
    Node2 theActualInsertMethod(Node2 root, float key) {

        //jezeli drzewo jest puste to tworzymy nowy node ktory staje sie korzeniem
        if (root == null) {
            root = new Node2(key);
            return root;
        }

        // jezeli drzewo nie jest puste to szukamy miejsca w ktore nalezy je wstawic
        //wywolujemy rekursywnie ta metode dla prawego/ lewego dziecka
        // w zaleznosci od tego czy aktualny key jest wiekszy / mniejszy
        if (key < root.key)
            root.left = theActualInsertMethod(root.left, key);
        else if (key > root.key)
            root.right = theActualInsertMethod(root.right, key);

        return root;
    }


    // wywoluje wlasciwa funkcje usuwajaca element
    void callToDelete(float key) {
        root = theRealDeletingMethod(root, key);
    }

// funkcja rekursywnie szuka odpowiedniego elementu nastepnie podejmuje odpowiednie kroki zeby usunac
    //mamy rozne przypadki usuwania :
    // element ktory chcemy usunac nie ma dzieci
    // element ma jedno dziecko
    // element ma dwojke dzieci
    Node2 theRealDeletingMethod(Node2 root, float key) {
        if (root == null)// jezeli drzewo puste nie mamy czego usuwac
            return root;

        // szukamy teraz odpowiedniego elementu do usuniecia
        if (key < root.key)
            root.left = theRealDeletingMethod(root.left, key);
        else if (key > root.key)
            root.right = theRealDeletingMethod(root.right, key);

        // jezeli nie jest zadnym z powyzszych przypadkow  to znalezlismy nasz szukany element
        else {
            // Jezeli szukany element nie ma dzieci albo ma jedno dziecko
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // czyli ma dwojke dzieci
            // zamieniamy go z nastepnym w kolejnosci elementem

            root.key = minElement(root.right);
            root.right = theRealDeletingMethod(root.right, root.key);
        }

        return root;
    }

    // to tylko wywyolywuje prawdziwy inorder
    void inorder() {
        System.out.println("INORDER");
        prawdziwyInorder(root);
        System.out.println();
    }

    //prawdziwy instorder, dzialajacy rekursywnie. wypisujemy kolejno od najmniejszego do najwiekszego
    void prawdziwyInorder(Node2 root) {
        if (root != null) {
            prawdziwyInorder(root.left);
            System.out.print(root.key+"  ");
            prawdziwyInorder(root.right);
        }
    }

    //tylko wywyolywuje prawdziwy postorder dzialajacy rekursywnie
    void postorder(){
        System.out.println("POSTORDER: ");
        prawdziwyPostOrder(root);
        System.out.println();
    }

    // prawdziwy postorder
    // najpierw wypisuje rzeczy mniejsze potem rzeczy wieksze, a dopiero na koncu samego rozwazanego nodea
    void prawdziwyPostOrder(Node2 root) {
        if (root != null) {
            prawdziwyInorder(root.left);
            prawdziwyInorder(root.right);
            System.out.print(root.key+"  ");
        }
    }

    //zwraca najwieksza wartosc w drzewie
    static float maxElement(Node2 rt) {
        Node2 curr = rt;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.key;
    }

    // zwraca namjmniejsza wartosc w tablece
    static float minElement(Node2 rt) {
        Node2 curr = rt;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.key;
    }

    public static void main(String[] args) {
        btsZadanie tree = new btsZadanie();

        tree.insertKey(5F);
        tree.insertKey(3.0F);
        tree.insertKey(2.0F);
        tree.insertKey(3.14159265358979323846264338F);
        tree.insertKey(2.71828F);
        tree.insertKey(1.414F);
        tree.insertKey(1.73205080757F);

        tree.inorder();
        System.out.println();

        tree.postorder();
        System.out.println();

        System.out.println("min element is: " + minElement(root));
        System.out.println("max element is: " + maxElement(root));

        tree.callToDelete(1.73205080757F);
        tree.callToDelete(5F);

        tree.inorder();
        System.out.println("min element is: " + minElement(root));
        System.out.println("max element is: " + maxElement(root));

    }
}

