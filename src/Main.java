import util.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//roles:
//1. RealBidder
//2. CommissionBidder
//3. AuctionHouse

public class Main {
    public static void main(String[] args) {
        RealBidder realBidder1 = new RealBidder("C"); //{⊥}
        List<RealBidder> realBidders = new ArrayList<>(); //{⊥} {size is also ⊥}
        realBidders.add(realBidder1); //{⊥} -> {⊥}

        CommissionBidder commissionBidder1 = new CommissionBidder("A", 0, 500); // secr of AuctionHouse1
        CommissionBidder commissionBidder2 = new CommissionBidder("B", 0, 700); // secr of AuctionHouse1
        List<CommissionBidder> commissionBidders = new ArrayList<>(); // secr of AuctionHouse1, size is also secret
        commissionBidders.add(commissionBidder1); //secr of AuctionHouse1  -> secr of AuctionHouse1
        commissionBidders.add(commissionBidder2); //secr of AuctionHouse1  -> secr of AuctionHouse1

        Item item = new Item(50, 50); //{⊥}
        System.out.println("Action starts. Item has initial price of " + item.startingPrice + " Kr");

        doInitialBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        doBid(realBidder1, item);
        doBidOnBehalfOfCBidder(item, commissionBidders);
        endAuction(item);

        List<Bidder> allBidders = new ArrayList<>();
        allBidders.addAll(commissionBidders);
        allBidders.addAll(realBidders);
        Auction auction1 = new Auction(allBidders, realBidder1, true);

        AuctionHouse auctionHouse1 = new AuctionHouse(); // {⊥} (existence of auction 1 house is public)
        AuctionHouse auctionHouse2 = new AuctionHouse(); // {⊥} (existence of auction 2 house is public)

        auctionHouse1.addAuction(auction1);
        auctionHouse2.addAuction(new Auction(new ArrayList<>(), commissionBidder2, false));

        auctionHouse1.shareBidderReputations(auctionHouse2);
        auctionHouse2.updateReputationsOfBidders();
    }

    private static void endAuction(Item item) {
        System.out.println("---");
        System.out.println("Auction ends. " + item.currentBidder.name + " wins.");
        System.out.println("Final price: " + item.currentPrice + " Kr");
    }

    // process called with authority of AuctionHouse1
    public static void doInitialBidOnBehalfOfCBidder(Item item, List<CommissionBidder> commissionBidders) {
        if (commissionBidders.isEmpty()) {
            if (actsOfBehalfOf(AuctionHouse.class)) { //secr of AuctionHouse1 -> {⊥}
                System.out.println("There are no commision bidders {⊥}");
            }

            return;
        }
        ;

        commissionBidders.sort(Comparator.comparingInt(o -> o.highest)); //secr of AuctionHouse1 -> secr of AuctionHouse1

        CommissionBidder cBidderWithHighest = commissionBidders.get(commissionBidders.size() - 1); //secr of AuctionHouse1 -> secr of AuctionHouse1
        CommissionBidder cBidderWithSecondHighest = commissionBidders.get(commissionBidders.size() - 2); //secr of AuctionHouse1 -> secr of AuctionHouse1

        item.currentPrice = Math.min(cBidderWithSecondHighest.highest + item.step, cBidderWithHighest.highest); //secr of AuctionHouse1 -> {⊥}

        if (actsOfBehalfOf(AuctionHouse.class)) { //secr of CommisionBidder1 -> {⊥}
            cBidderWithHighest.declassifyBidderName(); //secr of CommisionBidder1 -> {⊥}
            item.uniqueNumberOfCurrentBidder = cBidderWithHighest.number; //{⊥} -> {⊥}

            System.out.println("action house bids for " + item.uniqueNumberOfCurrentBidder + ": " + item.currentPrice + " Kr");
        }
    }

    public static void doBid(RealBidder realBidder, Item item) {
        item.currentPrice += item.step;
        item.currentBidder = realBidder;

        System.out.println(realBidder.number + " bids " + item.currentPrice + " Kr");
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


    private static boolean actsOfBehalfOf(Class<AuctionHouse> auctionHouseClass) {
        return true;
    }

    public void communicateAuctionResults(Auction auction) {
        Bidder winner = auction.item.currentBidder; // {⊥}

        String finalPrice = auction.item.currentPrice + ""; // {⊥}
        String bidderNumber = winner.number + ""; // {⊥}
        String bidderName = winner.name; // {auctionHouse: auctionHouse, bidder}

        new ArrayList<>(Arrays.asList(finalPrice, bidderNumber, bidderName)).forEach(info -> Channel.write("publicChannel", info));
        //the last element will not be written to the channel, (will act as a filter)
        //on the other hand
        Channel.write("winnerBidderChannel", bidderName); //would work, since he is reader
    }
}