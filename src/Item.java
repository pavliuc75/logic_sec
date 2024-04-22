public class Item {
    public int startingPrice; //{⊥}
    public int step; //{⊥}
    public int currentPrice; //{⊥}
    public Bidder currentBidder; //{⊥}

    public Item(int startingPrice, int step) {
        this.startingPrice = startingPrice;
        this.step = step;
        this.currentPrice = startingPrice;
        this.currentBidder = null;
    }
}
