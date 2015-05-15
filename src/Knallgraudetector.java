/**
 * Created with IntelliJ IDEA.
 * User: Dansof
 * Date: 2012-09-06
 * Time: 01:46
 * To change this template use File | Settings | File Templates.
 */
import org.knallgrau.utils.textcat.*;


public class Knallgraudetector extends LDL {

    private String description = "LDL library";
    private TextCategorizer tc;

    public Knallgraudetector() {
        tc = new TextCategorizer();

    }

    public Knallgraudetector(String description) {
        tc = new TextCategorizer();

        this.description = description;
    }


    public void detect(String postInFocus) {
        long startTime;
        startTime = System.nanoTime();


        this.guess = tc.categorize(postInFocus);
        this.elapsedTime = System.nanoTime() - startTime;


    }


    public String getDescription() {
        return description;
    }

}
