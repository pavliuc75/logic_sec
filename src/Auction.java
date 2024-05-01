import java.util.List;

public class Auction {
    public Item item; // {⊥}
    public AuctionHouse hostingAuctionHouse; // {⊥}
    public List<Bidder> participatedBidders;
    public boolean didBidderPay;

    public Auction(List<Bidder> participatedBidders, boolean didBidderPay) {
        this.participatedBidders = participatedBidders;
        this.didBidderPay = didBidderPay;
        this.hostingAuctionHouse = null;
    }

    public void addBidder(Bidder bidder) {

// name:{bidder:bidder}
// -> {bidder: bidder, actionHouses: actionHouses}
        participatedBidders.add(bidder);
    }
}
