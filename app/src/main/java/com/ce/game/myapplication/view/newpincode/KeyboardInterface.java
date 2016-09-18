package com.ce.game.myapplication.view.newpincode;

import android.view.View;

/**
 * Created on 2016/9/18
 * in BlaBla by Kyle
 */

public interface KeyboardInterface {
     View decideViewToResponse();

     void setPressCallback(PressCallback pressCallback);

     KeyboardInterface NULL = new KeyboardInterface() {
          @Override
          public View decideViewToResponse() {
               return null;
          }

          @Override
          public void setPressCallback(PressCallback pressCallback) {

          }
     };
}
