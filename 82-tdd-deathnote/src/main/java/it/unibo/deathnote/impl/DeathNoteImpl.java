package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{
    private Map<String, Death> listOfDeaths = new HashMap<>();
    private Death currentDeath;

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Illegal rule numer: " + ruleNumber);
        }

        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name == null) {
            throw new NullPointerException();
        }
        Death newDeath = new Death(System.currentTimeMillis());
        listOfDeaths.put(name, newDeath);
        currentDeath = newDeath;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(currentDeath == null) {
            throw new IllegalStateException("No name has been written yet in the DeathNote");
        }

        if(cause == null) {
            throw new IllegalStateException();
        }
        
        long timeNow = System.currentTimeMillis();

        if(timeNow - currentDeath.timeName <= 40) {
            currentDeath.deathCause = cause;
            currentDeath.timeDeath = timeNow;
            return true;
        }

        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if(currentDeath == null) {
            throw new IllegalStateException("No name has been written yet in the DeathNote");
        }

        if(details == null) {
            throw new IllegalStateException();
        }
        
        if(currentDeath.timeDeath != 0) {
            long timeNow = System.currentTimeMillis();

            if(timeNow - currentDeath.timeDeath <= 6040) {
                currentDeath.details = details;
                return true;
            }
        }

        return false;
    }

    @Override
    public String getDeathCause(String name) {
        return checkArgument(name).deathCause;
    }

    @Override
    public String getDeathDetails(String name) {
        return checkArgument(name).details;
    }

    @Override
    public boolean isNameWritten(String name) {
        return listOfDeaths.containsKey(name);
    }

    private Death checkArgument(String argument) {
        Death d = listOfDeaths.get(argument);
        if(d == null) {
            throw new IllegalArgumentException();
        }
        return d;
    }
    
    private static final class Death {
        private final String DEFAULT_DEATH_CAUSE = "heart attack";
        private String deathCause;
        private String details;
        private long timeName;
        private long timeDeath;

        private Death(final long time) {
            this.timeName = time;
            this.deathCause = DEFAULT_DEATH_CAUSE;
            this.timeDeath = time;
            this.details = "";
        }
    }
}
