package techguns.util;

public class EntityPosInd {
    public int x;
    public int y;
    public int z;
    public int index;

    public EntityPosInd(int x, int y, int z, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.index = index;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z + "," + index;
    }
}