import util.Util;

import java.util.HashMap;

public class AuctionHouse {
    public HashMap<String, Integer> reputationsOfBiddersDb = new HashMap<>(); //{thisAuctionHouse: thisAuctionHouse}

    public HashMap<String, Integer> newlyReceivedReputationsOfBidders = new HashMap<>(); //{⊥}

    public void addAuction(Auction auction) {
        Bidder winner = auction.winner;

        boolean isWinnerInHM = reputationsOfBiddersDb.containsKey(winner.uniqueNumber);

        if (auction.didBidderPay) {
            if (isWinnerInHM) {
                reputationsOfBiddersDb.put(winner.uniqueNumber, reputationsOfBiddersDb.get(winner.uniqueNumber) + 1);
            } else {
                reputationsOfBiddersDb.put(winner.uniqueNumber, 1);
            }
        } else {
            if (isWinnerInHM) {
                reputationsOfBiddersDb.put(winner.uniqueNumber, reputationsOfBiddersDb.get(winner.uniqueNumber) - 1);
            } else {
                reputationsOfBiddersDb.put(winner.uniqueNumber, -1);
            }
        }
    }

    //todo should it go thru public stage?
    public void shareReputationAboutBidders(AuctionHouse targetAuctionHouse) {
        HashMap<String, Integer> toBeShared = new HashMap<>(); //{⊥}

        toBeShared = reputationsOfBiddersDb; //{⊥} -> {thisAuctionHouse: thisAuctionHouse}
        //{thisAuctionHouse: thisAuctionHouse} is the new label of toBeShared

        if (Util.actsFor("shareReputationAboutBidders", "thisAuctionHouse")) {
            Util.declassify(toBeShared); // {thisAuctionHouse: thisAuctionHouse} -> {⊥}
            //{⊥} is the new label of toBeShared

            targetAuctionHouse.newlyReceivedReputationsOfBidders = toBeShared; //{⊥} -> {⊥}
            //newlyReceivedReputationsOfBidders is a temporary variable (just so it works)
        }
    }

    public void updateReputationsOfBidders() {
        reputationsOfBiddersDb.forEach((key, value) -> {
            if (newlyReceivedReputationsOfBidders.containsKey(key)) {
                reputationsOfBiddersDb.put(key, reputationsOfBiddersDb.get(key) + newlyReceivedReputationsOfBidders.get(key));
            } else {
                reputationsOfBiddersDb.put(key, value);
            }
        });
    }
}