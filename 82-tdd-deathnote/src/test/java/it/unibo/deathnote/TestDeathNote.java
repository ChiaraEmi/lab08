package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final String NAME = "Pippo Pippi";
    private static final String NAME2 = "Tizio Caio";
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";
    private static final String DEATH_CAUSE = "karting accident";
    private static final String DETAILS = "ran for too long";
    private static final int SLEEP_TIME_1 = 100;
    private static final int SLEEP_TIME_2 = 6100;

    private final DeathNote blackList = new DeathNoteImpl();

    @Test
    void testZeroAndNegativeRules() {
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

    @Test
    void testEmptyOrNullRules() {
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testHumanWithNameWritten() {
        assertFalse(blackList.isNameWritten(NAME));
        blackList.writeName(NAME);
        assertTrue(blackList.isNameWritten(NAME));
        assertFalse(blackList.isNameWritten(NAME2));
        assertFalse(blackList.isNameWritten(""));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException {
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
        final boolean setCause = blackList.writeDeathCause(DEATH_CAUSE);
        assertTrue(setCause);
        assertEquals(DEATH_CAUSE, blackList.getDeathCause(NAME2));
        Thread.sleep(SLEEP_TIME_1);
        final boolean changeCause = blackList.writeDeathCause(DEFAULT_DEATH_CAUSE);
        assertFalse(changeCause);
        assertEquals(DEATH_CAUSE, blackList.getDeathCause(NAME2));
    }

    @Test
    void testDetailsOfDeath() throws InterruptedException {
        try {
            blackList.writeDetails(DETAILS);
            Assertions.fail("Writing the death details before writing a name was possible, but should have thrown an exception");
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
        }
        blackList.writeName(NAME);
        assertEquals("", blackList.getDeathDetails(NAME));
        final boolean setDetails = blackList.writeDetails(DETAILS);
        assertTrue(setDetails);
        assertEquals(DETAILS, blackList.getDeathDetails(NAME));
        blackList.writeName(NAME2);
        Thread.sleep(SLEEP_TIME_2);
        final boolean changeDetails = blackList.writeDetails("bla bla bla");
        assertFalse(changeDetails);
        assertEquals("", blackList.getDeathDetails(NAME2));
    }
}
