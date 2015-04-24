package com.google.appinventor.components.runtime.util;

import java.util.HashMap;
import java.util.Map;

public final class ErrorMessages {

   public static final int ERROR_ACTIVITY_STARTER_NO_CORRESPONDING_ACTIVITY = 601;
   public static final int ERROR_BAD_VALUE_FOR_ACCELEROMETER_SENSITIVITY = 1901;
   public static final int ERROR_BAD_VALUE_FOR_HORIZONTAL_ALIGNMENT = 1401;
   public static final int ERROR_BAD_VALUE_FOR_TEXT_RECEIVING = 1701;
   public static final int ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT = 1402;
   public static final int ERROR_BLUETOOTH_COULD_NOT_DECODE = 510;
   public static final int ERROR_BLUETOOTH_COULD_NOT_DECODE_ELEMENT = 513;
   public static final int ERROR_BLUETOOTH_COULD_NOT_FIT_ELEMENT_IN_BYTE = 514;
   public static final int ERROR_BLUETOOTH_COULD_NOT_FIT_NUMBER_IN_BYTE = 511;
   public static final int ERROR_BLUETOOTH_COULD_NOT_FIT_NUMBER_IN_BYTES = 512;
   public static final int ERROR_BLUETOOTH_END_OF_STREAM = 518;
   public static final int ERROR_BLUETOOTH_INVALID_ADDRESS = 503;
   public static final int ERROR_BLUETOOTH_INVALID_UUID = 506;
   public static final int ERROR_BLUETOOTH_NOT_AVAILABLE = 501;
   public static final int ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE = 515;
   public static final int ERROR_BLUETOOTH_NOT_ENABLED = 502;
   public static final int ERROR_BLUETOOTH_NOT_PAIRED_DEVICE = 504;
   public static final int ERROR_BLUETOOTH_NOT_REQUIRED_CLASS_OF_DEVICE = 505;
   public static final int ERROR_BLUETOOTH_UNABLE_TO_ACCEPT = 509;
   public static final int ERROR_BLUETOOTH_UNABLE_TO_CONNECT = 507;
   public static final int ERROR_BLUETOOTH_UNABLE_TO_LISTEN = 508;
   public static final int ERROR_BLUETOOTH_UNABLE_TO_READ = 517;
   public static final int ERROR_BLUETOOTH_UNABLE_TO_WRITE = 516;
   public static final int ERROR_BLUETOOTH_UNSUPPORTED_ENCODING = 519;
   public static final int ERROR_CAMCORDER_NO_CLIP_RETURNED = 1201;
   public static final int ERROR_CAMERA_NO_IMAGE_RETURNED = 201;
   public static final int ERROR_CANNOT_COPY_MEDIA = 1602;
   public static final int ERROR_CANNOT_CREATE_FILE = 2103;
   public static final int ERROR_CANNOT_DELETE_ASSET = 2105;
   public static final int ERROR_CANNOT_FIND_FILE = 2101;
   public static final int ERROR_CANNOT_READ_FILE = 2102;
   public static final int ERROR_CANNOT_SAVE_IMAGE = 1601;
   public static final int ERROR_CANNOT_WRITE_ASSET = 2106;
   public static final int ERROR_CANNOT_WRITE_TO_FILE = 2104;
   public static final int ERROR_CANVAS_BITMAP_ERROR = 1001;
   public static final int ERROR_CANVAS_HEIGHT_ERROR = 1003;
   public static final int ERROR_CANVAS_WIDTH_ERROR = 1002;
   public static final int ERROR_FILE_NOT_FOUND_FOR_SHARING = 2001;
   public static final int ERROR_FUNCTIONALITY_NOT_SUPPORTED_CONTACT_EMAIL = 1;
   public static final int ERROR_FUNCTIONALITY_NOT_SUPPORTED_EMAIL_PICKER = 2;
   public static final int ERROR_FUNCTIONALITY_NOT_SUPPORTED_FUSIONTABLES_CONTROL = 3;
   public static final int ERROR_FUNCTIONALITY_NOT_SUPPORTED_WEB_COOKIES = 4;
   public static final int ERROR_ILLEGAL_DATE = 2401;
   public static final int ERROR_ILLEGAL_HOUR = 2301;
   public static final int ERROR_ILLEGAL_MINUTE = 2302;
   public static final int ERROR_INVALID_SCREEN_ORIENTATION = 901;
   public static final int ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND = 101;
   public static final int ERROR_LOCATION_SENSOR_LONGITUDE_NOT_FOUND = 102;
   public static final int ERROR_MEDIA_CANNOT_OPEN = 707;
   public static final int ERROR_MEDIA_EXTERNAL_STORAGE_NOT_AVAILABLE = 705;
   public static final int ERROR_MEDIA_EXTERNAL_STORAGE_READONLY = 704;
   public static final int ERROR_MEDIA_FILE_ERROR = 708;
   public static final int ERROR_MEDIA_IMAGE_FILE_FORMAT = 706;
   public static final int ERROR_NO_SCANNER_FOUND = 1501;
   public static final int ERROR_NXT_BLUETOOTH_NOT_SET = 401;
   public static final int ERROR_NXT_CANNOT_DETECT_COLOR = 417;
   public static final int ERROR_NXT_CANNOT_DETECT_LIGHT = 418;
   public static final int ERROR_NXT_COULD_NOT_DECODE_ELEMENT = 412;
   public static final int ERROR_NXT_COULD_NOT_FIT_ELEMENT_IN_BYTE = 413;
   public static final int ERROR_NXT_DATA_TOO_LARGE = 411;
   public static final int ERROR_NXT_ERROR_CODE_RECEIVED = 404;
   public static final int ERROR_NXT_INVALID_DESTINATION_ARGUMENT = 415;
   public static final int ERROR_NXT_INVALID_FILE_NAME = 406;
   public static final int ERROR_NXT_INVALID_GENERATE_COLOR = 419;
   public static final int ERROR_NXT_INVALID_MAILBOX = 409;
   public static final int ERROR_NXT_INVALID_MOTOR_PORT = 407;
   public static final int ERROR_NXT_INVALID_PROGRAM_NAME = 405;
   public static final int ERROR_NXT_INVALID_RETURN_PACKAGE = 403;
   public static final int ERROR_NXT_INVALID_SENSOR_PORT = 408;
   public static final int ERROR_NXT_INVALID_SOURCE_ARGUMENT = 414;
   public static final int ERROR_NXT_MESSAGE_TOO_LONG = 410;
   public static final int ERROR_NXT_NOT_CONNECTED_TO_ROBOT = 402;
   public static final int ERROR_NXT_UNABLE_TO_DOWNLOAD_FILE = 416;
   public static final int ERROR_OUT_OF_MEMORY_LOADING_MEDIA = 711;
   public static final int ERROR_PHONE_UNSUPPORTED_CONTACT_PICKER = 1107;
   public static final int ERROR_PHONE_UNSUPPORTED_SEARCH_IN_CONTACT_PICKING = 1108;
   public static final int ERROR_REPL_SECURITY_ERROR = 1801;
   public static final int ERROR_SCREEN_BAD_VALUE_FOR_SENDING = 904;
   public static final int ERROR_SCREEN_BAD_VALUE_RECEIVED = 903;
   public static final int ERROR_SCREEN_INVALID_ANIMATION = 905;
   public static final int ERROR_SCREEN_NOT_FOUND = 902;
   public static final int ERROR_SOUND_NOT_READY = 710;
   public static final int ERROR_SOUND_RECORDER = 801;
   public static final int ERROR_SOUND_RECORDER_CANNOT_CREATE = 802;
   public static final int ERROR_TRANSLATE_JSON_RESPONSE = 2203;
   public static final int ERROR_TRANSLATE_NO_KEY_FOUND = 2201;
   public static final int ERROR_TRANSLATE_SERVICE_NOT_AVAILABLE = 2202;
   public static final int ERROR_TTS_NOT_READY = 2701;
   public static final int ERROR_TWITTER_AUTHORIZATION_FAILED = 305;
   public static final int ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET = 302;
   public static final int ERROR_TWITTER_DIRECT_MESSAGE_FAILED = 310;
   public static final int ERROR_TWITTER_EXCEPTION = 303;
   public static final int ERROR_TWITTER_FOLLOW_FAILED = 311;
   public static final int ERROR_TWITTER_INVALID_IMAGE_PATH = 315;
   public static final int ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED = 309;
   public static final int ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED = 308;
   public static final int ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED = 313;
   public static final int ERROR_TWITTER_REQUEST_MENTIONS_FAILED = 307;
   public static final int ERROR_TWITTER_SEARCH_FAILED = 314;
   public static final int ERROR_TWITTER_SET_STATUS_FAILED = 306;
   public static final int ERROR_TWITTER_STOP_FOLLOWING_FAILED = 312;
   public static final int ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN = 304;
   public static final int ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION = 301;
   public static final int ERROR_UNABLE_TO_FOCUS_MEDIA = 709;
   public static final int ERROR_UNABLE_TO_LOAD_MEDIA = 701;
   public static final int ERROR_UNABLE_TO_PLAY_MEDIA = 703;
   public static final int ERROR_UNABLE_TO_PREPARE_MEDIA = 702;
   public static final int ERROR_VIDEOPLAYER_FULLSCREEN_CANT_EXIT = 1302;
   public static final int ERROR_VIDEOPLAYER_FULLSCREEN_UNAVAILBLE = 1301;
   public static final int ERROR_VIDEOPLAYER_FULLSCREEN_UNSUPPORTED = 1303;
   public static final int ERROR_WEBVIEW_SSL_ERROR = 2501;
   public static final int ERROR_WEB_BUILD_REQUEST_DATA_NOT_LIST = 1112;
   public static final int ERROR_WEB_BUILD_REQUEST_DATA_NOT_TWO_ELEMENTS = 1113;
   public static final int ERROR_WEB_HTML_TEXT_DECODE_FAILED = 1106;
   public static final int ERROR_WEB_JSON_TEXT_DECODE_FAILED = 1105;
   public static final int ERROR_WEB_MALFORMED_URL = 1109;
   public static final int ERROR_WEB_REQUEST_HEADER_NOT_LIST = 1110;
   public static final int ERROR_WEB_REQUEST_HEADER_NOT_TWO_ELEMENTS = 1111;
   public static final int ERROR_WEB_UNABLE_TO_DELETE = 1114;
   public static final int ERROR_WEB_UNABLE_TO_GET = 1101;
   public static final int ERROR_WEB_UNABLE_TO_POST_OR_PUT = 1103;
   public static final int ERROR_WEB_UNABLE_TO_POST_OR_PUT_FILE = 1104;
   public static final int ERROR_WEB_UNSUPPORTED_ENCODING = 1102;
   public static final int ERROR_WEB_XML_TEXT_DECODE_FAILED = 1115;
   public static final int FUSION_TABLES_QUERY_ERROR = 2601;
   private static final Map errorMessages = new HashMap();


   static {
      errorMessages.put(Integer.valueOf(1), "Warning: This app contains functionality that does not work on this phone: picking an EmailAddress.");
      errorMessages.put(Integer.valueOf(2), "Warning: This app contains functionality that does not work on this phone: the EmailPicker component.");
      errorMessages.put(Integer.valueOf(3), "Warning: This app contains functionality that does not work on this phone: the FusiontablesControl component.");
      errorMessages.put(Integer.valueOf(4), "Warning: This app contains functionality that does not work on this phone: using cookies in the Web component.");
      errorMessages.put(Integer.valueOf(101), "Unable to find latitude from %s.");
      errorMessages.put(Integer.valueOf(102), "Unable to find longitude from %s.");
      errorMessages.put(Integer.valueOf(201), "The camera did not return an image.");
      errorMessages.put(Integer.valueOf(301), "Twitter no longer supports this form of Login. Use the Authorize call instead.");
      errorMessages.put(Integer.valueOf(302), "The ConsumerKey and ConsumerSecret properties must be set in order to authorize access for Twitter. Please obtain a Comsumer Key and Consumer Secret specific to your app from http://twitter.com/oauth_clients/new");
      errorMessages.put(Integer.valueOf(303), "Twitter error: %s");
      errorMessages.put(Integer.valueOf(304), "Unable to get access token: %s");
      errorMessages.put(Integer.valueOf(305), "Twitter authorization failed");
      errorMessages.put(Integer.valueOf(306), "SetStatus failed. %s");
      errorMessages.put(Integer.valueOf(307), "RequestMentions failed. %s");
      errorMessages.put(Integer.valueOf(308), "RequestFollowers failed. %s");
      errorMessages.put(Integer.valueOf(309), "RequestDirectMessages failed. %s");
      errorMessages.put(Integer.valueOf(310), "DirectMessage failed. %s");
      errorMessages.put(Integer.valueOf(311), "Follow failed. %s");
      errorMessages.put(Integer.valueOf(312), "StopFollowing failed. %s");
      errorMessages.put(Integer.valueOf(313), "Twitter RequestFriendTimeline failed: %s");
      errorMessages.put(Integer.valueOf(314), "Twitter search failed.");
      errorMessages.put(Integer.valueOf(315), "Invalid Path to Image; Update will not be sent.");
      errorMessages.put(Integer.valueOf(401), "The Bluetooth property has not been set.");
      errorMessages.put(Integer.valueOf(402), "Not connected to a robot.");
      errorMessages.put(Integer.valueOf(403), "Unable to receive return package. Has the robot gone to sleep?");
      errorMessages.put(Integer.valueOf(404), "Error code received from robot: %s.");
      errorMessages.put(Integer.valueOf(405), "Invalid program name.");
      errorMessages.put(Integer.valueOf(406), "Invalid file name.");
      errorMessages.put(Integer.valueOf(407), "The NXT does not have a motor port labeled %s.");
      errorMessages.put(Integer.valueOf(408), "The NXT does not have a sensor port labeled %s.");
      errorMessages.put(Integer.valueOf(409), "The NXT does not have a mailbox number %s.");
      errorMessages.put(Integer.valueOf(410), "The NXT only accepts messages up to 58 characters.");
      errorMessages.put(Integer.valueOf(411), "The data is too large; it must be 16 bytes or less.");
      errorMessages.put(Integer.valueOf(412), "Could not decode element %s as an integer.");
      errorMessages.put(Integer.valueOf(413), "Could not fit element %s into 1 byte.");
      errorMessages.put(Integer.valueOf(414), "Invalid source argument.");
      errorMessages.put(Integer.valueOf(415), "Invalid destination argument.");
      errorMessages.put(Integer.valueOf(416), "Unable to download file to robot: %s");
      errorMessages.put(Integer.valueOf(417), "Cannot detect color when the DetectColor property is set to False.");
      errorMessages.put(Integer.valueOf(418), "Cannot detect light level when the DetectColor property is set to True.");
      errorMessages.put(Integer.valueOf(419), "The GenerateColor property is limited to None, Red, Green, or Blue.");
      errorMessages.put(Integer.valueOf(501), "Bluetooth is not available.");
      errorMessages.put(Integer.valueOf(502), "Bluetooth is not available.");
      errorMessages.put(Integer.valueOf(503), "The specified address is not a valid Bluetooth MAC address.");
      errorMessages.put(Integer.valueOf(504), "The specified address is not a paired Bluetooth device.");
      errorMessages.put(Integer.valueOf(505), "The specified address is not the required class of device.");
      errorMessages.put(Integer.valueOf(506), "The UUID \"%s\" is not formatted correctly.");
      errorMessages.put(Integer.valueOf(507), "Unable to connect. Is the device turned on?");
      errorMessages.put(Integer.valueOf(508), "Unable to listen for a connection from a bluetooth device.");
      errorMessages.put(Integer.valueOf(509), "Unable to accept a connection from a bluetooth device.");
      errorMessages.put(Integer.valueOf(510), "Could not decode \"%s\" as an integer.");
      errorMessages.put(Integer.valueOf(511), "Could not fit \"%s\" into 1 byte.");
      errorMessages.put(Integer.valueOf(512), "Could not fit \"%s\" into %s bytes.");
      errorMessages.put(Integer.valueOf(513), "Could not decode element %s as an integer.");
      errorMessages.put(Integer.valueOf(514), "Could not fit element %s into 1 byte.");
      errorMessages.put(Integer.valueOf(515), "Not connected to a Bluetooth device.");
      errorMessages.put(Integer.valueOf(516), "Unable to write: %s");
      errorMessages.put(Integer.valueOf(517), "Unable to read: %s");
      errorMessages.put(Integer.valueOf(518), "End of stream has been reached.");
      errorMessages.put(Integer.valueOf(519), "The encoding %s is not supported.");
      errorMessages.put(Integer.valueOf(601), "No corresponding activity was found.");
      errorMessages.put(Integer.valueOf(701), "Unable to load %s.");
      errorMessages.put(Integer.valueOf(702), "Unable to prepare %s.");
      errorMessages.put(Integer.valueOf(703), "Unable to play %s.");
      errorMessages.put(Integer.valueOf(704), "External storage is available but read-only.");
      errorMessages.put(Integer.valueOf(705), "External storage is not available.");
      errorMessages.put(Integer.valueOf(706), "Image file name must end in \".jpg\", \".jpeg\", or \".png\".");
      errorMessages.put(Integer.valueOf(707), "Cannot open file %s.");
      errorMessages.put(Integer.valueOf(708), "Got file error: %s.");
      errorMessages.put(Integer.valueOf(709), "Unable to grant exclusive lock of audio output stream to %s.");
      errorMessages.put(Integer.valueOf(710), "The sound is not ready to play: %s.");
      errorMessages.put(Integer.valueOf(711), "Not Enough Memory to load: %s.");
      errorMessages.put(Integer.valueOf(801), "An unexpected error occurred while recording sound.");
      errorMessages.put(Integer.valueOf(802), "Cannot start recording: %s");
      errorMessages.put(Integer.valueOf(901), "The specified screen orientation is not valid: %s");
      errorMessages.put(Integer.valueOf(902), "Screen not found: %s");
      errorMessages.put(Integer.valueOf(903), "Bad value received from other screen: %s");
      errorMessages.put(Integer.valueOf(904), "Bad value for sending to other screen: %s");
      errorMessages.put(Integer.valueOf(905), "Bad value for screen open/close animation: %s");
      errorMessages.put(Integer.valueOf(1001), "Error getting Canvas contents to save");
      errorMessages.put(Integer.valueOf(1002), "Canvas width cannot be set to non-positive number");
      errorMessages.put(Integer.valueOf(1003), "Canvas height cannot be set to non-positive number");
      errorMessages.put(Integer.valueOf(1101), "Unable to get a response with the specified URL: %s");
      errorMessages.put(Integer.valueOf(1102), "The encoding %s is not supported.");
      errorMessages.put(Integer.valueOf(1103), "Unable to post or put the text \"%s\" with the specified URL: %s");
      errorMessages.put(Integer.valueOf(1104), "Unable to post or put the file \"%s\" with the specified URL %s.");
      errorMessages.put(Integer.valueOf(1105), "Unable to decode the JSON text: %s");
      errorMessages.put(Integer.valueOf(1106), "Unable to decode the HTML text: %s");
      errorMessages.put(Integer.valueOf(1115), "Unable to decode the XML text: %s");
      errorMessages.put(Integer.valueOf(1109), "The specified URL is not valid: %s");
      errorMessages.put(Integer.valueOf(1110), "The specified request headers are not valid: element %s is not a list");
      errorMessages.put(Integer.valueOf(1111), "The specified request headers are not valid: element %s does not contain two elements");
      errorMessages.put(Integer.valueOf(1112), "Unable to build request data: element %s is not a list");
      errorMessages.put(Integer.valueOf(1113), "Unable to build request data: element %s does not contain two elements");
      errorMessages.put(Integer.valueOf(1114), "Unable to delete a resource with the specified URL: %s");
      errorMessages.put(Integer.valueOf(1107), "The software used in this app cannot extract contacts from this type of phone.");
      errorMessages.put(Integer.valueOf(1108), "To pick contacts, pick them directly, without using search.");
      errorMessages.put(Integer.valueOf(1201), "The camcorder did not return a clip.");
      errorMessages.put(Integer.valueOf(1301), "Cannot start fullscreen mode.");
      errorMessages.put(Integer.valueOf(1302), "Cannot exit fullscreen mode.");
      errorMessages.put(Integer.valueOf(1303), "Fullscreen mode not supported on this version of Android.");
      errorMessages.put(Integer.valueOf(1401), "The value -- %s -- provided for HorizontalAlignment was bad.  The only legal values are 1, 2, or 3.");
      errorMessages.put(Integer.valueOf(1402), "The value -- %s -- provided for VerticalAlignment was bad.  The only legal values are 1, 2, or 3.");
      errorMessages.put(Integer.valueOf(1501), "Your device does not have a scanning application installed.");
      errorMessages.put(Integer.valueOf(1601), "Unable to save image: %s");
      errorMessages.put(Integer.valueOf(1602), "Unable to copy selected media: %s");
      errorMessages.put(Integer.valueOf(1701), "Text Receiving should be either 1, 2 or 3.");
      errorMessages.put(Integer.valueOf(1801), "Security Error Receiving Blocks from Browser.");
      errorMessages.put(Integer.valueOf(1901), "The value -- %s -- provided for AccelerometerSensor\'s sensitivity was bad. The only legal values are 1, 2, or 3.");
      errorMessages.put(Integer.valueOf(2001), "The File %s could not be found on your device.");
      errorMessages.put(Integer.valueOf(2101), "The file %s could not be found");
      errorMessages.put(Integer.valueOf(2102), "The file %s could not be opened");
      errorMessages.put(Integer.valueOf(2103), "The file %s could not be created");
      errorMessages.put(Integer.valueOf(2104), "Cannot write to file %s");
      errorMessages.put(Integer.valueOf(2105), "Cannot delete asset file at %s");
      errorMessages.put(Integer.valueOf(2106), "Cannot write asset file at %s");
      errorMessages.put(Integer.valueOf(2201), "Missing API key for the Yandex.Translate service.");
      errorMessages.put(Integer.valueOf(2202), "The translation service is not available; Please try again later.");
      errorMessages.put(Integer.valueOf(2203), "The response from the Yandex.Translate service cannot be parsed; Please try again later.");
      errorMessages.put(Integer.valueOf(2301), "The hour must be set to a value between 0 and 23.");
      errorMessages.put(Integer.valueOf(2302), "The minute must be set to a value between 0 and 59.");
      errorMessages.put(Integer.valueOf(2401), "The date you entered is invalid.");
      errorMessages.put(Integer.valueOf(2501), "SSL Connection could not complete.");
      errorMessages.put(Integer.valueOf(2601), "Fusion tables returned an error. The query was: %s. The response was: %s");
      errorMessages.put(Integer.valueOf(2701), "TextToSpeech is not yet ready to perform this operation");
   }

   public static String formatMessage(int var0, Object[] var1) {
      return String.format((String)errorMessages.get(Integer.valueOf(var0)), var1);
   }
}
