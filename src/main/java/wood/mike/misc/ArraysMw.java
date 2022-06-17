package wood.mike.misc;

import java.util.Arrays;

public class ArraysMw {

    public static void main(String[] args) {
        int[] sdArray = {1,2,3};
        int[] sdArray2 = new int[3];

        // ------------------------------------------------

        Integer[][] mdArray =
                {{1,2,3},
                {4,5,6},
                {7,8,9}};
        printMultiDimensionalArray(mdArray);

        // ------------------------------------------------

        int rows = 5;
        int cols = 10;

        Integer[][] mdArray2 = new Integer[rows][cols];

        int val = 1;
        for ( int i = 0 ; i < rows ; i++ ) {
            for ( int j = 0 ; j < cols ; j++ ) {
                mdArray2[i][j] = val++;
            }
        }

        printMultiDimensionalArray(mdArray2);

        if ( mdArray2[2][8] != 29 )
            throw new AssertionError( "Eh?" );
    }

    static void printMultiDimensionalArray( Object[][] mdArray ) {
        System.out.println(Arrays.deepToString(mdArray).replace("], ", "]\n"));
    }
}
