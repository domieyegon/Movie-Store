
package moviestore;

import java.awt.Dimension;

public class MovieStore {

   
    public static void main(String[] args) {
        
        MainPage mp=new MainPage();
        mp.setSize(new Dimension(1300,700));
        mp.setLocation(30, 5);
        mp.setTitle("Movie Store");
        mp.setResizable(false);
        mp.setVisible(true);
        
        
    }
    
}
