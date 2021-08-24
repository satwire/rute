package com.example.skripsigre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

public class dijkstra {
    String[][] graph = new String[100][100];
    String jalur_terpendek = "";
    String status = "none";

    void jalurterpendek(String[][] arg_graph, int simpulawal, int simpultujuan) {
        System.out.println("sa: " + simpulawal + " & st : " + simpultujuan);
        if (simpulawal == simpultujuan) {
            status = "die";
            return;
        }

        graph = arg_graph;
        int simpul_awal = simpulawal;
        int simpul_maju = simpul_awal;
        int simpul_tujuan = simpultujuan;

        // hitung jml smpl
        if (simpul_maju != simpul_tujuan) {
            int jumlah_simpul = 0;
            for (String[] array : graph) {
                if (array[0] != null) {
                    jumlah_simpul += 1;
                }
            }
            List<Integer> simpulygdikerjakan = new ArrayList<>();

            List<Integer> simpulselesai = new ArrayList<>();

            double nilaisimpulditandai = 0;
            double nilaisimpulfix = 0;

            // looping
            for (int perulangan = 0; perulangan < 1; perulangan++) {
                // dapatkan 1 bobot minimum dr smua simpul
                List<Double> perbandingansemuabobot = new ArrayList<>();
                // daftarkan simpul pertama yg dikerjakan ke array
                if (!simpulygdikerjakan.contains(simpul_maju)) {
                    simpulygdikerjakan.add(simpul_maju);
                }
                // looping simpul yg ditandai
                for (int loopingsimpul = 0; loopingsimpul < simpulygdikerjakan.size(); loopingsimpul++) {
                    // hitung jumlah baris per kolom simpul
                    int jml_bristtap = 0;
                    for (int min_batas_baris = 0; min_batas_baris < 100; min_batas_baris++) {
                        if (graph[simpulygdikerjakan.get(loopingsimpul)][min_batas_baris] != null) {
                            jml_bristtap += 1;
                        }
                    }
                    // cari bobot minimum berdasar urut [0][0]
                    List<Double> bobot = new ArrayList<>();
                    int status_baris = 0;

                    // looping find 2 bobot di 1 simpul

                    for (int min_batas_baris_ttap = 0; min_batas_baris_ttap < jml_bristtap; min_batas_baris_ttap++) {
                        String bobotruas = graph[simpulygdikerjakan.get(loopingsimpul)][min_batas_baris_ttap];
                        String[] explode;
                        explode = bobotruas.split("->");
                        // cari bobot yg belum dikerjakan
                        if (explode.length == 2) {
                            status_baris += 1;

                            if (!simpulselesai.isEmpty()) {
                                if (simpulselesai.contains(simpulygdikerjakan.get(loopingsimpul))) {
                                    nilaisimpulditandai = 0;
                                } else {
                                    nilaisimpulditandai = nilaisimpulfix;
                                }
                            }
                            bobot.add(Double.parseDouble(explode[1]) + nilaisimpulditandai);
                        }
                        graph[simpulygdikerjakan.get(loopingsimpul)][min_batas_baris_ttap] = String
                                .valueOf(explode[0] + "->" + (Double.parseDouble(explode[1] + nilaisimpulditandai)));
                    }
                    if (status_baris > 0) {
                        // dapatkan bobot minimum
                        for (int indexbobot = 0; indexbobot < bobot.size(); indexbobot++) {
                            if (bobot.get(indexbobot) <= bobot.get(0)) {
                                bobot.set(0, bobot.get(indexbobot));
                            }
                        }
                    } else {
                        continue;
                    }
                    // daftar simpul yg baru selesai dikerjakan
                    if (!simpulselesai.contains(simpulygdikerjakan.get(loopingsimpul))) {
                        simpulselesai.add(simpulygdikerjakan.get(loopingsimpul));
                    } // end looping simpul
                }
                // get 1 bobot minimum dari simpul yg ditandai
                for (int minindexantarbobot = 0; minindexantarbobot < perbandingansemuabobot
                        .size(); minindexantarbobot++) {
                    if (perbandingansemuabobot.get(minindexantarbobot) <= perbandingansemuabobot.get(0)) {
                        perbandingansemuabobot.set(0, perbandingansemuabobot.get(minindexantarbobot));
                    }
                }
                // get index simpul+bobot yg asli dari simpul yg ditandai
                int indexawalreal = 0;// index ximpul
                int statusbaris1 = 0;
                int getindexbobotasli = 0;
                int simpullama = 0;
                int status_baris = 0;
                for (Integer indexasli_bobot : simpulygdikerjakan) {
                    for (int baris1 = 0; baris1 < 100; baris1++) {
                        if (graph[simpulygdikerjakan.get(indexawalreal)][baris1] != null) {
                            String bobotruas1 = graph[simpulygdikerjakan.get(indexawalreal)][baris1];
                            String[] explode1;
                            explode1 = bobotruas1.split("->");
                            if (explode1.length == 2) {
                                if (perbandingansemuabobot.get(0) == Double.parseDouble(explode1[1])) {
                                    getindexbobotasli = baris1;
                                    simpullama = simpulygdikerjakan.get(indexawalreal);
                                    simpul_maju = Integer.parseInt(explode1[0]);
                                    status_baris += 1;
                                }
                            }
                        }
                    }
                    indexawalreal++;
                    // buletin bobot minimal yg udh didapet dan delete ruas yg berhubungan
                    if (status_baris > 0) {
                        graph[simpullama][getindexbobotasli] = graph[simpullama][getindexbobotasli] + "->y";
                        // dlete ruas lain
                        for (int min_kolom = 0; min_kolom < jumlah_simpul; min_kolom++) {
                            for (int min_baris = 0; min_baris < 100; min_baris++) {
                                if (graph[min_kolom][min_baris] != null) {
                                    String ruasygakandihapus = graph[min_kolom][min_baris];
                                    String[] explode3 = ruasygakandihapus.split("->");
                                    if (explode3.length == 2) {
                                        if (explode3[0].equals(String.valueOf(simpul_maju))) {
                                            graph[min_baris][min_kolom] = graph[min_kolom][min_baris] + "->t";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    nilaisimpulfix = perbandingansemuabobot.get(0);
                    if (simpul_maju != simpul_tujuan) {
                        --perulangan;
                    } else {
                        break;
                    }

                    // taro simpul gabungan ke array example 1-2
                    List<String> gabungansimpulplihan = new ArrayList<>();
                    for (int a = 0; a < jumlah_simpul; a++) {
                        for (int b = 0; b < 100; b++) {
                            if (graph[a][b] != null) {
                                String str_graph = graph[a][b];
                                if (str_graph.substring(str_graph.length() - 1).equals("y")) {
                                    String[] explode4 = graph[a][b].split("->");
                                    String simpulgabung = a + "-" + explode4[0];

                                    gabungansimpulplihan.add(simpulgabung);
                                }
                            }
                        }
                    }
                    List<Integer> simpulfixfinish = new ArrayList<>();
                    simpulfixfinish.add(simpul_tujuan);
                    int simpulexplode = simpul_tujuan;

                    for (int d = 0; d < 1; d++) {
                        for (int e = 0; e < gabungansimpulplihan.size(); e++) {
                            String explodesimpl = gabungansimpulplihan.get(e);
                            String[] explode5 = explodesimpl.split("-");
                            if (simpulexplode == Integer.parseInt(explode5[1])) {
                                simpulfixfinish.add(Integer.parseInt(explode5[0]));
                                simpulexplode = Integer.parseInt(explode5[0]);
                            }
                            if (simpulexplode == simpul_awal) {
                                break;
                            }
                        }
                        if (simpul_awal != simpulexplode) {
                            --d;
                        } else {
                            break;
                        }
                    }
                    // aray dibalik index, simpul dipindah ke ahir index array
                    Collections.reverse(simpulfixfinish);
                    String jalur_terpendek = "";
                    for (int x = 0; x < simpulfixfinish.size(); x++) {
                        if (x == simpulfixfinish.size() - 1) {
                            jalur_terpendek += simpulfixfinish.get(x);
                        } else {
                            jalur_terpendek += simpulfixfinish.get(x) + "->";
                        }
                    }
                    // jalur_terpendek = jalur_terpendek;
                }
            }
        }
    }
}