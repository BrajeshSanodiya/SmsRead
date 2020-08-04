import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 21
    const val compileSdk = 29
    const val targetSdk = 29
    val javaVersion = JavaVersion.VERSION_1_8
    const val buildTools = "29.0.3"
}

object Versions {
    //region Google
    const val androidxAppcompat = "1.2.0-beta01"
    const val androidxAnnotation = "1.1.0"
    const val androidxConstraintlayout = "1.1.3"
    const val androidxRecyclerview = "1.1.0"
    const val androidxCoreKtx = "1.2.0"
    const val androidxLifecyle = "2.2.0"
    const val androidxRoom = "2.2.5"
    const val androidxLocalBroadcastManager = "1.0.0"
    const val androidxActivityKtx = "1.1.0"
    //endregion

    const val kotlinxCoroutines = "1.3.5"
    const val dagger = "2.27"

    //region Tools
    const val kotlin = "1.3.72"
    const val gradleAndroid = "3.6.0"
    //endregion
}

object Deps {
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
    const val androidxAnnotation = "androidx.annotation:annotation:${Versions.androidxAnnotation}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintlayout}"
    const val androidxRecyclerview =
        "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerview}"
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.androidxCoreKtx}"
    const val androidxLifecycleViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecyle}"
    const val androidxRoom = "androidx.room:room-runtime:${Versions.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${Versions.androidxRoom}"
    const val androidxRoomKtx = "androidx.room:room-ktx:${Versions.androidxRoom}"
    const val androidxLocalBroadcastManager = "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.androidxLocalBroadcastManager}"
    const val androidxActivityKtx ="androidx.activity:activity-ktx:${Versions.androidxActivityKtx}"


    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinxCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndProccessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val daggerAndSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"


    const val toolsGradleAndroid = "com.android.tools.build:gradle:${Versions.gradleAndroid}"
    const val toolsKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val toolsKotlinSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"

}
