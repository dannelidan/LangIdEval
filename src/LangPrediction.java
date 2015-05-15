/**
 * This class represents a prediction made by the DLD library
 * that is being tested by {@link Tester}.
 */

public class LangPrediction {

    private String predictedLangCode;
    private String corrLangCode;
    private long nanotime;
    private int charcount;
    private int index;

    public LangPrediction() {

    }

    /**
     * @param predictedLangCode the language code expressed in iso 639-1 format.
     * @param corrLangCode      the correct language code.
     * @param nanotime          the time taken in nanoseconds for a certain
     *                          detector to detect the language of a blog post.
     * @param charcount         the number of bytes of the string detected.
     * @param index             the place index within the original LinkedList produced by
     *                          {@link DocumentReader#readData} of the analyzed blog post.
     */

    public LangPrediction(int index, String predictedLangCode, String corrLangCode, long nanotime, int charcount) {
        this.index = index;
        this.predictedLangCode = predictedLangCode;
        this.corrLangCode = corrLangCode;
        this.nanotime = nanotime;
        this.charcount = charcount;

    }

    /**
     * @return the predicted ISO-639-1 language code
     */

    public String getPredictedLangCode() {

        return predictedLangCode;

    }

    /**
     * @return the correct ISO-639-1 language code.
     */
    public String getCorrLangCode() {

        return corrLangCode;
    }

    /**
     * @return the time in nanoseconds
     *         taken to detect the language of a blog post.
     */

    public long getNanoTime() {

        return nanotime;
    }

    /**
     * @return the number of bytes (UTF-8)
     *         for a certain blog post.
     */

    public int getCharCount() {
        return charcount;

    }

    /**
     * return the place index within the original LinkedList.
     */
    public int getIndex() {
        return index;

    }

    public void setPredictedLangCode(String predictedLangCode) {

        this.predictedLangCode = predictedLangCode;

    }


    public void setNanoTime() {
        this.nanotime = nanotime;

    }

    public void setCharCount(int charcount) {
        this.charcount = charcount;

    }

    public void setIndex(int index) {
        this.index = index;
    }

}