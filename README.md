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

<b>xml file</b>
<pre>&lt;<span class="pl-ent">RelativeLayout</span>
    <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">id</span>=<span class="pl-s"><span class="pl-pds">"</span>@+id/imageView<span class="pl-pds">"</span></span>
    <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_width</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"</span></span>
    <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_height</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"</span></span>
    <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">background</span>=<span class="pl-s"><span class="pl-pds">"</span>@string/app_name<span class="pl-pds">"</span></span>&gt;
	
	&lt;<span class="pl-ent">com.app.drawing.Util.CanvasView</span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">id</span>=<span class="pl-s"><span class="pl-pds">"</span>@+id/imageView<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_width</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_height</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"/</span></span>&gt;
	
	&lt;<span class="pl-ent">ImageView</span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">id</span>=<span class="pl-s"><span class="pl-pds">"</span>@+id/imageView<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_width</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">layout_height</span>=<span class="pl-s"><span class="pl-pds">"</span>match_parent<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">contentDescription</span>=<span class="pl-s"><span class="pl-pds">"</span>@string/app_name<span class="pl-pds">"</span></span>
	  <span class="pl-e">android</span><span class="pl-e">:</span><span class="pl-e">src</span>=<span class="pl-s"><span class="pl-pds">"</span>@mipmap/ic_app_icon<span class="pl-pds">"/</span></span>&gt;

&lt;<span class="pl-ent">RelativeLayout</span>&gt;
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

