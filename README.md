# CozyDialogFragment
Easy to use API based on DialogFragment



1. 使用 DialogFragment 中的疑难杂症

   - Activity 全屏时 DialogFragment 宽高 MATCH_PARENT ，显示 DialogFragment 时状态栏会显示出来？

     这是因为全屏、透明状态栏等操作都需要对 Window 进行操作，为 Window 添加一些 Flag ，而 Activity 和 Dialog 都显示在 Window 上，内部都持有自己的 Window ，所以想和 Activity 效果一致需要给 Dialog 的 Window 设置同样的属性。

     DialogFragment 宽高 MATCH_PARENT 时状态栏变黑色只需给 Dialog 的 Window 适配一下沉浸式状态栏即可，想要得到全屏 DialogFragment 也只需在 Dialog 的 Window 中添加全屏的 Flag。

   - Activity 设置了状态栏字体颜色为暗色，DialogFragment 弹出后状态栏字体颜色变为白色？

     通过 ``` window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)``` 清除阴影则 DialogFragment 的状态栏会和它的 Activity 保持一致，然后通过 ViewGroupOverlay 实现阴影效果。

