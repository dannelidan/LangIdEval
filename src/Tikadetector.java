import org.apache.tika.language.*;


public class Tikadetector extends LDL {

    private String description = "LDL library";

    public Tikadetector() {
        LanguageIdentifier.initProfiles();
    }

    public Tikadetector(String description) {
        LanguageIdentifier.initProfiles();
        this.description = description;
    }


    public void detect(String postInFocus) {
        long startTime;
        startTime = System.nanoTime();

        LanguageIdentifier ld = new LanguageIdentifier(postInFocus);

        this.guess = ld.getLanguage();
        this.elapsedTime = System.nanoTime() - startTime;


    }


    public String getDescription() {
        return description;
    }

}