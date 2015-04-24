package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.GoogleKeyInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.Fusiontables.Query.Sql;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.ClientLoginHelper;
import com.google.appinventor.components.runtime.util.IClientLoginHelper;
import com.google.appinventor.components.runtime.util.OAuth2Helper;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

@DesignerComponent(
   category = ComponentCategory.STORAGE,
   description = "<p>A non-visible component that communicates with Google Fusion Tables. Fusion Tables let you store, share, query and visualize data tables; this component lets you query, create, and modify these tables.</p> <p>This component uses the <a href=\"https://developers.google.com/fusiontables/docs/v1/getting_started\" target=\"_blank\">Fusion Tables API V1.0</a>. <p>Applications using Fusion Tables must authentication to Google\'s servers. There are two ways this can be done. The first way uses an API Key which you the developer obtain (see below). With this approach end-users must also login to access a Fusion Table. The second approach is to use a Service Account. With this approach you create credentials and a special \"Service Account Email Address\" which you obtain from the <a href=\"https://code.google.com/apis/console/\" target=\"_blank\">Google APIs Console</a>. You then tell the Fusion Table Control the name of the Service Account Email address and upload the secret key as an asset to your application and set the KeyFile property to point at this file. Finally you check the \"UseServiceAuthentication\" checkbox in the designer. When using a Service Account, end-users do not need to login to use Fusion Tables, your service account authenticates all access.</p> <p>To get an API key, follow these instructions.</p> <ol><li>Go to your <a href=\"https://code.google.com/apis/console/\" target=\"_blank\">Google APIs Console</a> and login if necessary.</li><li>Select the <i>Services</i> item from the menu on the left.</li><li>Choose the <i>Fusiontables</i> service from the list provided and turn it on.</li><li>Go back to the main menu and select the <i>API Access</i> item. </li></ol><p>Your API Key will be near the bottom of that pane in the section called \"Simple API Access\".You will have to provide that key as the value for the <i>ApiKey</i> property in your Fusiontables app.</p><p>Once you have an API key, set the value of the <i>Query</i> property to a valid Fusiontables SQL query and call <i>SendQuery</i> to execute the query.  App Inventor will send the query to the Fusion Tables server and the <i>GotResult</i> block will fire when a result is returned from the server.Query results will be returned in CSV format, and can be converted to list format using the \"list from csv table\" or \"list from csv row\" blocks.</p><p>Note that you do not need to worry about UTF-encoding the query. But you do need to make sure the query follows the syntax described in <a href=\"https://developers.google.com/fusiontables/docs/v1/getting_started\" target=\"_blank\">the reference manual</a>, which means that things like capitalization for names of columns matters, and that single quotes must be used around column names if there are spaces in them.</p>",
   iconName = "images/fusiontables.png",
   nonVisible = true,
   version = 3
)
@SimpleObject
@UsesLibraries(
   libraries = "fusiontables.jar,google-api-client-beta.jar,google-api-client-android2-beta.jar,google-http-client-beta.jar,google-http-client-android2-beta.jar,google-http-client-android3-beta.jar,google-oauth-client-beta.jar,guava-14.0.1.jar,gson-2.1.jar"
)
@UsesPermissions(
   permissionNames = "android.permission.INTERNET,android.permission.ACCOUNT_MANAGER,android.permission.MANAGE_ACCOUNTS,android.permission.GET_ACCOUNTS,android.permission.USE_CREDENTIALS"
)
public class FusiontablesControl extends AndroidNonvisibleComponent implements Component {

   public static final String APP_NAME = "App Inventor";
   public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
   public static final String AUTH_TOKEN_TYPE_FUSIONTABLES = "oauth2:https://www.googleapis.com/auth/fusiontables";
   private static final String DEFAULT_QUERY = "show tables";
   private static final String DIALOG_TEXT = "Choose an account to access FusionTables";
   public static final String FUSIONTABLES_POST = "https://www.googleapis.com/fusiontables/v1/tables";
   public static final String FUSIONTABLES_URL = "https://www.googleapis.com/fusiontables/v1/query";
   private static final String FUSIONTABLE_SERVICE = "fusiontables";
   private static final String FUSION_QUERY_URL = "http://www.google.com/fusiontables/api/query";
   private static final String LOG_TAG = "FUSION";
   private static final int SERVER_TIMEOUT_MS = 30000;
   private final Activity activity;
   private String apiKey;
   private String authTokenType = "oauth2:https://www.googleapis.com/auth/fusiontables";
   private java.io.File cachedServiceCredentials = null;
   private final ComponentContainer container;
   private String errorMessage;
   private boolean isServiceAuth = false;
   private String keyPath = "";
   private String query;
   private String queryResultStr;
   private final IClientLoginHelper requestHelper;
   private String scope = "https://www.googleapis.com/auth/fusiontables";
   private String serviceAccountEmail = "";
   private String standardErrorMessage = "Error on Fusion Tables query";


   public FusiontablesControl(ComponentContainer var1) {
      super(var1.$form());
      this.container = var1;
      this.activity = var1.$context();
      this.requestHelper = this.createClientLoginHelper("Choose an account to access FusionTables", "fusiontables");
      this.query = "show tables";
      if(SdkLevel.getLevel() < 5) {
         this.showNoticeAndDie("Sorry. The Fusiontables component is not compatible with this phone.", "This application must exit.", "Rats!");
      }

   }

   // $FF: synthetic method
   static String access$1000(FusiontablesControl var0) {
      return var0.standardErrorMessage;
   }

   // $FF: synthetic method
   static String access$1100(FusiontablesControl var0) {
      return var0.keyPath;
   }

   // $FF: synthetic method
   static java.io.File access$1200(FusiontablesControl var0) {
      return var0.cachedServiceCredentials;
   }

   // $FF: synthetic method
   static java.io.File access$1202(FusiontablesControl var0, java.io.File var1) {
      var0.cachedServiceCredentials = var1;
      return var1;
   }

   // $FF: synthetic method
   static ComponentContainer access$1300(FusiontablesControl var0) {
      return var0.container;
   }

   // $FF: synthetic method
   static String access$1400(FusiontablesControl var0) {
      return var0.scope;
   }

   // $FF: synthetic method
   static String access$1500(FusiontablesControl var0) {
      return var0.serviceAccountEmail;
   }

   // $FF: synthetic method
   static String access$902(FusiontablesControl var0, String var1) {
      var0.errorMessage = var1;
      return var1;
   }

   private IClientLoginHelper createClientLoginHelper(String var1, String var2) {
      if(SdkLevel.getLevel() >= 5) {
         DefaultHttpClient var3 = new DefaultHttpClient();
         HttpConnectionParams.setSoTimeout(var3.getParams(), 30000);
         HttpConnectionParams.setConnectionTimeout(var3.getParams(), 30000);
         return new ClientLoginHelper(this.activity, var2, var1, var3);
      } else {
         return null;
      }
   }

   private String doPostRequest(String var1, String var2) {
      String var4 = var1.trim().substring("create table".length());
      Log.i("FUSION", "Http Post content = " + var4);
      HttpPost var3 = new HttpPost("https://www.googleapis.com/fusiontables/v1/tables?key=" + this.ApiKey());

      StringEntity var14;
      try {
         var14 = new StringEntity(var4);
      } catch (UnsupportedEncodingException var10) {
         var10.printStackTrace();
         return "Error: " + var10.getMessage();
      }

      var14.setContentType("application/json");
      var3.addHeader("Authorization", "Bearer " + var2);
      var3.setEntity(var14);
      DefaultHttpClient var11 = new DefaultHttpClient();

      HttpResponse var12;
      try {
         var12 = var11.execute(var3);
      } catch (ClientProtocolException var8) {
         var8.printStackTrace();
         return "Error: " + var8.getMessage();
      } catch (IOException var9) {
         var9.printStackTrace();
         return "Error: " + var9.getMessage();
      }

      int var5 = var12.getStatusLine().getStatusCode();
      if(var12 != null && var5 == 200) {
         try {
            String var13 = httpApacheResponseToString(var12);
            JSONObject var15 = new JSONObject(var13);
            if(var15.has("tableId")) {
               this.queryResultStr = "tableId," + var15.get("tableId");
            } else {
               this.queryResultStr = var13;
            }
         } catch (IllegalStateException var6) {
            var6.printStackTrace();
            return "Error: " + var6.getMessage();
         } catch (JSONException var7) {
            var7.printStackTrace();
            return "Error: " + var7.getMessage();
         }

         Log.i("FUSION", "Response code = " + var12.getStatusLine());
         Log.i("FUSION", "Query = " + var1 + "\nResultStr = " + this.queryResultStr);
      } else {
         Log.i("FUSION", "Error: " + var12.getStatusLine().toString());
         this.queryResultStr = var12.getStatusLine().toString();
      }

      return this.queryResultStr;
   }

   private HttpUriRequest genFusiontablesQuery(String var1) throws IOException {
      HttpPost var2 = new HttpPost("http://www.google.com/fusiontables/api/query");
      ArrayList var3 = new ArrayList(1);
      var3.add(new BasicNameValuePair("sql", var1));
      UrlEncodedFormEntity var4 = new UrlEncodedFormEntity(var3, "UTF-8");
      var4.setContentType("application/x-www-form-urlencoded");
      var2.setEntity(var4);
      return var2;
   }

   public static String httpApacheResponseToString(HttpResponse var0) {
      String var1 = "";
      if(var0 != null) {
         if(var0.getStatusLine().getStatusCode() == 200) {
            try {
               String var3 = parseResponse(var0.getEntity().getContent());
               return var3;
            } catch (IOException var2) {
               var2.printStackTrace();
               return "";
            }
         }

         var1 = var0.getStatusLine().getStatusCode() + " " + var0.getStatusLine().getReasonPhrase();
      }

      return var1;
   }

   public static String httpResponseToString(com.google.api.client.http.HttpResponse var0) {
      String var1 = "";
      if(var0 != null) {
         if(var0.getStatusCode() == 200) {
            try {
               String var3 = parseResponse(var0.getContent());
               return var3;
            } catch (IOException var2) {
               var2.printStackTrace();
               return "";
            }
         }

         var1 = var0.getStatusCode() + " " + var0.getStatusMessage();
      }

      return var1;
   }

   public static String parseResponse(InputStream param0) {
      // $FF: Couldn't be decompiled
   }

   private String parseSqlCreateQueryToJson(String var1) {
      Log.i("FUSION", "parsetoJSonSqlCreate :" + var1);
      StringBuilder var2 = new StringBuilder();
      String var3 = var1.trim();
      var1 = var3.substring("create table".length(), var3.indexOf(40)).trim();
      String[] var6 = var3.substring(var3.indexOf(40) + 1, var3.indexOf(41)).split(",");
      var2.append("{\'columns\':[");

      for(int var5 = 0; var5 < var6.length; ++var5) {
         String[] var4 = var6[var5].split(":");
         var2.append("{\'name\': \'" + var4[0].trim() + "\', \'type\': \'" + var4[1].trim() + "\'}");
         if(var5 < var6.length - 1) {
            var2.append(",");
         }
      }

      var2.append("],");
      var2.append("\'isExportable\':\'true\',");
      var2.append("\'name\': \'" + var1 + "\'");
      var2.append("}");
      var2.insert(0, "CREATE TABLE ");
      Log.i("FUSION", "result = " + var2.toString());
      return var2.toString();
   }

   private void showNoticeAndDie(String var1, String var2, String var3) {
      AlertDialog var4 = (new Builder(this.activity)).create();
      var4.setTitle(var2);
      var4.setCancelable(false);
      var4.setMessage(var1);
      var4.setButton(var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            FusiontablesControl.this.activity.finish();
         }
      });
      var4.show();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Your Google API Key. For help, click on the questionmark (?) next to the FusiontablesControl component in the Palette. "
   )
   public String ApiKey() {
      return this.apiKey;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ApiKey(String var1) {
      this.apiKey = var1;
   }

   @SimpleFunction(
      description = "DEPRECATED. This block is deprecated as of the end of 2012.  Use SendQuery.",
      userVisible = false
   )
   public void DoQuery() {
      if(this.requestHelper != null) {
         (new FusiontablesControl.QueryProcessor(null)).execute(new String[]{this.query});
      } else {
         this.form.dispatchErrorOccurredEvent(this, "DoQuery", 3, new Object[0]);
      }
   }

   @SimpleFunction(
      description = "Forget end-users login credentials. Has no effect on service authentication"
   )
   public void ForgetLogin() {
      OAuth2Helper.resetAccountCredential(this.activity);
   }

   @SimpleFunction(
      description = "Gets all the rows from a specified fusion table. The tableId field is the id of therequired fusion table. The columns field is a comma-separeted list of the columns to retrieve."
   )
   public void GetRows(String var1, String var2) {
      this.query = "SELECT " + var2 + " FROM " + var1;
      (new FusiontablesControl.QueryProcessorV1(this.activity)).execute(new String[]{this.query});
   }

   @SimpleFunction(
      description = "Gets all the rows from a fusion table that meet certain conditions. The tableId field isthe id of the required fusion table. The columns field is a comma-separeted list of the columns toretrieve. The conditions field specifies what rows to retrieve from the table, for example the rows in whicha particular column value is not null."
   )
   public void GetRowsWithConditions(String var1, String var2, String var3) {
      this.query = "SELECT " + var2 + " FROM " + var1 + " WHERE " + var3;
      (new FusiontablesControl.QueryProcessorV1(this.activity)).execute(new String[]{this.query});
   }

   @SimpleEvent(
      description = "Indicates that the Fusion Tables query has finished processing, with a result.  The result of the query will generally be returned in CSV format, and can be converted to list format using the \"list from csv table\" or \"list from csv row\" blocks."
   )
   public void GotResult(String var1) {
      EventDispatcher.dispatchEvent(this, "GotResult", new Object[]{var1});
   }

   @SimpleFunction(
      description = "Inserts a row into the specified fusion table. The tableId field is the id of thefusion table. The columns is a comma-separated list of the columns to insert values into. The values field specifies what values to insert into each column."
   )
   public void InsertRow(String var1, String var2, String var3) {
      this.query = "INSERT INTO " + var1 + " (" + var2 + ")" + " VALUES " + "(" + var3 + ")";
      (new FusiontablesControl.QueryProcessorV1(this.activity)).execute(new String[]{this.query});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Specifies the path of the private key file.  This key file is used to get access to the FusionTables API."
   )
   public String KeyFile() {
      return this.keyPath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty
   public void KeyFile(String var1) {
      if(!var1.equals(this.keyPath)) {
         if(this.cachedServiceCredentials != null) {
            this.cachedServiceCredentials.delete();
            this.cachedServiceCredentials = null;
         }

         String var2 = var1;
         if(var1 == null) {
            var2 = "";
         }

         this.keyPath = var2;
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The query to send to the Fusion Tables API. <p>For legal query formats and examples, see the <a href=\"https://developers.google.com/fusiontables/docs/v1/getting_started\" target=\"_blank\">Fusion Tables API v1.0 reference manual</a>.</p> <p>Note that you do not need to worry about UTF-encoding the query. But you do need to make sure it follows the syntax described in the reference manual, which means that things like capitalization for names of columns matters, and that single quotes need to be used around column names if there are spaces in them.</p> "
   )
   public String Query() {
      return this.query;
   }

   @DesignerProperty(
      defaultValue = "show tables",
      editorType = "string"
   )
   @SimpleProperty
   public void Query(String var1) {
      this.query = var1;
   }

   @SimpleFunction(
      description = "Send the query to the Fusiontables server."
   )
   public void SendQuery() {
      (new FusiontablesControl.QueryProcessorV1(this.activity)).execute(new String[]{this.query});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The Service Account Email Address when service account authentication is in use."
   )
   public String ServiceAccountEmail() {
      return this.serviceAccountEmail;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ServiceAccountEmail(String var1) {
      this.serviceAccountEmail = var1;
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void UseServiceAuthentication(boolean var1) {
      this.isServiceAuth = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Indicates whether a service account should be used for authentication"
   )
   public boolean UseServiceAuthentication() {
      return this.isServiceAuth;
   }

   public void handleOAuthError(String var1) {
      Log.i("FUSION", "handleOAuthError: " + var1);
      this.errorMessage = var1;
   }

   public com.google.api.client.http.HttpResponse sendQuery(String var1, String var2) {
      this.errorMessage = this.standardErrorMessage;
      Log.i("FUSION", "executing " + var1);
      Fusiontables var3 = (new com.google.api.services.fusiontables.Fusiontables.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), new GoogleCredential())).setApplicationName("App Inventor Fusiontables/v1.0").setJsonHttpRequestInitializer(new GoogleKeyInitializer(this.ApiKey())).build();

      try {
         Sql var6 = var3.query().sql(var1);
         var6.put("alt", "csv");
         var6.setOauthToken(var2);
         com.google.api.client.http.HttpResponse var7 = var6.executeUnparsed();
         return var7;
      } catch (GoogleJsonResponseException var4) {
         var4.printStackTrace();
         this.errorMessage = var4.getMessage();
         Log.e("FUSION", "JsonResponseException");
         Log.e("FUSION", "e.getMessage() is " + var4.getMessage());
         Log.e("FUSION", "response is " + null);
         return null;
      } catch (IOException var5) {
         var5.printStackTrace();
         this.errorMessage = var5.getMessage();
         Log.e("FUSION", "IOException");
         Log.e("FUSION", "e.getMessage() is " + var5.getMessage());
         Log.e("FUSION", "response is " + null);
         return null;
      }
   }

   void signalJsonResponseError(String var1, String var2) {
      this.form.dispatchErrorOccurredEventDialog(this, "SendQuery", 2601, new Object[]{var1, var2});
   }

   private class QueryProcessor extends AsyncTask {

      private ProgressDialog progress;


      private QueryProcessor() {
         this.progress = null;
      }

      // $FF: synthetic method
      QueryProcessor(Object var2) {
         this();
      }

      protected String doInBackground(String ... var1) {
         try {
            HttpUriRequest var2 = FusiontablesControl.this.genFusiontablesQuery(var1[0]);
            Log.d("FUSION", "Fetching: " + var1[0]);
            HttpResponse var4 = FusiontablesControl.this.requestHelper.execute(var2);
            ByteArrayOutputStream var6 = new ByteArrayOutputStream();
            var4.getEntity().writeTo(var6);
            Log.d("FUSION", "Response: " + var4.getStatusLine().toString());
            String var5 = var6.toString();
            return var5;
         } catch (IOException var3) {
            var3.printStackTrace();
            return var3.getMessage();
         }
      }

      protected void onPostExecute(String var1) {
         this.progress.dismiss();
         FusiontablesControl.this.GotResult(var1);
      }

      protected void onPreExecute() {
         this.progress = ProgressDialog.show(FusiontablesControl.this.activity, "Fusiontables", "processing query...", true);
      }
   }

   private class QueryProcessorV1 extends AsyncTask {

      private static final String STAG = "FUSION_SERVICE_ACCOUNT";
      private static final String TAG = "QueryProcessorV1";
      private final Activity activity;
      private final ProgressDialog dialog;


      QueryProcessorV1(Activity var2) {
         Log.i("QueryProcessorV1", "Creating AsyncFusiontablesQuery");
         this.activity = var2;
         this.dialog = new ProgressDialog(var2);
      }

      private String serviceAuthRequest(String param1) {
         // $FF: Couldn't be decompiled
      }

      private String userAuthRequest(String var1) {
         FusiontablesControl.this.queryResultStr = "";
         String var2 = (new OAuth2Helper()).getRefreshedAuthToken(this.activity, FusiontablesControl.this.authTokenType);
         if(var2 != null) {
            if(var1.toLowerCase().contains("create table")) {
               FusiontablesControl.this.queryResultStr = FusiontablesControl.this.doPostRequest(FusiontablesControl.this.parseSqlCreateQueryToJson(var1), var2);
               return FusiontablesControl.this.queryResultStr;
            } else {
               com.google.api.client.http.HttpResponse var3 = FusiontablesControl.this.sendQuery(var1, var2);
               if(var3 != null) {
                  FusiontablesControl.this.queryResultStr = FusiontablesControl.httpResponseToString(var3);
                  Log.i("QueryProcessorV1", "Query = " + var1 + "\nResultStr = " + FusiontablesControl.this.queryResultStr);
               } else {
                  FusiontablesControl.this.queryResultStr = FusiontablesControl.this.errorMessage;
                  Log.i("QueryProcessorV1", "Error:  " + FusiontablesControl.this.errorMessage);
               }

               return FusiontablesControl.this.queryResultStr;
            }
         } else {
            return OAuth2Helper.getErrorMessage();
         }
      }

      protected String doInBackground(String ... var1) {
         String var2 = var1[0];
         Log.i("QueryProcessorV1", "Starting doInBackground " + var2);
         return FusiontablesControl.this.isServiceAuth?this.serviceAuthRequest(var2):this.userAuthRequest(var2);
      }

      protected void onPostExecute(String var1) {
         Log.i("FUSION", "Query result " + var1);
         String var2 = var1;
         if(var1 == null) {
            var2 = FusiontablesControl.this.errorMessage;
         }

         this.dialog.dismiss();
         FusiontablesControl.this.GotResult(var2);
      }

      protected void onPreExecute() {
         this.dialog.setMessage("Fusiontables...");
         this.dialog.show();
      }

      String parseJsonResponseException(String var1) {
         Log.i("FUSION_SERVICE_ACCOUNT", "parseJsonResponseException: " + var1);
         return var1;
      }
   }
}
