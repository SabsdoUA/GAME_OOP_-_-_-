package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door {
    private boolean locked = true;

    public LockedDoor(Orientation orientation) {
        super(orientation);
    }

    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
    }

    public void lock() {
        locked = true;
        close();
    }

    public void unlock() {
        locked = false;
        open();
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void open() {
        if (locked) return;
        super.open();

        System.out.println("LockedDoor: "+isLocked());
    }
}
