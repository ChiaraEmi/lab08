package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

/**
 * DeathNoteImpl implements DeathNote interface.
 */
public final class DeathNoteImpl implements DeathNote {
    private static final long DEATH_TIME_WINDOW = 40;
    private static final long DETAILS_TIME_WINDOW = 6040;
    private final Map<String, Death> listOfDeaths = new HashMap<>();
    private Death currentDeath;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Illegal rule numer: " + ruleNumber);
        }

        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException(); //NOPMD suppressed because this behavior is required by the project specifications 
        }
        final Death newDeath = new Death(System.currentTimeMillis());
        listOfDeaths.put(name, newDeath);
        currentDeath = newDeath;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (currentDeath == null) {
            throw new IllegalStateException("No name has been written yet in the DeathNote");
        }

        if (cause == null) {
            throw new IllegalStateException();
        }

        final long timeNow = System.currentTimeMillis();

        if (timeNow - currentDeath.timeName <= DEATH_TIME_WINDOW) {
            currentDeath.deathCause = cause;
            currentDeath.timeDeath = timeNow;
            return true;
        }

        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if (currentDeath == null) {
            throw new IllegalStateException("No name has been written yet in the DeathNote");
        }

        if (details == null) {
            throw new IllegalStateException();
        }

        if (currentDeath.timeDeath != 0) {
            final long timeNow = System.currentTimeMillis();

            if (timeNow - currentDeath.timeDeath <= DETAILS_TIME_WINDOW) {
                currentDeath.details = details;
                return true;
            }
        }

        return false;
    }

    @Override
    public String getDeathCause(final String name) {
        return checkArgument(name).deathCause;
    }

    @Override
    public String getDeathDetails(final String name) {
        return checkArgument(name).details;
    }

    @Override
    public boolean isNameWritten(final String name) {
        return listOfDeaths.containsKey(name);
    }

    private Death checkArgument(final String argument) {
        final Death d = listOfDeaths.get(argument);
        if (d == null) {
            throw new IllegalArgumentException();
        }
        return d;
    }

    private static final class Death {
        private static final String DEFAULT_DEATH_CAUSE = "heart attack";
        private String deathCause;
        private String details;
        private final long timeName;
        private long timeDeath;

        private Death(final long time) {
            this.timeName = time;
            this.deathCause = DEFAULT_DEATH_CAUSE;
            this.timeDeath = time;
            this.details = "";
        }
    }
}
