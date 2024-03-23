public class Kadane {
    // Function to find maximum sum rectangular
    // submatrix
    public static int[] kadan(int[] temp, boolean has_positive) {
        int curr=0, bc=0;
        int max=-1, bm=0, em=0, melem=0;
            if (has_positive) {
                for(int i=0; i< temp.length; i++) {
                    curr+=temp[i];

                    if (curr <= 0){
                    bc=i+1;
                    curr=0;
                }else{
                    if(curr>max){
                        max=curr;
                        bm=bc;
                        em=i;
                        melem = i-bm+1;
                    }else if(curr==max && melem>(i-bc+1)){
                        max=curr;
                        bm=bc;
                        em=i;
                        melem = i-bm+1;
                    }
                }
            }
            } else {
                for(int i=0; i<temp.length; i++){
                    if(temp[i] == 0) {
                        bm = i;
                        em = i;
                        max=0;
                        break;
                    }
                }
        }
            int[] wynik = new int[3];
            wynik[0] = bm;
            wynik[1] = em;
            wynik[2] = max;
            return wynik;
    }

    public static void main(String[] args)
    {
        int arr[]= new int[] { -1,-1};

        // Function call
       int[] wynik =  kadan(arr, false);
        System.out.println(wynik[0]+" "+wynik[1] + " "+wynik[2]);
    }
}
