package com.outcast.rpgskill.api.effect;

//===========================================================================================================
// An effect that will last for a certain amount of time that ticks at a given rate
//===========================================================================================================
public abstract class PeriodicEffect extends AbstractEffect {

    private long lastApplied;

    private long period;

    private int ticksRemaining;

    private boolean removed;

    protected PeriodicEffect(String id, String name, long period, int ticks, boolean isPositive) {
        super(id, name, isPositive);
        this.period = period;
        this.ticksRemaining = ticks;
        this.removed = false;
    }

    @Override
    public boolean canApply(long timestamp, ApplyableCarrier<?> character) {
        return lastApplied + period < timestamp && ticksRemaining > 0;
    }

    @Override
    public boolean apply(long timestamp, ApplyableCarrier<?> character) {
        lastApplied = timestamp;
        boolean result = apply(character);
        if (result) ticksRemaining--;
        return result;
    }

    @Override
    public boolean canRemove(long timestamp, ApplyableCarrier<?> character) {
        return removed || ticksRemaining == 0;
    }

    @Override
    public boolean remove(long timestamp, ApplyableCarrier<?> character) {
        return remove(character);
    }

    @Override
    public void setRemoved() {
        this.removed = true;
        this.ticksRemaining = 0;
    }

    protected abstract boolean apply(ApplyableCarrier<?> character);

    protected abstract boolean remove(ApplyableCarrier<?> character);

}