import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerTest {

    private Customer customer;
    private String name;
    private Movie testMovie;
    private String testMovieTitle;
    private int testDuration;
    private int testPriceCode;
    private int frequentRenterPointsTest;

    @Before
    public void setup() {
        name = "TestName";
        testMovieTitle = "TestMovieTitle";
        testDuration = 1;
        testPriceCode = Movie.REGULAR;
        customer = new Customer(name);
        testMovie = new Movie(testMovieTitle, testPriceCode);
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(customer.getName(), this.name);
    }

    @Test
    public void testAddRental() {
        customer.addRental(new Rental(testMovie, testDuration));
        String[] testOutput = customer.statement().split("\n");
        testOutput = testOutput[2].split("\t");
        Assert.assertEquals(testMovieTitle, testOutput[1]);
        Assert.assertEquals(testDuration, Integer.parseInt(testOutput[3]));

    }

    @Test
    public void rentMultipleMovies() {
        String tile1 = "Titel1";
        String tile2 = "Titel2";
        Rental rental1 = mock(Rental.class);
        Rental rental2 = mock(Rental.class);
        Movie movie1 = mock(Movie.class);
        Movie movie2 = mock(Movie.class);

        when(rental1.getMovie()).thenReturn(movie1);
        when(rental1.getDaysRented()).thenReturn(testDuration);
        when(movie1.getTitle()).thenReturn(tile1);
        when(movie1.getPriceCode()).thenReturn(testPriceCode);
        if (testPriceCode == Movie.NEW_RELEASE)
            this.frequentRenterPointsTest += 2;
        else {
            this.frequentRenterPointsTest++;
        }
        customer.addRental(rental1);

        when(rental2.getMovie()).thenReturn(movie2);
        when(rental2.getDaysRented()).thenReturn(testDuration);
        when(movie2.getTitle()).thenReturn(tile2);
        when(movie2.getPriceCode()).thenReturn(testPriceCode);

        if (testPriceCode == Movie.NEW_RELEASE)
            this.frequentRenterPointsTest += 2;
        else {
            this.frequentRenterPointsTest++;
        }

        customer.addRental(rental2);

        String[] testOutputOriginal = customer.statement().split("\n");
        String[] testOutput = testOutputOriginal[2].split("\t");
        Assert.assertEquals(tile1, testOutput[1]);
        Assert.assertEquals(testDuration, Integer.parseInt(testOutput[3]));

        testOutput = testOutputOriginal[3].split("\t");
        Assert.assertEquals(tile2, testOutput[1]);
        Assert.assertEquals(testDuration, Integer.parseInt(testOutput[3]));

    }

    @Test
    public void testFrequentRenterPoints() {
        this.frequentRenterPointsTest = 0;
        rentMultipleMovies();
        String[] testOutput = customer.statement().split("\n");
        testOutput = testOutput[testOutput.length - 1].split(" ");
        int frequentRenterPoints = Integer.parseInt(testOutput[2]);
        Assert.assertEquals(this.frequentRenterPointsTest, frequentRenterPoints);
    }


}
