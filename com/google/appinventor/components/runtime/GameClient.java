package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.GameInstance;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.PlayerListDelta;
import com.google.appinventor.components.runtime.util.WebServiceUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

@DesignerComponent(
   category = ComponentCategory.INTERNAL,
   description = "Provides a way for applications to communicate with online game servers",
   iconName = "images/gameClient.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET, com.google.android.googleapps.permission.GOOGLE_AUTH"
)
public class GameClient extends AndroidNonvisibleComponent implements Component, OnResumeListener, OnStopListener {

   private static final String COMMAND_ARGUMENTS_KEY = "args";
   private static final String COMMAND_TYPE_KEY = "command";
   private static final String COUNT_KEY = "count";
   private static final String ERROR_RESPONSE_KEY = "e";
   private static final String GAME_ID_KEY = "gid";
   private static final String GET_INSTANCE_LISTS_COMMAND = "getinstancelists";
   private static final String GET_MESSAGES_COMMAND = "messages";
   private static final String INSTANCE_ID_KEY = "iid";
   private static final String INSTANCE_PUBLIC_KEY = "makepublic";
   private static final String INVITED_LIST_KEY = "invited";
   private static final String INVITEE_KEY = "inv";
   private static final String INVITE_COMMAND = "invite";
   private static final String JOINED_LIST_KEY = "joined";
   private static final String JOIN_INSTANCE_COMMAND = "joininstance";
   private static final String LEADER_KEY = "leader";
   private static final String LEAVE_INSTANCE_COMMAND = "leaveinstance";
   private static final String LOG_TAG = "GameClient";
   private static final String MESSAGES_LIST_KEY = "messages";
   private static final String MESSAGE_CONTENT_KEY = "contents";
   private static final String MESSAGE_RECIPIENTS_KEY = "mrec";
   private static final String MESSAGE_SENDER_KEY = "msender";
   private static final String MESSAGE_TIME_KEY = "mtime";
   private static final String NEW_INSTANCE_COMMAND = "newinstance";
   private static final String NEW_MESSAGE_COMMAND = "newmessage";
   private static final String PLAYERS_LIST_KEY = "players";
   private static final String PLAYER_ID_KEY = "pid";
   private static final String PUBLIC_LIST_KEY = "public";
   private static final String SERVER_COMMAND = "servercommand";
   private static final String SERVER_RETURN_VALUE_KEY = "response";
   private static final String SET_LEADER_COMMAND = "setleader";
   private static final String TYPE_KEY = "type";
   private Activity activityContext;
   private Handler androidUIHandler = new Handler();
   private String gameId;
   private GameInstance instance;
   private List invitedInstances;
   private List joinedInstances;
   private List publicInstances;
   private String serviceUrl;
   private String userEmailAddress = "";


   public GameClient(ComponentContainer var1) {
      super(var1.$form());
      this.activityContext = var1.$context();
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.gameId = "";
      this.instance = new GameInstance("");
      this.joinedInstances = Lists.newArrayList();
      this.invitedInstances = Lists.newArrayList();
      this.publicInstances = Lists.newArrayList();
      this.serviceUrl = "http://appinvgameserver.appspot.com";
   }

   // $FF: synthetic method
   static void access$1100(GameClient var0, JSONObject var1) throws JSONException {
      var0.updateInstanceInfo(var1);
   }

   // $FF: synthetic method
   static GameInstance access$300(GameClient var0) {
      return var0.instance;
   }

   private void postCommandToGameServer(String var1, List var2, AsyncCallbackPair var3) {
      this.postCommandToGameServer(var1, var2, var3, false);
   }

   private void postCommandToGameServer(final String var1, final List var2, final AsyncCallbackPair var3, final boolean var4) {
      var3 = new AsyncCallbackPair() {
         public void onFailure(String var1x) {
            Log.d("GameClient", "Posting to server failed for " + var1 + " with arguments " + var2 + "\n Failure message: " + var1x);
            var3.onFailure(var1x);
         }
         public void onSuccess(JSONObject param1) {
            // $FF: Couldn't be decompiled
         }
      };
      WebServiceUtil.getInstance().postCommandReturningObject(this.ServiceUrl(), var1, var2, var3);
   }

   private void postGetInstanceLists() {
      AsyncCallbackPair var1 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("GetInstanceLists", "Failed to get up to date instance lists.");
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.processInstanceLists(var1);
            GameClient.this.FunctionCompleted("GetInstanceLists");
         }
      };
      this.postCommandToGameServer("getinstancelists", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress())}), var1);
   }

   private void postGetMessages(final String var1, int var2) {
      AsyncCallbackPair var3 = new AsyncCallbackPair() {
         public void onFailure(String var1x) {
            GameClient.this.WebServiceError("GetMessages", var1x);
         }
         public void onSuccess(JSONObject param1) {
            // $FF: Couldn't be decompiled
         }
      };
      if(this.InstanceId().equals("")) {
         this.Info("You must join an instance before attempting to fetch messages.");
      } else {
         this.postCommandToGameServer("messages", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("count", Integer.toString(var2)), new BasicNameValuePair("mtime", this.instance.getMessageTime(var1)), new BasicNameValuePair("type", var1)}), var3);
      }
   }

   private void postInvite(String var1) {
      AsyncCallbackPair var2 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("Invite", var1);
         }
         public void onSuccess(JSONObject var1) {
            try {
               String var3 = var1.getString("inv");
               if(var3.equals("")) {
                  GameClient.this.Info(var3 + " was already invited.");
               } else {
                  GameClient.this.Info("Successfully invited " + var3 + ".");
               }
            } catch (JSONException var2) {
               Log.w("GameClient", var2);
               GameClient.this.Info("Failed to parse invite player response.");
            }

            GameClient.this.FunctionCompleted("Invite");
         }
      };
      if(this.InstanceId().equals("")) {
         this.Info("You must have joined an instance before you can invite new players.");
      } else {
         this.postCommandToGameServer("invite", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("inv", var1)}), var2);
      }
   }

   private void postLeaveInstance() {
      AsyncCallbackPair var1 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("LeaveInstance", var1);
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.SetInstance("");
            GameClient.this.processInstanceLists(var1);
            GameClient.this.FunctionCompleted("LeaveInstance");
         }
      };
      this.postCommandToGameServer("leaveinstance", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress())}), var1);
   }

   private void postMakeNewInstance(String var1, Boolean var2) {
      AsyncCallbackPair var3 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("MakeNewInstance", var1);
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.processInstanceLists(var1);
            GameClient.this.NewInstanceMade(GameClient.this.InstanceId());
            GameClient.this.FunctionCompleted("MakeNewInstance");
         }
      };
      this.postCommandToGameServer("newinstance", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", var1), new BasicNameValuePair("makepublic", var2.toString())}), var3, true);
   }

   private void postNewMessage(String var1, YailList var2, YailList var3) {
      AsyncCallbackPair var4 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("SendMessage", var1);
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.FunctionCompleted("SendMessage");
         }
      };
      if(this.InstanceId().equals("")) {
         this.Info("You must have joined an instance before you can send messages.");
      } else {
         this.postCommandToGameServer("newmessage", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("type", var1), new BasicNameValuePair("mrec", var2.toJSONString()), new BasicNameValuePair("contents", var3.toJSONString()), new BasicNameValuePair("mtime", this.instance.getMessageTime(var1))}), var4);
      }
   }

   private void postServerCommand(final String var1, final YailList var2) {
      AsyncCallbackPair var3 = new AsyncCallbackPair() {
         public void onFailure(String var1x) {
            GameClient.this.ServerCommandFailure(var1, var2);
            GameClient.this.WebServiceError("ServerCommand", var1x);
         }
         public void onSuccess(JSONObject var1x) {
            try {
               GameClient.this.ServerCommandSuccess(var1, JsonUtil.getListFromJsonArray(var1x.getJSONArray("contents")));
            } catch (JSONException var2x) {
               Log.w("GameClient", var2x);
               GameClient.this.Info("Server command response failed to parse.");
            }

            GameClient.this.FunctionCompleted("ServerCommand");
         }
      };
      Log.d("GameClient", "Going to post " + var1 + " with args " + var2);
      this.postCommandToGameServer("servercommand", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("command", var1), new BasicNameValuePair("args", var2.toJSONString())}), var3);
   }

   private void postSetInstance(String var1) {
      AsyncCallbackPair var2 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("SetInstance", var1);
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.processInstanceLists(var1);
            GameClient.this.FunctionCompleted("SetInstance");
         }
      };
      this.postCommandToGameServer("joininstance", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", var1), new BasicNameValuePair("pid", this.UserEmailAddress())}), var2, true);
   }

   private void postSetLeader(String var1) {
      AsyncCallbackPair var2 = new AsyncCallbackPair() {
         public void onFailure(String var1) {
            GameClient.this.WebServiceError("SetLeader", var1);
         }
         public void onSuccess(JSONObject var1) {
            GameClient.this.FunctionCompleted("SetLeader");
         }
      };
      if(this.InstanceId().equals("")) {
         this.Info("You must join an instance before attempting to set a leader.");
      } else {
         this.postCommandToGameServer("setleader", Lists.newArrayList(new NameValuePair[]{new BasicNameValuePair("gid", this.GameId()), new BasicNameValuePair("iid", this.InstanceId()), new BasicNameValuePair("pid", this.UserEmailAddress()), new BasicNameValuePair("leader", var1)}), var2);
      }
   }

   private void processInstanceLists(JSONObject param1) {
      // $FF: Couldn't be decompiled
   }

   private void updateInstanceInfo(JSONObject var1) throws JSONException {
      boolean var3 = false;
      String var2 = var1.getString("leader");
      List var4 = JsonUtil.getStringListFromJsonArray(var1.getJSONArray("players"));
      if(!this.Leader().equals(var2)) {
         this.instance.setLeader(var2);
         var3 = true;
      }

      PlayerListDelta var5 = this.instance.setPlayers(var4);
      if(var5 != PlayerListDelta.NO_CHANGE) {
         Iterator var7 = var5.getPlayersRemoved().iterator();

         while(var7.hasNext()) {
            this.PlayerLeft((String)var7.next());
         }

         Iterator var6 = var5.getPlayersAdded().iterator();

         while(var6.hasNext()) {
            this.PlayerJoined((String)var6.next());
         }
      }

      if(var3) {
         this.NewLeader(this.Leader());
      }

   }

   @SimpleEvent(
      description = "Indicates that a function call completed."
   )
   public void FunctionCompleted(final String var1) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            Log.d("GameClient", "Request completed: " + var1);
            EventDispatcher.dispatchEvent(GameClient.this, "FunctionCompleted", new Object[]{var1});
         }
      });
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The game name for this application. The same game ID can have one or more game instances."
   )
   public String GameId() {
      return this.gameId;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   public void GameId(String var1) {
      this.gameId = var1;
   }

   @SimpleFunction(
      description = "Updates the InstancesJoined and InstancesInvited lists. This procedure can be called before setting the InstanceId."
   )
   public void GetInstanceLists() {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postGetInstanceLists();
         }
      });
   }

   @SimpleFunction(
      description = "Retrieves messages of the specified type."
   )
   public void GetMessages(final String var1, final int var2) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postGetMessages(var1, var2);
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a new message has been received."
   )
   public void GotMessage(final String var1, final String var2, final List var3) {
      Log.d("GameClient", "Got message of type " + var1);
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "GotMessage", new Object[]{var1, var2, var3});
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that something has occurred which the player should know about."
   )
   public void Info(final String var1) {
      Log.d("GameClient", "Info: " + var1);
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "Info", new Object[]{var1});
         }
      });
   }

   public void Initialize() {
      Log.d("GameClient", "Initialize");
      if(this.gameId.equals("")) {
         throw new YailRuntimeError("Game Id must not be empty.", "GameClient Configuration Error.");
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The game instance id.  Taken together,the game ID and the instance ID uniquely identify the game."
   )
   public String InstanceId() {
      return this.instance.getInstanceId();
   }

   @SimpleEvent(
      description = "Indicates that the InstanceId property has changed as a result of calling MakeNewInstance or SetInstance."
   )
   public void InstanceIdChanged(final String var1) {
      Log.d("GameClient", "Instance id changed to " + var1);
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "InstanceIdChanged", new Object[]{var1});
         }
      });
   }

   @SimpleFunction(
      description = "Invites a player to this game instance."
   )
   public void Invite(final String var1) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postInvite(var1);
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a user has been invited to this game instance."
   )
   public void Invited(final String var1) {
      Log.d("GameClient", "Player invited to " + var1);
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "Invited", new Object[]{var1});
         }
      });
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The set of game instances to which this player has been invited but has not yet joined.  To ensure current values are returned, first invoke GetInstanceLists."
   )
   public List InvitedInstances() {
      return this.invitedInstances;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The set of game instances in which this player is participating.  To ensure current values are returned, first invoke GetInstanceLists."
   )
   public List JoinedInstances() {
      return this.joinedInstances;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The game\'s leader. At any time, each game instance has only one leader, but the leader may change with time.  Initially, the leader is the game instance creator. Application writers determine special properties of the leader. The leader value is updated each time a successful communication is made with the server."
   )
   public String Leader() {
      return this.instance.getLeader();
   }

   @SimpleFunction(
      description = "Leaves the current instance."
   )
   public void LeaveInstance() {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postLeaveInstance();
         }
      });
   }

   @SimpleFunction(
      description = "Asks the server to create a new instance of this game."
   )
   public void MakeNewInstance(final String var1, final boolean var2) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postMakeNewInstance(var1, Boolean.valueOf(var2));
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a new instance was successfully created after calling MakeNewInstance."
   )
   public void NewInstanceMade(final String var1) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            Log.d("GameClient", "New instance made: " + var1);
            EventDispatcher.dispatchEvent(GameClient.this, "NewInstanceMade", new Object[]{var1});
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that this game has a new leader as specified through SetLeader"
   )
   public void NewLeader(final String var1) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            Log.d("GameClient", "Leader change to " + var1);
            EventDispatcher.dispatchEvent(GameClient.this, "NewLeader", new Object[]{var1});
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a new player has joined this game instance."
   )
   public void PlayerJoined(final String var1) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            if(!var1.equals(GameClient.this.UserEmailAddress())) {
               Log.d("GameClient", "Player joined: " + var1);
               EventDispatcher.dispatchEvent(GameClient.this, "PlayerJoined", new Object[]{var1});
            }

         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a player has left this game instance."
   )
   public void PlayerLeft(final String var1) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            Log.d("GameClient", "Player left: " + var1);
            EventDispatcher.dispatchEvent(GameClient.this, "PlayerLeft", new Object[]{var1});
         }
      });
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The current set of players for this game instance. Each player is designated by an email address, which is a string. The list of players is updated each time a successful communication is made with the game server."
   )
   public List Players() {
      return this.instance.getPlayers();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The set of game instances that have been marked public. To ensure current values are returned, first invoke {@link #GetInstanceLists}. "
   )
   public List PublicInstances() {
      return this.publicInstances;
   }

   @SimpleFunction(
      description = "Sends a keyed message to all recipients in the recipients list. The message will consist of the contents list."
   )
   public void SendMessage(final String var1, final YailList var2, final YailList var3) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postNewMessage(var1, var2, var3);
         }
      });
   }

   @SimpleFunction(
      description = "Sends the specified command to the game server."
   )
   public void ServerCommand(final String var1, final YailList var2) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postServerCommand(var1, var2);
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a server command failed."
   )
   public void ServerCommandFailure(final String var1, final YailList var2) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            Log.d("GameClient", "Server command failed: " + var1);
            EventDispatcher.dispatchEvent(GameClient.this, "ServerCommandFailure", new Object[]{var1, var2});
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that a server command returned successfully."
   )
   public void ServerCommandSuccess(final String var1, final List var2) {
      Log.d("GameClient", var1 + " server command returned.");
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "ServerCommandSuccess", new Object[]{var1, var2});
         }
      });
   }

   @DesignerProperty(
      defaultValue = "http://appinvgameserver.appspot.com",
      editorType = "string"
   )
   public void ServiceURL(String var1) {
      if(var1.endsWith("/")) {
         this.serviceUrl = var1.substring(0, var1.length() - 1);
      } else {
         this.serviceUrl = var1;
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The URL of the game server."
   )
   public String ServiceUrl() {
      return this.serviceUrl;
   }

   @SimpleFunction(
      description = "Sets InstanceId and joins the specified instance."
   )
   public void SetInstance(final String var1) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            if(var1.equals("")) {
               Log.d("GameClient", "Instance id set to empty string.");
               if(!GameClient.this.InstanceId().equals("")) {
                  GameClient.this.instance = new GameInstance("");
                  GameClient.this.InstanceIdChanged("");
                  GameClient.this.FunctionCompleted("SetInstance");
               }

            } else {
               GameClient.this.postSetInstance(var1);
            }
         }
      });
   }

   @SimpleFunction(
      description = "Tells the server to set the leader to playerId. Only the current leader may successfully set a new leader."
   )
   public void SetLeader(final String var1) {
      AsynchUtil.runAsynchronously(new Runnable() {
         public void run() {
            GameClient.this.postSetLeader(var1);
         }
      });
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The email address that is being used as the player id for this game client.   At present, users must set this manually in oder to join a game.  But this property will change in the future so that is set automatically, and users will not be able to change it."
   )
   public String UserEmailAddress() {
      if(this.userEmailAddress.equals("")) {
         this.Info("User email address is empty.");
      }

      return this.userEmailAddress;
   }

   @SimpleProperty
   public void UserEmailAddress(String var1) {
      this.userEmailAddress = var1;
      this.UserEmailAddressSet(var1);
   }

   @SimpleEvent(
      description = "Indicates that the user email address has been set."
   )
   public void UserEmailAddressSet(final String var1) {
      Log.d("GameClient", "Email address set.");
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "UserEmailAddressSet", new Object[]{var1});
         }
      });
   }

   @SimpleEvent(
      description = "Indicates that an error occurred while communicating with the web server."
   )
   public void WebServiceError(final String var1, final String var2) {
      Log.e("GameClient", "WebServiceError: " + var2);
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(GameClient.this, "WebServiceError", new Object[]{var1, var2});
         }
      });
   }

   public void onResume() {
      Log.d("GameClient", "Activity Resumed.");
   }

   public void onStop() {
      Log.d("GameClient", "Activity Stopped.");
   }
}
