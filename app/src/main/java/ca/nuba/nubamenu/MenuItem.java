package ca.nuba.nubamenu;

/**
 * Created by Borys on 15-10-13.
 */
public class MenuItem {
    public int picturePath;
    public int iconPath;
    public String name;
    public String price;
    public Boolean v;
    public Boolean ve;
    public Boolean gf;
    public String desc;
    public String featureType;
    public String type; //mezze, plate, pita etc

    public MenuItem(int picturePath, int iconPath, String name, String price, Boolean v, Boolean ve, Boolean gf, String desc) {
        this.picturePath = picturePath;
        this.iconPath = iconPath;
        this.name = name;
        this.price = price;
        this.v = v;
        this.ve = ve;
        this.gf = gf;
        this.desc = desc;
    }

    public MenuItem(int picturePath, int iconPath, String name, String price, Boolean v, Boolean ve, Boolean gf, String desc,
                    String featureType) {
        this(picturePath, iconPath, name,price,v,ve,gf,desc);
        this.featureType = featureType;
    }


}
