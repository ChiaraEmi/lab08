package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {
    private static final String NAME = "Pippo Pippi";
    private static final String NAME2 = "Tizio Caio";
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";
    private static final String DEATH_CAUSE = "karting accident";
    private static final String DETAILS = "ran for too long";

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

    public void testCauseOfDeath() throws InterruptedException{
        try {
            blackList.writeDeathCause(DEATH_CAUSE);
            Assertions.fail("Writing a cause of death before writing a name was possible, but should have thrown an exception");
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
        }
        blackList.writeName(NAME);
        assertEquals(DEFAULT_DEATH_CAUSE, blackList.getDeathCause(NAME));
        blackList.writeName(NAME2);
        boolean setCause = blackList.writeDeathCause(DEATH_CAUSE);
        assertTrue(setCause);
        assertEquals(DEATH_CAUSE, blackList.getDeathCause(NAME2));
        Thread.sleep(100);
        boolean changeCause = blackList.writeDeathCause(DEFAULT_DEATH_CAUSE);
        assertFalse(changeCause);
        assertEquals(DEATH_CAUSE, blackList.getDeathCause(NAME2));
    }

    public void testDetailsOfDeath() throws InterruptedException{
        try {
            blackList.writeDetails(DETAILS);
            Assertions.fail("Writing the death details before writing a name was possible, but should have thrown an exception");
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
        }
        blackList.writeName(NAME);
        assertEquals("", blackList.getDeathDetails(NAME));
        boolean setDetails = blackList.writeDetails(DETAILS);
        assertTrue(setDetails);
        assertEquals(DETAILS, blackList.getDeathDetails(NAME));
        blackList.writeName(NAME2);
        Thread.sleep(6100);
        boolean changeDetails = blackList.writeDetails("bla bla bla");
        assertFalse(changeDetails);
        assertEquals("", blackList.getDeathDetails(NAME2));
    }
}
