package com.klisly.bookbox.ui.activity;

public class MainActivity {

//    private static final int ANIM_DURATION_TOOLBAR = 300;
//    private static final int ANIM_DURATION_FAB = 400;
//    @Bind(R.id.content)
//    CoordinatorLayout clContent;
//    @Bind(R.id.content_layout)
//    FrameLayout frameLayout;
//
//    private boolean pendingIntroAnimation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            pendingIntroAnimation = true;
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        if (pendingIntroAnimation) {
//            pendingIntroAnimation = false;
//            startIntroAnimation();
//        }
//        return true;
//    }
//
//    private void startIntroAnimation() {
//        int actionbarSize = Utils.dpToPx(56);
//        getToolbar().setTranslationY(-actionbarSize);
//        getIvLogo().setTranslationY(-actionbarSize);
//        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);
//
//        getToolbar().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(300);
//        getIvLogo().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(400);
//        getInboxMenuItem().getActionView().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(500)
//                .start();
//    }
//
//    public void showNotificationSnackbar() {
//        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
//    }
}