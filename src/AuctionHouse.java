import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionHouse {
    public List<BidderReputation> biddersReputations; //{thisAuctionHouse: thisAuctionHouse}
    public List<Map.Entry<String, Integer>> tempBidderReputations; //{thisAuctionHouse: thisAuctionHouse}


    //todo explain step by step how the labels are derived (from middle)
    public void shareBidderReputations(AuctionHouse targetAuctionHouse) {
        List<Map.Entry<String, Integer>> reputationsToBeShared = new ArrayList<>(); //{thisAuctionHouse: thisAuctionHouse}

        int i = 0; //{thisAuctionHouse: thisAuctionHouse}
        while (i < biddersReputations.size()) { //{thisAuctionHouse: thisAuctionHouse} (security label of entire body)
            Map.Entry<String, Integer> entry; //{⊥}

            //todo say that this is was drives the labels
            int tempRep = biddersReputations.get(i).reputation; //{thisAuctionHouse: thisAuctionHouse}
            String tempName = biddersReputations.get(i).name; //{thisAuctionHouse: thisAuctionHouse, bidder}

            entry = Map.entry(tempName, tempRep); //{thisAuctionHouse: thisAuctionHouse}  (E1 op E2 = E1 U E2) without biider

            reputationsToBeShared.add(entry);

            i++; //{thisAuctionHouse: thisAuctionHouse} (database size is secret)
        }

        //now we actually need to share the reputations, and we observe that labels are different, so we need todeclassify

        if (Util.actsFor("shareReputationAboutBidders", "thisAuctionHouse")) {
            Util.declassify(reputationsToBeShared, "{⊥}");
            //⊥ is the new label of reputationsToBeShared
            //todo mention that actually every single element in the list has to be declassified as well but we skip that for simplicity

            targetAuctionHouse.tempBidderReputations = reputationsToBeShared;
            //when this happens. first, the other action house applies 1.restriction (adds: {theOtherActionHouse: })
            //2. declassification, by adding himself as owner (mby add that not sure mby this is just how it works.)
        } else {
            System.out.println("Procedure needs to be called with authority of thisAuctionHouse.");
        }
    }

    //TODO add that more flows can be derived e.g. tempName -> entry (but all the essential flows are is shown)
    public void shareBidderReputations_(AuctionHouse targetAuctionHouse) {
        List<Map.Entry<String, Integer>> reputationsToBeShared = new ArrayList<>();

        int i = 0;
        while (i < biddersReputations.size()) {
            Map.Entry<String, Integer> entry;

            int tempRep = biddersReputations.get(i).reputation;         //biddersReputations.get(i).reputation -> tempRep   //i -> tempRep
            String tempName = biddersReputations.get(i).name;           //biddersReputations.get(i).name -> tempName        //i -> tempName

            entry = Map.entry(tempName, tempRep);                       //tempName -> entry, tempRep -> entry               //i -> entry

            reputationsToBeShared.add(entry);                           //entry -> reputationsToBeShared                    //i -> reputationsToBeShared

            i++;                                                        //i -> i                                            //biddersReputations.size() -> i
        }

        targetAuctionHouse.tempBidderReputations = reputationsToBeShared; //reputationsToBeShared -> targetAuctionHouse.tempBidderReputations
    }

    public void shareBidderReputations__(AuctionHouse targetAuctionHouse) {
        List<Map.Entry<String, Integer>> reputationsToBeShared = new ArrayList<>(); //new ArrayList<>() <= reputationsToBeShared (variable assignment statement)

        int i = 0;                                //0 <= i (variable assignment statement)
        while (i < biddersReputations.size()) {    //(i < biddersReputations.size()) <= body (while statement)
            Map.Entry<String, Integer> entry; //nothing here, just declaration

            int tempRep = biddersReputations.get(i).reputation;         //biddersReputations.get(i).reputation <= tempRep (variable assignment statement) //i <= tempRep
            String tempName = biddersReputations.get(i).name;           //biddersReputations.get(i).name <= tempName (variable assignment statement) //i <= tempName

            entry = Map.entry(tempName, tempRep);                       //entry <= Map.entry(tempName, tempRep) (variable assignment statement) //i <= entry

            reputationsToBeShared.add(entry);                           //(entry U nextFreeElementInreputationsToBeShared) <= reputationsToBeShared (assinment statement)  //i <= reputationsToBeShared

            i++;                                                        //(i U 1) <= i (var assignment statement) //biddersReputations.size() <= i
        }

        targetAuctionHouse.tempBidderReputations = reputationsToBeShared; //reputationsToBeShared <= targetAuctionHouse.tempBidderReputations (variable assignment statement)
    }

    // process called with authority of thisAuctionHouse (info does not flow outside the auction house)
    //TODO explaion here how supremum results in same labe lo procedure is not leaking anything. no declasification needed
    public void updateReputationsOfBidders() {
        biddersReputations.forEach((bidderReputation) -> { //{thisAuctionHouse: thisAuctionHouse}
            tempBidderReputations.forEach((tempBidderReputation) -> { //{thisAuctionHouse: thisAuctionHouse}
                if (bidderReputation.name.equals(tempBidderReputation.getKey())) { //{thisAuctionHouse: thisAuctionHouse}
                    bidderReputation.reputation += tempBidderReputation.getValue(); //{thisAuctionHouse: thisAuctionHouse}
                } //{thisAuctionHouse: thisAuctionHouse}
            }); //{thisAuctionHouse: thisAuctionHouse}
        }); //{thisAuctionHouse: thisAuctionHouse}
    }

}