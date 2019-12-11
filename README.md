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
    implementation 'com.github.KishanViramgama:Drawing:0.1.5'
}
</pre>

     <RelativeLayout
        android:id="@+id/relativeLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@+id/toolbar"
        android:background="@color/white">

        <com.app.drawing.Util.CanvasView
            android:id="@+id/canvas_drawing"
            app:paintColor="@color/colorPrimary"
            app:paintStork="10"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:contentDescription="@string/app_name"
            android:src="@mipmap/ic_app_icon" />

    </RelativeLayout>

</pre>

<b>java file</b>

<pre>
private CanvasView customCanvas = findViewById(R.id.canvas_drawing);
ImageView imageView = findViewById(R.id.imageView);

Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
imageView.setImageBitmap(bitmap);

customCanvas.paintColor(getResources().getColor(R.color.colorAccent));
customCanvas.paintStork(20);
customCanvas.clear();
</pre>

