package com.ce.game.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.InstrumentationTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ce.game.myapplication.showcase.LeadActivity;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class LoginTest extends InstrumentationTestCase {

    @org.junit.Test
    @MediumTest
    public void testAValidUserCanLogIn() {

        Instrumentation instrumentation = getInstrumentation();

        // Register we are interested in the authentication activiry...
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(LeadActivity.class.getName(), null, false);

        // Start the authentication activity as the first activity...
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), LeadActivity.class.getName());
        instrumentation.startActivitySync(intent);

        // Wait for it to start...
        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertThat(currentActivity, is(notNullValue()));

        // Type into the username field...
        View currentView = currentActivity.findViewById(username_field);
        assertThat(currentView, is(notNullValue()));
        assertThat(currentView, instanceOf(EditText.class));
        TouchUtils.clickView(this, currentView);
        instrumentation.sendStringSync("MyUsername");

        // Type into the password field...
        currentView = currentActivity.findViewById(password_field);
        assertThat(currentView, is(notNullValue()));
        assertThat(currentView, instanceOf(EditText.class));
        TouchUtils.clickView(this, currentView);
        instrumentation.sendStringSync("MyPassword");

        // Register we are interested in the welcome activity...
        // this has to be done before we do something that will send us to that
        // activity...
        instrumentation.removeMonitor(monitor);
        monitor = instrumentation.addMonitor(WelcomeActivity.class.getName(), null, false);

        // Click the login button...
        currentView = currentActivity.findViewById(login_button;
        assertThat(currentView, is(notNullValue()));
        assertThat(currentView, instanceOf(Button.class));
        TouchUtils.clickView(this, currentView);

        // Wait for the welcome page to start...
        currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertThat(currentActivity, is(notNullValue()));

        // Make sure we are logged in...
        currentView = currentActivity.findViewById(welcome_message);
        assertThat(currentView, is(notNullValue()));
        assertThat(currentView, instanceOf(TextView.class));
        assertThat(((TextView)currentView).getText().toString(), is("Welcome, MyUsername!"));
    }
}