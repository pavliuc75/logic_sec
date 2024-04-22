import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RealBidder realBidder1 = new RealBidder("C", 0);
        List<RealBidder> realBidders = new ArrayList<>();
        realBidders.add(realBidder1);

        CommissionBidder commissionBidder1 = new CommissionBidder("A", 0, 500);
        CommissionBidder commissionBidder2 = new CommissionBidder("B", 0, 700);
        List<CommissionBidder> commissionBidders = new ArrayList<>();
        commissionBidders.add(commissionBidder1);
        commissionBidders.add(commissionBidder2);

        Item item = new Item(50, 50);
        System.out.println("Action starts. Item has initial price of " + item.startingPrice + " Kr");

        doInitialBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        endAuction(item);
    }

    private static void endAuction(Item item) {
        System.out.println("---");
        System.out.println("Auction ends. " + item.currentBidder.name + " wins.");
        System.out.println("Final price: " + item.currentPrice + " Kr");

    }

    public static void doInitialBidOnBehalfOfCBidder(Item item, List<CommissionBidder> commissionBidders) {
        if (commissionBidders.isEmpty()) return;

        commissionBidders.sort(Comparator.comparingInt(o -> o.highest));

        CommissionBidder cBidderWithHighest = commissionBidders.get(commissionBidders.size() - 1);
        CommissionBidder cBidderWithSecondHighest = commissionBidders.get(commissionBidders.size() - 2);

        item.currentPrice = Math.min(cBidderWithSecondHighest.highest + item.step, cBidderWithHighest.highest);
        item.currentBidder = cBidderWithHighest;

        cBidderWithHighest.declassifyBidderName();
        System.out.println("action house bids for " + item.currentBidder.name + ": " + item.currentPrice + " Kr");
    }

    public static void doBid(RealBidder realBidder, Item item) {
        item.currentPrice += item.step;
        item.currentBidder = realBidder;

        System.out.println(realBidder.name + " bids " + item.currentPrice + " Kr");
    }

    public static void doBidOnBehalfOfCBidder(Item item, List<CommissionBidder> commissionBidders) {
        commissionBidders.sort(Comparator.comparingInt(o -> o.highest));
        CommissionBidder cBidderWithHighest = commissionBidders.get(commissionBidders.size() - 1);

        if (item.currentPrice == cBidderWithHighest.highest) {
            item.currentBidder = cBidderWithHighest;
            cBidderWithHighest.declassifyHighest();
            System.out.println("action house bids for " + item.currentBidder.name + ": " + item.currentPrice + " Kr");
        } else if (item.currentPrice < cBidderWithHighest.highest) {
            item.currentPrice += item.step;
            item.currentBidder = cBidderWithHighest;
            System.out.println("action house bids for " + item.currentBidder.name + ": " + item.currentPrice + " Kr");
        } else {
            cBidderWithHighest.declassifyHighest();
        }
    }
}