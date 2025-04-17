package model.api;

public interface Bullet extends Entities {
    double getSpeed();

    double getDamage();

    void move();
}
