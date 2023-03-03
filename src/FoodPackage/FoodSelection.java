package FoodPackage;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import UserPackage.User;

/**
 * Food Selection Class: Imports food items from SQL and adds them to ArrayLists. Has log and recommend methods.
 * @author Barış Tan Ünal
 * @version 03.05.2021
 */

public class FoodSelection {

    // Properties

    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public final static Instant today = Instant.now();
    public final static Instant oneDayAgo = today.minus(1, ChronoUnit.DAYS);

    public final static String oneString = dateFormat.format(Date.from(oneDayAgo));

    ArrayList<Food> breakfastList;
    ArrayList<Food> mainCourseList;
    ArrayList<Food> carbsList;
    ArrayList<Food> vegetablesList;
    ArrayList<Food> meatsList;
    //ArrayList<FoodPackage.Food> aperitifs;
    //ArrayList<FoodPackage.Food> snacks;
    ArrayList<Food> dessertList;
    ArrayList<Food> fruitList;
    ArrayList<Food> drinkList;
    ArrayList<Food> vegetableList;
    ArrayList<Food> soupList;

    Food foodSelected;

    String user;

    double dailyKcalNeed, breakfastKcalNeed, lunchKcalNeed, dinnerKcalNeed;


    // Constructors

    public FoodSelection( String user ) {
        this.user = user;
        breakfastList = getBreakfastFromSQL( "breakfast" );
        mainCourseList = getMainCourseFromSQL( "mainCourse" );
        meatsList = getMeatsFromSQL( "mainCourse" );
        carbsList = getCarbsFromSQL( "mainCourse" );
        vegetablesList = getVegetablesFromSQL( "mainCourse" );
        //aperitifs = getFoodFromSQL( "aperitifs" );
        //snacks = getFoodFromSQL( "snacks" );
        dessertList = getFoodFromSQL( "desserts" );
        fruitList = getFoodFromSQL( "fruits" );
        vegetableList = getFoodFromSQL( "vegetables" );
        drinkList = getFoodFromSQL( "drinks" );
        soupList = getFoodFromSQL( "soups" );
        foodSelected = null;
    }


    // Methods

    /**
     * Select Food Method: A method that selects food with from the given list with the given index.
     * @param list desired food list name for a food to be chosen from
     * @param foodName the name of the wanted food
     * @return food object at the given index
     */
    public Food selectFood ( ArrayList<Food> list, String foodName ) {

        for ( int i = 0; i < list.size(); i++ ) {

            if ( list.get( i ).getName().equals( foodName ) ) {
                return list.get( i );
            }
        }
        return null;
    }

    public Food selectFoodFromALlLists( String foodName ) {

        for ( int i = 0; i < breakfastList.size(); i++ ) {

            if ( breakfastList.get( i ).getName().equals( foodName ) ) {
                return breakfastList.get( i );
            }
        }

        for ( int i = 0; i < mainCourseList.size(); i++ ) {

            if ( mainCourseList.get( i ).getName().equals( foodName ) ) {
                return mainCourseList.get( i );
            }
        }

        for ( int i = 0; i < fruitList.size(); i++ ) {

            if ( fruitList.get( i ).getName().equals( foodName ) ) {
                return fruitList.get( i );
            }
        }

        for ( int i = 0; i < vegetableList.size(); i++ ) {

            if ( vegetableList.get( i ).getName().equals( foodName ) ) {
                return vegetableList.get( i );
            }
        }

        for ( int i = 0; i < drinkList.size(); i++ ) {

            if ( drinkList.get( i ).getName().equals( foodName ) ) {
                return drinkList.get( i );
            }
        }

        for ( int i = 0; i < soupList.size(); i++ ) {

            if ( soupList.get( i ).getName().equals( foodName ) ) {
                return soupList.get( i );
            }
        }

        for ( int i = 0; i < dessertList.size(); i++ ) {

            if ( dessertList.get( i ).getName().equals( foodName ) ) {
                return dessertList.get( i );
            }
        }

        return null;
    }


    public ArrayList<Food> getBreakfastList() {
        return breakfastList;
    }

    public ArrayList<Food> getDessertList() {
        return dessertList;
    }

    public ArrayList<Food> getDrinkList() {
        return drinkList;
    }

    public ArrayList<Food> getFruitList() {
        return fruitList;
    }

    public ArrayList<Food> getMainCourseList() {
        return mainCourseList;
    }

    public ArrayList<Food> getSoupList() {
        return soupList;
    }

    public ArrayList<Food> getVegetableList() {
        return vegetableList;
    }

    public String getUser() {
        return user;
    }


    /**
     * Log Food Method: Logs food intake to specified user's history.
     * @param foodName
     * @param listName
     * @param time
     * @param grams
     * @param date
     */
    public Food logFood( String date, String time, String listName, String foodName, double grams ) {

        Food selectedFood;
        double foodCalorie;

        try {
            Class.forName( "com.mysql.jdbc.Driver" );

            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/" + user,"root","20210406cp" );


            if ( listName.equals( "breakfast" ) ) {
                selectedFood = this.selectFood( breakfastList, foodName );
            }

            else if ( listName.equals( "mainCourse" ) ) {
                selectedFood = this.selectFood( mainCourseList, foodName );
            }

            else if ( listName.equals( "desserts" ) ) {
                selectedFood = this.selectFood( dessertList, foodName );
            }

            else if ( listName.equals( "fruits" ) ) {
                selectedFood = this.selectFood( fruitList, foodName );
            }

            else if ( listName.equals( "drinks" ) ) {
                selectedFood = this.selectFood( drinkList, foodName );
            }

            else if ( listName.equals( "vegetables" ) ) {
                selectedFood = this.selectFood( vegetableList, foodName );
            }

            else if ( listName.equals( "soups" ) ) {
                selectedFood = this.selectFood( soupList, foodName );
            }

            else {
                selectedFood = null;
            }

            foodCalorie = selectedFood.calculateGramsKcal( grams );

            PreparedStatement statement = connect.prepareStatement(
                    "insert into " + user + ".food values (?, ?, ?, ?, ? )");
            statement.setString( 1, date );
            statement.setString(2, time );
            statement.setString(3, foodName );
            statement.setDouble(4, grams );
            statement.setDouble(5, foodCalorie);
            statement.executeUpdate();

            PreparedStatement statement2 = connect.prepareStatement(
                    "insert into " + user + ".energytracker values (?, ?, ?, ?)");
            statement2.setString(1, date );
            statement2.setString(2, time );
            statement2.setDouble(3, foodCalorie );
            statement2.setDouble(4, 0 );
            statement2.executeUpdate();
        }
        catch( Exception e ){
            System.out.println( e );
            selectedFood = null;
        }

        return selectedFood;
    }



    /**
     * Get Food From SQL Method: A method that imports all foods to an arraylist from an SQL database table.
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getFoodFromSQL( String foodListName ){

        try{
            Food food;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName );

            while( resultset.next() ) {
                food = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ),
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "cirrhosis" )),
                        intToBoolean( resultset.getInt( "lactose" ) ) );
                list.add( food );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }


    /**
     * Get Food Main Course SQL Method:
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getMainCourseFromSQL( String foodListName ){

        try{
            Food food;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName );

            while( resultset.next() ) {
                food = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ), 0,
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "cirrhosis" )),
                        intToBoolean( resultset.getInt( "lactose" ) ),
                        resultset.getString( "type" ));

                list.add( food );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }


    /**
     * Get Meats From SQL Method:
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getMeatsFromSQL( String foodListName ){

        try{
            Food food;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName + " where type = 'm'" );

            while( resultset.next() ) {
                food = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ), 0,
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "cirrhosis" )),
                        intToBoolean( resultset.getInt( "lactose" ) ),
                        resultset.getString( "type" ));

                list.add( food );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }


    /**
     * Get Carbs From SQL Method:
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getCarbsFromSQL( String foodListName ){

        try{
            Food food;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName + " where type = 'c'" );

            while( resultset.next() ) {
                food = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ), 0,
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "cirrhosis" )),
                        intToBoolean( resultset.getInt( "lactose" ) ),
                        resultset.getString( "type" ));

                list.add( food );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }

    /**
     * Get Vegetables From SQL Method:
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getVegetablesFromSQL( String foodListName ){

        try{
            Food food;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName + " where type = 'v'" );

            while( resultset.next() ) {
                food = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ), 0,
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "cirrhosis" )),
                        intToBoolean( resultset.getInt( "lactose" ) ),
                        resultset.getString( "type" ));

                list.add( food );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }


    /**
     * Get Breakfast From SQL Method: A method that imports all foods to an arraylist from an SQL database table.
     * @param foodListName name of the wanted table in SQL database
     * @return an arraylist of foods with the given name
     */
    public ArrayList<Food> getBreakfastFromSQL( String foodListName ){

        try{

            Food breakfast;
            ArrayList<Food> list = new ArrayList<Food>();
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/food", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from " + foodListName );

            while( resultset.next() ) {
                breakfast = new Food( resultset.getString( 1 ), resultset.getInt("calorie" ),
                        resultset.getInt( "portionCalorie" ),
                        intToBoolean( resultset.getInt( "diabet" ) ),
                        intToBoolean( resultset.getInt( "ulcer" ) ),
                        intToBoolean( resultset.getInt( "obesity" ) ),
                        intToBoolean( resultset.getInt( "gout" ) ),
                        intToBoolean( resultset.getInt( "ulcer") ),
                        intToBoolean( resultset.getInt( "lactose" ) ) );
                list.add( breakfast );
            }
            connect.close();
            return list;
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }
    }


    public boolean[] checkHealth1( Food food ) {

        boolean[] diseases = new boolean[ 6 ];
        boolean diabetes, obesity, ulcer, gout, cirrhosis, lactose;
        diabetes = false;
        obesity= false;
        ulcer = false;
        gout = false;
        cirrhosis = false;
        lactose = false;

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/userdemo", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from user where username = '" + user + "'" );

            while( resultset.next() ) {

                obesity = intToBoolean( resultset.getInt( "obesity" ) );
                diabetes = intToBoolean( resultset.getInt( "diabet" ) );
                gout = intToBoolean( resultset.getInt( "gout" ) );
                ulcer = intToBoolean( resultset.getInt( "ulcer" ) );
                cirrhosis = intToBoolean( resultset.getInt( "cirrhosis" ) );
                lactose = intToBoolean( resultset.getInt( "lactose" ) );
            }

            if ( food.isObesity() && obesity ) {
                diseases[ 0 ] = true;
            }
            else {
                diseases[ 0 ] = false;
            }

            if ( food.isDiabetes() && diabetes ) {
                diseases[ 1 ] = true;
            }
            else {
                diseases[ 1 ] = false;
            }

            if ( food.isGout() && gout ) {
                diseases[ 2 ] = true;
            }
            else {
                diseases[ 2 ] = false;
            }

            if ( food.isUlcer() && ulcer ) {
                diseases[ 3 ] = true;
            }
            else {
                diseases[ 3 ] = false;
            }

            if ( food.isCirrhosis() && cirrhosis ) {
                diseases[ 4 ] = true;
            }
            else {
                diseases[ 4 ] = false;
            }

            if ( food.isLactose() && lactose ) {
                diseases[ 5 ] = true;
            }
            else {
                diseases[ 5 ] = false;
            }

        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }

        return diseases;
    }


    public boolean[] checkHealth() {

        boolean[] diseases = new boolean[ 6 ];
        boolean[] fixed = new boolean[] {false, false, false, false, false, false};
        boolean diabetes, obesity, ulcer, gout, cirrhosis, lactose;
        boolean foodDiabetes, foodObesity, foodUlcer, foodGout, foodCirrhosis, foodLactose;
        diabetes = false;
        obesity= false;
        ulcer = false;
        gout = false;
        cirrhosis = false;
        lactose = false;

        diseases[ 0 ] = false;
        diseases[ 1 ] = false;
        diseases[ 2 ] = false;
        diseases[ 3 ] = false;
        diseases[ 4 ] = false;
        diseases[ 5 ] = false;

        String foodName;
        Food food;

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/userdemo", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from user where username = '" + user + "'" );

            while( resultset.next() ) {

                obesity = intToBoolean( resultset.getInt( "obesity" ) );
                diabetes = intToBoolean( resultset.getInt( "diabet" ) );
                gout = intToBoolean( resultset.getInt( "gout" ) );
                ulcer = intToBoolean( resultset.getInt( "ulcer" ) );
                cirrhosis = intToBoolean( resultset.getInt( "cirrhosis" ) );
                lactose = intToBoolean( resultset.getInt( "lactose" ) );
            }

            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect1 = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/" + user, "root", "20210406cp" );

            Statement statement1 = connect1.createStatement();
            ResultSet resultset1 = statement1.executeQuery("select * from food");

            while ( resultset1.next() ) {

                if ( resultset1.getString( "date" ).equals( oneString ) ) {

                    foodName = resultset1.getString( "foodname" );

                    food = selectFoodFromALlLists( foodName );

                    foodObesity = food.isObesity();
                    foodDiabetes = food.isDiabetes();
                    foodUlcer = food.isUlcer();
                    foodGout = food.isGout();
                    foodLactose = food.isLactose();
                    foodCirrhosis = food.isCirrhosis();

                    if ( foodObesity && obesity ) {
                        diseases[ 0 ] = true;
                    }

                    if ( foodDiabetes && diabetes ) {
                        diseases[ 1 ] = true;
                    }

                    if ( foodGout && gout ) {
                        diseases[ 2 ] = true;
                    }

                    if ( foodUlcer && ulcer ) {
                        diseases[ 3 ] = true;
                    }

                    if ( foodCirrhosis && cirrhosis ) {
                        diseases[ 4 ] = true;
                    }

                    if ( foodLactose && lactose ) {
                        diseases[ 5 ] = true;
                    }
                }
            }
        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return fixed;
            // return null;
        }

        return diseases;
    }


    /**
     * Recommend Breakfast Method
     * @return breakfast list
     */
    public ArrayList<Food> recommendBreakfast() {

        ArrayList<Food> breakfastList1 = new ArrayList<>();
        ArrayList<Food> breakfastList2 = new ArrayList<>();
        ArrayList<Food> breakfastList3 = new ArrayList<>();
        int randomInt;

        for ( Food food : breakfastList ) {

            if ( food.getName().equals( "eggs" ) ) {
                breakfastList1.add( food );
            }
            if ( food.getName().equals( "tomatoes" ) ) {
                breakfastList1.add( food );
                breakfastList2.add( food );
                breakfastList3.add( food );
            }
            if ( food.getName().equals( "cheese" ) ) {
                breakfastList1.add( food );
                breakfastList2.add( food );
                breakfastList3.add( food );
            }
            if ( food.getName().equals( "croissant" ) ) {
                breakfastList1.add( food );
            }
            if ( food.getName().equals( "olives" ) ) {
                breakfastList1.add( food );
                breakfastList2.add( food );
                breakfastList3.add( food );
            }
            if ( food.getName().equals( "jam" ) ) {
                breakfastList1.add( food );
                breakfastList3.add( food );
            }

            if ( food.getName().equals( "omelette" ) ) {
                breakfastList2.add( food );
            }
            if ( food.getName().equals( "bread" ) ) {
                breakfastList2.add( food );
            }
            if ( food.getName().equals( "honey" ) ) {
                breakfastList2.add( food );
            }
            if ( food.getName().equals( "kuymak" ) ) {
                breakfastList2.add( food );
            }

            if ( food.getName().equals( "menemen" ) ) {
                breakfastList3.add( food );
            }
            if ( food.getName().equals( "simit" ) ) {
                breakfastList3.add( food );
            }

        }

        randomInt = ( int ) ( Math.random() * 3 );

        if ( randomInt == 0 ) {
            return breakfastList1;
        }
        else if ( randomInt == 1 ) {
            return breakfastList2;
        }
        else {
            return breakfastList3;
        }
    }


    /**
     * Lunch Recommendation Method
     * @return lunch list
     */
    public ArrayList<Food> recommendLunch() {

        ArrayList<Food> lunchList = new ArrayList<>();

        Food soup, mainCourse, carbs, fruit;
        double dailyKcalNeed, lunchKcalNeed, soupKcalNeed, mainKcalNeed, carbsKcalNeed, fruitKcalNeed;
        double soupKcal, mainKcal, carbsKcal, fruitKcal;
        double age, weight, height;
        int randomIndex;
        boolean diabetes, obesity, gout, ulcer, cirrhosis, lactose;
        String gender, goal;
        boolean[] comparisonArray = { false, false, false, false, false, false };

        diabetes = false;
        obesity = false;
        gout = false;
        ulcer = false;
        cirrhosis = false;
        lactose = false;

        dailyKcalNeed = 2000;

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/userdemo", "root", "20210406cp" );

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from user where username = '" + user + "'" );


            while( resultset.next() ) {
                if ( resultset.getString( "username" ).equals( user ) ) {

                    gout = intToBoolean( resultset.getInt( "gout" ) );
                    obesity = intToBoolean( resultset.getInt( "obesity" ) );
                    diabetes = intToBoolean( resultset.getInt( "diabet" ) );
                    ulcer = intToBoolean( resultset.getInt( "ulcer" ) );
                    cirrhosis = intToBoolean( resultset.getInt( "cirrhosis" ) );
                    lactose = intToBoolean( resultset.getInt( "lactose" ) );

                    gender = resultset.getString( "gender" );
                    age = resultset.getDouble( "age" );
                    weight = resultset.getDouble( "weight" );
                    height = resultset.getDouble( "height" );
                    goal = resultset.getString( "goal" );

                    if ( gender.equalsIgnoreCase( "female" ) ) {
                        dailyKcalNeed = 387 - ( 7.31 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
                    }
                    else {
                        dailyKcalNeed = 864 - ( 9.72 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
                    }


                    if ( goal.equalsIgnoreCase( "Gain weight" ) ) {
                        dailyKcalNeed = dailyKcalNeed + 200;
                    }

                    else if ( goal.equalsIgnoreCase( "Lose weight" ) ) {
                        dailyKcalNeed = dailyKcalNeed - 200;
                    }
                }
            }

            lunchKcalNeed = dailyKcalNeed * 4 / 10;
            soupKcalNeed = lunchKcalNeed * 140 / 800;
            mainKcalNeed = lunchKcalNeed * 260 / 800;
            carbsKcalNeed = lunchKcalNeed * 280 / 800;
            fruitKcalNeed = lunchKcalNeed * 110 / 800;

            // If the user is gout, do not recommend meals with meat, instead recommend a vegetable diet.
            if ( gout ) {
                return this.recommendDinner();
            }

            else {

                // find & assign a suitable soup
                do {
                    randomIndex = (int) (soupList.size() * Math.random());
                    soup = soupList.get(randomIndex);
                    soupKcal = soup.getCalorie() * 2;
                } while ( !( soupKcal - soupKcalNeed < 20 && soupKcal - soupKcalNeed > - 20 ) );


                // find & assign a suitable main course
                do {
                    randomIndex = (int) (meatsList.size() * Math.random());
                    mainCourse = meatsList.get(randomIndex);
                    mainKcal = mainCourse.getCalorie() * 1.5;

                } while ( !( ( mainKcal - mainKcalNeed < 30 && mainKcal - mainKcalNeed > - 30 ) &&
                        Arrays.equals(this.checkHealth1(mainCourse), comparisonArray) ) );

                // find & assign a suitable carbohydrate based foodİ if obese, give vegetable instead.
                if (obesity) {
                    do {
                        randomIndex = (int) (vegetablesList.size() * Math.random());
                        carbs = vegetablesList.get(randomIndex);
                        carbsKcal = carbs.getCalorie() * 2;

                    } while ( !( ( carbsKcal - carbsKcalNeed < 30 && carbsKcal - carbsKcalNeed > - 30 ) &&
                            Arrays.equals(this.checkHealth1(carbs), comparisonArray) ) );
                }
                else {
                    do {
                        randomIndex = (int) (carbsList.size() * Math.random());
                        carbs = carbsList.get(randomIndex);
                        carbsKcal = carbs.getCalorie() * 2;

                    } while ( !( ( carbsKcal - carbsKcalNeed < 30 && carbsKcal - carbsKcalNeed > - 30 ) &&
                            Arrays.equals(this.checkHealth1(carbs), comparisonArray) ) );
                }

                // find & assign a suitable fruit
                do {
                    randomIndex = (int) (fruitList.size() * Math.random());
                    fruit = fruitList.get(randomIndex);
                    fruitKcal = fruit.getCalorie() * 2;
                } while ( !( ( fruitKcal - fruitKcalNeed < 20 && fruitKcal - fruitKcalNeed > - 20 ) &&
                        Arrays.equals(this.checkHealth1(fruit), comparisonArray) ) );

                soup.setPortionCalorie( soupKcal );
                mainCourse.setPortionCalorie( mainKcal );
                carbs.setPortionCalorie( carbsKcal );
                fruit.setPortionCalorie( fruitKcal );

                // add all of those food to the arraylist
                lunchList.add( soup );
                lunchList.add( mainCourse );
                lunchList.add( carbs );
                lunchList.add( fruit );
            }

        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }

        return lunchList;
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


    /**
     * Dinner Recommendation Method
     * @return dinner list
     */
    public ArrayList<Food> recommendDinner() {

        ArrayList<Food> dinnerList = new ArrayList<>();

        Food soup, mainCourse, carbs, fruit;
        double dailyKcalNeed, dinnerKcalNeed, soupKcalNeed, mainKcalNeed, carbsKcalNeed, fruitKcalNeed;
        double soupKcal, mainKcal, carbsKcal, fruitKcal;
        double age, weight, height;
        int randomIndex;
        boolean diabetes, obesity, gout, ulcer, cirrhosis, lactose;
        String gender, goal;

        dailyKcalNeed = 2000;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://34.69.74.87:3306/userdemo", "root", "20210406cp");

            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery("select * from user");

            while( resultset.next() ) {
                if ( resultset.getString( "username" ).equals( user ) ) {

                    gender = resultset.getString( "gender" );
                    age = resultset.getDouble( "age" );
                    weight = resultset.getDouble( "weight" );
                    height = resultset.getDouble( "height" );
                    goal = resultset.getString( "goal" );

                    if ( gender.equalsIgnoreCase( "female" ) ) {
                        dailyKcalNeed = 387 - ( 7.31 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
                    }
                    else {
                        dailyKcalNeed = 864 - ( 9.72 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
                    }


                    if ( goal.equalsIgnoreCase( "Gain weight" ) ) {
                        dailyKcalNeed = dailyKcalNeed + 200;
                    }

                    else if ( goal.equalsIgnoreCase( "Lose weight" ) ) {
                        dailyKcalNeed = dailyKcalNeed - 200;
                    }

                }
            }

            dinnerKcalNeed = dailyKcalNeed * 3 / 10;
            soupKcalNeed = dinnerKcalNeed * 140 / 600;
            mainKcalNeed = dinnerKcalNeed * 265 / 600;
            carbsKcalNeed = dinnerKcalNeed * 140 / 600;
            fruitKcalNeed = dinnerKcalNeed * 55 / 600;

            do {
                randomIndex = ( int ) ( soupList.size() * Math.random() );
                soup = soupList.get( randomIndex );
                soupKcal = soup.getCalorie() * 2;
            } while ( !( soupKcal < soupKcalNeed + 20 && soupKcal > soupKcalNeed - 20 ) );


            do {
                randomIndex = ( int ) ( vegetablesList.size() * Math.random() );
                mainCourse = vegetablesList.get( randomIndex );
                mainKcal = mainCourse.getCalorie() * 2;
            } while ( !( mainKcal < mainKcalNeed + 40 && mainKcal > mainKcalNeed - 40 ) );


            do {
                randomIndex = ( int ) ( carbsList.size() * Math.random() );
                carbs = carbsList.get( randomIndex );
                carbsKcal = carbs.getCalorie();
            } while ( !( carbsKcal < carbsKcalNeed + 30 && carbsKcal > carbsKcalNeed - 30 ) );


            do {
                randomIndex = ( int ) ( fruitList.size() * Math.random() );
                fruit = fruitList.get( randomIndex );
                fruitKcal = fruit.getCalorie();
            } while ( !( fruitKcal < fruitKcalNeed + 30 && fruitKcal > fruitKcalNeed - 30 ) );

            soup.setPortionCalorie( soupKcal );
            mainCourse.setPortionCalorie( mainKcal );
            carbs.setPortionCalorie( carbsKcal );
            fruit.setPortionCalorie( fruitKcal );

            dinnerList.add( soup );
            dinnerList.add( mainCourse );
            dinnerList.add( carbs );
            dinnerList.add( fruit );

        }
        catch( Exception e ) {
            System.out.println( e ) ;
            return null;
        }

        return dinnerList;
    }


    /**
     * Int to Boolean Method: Takes the integer values from SQL which are 0 or 1, and converts them to boolean values.
     * @param i integer value ideally 0 or 1.
     * @return true if i is 1, false if i is 0.
     */
    public static boolean intToBoolean( int i ) {
        if ( i == 0 ) {
            return false;
        }
        else {
            return true;
        }
    }


    public static void main( String[] args ) {

        FoodSelection foodLists = new FoodSelection( "baris" );

        /*
        System.out.println( foodLists.getFoodFromSQL("breakfast") );
        System.out.println( foodLists.selectFood( foodLists.getFoodFromSQL(
                "soups" ), "minestrone" ) );


        foodLists.logFood( "baris123", "31/01/1995", "13.43", "fruits",
                "tangerine", 20 );

        System.out.println( Arrays.toString( foodLists.checkHealth( foodLists.selectFood(
                foodLists.getMainCourseList(), "beef" ) ) ) );

         */

        System.out.println( foodLists.recommendBreakfast() );
        System.out.println();

        System.out.println( foodLists.recommendLunch() );
        System.out.println();

        System.out.println( foodLists.recommendDinner() );

    }

}
