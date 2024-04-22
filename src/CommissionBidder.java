public class CommissionBidder extends Bidder {
    public int highest;

    public CommissionBidder(String name, int reputation, int highest) {
        // String name {action: action, bidder}, but can be declassified to {⊥} if highest bid
        // int highest {action: action, bidder}
        super(name);
        this.highest = highest;
    }

    public void declassifyBidderName() {
        System.out.println("Declassifying " + uniqueNumber);
    }

    public void declassifyHighest() {
        System.out.println("Declassifying " + highest);
    }
}
