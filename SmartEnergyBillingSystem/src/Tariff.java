abstract class Tariff {
    protected static final double SLAB1_RATE = 10.0;
    protected static final double SLAB2_RATE = 15.0;
    protected static final double SLAB3_RATE = 20.0;
    protected static final double FIXED_CHARGE = 100.0;

    public abstract double calculateCharge(double units);
}

//Calculating charges for Domestic Tariff
class DomesticTariff extends Tariff{
    public double calculateCharge(double units){
        double charge = FIXED_CHARGE;
        if(units > 300){
            charge += (100 * SLAB1_RATE) + (200 * SLAB2_RATE) + ((units - 300) * SLAB3_RATE);
        }else if(units > 100){
            charge += (100 * SLAB1_RATE) + ((units - 100) * SLAB2_RATE);
        }else{
            charge += (units * SLAB1_RATE);
        }
        return charge;
    }
}

//Calculating charges for Commercial Tariff
class CommercialTariff extends Tariff{
    private static final double COMMERCIAL_SURCHARGE = 1.2;
    @Override
    public double calculateCharge(double units){
        double charge = FIXED_CHARGE * 1.5;
        if (units > 300){
            charge += ((100 * SLAB1_RATE) + (200 * SLAB2_RATE) + ((units - 300) * SLAB3_RATE)) * COMMERCIAL_SURCHARGE;
        }else if (units > 100){
            charge += ((100 * SLAB1_RATE) + ((units - 200) * SLAB2_RATE)) * COMMERCIAL_SURCHARGE;
        }else{
            charge += (units * SLAB1_RATE) * COMMERCIAL_SURCHARGE;
        }
        return charge;
    }
}
