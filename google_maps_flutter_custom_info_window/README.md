# google_maps_flutter_custom_info_window

Wrapper package tunggal untuk memakai fork `google_maps_flutter` dengan configurable native custom info window.

Dengan package ini, aplikasi cukup menambahkan satu dependency di `pubspec.yaml`:

```yaml
google_maps_flutter_custom_info_window:
  git:
    url: git@github.com:Averoes12/custom-info-window-maps.git
    ref: v0.3.0
    path: google_maps_flutter_custom_info_window
```

Lalu bisa import salah satu dari dua cara berikut:

```dart
import 'package:google_maps_flutter_custom_info_window/google_maps_flutter_custom_info_window.dart';
```

atau tetap memakai import lama jika diperlukan:

```dart
import 'package:google_maps_flutter/google_maps_flutter.dart';
```

Contoh `InfoWindowStyle`:

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
```
