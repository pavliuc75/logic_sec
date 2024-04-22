public class CommissionBidder extends Bidder {
    public int highest;

    public CommissionBidder(String name, int reputation, int highest) {
        // String name {action: action, bidder}, but can be declassified to {⊥} if highest bid
        // int reputation {actions: actions}
        // int highest {action: action, bidder}
        super(name, reputation);
        this.highest = highest;
    }

    public void declassifyBidderName() {
        System.out.println("Declassifying " + name);
    }

    public void declassifyHighest() {
        System.out.println("Declassifying " + highest);
    }
}
