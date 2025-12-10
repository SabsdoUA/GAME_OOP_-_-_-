package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int ammo;
    private final int maxAmmo;

    public Firearm(int ammo, int maxAmmo) {
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int maxAmmo) {
        this.ammo = maxAmmo;
        this.maxAmmo = maxAmmo;
    }

    public int getAmmo() {
        return ammo;
    }

   public   void reload(int newAmmo){
        ammo = Math.min(newAmmo + ammo, maxAmmo);
    }

    public Fireable fire(){
        if (ammo<=0) return null;

        ammo--;
       return  createBullet();
    }

    protected abstract Fireable createBullet();
}
