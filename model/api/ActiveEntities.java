package model.api;

public interface ActiveEntities extends Entities {
    void setLastTimeAttack(long lastTimeAttack);

    void receiveDamage(double damageReceived);

    long getLastTimeAttack();

    double getDamage();

    double getRemainingHealth();

    long getCoolDown();
}
