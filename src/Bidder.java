public abstract class Bidder {
    public String name;
    public int reputation; //0-10

    public Bidder(String name, int reputation) {
        this.name = name;
        this.reputation = reputation;
    }
}
