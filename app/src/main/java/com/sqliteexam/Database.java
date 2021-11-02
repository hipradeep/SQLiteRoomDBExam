package com.sqliteexam;

import java.util.ArrayList;
import java.util.List;

public class Database {
   private ArrayList<String> latestDp ;
   private ArrayList<String> loveRomantic ;
   private ArrayList<String> life ;
   private ArrayList<String> sad ;


   public Database(){

       latestDp=new ArrayList<>();
       latestDp.add("");
       latestDp.add("");
       latestDp.add("");
       latestDp.add("");
       latestDp.add("");
       latestDp.add("");
       latestDp.add("");

   }
   public ArrayList<String> getLatestDp(){
       return latestDp;
   }
}
