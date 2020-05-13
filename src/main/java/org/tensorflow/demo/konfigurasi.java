package org.tensorflow.demo;

public class konfigurasi {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_GET_ALL = "http://192.168.43.81/insect_f/readall.php";
    public static final String URL_GET_EMP = "http://192.168.43.81/insect_f/readone.php?id_hama=";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_IDHAMA = "id_h";
    public static final String TAG_NAMALATIN = "name_l";
    public static final String TAG_NAMAUMUM = "name_u";
    public static final String TAG_HAMAPADA = "hama_p";
    public static final String TAG_CARABASMI = "cara_b";

    //ID karyawan
    public static final String HAM_ID = "hama_id";
    public static final String HAM_NM = "hama_nama";
}
