import java.util.List;

public class Auction {
    public List<Bidder> participatedBidders;
    public Bidder winner;
    public boolean didBidderPay;

    public Auction(List<Bidder> participatedBidders, Bidder winner, boolean didBidderPay) {
        this.participatedBidders = participatedBidders;
        this.winner = winner;
        this.didBidderPay = didBidderPay;
    }
}
