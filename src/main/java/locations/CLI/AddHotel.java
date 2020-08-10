package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.CLI.validators.OneToFive;
import locations.CLI.validators.PositiveInteger;
import locations.backend.Hotel;
import locations.backend.Location;

class AddHotel extends AddLocation {

    @Parameter(names = {"--capacity"}, description = "The number of rooms in the hotel.", required = true,
            validateWith = PositiveInteger.class)
    private int capacity;

    @Parameter(names = {"--rating"}, description = "The star rating (1-5) of the hotel.", required = true,
            validateWith = OneToFive.class)
    private int starRating;

    public Location toLocation() {
        return new Hotel(this.getName(), this.getX(), this.getY(), this.capacity, this.starRating);
    }


}
