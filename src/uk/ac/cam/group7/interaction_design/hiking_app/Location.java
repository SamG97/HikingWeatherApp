package uk.ac.cam.group7.interaction_design.hiking_app;

/**
 * Holds information about the location
 *  2 ints for Lat and Long (final)
 *  String for name (possibly NULL)
 *  boolean for favourite
 *  @author dobrik
 */
public class Location {
    private final int latitude, longtitude;
    private String name=null;
    private boolean isFavourite=false;



    /**
     * Constructor that takes 2 parameters (LAT and LONG)
     * and makes a new Location class with these coordinates
     * @param _latitude
     * Latitude of location
     * @param _longtitude
     * Longitude of location
     */
    public Location(int _latitude,int _longtitude){
        latitude=_latitude;
        longtitude=_longtitude;
    }

    /**
     * Constructor that takes 2 parameters (LAT and LONG)
     * and the name of the Location
     * and makes a new Location class with these coordinates and the name
     * @param _latitude
     * Latitude of location
     * @param _longtitude
     * Longitude of location
     * @param _name
     * Name of the location
     */
    public Location(int _latitude,int _longtitude,String _name){
        latitude=_latitude;
        longtitude=_longtitude;
        name=_name;
    }

    /**
     * Getter for latitude
     * @return
     * latitude ofc (integer)
     */
    public int getLatitude(){
        return latitude;
    }

    /**
     * Getter for longtitude
     * @return
     * longtitude (integer), you idiot, do you expect something else
     */
    public int getLongtitude(){
        return longtitude;
    }

    /**
     * Getter for name
     * @return
     * Name of location
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for favourite
     * @return
     * boolean for isFavourite or not
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     * Setter for the name of the location
     * @param _name
     * Name of the location
     */
    public void setName(String _name){name=_name;}

    /**
     * Set favourite location
     * @param favourite
     * boolean (false:notFav, true:Fav)
     */
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    /**
     * Autogenerated method for equals
     * @param o
     * Object to compare to
     * @return
     * true or false depending on equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (latitude != location.latitude) return false;
        if (longtitude != location.longtitude) return false;
        return name != null ? name.equals(location.name) : location.name == null;
    }

    /**
     * Autogenerated hash code
     * @return
     * hashcode of the object
     */
    @Override
    public int hashCode() {
        int result = latitude;
        result = 31 * result + longtitude;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    /**
     * String for displaying the location in appropriate form
     * @return
     * name or coordinates (if name==null)
     */
    @Override
    public String toString() {
        if(name!=null)return name;
        return "("+latitude+","+longtitude+")";
    }
    //public static void main(String args[]){
    //    Location test=new Location(11,22,"haway");
    //    System.out.println(test);
    //    test.setFavourite(true);
    //    System.out.println(test.isFavourite());
    //}

}

