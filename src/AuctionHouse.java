import java.util.HashMap;

public class AuctionHouse {
    public HashMap<String, Integer> reputationsOfBidders = new HashMap<>(); //{thisAuctionHouse: thisAuctionHouse}

    public void addAuction(Auction auction) {
        Bidder winner = auction.winner;

        boolean isWinnerInHM = reputationsOfBidders.containsKey(winner.uniqueNumber);

        if (auction.didBidderPay) {
            if (isWinnerInHM) {
                reputationsOfBidders.put(winner.uniqueNumber, reputationsOfBidders.get(winner.uniqueNumber) + 1);
            } else {
                reputationsOfBidders.put(winner.uniqueNumber, 1);
            }
        } else {
            if (isWinnerInHM) {
                reputationsOfBidders.put(winner.uniqueNumber, reputationsOfBidders.get(winner.uniqueNumber) - 1);
            } else {
                reputationsOfBidders.put(winner.uniqueNumber, -1);
            }
        }
    }

    public void shareOwnReputationOfBidders(AuctionHouse targetAuctionHouse) {
        targetAuctionHouse.reputationsOfBidders.forEach((key, value) -> {
            if (reputationsOfBidders.containsKey(key)) {
                reputationsOfBidders.put(key, reputationsOfBidders.get(key) + value);
            } else {
                reputationsOfBidders.put(key, value);
            }
        });
    }
}
