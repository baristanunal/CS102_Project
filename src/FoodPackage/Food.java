package FoodPackage;

/**
 * FoodPackage.Food Class: Creates food items with their calorie and diseases.
 * @author Barış Tan Ünal
 * @version 29.04.2021
 */

public class Food {

    // Properties

   String name, type;
   double calorie, portionCalorie;
   boolean diabetes, ulcer, obesity, cirrhosis, gout, lactose;


    // Constructors

    public Food( String name, double calorie, boolean diabetes, boolean ulcer,
                 boolean obesity, boolean gout, boolean cirrhosis, boolean lactose)
    {
        this.name = name;
        this.calorie = calorie;
        this.diabetes = diabetes;
        this.ulcer = ulcer;
        this.obesity = obesity;
        this.cirrhosis = cirrhosis;
        this.gout = gout;
        this.lactose = lactose;
    }

    // special constructor for breakfasts
    public Food( String name, double calorie, double portionCalorie, boolean diabetes,
                          boolean ulcer, boolean obesity, boolean gout, boolean cirrhosis, boolean lactose)
    {
        this.name = name;
        this.calorie = calorie;
        this.portionCalorie = portionCalorie;
        this.diabetes = diabetes;
        this.ulcer = ulcer;
        this.obesity = obesity;
        this.cirrhosis = cirrhosis;
        this.gout = gout;
        this.lactose = lactose;
    }

    // special constructor for main course
    public Food( String name, double calorie, double portionCalorie, boolean diabetes, boolean ulcer,
                 boolean obesity, boolean gout, boolean cirrhosis, boolean lactose, String type)
    {
        this.portionCalorie = portionCalorie;
        this.name = name;
        this.calorie = calorie;
        this.diabetes = diabetes;
        this.ulcer = ulcer;
        this.obesity = obesity;
        this.cirrhosis = cirrhosis;
        this.gout = gout;
        this.lactose = lactose;
        this.type = type;
    }


    // Methods


    public String getName() {
        return name;
    }

    public double getCalorie() {
        return calorie;
    }

    public String getType() {
        return type;
    }

    public double getPortionCalorie() {
        return portionCalorie;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public boolean isUlcer() {
        return ulcer;
    }

    public boolean isObesity() {
        return obesity;
    }

    public boolean isCirrhosis() {
        return cirrhosis;
    }

    public boolean isGout() {
        return gout;
    }

    public boolean isLactose() {
        return lactose;
    }

    public double calculatePortionKcal( double portions ) {
        return this.calorie * portions;
    }

    public double calculateGramsKcal( double grams ) {

        return this.calorie * grams / 100;
    }

    public void setPortionCalorie( double portionCalorie ) {
        this.portionCalorie = portionCalorie;
    }

    public String toString() {
        return ( name + " - " + portionCalorie + "kcal" );
    }

      /*
    AVERAGE KCAL:
    soup average:140 (200g); *
    vegetable average:260 (200g);
    carbs average:140 (100g);
    carbs average:280 (200g); *
    meat average:173 (100g);
    meat average: 260 (150g); *
    dessert average:295 (100g);
    fruit average:55 (100g);
    fruit average:80 (150g);
    fruit average:110 (200g); *
    */


}
