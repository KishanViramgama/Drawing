# Drawing

<img src="https://github.com/KishanViramgama/Drawing/blob/master/app/src/main/res/drawable/app_demo.gif" height="368px" align="right" style="max-width:100%;">

<b>Gradle</b>

Add following dependency to your root project build.gradle file:

<pre>
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
</pre>

Add following dependency to your app module build.gradle file:

<pre>
dependencies {
    implementation 'com.github.KishanViramgama:Drawing:0.1.1'
}
</pre>

<b>Gradle</b>

xml file

<pre>
<com.app.drawing.Util.CanvasView
   android:id="@+id/canvas_drawing"
   android:layout_width="match_parent"
   android:layout_height="match_parent" />
</pre>

