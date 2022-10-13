package com.outcast.rpgskill.api.effect;

//===========================================================================================================
// An effect that will last for a certain amount
//===========================================================================================================
public abstract class TemporaryEffect extends AbstractEffect {

    private long timeApplied;
    private long duration;
    private boolean applied;
    private boolean removed;

    protected TemporaryEffect(String id, String name, long duration, boolean isPositive) {
        super(id, name, isPositive);
        this.duration = duration;
        this.applied = false;
        this.removed = false;
    }

    @Override
    public boolean canApply(long timestamp, ApplyableCarrier<?> character) {
        return !applied;
    }

    @Override
    public boolean apply(long timestamp, ApplyableCarrier<?> character) {
        this.timeApplied = timestamp;
        this.applied = true;
        return apply(character);
    }

    @Override
    public boolean canRemove(long timestamp, ApplyableCarrier<?> character) {
        return (timestamp + duration < timestamp && applied) || removed;
    }

    @Override
    public boolean remove(long timestamp, ApplyableCarrier<?> character) {
        return remove(character);
    }

    @Override
    public void setRemoved() {
        this.removed = true;
    }

    protected abstract boolean apply(ApplyableCarrier<?> character);

    protected abstract boolean remove(ApplyableCarrier<?> character);

}
