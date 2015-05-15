/**
 * Abstract class <code>LDL</code> provides a framework
 * to easily add and test new Language Detection libraries.
 */


public abstract class LDL {

    protected String guess = "";
    protected long elapsedTime = 0;

    /**
     * Implementation of this class depends on the LDL to test.
     *
     * @param postInFocus the String to be guessed by the LDL.
     */

    public abstract void detect(String postInFocus);

    /**
     * @return a name or description of the LDL
     */

    public abstract String getDescription();

    /**
     * @return the language guess made by the LDL
     *         expressed as an ISO-639-1 language code.
     */

    public String getLangGuess() {

        return guess;
    }

    /**
     * @return the time taken for the LDL
     *         to make a language guess.
     */
    public long getDetectionTime() {
        return elapsedTime;

    }


}