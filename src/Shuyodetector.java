import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;


public class Shuyodetector extends LDL {

    private String description = "LDL library";


    public Shuyodetector() {
        try {

            DetectorFactory.loadProfile("C:\\javacode\\Langid\\src\\profiles\\langdetect");

        } catch (LangDetectException e) {

            System.out.println("Unable to load profiles");
            //System.exit(0);
        }


    }

    public Shuyodetector(String description) {

        this.description = description;

        try {

            DetectorFactory.loadProfile("C:\\Users\\Daniel\\Desktop\\Langid\\src\\profiles\\langdetect");

        } catch (LangDetectException e) {

            System.out.println("Unable to load profiles");
           System.exit(1);
        }


    }

    public void detect(String postInFocus) {
        long startTime;


        startTime = System.nanoTime();

        try {
            Detector detector = DetectorFactory.create();

            detector.append(postInFocus);

            this.guess = detector.detect();
        } catch (LangDetectException e) {

            System.err.println("unable to detect: " + postInFocus);
        }
        this.elapsedTime = System.nanoTime() - startTime;


    }

    public String getDescription() {
        return description;
    }


}