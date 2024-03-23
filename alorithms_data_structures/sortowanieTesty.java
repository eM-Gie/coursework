import java.util.Random;

public class sortowanieTesty {
    public static String radnomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 2;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static void gettest(int ile){
        Random random = new Random();

        System.out.println(ile);

        for(int ii=0; ii<ile; ii++) {

            int ileCol = random.nextInt(20);
            int ileRows = random.nextInt(20);
            System.out.println((ileCol - 1) + "," + 2 + "," + 1);

            for (int i = 0; i < ileCol; i++) {

                for (int j = 0; j < ileRows; j++) {
                    if (j % 2 == 0) {
                        System.out.print(radnomString());
                        if (j != ileRows - 1) System.out.print(",");
                    } else {
                        System.out.print(random.nextInt(100));
                        if (j != ileRows - 1) System.out.print(",");
                    }
                }
                System.out.println();
            }
        }

    }
    public static void main(String[] args) {
        gettest(1);
    }
}
