package seedu.duck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ParserTest {

    String[] words = {"remove", "keyword"};

    @Test
    public void testIsNumeric() {
        assertTrue(Parser.isNumeric("2"));
        assertTrue(Parser.isNumeric("5"));
        assertFalse(Parser.isNumeric("letter"));
    }

    @Test
    public void testProcessKeywords() {
        assertEquals("keyword", Parser.processKeywords(words,1));
    }
}
