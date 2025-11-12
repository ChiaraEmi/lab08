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
            throw new IllegalArgumentException();
        }

        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name == null) {
            throw new NullPointerException();
        }
        Death newDeath = new Death(name, System.currentTimeMillis());
        listOfDeaths.put(name, newDeath);
        currentDeath = newDeath;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(currentDeath.name == null || cause == null) {
            throw new IllegalStateException();
        }
        
        if(System.currentTimeMillis() - currentDeath.timeName <= 40) {
            currentDeath.deathCause = cause;
            currentDeath.timeCause = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }
    
    private static final class Death {
        private final String DEFAULT_DEATH_CAUSE = "heart attack";
        private String name;
        private String deathCause;
        private String details;
        private long timeName;
        private long timeCause;

        private Death(final String name, final long time) {
            this.name = name;
            this.timeName = time;
            this.deathCause = DEFAULT_DEATH_CAUSE;
            this.timeCause = 0;
            this.details = "";
        }

        private Death(final String cause, final String details) {
            this.deathCause = cause;
            this.details = details;
        }

    }
}
