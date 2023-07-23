#!/bin/bash

cat performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_entire.png

head -n 18 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_head.png

head -n 11 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_low.png
