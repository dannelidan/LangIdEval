import java.io.*;

/**
 * Runs tests of LDL libraries on data contained in
 * the specified file or directory provided as <code>args[0]</code>
 * when running this class.
 */


public class Testrunner {
    public static void main(String args[]) {

        if (args.length == 0 || args.length > 1) {
            System.out.println("Please provide a file or folder\nexiting...");
            System.exit(0);
        }

        Tester tester1 = new Tester();
        Tester tester2 = new Tester();
        Tester tester3 = new Tester();



        String path = args[0];
        File folder = new File(path);

        System.out.println("Loading library Shyuo");
        LDL library1 = new Shuyodetector("Langdetect library for java by Shuyo Nakatani");

        System.out.println("Loading library Tika");
        LDL library2 = new Tikadetector("Tika language detection library");


        System.out.println("Loading library Knallgrau");
        LDL library3 = new Knallgraudetector("Knallgrau textcat library");




        System.out.println("Testing " + library1.getDescription());
        tester1.runTest("shuyo", folder, library1);

        System.out.println("Testing " + library2.getDescription());
        tester2.runTest("tika", folder, library2);

        System.out.println("Testing " + library3.getDescription());
        tester3.runTest("knall", folder, library3);



    }


}