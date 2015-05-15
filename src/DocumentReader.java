import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class <code>DocumentReader</code> is used to parse blog posts
 * and it's correct language code, read from a textfile .
 *
 * @author Daniel Lindmark
 */

public class DocumentReader {

    /**
     * Reads data in blog post format
     * and produces a LinkedList of Blogpost containing the
     * normalized blog post String and the supposed
     * correct ISO-639-1 language code.
     *
     * @param thefile the file to be parsed.
     */
    public static LinkedList<Blogpost> readData(File thefile) throws FileNotFoundException {

        FileReader file = new FileReader(thefile);


        Scanner sc = new Scanner(file);

        LinkedList<Blogpost> blogposter = new LinkedList<Blogpost>();

        String lines = "";
        String lang = "";

        boolean started = false;
        while (sc.hasNext()) {

            String str = sc.next();

                     //Find the beginning of a Blog Item
            if (str.matches("BlogItem\\{rawText='.*")) {

                started = true;
                   //Split the initial string into array to ignore first meta data
                String[] tempstr = str.split("='");

                //Check if array
                if (tempstr.length == 2) {      // To avoid saving meta data when blog post is empty
                    if (!tempstr[1].matches("[\\[ :]?http(://.+)?")) {
                        lines = tempstr[1];
                    }
                }
            } else {

                if (started) {
                    if (str.matches("[\\[ :]?http(://.+)?")) {

                        continue;

                    } else if (str.matches(" ?language=[A-Z][A-Z],")) {  //Reached the end of blogpost
                        lang = str.substring(9, 11).toLowerCase(); //Save the language code string


                        blogposter.add(new Blogpost(lines, lang));


                        for (int titles = 0; titles < 9; titles++) {    //get past the remaining meta data
                            sc.next();
                        }


                    } else {     //no meta data - the content of the blog item that we want to save

                        str = str.replaceAll("[0-9]", " "); //Remove numbers

                        str = str.replaceAll("[^\\p{L}]", " ").trim().toLowerCase();
                        lines = lines + " " + str;    //Add words to the lines


                    }
                }

            }

        }

        return blogposter;


    }


}


