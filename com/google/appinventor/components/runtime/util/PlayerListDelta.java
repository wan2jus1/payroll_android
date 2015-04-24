package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.List;

public class PlayerListDelta {

   public static PlayerListDelta NO_CHANGE = new PlayerListDelta(new ArrayList(), new ArrayList());
   private List playersAdded;
   private List playersRemoved;


   public PlayerListDelta(List var1, List var2) {
      this.playersRemoved = var1;
      this.playersAdded = var2;
   }

   public List getPlayersAdded() {
      return this.playersAdded;
   }

   public List getPlayersRemoved() {
      return this.playersRemoved;
   }
}
