public class DecisionFactory {

    public static OrderCheck getDecision(String d){

        if (d.equals("Order Confirmed")){
            return new Confrim();
        }
        else if (d.equals("Cancelled")){
            return new Cancel();
        }
        return null;
    }

}
