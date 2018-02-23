Tugas Besar IF3111 Platform Based Development
===========================================
Group IF3111-2018-SAMS

K-02 Group-4

# Contributors

1. 13515002 Wenny Yustalim
2. 13515008 Kanisius Kenneth Halim
3. 13515038 Ferdinandus Richard

# Installation Guide

1. Clone Android project repository from terminal

```
git clone http://gitlab.informatika.org/IF3111-2018-SAMS/android.git
```

2. Clone Beezy server repository from terminal

```
git clone http://gitlab.informatika.org/IF3111-2018-SAMS/beezy.git
```

3. Download and install Android Studio (optional)
[Install Android Studio](https://developer.android.com/studio/install.html)

4. Open cloned project in Android Studio

5. Setup Android SDK
[Install Android SDK in Android Studio](http://www.gadgetreview.com/how-to-install-android-sdk-android-studio)

6. Run your application from Android Studio using Android emulator or your own Android gadget.

7. *Bee* amazed by *Unbeezy*.

# Application description

Unbeezy is an Android-based reminder application that receives input
from user in the form of class schedules, assignments, and alarms. This
application triggers a *Panic Attack* everytime an event needs to be
reminded, according to the time set by the users themselves. There are
a few interactive alarm dismisser options, by shaking or by light detection.

There are a few additions to the system that will be implemented in the
next milestone, such as integration with Arduino. This system is
integrated to a server to receive notification via Firebase Cloud Messaging.
Sign in method is implemented using Google Single Sign On.

# How to Use
|Feature|Description|
|:-:|:--|
|Google Single Sign On|Melakukan sign in ke aplikasi dengan akun Google yang terdaftar pada device Android|
|Add Course|Menambahkan kuliah ke aplikasi beserta jadwal dan kode warna kuliah tersebut|
|Select Course Schedule|Memilih jadwal kuliah yang akan dibuat ke aplikasi, pemilihan dilakukan setelah “Add Course”|
|Select Course Color|Memilih kode warna kuliah di aplikasi, pemilihan dilakukan setelah “Add Couse”|
|Add Task|Menambahkan sebuah task ke aplikasi, dapat berupa deadline, event tertentu yang bekerja sebagai reminder|
|Add Alarm|Menambahkan alarm ke aplikasi yang akan bunyi pada waktu yang ditentukan user|
|View Course Schedule|Melihat jadwal kuliah yang sudah dimasukkan ke dalam aplikasi|
|View Task List|Melihat task yang sudah dimasukkan ke aplikasi|
|View Alarm List|Melihat list alarm yang sudah dimasukkan ke aplikasi|
|Delete Course|Menghapus schedule yang sudah dibuat dari aplikasi|
|Rise and Shine Panic Attack|Alarm yang harus dimatikan dengan cara memberikan cahaya terang pada device Android|
|Shake It Off Panic Attack|Alarm yang harus dimatikan dengan cara memberikan guncangan/goyangan pada device Android|
|Changing Alarm Dismisser|Mengganti metode mematikan alarm yang sudah dibuat menjadi salah satu dari “Rise and Shine” atau “Shake It Off”|
|Send Email to Lecturer|Mengirim email ke pengajar yang sudah didaftarkan ke kuliah yang sudah dibuat|
|Task Reminder|Mengirim push notification ke aplikasi apabila task yang sudah dibuat berada kurang dari atau sama dengan 3 hari sebelum task tersebut terjadi|
|Course Reminder Panic Attack|Menyalakan alarm untuk mengikuti kuliah apabila user berada 600 m dari ITB, 30 menit sebelum perkuliahan dimulai|
|Course Reminder|Mengirim push notification ke aplikasi apabila user berada dalam radius 600 m dari ITB, 15 menit sebelum perkuliahan dimulai|

# Deliverables Location

|Deliverables|Location|
|:--|:--|
|Source code|http://gitlab.informatika.org/IF3111-2018-SAMS/android.git|
|Initial proposal|https://drive.google.com/open?id=1efJIjy6hfVz_5Zlzbj5WNgq8AsIT43Nj|
|Final proposal|https://drive.google.com/open?id=1_PTVpNIwHn1OQL0tmGiIZfzBd1R9fmyj|
|Report|https://drive.google.com/open?id=1edCrZ775Rrqwvdes_KA_UYvErhEMb41u|
|README|If you're reading this, this is the readme file|
|Video|To be made at the end of the 3rd milestone|