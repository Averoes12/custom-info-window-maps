# custom-info-window-maps

Monorepo internal untuk override implementation package `google_maps_flutter` agar marker menggunakan custom native info window yang posisinya mengikuti rendering native Google Maps.

Isi repo:

- `google_maps_flutter`
- `google_maps_flutter_android`
- `google_maps_flutter_ios`
- `google_maps_flutter_platform_interface`

## Latar belakang

`google_maps_flutter` Flutter plugin belum expose native `InfoWindowAdapter` Android atau custom native info window iOS ke layer Dart. Solusi overlay Flutter dan package `custom_info_window` masih menghasilkan gap/offset yang berbeda antar device.

Repo ini menyimpan fork implementation package yang menambahkan custom native info window langsung di platform Android dan iOS.

## Integrasi di aplikasi

Tambahkan override berikut di `pubspec.yaml` aplikasi:

```yaml
dependency_overrides:
  google_maps_flutter:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter
  google_maps_flutter_android:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter_android
  google_maps_flutter_ios:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter_ios
  google_maps_flutter_platform_interface:
    git:
      url: git@github.com:Averoes12/custom-info-window-maps.git
      path: google_maps_flutter_platform_interface
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

## API custom dari Flutter

Fork ini menambahkan `InfoWindowStyle` ke `InfoWindow`, sehingga style bubble bisa dikirim dari aplikasi Flutter dan dirender native di Android/iOS.

Contoh:

```dart
const infoWindowStyle = InfoWindowStyle(
  backgroundColor: Color(0xFF14A3B8),
  borderColor: Colors.white,
  titleTextColor: Colors.white,
  snippetTextColor: Colors.white,
  cornerRadius: 18,
  borderWidth: 2,
  horizontalPadding: 18,
  verticalPadding: 12,
  titleFontSize: 12,
  snippetFontSize: 18,
  minWidth: 144,
  maxWidth: 220,
  titleBold: false,
  snippetBold: true,
);

Marker(
  markerId: const MarkerId('customer'),
  position: const LatLng(-6.2, 106.8),
  infoWindow: const InfoWindow(
    title: 'ID Pelanggan',
    snippet: '123456789010',
    style: infoWindowStyle,
  ),
)
```

## Catatan

- Native Android menggunakan `GoogleMap.InfoWindowAdapter`.
- Native iOS menggunakan `mapView:markerInfoWindow:`.
- Custom tampilan dirender native di Android dan iOS.
