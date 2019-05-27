// enable gradle file auto completion / suggestion, currently is bugged
// Android Studio 3.5 will fix the issue:
// https://issuetracker.google.com/issues/123032843
object Versions {

    const val BUILD_TOOL = "28.0.3"
    const val MIN_SDK = 21
    const val TARGET_SDK = 28
    const val COMPILE_SDK = 28
    const val CODE = 1
    const val NAME = "1.0"

    const val KOTLIN = "1.3.31"
    const val KOTLIN_ANDROID = "1.2.1"
    const val KOTLIN_COROUTINES = "1.2.1"

    const val ANDROIDX = "1.0.0"
    const val ANDROIDX_UI = "1.0.0"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "1.1.3"
    const val ANDROIDX_LIFECYCLE = "2.0.0"
    const val ANDROIDX_MULTIDEX = "2.0.0"
    const val ANDROIDX_KTX = "1.0.0"
    const val ANDROIDX_KTX_VIEWMODEL = "2.2.0-alpha01"
    const val ANDROIDX_NAVIGATION = "2.0.0"

    const val MATERIAL = "1.0.0"

    const val FIREBASE_CORE = "16.0.6"
    const val FIREBASE_MESSAGING = "17.3.4"
    const val FIREBASE_CRASHLYTICS = "2.9.7"

    const val KOIN = "2.0.0-GA4"

    const val RETROFIT = "2.5.0"
    const val RETROFIT_COROUTINES = "0.9.2"
    const val OKHTTP_INTEREPTOR = "3.9.0"

    const val GLIDE = "4.9.0"
    const val CODE_UTILS = "1.19.3"
    const val MATERIAL_DIALOG = "2.8.1"
    const val TIMBER = "4.7.1"
    const val JODA_TIME = "2.10.1.2"

    const val JUNIT = "4.12"
    const val MOCKIO = "2.1.0"
    const val JUNIT_TIMBER = "1.0.1"
}

object Kotlin {
    const val STANDARD = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_ANDROID}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}"
}

// Google
// ==========
object Google {
    const val SUPPORT_V4 = "androidx.legacy:legacy-support-v4:${Versions.ANDROIDX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX}"
    const val ANNOTATION = "androidx.annotation:annotation:${Versions.ANDROIDX}"
    const val LIFECYCLE_RUNTIME =
        "androidx.lifecycle:lifecycle-runtime:${Versions.ANDROIDX_LIFECYCLE}"
    const val LIFECYCLE_COMPILER =
        "androidx.lifecycle:lifecycle-compiler:${Versions.ANDROIDX_LIFECYCLE}"
    const val LIFECYCLE_EXTENSIONS =
        "androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE}"
    const val MULTIDEX = "androidx.multidex:multidex:${Versions.ANDROIDX_MULTIDEX}"
    const val KTX_CORE = "androidx.core:core-ktx:${Versions.ANDROIDX_KTX}"
    const val KTX_FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.ANDROIDX_KTX}"
    const val KTX_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_KTX_VIEWMODEL}"

    const val DESIGN = "com.google.android.material:material:${Versions.MATERIAL}"

    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION}"
}

object Ui {
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_UI}"
    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.ANDROIDX_UI}"
    const val CARD_VIEW = "androidx.cardview:cardview:${Versions.ANDROIDX_UI}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT_LAYOUT}"
}

// Firebase
// ==========
object Firebase {
    const val CORE = "com.google.firebase:firebase-core:${Versions.FIREBASE_CORE}"
    const val MESSAGING =
        "com.google.firebase:firebase-messaging:${Versions.FIREBASE_MESSAGING}"
    const val CRASHLYTICS =
        "com.crashlytics.sdk.android:crashlytics:${Versions.FIREBASE_CRASHLYTICS}"
}

// Glide
// https://github.com/bumptech/glide
// ==========
object Glide {
    const val CORE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
}

// Koin
// https://github.com/InsertKoinIO/koin
// ==========
object Koin {
    const val ANDROID = "org.koin:koin-android:${Versions.KOIN}"
    const val SCOPE = "org.koin:koin-android-scope:${Versions.KOIN}"
    const val VIEWMODEL = "org.koin:koin-android-viewmodel:${Versions.KOIN}"
}

// Retrofit
// https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
// https://github.com/square/retrofit
// ==========
object Retrofit {
    const val CORE = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val COROUTINES = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.RETROFIT_COROUTINES}"
    const val MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    const val OKHTTP_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_INTEREPTOR}"
}

// Utils
// https://github.com/Blankj/AndroidUtilCode
// https://github.com/afollestad/material-dialogs
// https://github.com/JakeWharton/timber
// https://github.com/dlew/joda-time-android
// https://github.com/kaiyan910/Pirate
// ==========
object Utils {
    const val CODE_UTILS = "com.blankj:utilcode:${Versions.CODE_UTILS}"
    const val MATERIAL_DIALOG = "com.afollestad.material-dialogs:core:${Versions.MATERIAL_DIALOG}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val JODA_TIME = "net.danlew:android.joda:${Versions.JODA_TIME}"
}

// Test
// https://github.com/junit-team/junit4
// https://github.com/nhaarman/mockito-kotlin
// https://github.com/LachlanMcKee/timber-junit-rule
// ==========
object Testing {
    const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKIO}"
    const val LIFECYCLE = "androidx.arch.core:core-testing:${Versions.ANDROIDX_LIFECYCLE}"
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val JUNIT_TIMBER = "net.lachlanmckee:timber-junit-rule:${Versions.JUNIT_TIMBER}"
}
