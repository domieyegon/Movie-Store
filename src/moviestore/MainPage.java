/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviestore;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class MainPage extends JFrame implements ActionListener{
    
    
    JPanel titlepanel=new JPanel(),
            navpanel=new JPanel(),
            displaypanel=new JPanel(),
            memberpanel=new JPanel(),
            moviepanel=new JPanel(),
            borrpanel=new JPanel(),
            RETURNPANEL=new JPanel(),
            BORROWPANEL=new JPanel();
    
    JButton mbtn=new JButton("MEMBER"),
            mobtn=new JButton("MOVIES"),
            rbbtn=new JButton("RETURN/BORROW"),
             rebtn=new JButton("RETURN"),
             bobtn=new JButton("BORROW"),
             btnupdatemember=new JButton("UPDATE"),
             deletemember=new JButton("DELETE"),
             clearmember=new JButton("CLEAR"),
             btnsavemember=new JButton("SAVE"),
             btnupdatemovie=new JButton("UPDATE"),
             btndeletemovie=new JButton("DELETE"),
             btnclearmovie=new JButton("CLEAR"),
             btnsavemovie=new JButton("SAVE"),
             btnsaveborrower=new JButton("SAVE"),
             btnclearborrower=new JButton("CLEAR"),
            btnupdateborrower=new JButton("UPDATE"),
            btndeleteborrower=new JButton("DELETE"),
            btnprevborrower=new JButton("PREVIOUS"),
            btnshowborrowertable=new JButton("SHOW TABLE"),
            btnsavereturn=new JButton("SAVE"),
            btnclearreturn=new JButton("CLEAR"),
            btnupdatereturn=new JButton("UPDATE"),
            btndeletereturn=new JButton("DELETE"),
            btnprevreturn=new JButton("PREVIOUS"),
            btnreturntbl1=new JButton("SHOW TABLE");
    
    
    JTextField mid=new JTextField(),
               mname=new JTextField(),
               mmobile=new JTextField(),
               moid=new JTextField(),
               motypeno=new JTextField(),
               moname=new JTextField(),
               bjid=new JTextField(),
               bjname=new JTextField(),
               bjtype=new JTextField(),
               bjtitle=new JTextField(),
               rjid=new JTextField(),
               rjname=new JTextField(),
               rjtype=new JTextField(),
               rjtitle=new JTextField(),
               rjdate=new JTextField(),
               moparentid=new JTextField();
    
    
    JDateChooser borroweddate=new JDateChooser(),
                 duedate=new JDateChooser(),
                 returndate=new JDateChooser();
    
 String[] movielistdrop = {"Select Movies", "Action Movies", "Adventure Movies","Animation Movies",  "Comedy Movies", "Documentary Movies", "Horror Movies", "Musical Movies","Sci-Fi Movies", "SuperHero Movies", "War Movies" };
String[]  Movietypelist= {"Select Movie Type"};
String[] movietitlelist= {"Select Movie Title"};

//String[] ListOfBorrowers= {""};



JComboBox Borrowersdrop = new JComboBox();
JComboBox Returndrop = new JComboBox();
JComboBox movielistdropdown = new JComboBox(movielistdrop);
JComboBox movielist = new JComboBox(Movietypelist);
JComboBox movietitlelistdropdown = new JComboBox(movietitlelist);

UtilDateModel model = new UtilDateModel();
UtilDateModel duemodel = new UtilDateModel();
UtilDateModel retmodel = new UtilDateModel();
Properties p = new Properties();
       
         JDatePanelImpl duePanel = new JDatePanelImpl(duemodel,p);
         JDatePanelImpl rettPanel = new JDatePanelImpl(retmodel,p);
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
       JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter()),
                       dateDue=new JDatePickerImpl(duePanel, new DateLabelFormatter()),
                       dateReturned=new JDatePickerImpl(rettPanel, new DateLabelFormatter());

 

Connection conn=null;
    PreparedStatement pst= null;
    ResultSet rs=null;
    
    
    public void RandomSerialNO(){
    Random ra=new Random();
    mid.setText(""+ra.nextInt(10000+1));
    }
    
    public void RandomMovieID(){
    Random ra=new Random();
    moid.setText(""+ra.nextInt(1000+1));
    }
    
   
    
    public  MainPage(){
        
          conn=JavaConnect.ConnectDB();
          RandomSerialNO();
          RandomMovieID();
          SelectMovieTypeToCombo();
          LoadAllBorrowersNames();
          LoadAllReturndetails();
         
               
                mid.setEditable(false);
                moid.setEditable(false);
                moid.setBackground(Color.white);
                mid.setBackground(Color.white);
                memberpanel.setVisible(true);
                moviepanel.setVisible(false);
                borrpanel.setVisible(false);
                BORROWPANEL.setVisible(false);
                RETURNPANEL.setVisible(false);
                
        
        
        
        titlepanel.setLayout(new BoxLayout(titlepanel, BoxLayout.X_AXIS));
        titlepanel.setPreferredSize(new Dimension(1300,120));
        titlepanel.setBackground(Color.BLUE);
        JLabel title=new JLabel("SPENCE MOVIE STORE");
        title.setForeground(Color.white);
        //title.setPreferredSize(new Dimension(1000,120));
        title.setFont(new Font("Tohoma", 1, 48));
        //title.setAlignmentX(RIGHT_ALIGNMENT);
        //title.setLocation(750, 75);
        //titlepanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        titlepanel.add(title);
        
        getContentPane().add("North", titlepanel);
        
        
        navpanel.setLayout(new BoxLayout(navpanel, BoxLayout.Y_AXIS));
        navpanel.setMaximumSize(new Dimension(300,150));
        navpanel.setBackground(Color.BLUE);
        
        JPanel memberp=new JPanel();
        memberp.setBackground(Color.BLUE);
        memberp.setMaximumSize(new Dimension(300,150));
        mbtn.setFont(new Font("Tohoma", 1, 18));
        mbtn.addActionListener(this);
        mbtn.setForeground(Color.BLACK);
        mbtn.setPreferredSize(new Dimension(200,70));
        
        memberp.add(mbtn);
        navpanel.add(memberp);
        
        
         JPanel moviep=new JPanel();
         moviep.setBackground(Color.BLUE);
         moviep.setMaximumSize(new Dimension(300,150));
         mobtn.setFont(new Font("Tohoma", 1, 18));
         mobtn.addActionListener(this);
         mobtn.setForeground(Color.BLACK);
         mobtn.setPreferredSize(new Dimension(200,70));
         moviep.add(mobtn);
         navpanel.add(moviep);
        
         JPanel borrep=new JPanel();
         borrep.setBackground(Color.BLUE);
         borrep.setMaximumSize(new Dimension(300,150));
         rbbtn.setFont(new Font("Tohoma", 1, 18));
         rbbtn.addActionListener(this);
        rbbtn.setForeground(Color.BLACK);
        rbbtn.setPreferredSize(new Dimension(200,70));
        borrep.add(rbbtn);
        navpanel.add(borrep);
        
        getContentPane().add("West", navpanel);
        
        
        //this is for the add member panel
        
        displaypanel.setLayout(new BoxLayout(displaypanel, BoxLayout.Y_AXIS));
        displaypanel.setMaximumSize(new Dimension(1000,550));
        displaypanel.setBackground(Color.WHITE);
        
        memberpanel.setLayout(new BoxLayout(memberpanel, BoxLayout.Y_AXIS));
        memberpanel.setMaximumSize(new Dimension(1090,550));
        //memberpanel.setBackground(Color.LIGHT_GRAY);
       // memberpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
       
       
       
        JPanel re=new JPanel();
        re.setLayout(new BoxLayout(re, BoxLayout.X_AXIS));
        re.setMaximumSize(new Dimension(1090,70));
        re.setBackground(Color.CYAN);
        JLabel tttt=new JLabel("Register a new member!");
        tttt.setPreferredSize(new Dimension(100,70));
        tttt.setFont(new Font("Tohoma", 1, 34));
        re.add(tttt);
        //re.setAlignmentX(LEFT_ALIGNMENT);
        memberpanel.add(re);
        displaypanel.add(memberpanel);
        
         JPanel Wholepanel=new JPanel();
        Wholepanel.setLayout(new BoxLayout(Wholepanel, BoxLayout.X_AXIS));
        
        JPanel inputbtnpan=new JPanel();
        inputbtnpan.setLayout(new BoxLayout(inputbtnpan, BoxLayout.Y_AXIS));
        inputbtnpan.setMaximumSize(new Dimension(450,450)); 
        inputbtnpan.setAlignmentX(TOP_ALIGNMENT);
        
        JPanel inputfi=new JPanel();
        inputfi.setLayout(new BoxLayout(inputfi, BoxLayout.Y_AXIS));
        
        
        JPanel idpan=new JPanel();
       
        idpan.setMaximumSize(new Dimension(400,50));
        idpan.setBackground(Color.WHITE);
        JLabel idl=new JLabel("Serial No: ");
        idl.setPreferredSize(new Dimension(80,30));
        idl.setFont(new Font("Tohoma", 1, 14));
        idpan.add(idl);
        mid.setPreferredSize(new Dimension(300,30));
        mid.setFont(new Font("Tohoma", 1, 14));
        idpan.add(mid);
        //idpan.setAlignmentX(LEFT_ALIGNMENT);
        inputfi.add(idpan);
        inputbtnpan.add(inputfi);
        
        
        
        JPanel namepan=new JPanel();
        namepan.setMaximumSize(new Dimension(400,50));
        namepan.setBackground(Color.WHITE);
        JLabel namel=new JLabel("Full Name: ");
        namel.setPreferredSize(new Dimension(80,30));
        namel.setFont(new Font("Tohoma", 1, 14));
        namepan.add(namel);
        mname.setPreferredSize(new Dimension(300,30));
        mname.setFont(new Font("Tohoma", 1, 14));
        namepan.add(mname);
        //namepan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputfi.add(namepan);
        inputbtnpan.add(inputfi);
        
        
        
        JPanel mobilepan=new JPanel();
        mobilepan.setMaximumSize(new Dimension(400,70));
        mobilepan.setBackground(Color.WHITE);
        JLabel mobilel=new JLabel("Mobile: ");
        mobilel.setPreferredSize(new Dimension(80,30));
        mobilel.setFont(new Font("Tohoma", 1, 14));
        mobilepan.add(mobilel);
        mmobile.setPreferredSize(new Dimension(300,30));
        mmobile.setFont(new Font("Tohoma", 1, 14));
        mobilepan.add(mmobile);
        //mobilepan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputfi.add(mobilepan);
        inputbtnpan.add(inputfi);
        
        
        //BUTTONS IN THE MEMBER PANEL
        
        JPanel savememberpanel=new JPanel();
        //savememberpanel.setMaximumSize(new Dimension(1000,50));
        //savememberpanel.setBackground(Color.WHITE);
        savememberpanel.setLayout(new BoxLayout(savememberpanel, BoxLayout.X_AXIS));
        
       
        JPanel dettt=new JPanel();
        dettt.setBackground(Color.LIGHT_GRAY);
        dettt.setMaximumSize(new Dimension(430,50));
        
        
        btnsavemember.setPreferredSize(new Dimension(100,40));
        btnsavemember.setFont(new Font("Tohoma", 1, 14));
        btnsavemember.setActionCommand("savememberdetails");
        btnsavemember.setAlignmentX(LEFT_ALIGNMENT);
        btnsavemember.addActionListener(this);
        dettt.add(btnsavemember);
        savememberpanel.add(dettt);
        
       
       
        clearmember.setPreferredSize(new Dimension(100,40));
        clearmember.setActionCommand("clearmemberdetails");
        clearmember.setAlignmentX(LEFT_ALIGNMENT);
        clearmember.setFont(new Font("Tohoma", 1, 14));
        clearmember.addActionListener(this);
        dettt.add(clearmember);
        savememberpanel.add(dettt);
       
        
        
         
        btnupdatemember.setPreferredSize(new Dimension(100,40));
        btnupdatemember.setActionCommand("updatememberdetails");
        btnupdatemember.setFont(new Font("Tohoma", 1, 14));
        btnupdatemember.setAlignmentX(LEFT_ALIGNMENT);
        btnupdatemember.addActionListener(this);
        dettt.add(btnupdatemember);
        savememberpanel.add(dettt);
        
        
        deletemember.setPreferredSize(new Dimension(100,40));
        deletemember.setActionCommand("deletememberdetails");
        deletemember.setFont(new Font("Tohoma", 1, 14));
        deletemember.addActionListener(this);
        dettt.add(deletemember);
        savememberpanel.add(dettt);
        inputbtnpan.add(savememberpanel);
        Wholepanel.add(inputbtnpan);
        memberpanel.add(Wholepanel);
        
        displaypanel.add(memberpanel);
        
        
        //TABLE OF THE MEMBER PANEL
        
        
        JPanel panelt=new JPanel();
        panelt.setBackground(Color.LIGHT_GRAY);
        //panelt.setLayout(new BoxLayout(panelt, BoxLayout.Y_AXIS));
        panelt.setMaximumSize(new Dimension(650, 490));
        
         String data[][]={ {null,null,null,null},    
                          {null,null,null,null},    
                          {null,null,null,null}};    
         String column[]={"SERIAL NUMBER","CLIENT NAME","CLIENT PHONE NUMBER"};  
        
        membertbl = new JTable(data,column);
        membertbl.setPreferredSize(new Dimension(600, 560));
        
        //tbl.setBounds(30,40,200,300);
       
        membertbl.setCellSelectionEnabled(true);  
            ListSelectionModel select= membertbl.getSelectionModel();  
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select.addListSelectionListener(new ListSelectionListener() {  
              public void valueChanged(ListSelectionEvent e) {  
                TableMemberMouseClick();    
              }       
            });
        JScrollPane scp=new JScrollPane(membertbl); 
        scp.setPreferredSize(new Dimension(610, 470));
         MemberTable();
        
        panelt.add(scp);
        //panelt.setAlignmentX(LEFT_ALIGNMENT);
        
        // panelt.setBackground(Color.white);
         Wholepanel.add(panelt);
         memberpanel.add(Wholepanel);
        displaypanel.add(memberpanel);
        
       
        
        
        getContentPane().add(displaypanel);
        
        
       //This is for the movie panel
       
        
        
        moviepanel.setLayout(new BoxLayout(moviepanel, BoxLayout.Y_AXIS));
        moviepanel.setMaximumSize(new Dimension(1090,550));
        
        JPanel mov=new JPanel();
        mov.setLayout(new BoxLayout(mov, BoxLayout.X_AXIS));
        mov.setMaximumSize(new Dimension(1200,70));
        mov.setBackground(Color.CYAN);
        JLabel mmm=new JLabel("View Movies in our store & Add new ones");
        mmm.setPreferredSize(new Dimension(100,70));
        mmm.setFont(new Font("Tohoma", 1, 34));
        mov.add(mmm);
        moviepanel.add(mov);
        displaypanel.add(moviepanel);
        
         JPanel Wholemoviepanel=new JPanel();
        Wholemoviepanel.setLayout(new BoxLayout(Wholemoviepanel, BoxLayout.X_AXIS));
        
        JPanel movieinputbtnpan=new JPanel();
        movieinputbtnpan.setLayout(new BoxLayout(movieinputbtnpan, BoxLayout.Y_AXIS));
        movieinputbtnpan.setMaximumSize(new Dimension(500,400)); 
        //movieinputbtnpan.setBackground(Color.red);
        movieinputbtnpan.setAlignmentX(TOP_ALIGNMENT);
        
        JPanel moinputfi=new JPanel();
        moinputfi.setLayout(new BoxLayout(moinputfi, BoxLayout.Y_AXIS));
        
        
        JPanel idmpan=new JPanel();
        idmpan.setMaximumSize(new Dimension(450,70));
        idmpan.setBackground(Color.WHITE);
        JLabel idml=new JLabel("Movie Id: ");
        idml.setPreferredSize(new Dimension(100,30));
        idml.setFont(new Font("Tohoma", 1, 14));
        idmpan.add(idml);
        moid.setPreferredSize(new Dimension(300,30));
        idmpan.add(moid);
       // idmpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        moinputfi.add(idmpan);
       
        
        
        JPanel namempan=new JPanel();
        namempan.setMaximumSize(new Dimension(450,70));
        namempan.setBackground(Color.WHITE);
        JLabel mtypel=new JLabel("Movie Type: ");
        mtypel.setPreferredSize(new Dimension(100,30));
        mtypel.setFont(new Font("Tohoma", 1, 14));
        namempan.add(mtypel);
        movielistdropdown.setSelectedIndex(0);
        movielistdropdown.addActionListener(this);
        movielistdropdown.setActionCommand("selectmoviecombo");
        movielistdropdown.setPreferredSize(new Dimension(300,30));
        namempan.add(movielistdropdown);
        //namempan.setAlignmentX(Component.LEFT_ALIGNMENT);
        moinputfi.add(namempan);
        
        
        
        JPanel mnpan=new JPanel();
        mnpan.setMaximumSize(new Dimension(450,70));
        mnpan.setBackground(Color.WHITE);
        JLabel monml=new JLabel("Movie Name: ");
        monml.setPreferredSize(new Dimension(100,30));
        monml.setFont(new Font("Tohoma", 1, 14));
        mnpan.add(monml);
        
        moname.addActionListener(this);   
        moname.setPreferredSize(new Dimension(300,30));
        mnpan.add(moname);
        //mnpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        moinputfi.add(mnpan);
        
        
         JPanel mnopan=new JPanel();
        mnopan.setMaximumSize(new Dimension(450,70));
        mnopan.setBackground(Color.WHITE);
        JLabel monoml=new JLabel("NoOf Movies: ");
        monoml.setPreferredSize(new Dimension(100,30));
        monoml.setFont(new Font("Tohoma", 1, 14));
        mnopan.add(monoml);
        motypeno.addActionListener(this);   
        motypeno.setPreferredSize(new Dimension(300,30));
        mnopan.add(motypeno);
        //mnpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        moinputfi.add(mnopan);
        
        /*
        
        JPanel mdespan=new JPanel();
        mdespan.setMaximumSize(new Dimension(450,70));
        mdespan.setBackground(Color.WHITE);
        JLabel modesml=new JLabel("Movie par Id: ");
        modesml.setPreferredSize(new Dimension(100,30));
        modesml.setFont(new Font("Tohoma", 1, 14));
        mdespan.add(modesml);
        moparentid.setPreferredSize(new Dimension(300,30));
        mdespan.add(moparentid);
        //mnpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        moinputfi.add(mdespan);
        
        */
        
        movieinputbtnpan.add(moinputfi);
        
        moviepanel.add(movieinputbtnpan);
        
         //BUTTONS IN THE MOVIE PANEL
        
        JPanel savemoviepanel=new JPanel();
        //savememberpanel.setMaximumSize(new Dimension(1000,50));
        //savememberpanel.setBackground(Color.WHITE);
        savemoviepanel.setLayout(new BoxLayout(savemoviepanel, BoxLayout.X_AXIS));
        
       
        JPanel qqqqq=new JPanel();
        //qqqqq.setBackground(Color.LIGHT_GRAY);
        qqqqq.setMaximumSize(new Dimension(430,50));
        
        
        btnsavemovie.setPreferredSize(new Dimension(100,40));
        btnsavemovie.setFont(new Font("Tohoma", 1, 14));
        btnsavemovie.setActionCommand("savemoviedetails");
        btnsavemovie.setAlignmentX(LEFT_ALIGNMENT);
        btnsavemovie.addActionListener(this);
        qqqqq.add(btnsavemovie);
        savemoviepanel.add(qqqqq);
        
       
       
        btnclearmovie.setPreferredSize(new Dimension(100,40));
        btnclearmovie.setActionCommand("clearmoviedetails");
        btnclearmovie.setAlignmentX(LEFT_ALIGNMENT);
        btnclearmovie.setFont(new Font("Tohoma", 1, 14));
        btnclearmovie.addActionListener(this);
        qqqqq.add(btnclearmovie);
        savemoviepanel.add(qqqqq);
       
        
        
         
        btnupdatemovie.setPreferredSize(new Dimension(100,40));
        btnupdatemovie.setActionCommand("updatemoviedetails");
        btnupdatemovie.setFont(new Font("Tohoma", 1, 14));
        btnupdatemovie.setAlignmentX(LEFT_ALIGNMENT);
        btnupdatemovie.addActionListener(this);
        qqqqq.add(btnupdatemovie);
        savemoviepanel.add(qqqqq);
        
        
        btndeletemovie.setPreferredSize(new Dimension(100,40));
        btndeletemovie.setActionCommand("deletemoviedetails");
        btndeletemovie.setFont(new Font("Tohoma", 1, 14));
        btndeletemovie.addActionListener(this);
        qqqqq.add(btndeletemovie);
        savemoviepanel.add(qqqqq);
        
        movieinputbtnpan.add(savemoviepanel);
       Wholemoviepanel.add(movieinputbtnpan);
        moviepanel.add(Wholemoviepanel);
        
        
         //TABLE OF THE MOVIE PANEL
        
        
        JPanel panelt2=new JPanel();
        panelt2.setBackground(Color.LIGHT_GRAY);
        //panelt.setLayout(new BoxLayout(panelt, BoxLayout.Y_AXIS));
        panelt2.setMaximumSize(new Dimension(650, 490));
        
         String object[][]={ {null,null,null,null},    
                          {null,null,null,null},    
                          {null,null,null,null}};    
         String column1[]={"MOVIE ID","MOVIE TYPE","MOVIE NAME"};  
        
        movietbl = new JTable(object,column1);
        movietbl.setPreferredSize(new Dimension(600, 560));
        
        //tbl.setBounds(30,40,200,300);
       
        movietbl.setCellSelectionEnabled(true);  
            ListSelectionModel select1= movietbl.getSelectionModel();  
            select1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select1.addListSelectionListener(new ListSelectionListener() {  
              public void valueChanged(ListSelectionEvent e) {  
                TableMovieMouseClick();    
              }       
            });
        JScrollPane scp1=new JScrollPane(movietbl); 
        scp1.setPreferredSize(new Dimension(610, 470));
         MovieTable();
        
        panelt2.add(scp1);
        //panelt.setAlignmentX(LEFT_ALIGNMENT);
        
        // panelt.setBackground(Color.white);
         Wholemoviepanel.add(panelt2);
         moviepanel.add(Wholemoviepanel);
        
        
       
 
        displaypanel.add(moviepanel);
        
        
        getContentPane().add(displaypanel);
        
        
        //The return and borrow panel
        
        
         borrpanel.setLayout(new BoxLayout(borrpanel, BoxLayout.Y_AXIS));
        borrpanel.setMaximumSize(new Dimension(1090,550));
       borrpanel.setBackground(Color.yellow);
        
        
        
        
        JPanel returnnborp=new JPanel();
        returnnborp.setLayout(new BoxLayout(returnnborp, BoxLayout.X_AXIS));
        returnnborp.setMaximumSize(new Dimension(1300,50));
        returnnborp.setBackground(Color.WHITE);
        
        JPanel ytyt=new JPanel();
        
        //ytyt.setPreferredSize(new Dimension(500,50));
        bobtn.setPreferredSize(new Dimension(200,40));
        bobtn.setFont(new Font("Tohoma", 1, 14));
       // bobtn.setAlignmentX(LEFT_ALIGNMENT);
        bobtn.addActionListener(this);
        ytyt.add(bobtn);
        returnnborp.add(ytyt);
        
        JPanel uyyt=new JPanel();
        //uyyt.setPreferredSize(new Dimension(500,50));
        rebtn.setPreferredSize(new Dimension(200,40));
        rebtn.setFont(new Font("Tohoma", 1, 14));
        rebtn.addActionListener(this);
        uyyt.add(rebtn);
        returnnborp.add(uyyt);
       
        borrpanel.add(returnnborp);
        displaypanel.add(borrpanel);
        
        
        
        //the borrow panel
        BORROWPANEL.setLayout(new BoxLayout(BORROWPANEL, BoxLayout.Y_AXIS));
        BORROWPANEL.setMaximumSize(new Dimension(1200,500));
        BORROWPANEL.setBackground(Color.red);
        
       
        
        JPanel bor=new JPanel();
        bor.setLayout(new BoxLayout(bor, BoxLayout.X_AXIS));
        bor.setMaximumSize(new Dimension(1300,70));
        bor.setBackground(Color.CYAN);
        JLabel bbb=new JLabel("Borrow A Movie");
        bbb.setPreferredSize(new Dimension(100,70));
        bbb.setFont(new Font("Tohoma", 1, 34));
        bor.add(bbb);
        BORROWPANEL.add(bor);
        borrpanel.add(BORROWPANEL);
        displaypanel.add(borrpanel);
        
        
         JPanel inputbtns=new JPanel();
        inputbtns.setLayout(new BoxLayout(inputbtns, BoxLayout.X_AXIS));
        inputbtns.setMaximumSize(new Dimension(1090,550));
        //inputbtns.setBackground(Color.GREEN);
        
        JPanel inputborbtns=new JPanel();
        inputborbtns.setLayout(new BoxLayout(inputborbtns, BoxLayout.Y_AXIS));
        inputborbtns.setMaximumSize(new Dimension(500,550));
        //inputborbtns.setBackground(Color.GREEN);
                 
        JPanel idbpan=new JPanel();
        idbpan.setMaximumSize(new Dimension(500,70));
        idbpan.setBackground(Color.WHITE);
        JLabel idbl=new JLabel("Borrower Id: ");
        idbl.setPreferredSize(new Dimension(130,30));
        idbl.setFont(new Font("Tohoma", 1, 14));
        idbpan.add(idbl);
        bjid.setPreferredSize(new Dimension(300,30));
        bjid.setFont(new Font("Tohoma", 1, 14));
        idbpan.add(bjid);
        //idbpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(idbpan);
       
        
        JPanel namebpan=new JPanel();
        namebpan.setMaximumSize(new Dimension(500,70));
        namebpan.setBackground(Color.WHITE);
        JLabel namebl=new JLabel("Borrower Name: ");
        namebl.setPreferredSize(new Dimension(130,30));
        namebl.setFont(new Font("Tohoma", 1, 14));
        namebpan.add(namebl);
        bjname.setPreferredSize(new Dimension(300,30));
        bjname.setFont(new Font("Tohoma", 1, 14));
        namebpan.add(bjname);
        //namebpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(namebpan);
       
        
        
         JPanel typebpan=new JPanel();
        typebpan.setMaximumSize(new Dimension(500,70));
        typebpan.setBackground(Color.WHITE);
        JLabel typebl=new JLabel("Movie Type: ");
        typebl.setPreferredSize(new Dimension(130,30));
        typebl.setFont(new Font("Tohoma", 1, 14));
        typebpan.add(typebl);
        movielist.setSelectedIndex(0);
        movielist.setActionCommand("selectonekindofmovies");
        movielist.addActionListener(this);   
        movielist.setPreferredSize(new Dimension(300,30));
        movielist.setFont(new Font("Tohoma", 1, 14));
        typebpan.add(movielist);
        //typebpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(typebpan);
       
        
        
         JPanel titlebpan=new JPanel();
        titlebpan.setMaximumSize(new Dimension(500,70));
        titlebpan.setBackground(Color.WHITE);
        JLabel titlebl=new JLabel("Movie Title: ");
        titlebl.setPreferredSize(new Dimension(130,30));
        titlebl.setFont(new Font("Tohoma", 1, 14));
        titlebpan.add(titlebl);
        movietitlelistdropdown.setSelectedIndex(0);
        movietitlelistdropdown.addActionListener(this);   
        movietitlelistdropdown.setPreferredSize(new Dimension(300,30));
        movietitlelistdropdown.setFont(new Font("Tohoma", 1, 14));
        titlebpan.add(movietitlelistdropdown);
        //titlebpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(titlebpan);
       
        
        
         JPanel datebpan=new JPanel();
        datebpan.setMaximumSize(new Dimension(500,70));
        datebpan.setBackground(Color.WHITE);
        JLabel datebl=new JLabel("Date Borrowed: ");
        datebl.setPreferredSize(new Dimension(130,30));
        datebl.setFont(new Font("Tohoma", 1, 14));
        datebpan.add(datebl);
       borroweddate.setPreferredSize(new Dimension(300,30));
       borroweddate.setDateFormatString("yyyy-MM-dd");
       borroweddate.setFont(new Font("Tohoma", 1, 14));
       datebpan.add(borroweddate);
       //datebpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(datebpan);
       
        
        
        
         JPanel datedpan=new JPanel();
        datedpan.setMaximumSize(new Dimension(500,70));
        datedpan.setBackground(Color.WHITE);
        JLabel datedl=new JLabel("Due Date: ");
        datedl.setPreferredSize(new Dimension(130,30));
        datedl.setFont(new Font("Tohoma", 1, 14));
        datedpan.add(datedl);
       duedate.setPreferredSize(new Dimension(300,30));
       duedate.setFont(new Font("Tohoma", 1, 14));
       duedate.setDateFormatString("yyyy-MM-dd");
       datedpan.add(duedate);
       //datedpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputborbtns.add(datedpan);
        inputbtns.add(inputborbtns);
        
        
         //BUTTONS IN THE MOVIE PANEL
        
        JPanel borrowmoviepanel=new JPanel();
        //savememberpanel.setMaximumSize(new Dimension(1000,50));
        savememberpanel.setBackground(Color.WHITE);
        borrowmoviepanel.setLayout(new BoxLayout(borrowmoviepanel, BoxLayout.X_AXIS));
        
       
        JPanel zzzzz=new JPanel();
        //qqqqq.setBackground(Color.LIGHT_GRAY);
        zzzzz.setMaximumSize(new Dimension(210,500));
        //zzzzz.setBackground(Color.ORANGE);
        zzzzz.setLayout(new BoxLayout(zzzzz, BoxLayout.Y_AXIS));
        
        JPanel savebtnbb=new JPanel();
        savebtnbb.setMaximumSize(new Dimension(200,50));
        
        btnsaveborrower.setPreferredSize(new Dimension(150,40));
        btnsaveborrower.setFont(new Font("Tohoma", 1, 14));
        btnsaveborrower.setActionCommand("savemovieborrower");
        btnsaveborrower.addActionListener(this);
        savebtnbb.add(btnsaveborrower);
        zzzzz.add(savebtnbb);
        borrowmoviepanel.add(zzzzz);
        
       
        JPanel clearbtnbb=new JPanel();
        clearbtnbb.setMaximumSize(new Dimension(200,50));
        btnclearborrower.setPreferredSize(new Dimension(150,40));
        btnclearborrower.setActionCommand("clearmovieborrower");
        btnclearborrower.setAlignmentX(LEFT_ALIGNMENT);
        btnclearborrower.setFont(new Font("Tohoma", 1, 14));
        btnclearborrower.addActionListener(this);
        clearbtnbb.add(btnclearborrower);
        zzzzz.add(clearbtnbb);
        borrowmoviepanel.add(zzzzz);
       
        
        
        JPanel updatebtnbb=new JPanel();
        updatebtnbb.setMaximumSize(new Dimension(200,50)); 
        btnupdateborrower.setPreferredSize(new Dimension(150,40));
        btnupdateborrower.setActionCommand("updatemovieborrower");
        btnupdateborrower.setFont(new Font("Tohoma", 1, 14));
      //  btnupdateborrower.setAlignmentX(LEFT_ALIGNMENT);
        btnupdateborrower.addActionListener(this);
        updatebtnbb.add(btnupdateborrower);
        zzzzz.add(updatebtnbb);
        borrowmoviepanel.add(zzzzz);
        
        
         JPanel deletebtnbb=new JPanel();
        deletebtnbb.setMaximumSize(new Dimension(200,50)); 
        btndeleteborrower.setPreferredSize(new Dimension(150,40));
        btndeleteborrower.setActionCommand("deletemovieborrower");
        btndeleteborrower.setFont(new Font("Tohoma", 1, 14));
        btndeleteborrower.addActionListener(this);
        deletebtnbb.add(btndeleteborrower);
        zzzzz.add(deletebtnbb);
        borrowmoviepanel.add(zzzzz);
        
        inputbtns.add(borrowmoviepanel);
   
        //codind for borrower dropdown
        JPanel paneldropdown=new JPanel();
        paneldropdown.setMaximumSize(new Dimension(400,400));
        
        JPanel drop=new JPanel();
        drop.setMaximumSize(new Dimension(400,400));
        drop.setLayout(new BoxLayout(drop, BoxLayout.Y_AXIS));
        
         JPanel prevbtnbb=new JPanel();
        prevbtnbb.setMaximumSize(new Dimension(400,50)); 
        Borrowersdrop.setPreferredSize(new Dimension(350,40));
        Borrowersdrop.setActionCommand("placedatatoimputfields");
        Borrowersdrop.setFont(new Font("Tohoma", 1, 14));
        Borrowersdrop.addActionListener(this);
        prevbtnbb.add(Borrowersdrop);
        drop.add(prevbtnbb);
        paneldropdown.add(drop);
        
        
         JPanel nextbtnbb=new JPanel();
        nextbtnbb.setMaximumSize(new Dimension(400,50)); 
        btnshowborrowertable.setPreferredSize(new Dimension(350,40));
        btnshowborrowertable.setActionCommand("showborrowertable");
        btnshowborrowertable.setFont(new Font("Tohoma", 1, 18));
        btnshowborrowertable.addActionListener(this);
        nextbtnbb.add(btnshowborrowertable);
        drop.add(nextbtnbb);
        paneldropdown.add(drop);
        
        inputbtns.add(paneldropdown);
        
       
        
        BORROWPANEL.add(inputbtns);
        
        
        borrpanel.add(BORROWPANEL);
        displaypanel.add(borrpanel);
        
        
        //the return panel
        RETURNPANEL.setLayout(new BoxLayout(RETURNPANEL, BoxLayout.Y_AXIS));
        RETURNPANEL.setMaximumSize(new Dimension(1300,500));
        
       
        
        
        JPanel ret=new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
        ret.setMaximumSize(new Dimension(1300,70));
        ret.setBackground(Color.CYAN);
        JLabel rrr=new JLabel("Return A Movie");
        rrr.setPreferredSize(new Dimension(100,70));
        rrr.setFont(new Font("Tohoma", 1, 34));
        ret.add(rrr);
        RETURNPANEL.add(ret);
        borrpanel.add(RETURNPANEL);
        displaypanel.add(borrpanel);
        
        
         JPanel allreturnfields=new JPanel();
        allreturnfields.setLayout(new BoxLayout(allreturnfields, BoxLayout.X_AXIS));
        allreturnfields.setMaximumSize(new Dimension(1300,500));
        
        
        
        
         JPanel textfieldsandlables=new JPanel();
        textfieldsandlables.setLayout(new BoxLayout(textfieldsandlables, BoxLayout.Y_AXIS));
        textfieldsandlables.setMaximumSize(new Dimension(500,500));
        
        
        JPanel idrpan=new JPanel();
        idrpan.setMaximumSize(new Dimension(500,70));
        idrpan.setBackground(Color.WHITE);
        JLabel idrl=new JLabel("Borrower Id: ");
        idrl.setPreferredSize(new Dimension(130,30));
        idrl.setFont(new Font("Tohoma", 1, 14));
        idrpan.add(idrl);
        rjid.setPreferredSize(new Dimension(300,30));
        rjid.setActionCommand("checkmembership");
//        rjid.addFocusListener(this);
        idrpan.add(rjid);
        idrpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(idrpan);
       
        
        JPanel namerpan=new JPanel();
        namerpan.setMaximumSize(new Dimension(500,70));
        namerpan.setBackground(Color.WHITE);
        JLabel namerl=new JLabel("Borrower Name: ");
        namerl.setPreferredSize(new Dimension(130,30));
        namerl.setFont(new Font("Tohoma", 1, 14));
        namerpan.add(namerl);
        rjname.setPreferredSize(new Dimension(300,30));
        namerpan.add(rjname);
        namerpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(namerpan);
       
        
        
         JPanel typerpan=new JPanel();
        typerpan.setMaximumSize(new Dimension(500,70));
        typerpan.setBackground(Color.WHITE);
        JLabel typerl=new JLabel("Movie Type: ");
        typerl.setPreferredSize(new Dimension(130,30));
        typerl.setFont(new Font("Tohoma", 1, 14));
        typerpan.add(typerl);
        rjtype.setPreferredSize(new Dimension(300,30));
        typerpan.add(rjtype);
        typerpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(typerpan);
        
        
        
         JPanel titlerpan=new JPanel();
        titlerpan.setMaximumSize(new Dimension(500,70));
        titlerpan.setBackground(Color.WHITE);
        JLabel titlerl=new JLabel("Movie Title: ");
        titlerl.setPreferredSize(new Dimension(130,30));
        titlerl.setFont(new Font("Tohoma", 1, 14));
        titlerpan.add(titlerl);
        rjtitle.setPreferredSize(new Dimension(300,30));
        titlerpan.add(rjtitle);
        titlerpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(titlerpan);
        
        
        
        JPanel daterpan=new JPanel();
        daterpan.setMaximumSize(new Dimension(500,70));
        daterpan.setBackground(Color.WHITE);
        JLabel daterl=new JLabel("Date Borrowed: ");
        daterl.setPreferredSize(new Dimension(130,30));
        daterl.setFont(new Font("Tohoma", 1, 14));
        daterpan.add(daterl);
        rjdate.setPreferredSize(new Dimension(300,30));
        daterpan.add(rjdate);
        daterpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(daterpan);
        
        
        
        JPanel daterepan=new JPanel();
        daterepan.setMaximumSize(new Dimension(500,70));
        daterepan.setBackground(Color.WHITE);
        JLabel daterel=new JLabel("Date Returned: ");
        daterel.setPreferredSize(new Dimension(130,30));
        daterel.setFont(new Font("Tohoma", 1, 14));
        daterepan.add(daterel);
       returndate.setPreferredSize(new Dimension(300,30));
       returndate.setDateFormatString("yyyy-MM-dd");
       daterepan.add(returndate);
       daterepan.setAlignmentX(Component.LEFT_ALIGNMENT);
        textfieldsandlables.add(daterepan);
        
        allreturnfields.add(textfieldsandlables);
        
        
         //BUTTONS IN THE RETURN MOVIE PANEL
        
        JPanel returnmoviepanel=new JPanel();
        returnmoviepanel.setLayout(new BoxLayout(returnmoviepanel, BoxLayout.X_AXIS));
        //returnmoviepanel.setBackground(Color.GREEN);
       
        JPanel rrrrrr=new JPanel();
        //rrrrrr.setBackground(Color.LIGHT_GRAY);
        rrrrrr.setMaximumSize(new Dimension(210,500));
        //zzzzz.setBackground(Color.ORANGE);
        rrrrrr.setLayout(new BoxLayout(rrrrrr, BoxLayout.Y_AXIS));
       
        JPanel updatebtnrr=new JPanel();
        updatebtnrr.setMaximumSize(new Dimension(200,50)); 
        btnupdatereturn.setPreferredSize(new Dimension(150,40));
        btnupdatereturn.setActionCommand("updatemoviereturn");
        btnupdatereturn.setFont(new Font("Tohoma", 1, 14));
      //  btnupdateborrower.setAlignmentX(LEFT_ALIGNMENT);
        btnupdatereturn.addActionListener(this);
        updatebtnrr.add(btnupdatereturn);
        rrrrrr.add(updatebtnrr);
        returnmoviepanel.add(rrrrrr);
     
        
        allreturnfields.add(returnmoviepanel);
        
         //codind for borrower dropdown
        JPanel paneldropdownreturn=new JPanel();
        paneldropdownreturn.setMaximumSize(new Dimension(400,400));
        
        JPanel drop1=new JPanel();
        drop1.setMaximumSize(new Dimension(400,400));
        drop1.setLayout(new BoxLayout(drop1, BoxLayout.Y_AXIS));
        
         JPanel showrettbl=new JPanel();
        showrettbl.setMaximumSize(new Dimension(400,50)); 
        Returndrop.setPreferredSize(new Dimension(350,40));
        Returndrop.setActionCommand("placedatatoimputfieldsinreturnpanel");
        Returndrop.setFont(new Font("Tohoma", 1, 14));
        Returndrop.addActionListener(this);
        showrettbl.add(Returndrop);
        drop1.add(showrettbl);
        paneldropdownreturn.add(drop1);
        
        
         JPanel showrettbl1=new JPanel();
        showrettbl1.setMaximumSize(new Dimension(400,50)); 
        btnreturntbl1.setPreferredSize(new Dimension(350,40));
        btnreturntbl1.setActionCommand("showreturntable");
        btnreturntbl1.setFont(new Font("Tohoma", 1, 18));
        btnreturntbl1.addActionListener(this);
        showrettbl1.add(btnreturntbl1);
        drop1.add(showrettbl1);
        paneldropdownreturn.add(drop1);
        
        allreturnfields.add(paneldropdownreturn);
        
        //inputborbtns.add(borrowmoviepanel);
        
       
        
        
        
        RETURNPANEL.add(allreturnfields);
        borrpanel.add(RETURNPANEL);
        displaypanel.add(borrpanel);
        
        
        getContentPane().add(displaypanel);
        
         setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        switch (e.getActionCommand()) {
            case "MEMBER":
                memberpanel.setVisible(true);
                moviepanel.setVisible(false);
                borrpanel.setVisible(false);
                BORROWPANEL.setVisible(false);
                RETURNPANEL.setVisible(false);
                rebtn.setVisible(false);
                bobtn.setVisible(false);
                break;
            case "MOVIES":
                memberpanel.setVisible(false);
                moviepanel.setVisible(true);
                borrpanel.setVisible(false);
                BORROWPANEL.setVisible(false);
                RETURNPANEL.setVisible(false);
                rebtn.setVisible(false);
                bobtn.setVisible(false);
                break;
            case "RETURN/BORROW":
                memberpanel.setVisible(false);
                moviepanel.setVisible(false);
                borrpanel.setVisible(true);
                BORROWPANEL.setVisible(true);
                RETURNPANEL.setVisible(false);
                rebtn.setVisible(true);
                bobtn.setVisible(true);
                break;
             case "RETURN":
                memberpanel.setVisible(false);
                moviepanel.setVisible(false);
                borrpanel.setVisible(true);
                BORROWPANEL.setVisible(false);
                RETURNPANEL.setVisible(true);
                rebtn.setVisible(true);
                bobtn.setVisible(true);
                SetNotEditable();
                break; 
             case "BORROW":
                memberpanel.setVisible(false);
                moviepanel.setVisible(false);
                borrpanel.setVisible(true);
                BORROWPANEL.setVisible(true);
                RETURNPANEL.setVisible(false);
                rebtn.setVisible(true);
                bobtn.setVisible(true);
                break;    
            case "savememberdetails":
                SaveMemberData();
                RandomSerialNO();
                 MemberTable();
                break;
            case "clearmemberdetails":
                mid.setText("");
                mname.setText("");
                mmobile.setText("");
                RandomSerialNO();
                break; 
            case "updatememberdetails":
                UpdateMemberDetails();
                RandomSerialNO();
                break;
            case "deletememberdetails":
                deleteMemberdetails();
                RandomSerialNO();
                MemberTable();
                break;
            case "savemoviedetails":
                SaveMovieData();
                RandomMovieID();
                MovieTable();
                
                SelectMovieTypeToCombo();
                break; 
            case "clearmoviedetails":
                moid.setText("");
                moname.setText("");
                moparentid.setText("");
                motypeno.setText("");
                movielistdropdown.setSelectedIndex(0);
                RandomMovieID();
                break;
             case "updatemoviedetails":
                UpdateMovieDtails();
                RandomMovieID();
                MovieTable();
                
                SelectMovieTypeToCombo();
                break;    
             case "deletemoviedetails":
                DeleteMovieData();
                RandomMovieID();
                MovieTable();
                
                SelectMovieTypeToCombo();
                break;  
             case "savemovieborrower": 
                SaveBorrowerData(); 
                SelectMovieTypeToCombo();
                LoadAllBorrowersNames();
                LoadAllReturndetails();
                break; 
            case "clearmovieborrower":
                bjid.setText("");
                bjname.setText("");
                movielist.setSelectedIndex(0);
                movietitlelistdropdown.setSelectedItem("");
                ((JTextField)borroweddate.getDateEditor().getUiComponent()).setText("");
                ((JTextField)duedate.getDateEditor().getUiComponent()).setText("");
                break;     
            case "updatemovieborrower":
                UpdateBorrowerDetails();
                SelectMovieTypeToCombo();
                LoadAllBorrowersNames();
                LoadAllReturndetails();
                break;  
            case "deletemovieborrower": 
                DeleteBorrowerData();
                LoadAllBorrowersNames();
                SelectMovieTypeToCombo();
                LoadAllReturndetails();
                break;  
            case "prevmovieborrower":
                JOptionPane.showMessageDialog(null, "Previous");
                break;  
                
                
            case "selectmoviecombo":
                
                 movietypecombo();
                break; 
            case "selectonekindofmovies":
                 movietitlelistdropdown.removeAllItems();
                 LoadMovieTitles();
                break;     
            case "placedatatoimputfields":
                 movietitlelistdropdown.removeAllItems();
                 movielist.removeAllItems();
                PlaceAllInInputFields();
                break; 
            case "placedatatoimputfieldsinreturnpanel":
                PlaceAllReturnInputFields();
                break; 
               
            case "showborrowertable": 
                ShowBorrTable();
                borrowerdialogue.setLocationRelativeTo(null);
                break; 
            case "updatemoviereturn":
               UpdateReturnDetails();
                break;
            case "showreturntable":
                ShowReturnerTable();
                returnerdialogue.setLocationRelativeTo(null);
                break; 
                
            default:
                break;
        }
        
        
    }
    
    
   public void focusLost(FocusEvent fe){
       
       bjid.setText(fe.getComponent().getClass().getName()+"is it working");
       
   }
    
    
    
    
    //code for saving member details to database
    private void SaveMemberData(){
        
        if (mid.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please input the Serial Number Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(mname.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please input the Name Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(mmobile.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please input the Phone Number Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                
                String sql="insert into member(serialno,name,mobile)values(?,?,?)";
                
                pst=conn.prepareStatement(sql);
                
                pst.setString(1, mid.getText());
                pst.setString(2, mname.getText());
                pst.setString(3, mmobile.getText());
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Saved!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        }
        
                mid.setText("");
                mname.setText("");
                mmobile.setText("");
        
    }
    
    private void UpdateMemberDetails(){
         
         if(mid.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated", "Error" ,JOptionPane.ERROR_MESSAGE);
        }else if( mname.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated", "Error" ,JOptionPane.ERROR_MESSAGE);
        }else if(mmobile.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated", "Error" ,JOptionPane.ERROR_MESSAGE);
        }else{
             try {
                 String value1=mid.getText();
                 String value2=mname.getText();
                 String value3=mmobile.getText();
                 
                 
                 
                 String sql="update member set serialno='"+value1+"',name='"+value2+"',mobile='"+value3+"'where serialno='"+value1+"'";
                 pst=conn.prepareStatement(sql);
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Updated!");
             } catch (SQLException ex) {
                 System.out.println(ex);
             }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
         }MemberTable();
          mid.setText("");
          mname.setText("");
          mmobile.setText("");
    }
    
    //delete member details
    private void deleteMemberdetails(){
         if(mid.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Deleted", "Error" ,JOptionPane.ERROR_MESSAGE);
        }else if( mname.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted", "Error" ,JOptionPane.ERROR_MESSAGE);
        }else if(mmobile.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted", "Error" ,JOptionPane.ERROR_MESSAGE);
        }
         
         
         else{
              int p=JOptionPane.showConfirmDialog(null, "Do you really want to permanently delete the details of  "+mname.getText(),"Delete",JOptionPane.YES_NO_OPTION);
             if(p==0){
                 try {
                      String sql="delete from member where serialno=?";
                        pst=conn.prepareStatement(sql);

                        pst.setString(1, mid.getText());

                        pst.execute();
                 
                 JOptionPane.showMessageDialog(null, "Deleted!");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
                
             }
         }
                mid.setText("");
                mname.setText("");
                mmobile.setText("");
    }
    
    private void MemberTable(){
        
        try {
            String sql="select serialno as 'SERIAL NUMBER', name as 'CLIENT NAME', mobile 'PHONE NUMBER' from member";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            membertbl.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    private void TableMemberMouseClick() {
        try {
            
        int i=membertbl.getSelectedRow();
        TableModel model=membertbl.getModel();
        mid.setText(model.getValueAt(i, 0).toString());
        mname.setText(model.getValueAt(i, 1).toString());
        mmobile.setText(model.getValueAt(i, 2).toString());
            
        } catch (Exception e) {
           
        }  
    }
    
    
    
    //code for saving movie details to database
    private void SaveMovieData(){
         if(moparentid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please input the Movie type Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (moname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please input the Movie Name Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(motypeno.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please input the Number of that kind of movies Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                
                String sql="insert into savemovie(movieid,movietype,moviename,movieparid,number)values(?,?,?,?,?)";
                
                pst=conn.prepareStatement(sql);
                
                pst.setString(1, moid.getText());
                String val1=movielistdropdown.getSelectedItem().toString();
                pst.setString(2, val1);
                pst.setString(3, moname.getText());
                pst.setString(4, moparentid.getText());
                 pst.setString(5, motypeno.getText());
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Saved!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        }
        
               moid.setText("");
                moname.setText("");
                moparentid.setText("");
                 movielistdropdown.setSelectedIndex(0);
                motypeno.setText("");
         
    }
    
    
    
    private void UpdateMovieDtails(){
        
          if(moparentid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (moname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(motypeno.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
             try {
                 String value1=moid.getText();
                  String value5=movielistdropdown.getSelectedItem().toString();
                 String value2=moname.getText();
                 String value3=moparentid.getText();
                 String value4=motypeno.getText();
                 
                 
                 
                 String sql="update savemovie set movieid='"+value1+"',movietype='"+value5+"',moviename='"+value2+"',movieparid='"+value3+"',number='"+value4+"'where movieid='"+value1+"'";
                 pst=conn.prepareStatement(sql);
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Updated!");
             } catch (SQLException ex) {
                 System.out.println(ex);
             }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
         }      
                moid.setText("");
                moname.setText("");
                moparentid.setText("");
                movielistdropdown.setSelectedIndex(0);
                motypeno.setText("");
       
    }
    
    
    private void DeleteMovieData(){
          if(moparentid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (moname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(motypeno.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }
         

         else{
              int p=JOptionPane.showConfirmDialog(null, "Do you really want to permanently delete the details of  "+moname.getText(),"Delete",JOptionPane.YES_NO_OPTION);
             if(p==0){
                 try {
                      String sql="delete from savemovie where movieid=?";
                        pst=conn.prepareStatement(sql);

                        pst.setString(1, moid.getText());

                        pst.execute();
                 
                 JOptionPane.showMessageDialog(null, "Deleted!");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
                
             }
         }
                moid.setText("");
                moname.setText("");
                moparentid.setText("");
                motypeno.setText("");
                 movielistdropdown.setSelectedIndex(0);
                
        
    }
    
    
    private void MovieTable(){
        
        try {
            String sql="select movieid as 'MOVIE ID', movietype as 'MOVIE TYPE', moviename as 'MOVIE TITLE', number as 'NUMBER OF MOVIES' from savemovie";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            movietbl.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    
    
    private void TableMovieMouseClick() {
        try {
            
        int i=movietbl.getSelectedRow();
        TableModel model=movietbl.getModel();
        moid.setText(model.getValueAt(i, 0).toString());
         movielistdropdown.setSelectedItem(model.getValueAt(i, 1).toString());
        moname.setText(model.getValueAt(i, 2).toString());
        motypeno.setText(model.getValueAt(i, 3).toString());
            
        } catch (Exception e) {
           
        }  
    }
    
    
    
    
     //code for saving movie details to database
    private void SaveBorrowerData(){
         if(bjid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please input the ID Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (bjname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please input the Name Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(movielist.getSelectedItem().equals("Select Movie Type")){
             JOptionPane.showMessageDialog(null, "Please input the Movie Type Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)borroweddate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please input the Date of Borrow Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)duedate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please input the Due Date Input Field!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                
                String sql="insert into borrow(borrowerid,borrowername,movietype,monietitle,dateborrowed,duedate)values(?,?,?,?,?,?)";
                
                pst=conn.prepareStatement(sql);
                
                pst.setString(1, bjid.getText());
                pst.setString(2, bjname.getText());
                String val1=movielist.getSelectedItem().toString();
                pst.setString(3, val1);
                 String val2=movietitlelistdropdown.getSelectedItem().toString();
                pst.setString(4, val2);
                pst.setString(5, ((JTextField)borroweddate.getDateEditor().getUiComponent()).getText());
                pst.setString(6, ((JTextField)duedate.getDateEditor().getUiComponent()).getText());
                pst.execute();
                
                
                JOptionPane.showMessageDialog(null, "Saved!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        }IncludeTheTeturnDate();
        
                bjid.setText("");
                bjname.setText("");
                //movielist.setSelectedIndex(0);
               // movietitlelistdropdown.setSelectedItem("");
                ((JTextField)borroweddate.getDateEditor().getUiComponent()).setText("");
                ((JTextField)duedate.getDateEditor().getUiComponent()).setText("");
         
    }
    
    
    private void DeleteBorrowerData(){
         if(bjid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (bjname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(movielist.getSelectedItem().equals("Select Movie Type")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)borroweddate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)duedate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Deleted!","Error",JOptionPane.ERROR_MESSAGE);
        }
         

         else{
              int p=JOptionPane.showConfirmDialog(null, "Do you really want to permanently delete the details of  "+bjname.getText(),"Delete",JOptionPane.YES_NO_OPTION);
             if(p==0){
                 try {
                      String sql="delete from borrow where borrowerid=?";
                        pst=conn.prepareStatement(sql);

                        pst.setString(1, bjid.getText());

                        pst.execute();
                 
                 JOptionPane.showMessageDialog(null, "Deleted!");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
                
             }
         }DeleteReturnDatabase();
                bjid.setText("");
                bjname.setText("");
               // movielist.setSelectedIndex(0);
               // movietitlelistdropdown.setSelectedItem("");
                ((JTextField)borroweddate.getDateEditor().getUiComponent()).setText("");
                ((JTextField)duedate.getDateEditor().getUiComponent()).setText("");
                
        
    }
    
    
     private void DeleteReturnDatabase(){
         
                 try {
                      String sql="delete from returnmovie where borrowerid=?";
                        pst=conn.prepareStatement(sql);

                        pst.setString(1, bjid.getText());

                        pst.execute();
                 
                
                 } catch (Exception e) {
                     e.printStackTrace();
                 }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
                
     }
         
  
    
    
    
     private void UpdateBorrowerDetails(){
        
         if(bjid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (bjname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if(movielist.getSelectedItem().equals("Select Movie Type")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)borroweddate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (((JTextField)duedate.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
             try {
                 String value1=bjid.getText();
                 String value2=bjname.getText();
                  String value3=movielist.getSelectedItem().toString();
                  String value4=movietitlelistdropdown.getSelectedItem().toString();
                 String value5=((JTextField)borroweddate.getDateEditor().getUiComponent()).getText();
                  String value6=((JTextField)duedate.getDateEditor().getUiComponent()).getText();
                 
                 
                 String sql="update borrow set borrowerid='"+value1+"',borrowername='"+value2+"',movietype='"+value3+"',monietitle='"+value4+"',dateborrowed='"+value5+"',duedate='"+value6+"'where borrowerid='"+value1+"'";
                 pst=conn.prepareStatement(sql);
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Updated!");
             } catch (SQLException ex) {
                 System.out.println(ex);
             }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
         }   UpdateReturnDatabase();   
                 bjid.setText("");
                bjname.setText("");
                //movielist.setSelectedIndex(0);
                //movietitlelistdropdown.setSelectedItem("");
                ((JTextField)borroweddate.getDateEditor().getUiComponent()).setText("");
                ((JTextField)duedate.getDateEditor().getUiComponent()).setText("");
       
    }
     
     
     private void UpdateReturnDatabase(){
        
        
             try {
                 String value1=bjid.getText();
                 String value2=bjname.getText();
                  String value3=movielist.getSelectedItem().toString();
                  String value4=movietitlelistdropdown.getSelectedItem().toString();
                 String value5=((JTextField)borroweddate.getDateEditor().getUiComponent()).getText();
                 
                 
                 
                 String sql="update returnmovie set borrowerid='"+value1+"',borrowername='"+value2+"',movietype='"+value3+"',movietitle='"+value4+"',dateborrowed='"+value5+"'where borrowerid='"+value1+"'";
                 pst=conn.prepareStatement(sql);
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Updated!");
             } catch (SQLException ex) {
                 System.out.println(ex);
             }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    
    
    private void movietypecombo(){
        
      
       if(movielistdropdown.getSelectedItem()=="Action Movies"){
         moparentid.setText("001");
       }
        if(movielistdropdown.getSelectedItem()=="Adventure Movies"){
          moparentid.setText("002");
       }
         if(movielistdropdown.getSelectedItem()=="Animation Movies"){
          moparentid.setText("003");
       }
          if(movielistdropdown.getSelectedItem()=="Comedy Movies"){
          moparentid.setText("004");
       }
           if(movielistdropdown.getSelectedItem()=="Documentary Movies"){
           moparentid.setText("005");
       }
           if(movielistdropdown.getSelectedItem()=="Horror Movies"){
            moparentid.setText("006");
       }
           if(movielistdropdown.getSelectedItem()=="Musical Movies"){
             moparentid.setText("007");
       }
           if(movielistdropdown.getSelectedItem()=="Sci-Fi Movies"){
             moparentid.setText("008");
       }
           if(movielistdropdown.getSelectedItem()=="SuperHero Movies"){
             moparentid.setText("009");
       }
           if(movielistdropdown.getSelectedItem()=="War Movies"){
             moparentid.setText("010");
       }
           if(movielistdropdown.getSelectedItem()=="Select Movies"){
             moparentid.setText("");
       }
        
   
    }
    
    
    private void SelectMovieTypeToCombo(){
        try {
            conn=JavaConnect.ConnectDB();
            String sql="select * from savemovie";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add1=rs.getString("movietype");
                movielist.addItem(add1);
            }
        } catch (Exception e) {
        }
        
    }
    
    
    
     private void LoadMovieTitles(){
        try {
            conn=JavaConnect.ConnectDB();
            String sql="select moviename from savemovie where movietype='"+movielist.getSelectedItem().toString()+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add2=rs.getString("moviename");
                movietitlelistdropdown.addItem(add2);
            }
        } catch (Exception e) {
        }
        
    }
    
      private void LoadAllBorrowersNames(){
        try {
            conn=JavaConnect.ConnectDB();
            String sql="select * from borrow";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add1=rs.getString("borrowername");
                Borrowersdrop.addItem(add1);
            }
        } catch (Exception e) {
        }
        
    }
      
      
      //load to the comboboxi in the return panel
       private void LoadAllReturndetails(){
        try {
            conn=JavaConnect.ConnectDB();
            String sql="select * from returnmovie";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add1=rs.getString("borrowername");
                Returndrop.addItem(add1);
            }
        } catch (Exception e) {
        }
        
    }
      
      //for placing to input fields in the borrow panel
      private void PlaceAllInInputFields(){
        try {
            
            String sql="select * from borrow where borrowername='"+Borrowersdrop.getSelectedItem().toString()+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add1=rs.getString("borrowerid");
                bjid.setText(add1);
                 String add2=rs.getString("borrowername");
                bjname.setText(add2);
                String add5=rs.getString("dateborrowed");
                java.util.Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                borroweddate.setDate(date0);
                
                String add6=rs.getString("duedate");
                java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(add6);
                duedate.setDate(date1);
                String add3=rs.getString("movietype");
                movielist.addItem(add3);
               // String add4=rs.getString("monietitle");
               // movietitlelistdropdown.addItem(add4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
      
      
      //PLACE THE DATA TO INPUT FIELDS IN RETURN
      private void PlaceAllReturnInputFields(){
        try {
            
            String sql="select * from returnmovie where borrowername='"+Returndrop.getSelectedItem().toString()+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String add1=rs.getString("borrowerid");
                rjid.setText(add1);
                 String add2=rs.getString("borrowername");
                rjname.setText(add2);
                String add3=rs.getString("movietype");
                rjtype.setText(add3);
                 String add4=rs.getString("movietitle");
                rjtitle.setText(add4);
                String add5=rs.getString("dateborrowed");
                rjdate.setText(add5);
               String add6=rs.getString("returndate");
               if(add6==null){
                    
                   ((JTextField)returndate.getDateEditor().getUiComponent()).setText("");
 
               }else{
                java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(add6);
                returndate.setDate(date1);
               }
              
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
      
      //SHOW THE BORROWER TABLE
      private void ShowBorrTable(){
          
           borrowerdialogue = new JDialog();
           
           JPanel banana=new JPanel();
           banana.setMaximumSize(new Dimension(1000,600));
            String object1[][]={ {null,null,null,null,null,null},    
                                 {null,null,null,null,null,null},    
                                 {null,null,null,null,null,null}};    
         String column11[]={"BORROWER ID","BORROWER NAME","MOVIE TYPE","MOVIE TITLE","DATE BORROWED","DUE DATE"};  
        
        borrowertbl = new JTable(object1,column11);
        borrowertbl.setPreferredSize(new Dimension(980, 580));
        JScrollPane scrpan=new JScrollPane(borrowertbl); 
        scrpan.setPreferredSize(new Dimension(990, 590));
         LoadDataToBorTBL();//here we are callong the load table
         banana.add(scrpan);
        borrowerdialogue.add(banana);
           
           
          borrowerdialogue.setTitle("Borrower Table");
          borrowerdialogue.setPreferredSize(new Dimension(1000,600));
          borrowerdialogue.setAlwaysOnTop(true);
          borrowerdialogue.setLocationRelativeTo(null);
           borrowerdialogue.setVisible(true);
          borrowerdialogue.pack();
      }
      
      
       //SHOW THE RETURN TABLE
      private void ShowReturnerTable(){
          
           returnerdialogue = new JDialog();
           
           JPanel retret=new JPanel();
           retret.setMaximumSize(new Dimension(1000,600));
            String object1[][]={ {null,null,null,null,null,null},    
                                 {null,null,null,null,null,null},    
                                 {null,null,null,null,null,null}};    
         String column11[]={"BORROWER ID","BORROWER NAME","MOVIE TYPE","MOVIE TITLE","DATE BORROWED","RETURN DATE"};  
        
        returnertbl = new JTable(object1,column11);
        returnertbl.setPreferredSize(new Dimension(980, 580));
        JScrollPane scropan=new JScrollPane(returnertbl); 
        scropan.setPreferredSize(new Dimension(990, 590));
       LoadDataToRETETBL();//here we are callong the load table
         retret.add(scropan);
       returnerdialogue.add(retret);
           
           
          returnerdialogue.setTitle("Return Table");
          returnerdialogue.setPreferredSize(new Dimension(1000,600));
          returnerdialogue.setAlwaysOnTop(true);
           returnerdialogue.setVisible(true);
          returnerdialogue.pack();
      }
      
      
      
      //INSERT DATA TO THE BORROWER TABLE
      private void LoadDataToBorTBL(){
          try {
              String sql="select borrowerid as 'BORROWER ID', borrowername as 'BORROWER NAME', movietype as 'MOVIE TYPE', monietitle as 'MOVIE TITLE', dateborrowed as 'DATE BORROWED', duedate as 'DUE DATE' from borrow";
              pst=conn.prepareStatement(sql);
              rs=pst.executeQuery();
              borrowertbl.setModel(DbUtils.resultSetToTableModel(rs));
          } catch (Exception e) {
          }
      }
      
       //INSERT DATA TO THE RETURN TABLE
      private void LoadDataToRETETBL(){
          try {
              String sql="select borrowerid as 'BORROWER ID', borrowername as 'BORROWER NAME', movietype as 'MOVIE TYPE', movietitle as 'MOVIE TITLE', dateborrowed as 'DATE BORROWED', returndate as 'RETURN DATE' from returnmovie";
              pst=conn.prepareStatement(sql);
              rs=pst.executeQuery();
              returnertbl.setModel(DbUtils.resultSetToTableModel(rs));
          } catch (Exception e) {
          }
      }
      
      
      private void IncludeTheTeturnDate(){
          try {
                String sql="insert into returnmovie(borrowerid,borrowername,movietype,movietitle,dateborrowed)values(?,?,?,?,?)";
                
                pst=conn.prepareStatement(sql);
                
                pst.setString(1, bjid.getText());
                pst.setString(2, bjname.getText());
                String val1=movielist.getSelectedItem().toString();
                pst.setString(3, val1);
                 String val2=movietitlelistdropdown.getSelectedItem().toString();
                pst.setString(4, val2);
                pst.setString(5, ((JTextField)borroweddate.getDateEditor().getUiComponent()).getText());
                
                pst.execute();
                
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
      }
      
      //the return panel
      private void SetNotEditable(){
          rjid.setEditable(false);
          rjid.setBackground(Color.WHITE);
          rjname.setEditable(false);
          rjname.setBackground(Color.WHITE);
          rjtitle.setEditable(false);
          rjtitle.setBackground(Color.WHITE);
          rjtype.setEditable(false);
          rjtype.setBackground(Color.WHITE);
          rjdate.setEditable(false);
          rjdate.setBackground(Color.WHITE);
      }
      
      //when a movie is return it should be known and updated below.
      private void UpdateReturnDetails(){
        
         if(rjid.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (rjname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }else if (rjtitle.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if (rjtype.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if (rjdate.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please first select details to be Updated!","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
             try {
                String value1=rjid.getText();
                String value2=rjname.getText();
                String value3=rjtitle.getText();
                String value4=rjtype.getText();
                String value5=rjdate.getText();
                String value6=((JTextField)returndate.getDateEditor().getUiComponent()).getText();
                 String sql="update returnmovie set borrowerid='"+value1+"',borrowername='"+value2+"',movietype='"+value3+"',movietitle='"+value4+"',returndate='"+value6+"'where borrowerid='"+value1+"'";
                 pst=conn.prepareStatement(sql);
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Updated!");
             } catch (SQLException ex) {
                 System.out.println(ex);
             }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
         }
          rjid.setText("");
          rjname.setText("");
          rjtitle.setText("");
          rjtype.setText("");
          rjdate.setText("");
         ((JTextField)returndate.getDateEditor().getUiComponent()).setText("");
    }
     
      
      
    
    JTable membertbl, movietbl,borrowertbl,returnertbl;
     JDialog borrowerdialogue,returnerdialogue;
    
}
