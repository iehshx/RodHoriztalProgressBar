# RodHoriztalProgressBar
说明：

----------
> 这是一个基于ProgressBar的进度条。
> 颜色、大小、背景都通过属性直接设置。
> 本项目没有做代码设置的属性的实现，可以自行加入。

![RodHoriztalProgressBar.jpg](http://upload-images.jianshu.io/upload_images/2405115-d0e7dd04981c82d8?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![RodHoriztalProgressBar.gif](http://upload-images.jianshu.io/upload_images/2405115-fb5921ccc97cb147?imageMogr2/auto-orient/strip)

使用方法：
 <com.iehshx.rodhoriztalprogressbar.RodHoriztalProgressBar
        android:id="@+id/mine_credit_pb_credit"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:layout_marginTop="40dp"
        app:progressbar_reach_color="@color/bule_0aaccc"
        app:progressbar_reach_height="3dp"
        app:progressbar_rod_bg="@mipmap/bg_rod"
        app:progressbar_text_bg="@mipmap/bg_zs"
        app:progressbar_text_color="#fff"
        app:progressbar_text_offset_line="5dp"
        app:progressbar_text_size="@dimen/fontSize_16"
      app:progressbar_unreach_color="@color/bg_gray_empty"
        app:progressbar_unreach_height="3dp"
        tools:layout_editor_absoluteX="8dp" />
        
