#!/bin/bash

cat performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_all.png

head -n 18 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_100.png

head -n 11 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_11.png
