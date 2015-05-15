/**
 * Class <code>Blogpost</code> represents a blog post
 * parsed by DocumentReader.
 *
 * @author Daniel Lindmark
 */
public class Blogpost {

    private String post;
    private String langcode;

    /**
     * Creates an empty Blogpost
     */
    public Blogpost() {

    }

    /**
     * Creates a new Blogpost containing only a String of text
     * without any information about the language yet.
     *
     * @param post the text of the blog post to be analyzed.
     */
    public Blogpost(String post) {
        this.post = post;

    }

    /**
     * Creates a new Blogpost containing a String of text
     * and a language code supposed to be the correct one.
     *
     * @param post     the text of the blog post to be analyzed.
     * @param langcode the correct ISO-639-1 code of the language.
     */
    public Blogpost(String post, String langcode) {
        //this.index = index;
        this.post = post;
        this.langcode = langcode;

    }

    /**
     * @return the String of the parsed blog post
     */

    public String getPost() {
        return post;
    }

    /**
     * @return the correct ISO-839 language code
     */
    public String getLangCode() {

        return langcode;
    }

}