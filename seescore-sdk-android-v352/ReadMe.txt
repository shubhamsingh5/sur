ReadMe
------
SeeScoreLib SDK for Android

This release provides:

* The SeeScore library for Android providing support for rendering of MusicXML in a Canvas with a simple java interface;

* class uk.co.dolphin_com.seescoreandroid.SeeScoreView (with SystemView) which can be embedded in a android.widget.ScrollView for displaying the music score defined by a MusicXML file in an Android app.

* The SeeScoreAndroid sample project which builds an app using SeeScoreView. This provides a starting point for a developer wanting to use the SeeScore library.

* An evaluation licence key file LicenceKeyInstance.java which causes translucent red 'watermark' text to be displayed over the top of the score to prevent resale.
A licence key file can be purchased from Dolphin Computing to remove the watermark, and to enable other features

* extra licenses are required for certain functions in classes SScore, SSystem and PlayData. Details are in the documentation of the individual functions.
Some of these licenses are included in the evaluation version so the functions can be evaluated. Some of the methods can be used for a limited time without the appropriate licence for evaluation.


Starting Development with Android
—————————————————————————————————
1. Start by downloading the free Android Studio at https://developer.android.com/sdk/index.html
2. Install and run Android Studio (included in the Android SDK)
3. Use File->Import to import the SeeScoreAndroid project into your workspace. You should select the option to copy the project in order to ensure you don’t change the original project.
4. If you have an Android device plug it in. You can also use the Android virtual device simulator.
5. You may need to sync by pressing the button at the top with a downward pointing arrow (hold the cursor over for 2s and it says "Sync Project with Gradle Files").
6. press the Run or the Debug button, select your Android device to run on and you should shortly see it displaying a score on the device.
7. Each time you tap Next it will show a different score. You can scroll and pinch-zoom and you can transpose using the -/+ buttons. By tapping the play button under the score you should hear it play, and you will see a blue cursor which follows the score while it plays
7. The sample app has been tested on the Google Nexus 7 with Android version 5.1.1. 

Documentation
—————————————
Full documentation is in directory doc/

Note
----
All source code supplied may be used without any conditions attached, or any warranty implied.
SeeScoreLib.so must not be copied to a third party, but should always be sourced from Dolphin Computing. http://www.dolphin-com.co.uk
