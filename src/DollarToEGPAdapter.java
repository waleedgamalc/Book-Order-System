class DollarToEGPAdapter implements EGPValue {
    private DollarValue dollarValue;
    private static final double CONVERSION_RATE = 30; // Replace with the actual conversion rate

    public DollarToEGPAdapter(DollarValue dollarValue) {
        this.dollarValue = dollarValue;
    }

    @Override
    public double getEGP() {
        // Convert Dollar to EGP using the conversion rate
        return dollarValue.getDollar() * CONVERSION_RATE;
    }
}