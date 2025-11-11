package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {
    private static final String NAME = "Pippo Pippi";
    private static final String NAME2 = "Tizio Caio";
    private DeathNote blackList = new DeathNoteImpl();

    public void testZeroAndNegativeRules(){
        try {
            blackList.getRule(0);
            Assertions.fail("Passing rule number 0 was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
        }

        try {
            blackList.getRule(-1);
            Assertions.fail("Passing a negative rule number was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
        }
    }

    public void testEmptyOrNullRules(){
        for(String rule : DeathNote.RULES){
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    public void testHumanWithNameWritten(){
        assertFalse(blackList.isNameWritten(NAME));
        blackList.writeName(NAME);
        assertTrue(blackList.isNameWritten(NAME));
        assertFalse(blackList.isNameWritten(NAME2));
        assertFalse(blackList.isNameWritten(""));
    }
}
