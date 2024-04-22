import java.util.List;

public class Auction {
    public Item item;
    public List<RealBidder> realBidders;
    public List<CommissionBidder> commissionBidders;

    public Auction(Item item, List<RealBidder> realBidders, List<CommissionBidder> commissionBidders) {
        this.item = item;
        this.realBidders = realBidders;
        this.commissionBidders = commissionBidders;
    }



}
