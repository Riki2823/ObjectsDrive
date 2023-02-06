package villanueva.ricardo.Objects.Model;

public class File {
    private int id;
    private byte[] content;
    private String hash;


    public File(int id, byte[] content, String hash) {
        this.id = id;
        this.content = content;
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
