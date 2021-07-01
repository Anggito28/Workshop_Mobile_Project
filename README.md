# Petunjuk untuk Memulai Projek

1. Clone projek
2. Buka di android studio
3. Mengatur `network_security_config.xml` :
  - Buka folder `Rudibonsai/app/src/main/res/xml/`
  - Duplikat / copas file `network_security_config_sample.xml`
    - __File `network_security_config_sample.xml` JANGAN DIHAPUS DAN JANGAN DIUBAH!!!__ 
  - Ganti nama file duplikatnya menjadi `network_security_config.xml`
  - Buka file `network_security_config.xml`
  - Ubah bagian `SERVER_ADDRESS` di `network_security_config.xml` menjadi alamat ip laptop / laravel
4. Mengatur `ConstantValue` :
  - Buka folder `Rudibonsai/app/src/main/java/com/kelompok2/rudibonsai/constant`
  - Duplikat / copas file `ConstantValueSample`
    - __File `ConstantValueSample` JANGAN DIHAPUS DAN JANGAN DIUBAH!!!__ 
  - Ganti nama file duplikatnya menjadi `ConstantValue`
  - Buka file `ConstantValue`
  - Ubah bagian "IP_ADDRESS" di "ConstantValue" menjadi alamat ip laptop / laravel
5. Mulai koding
