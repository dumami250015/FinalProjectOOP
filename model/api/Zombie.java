package model.api;

public interface Zombie extends ActiveEntities {
    void moveLeft();

    void setCanGo(boolean canGo);

    boolean isCanGo();
}
