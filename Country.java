/**
 * Country class - this class will create an "objects" - country  with a list of cities it contains.
 * <br> The country class will have a few methods to add cities, see the southest city or all the cities north from
 * another, and copy an array of cities.
 * @author Itay Nir
 */
public class Country {
    private String _countryName;
    private City[] _cities;
    private int _noOfCities;
    private final int MAX_NUM_CITIES = 1000;

    /**
     * Constructor for country "objects".
     * <br> Generate a new country object with a given name and default array of city objects(initialized to 1000
     * empty cells.
     * <br> Initial count of cities for a country built is 0.
     * <br> Maximum cities allowed is 1000.
     * @param countryName The name of the country.
     */
    public Country(String countryName) {
        _countryName = countryName;
        _cities = new City[MAX_NUM_CITIES];
        _noOfCities = 0;
    }

    /**
     * Adding a city object to the city array of this country.
     * <br> Each add setts the cities array count to one more.
     * <br> If the cities array is at the number of cities limit, the city will not add.
     * <br> Returns true if the city has been added, and false otherwise.
     * @param cityName The name of the city.
     * @param xCenter The X coordinate of the center point of the city.
     * @param yCenter The Y coordinate of the center point of the city.
     * @param xStation The X coordinate of the central station of the city
     * @param yStation The Y coordinate of the central station of the city
     * @param numOfResidents The number of residents in the city.
     * @param noOfNeighborhoods The number of neighborhoods in the city.
     * @return If the city has been added.
     */
    public boolean addCity(String cityName, double xCenter, double yCenter, double xStation, double yStation, long numOfResidents, int noOfNeighborhoods) {
        if (_noOfCities < MAX_NUM_CITIES) {
            City customCity = new City(cityName, xCenter, yCenter, xStation, yStation, numOfResidents, noOfNeighborhoods);
            _cities[_noOfCities] = new City(customCity);
            _noOfCities++;
            return true;
        } else return false;
    }

    /**
     * Returns the number of residents of the country.
     * @return The number of residents of the country.
     */
    public long getNumOfResidents() {
        long residentsCount = 0;
        for (int i = 0; i < _noOfCities; i++)
            residentsCount += _cities[i].getNumOfResidents();

        return residentsCount;
    }

    /**
     * Returns the longest distance between two cities of the country(city centers).
     * <br> If the country have less than two cities, returns 0- zero.
     * @return The longest distance between two cities of the country.
     */
    public double longestDistance() {
        double longestDistance = 0;
        //the loop will continue the run after finding a distance and compare it to the others to find the longest.
        for (int i = 0; i < _noOfCities; i++) {
            Point first = new Point(_cities[i].getCityCenter());
            for (int j = 0; j < _noOfCities; j++) {
                Point second = new Point(_cities[j].getCityCenter());
                if (first.distance(second) > longestDistance) {
                    longestDistance = first.distance(second);
                }
            }
        }
        //it w'ont run if the country have less than 2 cities.
        if(_noOfCities <2)
            return 0;
          else return longestDistance;
    }

    /**
     * String a list of cities norther than a given city.
     * <br> If there is no city with the given name, returns a string statement that say so.
     * <br> If there are no cities norther, returns a string statement that say so.
     * @param cityName The name of the city to find cities north of.
     * @return A string representation of all cities in the country that are north of the given city name or
     * a string says why it did not.
     */
    public String citiesNorthOf(String cityName) {

        boolean foundNorthOf = false;
        boolean foundCityName = false;
        Point checkPoint = new Point(_cities[0].getCityCenter());
        String res = "The cities north of " + cityName + " are:\n";

        //First, will search for the city name in the array of cities belongs to this country, and keep its city center.
        for (int i = 0; i < _noOfCities; i++)
        if (cityName == _cities[i].getCityName()) {
            foundCityName = true;
            checkPoint = new Point(_cities[i].getCityCenter());

        }

        //Second, need to find and string every city that its city center is above our given city.
        for (int j = 0; j < _noOfCities; j++) {
            if (_cities[j].getCityCenter().isAbove(checkPoint)) {
                foundNorthOf = true;
                res += "\n" + _cities[j].toString() + "\n";
            }
        }

        if (foundCityName == false)
            res = "There is no city with the name " + cityName;
        if (foundNorthOf == false)
            res = "There are no cities north of " + cityName;

        return res;
    }

    /**
     * Returns the most south city in the country based on the lower city center y coordinate.
     * <br> If there are no cities in the country, returns null.
     * @return The most south city of the country.
     */
    public City southernmostCity() {

        City southestCity = new City(_cities[0]);

        //We'll compare the first city in the array with every other to check which has the lower y coordinate.
        //than we'll keep the one we found , and run the test for every other index comparing the lower point
        //we are finding with the lower point we already found.
        for (int i = 0; i < _noOfCities; i++)
            if (_cities[i].getCityCenter().isUnder(southestCity.getCityCenter()))
            {
                southestCity = new City(_cities[i]);

                for (int j = 0; j < _noOfCities; j++)    //runs for each index in the array.
                    if (_cities[j].getCityCenter().isUnder(southestCity.getCityCenter()))
                        southestCity = new City(_cities[j]);
            }

            if(_noOfCities==0)
                southestCity=null;

        return southestCity;
    }

    /**
     * Returns the name of the country.
     * @return The name of the country.
     */
    public String getCountryName() {
        String countryName = new String(_countryName);
        return countryName;
    }

    /**
     * Return the number of cities in the country.
     * @return The number of cities in the country.
     */
    public int getNumOfCities() {
        int numOfCities = _noOfCities;
        return numOfCities;
    }

    /**
     *Returns copied array of the cities in the country.
     * @return Array of cities copied from the country.
     */
    public City[] getCities() {
        City[] cities = new City[_noOfCities];
        for(int i=0 ; i<cities.length ; i++)    //copying the cities.
        cities[i] = new City(_cities[i]);

        return cities ;
    }

    /**
     * Return a unified city from two city names given.
     * <br> The unified city will replace the bigger city from the two given(by number of residents).
     * <br> If the two have the same number of residents, the unified city will replace the first city by default.
     * <br> The unified city should set to have the sum of residents and neighborhoods of the cities unified.
     * <br> The city center point of the unified city should be half way between the two city centers unified.
     * <br> The central station should be the west station of the two that have been unified(the x coordinate should
     * be the less of the two).
     * <br> The smaller city(by number of residents) should be deleted from the country.
     * <br> If the cities are equal, the second city will be deleted by default.
     * @param cityName1 The name of first city to unify.
     * @param cityName2 The name of second city to unify.
     * @return The unified city.
     */
    public City unifyCities(String cityName1, String cityName2) {

           String updatedCityName = cityName1 + "-" + cityName2;

           //First we'll find the indexes of the cities with our private method.
           int index1 = searchForIndex(cityName1);
           int index2 = searchForIndex(cityName2);

           //by default we'll have the first as the bigger one(as requested in case of equals).
           int indexOfBigger = index1;
           int indexOfSmaller = index2;

           //Setting the parameters of the unified city.
           int updatedNoOfNieghborhoods = _cities[index1].getNoOfNeighborhoods() + _cities[index2].getNoOfNeighborhoods();
           long updatedNumOfResidents =  _cities[index1].getNumOfResidents()+_cities[index2].getNumOfResidents();
           double updatedCityCenterX = _cities[index1].getCityCenter().getX()/2 + _cities[index2].getCityCenter().getX()/2;
           double updatedCityCenterY = _cities[index1].getCityCenter().getY()/2 + _cities[index2].getCityCenter().getY()/2;

           //Declaring the updated points.
           Point updatedCityCenter = new Point(updatedCityCenterX,updatedCityCenterY);
           Point updatedCentralStation = new Point(_cities[index1].getCentralStation());
               if(_cities[index2].getCentralStation().isLeft(_cities[index1].getCentralStation()))
                    updatedCentralStation = new Point(_cities[index2].getCentralStation());

            //Setting the unified city to the array.
            if(_cities[index1].getNumOfResidents() < _cities[index2].getNumOfResidents())
            {
           _cities[index2].setNumOfResidents(updatedNumOfResidents);
           _cities[index2].setNoOfNeighborhoods(updatedNoOfNieghborhoods);
           _cities[index2].setCityCenter(updatedCityCenter);
           _cities[index2].setCentralStation(updatedCentralStation);
           _cities[index2].setCityName(updatedCityName);
           indexOfBigger = index2;
           indexOfSmaller = index1;
            }
            else if(_cities[index1].getNumOfResidents() >= _cities[index2].getNumOfResidents())
            {
           _cities[index1].setNumOfResidents(updatedNumOfResidents);
           _cities[index1].setNoOfNeighborhoods(updatedNoOfNieghborhoods);
           _cities[index1].setCityCenter(updatedCityCenter);
           _cities[index1].setCentralStation(updatedCentralStation);
           _cities[index1].setCityName(updatedCityName);
            }

          //Copying the cities to get the smaller city deleted, and deleting the last cell of the array by putting
          // null in it and shorten the array length.
          for(int i=indexOfSmaller ; i<_noOfCities ; i++)
          {
           if(indexOfSmaller>indexOfBigger)
             _cities[i] = _cities[i+1];
           else if(indexOfSmaller<indexOfBigger) {
               _cities[i] = _cities[i + 1];
           }
           }
           if(indexOfSmaller<indexOfBigger)
           indexOfBigger--;

           _cities[_noOfCities-1]=null;
           _noOfCities--;

       return _cities[indexOfBigger];
    }

    //we use in private method to find the index of a city in the array.
    private int searchForIndex(String cityName)
    {
        int index=0;
        for(int i=0 ; i<_noOfCities ; i++)
            if(_cities[i].getCityName().equals(cityName))
              index = i;

            return index;
    }

    /**
     * Returns all of the cities details(by a string City object format) in the country.
     * @return A string representation of the cities in the country.
     */
    public String toString() {

        String res = "Cities of " + getCountryName() + ":"+ "\n\n";

        //As requested, we will use the previus method to string the cities by string the southest city and all
        // the city that are north to this city.
        if(_noOfCities==0)
            res = "There are no cities in this country.";
        else {
            res += southernmostCity() + "\n";
            res += citiesNorthOf(southernmostCity().getCityName());
        }

        return res;
    }


}
