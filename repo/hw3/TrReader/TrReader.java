import net.sf.saxon.trace.XSLTTraceListener;

import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Xiuhui Ming
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */

    public TrReader(Reader str, String from, String to) {
        assert from != null;
        assert to != null;
        assert from.length() == to.length();

        this.src = str;
        this.from = from;
        this.to = to;
    }
    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
     */

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int srcRead = this.src.read(cbuf, off, len);
        for (int i = off; i < off + len; i++) {
            cbuf[i] = translate(cbuf[i]);
        }
        return Math.min(srcRead, len);
    }

    public char translate(char og) {
        int fromIndex = from.indexOf(og);
        if (fromIndex == -1) {
            return og;
        }
        return to.charAt(fromIndex);
    }
    @Override
    public void close() throws IOException {
        this.src.close();
    }

    private Reader src;
    private String from;
    private String to;
}
