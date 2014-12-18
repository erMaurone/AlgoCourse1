package algoCourse2.lesson2.assignment.seamCarving;

import algoCourse2.lesson2.assignment.seamCarving.testing.SeamCarver;
import org.junit.Assert;
import org.junit.Test;
import princeton.stdlib.Picture;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 15/12/2014
 * Time: 15:39
 */

public class SeamCarverTest {

    @Test
    public void testWidth() throws Exception {
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int width = sc.width();
        Assert.assertEquals(3, width);
    }

    @Test
    public void testHeight() throws Exception {
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int height = sc.height();
        Assert.assertEquals(7, height);
    }

    @Test
    public void testEnergy() throws Exception {
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[][] resultEnergy = {
                {195075,195075,195075},
                {195075, 86627, 195075},
                {195075, 55775, 195075},
                {195075, 105720, 195075},
                {195075, 63180, 195075},
                {195075, 78196, 195075},
                {195075, 195075, 195075} };

        for (int j = 0; j < sc.height(); j++)
            for (int i = 0; i < sc.width(); i++)
                Assert.assertEquals(resultEnergy[j][i],sc.energy(i, j), 0);
    }

    @Test
    public void testRemoveHoriziontalSeam() {
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/6x5.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expectedRows = {3,3,3,3,2,2};
        int[] horizontalSeam = sc.findHorizontalSeam();;

        Picture pic = sc.picture();
        SeamCarver scTest = new SeamCarver(pic);
        for (int row = 0; row < sc.height(); row++)
            for (int col = 0; col < sc.width(); col++)
                Assert.assertEquals(scTest.energy(col,row), sc.energy(col,row),0);
    }

    @Test
    public void testFindHorizontalSeam() throws Exception {
        double totalSeamEnergy = 0;
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/6x5.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expectedRows = {3,3,3,3,2,2};
        int[] horizontalSeam = sc.findHorizontalSeam();
        Assert.assertArrayEquals("expected", expectedRows, horizontalSeam);

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                char lMarker = ' ';
                char rMarker = ' ';
                if (j == horizontalSeam[i]) {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }
                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }
            System.out.println();
        }
        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
        Assert.assertEquals(512801,totalSeamEnergy,0);
    }

    @Test
    public void testRemoveVerticalSeam() {
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expectedColumns = {5,6,7,8,7,7,6,7,6,5,6,6};
        int[] verticalSeam = sc.findVerticalSeam();
        sc.removeVerticalSeam(verticalSeam);

        Picture pic = sc.picture();
        SeamCarver scTest = new SeamCarver(pic);
        for (int row = 0; row < sc.height(); row++)
            for (int col = 0; col < sc.width(); col++)
                Assert.assertEquals(scTest.energy(col,row), sc.energy(col,row),0);
    }

    @Test
    public void testFindVerticalSeam() throws Exception {

        double totalSeamEnergy = 0;
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expectedColumns = {5,6,7,8,7,7,6,7,6,5,6,6};
        int[] verticalSeam = sc.findVerticalSeam();
        Assert.assertArrayEquals("expected", expectedColumns, verticalSeam);

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                char lMarker = ' ';
                char rMarker = ' ';
                if (i == verticalSeam[j]) {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }
                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }
            System.out.println();
        }
        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
        Assert.assertEquals(652800,totalSeamEnergy,0);
    }

    //array of the wrong length or if the array is not a valid seam
    // (i.e., either an entry is outside its prescribed range or two adjacent entries differ by more than 1)
    //  Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() or removeHorizontalSeam()
    // is called when either the width or height is less than or equal to 1.
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveVerticalSeamSmallImage() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        scSmall.removeVerticalSeam(scSmall.findVerticalSeam());
        scSmall.removeVerticalSeam(scSmall.findVerticalSeam());
    }

    @Test(expected = IllegalArgumentException.class)
         public void testIllegalArgRemoveVerticalSeamSeamOutsideRange() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {2,3,4,5,6,7,8};
        scSmall.removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveVerticalSeamSeamInvariant() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {2,3,4,5,6,1,0};
        scSmall.removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveVerticalSeamSeamSize() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {2,3,4,5,6,1};
        scSmall.removeVerticalSeam(seam);
    }
     //********************
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveHorizontalSeamSmallImage() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
        scSmall.removeVerticalSeam(scSmall.findHorizontalSeam());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveHorizontalSeamSeamOutsideRange() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {1,2,3,4};
        scSmall.removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveHorizontalSeamSeamInvariant() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {0,2,1};
        scSmall.removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgRemoveHorizontalSeamSeamSize() throws Exception {
        Picture small = new Picture("src/main/resources/algo2/seamCarving/3x7.png");
        SeamCarver scSmall = new SeamCarver(small);
        int[] seam = {2,3,4,5,6,1};
        scSmall.removeHorizontalSeam(seam);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveHorizontalSeam() throws Exception {
        double totalSeamEnergy = 0;
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        sc.removeHorizontalSeam(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRemoveVerticalSeam() throws Exception {
        double totalSeamEnergy = 0;
        Picture inputImg = new Picture("src/main/resources/algo2/seamCarving/10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        sc.removeVerticalSeam(null);
    }
}
