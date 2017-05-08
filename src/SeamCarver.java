/**
 * Created by tongs on 2/19/2017.
 */

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;


public class SeamCarver {
    private Picture pic;
    private double[][] pEnergy;
    private int[][] colorMap;
    private int height,width;

    public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
    {
        this.pic = picture;
        this.height = pic.height();
        this.width = pic.width();
        colorMap = new int[width][height];
        pEnergy = new double[pic.width()][pic.height()];
        for(int i = 0;i < pic.width();i++){
            for(int j = 0;j<pic.height();j++){
                this.pEnergy[i][j] = energy(i,j);
                colorMap[i][j] = pic.get(i,j).getRGB();
            }
        }

    }
    public Picture picture()                          // current picture
    {
        Picture newPic = new Picture(width,height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                newPic.set(x, y, new Color(colorMap[x][y]));
            }
        return newPic;
    }

    public     int width()                            // width of current picture
    {
        return width;
    }
    public     int height()                           // height of current picture
    {
        return height;
    }
    public  double energy(int x, int y)               // energy of pixel at column x and row y
    {
        if(x > pic.width() - 1 || y > pic.height() - 1|| x<0|| y < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        if(x == pic.width() - 1 || y == pic.height() - 1 || x*y == 0){
            return 1000;
        }
        else {
            return Math.sqrt(xEnergy(x, y) + yEnergy(x, y));
        }
    }
    private double xEnergy(int x, int y){
        Color leftColor = pic.get(x - 1,y);
        Color rightColor = pic.get(x+1,y);

        double l1 = leftColor.getRed();
        double l2 = leftColor.getGreen();
        double l3 = leftColor.getBlue();
        double r1 = rightColor.getRed();
        double r2 = rightColor.getGreen();
        double r3 = rightColor.getBlue();
        return (l1 - r1)*(l1 - r1) + (l2 - r2)*(l2 - r2) + (l3 - r3)*(l3 - r3);
    }
    private double yEnergy(int x, int y){
        Color upColor = pic.get(x,y+1);
        Color downColor = pic.get(x,y-1);

        double l1 = upColor.getRed();
        double l2 = upColor.getGreen();
        double l3 = upColor.getBlue();
        double r1 = downColor.getRed();
        double r2 = downColor.getGreen();
        double r3 = downColor.getBlue();
        return (l1 - r1)*(l1 - r1) + (l2 - r2)*(l2 - r2) + (l3 - r3)*(l3 - r3);
    }


    public  int[] findVerticalSeam()                 // sequence of indices for vertical seam
    {
        IndexMinPQ<Double> pq = new IndexMinPQ<>(width);
        double[][] disTo;  // disTo to the top of the image.
        int[][] xTo;       // shortest pixel path
        disTo = new double[width][height];
        xTo = new int[width][height];
        for(int i = 0;i < width;i++){
            for(int j = 0;j<height;j++){
                disTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        int xxx = height;
        int yyy = width;
        int[] temp = new int[xxx];
        int d,x,y;
        pq.insert(0,Double.POSITIVE_INFINITY);
        if(width != 1) {
            pq.insert(width - 1, Double.POSITIVE_INFINITY);
        }
        for(int i = 0;i < xxx;i++){

            for(int j = 1;j< yyy - 1;j++) {
                if (i == 0) {
                    pq.insert(j,pEnergy[j][i]);
                    disTo[j][i] = 1000;
                }
                else{
                    x = j;
                    y = i;
                    if(disTo[x-1][y-1] <= disTo[x][y-1]){
                        if(disTo[x-1][y-1] < disTo[x+1][y-1]){
                            d = -1;
                        }
                        else{
                            d = 1;
                        }
                    }else{
                        if(disTo[x][y-1] <= disTo[x+1][y-1]){
                            d = 0;
                        }
                        else{
                            d = 1;
                        }
                    }

                    disTo[x][y] = disTo[x+d][y-1] + pEnergy[x][y];
                    xTo[x][y] = x+d;
                    pq.changeKey(j,disTo[x][y]);
                }
            }
        }
        int minLoc = pq.minIndex();
        temp[xxx - 1] = minLoc;
        for(int p = xxx - 1;p > 0;p--){
            temp[p - 1] = xTo[temp[p]][p];
        }

        return temp;
    }
    public int[] findHorizontalSeam(){
        IndexMinPQ<Double> pq = new IndexMinPQ<>(height);
        double disTo[][] = new double[width][height];
        int yTo[][] = new int[width][height];
        for(int i = 0;i < width;i++){
            for(int j = 0;j<height;j++){
                disTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        int[] temp = new int[width];
        int d;

        pq.insert(0,Double.POSITIVE_INFINITY);
        if(height!= 1) {
            pq.insert(height - 1, Double.POSITIVE_INFINITY);
        }
        for(int x = 0;x<width;x++){

            for(int y = 1;y<height - 1;y++){
                if(x == 0){
                    pq.insert(y,pEnergy[x][y]);
                    disTo[x][y] = 1000;
                }
                else{
                    if(disTo[x-1][y-1] <= disTo[x-1][y]){
                        if(disTo[x-1][y-1] < disTo[x-1][y+1]){
                            d = -1;
                        }
                        else{
                            d = 1;
                        }
                    }else{
                        if(disTo[x-1][y] <= disTo[x-1][y+1]){
                            d = 0;
                        }
                        else{
                            d = 1;
                        }
                    }
                    disTo[x][y] = disTo[x-1][y+d] + pEnergy[x][y];
                    yTo[x][y] = y+d;
                    pq.changeKey(y,disTo[x][y]);
                }
            }
        }
        int minLoc = pq.minIndex();
        temp[width - 1] = minLoc;
        for(int p = width - 1;p > 0;p--){
            temp[p - 1] = yTo[p][temp[p]];
        }

        return temp;

    }
    private void checkValidity(int[] seam) {


        if (seam.length <= 0) {
            throw new IllegalArgumentException("The seam size must be greater than 0.");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }

    }
    public void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
    {
        checkValidity(seam);
        if (seam == null) {
            throw new NullPointerException();
        }

        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] > height - 1) {
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height - 1;j++){
                if(j>=seam[i]) {
                    colorMap[i][j] = colorMap[i][j+1];
                    pEnergy[i][j] = pEnergy[i][j+1];
                }
            }
        }
        height--;
    }
    public void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
    {
        checkValidity(seam);
        if (seam == null) {
            throw new NullPointerException();
        }

        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] > width - 1) {
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0;i<width - 1;i++){
            for(int j = 0;j<height;j++){
                if(i>=seam[j]){
                    colorMap[i][j] = colorMap[i+1][j];
                    pEnergy[i][j] = pEnergy[i+1][j];
                }
            }
        }
        width--;
    }



    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());

        SeamCarver sc = new SeamCarver(picture);
        int[] temp;
        for(int i = 0;i<400;i++){
            temp = sc.findVerticalSeam();
            sc.removeVerticalSeam(temp);
        }
        int[] he;
        for(int j = 0;j<300;j++){
            he = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(he);
        }

        sc.picture().show();

    }

}
