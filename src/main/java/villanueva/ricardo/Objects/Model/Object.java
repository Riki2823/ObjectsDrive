package villanueva.ricardo.Objects.Model;

public class Object {
    private int id;
    private String name;
    private String owner;
    private String bucketSrcName;
    private String date;

    public Object(int id, String name, String owner, String bucketSrcName) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.bucketSrcName = bucketSrcName;
    }
}
