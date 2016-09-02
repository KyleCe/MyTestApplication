package com.ce.game.myapplication.view.pincode;

/**
 * Created by KyleCe on 2016/6/23.
 *
 * @author: KyleCe
 */
public class Const {
    // For Activity Result
    public static final String REQUEST_ROLE = "role";
    public static final int REQUEST_USER_ROLE_CODE = 20;

    public static final String REQUEST_CITY_INFO = "city";
    public static final int REQUEST_CITY_INFO_CODE = 20;

    public static final String REQUEST_ACTIVITY_EXIT = "exit";
    public static final int REQUEST_ACTIVITY_EXIT_CODE = 20;

    public static final String REQUEST_CITY_PICKER_INFO = "city_picker";
    public static final int REQUEST_ACTIVITY_CITY = 23;

    public static final String NOW_CITY = "location";
    public static final String SAVE_CITY_LIST = "save_city_list";

    public static final String APP_HIDE = "Hide Apps";
    public static final String FOLDER_ADD_APPS = "folder_add_apps";
    public static final String APP_TITLE = "app_title";

    public static final int MESSAGE_HIDE_APP = 24;
    public static final int MESSAGE_HIDE_APP_ZERO = 25;

    public static final String EDIT_USER_HIDE_APPS_NUM = "edit_user_hide_apps_num";
    public static final String EDIT_USER = "editUser";
    public static final String USER_ID = "user_id";
    public static final String BROADCAST_KEY_USER_PASSWORD = "user_password";
    public static final String BROADCAST_KEY_USER_ID = "user_id";
    public static final String BROADCAST_KEY_USER_NAME = "user_name";
    public static final String BROADCAST_KEY_USER_ROLE = "user_role";

    public static final int MESSAGE_BOX_NOTIFICATION_ID = 10099;

    // For Activity Arguments
    // Action Bar
    public static final String ARGU_ACTION_BAR = "back";
    public static final int ACTION_BAR_FROM_CREATE = 20;
    public static final int ACTION_BAR_FROM_EDIT = 21;

    //    wallpaper
    public static final int IMAGE_PICK = 5;
    public static final String DEFAULT_WALLPAPER_THUMBNAIL = "default_thumb2.jpg";
    public static final String DEFAULT_WALLPAPER_THUMBNAIL_OLD = "default_thumb.jpg";
    public static final String DIR_ROOT = ".BlaBlaPrivacy";
    public static final String SCREEN_LOCK_WALLPAPER = "/screen_lock_wallpaper";
    public static final String DEFAULT_WALLPAPER_DIRECTORY = "/default-wallpaper";
    public static final String DEFAULT_WALLPAPER_NAME = "default.png";
    public static final String SYSTEM_DEFAULT_WALLPAPER_NAME = "system-default.png";
    public static final String USER_WALLPAPER_NAME = "wallpaper-";
    public static final String USER_WALLPAPER_NAME_SUFFIX = ".png";
    public static final String GUEST_ENIGMA_RESULT = "guest";
    public static final String SCREEN_LOCK_WALLPAPER_THUMBNAIL = "screen_lock_thumb.png";
    public static final String SCREEN_LOCK_WALLPAPER_THUMBNAIL_BLURRED = "screen_lock_thumb_blurred.png";
    public static final String EXCLUDE_DIRECTORY_FROM_GALLERY_TRICK_FILE = ".nomedia";
    public static final String WALLPAPER_TRIGGER_SOURCE = "wallpaper_trigger_source";
    public static final String WALLPAPER_TARGET_ROLE = "wallpaper_to_set_role";
    public static final String WALLPAPER_NOT_MYSELF_PASSWORD = "wallpaper_not_myself_password";
    public static final String EMPTY_PASSWORD_ENIGMA_RESULT = "empty";

    //weather
    public static final String TEM_UNIT = "tem_unit";
    public static final String TEM_UNIT_F = "f";
    public static final String TEM_UNIT_C = "c";

    //vault
    public static final String DEFAULT_VAULT_DIRECTORY = "/Vault";
    public static final String FIST_IN_VAULT = "fist_in_vault";
    // for screen display
    public static final int MAX_HOME_SCREEN_COUNT = 12;
    public static final int MAX_FOLDER_SCREEN_COUNT = 36;
    public static final int MAX_HOTSEAT_COLUMN_COUNT = 1;
    public static final int MAX_WORKSPACE_SCREEN_COUNT = 128;

    // for screen lock
    public static final String FIRST_LAUNCH_LOCK_SCREEN = "first_launch_lock_screen";
    public static final int DEFAULT_PIN_CODE_LEN = 4;

    //    retrieve password
    public static class OAuth2Credentials {
        public static final String URL_START = "https://accounts.google.com/o/oauth2/v2/auth?";
        public static final String CLIENT_ID = "294554247866-47jje3ufmkkvtlhkioqtcf43vi7c51to.apps.googleusercontent.com";
        public static final String REDIRECT_URI = "http%3A%2F%2Flocalhost%2F__%2Fauth%2Fhandler";
        public static final String SCOPE = "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile";
    }

    public static final long DEFAULT_SHOW_UP_TIPS_DELAY = 500;
}
