# custom-info-window-maps

Monorepo internal untuk override implementation package `google_maps_flutter` agar marker menggunakan custom native info window yang posisinya mengikuti rendering native Google Maps.

Isi repo:

- `google_maps_flutter_android`
- `google_maps_flutter_ios`

## Latar belakang

`google_maps_flutter` Flutter plugin belum expose native `InfoWindowAdapter` Android atau custom native info window iOS ke layer Dart. Solusi overlay Flutter dan package `custom_info_window` masih menghasilkan gap/offset yang berbeda antar device.

Repo ini menyimpan fork implementation package yang menambahkan custom native info window langsung di platform Android dan iOS.

## Integrasi di aplikasi

Tambahkan override berikut di `pubspec.yaml` aplikasi:

```yaml
dependency_overrides:
  google_maps_flutter_android:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter_android
  google_maps_flutter_ios:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter_ios
```

Lalu jalankan:

```bash
flutter pub get
```

## Patch utama

### Android

- Menambahkan `CustomMarkerInfoWindowAdapter`
- Mendaftarkan adapter di `GoogleMapController.onMapReady`

### iOS

- Menambahkan `mapView:markerInfoWindow:` di `FGMGoogleMapController`

## Catatan

- Surface API Dart aplikasi tetap memakai `InfoWindow(title/snippet)` bawaan `google_maps_flutter`.
- Custom tampilan dirender native di Android dan iOS.
- Jika nanti ingin surface Dart yang lebih kaya, langkah berikutnya adalah fork package induk `google_maps_flutter` dan menambah API baru di layer platform interface.
