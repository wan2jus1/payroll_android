package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.PlayerListDelta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstance {

   private String instanceId;
   private String leader;
   private Map messageTimes = new HashMap();
   private List players = new ArrayList(0);


   public GameInstance(String var1) {
      this.instanceId = var1;
      this.leader = "";
   }

   public String getInstanceId() {
      return this.instanceId;
   }

   public String getLeader() {
      return this.leader;
   }

   public String getMessageTime(String var1) {
      return this.messageTimes.containsKey(var1)?(String)this.messageTimes.get(var1):"";
   }

   public List getPlayers() {
      return this.players;
   }

   public void putMessageTime(String var1, String var2) {
      this.messageTimes.put(var1, var2);
   }

   public void setLeader(String var1) {
      this.leader = var1;
   }

   public PlayerListDelta setPlayers(List var1) {
      if(var1.equals(this.players)) {
         return PlayerListDelta.NO_CHANGE;
      } else {
         List var2 = this.players;
         ArrayList var3 = new ArrayList(var1);
         this.players = new ArrayList(var1);
         var3.removeAll(var2);
         var2.removeAll(var1);
         return var3.size() == 0 && var2.size() == 0?PlayerListDelta.NO_CHANGE:new PlayerListDelta(var2, var3);
      }
   }
}
